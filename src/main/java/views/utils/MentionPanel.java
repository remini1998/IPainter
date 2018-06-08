package views.utils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MentionPanel extends JPanel {
    private JLabel label;
    private String msg = "";
    public MentionPanel(){
        this.label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setFont(new Font("微软雅黑",Font.BOLD, 16));
        this.setOpaque(false);
        Dimension size = new Dimension(800, 50);
        this.setPreferredSize(size);
        this.setLayout(new BorderLayout());
        this.add("Center", label);
        push(msg);
    }

    public static void main(String[] args){
        JFrame frame = new JFrame();
        MentionPanel panel = new MentionPanel();
        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        panel.push("测试文本111");
    }
    public void push(String msg){
        this.msg = msg;
        label.setText(msg);
    }

    public void clear() {
        this.msg = "";
        label.setText(msg);
    }
}
