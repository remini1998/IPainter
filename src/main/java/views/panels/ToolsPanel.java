package views.panels;

import controllers.FileController;
import javafx.stage.FileChooser;
import models.shapes.*;
import models.shapes.Polygon;
import models.shapes.Shape;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import views.utils.DrawToolsPanel;
import views.utils.MentionPanel;
import views.utils.OptionPanel;
import views.utils.ShapeTools.AddToolsPanel;
import views.utils.ShapeTools.ShapeToolPanel;
import views.utils.ShapesManagerPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class ToolsPanel extends JPanel {

    private Vector<Shape> drawing;
    private Vector<Shape> shapes;

    private JPanel topPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    private JPanel centerLeftPanel = new JPanel();
    private JPanel centerRightPanel = new JPanel();


    private DrawToolsPanel drawToolsPanel = new DrawToolsPanel();
    private OptionPanel optionPanel = new OptionPanel();
    private ShapesManagerPanel shapesManagerPanel = new ShapesManagerPanel();
    private MentionPanel mentionPanel = new MentionPanel();

    private JFileChooser fileChooser = new JFileChooser(".");

    // 记录上次的移动，移动使用
    private Point lastMove;
    // 记录上次的旋转，旋转使用
    private double lastRotate = 0;
    //旋转率
    private double rotateRate = 1;
    // 记录初始点
    Point firstPoint = null;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.getContentPane().add(new ToolsPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public ToolsPanel(){

        this.setPreferredSize(new Dimension(1080, 720));
        this.setOpaque(false);

        this.setLayout(new BorderLayout());
        this.add("North", topPanel);
        this.add("Center", centerPanel);
        this.add("South", mentionPanel);

        topPanel.setLayout(new FlowLayout());
        topPanel.add(drawToolsPanel);
        topPanel.setOpaque(false);

        centerPanel.setLayout(new BorderLayout());
        centerPanel.add("West", centerLeftPanel);
        centerPanel.add("East", centerRightPanel);
        centerPanel.setOpaque(false);

        centerRightPanel.setLayout(new BorderLayout());
        centerRightPanel.add(shapesManagerPanel, BorderLayout.CENTER);
        centerRightPanel.setBorder(new EmptyBorder(10,20,100,20));
        centerRightPanel.setOpaque(false);

        centerLeftPanel.setLayout(new BorderLayout());
        centerLeftPanel.add(optionPanel, BorderLayout.CENTER);
        centerLeftPanel.setBorder(new EmptyBorder(10,20,100,20));
        centerLeftPanel.setOpaque(false);

        drawToolsPanel.addSelectionListener(new DrawToolsPanel.SelectionListener() {
            @Override
            public void selectedChanged(DrawToolsPanel.DrawTools nowSelected) {
                switch (nowSelected){
                    case NONE:
                        setCursor(MyCursor.POINTER);
                        break;
                    case CREATE:
                        setCursor(MyCursor.ADD);
                        break;
                    case MOVE:
                        setCursor(MyCursor.MOVE);
                        break;
                    case ROTATE:
                        setCursor(MyCursor.ROTATE);
                        break;
                    default:
                        setCursor(MyCursor.POINTER);
                }
            }
        });

        drawToolsPanel.addBtnListener(new DrawToolsPanel.BtnListener() {
            @Override
            public void btnClicked(DrawToolsPanel.DrawButtons btnClicked) {
                DrawToolCalled(btnClicked);
            }
        });

        addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }

            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                onRelease(e);
            }

            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                onClick(e);
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             * @since 1.6
             */
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                onDrag(e);
            }

            /**
             * {@inheritDoc}
             *
             * @param e
             * @since 1.6
             */
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
            }
        });

        fileChooser.setFileFilter(new FileNameExtensionFilter("图形json文件(*.sson)", "sson"));
        fileChooser.setMultiSelectionEnabled(false);

    }

    public void addDrawToolSelectedListener(DrawToolsPanel.SelectionListener listener){
        drawToolsPanel.addSelectionListener(listener);
    }

    public void addDrawBtnListener(DrawToolsPanel.BtnListener listener){
        drawToolsPanel.addBtnListener(listener);
    }

    public DrawToolsPanel.DrawTools getToolSelected(){
        return drawToolsPanel.getSelected();
    }

    public void setTool(DrawToolsPanel.DrawTools toolSelected){
        drawToolsPanel.setSelected(toolSelected);
    }

    private enum MyCursor {
        POINTER, ADD, HAND, MOVE, PENCIL, ROTATE, TEXT
    }

    private void setCursor(MyCursor cursor){
        switch (cursor){
            case POINTER:
                setCursor(Cursor.getDefaultCursor());
                break;
            case ADD:
                setCursor("/cursors/add.png");
                break;
            case HAND:
                setCursor("/cursors/hand.png");
                break;
            case MOVE:
                setCursor("/cursors/move.png");
                break;
            case PENCIL:
                setCursor("/cursors/pencil.png", new Point(5, 28));
                break;
            case ROTATE:
                setCursor("/cursors/rotate.png", new Point(19, 17));
                break;
            case TEXT:
                setCursor("/cursors/text.png");
                break;
            default:
                setCursor(Cursor.getDefaultCursor());
                break;

        }
    }

    private void setCursor(String file){
        setCursor(file, new Point(16, 16));
    }

    private void setCursor(String file, Point center){
        setCursor(file, center, "dynamite stick");
    }

    private void setCursor(String file, Point center, String name){
        Toolkit kit = Toolkit.getDefaultToolkit();
        java.net.URL imageURL = this.getClass().getResource(file);
        Image img = kit.getImage(imageURL);
        Cursor dynamiteCursor = kit.createCustomCursor(img, center,name) ;
        setCursor(dynamiteCursor);
    }

    public void refreshShapes(){
        shapesManagerPanel.refreshTree();
        mentionPanel.repaint();
    }

    public void setShapes(Vector<Shape> shapes){
        this.shapes = shapes;
        this.shapesManagerPanel.setShapes(shapes);
    }

    public void setDrawingList(Vector<Shape> drawing){
        this.drawing = drawing;
    }

    public void onClick(MouseEvent e){
        Point click = e.getPoint();
        if (drawToolsPanel.contains(click) || optionPanel.contains(click) || shapesManagerPanel.contains(click)){
            return;
        }
        mentionPanel.push(e.getPoint().toString());
        firstPoint = click;
    }

    public void onDrag(MouseEvent e){
        Point drag = e.getPoint();
        if(firstPoint != null){
            switch (drawToolsPanel.getSelected()){
                case NONE: break;
                case CREATE:
                    drawing.removeAllElements();
                    drawing.add(shapeFactory(firstPoint, drag));
                    mentionPanel.push(firstPoint.toString() + e.getPoint().toString());
                    break;
                case MOVE:

                    // 画辅助线
                    drawing.removeAllElements();
                    double x = drag.x - firstPoint.x;
                    double y = drag.y - firstPoint.y;
                    Point textPos = new Point((firstPoint.x + drag.x) / 2, (firstPoint.y + drag.y) / 2);
                    drawing.add(new Text(new MyPoint(textPos), String.format("(%.1f, %.1f)", x, y)));
                    drawing.add(new Line(new MyPoint(drag), new MyPoint(firstPoint)));

                    Point thisMove = new Point(drag.x - firstPoint.x, drag.y - firstPoint.y);
                    if(lastMove != null){
                        lastMove.x = -lastMove.x;
                        lastMove.y = -lastMove.y;
                        moveTool(lastMove);
                    }
                    moveTool(thisMove);
                    lastMove = thisMove;
                    break;

                case ROTATE:

                    // 画辅助线
                    drawing.removeAllElements();
                    double r = (drag.x - firstPoint.x) * rotateRate;
                    Point textRotatePos = new Point((firstPoint.x + drag.x) / 2, firstPoint.y);
                    drawing.add(new Text(new MyPoint(textRotatePos), String.format("%.1f°", r)));
                    drawing.add(new Line(firstPoint.x, firstPoint.y, drag.x, firstPoint.y));

                    rotateTool(r - lastRotate, firstPoint.x, firstPoint.y);
                    lastRotate = r;
                    break;
                default:
                    throw new NotImplementedException();

            }

            mentionPanel.repaint();
        }
    }

    public void onRelease(MouseEvent e){
        Point drag = e.getPoint();
        if(firstPoint != null){
            switch (drawToolsPanel.getSelected()){
                case NONE: break;
                case CREATE:
                    drawing.removeAllElements();
                    shapes.add(shapeFactory(firstPoint, drag));
                    mentionPanel.push(firstPoint.toString() + e.getPoint().toString());
                    refreshShapes();
                    break;
                case MOVE:
                    lastMove = null;
                    Vector<Shape> selected = this.shapesManagerPanel.getSelected();
                    drawing.removeAllElements();
                    break;
                case ROTATE:
                    lastRotate = 0;
                    drawing.removeAllElements();
                    break;
                default:
                    throw new NotImplementedException();

            }
            mentionPanel.repaint();
            firstPoint = null;
        }
    }

    private void moveTool(Point p){
        Vector<Shape> selected = shapesManagerPanel.getSelected();
        if(selected == null){
            shapes.forEach(s -> s.translate(p.x, p.y));
        }
        else {
            selected.forEach(s -> s.translate(p.x, p.y));
        }
    }

    private void rotateTool(double r, double cx, double cy){

        Vector<Shape> selected = shapesManagerPanel.getSelected();
        if(selected == null){
            shapes.forEach(s -> s.rotate(r, cx, cy));
        }
        else {
            selected.forEach(s -> s.rotate(r, cx, cy));
        }
    }

    private Shape shapeFactory(Point p1, Point p2){
        DrawToolsPanel.DrawTools selected = drawToolsPanel.getSelected();
        ShapeToolPanel p = optionPanel.getShapeToolComponent();
        AddToolsPanel.AddShape s = p.getAddToolsComponent().getSelectedShape();
        MyPoint mp1 = new MyPoint(p1);
        MyPoint mp2 = new MyPoint(p2);
        double width =  p.getAddToolsComponent().getLineWidth();
        Color color =  p.getAddToolsComponent().getFrontColor();
        Color bgColor =  p.getAddToolsComponent().getBackgroundColor();
        switch (s){
            case LINE: return new Line(mp1, mp2).setWidth(width).setColor(color).setBackgroundColor(bgColor);
            case RECTANGLE: return Polygon.RectangleFactory(mp1, mp2).setWidth(width).setColor(color).setBackgroundColor(bgColor);
            case CIRCLE:
                double r = mp1.getDistance(mp2);
                return new Circle(mp1, r).setDrawingInfo(mp2).setWidth(width).setColor(color).setBackgroundColor(bgColor); // 设置辅助绘画信息
            case Oval:
                return new Oval(mp1, mp2).setWidth(width).setColor(color).setBackgroundColor(bgColor);
            default:
                throw new NotImplementedException();
        }
    }

    private void DrawToolCalled(DrawToolsPanel.DrawButtons tool){
        Vector<Shape>
                selected = this.shapesManagerPanel.getSelected();;
        switch (tool){
            case DELETE:
                if(selected == null){
                    if(JOptionPane.showConfirmDialog(this,
                            "未选择任何项，确定全部清空？", "清空", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION){
                        shapes.removeAllElements();
                    }
                }
                else {
                    for(Shape s: selected){
                        Group.removeInVector(shapes, s);
                    }
                }
                refreshShapes();
                break;
            case GROUP:
                if(selected == null){
                    JOptionPane.showMessageDialog(this, "未选择元素！");
                }
                else {
                    Group g = new Group();
                    for(Shape s: selected){
                        g.add(s);
                        Group.removeInVector(shapes, s);
                    }
                    shapes.add(g);
                    refreshShapes();
                }
                break;
            case SAVE:
                save();
                break;
            case OPEN:
                open();
                break;
            default:
                throw new NotImplementedException();
        }

    }
    private boolean save(){
//                fileChooser.setDialogTitle("打开文件");
        if(fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
            String address = fileChooser.getSelectedFile().getPath();
            if (!address.endsWith(".sson")) address = address + ".sson";
            File f = new File(address);
            try{
                FileController.write2File(f, shapes);
                JOptionPane.showMessageDialog(this, "保存成功");
                return true;
            }
            catch (IOException e) {
                JOptionPane.showMessageDialog(this, "保存失败！");
                return false;
            }
        }
        return false;
    }
    private boolean open(){
        if(shapes.size() > 0){
            switch (JOptionPane.showConfirmDialog(this,
                    "当前未保存，请问是否保存？", "注意", JOptionPane.YES_NO_CANCEL_OPTION)){
                case JOptionPane.YES_OPTION:
                    save();
                    break;
                case JOptionPane.CANCEL_OPTION:
                    return false;
            }
        }
        if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            File f = fileChooser.getSelectedFile();
            try{
                this.shapes.removeAllElements();
                this.shapes.addAll(FileController.readFormFile(f));
                JOptionPane.showMessageDialog(this, "打开成功");
                refreshShapes();
                return true;
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(this, "打开失败！");
                e.printStackTrace();
                refreshShapes();
                return false;
            }
        }
        return false;

    }


}
