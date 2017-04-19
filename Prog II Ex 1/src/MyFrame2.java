import javax.swing.*;
import java.awt.*;

public class MyFrame2 extends JFrame{

    public MyFrame2(String title){
        super(title);

        Container container = getContentPane();
        container.setLayout(new FlowLayout());

        JButton ok = new JButton("OK");
        JTextField textField = new JTextField(15);

        JRadioButton times = new JRadioButton("Times");
        JRadioButton helvetica = new JRadioButton("Helvetica");
        JRadioButton courier = new JRadioButton("Courier");

        JCheckBox bold = new JCheckBox("Bold");
        JCheckBox italic = new JCheckBox("Italic");

        ButtonGroup radio = new ButtonGroup();
        radio.add(times);
        radio.add(helvetica);
        radio.add(courier);

        //Container radioPanel = new Container();
        //radioPanel.setLayout(new GridLayout(3,1));
        // radioPanel.add(times);
        //radioPanel.add(helvetica);
        //radioPanel.add(courier);

        String[] fontStrings = { "Times", "Helvetica", "Courier"};
        JComboBox<String> fonts = new JComboBox<>(fontStrings);


        Container checkPanel = new Container();
        checkPanel.setLayout(new GridLayout(2,1));
        checkPanel.add(bold);
        checkPanel.add(italic);

        container.add(checkPanel);
        //container.add(radioPanel);
        container.add(fonts);
        container.add(textField);
        container.add(ok);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);

    }
}
