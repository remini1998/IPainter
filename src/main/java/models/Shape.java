package models;

import controllers.Controller;
import models.interfaces.IOperatable;

import java.awt.*;
import java.util.Collections;
import java.util.Vector;

public abstract class Shape implements IOperatable {

    private Color color;
    // 小于等于0为填充模式
    private double width;
    // 视图控制器，组合模式
    public Controller controller;
    // toString时显示的名字
    protected String name = "Shape";
    // 点集合
    protected Vector<MyPoint> myPoints = new Vector<MyPoint>();

    // 模板模式
    protected String getDetail2String(){
        //建造者模式
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for(MyPoint p: myPoints)
            sb.append("[" + (count++) + "]" + p.toString() + ";");
        return sb.toString();
    }

    public String toString(){
        return name + " (color:"+ color.toString() + ", width:" + width + ", " + getDetail2String() + ")";
    }
    public void translate(double dx, double dy){
        myPoints.forEach(p -> p.translate(dx, dy));
    }
    public void rotate(double alpha, double cx, double cy){
        myPoints.forEach(p -> p.rotate(alpha, cx, cy));
    }
    public void rotate(double alpha){
        myPoints.forEach(p -> p.rotate(alpha));
    }
    protected Shape(){

    }
    public Shape(Color color, double width){
        this.color = color;
        this.width = width;
    }
    public Shape(Color color, double width, MyPoint[] myPoints){
        this(color, width);
        Collections.addAll(this.myPoints, myPoints);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }
}
