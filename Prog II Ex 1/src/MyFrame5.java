import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

public class MyFrame5 extends JFrame{

    JTextField textField;
    String font;
    boolean boldB = false, italicB = false;

    public MyFrame5(String title){
        super(title);

        Container container = getContentPane();
        container.setLayout(new FlowLayout());

        JButton ok = new JButton("OK");
        textField = new JTextField(20);
        textField.setEditable(false);

        JRadioButton times = new JRadioButton("Times");
        JRadioButton helvetica = new JRadioButton("Helvetica");
        JRadioButton courier = new JRadioButton("Courier");

        JCheckBox bold = new JCheckBox("Bold");
        JCheckBox italic = new JCheckBox("Italic");

        ButtonGroup radio = new ButtonGroup();
        radio.add(times);
        radio.add(helvetica);
        radio.add(courier);

        Container radioPanel = new Container();
        radioPanel.setLayout(new GridLayout(3,1));
        radioPanel.add(times);
        radioPanel.add(helvetica);
        radioPanel.add(courier);

        //String[] fontStrings = { "Times", "Helvetica", "Courier"};
       // JComboBox<String> fonts = new JComboBox<>(fontStrings);

        times.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                font = "Times New Roman";
                updateText();
            }
        });

        helvetica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                font = "Helvetica";
                updateText();
            }
        });

        courier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                font = "Courier";
                updateText();
            }
        });
        bold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                boldB = !boldB;
                updateText();
            }
        });
        italic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                italicB = !italicB;
                updateText();
            }
        });
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });


        Container checkPanel = new Container();
        checkPanel.setLayout(new GridLayout(2,1));
        checkPanel.add(bold);
        checkPanel.add(italic);

        container.add(checkPanel);
        container.add(radioPanel);
        //container.add(fonts);
        container.add(textField);
        container.add(ok);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);

    }

    public void updateText(){
        if(boldB && italicB){
            textField.setFont(new Font(font, Font.BOLD+Font.ITALIC, 12));
            textField.setText("Bold and Italic "+font);

            return;
        }

        if(boldB){
            textField.setFont(new Font(font, Font.BOLD, 12));
            textField.setText("Bold "+font);
        }else if(italicB){
            textField.setFont(new Font(font, Font.ITALIC, 12));
            textField.setText("Italic "+font);
        }else{
            textField.setFont(new Font(font, Font.PLAIN, 12));
            textField.setText(font);
        }
    }
}
