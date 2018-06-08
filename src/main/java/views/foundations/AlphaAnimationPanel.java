package views.foundations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class AlphaAnimationPanel extends AlphaPanel {
    private boolean animationSwitch = false;
    private float alphaTarget = 0.4f;
    private float step = 0.2f;
    private int delay = 10;
    private Timer timer;
    public boolean enableMouseInOutAnimation = true;
    public float mouseEnterOpacity = 1f;
    public float mouseExitOpacity = 0.4f;


    // 以下为动画完成监听器相关
    public interface AnimationFinishedListener {
        void animationFinished();
    }

    private Vector<AnimationFinishedListener> animationFinishedListeners = new Vector<AnimationFinishedListener>();

    public void addAnimationFinishedListener(AnimationFinishedListener animationFinishedListener) {
        this.animationFinishedListeners.add(animationFinishedListener);
    }

    private void emitSelectedChangedEvent(){
        for(AnimationFinishedListener listener: animationFinishedListeners){
            listener.animationFinished();
        }
    }

    public AlphaAnimationPanel(){
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 设置目标
                Point m = getMousePosition(true);
                if (m != null && containsPoint(m))
                    alphaTarget = mouseEnterOpacity;
                else
                    alphaTarget = mouseExitOpacity;

                // 动画
                float alphaNow = getAlpha();
                if (Math.abs(alphaNow - alphaTarget) <= step){
                    alphaNow = alphaTarget;
                }
                else
                if (alphaNow > alphaTarget)
                    alphaNow -= step;
                else
                    alphaNow += step;
                setAlpha(alphaNow);
            }
        });
        startAnimation();
    }
    public void updateTarget(float val){
        alphaTarget = val;
    }
    public void startAnimation(){
        timer.start();
    }
    public void stopAnimation(){
        timer.stop();
        alphaTarget = getAlpha();
    }
    public void pauseAnimation(){
        timer.stop();
    }
    public void setSpeedPerSecond(float speed){
        step = speed / 1000 * delay;
    }

    private boolean containsPoint(Point p){
        Point l = getLocation();
        Point n = new Point(p.x - l.x, p.y -l.y);
        return contains(n);
    }
}
