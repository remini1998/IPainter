package models.shapes;

import com.google.gson.JsonObject;

import java.awt.*;

public class Text extends Shape {
    protected static String type = "Text";

    public String getText() {
        return text;
    }

    public Text setText(String text) {
        this.text = text;
        return this;
    }

    private String text = "";

    public Text(double x, double y, String text){
        this(new MyPoint(x, y), text);
    }

    public Text(MyPoint posi, String text){
        super();
        points.add(posi);
        this.text = text;
    }

    @Override
    protected String getType() {
        return Text.type;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = super.toJson();
        json.add("position", this.points.get(0).toJson());
        json.addProperty("text", this.text);
        return json;
    }

    public static Text parseFromJsonFactory(JsonObject json){
        if (!json.get("type").getAsString().equals(Text.type)){
            return null;
        }
        MyPoint x = MyPoint.parseFromJsonFactory(json.get("position").getAsJsonObject());
        String text = json.get("text").getAsString();
        Text t = new Text(x, text);
        generalShapeSetter(t, json);
        return t;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d =  (Graphics2D) g.create();
        g2d.setColor(this.getColor());
        double x = this.points.get(0).x;
        double y = this.points.get(0).y;
        g2d.drawString(text, (int) Math.round(x), (int) Math.round(y));
        g2d.dispose();
    }
}
