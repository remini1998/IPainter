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
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class ShapesManagerPanel extends AlphaAnimationPanel {
    private Vector<Shape> shapes = new Vector<Shape>();
    private JScrollPane scrollPane;
    private JTree tree;
    private DefaultTreeModel model;
    private DefaultMutableTreeNode root;

    public ShapesManagerPanel(){
        // 鼠标指针重置
        setCursor(Cursor.getDefaultCursor());

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

        JButton clearSelection = new JButton("清除选择");
        this.add("South", clearSelection);
        clearSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tree.clearSelection();
            }
        });

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
        setBackground(new Color(255, 255, 255,255));
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        Border borderWithTitle = BorderFactory.createTitledBorder(border, "图形列表");
        setBorder(borderWithTitle);
    }

    public void refreshTree(){
        root.removeAllChildren();
        shapes.forEach(s->root.add(s.toTreeNode()));
        model.reload();
    }

    private DefaultMutableTreeNode buildTree(){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("所有图形");
        shapes.forEach(s->root.add(s.toTreeNode()));
        return root;
    }

    public void setShapes(Vector<Shape> shapes){
        this.shapes = shapes;
    }

    /**
     * 返回选中的集合
     * @return 如果没有返回null
     */
    public Vector<Shape> getSelected(){
        Vector<Shape> selected = new Vector<>();

        TreePath[] treePaths = tree.getSelectionModel().getSelectionPaths();
        for (TreePath treePath : treePaths) {
            DefaultMutableTreeNode selectedElement = (DefaultMutableTreeNode)treePath.getLastPathComponent();
            try{
                Shape userObject = (Shape) selectedElement.getUserObject();//Do what you want with selected element's user object
                selected.add(userObject);
            }
            catch (Exception e){  }
        }

        if (selected.size() == 0){
            return null;
        }else {
            return selected;
        }
    }

}
