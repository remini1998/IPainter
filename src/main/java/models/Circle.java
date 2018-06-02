package models;

import java.awt.*;

public class Circle extends Shape {
    public double r;
    public MyPoint center;

    protected String name = "Circle";

    public Circle(Color color, double width, double cx, double cy,
                  double r){
        this(color,width, new MyPoint(cx, cy), r);
    }

    public Circle(Color color, double width, MyPoint center, double r){
        super(color,width);
        this.r = r;
        this.center = center;
        this.myPoints.add(center);
    }

    @Override
    protected String getDetail2String() {
        return "center:" + center.toString() + "r:" + r;
    }
}
