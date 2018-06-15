package views.components;

import models.shapes.Shape;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ColorComponent extends JTextPane {

    private ColorComponent that = this;

    private Color color;

    private boolean enable = true;

    public void setColor(Color color){
        setEnable(true);
        this.color = color;
        setBackground(color);
        String text = getColorText();
//        this.setSelectionColor(color);
        // 设置字体颜色
        if(Shape.isMainColor(color)){
            this.setText(text, Color.WHITE);
        }else {
            this.setText(text, Color.BLACK);
        }
    }

    private String to2Hex(int num){
        StringBuilder result = new StringBuilder(Integer.toHexString(num));
        while (result.length() < 2){
            result.insert(0, "0");
        }
        return result.toString();
    }

    private String getColorText(){
        if(enable && color != null)
            return  "#"+(to2Hex(color.getRed()) + to2Hex(color.getGreen()) + to2Hex(color.getBlue())).toUpperCase();
        else
            return "";
    }

    private void setTextPaneAlign(){
        StyledDocument doc = this.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
    }

    private void setText(String msg, Color c)
    {
//        StyleContext sc = StyleContext.getDefaultStyleContext();
//        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
//
//        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
//
//        this.setCharacterAttributes(aset, false);
        this.setText(msg);

        this.setForeground(c);
    }

    public Color getColor(){
        return color;
    }

    public Color callColorChooser(){
        Color newColor = JColorChooser.showDialog(this, "请选择颜色", this.color);
        if (newColor == null) newColor = this.color;
        setColor(newColor);
        return newColor;
    }

    public ColorComponent(Color initialColor){
        setColor(initialColor);
        this.setEditable(false);
        this.setBorder(new LineBorder(Color.BLACK, 1, true));
        setTextPaneAlign();
        this.setToolTipText("点击改变颜色");
        this.setHighlighter(null);
        this.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(enable)
                    callColorChooser();
                e.consume();
            }
        });
    }

    public void paint(Graphics g){
        super.paint(g);
        setText(getColorText());
        if(enable){
            setBackground(color);

        }
        else {
            setBackground(Color.white);
            g.setColor(Color.red);
            g.drawLine(0, 0, getWidth(), getHeight());
        }
    }

    public void setEnable(boolean enable){
        this.enable = enable;
        repaint();
    }

    public ColorComponent(){
        this(Color.black);
    }

}
