package views.utils;

import javax.swing.*;
import java.awt.*;

public class MentionPanel extends JPanel {
    private JLabel label;
    private String msg = "";
    public MentionPanel(){
        this.label = new JLabel();
        this.setOpaque(false);
        this.setLayout(new BorderLayout());
        this.add(label);
    }
    public void push(String msg){
        this.msg = msg;
        label.setText(msg);
    }

}
