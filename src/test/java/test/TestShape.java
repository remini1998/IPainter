package test;

import models.*;
import models.Polygon;

import java.awt.*;

public class TestShape {
    public static void main(String[] args) {
        Group g = new Group();
        // 两种构造方式
        g.add(new Line(Color.BLACK, 1, 0, 0, 1, 1));
        g.add(new Line(Color.BLACK, 1, new MyPoint(4,3), new MyPoint(2,5)));

        // 两种构造方式
        g.add(new Circle(Color.BLUE, 1, new MyPoint(4,3), 5));
        g.add(new Circle(Color.BLUE, 1, 6,7, 5));

        // 两种构造方式
        g.add(new Polygon(Color.BLUE, 1, new MyPoint(6,3), 5, 9));
        g.add(new Polygon(Color.BLUE, 1, 4,7, 5, 8));

        System.out.println(g.toString());// 打印

        System.out.println("进行平移100，100");
        g.translate(100, 100);

        System.out.println(g.toString());// 打印

        System.out.println("进行旋转45度");
        g.rotate(45);

        System.out.println(g.toString());// 打印
    }
}