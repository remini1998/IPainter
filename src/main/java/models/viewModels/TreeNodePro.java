package models.viewModels;

import models.shapes.Shape;

import javax.swing.tree.DefaultMutableTreeNode;

public class TreeNodePro extends DefaultMutableTreeNode {
    private Shape shape;
    public TreeNodePro(Shape shape){
        super(shape.getName());
        this.shape = shape;
    }
}
