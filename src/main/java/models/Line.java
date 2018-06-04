package models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.awt.*;
import java.util.Vector;

public class Line extends Shape {
    protected static String name = "Line";
    public Line(double x1, double y1, double x2, double y2){
        this(new MyPoint(x1, y1), new MyPoint(x2, y2));
    }

    public Line(MyPoint p1, MyPoint p2){
        super();
        points.add(p1);
        points.add(p2);
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("type", Line.name);
        json.add("color", Shape.transColor2Json(this.getColor()));
        json.addProperty("width", this.getWidth());
        json.add("x", this.points.get(0).toJson());
        json.add("y", this.points.get(1).toJson());
        return json;
    }

    public static Line parseFromJsonFactory(JsonObject json){
        if (!json.get("type").getAsString().equals(Line.name)){
            return null;
        }
        MyPoint x = MyPoint.parseFromJsonFactory(json.get("x").getAsJsonObject());
        MyPoint y = MyPoint.parseFromJsonFactory(json.get("y").getAsJsonObject());
        Color color = Shape.parseJson2Color(json.get("color").getAsJsonObject());
        double width = json.get("width").getAsDouble();
        Line l = new Line(x, y);
        l.setColor(color);
        l.setWidth(width);
        return l;
    }
}
