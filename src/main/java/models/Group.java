package models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import controllers.JsonController;

import java.awt.*;
import java.util.Vector;
import java.util.function.Consumer;

//组合模式
public class Group extends Shape {
    protected static String name = "Group";
    private Vector<Shape> shapes = new Vector<Shape>();
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Shape s: shapes){
            sb.append(s.toString());
            sb.append('\n');
        }
        return name + " {\n" + sb.toString() + "}";
    }
    public void translate(double dx, double dy){
        shapes.forEach(p -> p.translate(dx, dy));
    }
    public void rotate(double alpha, double cx, double cy){
        shapes.forEach(p -> p.rotate(alpha, cx, cy));
    }
    public void rotate(double alpha){
        shapes.forEach(p -> p.rotate(alpha));
    }


    public Color getColor() {
        return null;
    }

    public void setColor(Color color) {
        shapes.forEach(p -> p.setColor(color));
    }

    public double getWidth() {
        return 0;
    }

    public void setWidth(double width) {
        shapes.forEach(p -> p.setWidth(width));
    }

    public void add(Shape s){
        shapes.add(s);
    }

    public void remove(Shape s){
        shapes.remove(s);
    }

    public Shape get(int index){
        ;return shapes.get(index);
    }

    public int size(){
        ;return shapes.size();
    }

    public void forEach(Consumer<? super Shape> func){
        shapes.forEach(func);
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("type", Group.name);
        JsonArray jShapes = JsonController.toJson((Shape[]) shapes.toArray());
        json.add("shapes", jShapes);
        return json;
    }

    public static Group parseFromJsonFactory(JsonObject json){
        if (!json.get("type").getAsString().equals(Group.name)){
            return null;
        }
        Group g = new Group();
        JsonArray jShapes = json.get("shapes").getAsJsonArray();
        jShapes.forEach(j -> {
            try {
                g.add(JsonController.travel(j.getAsJsonObject()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return g;
    }
}
