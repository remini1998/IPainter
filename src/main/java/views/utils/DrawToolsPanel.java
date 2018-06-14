package views.utils;

import views.foundations.AlphaAnimationPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;


public class DrawToolsPanel extends AlphaAnimationPanel implements ActionListener {

    public enum DrawButtons {
        DELETE
    }

    public enum DrawTools {
        NONE, CREATE, MOVE, ROTATE
    }

    public interface SelectionListener {
        void selectedChanged(DrawTools nowSelected);
    }
    public interface BtnListener {
        void btnClicked(DrawButtons btnClicked);
    }

    private Vector<SelectionListener> selectionListeners = new Vector<SelectionListener>();
    private Vector<BtnListener> btnListeners = new Vector<BtnListener>();

    public void addSelectionListener(SelectionListener selectionListener) {
        this.selectionListeners.add(selectionListener);
    }
    public void addBtnListener(BtnListener BtnListener) {
        this.btnListeners.add(BtnListener);
    }

    private void emitSelectedChangedEvent(){
        for(SelectionListener listener: selectionListeners){
            listener.selectedChanged(nowSelected);
        }
    }

    private void emitBtnClickedEvent(DrawButtons btnClicked){
        for(BtnListener listener: btnListeners){
            listener.btnClicked(btnClicked);
        }
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source==exitBtn){
            System.exit(0);
            return;
        }
        else if(source==deleteBtn){
            emitBtnClickedEvent(DrawButtons.DELETE);
            return;
        }

        DrawTools s;
        if (source==createBtn) s = DrawTools.CREATE;
        else if (source==moveBtn) s = DrawTools.MOVE;
        else if (source==rotateBtn) s = DrawTools.ROTATE;
        else s = DrawTools.NONE;
        if (s == getSelected()) setSelected(DrawTools.NONE);
        else setSelected(s);
    }

    private DrawTools nowSelected;

    private JToggleButton createBtn;
    private JToggleButton moveBtn;
    private JToggleButton rotateBtn;
    private JButton deleteBtn;
    private JButton exitBtn;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.getContentPane().add(new DrawToolsPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public DrawToolsPanel(){
        // 鼠标指针样式重排
        setCursor(Cursor.getDefaultCursor());

        createBtn = toggleButtonBuilder("添加", "/drawTools/create.png");
        moveBtn = toggleButtonBuilder("移动", "/drawTools/move.png");
        rotateBtn = toggleButtonBuilder("旋转", "/drawTools/rotate.png");
        BlankFactory();
        deleteBtn = buttonBuilder("删除", "/drawTools/delete.png");
        exitBtn = buttonBuilder("退出", "/drawTools/exit.png");

        this.setOpaque(false);
        this.setBorder(new EmptyBorder(0, 0, 0, 0));

        setSelected(DrawTools.NONE);
    }

    public void paint(Graphics g){
        super.paint(g);
    }

    public void setSelected(DrawTools selected){
        this.nowSelected = selected;
        refreshBtns();
        emitSelectedChangedEvent();
    }

    public DrawTools getSelected(){
        return nowSelected;
    }

    private void refreshBtns(){
        createBtn.setSelected(nowSelected==DrawTools.CREATE);
        moveBtn.setSelected(nowSelected==DrawTools.MOVE);
        rotateBtn.setSelected(nowSelected==DrawTools.ROTATE);
    }

    private JButton buttonBuilder(String name, String file){
        JButton btn = new JButton();
        return (JButton) buttonSetter(btn, name, file);
    }

    private JToggleButton toggleButtonBuilder(String name, String file){
        JToggleButton btn = new JToggleButton();
        return (JToggleButton) buttonSetter(btn, name, file);
    }

    private JComponent buttonSetter(AbstractButton btn, String name, String file){
        Dimension size = new Dimension(50, 50);
        int logoSize = 20;

        java.net.URL imageURL = this.getClass().getResource(file);
        ImageIcon  imgIcon = new ImageIcon(imageURL);
        imgIcon.setImage(imgIcon.getImage().getScaledInstance(logoSize, logoSize, Image.SCALE_DEFAULT));
        btn.setIcon(imgIcon);
        btn.setSize(size);
        btn.setPreferredSize(size);
        btn.setToolTipText(name);
        this.add(btn);
        btn.addActionListener(this);
        return btn;
    }


    private JComponent BlankFactory(){
        Dimension size = new Dimension(50, 50);
        JPanel panel = new JPanel();
        panel.setSize(size);
        panel.setMaximumSize(size);
        panel.setMinimumSize(size);
        panel.setOpaque(false);
        this.add(panel);
        return panel;
    }
}
