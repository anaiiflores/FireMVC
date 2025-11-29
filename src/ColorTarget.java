import java.awt.*;

public class ColorTarget {

    private int temp;
    private final Color color;

    public ColorTarget(int r, int g, int b, int temp) {
        this.color = new Color(r, g, b, 255); // alfa por defecto
        this.temp = temp;
    }

    public ColorTarget(Color color, int temp) {
        this.setTemp(temp);
        this.color = color;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getR() {
        return this.color.getRed();
    }

    public int getG() {
        return this.color.getGreen();
    }

    public int getB() {
        return this.color.getBlue();
    }

    public int getA() {
        return this.color.getAlpha();
    }
}
