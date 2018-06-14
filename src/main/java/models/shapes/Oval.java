package models.shapes;

import com.google.gson.JsonObject;

import java.awt.*;

/**
 * 椭圆
 */
public class Oval extends Shape {

    protected static String type = "Oval";

    public Oval(int bx, int by, double ovalHeight, double ovalWidth){
        this(new MyPoint(bx, by), ovalHeight, ovalWidth);
    }

    public Oval(MyPoint begin, MyPoint end){
        this(begin, end.y - begin.y, end.x - begin.x);
    }

    public Oval(MyPoint begin, double ovalHeight, double ovalWidth){
        this.points.add(begin);
        this.points.add(new MyPoint(ovalWidth + begin.x, ovalHeight + begin.y));
    }

    @Override
    protected String getType() {
        return Oval.type;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.add("start", this.points.get(0).toJson());
        json.add("end", this.points.get(1).toJson());
        return json;
    }

        public static Oval parseFromJsonFactory(JsonObject json){
            if (!json.get("type").getAsString().equals(Oval.type)){
                return null;
            }
            MyPoint start = MyPoint.parseFromJsonFactory(json.get("start").getAsJsonObject());
            MyPoint end = MyPoint.parseFromJsonFactory(json.get("end").getAsJsonObject());
            Oval o = new Oval(start, end);
            generalShapeSetter(o, json);
            return o;
        }

        @Override
        public void draw(Graphics g) {
            Graphics2D g2d =  (Graphics2D) g.create();
            double x1 = this.points.get(0).x;
            double y1 = this.points.get(0).y;
            double x2 = this.points.get(1).x - x1;
            double y2 = this.points.get(1).y - y1;
            // 如果有填充
            if(this.getBackgroundColor() != null){
                g2d.setColor(getBackgroundColor());
                g2d.fillOval((int) Math.round(x1), (int) Math.round(y1), (int) Math.round(x2), (int) Math.round(y2));
            }
            // 更改线宽
            BasicStroke bs=new BasicStroke((float) this.getWidth());
            g2d.setStroke(bs);
            g2d.setColor(this.getColor());
            g2d.drawOval((int) Math.round(x1), (int) Math.round(y1), (int) Math.round(x2), (int) Math.round(y2));
            g2d.dispose();
        }
}
