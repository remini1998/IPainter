package views.panels;

import models.shapes.Shape;
import views.utils.DrawToolsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class DrawAreaPanel extends JPanel {

    private DrawAreaPanel that = this;

    private Vector<Shape> shapes = new Vector<>();
    private Vector<Shape> drawingList = new Vector<>();

    private DrawToolsPanel.DrawTools nowSelected = DrawToolsPanel.DrawTools.NONE;

    public class SelectionChangeEventHandler implements DrawToolsPanel.SelectionListener{
        @Override
        public void selectedChanged(DrawToolsPanel.DrawTools nowSelected) {
            that.nowSelected = nowSelected;
        }
    }

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
        this.shapes.forEach(s -> s.draw(g));
        this.drawingList.forEach(s -> s.drawing(g));
    }

    public void setShapes(Vector<Shape> shapes){
        this.shapes = shapes;
    }

    public void setDrawingList(Vector<Shape> drawingList){
        this.drawingList = drawingList;
    }
}
