package models.shapes;

import com.google.gson.JsonObject;
import models.viewModels.TreeNodePro;

import javax.swing.tree.TreeNode;
import java.awt.*;

public class Circle extends Shape {

    public double radius;

    protected static String type = "Circle";

    public MyPoint getCenter() {
        return center;
    }

    public void setCenter(MyPoint center) {
        this.center = center;
        if(this.points.size() > 0){
            this.points.set(0, center);
        }else {
            this.points.add(center);
        }
    }

    private MyPoint center;

    public Circle(double cx, double cy, double radius){
        this(new MyPoint(cx, cy), radius);
    }

    public Circle(MyPoint center, double radius){
        super(center);
        this.radius = radius;
        this.center = center;
    }

    @Override
    protected String getType() {
        return Circle.type;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("type", Circle.type);
        json.add("color", Shape.transColor2Json(this.getColor()));
        json.add("center", this.center.toJson());
        json.addProperty("radius", this.radius);
        json.addProperty("width", this.getWidth());
        return json;
    }

    public static Circle parseFromJsonFactory(JsonObject json){
        if (!json.get("type").getAsString().equals(Circle.type)){
            return null;
        }
        MyPoint center = MyPoint.parseFromJsonFactory(json.get("center").getAsJsonObject());
        Color color = Shape.parseJson2Color(json.get("color").getAsJsonObject());
        double radius = json.get("radius").getAsDouble();
        double width = json.get("width").getAsDouble();
        Circle c = new Circle(center, radius);
        c.setColor(color);
        c.setWidth(width);
        return c;
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
