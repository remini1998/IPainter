package models.interfaces;

import models.viewModels.TreeNodePro;

import javax.swing.*;
import java.awt.*;

public interface IDrawable {
    // 用于在层级中显示
    TreeNodePro toTreeNode();
    void draw(Graphics g);
}
