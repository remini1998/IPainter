package controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exceptions.JsonSyntaxException;
import models.*;
import models.interfaces.ISerialized;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public abstract class JsonController {

    // 可被序列化的所有类，需要实现ISerialized
    private static Class<?>[] chain= {
            Circle.class,
            Group.class,
            Line.class,
            Polygon.class
    };

    public static ArrayList<Shape> parse(String content) throws Exception {
        ArrayList<Shape> list = new ArrayList<>();
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
            if(!cls.isAssignableFrom(ISerialized.class)){
                throw new Exception("类型设置错误");
            }else {
                 Method m = cls.getMethod("parseFromJsonFactory", JsonObject.class);
                 Object result = m.invoke(null, shape);
                 if (result != null){
                     return (Shape) result;
                 }
            }
        }
        throw new JsonSyntaxException("遇到无法识别的图形格式");
    }

    public static JsonArray toJson(Shape[] shapes){
        JsonArray json = new JsonArray();
        Vector<Shape> vs = new Vector<>(Arrays.asList(shapes));
        vs.forEach(s -> json.add(s.toJson()));
        return json;
    }

}
