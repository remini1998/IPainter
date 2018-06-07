package views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindowsDemo {

    private JPanel layerspPanel;
    private JPanel drawPanel;
    private JPanel toolsPanel;
    private JPanel topToolsPanel;
    private JPanel drawToolsPanel;
    private JButton exitBtn;
    private JButton newBtn;
    private JButton moveBtn;
    private JButton rotateBtn;
    private JButton deleteBtn;
    private JPanel noticePanel;
    private JPanel shapePanel;
    private JPanel optionPanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainWindowsDemo");
        frame.setContentPane(new MainWindowsDemo().layerspPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    public MainWindowsDemo() {
        exitBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
