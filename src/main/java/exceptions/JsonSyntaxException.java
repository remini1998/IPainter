package exceptions;

public class JsonSyntaxException extends Exception {
    public JsonSyntaxException(){
        super("文件格式错误！");
    }
    public JsonSyntaxException(String msg){
        super(msg);
    }
}
