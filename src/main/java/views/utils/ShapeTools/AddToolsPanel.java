package views.utils.ShapeTools;

import views.components.ColorComponent;
import views.utils.ShapeSetting.ShapeSettingPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddToolsPanel extends ShapeSettingPanel {

    /**
     *  屏蔽非数字输入
     */
    private class OnlyNumberListener extends KeyAdapter {
        public void keyTyped(KeyEvent e) {
            int keyChar = e.getKeyChar();
            if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){

            }else{
                e.consume(); //关键，屏蔽掉非法输入
            }
        }
    }

    private JComboBox selectionBox = new JComboBox();
    private JTextField lineWidthTextField = new JTextField();

    public AddToolsPanel(){
        this.setOpaque(false);
        this.setLayout(new GridLayout(0, 2, 5,5));

        this.add(new JLabel("选择工具"));
        this.add(selectionBox);

        selectionBox.addItem("线工具");
        selectionBox.addItem("矩形工具");
        selectionBox.addItem("圆工具");

        this.add(new JLabel("线宽"));
        this.add(lineWidthTextField);
        lineWidthTextField.addKeyListener(new OnlyNumberListener());
        setLineWidth(1);

        this.add(new JLabel("颜色"));
        this.add(new ColorComponent());

    }
    public int getLineWidth(){
        return Integer.parseInt(lineWidthTextField.getText());
    }

    public void setLineWidth(int val){
        lineWidthTextField.setText(String.valueOf(val));
    }
}
