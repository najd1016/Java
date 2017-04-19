import javax.swing.*;
import java.awt.*;

public class Die extends JPanel{

    int value;

    public Die(){
        value = 1;
    }

    public void updateVal(int i){
        value = i;
    }

    public void paint(Graphics g){
        g.setColor(Color.black);
        g.fillOval(50,50,25,25);
    }
}
