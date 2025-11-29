import java.util.Random;

public class Temperatures {
    private final int[][] temperatures;
    private int width, height;
    private float percentageColdPoints, percentageSparks;


    public Temperatures(int width, int height, float percentageColdPoints, float percentageSparks) {
        this.temperatures = new int[height][width];
        this.setWidth(width);
        this.setHeight(height);
        this.setPercentageColdPoints(percentageColdPoints);
        this.setPercentageSparks(percentageSparks);
    }

    public int[][] getTemperatures() {
        return temperatures;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getPercentageColdPoints() {
        return percentageColdPoints;
    }

    public void setPercentageColdPoints(float percentageColdPoints) {
        this.percentageColdPoints = percentageColdPoints;
    }

    public float getPercentageSparks() {
        return percentageSparks;
    }

    public void setPercentageSparks(float percentageSparks) {
        this.percentageSparks = percentageSparks;
    }

    public int getTemp(int x, int y) {
        return temperatures[y][x];
    }

    public void next() {

        createColdPoints();
        createSparks();
        calcTemperatures();

    }

    private void createColdPoints() {
        // We need to cold  pixels randomly
        Random random = new Random();
        float probability;
        for (int i = 0; i < this.getWidth(); i++) {
            probability = random.nextFloat() * 100;
            if (probability < this.getPercentageColdPoints()) this.temperatures[this.getHeight() - 1][i] = 255;
        }
    }

    private void createSparks() {

        //Generate sparks randomly , beginning at the base>>
        Random random = new Random();
        float probability;
        for (int i = 0; i < this.getWidth(); i++) {
            probability = random.nextFloat() * 100;
            if (probability < this.getPercentageSparks()) this.temperatures[this.getHeight() - 1][i] = 0;
        }

    }

    private void calcTemperatures() {

        // Iterate through each pixel
        for (int i = this.height - 2; i >= 0; i--) {
            for (int j = 1; j < this.width; j++) {
                int averageTemperature = calculateAverageTemperature(i, j);
                temperatures[i][j] = averageTemperature;
            }
        }

    }

    private int calculateAverageTemperature(int row, int col) {
        int sum = temperatures[row][col] + temperatures[row + 1][col];
        int divisor = 2;


        if (col > 0) {
            sum += temperatures[row][col - 1];
            sum += temperatures[row + 1][col - 1];
            divisor += 2;
        }

        if (col < this.width - 1) {
            sum += temperatures[row][col + 1];
            sum += temperatures[row + 1][col + 1];
            divisor += 2;
        }

        return sum / divisor;
    }
}
