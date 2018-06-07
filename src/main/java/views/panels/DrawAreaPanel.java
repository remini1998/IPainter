package views.panels;

import javax.swing.*;
import java.awt.*;

public class DrawAreaPanel extends JPanel {
    public DrawAreaPanel(){

    }

    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.getContentPane().add(new DrawAreaPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public void paint(Graphics g){
        super.paint(g);
        setBackground(Color.white);
    }

}
