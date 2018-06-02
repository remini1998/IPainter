package models.interfaces;

public interface IOperatable {
    void translate(double dx, double dy);
    void rotate(double alpha, double cx, double cy);
    void rotate(double alpha);
}
