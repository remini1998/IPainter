package models.shapes;

import com.google.gson.JsonObject;
import models.viewModels.TreeNodePro;

import javax.swing.tree.TreeNode;
import java.awt.*;

public class Circle extends Shape {

    public double radius;

    protected static String type = "Circle";

    public String getIcon(){
        return "/shapes/circle.png";
    }

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
        JsonObject json = super.toJson();
        json.add("center", this.center.toJson());
        json.addProperty("radius", this.radius);
        return json;
    }

    public static Circle parseFromJsonFactory(JsonObject json){
        if (!json.get("type").getAsString().equals(Circle.type)){
            return null;
        }
        MyPoint center = MyPoint.parseFromJsonFactory(json.get("center").getAsJsonObject());
        double radius = json.get("radius").getAsDouble();
        Circle c = new Circle(center, radius);
        generalShapeSetter(c, json);
        return c;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d =  (Graphics2D) g.create();
        double cx = this.points.get(0).x;
        double cy = this.points.get(0).y;
        int d = (int) Math.round(radius * 2);
        // 如果有画背景
        if(this.getBackgroundColor() != null){
            g2d.setColor(getBackgroundColor());
            g2d.fillOval((int) Math.round(cx - radius), (int) Math.round(cy - radius), d, d);
        }
        // 更改线宽
        BasicStroke bs=new BasicStroke((float) this.getWidth());
        g2d.setStroke(bs);
        g2d.setColor(this.getColor());
        g2d.drawOval((int) Math.round(cx - radius), (int) Math.round(cy - radius), d, d);
        g2d.dispose();
    }

    @Override
    public void drawing(Graphics g) {
        super.drawing(g);
        try {
            Point center = this.center.toPoint();
            Point mouse = ((MyPoint) drawingInfo).toPoint();
            g.drawLine(center.x, center.y, mouse.x, mouse.y);
            double distance = new MyPoint(center).getDistance(new MyPoint(mouse));
            Point textPos = new Point((center.x + mouse.x) / 2, (center.y + mouse.y) / 2);
            g.drawString(String.format("%.1f", distance), textPos.x, textPos.y);
        }
        catch (Exception e){

        }
    }
}
