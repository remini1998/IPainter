package controllers;

import models.shapes.Shape;

public class ConsoleController extends Controller {

    @Override
    public void translate(double dx, double dy) {

    }

    @Override
    public void rotate(double alpha, double cx, double cy) {

    }

    @Override
    public void rotate(double alpha) {

    }

    @Override
    public ConsoleController ControllerFactory(Shape shape) {
        ConsoleController c = new ConsoleController();
        this.shape = shape;
        return c;
    }
}
