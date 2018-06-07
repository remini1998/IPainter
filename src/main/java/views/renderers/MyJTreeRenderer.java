package views.renderers;

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
        if(!value.getClass().isAssignableFrom(TreeNodePro.class)){
            // 顶点
            return this;
        }
        // 节点
        TreeNodePro nodePro = (TreeNodePro) value;


        return this;
    }
}
