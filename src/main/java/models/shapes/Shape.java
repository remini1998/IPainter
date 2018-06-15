package models.shapes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import controllers.Controller;
import models.interfaces.IOperatable;
import models.interfaces.ISerialized;
import models.interfaces.IDrawable;
import models.viewModels.TreeNodePro;

import java.awt.*;
import java.util.Collections;
import java.util.Vector;

public abstract class Shape implements IOperatable, ISerialized, IDrawable {

    private String name;

    private Color color;
    private Color bgColor;

    public String getIcon(){
        return "/shapes/shape.png";
    }

    // 小于等于0为填充模式
    private double width;
    // 视图控制器，组合模式
    public Controller controller;

    protected Object drawingInfo;

    public Shape setDrawingInfo(Object info){
        drawingInfo = info;
        return this;
    }

    // 点集合
    protected Vector<MyPoint> points = new Vector<MyPoint>();


    public String getName() {
        return name;
    }

    public Shape setName(String name) {
        this.name = name;
        return this;
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

    public Color getBackgroundColor() {
        return bgColor;
    }

    public Shape setBackgroundColor(Color color) {
        this.bgColor = color;
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
        if(color != null){
            json.addProperty("r", color.getRed());
            json.addProperty("g", color.getGreen());
            json.addProperty("b", color.getBlue());
            json.addProperty("a", color.getAlpha());
        }
        else{
            json.addProperty("r", 0);
            json.addProperty("g", 0);
            json.addProperty("b", 0);
            json.addProperty("a", 0);
        }
        return json;
    }
    public static Color parseJson2Color(JsonObject json){
        if (!json.get("type").getAsString().equals("color")){
            return null;
        }
        int r = json.get("r").getAsInt();
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

    public TreeNodePro toTreeNode() {
        TreeNodePro node = new TreeNodePro(this);
        return node;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("type", getType());
        json.addProperty("name", getName());
        json.add("color", Shape.transColor2Json(this.getColor()));
        json.add("bg-color", Shape.transColor2Json(this.getBackgroundColor()));
        json.add("points", Points2JsonArray());
        json.addProperty("width", this.getWidth());
        return json;
    }

    protected static Shape generalShapeSetter(Shape shape, JsonObject json){

        String name = json.get("name").getAsString();
        Color color = Shape.parseJson2Color(json.get("color").getAsJsonObject());
        Color bgColor = Shape.parseJson2Color(json.get("bg-color").getAsJsonObject());
        double width = json.get("width").getAsDouble();

        shape.setName(name);
        shape.setColor(color);
        shape.setBackgroundColor(bgColor);
        shape.setWidth(width);

        return shape;

    }


    public void drawing(Graphics g){
        draw(g);
    }

    public static boolean isMainColor(Color color){
        return color != null && color.getBlue() + color.getGreen() + color.getBlue() < 550 && color.getAlpha() > 150;
    }
}
