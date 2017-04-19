import javax.swing.*;
import java.awt.*;
import java.awt.image.renderable.ContextualRenderedImageFactory;

public class Q1
{

    public static void main(String[] args){
        JFrame myFrame = new JFrame("Simple Submit Cancel Form");

        JTextField textField = new JTextField(10);
        JButton submit = new JButton("Submit");
        JButton cancel = new JButton("Cancel");

        Container base = myFrame.getContentPane();
        base.setLayout(new GridLayout(2,1));

        Container text = new Container();
        text.setLayout(new FlowLayout());
        text.add(textField);

        Container buttons = new Container();
        buttons.setLayout(new FlowLayout());
        buttons.add(submit);
        buttons.add(cancel);

        base.add(text);
        base.add(buttons);

        myFrame.pack();
        myFrame.setVisible(true);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
