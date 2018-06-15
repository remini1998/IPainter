package models.shapes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import controllers.JsonController;
import models.viewModels.TreeNodePro;

import javax.swing.tree.TreeNode;
import java.awt.*;
import java.util.Vector;
import java.util.function.Consumer;

//组合模式
public class Group extends Shape {
    protected static String type = "Group";

    public String getIcon(){
        return "/shapes/group.png";
    }

    private Vector<Shape> shapes = new Vector<Shape>();
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Shape s: shapes){
            sb.append(s.toString());
            sb.append('\n');
        }
        return type + " {\n" + sb.toString() + "}";
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

    @Override
    protected String getType() {
        return Group.type;
    }

    public Color getColor() {
        return null;
    }

    public Shape setColor(Color color) {
        shapes.forEach(p -> p.setColor(color));
        return this;
    }

    public double getWidth() {
        return 0;
    }

    public Shape setWidth(double width) {
        shapes.forEach(p -> p.setWidth(width));
        return this;
    }

    public void add(Shape s){
        shapes.add(s);
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
        JsonObject json = super.toJson();
        json.addProperty("type", Group.type);
        json.addProperty("name", this.getName());
        JsonArray jShapes = JsonController.toJson(shapes).get("shapes").getAsJsonArray();
        json.add("shapes", jShapes);
        return json;
    }

    public static Group parseFromJsonFactory(JsonObject json){
        if (!json.get("type").getAsString().equals(Group.type)){
            return null;
        }
        Group g = new Group();
        JsonArray jShapes = json.get("shapes").getAsJsonArray();
        g.setName(json.get("name").getAsString());
        jShapes.forEach(j -> {
            try {
                g.add(JsonController.travel(j.getAsJsonObject()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return g;
    }

    @Override
    public TreeNodePro toTreeNode() {
        TreeNodePro node = new TreeNodePro(this);
        shapes.forEach(p -> node.add(p.toTreeNode()));
        return node;
    }

    @Override
    public void draw(Graphics g) {
        this.shapes.forEach(s -> s.draw(g));
    }

    @Override
    public void drawing(Graphics g) {
        this.shapes.forEach(s -> s.drawing(g));
    }

    public boolean remove(Shape s) {
        return removeInVector(this.shapes, s);
    }

    public static boolean removeInVector(Vector<Shape> ss, Shape remove){
        for(Shape s: ss){
            if(Group.class.isInstance(s)){
                if(((Group)s).remove(remove)){
                    return true;
                }
            }
        }
        return ss.remove(remove);
    }
}
