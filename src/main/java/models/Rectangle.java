package models;

import java.awt.*;

public class Rectangle extends Shape {

    // 左上角起始点
    public MyPoint begin;
    public double rectHeight;
    public double rectWidth;

    protected String name = "Rectangle";

    public Rectangle(Color color,int width,
                     int bx,int by, double rectHeight, double rectwidth){
        this(color,width, new MyPoint(bx, by), rectHeight, rectwidth);
    }

    public Rectangle(Color color, double width, MyPoint begin, double rectHeight, double rectwidth){
        super(color,width);
        this.begin = begin;
        this.myPoints.add(begin);
        this.rectHeight = rectHeight;
        this.rectWidth = rectwidth;
    }

    @Override
    protected String getDetail2String() {
        return "beginPoint:" + begin.toString() + "size:(" + rectHeight + ", " + rectWidth + ")";
    }
}
