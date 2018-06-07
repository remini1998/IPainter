package views.foundations;

import javax.swing.*;
import java.awt.*;

public class AlphaPanel extends JPanel {
    private AlphaComposite cmp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1);
    private float alpha = 1;
    public void setAlpha(float alpha) {
        this.alpha = alpha;
        if (isVisible()) paintImmediately(getBounds());
    }
    public float getAlpha() {
        return alpha;
    }
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setComposite(cmp.derive(alpha));
        super.paintComponent(g2d);
    }
    public static void main(String[] args) {
        new AlphaPanel().setVisible(true);
    }
}
