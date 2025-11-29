import java.awt.*;

public class GeneralParameters {
    private int fireWidth;
    private int fireHeight;

    private int fireXPosition;

    private int fireYPosition;

    private int calcFramesPerSecond;

    private int showFramesPerSecond;
    public Image backgroundImage;

    public GeneralParameters(int fireWidth, int fireHeight, int fireXPosition, int fireYPosition, int calcFramesPerSecond, int showFramesPerSecond) {
        this.setFireWidth(fireWidth);
        this.setFireHeight(fireHeight);
        this.setFireXPosition(fireXPosition);
        this.setFireYPosition(fireYPosition);
        this.setCalcFramesPerSecond(calcFramesPerSecond);
        this.setShowFramesPerSecond(showFramesPerSecond);
    }

    public int getFireWidth() {
        return fireWidth;
    }

    public void setFireWidth(int fireWidth) {
        this.fireWidth = fireWidth;
    }

    public int getFireHeight() {
        return fireHeight;
    }

    public void setFireHeight(int fireHeight) {
        this.fireHeight = fireHeight;
    }

    public int getFireXPosition() {
        return fireXPosition;
    }

    public void setFireXPosition(int fireXPosition) {
        this.fireXPosition = fireXPosition;
    }

    public int getFireYPosition() {
        return fireYPosition;
    }

    public void setFireYPosition(int fireYPosition) {
        this.fireYPosition = fireYPosition;
    }

    public int getCalcFramesPerSecond() {
        return calcFramesPerSecond;
    }

    public void setCalcFramesPerSecond(int calcFramesPerSecond) {
        this.calcFramesPerSecond = calcFramesPerSecond;
    }

    public int getShowFramesPerSecond() {
        return showFramesPerSecond;
    }

    public void setShowFramesPerSecond(int showFramesPerSecond) {
        this.showFramesPerSecond = showFramesPerSecond;
    }
    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
}
