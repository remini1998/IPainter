package views.utils;

import views.foundations.AlphaAnimationPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;


public class DrawToolsPanel extends AlphaAnimationPanel implements ActionListener {

    public interface SelectionListener {
        void selectedChanged(NowSelected nowSelected);
    }
    public interface DeleteBtnListener {
        void deleteBtnClicked();
    }

    private Vector<SelectionListener> selectionListeners = new Vector<SelectionListener>();
    private Vector<DeleteBtnListener> deleteBtnListeners = new Vector<DeleteBtnListener>();

    public void addSelectionListener(SelectionListener selectionListener) {
        this.selectionListeners.add(selectionListener);
    }
    public void addDeleteBtnListener(DeleteBtnListener deleteBtnListener) {
        this.deleteBtnListeners.add(deleteBtnListener);
    }

    private void emitSelectedChangedEvent(){
        for(SelectionListener listener: selectionListeners){
            listener.selectedChanged(nowSelected);
        }
    }

    private void emitDeleteBtnClickedEvent(){
        for(DeleteBtnListener listener: deleteBtnListeners){
            listener.deleteBtnClicked();
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
            emitDeleteBtnClickedEvent();
            return;
        }

        NowSelected s;
        if (source==createBtn) s = NowSelected.CREATE;
        else if (source==moveBtn) s = NowSelected.MOVE;
        else if (source==rotateBtn) s = NowSelected.ROTATE;
        else s = NowSelected.NONE;
        if (s == getSelected()) setSelected(NowSelected.NONE);
        else setSelected(s);
    }

    public enum  NowSelected{
        NONE, CREATE, MOVE, ROTATE
    }

    private NowSelected nowSelected;

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
        createBtn = toggleButtonBuilder("添加", "src/main/resources/drawTools/create.png");
        moveBtn = toggleButtonBuilder("移动", "src/main/resources/drawTools/move.png");
        rotateBtn = toggleButtonBuilder("旋转", "src/main/resources/drawTools/rotate.png");
        BlankFactory();
        deleteBtn = buttonBuilder("删除", "src/main/resources/drawTools/delete.png");
        exitBtn = buttonBuilder("退出", "src/main/resources/drawTools/exit.png");
        this.setOpaque(false);
        this.setBorder(new EmptyBorder(0, 50, 40, 0));
    }

    public void paint(Graphics g){
        super.paint(g);
    }

    public void setSelected(NowSelected selected){
        this.nowSelected = selected;
        refreshBtns();
        emitSelectedChangedEvent();
    }

    public NowSelected getSelected(){
        return nowSelected;
    }

    private void refreshBtns(){
        createBtn.setSelected(nowSelected==NowSelected.CREATE);
        moveBtn.setSelected(nowSelected==NowSelected.MOVE);
        rotateBtn.setSelected(nowSelected==NowSelected.ROTATE);
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

        ImageIcon  imgIcon = new ImageIcon(file);
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
        this.add(panel);
        return panel;
    }
}
