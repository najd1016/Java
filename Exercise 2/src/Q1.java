import javax.swing.*;
import java.awt.*;

public class Q1 extends JFrame {

    Die die;

    public static void main(String args[]){
        Q1 myQ1 = new Q1();
    }

    public Q1(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,400);
        setLayout(new FlowLayout());
        setBackground(Color.white);
        die = new Die();
        add(die);
        setVisible(true);
    }

    public void paint(Graphics g){
      die.paint(g);
    }
}
