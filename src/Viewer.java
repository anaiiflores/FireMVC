import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Viewer extends Canvas {
    private BufferedImage backgroundImg;
    private BufferStrategy bs;

    public Viewer(int width, int height) {

        this.setPreferredSize(new Dimension(width, height));
        this.loadBackground();
        this.bs = null;
    }

    public void setBackgroundImage(BufferedImage backgroundImage) {
        this.backgroundImg = backgroundImage;
    }

    public BufferedImage getBackgroundImage() {
        return backgroundImg;
    }



    public void paintBackgroundd() {
        if (this.bs == null) {
            this.createBufferStrategy(2);
            bs = this.getBufferStrategy();
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(getBackgroundImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        bs.show();
        g.dispose();
    }



    public void paintForeGroundAndBackground(FireModel fire, GeneralParameters generalParameters) {

        if (bs == null) {
            System.out.println("Creating double buffer Fire");
            this.createBufferStrategy(2);
            bs = this.getBufferStrategy();

        }

        Graphics g = bs.getDrawGraphics();

        g.drawImage(this.backgroundImg, 0, 0, this.getWidth(), this.getHeight(), null);
        g.drawImage(fire, generalParameters.getFireXPosition(), generalParameters.getFireYPosition(), generalParameters.getFireWidth(), generalParameters.getFireHeight(), null);


        bs.show();
        g.dispose();

    }

    private void loadBackground() {
        try {
            this.backgroundImg = ImageIO.read(new File("bg.jpg"));
            System.out.println("Background loaded :-)");
            System.out.println("Width: " + this.backgroundImg.getWidth());
            System.out.println("Height: " + this.backgroundImg.getHeight());
        } catch (IOException e) {
            System.err.println("Error loading background. ");
            System.err.println(e);
        }

    }

    @Override
    public void paint(Graphics g) {
        System.out.println("override the paint");
    }
}
