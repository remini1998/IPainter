package views.utils.ShapeTools;

import views.components.ColorComponent;
import views.utils.ShapeSetting.ShapeSettingPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddToolsPanel extends ShapeSettingPanel {

    public enum AddShape{
        LINE, RECTANGLE, CIRCLE, Oval
    }

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
    private ColorComponent frontColorSelector = new ColorComponent(Color.black);
    private ColorComponent bgColorSelector = new ColorComponent(Color.white);

    public AddToolsPanel(){
        this.setOpaque(false);
        this.setLayout(new GridLayout(0, 2, 5,5));

        this.add(new JLabel("选择工具"));
        this.add(selectionBox);

        selectionBox.addItem("线工具");
        selectionBox.addItem("矩形工具");
        selectionBox.addItem("圆工具");
        selectionBox.addItem("椭圆工具");

        changeShapeColorSetting(AddShape.LINE);

        this.add(new JLabel("线宽"));
        this.add(lineWidthTextField);
        lineWidthTextField.setHorizontalAlignment(JTextField.CENTER);
        lineWidthTextField.addKeyListener(new OnlyNumberListener());
        setLineWidth(1);

        this.add(new JLabel("前景色"));
        this.add(frontColorSelector);

        this.add(new JLabel("背景色"));
        this.add(bgColorSelector);

        selectionBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    changeShapeColorSetting(getSelectedShape());
                }
            }
        });
    }

    private void changeShapeColorSetting(AddShape shape){
        switch (shape){
            case LINE:
                frontColorSelector.setEnable(true);
                bgColorSelector.setEnable(false);
                break;
            case CIRCLE:
                frontColorSelector.setEnable(true);
                bgColorSelector.setEnable(true);
                break;
            case RECTANGLE:
                frontColorSelector.setEnable(true);
                bgColorSelector.setEnable(true);
                break;
            case Oval:
                frontColorSelector.setEnable(true);
                bgColorSelector.setEnable(true);
                break;
        }
    }

    public AddShape getSelectedShape(){
        int s = selectionBox.getSelectedIndex();
        switch (s){
            case 0: return AddShape.LINE;
            case 1: return AddShape.RECTANGLE;
            case 2: return AddShape.CIRCLE;
            case 3: return AddShape.Oval;
        }
        return null;
    }

    public int getLineWidth(){
        return Integer.parseInt(lineWidthTextField.getText());
    }

    public void setLineWidth(int val){
        lineWidthTextField.setText(String.valueOf(val));
    }

    public Color getFrontColor(){
        return frontColorSelector.getColor();
    }

    public Color getBackgroundColor(){
        return bgColorSelector.getColor();
    }
}
