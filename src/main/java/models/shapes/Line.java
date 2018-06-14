package models.shapes;

import com.google.gson.JsonObject;
import models.viewModels.TreeNodePro;

import javax.swing.tree.TreeNode;
import java.awt.*;

public class Line extends Shape {
    protected static String type = "Line";
    public Line(double x1, double y1, double x2, double y2){
        this(new MyPoint(x1, y1), new MyPoint(x2, y2));
    }

    public Line(MyPoint p1, MyPoint p2){
        super();
        points.add(p1);
        points.add(p2);
    }

    @Override
    protected String getType() {
        return Line.type;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = super.toJson();
        json.add("x", this.points.get(0).toJson());
        json.add("y", this.points.get(1).toJson());
        return json;
    }

    public static Line parseFromJsonFactory(JsonObject json){
        if (!json.get("type").getAsString().equals(Line.type)){
            return null;
        }
        MyPoint x = MyPoint.parseFromJsonFactory(json.get("x").getAsJsonObject());
        MyPoint y = MyPoint.parseFromJsonFactory(json.get("y").getAsJsonObject());
        Line l = new Line(x, y);
        generalShapeSetter(l, json);
        return l;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d =  (Graphics2D) g.create();
        // 更改线宽
        BasicStroke bs=new BasicStroke((float) this.getWidth());
        g2d.setStroke(bs);
        g2d.setColor(this.getColor());
        double x1 = this.points.get(0).x;
        double y1 = this.points.get(0).y;
        double x2 = this.points.get(1).x;
        double y2 = this.points.get(1).y;
        g2d.drawLine((int) Math.round(x1), (int) Math.round(y1),
                   (int) Math.round(x2), (int) Math.round(y2));
        g2d.dispose();
    }
}
