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
        AlphaAnimationPanel that = this;
        this.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                alphaTarget = mouseEnterOpacity;
            }

            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                Point p = e.getPoint();
                if (!that.contains(p)){
                    alphaTarget = mouseExitOpacity;
                }
            }
        });
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
}
