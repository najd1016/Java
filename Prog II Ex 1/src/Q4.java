import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Q4 implements ActionListener{

    int presses;
    JTextField textField;

    public Q4(){
        JFrame myFrame = new JFrame("Increment");

        Container pane = myFrame.getContentPane();
        pane.setLayout(new FlowLayout());

        JButton increase = new JButton("Increment");
        JButton reset = new JButton("Reset");
        textField = new JTextField(3);

        presses = 0;

        reset.addActionListener(this);
        increase.addActionListener(this);

        textField.setText(""+presses);

        pane.add(increase);
        pane.add(reset);
        pane.add(textField);

        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.pack();
        myFrame.setVisible(true);

    }

    public static void main(String[] args){
        Q4 q4 = new Q4();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton src = (JButton) actionEvent.getSource();

        if(src.getText().equals("Reset")){
            presses = 0;
        }else{
            presses++;
        }

        textField.setText(""+presses);
    }
}
