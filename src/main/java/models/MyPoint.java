package models;

import models.interfaces.IOperatable;

import java.awt.*;

import static java.lang.Math.*;

public class MyPoint extends Point implements IOperatable {
    public double x, y;
    public MyPoint(double x, double y){
        this.x = x;
        this.y = y;
    }
    public MyPoint(MyPoint p){
        this(p.x, p.y);
    }
    public void translate(double dx, double dy){
        x += dx;
        y += dy;
    }
    public void rotate(double alpha, double cx, double cy){
        // 移到原点
        this.translate(-cx, -cy);
        this.rotate(alpha);
        // 移回原位
        this.translate(cx, cy);
    }
    public void rotate(double alpha){
        double rad = alpha / 360 * Math.PI;
        x = x * cos(rad) + y * sin(rad);
        y = x * sin(rad) + y * cos(rad);
    }

    public String toString(){
        return "(" + x + ", " + y + ")";
    }

}
