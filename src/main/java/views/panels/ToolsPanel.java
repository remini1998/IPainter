package views.panels;

import views.utils.DrawToolsPanel;
import views.utils.MentionPanel;
import views.utils.OptionPanel;
import views.utils.ShapesManagerPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ToolsPanel extends JPanel {
    private JPanel topPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    private JPanel centerLeftPanel = new JPanel();
    private JPanel centerRightPanel = new JPanel();


    private DrawToolsPanel drawToolsPanel = new DrawToolsPanel();
    private OptionPanel optionPanel = new OptionPanel();
    private ShapesManagerPanel shapesManagerPanel = new ShapesManagerPanel();
    private MentionPanel mentionPanel = new MentionPanel();

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

        topPanel.setLayout(new BorderLayout());
        topPanel.add("Center", drawToolsPanel);
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
    }
}
