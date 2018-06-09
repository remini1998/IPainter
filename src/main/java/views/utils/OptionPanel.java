package views.utils;

import views.foundations.AlphaAnimationPanel;
import views.utils.ShapeSetting.ShapeSettingPanel;
import views.utils.ShapeTools.ShapeToolPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class OptionPanel extends AlphaAnimationPanel {

    private ShapeSettingPanel shapeSettingComponent = new ShapeSettingPanel();
    private ShapeToolPanel shapeToolComponent = new ShapeToolPanel();

    public OptionPanel(){
        Dimension size = new Dimension(250, 500);
        this.setPreferredSize(size);
        this.setSize(size);
        this.setMaximumSize(size);
//        this.setOpaque(false);
        this.add(shapeToolComponent);
        this.add(shapeSettingComponent);
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
        setBackground(new Color(255, 255, 255,255));
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        Border borderWithTitle = BorderFactory.createTitledBorder(border, "工具选项");
        setBorder(borderWithTitle);
    }

}
