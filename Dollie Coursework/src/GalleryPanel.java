import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GalleryPanel extends JPanel {

    private List<JLabel> labels;

    private JPanel imagePanel;

    private int index;

    //Sets the size of the panel, initialises the array along with other variables
    //and creates the scrollPane which is then attached to the panel.
    public GalleryPanel(){

        setPreferredSize(new Dimension(800,100));

        labels = new ArrayList<>();

        index = 0;

        setBackground(Color.WHITE);

        //required to make JScrollPane display correctly
        setLayout(new BorderLayout());

        imagePanel = new JPanel();

        initialiseImagePanel();

        JScrollPane scrollPane = new JScrollPane(imagePanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);
    }

    //A new imageListener is created. Then 12 empty jLabels with the attached listener
    //are created and added to both the imagePanel (scrollPane) and the arrayList of JLabels
    private void initialiseImagePanel(){
        ImageListener imageListener = new ImageListener();

        for(int i = 0; i < 12; i++) {
            JLabel label = new JLabel();

            label.setForeground(Color.WHITE);
            label.setPreferredSize(new Dimension(75,75));
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.CENTER);

            label.addMouseListener(imageListener);

            labels.add(label);
            imagePanel.add(labels.get(i));
        }
    }

    //Takes the image parameter and scales it. A imageIcon is then made from this
    //and set as the icon of the label at the index. Index is then incremented so as
    //to move along the list.
    public void addImage(BufferedImage image){
        if(index < 12) {
            Image scaledInstance = image.getScaledInstance(75, 75, Image.SCALE_SMOOTH);

            ImageIcon imageIcon = new ImageIcon(scaledInstance);
            labels.get(index).setIcon(imageIcon);

            index++;
        }
    }

    //Iterates through each label in the array list and remove the icon, text and border
    //from those that have been selected. Then calls sort array list.
    public void removeSelected(){

        for (JLabel label: labels) {
            if(label.getBorder() != null) {
                label.setIcon(null);
                label.setText("");
                label.setBorder(null);
            }
        }

        sortArrayList();
    }

    //Sorts the array list after removing saved images so that there aren't any gaps remaining
    //Gaps being labels with no/a blank icon. Then finds the first empty jlabel after sorting and
    //sets index = this blank space's index
    private void sortArrayList(){

        for (int x = 0; x < 12; x++) {

            //if it does have an icon then iterate through all previous positions
            //in array list and check if they're empty and thus we can move the icon there
            if(labels.get(x).getIcon() != null){

                for (int y = 0; y < x; y++) {

                    if(labels.get(y).getIcon() == null){
                        //swaps icon of empty label with icon at label position x
                        labels.get(y).setIcon(labels.get(x).getIcon());
                        //since has swapped can now make x empty, by setting icon = null
                        labels.get(x).setIcon(null);

                        break;
                    }
                }
            }
        }

        for (JLabel label: labels) {
            if(label.getIcon() == null) {
                index = labels.indexOf(label);
                break;
            }
        }
    }

    //Mouse adapter which is attached to each label. When it detects that the label is clicked
    //(have to click right in the center) it will invert is selected state. If it is now
    //selected it has a red border and text, otherwise it is just the image of the doily.
    //Only doily images and not blank jlabels can be selected.
    private class ImageListener extends MouseAdapter{

        boolean selected = false;

        @Override
        public void mouseClicked(MouseEvent e) {

            JLabel source = (JLabel) e.getSource();

            if (source.getIcon() != null) {
                selected = !selected;

                if (selected) {
                    source.setText("Selected");
                    source.setBorder(BorderFactory.createMatteBorder(
                            2, 2, 2, 2, Color.red));
                }else{
                    source.setText("");
                    source.setBorder(null);
                }
            }
        }
    }
}
