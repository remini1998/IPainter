package views.utils;

import models.shapes.Shape;
import models.viewModels.TreeNodePro;
import views.foundations.AlphaAnimationPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.util.Vector;

public class ShapesManagerPanel extends AlphaAnimationPanel {
    private Vector<Shape> shapes = new Vector<Shape>();
    private JScrollPane scrollPane;
    private JTree tree;
    private DefaultTreeModel model;
    private DefaultMutableTreeNode root;

    public ShapesManagerPanel(){
        Dimension size = new Dimension(250, 500);
        this.setPreferredSize(size);
        this.setSize(size);
        this.setMaximumSize(size);

        this.setLayout(new BorderLayout());

        root = buildTree();
        model = new DefaultTreeModel(root);
        tree = new JTree(model);

        scrollPane = new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.setOpaque(false);
        this.add("Center", scrollPane);
        scrollPane.setBorder(null);

        refreshTree();
    }

    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.getContentPane().add(new ShapesManagerPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public void paint(Graphics g){
        super.paint(g);
        setBackground(new Color(250, 250, 250,180));
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        Border borderWithTitle = BorderFactory.createTitledBorder(border, "图形列表");
        setBorder(borderWithTitle);
    }

    private void refreshTree(){
        root.removeAllChildren();
        shapes.forEach(s->root.add(s.toTreeNode()));
        model.reload();
    }

    private DefaultMutableTreeNode buildTree(){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("所有图形");
        shapes.forEach(s->root.add(s.toTreeNode()));
        return root;
    }

}
