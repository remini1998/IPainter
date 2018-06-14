package views.panels;

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
import java.awt.*;
import java.awt.dnd.MouseDragGestureRecognizer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
                        moveAll(lastMove);
                    }
                    moveAll(thisMove);
                    lastMove = thisMove;
                    break;

                case ROTATE:

                    // 画辅助线
                    drawing.removeAllElements();
                    double r = (drag.x - firstPoint.x) * rotateRate;
                    Point textRotatePos = new Point((firstPoint.x + drag.x) / 2, firstPoint.y);
                    drawing.add(new Text(new MyPoint(textRotatePos), String.format("%.1f°", r)));
                    drawing.add(new Line(firstPoint.x, firstPoint.y, drag.x, firstPoint.y));

                    rotateAll(r - lastRotate, firstPoint.x, firstPoint.y);
                    lastRotate = r;
                    break;
                default:
                    throw new NotImplementedException();

            }
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
                    drawing.removeAllElements();
                    break;
                case ROTATE:
                    lastRotate = 0;
                    drawing.removeAllElements();
                default:
                    throw new NotImplementedException();

            }
            firstPoint = null;
        }
    }

    private void moveAll(Point p){
        shapes.forEach(s -> s.translate(p.x, p.y));
    }

    private void rotateAll(double r, double cx, double cy){
        shapes.forEach(s -> s.rotate(r, cx, cy));
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
        }
        return null;
    }
}
