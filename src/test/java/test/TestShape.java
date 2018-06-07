package test;

import models.shapes.*;
import models.shapes.Polygon;

import java.awt.*;

public class TestShape {
    public static void main(String[] args) {
        Group g = new Group();
        // 两种构造方式
        g.add(new Line(0, 0, 1, 1));
        g.add(new Line(new MyPoint(4,3), new MyPoint(2,5)).setColor(Color.cyan).setWidth(2));

        // 两种构造方式
        g.add(new Circle(new MyPoint(4,3), 5));
        g.add(new Circle(6,7, 5));

        g.add(new Polygon(new MyPoint(5,4), new MyPoint(3,2), new MyPoint(2, 1)));

        // 两种构造方式
        g.add(Polygon.RectangleFactory(new MyPoint(6,3), 5, 9));
        g.add(Polygon.RectangleFactory(4,7, 5, 8));

        System.out.println(g.toString());// 打印

        System.out.println("进行平移100，100");
        g.translate(100, 100);

        System.out.println(g.toString());// 打印

        System.out.println("进行旋转45度");
        g.rotate(45);

        System.out.println(g.toString());// 打印
    }
}