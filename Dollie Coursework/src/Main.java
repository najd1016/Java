import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;


public class Main extends JFrame {

    public static void main(String[] args){
        Main gui = new Main();
    }

    private Main(){
        setTitle("Doily GUI - Niall Dickin - 28292545");

        add(new MainPanel());

        pack();

        setVisible(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //Custom JPanel class which contains all 3 panels, button/control panel drawing panel and gallery panel
    private class MainPanel extends JPanel{

        public MainPanel(){
            setLayout(new BorderLayout());
            //dimension set so that drawing area 800x800 and other two panels
            //are 800x100.
            setPreferredSize(new Dimension(800,1000));

            DoilyPanel doilyPanel = new DoilyPanel();
            //gallery panel requires doily panel reference to allow the
            //save button in the gallery panel to access galleryPanel
            GalleryPanel galleryPanel = new GalleryPanel();

            //we don't want resizing as it is difficult to keep dots in the
            //correct place and same size etc
            setResizable(false);

            //current colour is a solid block indicating the current brush colour
            JLabel currentColour = new JLabel();
            currentColour.setBackground(doilyPanel.getBrushColour());
            currentColour.setOpaque(true);

            JButton chooseColour = new JButton("Pick Colour");
            chooseColour.addActionListener(actionEvent -> {
                Color newColour = JColorChooser.showDialog(
                        doilyPanel,
                        "Choose Brush Colour",
                        doilyPanel.getBrushColour());

                //null returned when you cancel colour chooser - we don't want to set our colour to null
                if(newColour != null) {
                    doilyPanel.setBrushColour(newColour);
                    currentColour.setBackground(newColour);
                }
            });

            JButton clear = new JButton("Clear");
            clear.addActionListener(actionEvent -> doilyPanel.clear());

            JButton undo = new JButton("Undo");
            undo.addActionListener(actionEvent -> doilyPanel.undo());

            JButton save = new JButton("Save");
            save.addActionListener(actionEvent ->
                    galleryPanel.addImage(doilyPanel.getImage()));

            JButton remove = new JButton("Remove");
            remove.addActionListener(actionEvent ->
                    galleryPanel.removeSelected());

            Container galleryButtons = new Container();
            galleryButtons.setLayout(new GridLayout(2,1));
            galleryButtons.add(save);
            galleryButtons.add(remove);

            Container editButtons = new Container();
            editButtons.setLayout(new GridLayout(2,1));
            editButtons.add(clear);
            editButtons.add(undo);

            Container colourCon = new Container();
            colourCon.setLayout(new GridLayout(2,1));
            colourCon.add(currentColour);
            colourCon.add(chooseColour);

            //show sectors by default
            JRadioButton toggleSectorLines = new JRadioButton("Show Sectors");
            toggleSectorLines.setSelected(true);
            //toggles showing sectors
            toggleSectorLines.addItemListener(itemEvent ->
                    doilyPanel.setShowSectors(itemEvent.getStateChange() == ItemEvent.SELECTED));

            JRadioButton reflectSectors = new JRadioButton("Reflect Sectors");
            reflectSectors.setSelected(false);
            //toggles reflecting new points
            reflectSectors.addItemListener(itemEvent ->
                    doilyPanel.setReflect(itemEvent.getStateChange() == ItemEvent.SELECTED));


            JLabel sectorLabel = new JLabel("Number of Sectors", JLabel.CENTER);

            JSlider sectors = new JSlider(4, 52, doilyPanel.getSectors());
            sectors.setPreferredSize(new Dimension(170,45));
            sectors.setMajorTickSpacing(12);
            sectors.setMinorTickSpacing(1);
            sectors.setPaintLabels(true);
            sectors.setPaintTicks(true);

            //alters number of sector lines when changed
            sectors.addChangeListener(changeEvent -> {
                JSlider slider = (JSlider) changeEvent.getSource();
                doilyPanel.setSectors(slider.getValue());
            });

            //container for sector numbers slider and jlabel
            Container sectorContainer = new Container();
            sectorContainer.setLayout(new GridLayout(2,1));
            sectorContainer.add(sectorLabel);
            sectorContainer.add(sectors);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout());
            buttonPanel.setPreferredSize(new Dimension(800, 100));

            JPanel radioPanel = new JPanel();
            radioPanel.setLayout(new GridLayout(2,1));
            radioPanel.add(toggleSectorLines);
            radioPanel.add(reflectSectors);

            JLabel brushLabel = new JLabel("Brush Size", JLabel.CENTER);

            JSlider brushSize = new JSlider(1, 34, doilyPanel.getBrushSize());
            brushSize.setPreferredSize(new Dimension(170,45));
            brushSize.setMajorTickSpacing(11);
            brushSize.setMinorTickSpacing(1);
            brushSize.setPaintLabels(true);
            brushSize.setPaintTicks(true);

            //alters brush size when slider changed
            brushSize.addChangeListener(changeEvent -> {
                JSlider slider = (JSlider) changeEvent.getSource();
                doilyPanel.setBrushSize(slider.getValue());
            });

            //container for brush size slider and jlabel
            Container brushContainer = new Container();
            brushContainer.setLayout(new GridLayout(2,1));
            brushContainer.add(brushLabel);
            brushContainer.add(brushSize);

            buttonPanel.add(sectorContainer);
            buttonPanel.add(radioPanel);
            buttonPanel.add(galleryButtons);
            buttonPanel.add(editButtons);
            buttonPanel.add(colourCon);
            buttonPanel.add(brushContainer);

            add(buttonPanel, BorderLayout.NORTH);
            add(doilyPanel, BorderLayout.CENTER);
            add(galleryPanel, BorderLayout.SOUTH);
        }
    }
}
