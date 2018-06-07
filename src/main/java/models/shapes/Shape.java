package models.shapes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import controllers.Controller;
import models.interfaces.IOperatable;
import models.interfaces.ISerialized;
import models.interfaces.IDrawable;

import java.awt.*;
import java.util.Collections;
import java.util.Vector;

public abstract class Shape implements IOperatable, ISerialized, IDrawable {

    private String name;

    private Color color;
    // 小于等于0为填充模式
    private double width;
    // 视图控制器，组合模式
    public Controller controller;
    // 点集合
    protected Vector<MyPoint> points = new Vector<MyPoint>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 模板模式
    protected String getDetail2String(){
        //建造者模式
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for(MyPoint p: points)
            sb.append("[").append(count++).append("]").append(p.toString()).append(";");
        return sb.toString();
    }


    // toString时显示的名字
    protected abstract String getType();

    public String toString(){
        return this.getType() + " (color:"+ color.toString() + ", width:" + width + ", " + getDetail2String() + ")";
    }
    public void translate(double dx, double dy){
        points.forEach(p -> p.translate(dx, dy));
    }
    public void rotate(double alpha, double cx, double cy){
        points.forEach(p -> p.rotate(alpha, cx, cy));
    }
    public void rotate(double alpha){
        points.forEach(p -> p.rotate(alpha));
    }
    protected Shape(MyPoint[] points){
        this();
        Collections.addAll(this.points, points);
    }
    protected Shape(MyPoint point){
        this(new MyPoint[]{ point });
    }
    protected Shape(Vector<MyPoint> points){
        this();
        this.points = points;
    }
    protected Shape(){
        this.name = getType();
        this.color = Color.BLACK;
        this.width = 1;
    }

    public Color getColor() {
        return color;
    }

    public Shape setColor(Color color) {
        this.color = color;
        return this;
    }

    public double getWidth() {
        return width;
    }

    public Shape setWidth(double width) {
        this.width = width;
        return this;
    }

    public static JsonObject transColor2Json(Color color){
        JsonObject json = new JsonObject();
        json.addProperty("type", "color");
        json.addProperty("radius", color.getRed());
        json.addProperty("g", color.getGreen());
        json.addProperty("b", color.getBlue());
        json.addProperty("a", color.getAlpha());
        return json;
    }
    public static Color parseJson2Color(JsonObject json){
        if (!json.get("type").getAsString().equals("color")){
            return null;
        }
        int r = json.get("radius").getAsInt();
        int g = json.get("g").getAsInt();
        int b = json.get("b").getAsInt();
        int a = json.get("a").getAsInt();
        return new Color(r, g, b, a);
    }

    protected JsonArray Points2JsonArray(){
        JsonArray arr = new JsonArray();
        for (MyPoint point: points){
            arr.add(point.toJson());
        }
        return arr;
    }
}
