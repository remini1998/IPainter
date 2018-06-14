package views;

import models.shapes.Line;
import models.shapes.MyPoint;
import models.shapes.Shape;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import views.panels.DrawAreaPanel;
import views.panels.ToolsPanel;
import views.utils.DrawToolsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import static javax.swing.JLayeredPane.*;

public class MainWindows extends JFrame {

    private static String OS = System.getProperty("os.name").toLowerCase();
    private static String OSVer = System.getProperty("os.version").toLowerCase();

    JLayeredPane windowsPane = new JLayeredPane();
    ToolsPanel toolsPanel = new ToolsPanel();
    DrawAreaPanel drawAreaPanel = new DrawAreaPanel();
    boolean needCalcHorizShadowSize = true;
    boolean needCalcVertShadowSize = true;

    private DrawToolsPanel.DrawTools nowTools = DrawToolsPanel.DrawTools.NONE;

    private Vector<Shape> shapes = new Vector<>();
    private Vector<Shape> drawingList = new Vector<>();

    /** UIManager中UI字体相关的key */
    public static String[] DEFAULT_FONT  = new String[]{
            "Table.font"
            ,"TableHeader.font"
            ,"CheckBox.font"
            ,"Tree.font"
            ,"Viewport.font"
            ,"ProgressBar.font"
            ,"RadioButtonMenuItem.font"
            ,"ToolBar.font"
            ,"ColorChooser.font"
            ,"ToggleButton.font"
            ,"Panel.font"
            ,"TextArea.font"
            ,"Menu.font"
            ,"TableHeader.font"
            // ,"TextField.font"
            ,"OptionPane.font"
            ,"MenuBar.font"
            ,"Button.font"
            ,"Label.font"
            ,"PasswordField.font"
            ,"ScrollPane.font"
            ,"MenuItem.font"
            ,"ToolTip.font"
            ,"List.font"
            ,"EditorPane.font"
            ,"Table.font"
            ,"TabbedPane.font"
            ,"RadioButton.font"
            ,"CheckBoxMenuItem.font"
            ,"TextPane.font"
            ,"PopupMenu.font"
            ,"TitledBorder.font"
            ,"ComboBox.font"
    };

    public static void main(String[] args){
        try
        {
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
            UIManager.put("RootPane.setupButtonVisible", false);
            // 调整默认字体
            if(isWindows7()){
                for (int i = 0; i < DEFAULT_FONT.length; i++)
                    UIManager.put(DEFAULT_FONT[i],new Font("微软雅黑", Font.PLAIN,14));
            }
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
        }
        catch(Exception e)
        {
            System.out.println("加载皮肤失败");
        }

        MainWindows windows = new MainWindows();
        windows.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        windows.pack();
        windows.setVisible(true);
    }

    public MainWindows(){
        super(" IPainter: 绘手");

        this.setSize(1080, 720);
        this.add(windowsPane);
        resize();
        windowsPane.setPreferredSize(new Dimension(1080, 720));
        windowsPane.add(drawAreaPanel, DEFAULT_LAYER);
        windowsPane.add(toolsPanel, PALETTE_LAYER);

        // 改变大小时自动改变
        this.addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e){
                resize();
            }});
        // 解决最大化时不计算边缘阴影的bug
        this.addWindowStateListener(new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                super.windowStateChanged(e);
                needCalcHorizShadowSize = ((e.getNewState() & JFrame.MAXIMIZED_HORIZ) == 0);
                needCalcVertShadowSize = ((e.getNewState() & JFrame.MAXIMIZED_VERT) == 0);
            }
        });

        // 更改工具事件
        this.toolsPanel.addDrawToolSelectedListener(drawAreaPanel.new SelectionChangeEventHandler());

        this.toolsPanel.setShapes(shapes);
        this.toolsPanel.setDrawingList(drawingList);

        this.drawAreaPanel.setShapes(shapes);
        this.drawAreaPanel.setDrawingList(drawingList);

        toolsPanel.refreshShapes();
    }

    private void resize(){
        int heightHoriz = needCalcHorizShadowSize ? 50 : 0;
        int shadowSizeVert = needCalcVertShadowSize ? 50 : 0;
        int height = this.getHeight() - shadowSizeVert - 30;
        int width = this.getWidth() - heightHoriz;
        drawAreaPanel.setBounds(0, 0, width, height);
        toolsPanel.setBounds(0, 0, width, height);
    }

    public void paint(Graphics g){
        super.paint(g);
//        setBackground(Color.WHITE);
    }

    private static boolean isWindows7(){
        return OS.contains("windows") && !OSVer.startsWith("10");
    }
}
