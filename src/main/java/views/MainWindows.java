package views;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import views.panels.DrawAreaPanel;
import views.panels.ToolsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static javax.swing.JLayeredPane.*;

public class MainWindows extends JFrame {

    JLayeredPane windowsPane = new JLayeredPane();
    ToolsPanel toolsPanel = new ToolsPanel();
    DrawAreaPanel drawAreaPanel = new DrawAreaPanel();
    boolean needCalcHorizShadowSize = true;
    boolean needCalcVertShadowSize = true;

    public static void main(String[] args){;
        try
        {
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
            UIManager.put("RootPane.setupButtonVisible", false);
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
    }

    private void resize(){
        int shadowSize = 50;
        int heightHoriz = needCalcHorizShadowSize ? shadowSize : 0;
        int shadowSizeVert = needCalcVertShadowSize ? shadowSize : 0;
        int height = this.getHeight() - shadowSizeVert;
        int width = this.getWidth() - heightHoriz;
        drawAreaPanel.setBounds(0, 0, width, height);
        toolsPanel.setBounds(0, 0, width, height);
    }

    public void paint(Graphics g){
        super.paint(g);
//        setBackground(Color.WHITE);
    }
}
