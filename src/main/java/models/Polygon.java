package models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Vector;

public class Polygon extends Shape {

    protected static String name = "Polygon";

    public Polygon(MyPoint[] points){
        super(points);
    }
    public Polygon(Vector<MyPoint> points){
        this((MyPoint[]) points.toArray());
    }

    public static Polygon RectangleFactory(int bx, int by, double rectHeight, double rectWidth){
        return RectangleFactory(new MyPoint(bx, by), rectHeight, rectWidth);
    }

    public static Polygon RectangleFactory(MyPoint begin, double rectHeight, double rectWidth){
        Vector<MyPoint> points = new Vector<>();
        points.add(begin);
        points.add(new MyPoint(begin.x + rectWidth, begin.y));
        points.add(new MyPoint(begin.x + rectWidth, begin.y + rectHeight));
        points.add(new MyPoint(begin.x, begin.y + rectHeight));
        return new Polygon((MyPoint[]) points.toArray());
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("type", Polygon.name);
        json.add("color", Shape.transColor2Json(this.getColor()));
        json.addProperty("width", this.getWidth());
        json.add("points", this.Points2JsonArray());
        return json;
    }

    public static Polygon parseFromJsonFactory(JsonObject json){
        if (!json.get("type").getAsString().equals(Polygon.name)){
            return null;
        }
        Vector<MyPoint> ps = new Vector<>();
        JsonArray jPoints = json.get("points").getAsJsonArray();
        jPoints.forEach(p -> ps.add(MyPoint.parseFromJsonFactory(p.getAsJsonObject())));
        Polygon p = new Polygon(ps);
        Color color = Shape.parseJson2Color(json.get("color").getAsJsonObject());
        double width = json.get("width").getAsDouble();
        p.setColor(color);
        p.setWidth(width);
        return p;
    }
}
