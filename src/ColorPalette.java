import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class ColorPalette {

    public PaletteParameters dtoPalette;

    private int[][] colorPalette;
    private ArrayList<ColorTarget> colorTargets;

    public ColorPalette(PaletteParameters dtoPalette) {
        if (dtoPalette == null) {
            this.dtoPalette = new PaletteParameters();
        } else {
            this.dtoPalette = dtoPalette;
        }

        this.colorPalette = new int[256][4];
        this.colorTargets = new ArrayList<>();

        // Cargar colores por defecto
        this.addColorTargets();

        // Generar cálculo inicial
        this.calc1();

        // Guardar en dto
        this.dtoPalette.colorTarget = this.colorTargets;
    }

    public int[][] getColorsPalette() {
        this.calc();
        return colorPalette;
    }

    public void calc() {
        for (int target = 0; target < colorTargets.size() - 1; target++) {
            ColorTarget c1 = colorTargets.get(target);
            ColorTarget c2 = colorTargets.get(target + 1);

            int size = Math.abs(c1.getTemp() - c2.getTemp());

            float redCadence = Math.abs(c1.getR() - c2.getR()) / (float) size;
            float greenCadence = Math.abs(c1.getG() - c2.getG()) / (float) size;
            float blueCadence = Math.abs(c1.getB() - c2.getB()) / (float) size;
            float alphaCadence = Math.abs(c1.getA() - c2.getA()) / (float) size;

            calcIntervalColors(c1, c2, size, redCadence, greenCadence, blueCadence, alphaCadence);
        }
    }

    public void setColorTargets() {
        if (this.dtoPalette.colorTarget == null) {
            this.dtoPalette.colorTarget = new ArrayList<>();
        }
        this.colorTargets = this.dtoPalette.colorTarget;
    }

    public void addColorTargets1(Color color, int temp) {
        ColorTarget ct = new ColorTarget(color, temp);
        colorTargets.add(ct);
        sortColorTargets();
        calc1();
    }

    private void sortColorTargets() {
        colorTargets.sort(Comparator.comparingInt(ColorTarget::getTemp));
    }

    public void calc1() {
        if (colorTargets.isEmpty()) return;

        sortColorTargets();

        for (int i = 0; i < colorTargets.size() - 1; i++) {
            ColorTarget c1 = colorTargets.get(i);
            ColorTarget c2 = colorTargets.get(i + 1);

            int t1 = c1.getTemp();
            int t2 = c2.getTemp();

            int span = Math.abs(t1 - t2);
            if (span == 0) continue;

            double[] steps = calcSteps(c1, c2, t1, t2);

            // Rellenar entre t1 y t2
            for (int t = t1; t <= t2; t++) {
                int offset = t - t1;

                int r = clampColor((int) (c1.getR() + steps[0] * offset));
                int g = clampColor((int) (c1.getG() + steps[1] * offset));
                int b = clampColor((int) (c1.getB() + steps[2] * offset));
                int a = clampColor((int) (c1.getA() + steps[3] * offset));

                colorPalette[t][0] = r;
                colorPalette[t][1] = g;
                colorPalette[t][2] = b;
                colorPalette[t][3] = a;
            }
        }
    }

    private int clampColor(int c) {
        return Math.max(0, Math.min(255, c));
    }

    private double[] calcSteps(ColorTarget c1, ColorTarget c2, int t1, int t2) {
        int diff = t2 - t1;
        if (diff == 0) diff = 1;

        double red = (double) (c2.getR() - c1.getR()) / diff;
        double green = (double) (c2.getG() - c1.getG()) / diff;
        double blue = (double) (c2.getB() - c1.getB()) / diff;
        double alpha = (double) (c2.getA() - c1.getA()) / diff;

        return new double[]{red, green, blue, alpha};
    }

    public void addColorTargets() {
        colorTargets.clear(); // aseguramos que no se dupliquen

        colorTargets.add(new ColorTarget(new Color(0, 0, 0, 0), 0));
        colorTargets.add(new ColorTarget(new Color(0, 0, 0, 0), 55));
        colorTargets.add(new ColorTarget(new Color(155, 0, 0, 110), 60));
        colorTargets.add(new ColorTarget(new Color(200, 100, 0, 180), 73));
        colorTargets.add(new ColorTarget(new Color(235, 235, 40, 250), 113));
        colorTargets.add(new ColorTarget(new Color(255, 255, 200, 255), 130));
        colorTargets.add(new ColorTarget(new Color(255, 255, 255, 255), 150));
        colorTargets.add(new ColorTarget(new Color(255, 255, 255, 255), 255));

        // Guardamos en dtoPalette
        // this.dtoPalette.colorTarget = this.colorTargets;
    }

    public Color getColor(int temp) {
        int r = colorPalette[temp][0];
        int g = colorPalette[temp][1];
        int b = colorPalette[temp][2];
        int a = colorPalette[temp][3];
        return new Color(r, g, b, a);
    }

    private void calcIntervalColors(ColorTarget c1, ColorTarget c2,
                                    int size, float rC, float gC, float bC, float aC) {
        int count = 1;

        for (int i = c1.getTemp() + 1; count <= size && i < colorPalette.length; i++) {
            colorPalette[i][0] = Math.min((int) (colorPalette[c1.getTemp()][0] + rC * count), 255);
            colorPalette[i][1] = Math.min((int) (colorPalette[c1.getTemp()][1] + gC * count), 255);
            colorPalette[i][2] = Math.min((int) (colorPalette[c1.getTemp()][2] + bC * count), 255);
            colorPalette[i][3] = Math.min((int) (colorPalette[c1.getTemp()][3] + aC * count), 255);

            count++;
        }

        colorPalette[c2.getTemp()][0] = c2.getR();
        colorPalette[c2.getTemp()][1] = c2.getG();
        colorPalette[c2.getTemp()][2] = c2.getB();
        colorPalette[c2.getTemp()][3] = c2.getA();
    }


}
