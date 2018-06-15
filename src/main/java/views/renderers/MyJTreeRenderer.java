package views.renderers;

import models.shapes.Shape;
import models.viewModels.TreeNodePro;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class MyJTreeRenderer extends DefaultTreeCellRenderer {
    /**
     * 重写父类DefaultTreeCellRenderer的方法
     */
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean sel, boolean expanded, boolean leaf, int row,
                                                  boolean hasFocus){
        // 执行父类原型操作
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
                row, hasFocus);
        tree.setRootVisible(false);
        if(!(value instanceof TreeNodePro)){
            // 顶点
            return this;
        }
        // 节点
        Shape v = (Shape) ((TreeNodePro) value).getValue();
        setText(v.getName());
        if(Shape.isMainColor(v.getBackgroundColor())){
            setTextNonSelectionColor(v.getBackgroundColor());
        }else{
            setTextNonSelectionColor(v.getColor());
        }
        java.net.URL imageURL = this.getClass().getResource(v.getIcon());
        ImageIcon imgIcon = new ImageIcon(imageURL);
        int iconSize = 16;
        imgIcon.setImage(imgIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_DEFAULT));
        this.setIcon(imgIcon);
        return this;
    }
}
