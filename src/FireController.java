import javax.swing.*;
import java.awt.*;

public class FireController {

    FireView fireView;
    FireModel fireModel;
    public PaletteParameters dtoPalette;

    public FireController() {

        // Configurar la UI
        this.initClass();
        // Iniciar animación en hilo separado
        startAnimationThread();
    }

    public FireModel getFireModel() {
        return this.fireModel;
    }

    private void initClass() {
        this.dtoPalette = new PaletteParameters();
// Inicializar la vista
        // Inicializar el modelo
        this.fireModel = new FireModel(220, 95, this, dtoPalette);
        this.fireView = new FireView(this, 512, 512);

        configureFrame();
        addUIComponents();
    }

    private void configureFrame() {
        fireView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fireView.setSize(650, 650);
        fireView.setResizable(false);
        fireView.setLayout(new GridBagLayout());
        fireView.setVisible(true);
    }

    private void addUIComponents() {
        this.fireView.viewer.paintForeGroundAndBackground(fireModel, fireView.generalParameters);
    }

    // Animación en hilo separado
    private void startAnimationThread() {
        Thread animationThread = new Thread(() -> {
            while (true) {
                if (fireView.controlPanel.animationControls.getPlayPause().isSelected()) {
                    fireView.viewer.paintForeGroundAndBackground(fireModel, fireView.generalParameters);
                    fireModel.next();
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                    System.err.println(ex);
                }
            }
        });
        animationThread.start();
    }

    public static void main(String[] args) {
        // Crear el controlador
        new FireController();
    }
}
