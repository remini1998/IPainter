package controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exceptions.JsonSyntaxException;
import models.shapes.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Vector;

public abstract class JsonController {

    // 可被序列化的所有类，需要实现ISerialized
    private static Class<?>[] chain= {
            Circle.class,
            Group.class,
            Line.class,
            Polygon.class,
            Oval.class,
            Text.class
    };

    public static Vector<Shape> parse(String content) throws Exception {
        Vector<Shape> list = new Vector<>();
        JsonParser parser = new JsonParser();
        JsonObject js = parser.parse(content).getAsJsonObject();
        // 检查版本
        String ver = js.get("ver").getAsString();
        if(!ver.equals("1.0.0")){
            throw new JsonSyntaxException("文件版本无法识别！");
        }
        JsonArray shapes = js.get("shapes").getAsJsonArray();
        for(JsonElement element: shapes){
            JsonObject shape = element.getAsJsonObject();
            Shape s = travel(shape);
            list.add(s);
        }
        return list;
    }

    public static Shape travel(JsonObject shape) throws Exception {
        for(Class<?> cls: chain){
            Method m = cls.getMethod("parseFromJsonFactory", JsonObject.class);
            Object result = m.invoke(null, shape);
            if (result != null){
                return (Shape) result;
            }
        }
        throw new JsonSyntaxException("遇到无法识别的图形格式");
    }

    public static JsonObject toJson(Shape[] shapes){
        Vector<Shape> vs = new Vector<>(Arrays.asList(shapes));
        return toJson(vs);
    }
    public static JsonObject toJson(Vector<Shape> shapes){
        JsonObject json = new JsonObject();
        JsonArray jShapes = new JsonArray();
        shapes.forEach(s -> jShapes.add(s.toJson()));
        json.addProperty("ver", "1.0.0");
        json.add("shapes", jShapes);
        return json;
    }

}
