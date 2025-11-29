import java.awt.*;
import java.awt.image.BufferedImage;

public class FireModel extends BufferedImage {
    private Temperatures temperatures;
    private ColorPalette colorPalette;
    public PaletteParameters dtoPalette;

    public FireModel(int width, int height,FireController fireController, PaletteParameters dtoPalette) {
        super(width, height, BufferedImage.TYPE_INT_ARGB);

//      Generating Color Palette:
        this.dtoPalette = dtoPalette;
        this.temperatures = new Temperatures(220, 95, 5.099f, 13.925555f);

        this.colorPalette = new ColorPalette(dtoPalette);
        this.colorPalette.calc();

//      Initializing temperatures

        this.createFireImage();

    }
    public ColorPalette getColorPalette(){
        return this.colorPalette;
    }
    public void next() {

        this.temperatures.next();

        int tempe;
        int[][] colorPale = colorPalette.getColorsPalette();

        for (int posY = 0; posY < this.temperatures.getHeight(); posY++) {
            for (int posX = 0; posX < this.temperatures.getWidth(); posX++) {

                tempe = this.temperatures.getTemp(posX, posY);

                Color c = new Color(
                        colorPale[tempe][0],
                        colorPale[tempe][1],
                        colorPale[tempe][2],
                        colorPale[tempe][3]
                );

                this.setRGB(posX, posY, c.getRGB());
            }
        }




}
    private void createFireImage() {
        next();
    }


    private void createFireImage1() {
        for (int j = 0; j < this.temperatures.getWidth(); j++) {
            for (int i = 0; i < this.temperatures.getHeight() - 2; i++) {
                Color color = this.colorPalette.getColor(this.temperatures.getTemp(i, j));
                this.setRGB(j, i, color.getRGB());
            }
        }
    }
}
