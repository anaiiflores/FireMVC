import javax.swing.*;
import java.awt.event.*;

public class AnimationControls {
    private JToggleButton playPause;
    private JButton restart;
    private JButton stop;

    private JTextField fireWidth;
    private JTextField fireHeight;

    private JTextField fireX;

    private JTextField fireY;

    public AnimationControls(JToggleButton playPause, JButton restart, JButton stop, JTextField fireWidth, JTextField fireHeight, JTextField fireX, JTextField fireY) {
        this.setPlayPause(playPause);
        this.setRestart(restart);
        this.setStop(stop);
        this.setFireWidth(fireWidth);
        this.setFireHeight(fireHeight);
        this.setFireX(fireX);
        this.setFireY(fireY);
    }

    public JToggleButton getPlayPause() {
        return playPause;
    }

    public void setPlayPause(JToggleButton playPause) {
        this.playPause = playPause;
    }

    public JButton getRestart() {
        return restart;
    }

    public void setRestart(JButton restart) {
        this.restart = restart;
    }

    public JButton getStop() {
        return stop;
    }

    public void setStop(JButton stop) {
        this.stop = stop;
    }

    public JTextField getFireWidth() {
        return fireWidth;
    }

    public void setFireWidth(JTextField fireWidth) {
        this.fireWidth = fireWidth;
    }

    public JTextField getFireHeight() {
        return fireHeight;
    }

    public void setFireHeight(JTextField fireHeight) {
        this.fireHeight = fireHeight;
    }

    public JTextField getFireX() {
        return fireX;
    }

    public void setFireX(JTextField fireX) {
        this.fireX = fireX;
    }

    public JTextField getFireY() {
        return fireY;
    }

    public void setFireY(JTextField fireY) {
        this.fireY = fireY;
    }
}
