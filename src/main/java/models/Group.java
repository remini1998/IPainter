package models;

import java.awt.*;
import java.util.Vector;

//组合模式
public class Group extends Shape {
    protected String name = "Group";
    public Vector<Shape> shapes = new Vector<Shape>();
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Shape s: shapes){
            sb.append(s.toString());
            sb.append('\n');
        }
        return name + " {\n" + sb.toString() + "}";
    }
    public void translate(double dx, double dy){
        shapes.forEach(p -> p.translate(dx, dy));
    }
    public void rotate(double alpha, double cx, double cy){
        shapes.forEach(p -> p.rotate(alpha, cx, cy));
    }
    public void rotate(double alpha){
        shapes.forEach(p -> p.rotate(alpha));
    }


    public Color getColor() {
        return null;
    }

    public void setColor(Color color) {
        shapes.forEach(p -> p.setColor(color));
    }

    public double getWidth() {
        return 0;
    }

    public void setWidth(double width) {
        shapes.forEach(p -> p.setWidth(width));
    }

    public void add(Shape s){
        shapes.add(s);
    }
}
