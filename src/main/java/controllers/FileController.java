package controllers;

import models.shapes.*;
import models.shapes.Shape;

import java.awt.*;
import java.io.*;
import java.util.Scanner;
import java.util.Vector;

public abstract class FileController {
    public static Vector<Shape> readFormFile(File file) throws Exception {
        Scanner s = new Scanner(file);
        StringBuilder sb = new StringBuilder();
        while(true){
            sb.append(s.nextLine());
            if(!s.hasNextLine()){
                break;
            }
            sb.append('\n');
        }
        String str = sb.toString();
        s.close();
        return JsonController.parse(str);
    }

    public static void write2File(File file, Vector<Shape> shapes) throws IOException {
        Writer w = new FileWriter(file);
        String s = JsonController.toJson(shapes).toString();
        w.write(s);
        w.close();
    }

    public static void main(String[] args) throws Exception {
        Group g = new Group();
        // 两种构造方式
        g.add(new Line(0, 0, 1, 1));
        g.add(new Line(new MyPoint(4,3), new MyPoint(2,5)).setColor(Color.cyan).setWidth(2));

        // 两种构造方式
        g.add(new Circle(6,7, 5));

        g.add(new models.shapes.Polygon(new MyPoint(5,4), new MyPoint(3,2), new MyPoint(2, 1)));

        // 两种构造方式
        g.add(models.shapes.Polygon.RectangleFactory(new MyPoint(6,3), 5, 9));
        g.add(models.shapes.Polygon.RectangleFactory(4,7, 5, 8));

        System.out.println(g.toString());// 打印

        Vector<Shape> vs = new Vector<Shape>();
        vs.add(g);
        vs.add(g);

        write2File(new File("test.json"), vs);

        System.out.println("写入完成");

        Vector<Shape> ns = readFormFile(new File("test.json"));

        System.out.println("重新读取完成");

        ns.forEach(s-> System.out.println(s.toString()));

    }
}
