package models.interfaces;

import com.google.gson.JsonObject;

public interface ISerialized {
    /**
     * 转换成Java对象
     * @return
     */
    JsonObject toJson();

    /**
     * 解析json为对象
     * @param json
     * @return 如果是该项处理返回生成的Shape，否则返回null
     */
    static ISerialized parseFromJsonFactory(JsonObject json){
        return null;
    }
}
