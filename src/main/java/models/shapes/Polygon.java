package models.shapes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import models.viewModels.TreeNodePro;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.Vector;

public class Polygon extends Shape {

    protected static String type = "Polygon";

    @Override
    protected String getType() {
        return Polygon.type;
    }

    public Polygon(MyPoint...points){
        super(points);
    }
    public Polygon(Vector<MyPoint> points){
        super(points);
    }

    public static Polygon RectangleFactory(int bx, int by, double rectHeight, double rectWidth){
        return RectangleFactory(new MyPoint(bx, by), rectHeight, rectWidth);
    }

    public static Polygon RectangleFactory(MyPoint begin, double rectHeight, double rectWidth){
        MyPoint[] points = new MyPoint[4];
        points[0] = begin;
        points[1] = new MyPoint(begin.x + rectWidth, begin.y);
        points[2] = new MyPoint(begin.x + rectWidth, begin.y + rectHeight);
        points[3] = new MyPoint(begin.x, begin.y + rectHeight);
        return new Polygon(points);
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("type", Polygon.type);
        json.add("color", Shape.transColor2Json(this.getColor()));
        json.addProperty("width", this.getWidth());
        json.add("points", this.Points2JsonArray());
        return json;
    }

    public static Polygon parseFromJsonFactory(JsonObject json){
        if (!json.get("type").getAsString().equals(Polygon.type)){
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

    @Override
    public TreeNodePro toTreeNode() {
        TreeNodePro node = new TreeNodePro(this);
        return node;
    }

    @Override
    public void draw(Graphics g) {

    }
}
