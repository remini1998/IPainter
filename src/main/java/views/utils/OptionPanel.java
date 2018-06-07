package views.utils;

import views.foundations.AlphaAnimationPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class OptionPanel extends AlphaAnimationPanel {
    public OptionPanel(){
        Dimension size = new Dimension(250, 500);
        this.setPreferredSize(size);
        this.setSize(size);
        this.setMaximumSize(size);
//        this.setBackground(null);
//        this.setOpaque(false);
    }

    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.getContentPane().add(new OptionPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public void paint(Graphics g){
        super.paint(g);
        setBackground(new Color(250, 250, 250,180));
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        Border borderWithTitle = BorderFactory.createTitledBorder(border, "工具选项");
        setBorder(borderWithTitle);
    }

}
