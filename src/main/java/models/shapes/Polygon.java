package models.shapes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import models.viewModels.TreeNodePro;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.Vector;

public class Polygon extends Shape {

    protected static String type = "Polygon";

    public String getIcon(){
        return "/shapes/polygon.png";
    }


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

    public static Polygon RectangleFactory(MyPoint begin, MyPoint end){
        return RectangleFactory(begin, end.y - begin.y, end.x - begin.x);
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
        JsonObject json = super.toJson();
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
    public void draw(Graphics g) {
        Graphics2D g2d =  (Graphics2D) g.create();
        int n = points.size();
        int[] xPoints = new int[n];
        int[] yPoints = new int[n];
        for(int i = 0; i < n; i++){
            xPoints[i] = (int) Math.round(points.get(i).x);
            yPoints[i] = (int) Math.round(points.get(i).y);
        }
        // 如果有填充
        if(this.getBackgroundColor() != null){
            g2d.setColor(getBackgroundColor());
            g2d.fillPolygon(xPoints, yPoints, n);
        }
        // 更改线宽
        BasicStroke bs=new BasicStroke((float) this.getWidth());
        g2d.setStroke(bs);
        g2d.setColor(this.getColor());
        g2d.drawPolygon(xPoints, yPoints, n);
        g2d.dispose();
    }
}
