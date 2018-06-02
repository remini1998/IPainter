package models;

import java.awt.*;

public class Line extends Shape {
    protected String name = "Line";
    public Line(Color color, double width, double x1, double y1, double x2, double y2){
        super(color, width);
        MyPoint p1 = new MyPoint(x1, y1);
        MyPoint p2 = new MyPoint(x2, y2);
    }

    public Line(Color color, double width, MyPoint p1, MyPoint p2){
        super(color, width);
        myPoints.add(p1);
        myPoints.add(p2);
    }
}
