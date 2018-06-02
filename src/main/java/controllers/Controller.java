package controllers;

import models.Shape;

public abstract class Controller {
    Shape shape;
    public abstract void translate(double dx, double dy);
    public abstract void rotate(double alpha, double cx, double cy);
    public abstract void rotate(double alpha);
    //  抽象工厂模式
    public abstract Controller ControllerFactory(Shape shape);
    protected Controller(){

    }
}
