import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;

public class ControlPanel extends JPanel {
    AnimationControls animationControls;
    private PaletteConfiguration paletteC;
    FireView fView;

    JLabel nombreLabel = new JLabel("Nombre: ");

    JLabel carpetaLabel = new JLabel("Carpeta: ");

    JLabel resolucionLabel = new JLabel("Resolución: ");

    public ControlPanel(FireView fView) {


        this.setLayout(new GridBagLayout());
        this.fView = fView;
        this.paletteC = new PaletteConfiguration(this);
//      Creating buttons
        animationControls = new AnimationControls(new JToggleButton("Play"), new JButton("Reset"), new JButton("Stop"), new JTextField("fireWidth"), new JTextField("fireHeight"), new JTextField("fireX"), new JTextField("fireY"));

//      Default values for the textFields
        this.seDefaultValuesToTheTextsFields();

//      Adding buttons to the ControlPanel (JPanel)
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 0;
        this.add(animationControls.getPlayPause(), c);
        c.gridy = 1;
        this.add(animationControls.getRestart(), c);
        c.gridy = 2;
        this.add(animationControls.getStop(), c);
        c.gridy = 3;
        this.add(animationControls.getFireWidth(), c);
        c.gridy = 4;
        this.add(animationControls.getFireHeight(), c);
        c.gridy = 5;
        this.add(animationControls.getFireX(), c);
        c.gridy = 6;
        this.add(animationControls.getFireY(), c);
        c.gridy = 7;
        addPaletteConfiguration();
        addButtonImage();

    }

    private void addButtonImage() {
        // Crear botón
        JButton changeImageB = new JButton("Cambiar Imagen");
        changeImageB.setBorderPainted(false);
        changeImageB.setContentAreaFilled(false);
        changeImageB.setFocusPainted(false);
        changeImageB.setOpaque(false);
        changeImageB.setMargin(new Insets(2, 2, 2, 2));

        changeImageB.addActionListener(e -> fView.changeIm());

        // Posicionarlo en la paleta (o en ControlPanel según necesites)
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 8; // debajo de otros botones
        c.insets = new Insets(5, 0, 0, 0);

        this.add(changeImageB, c);
    }




    //ver la paleta2025
    public PaletteConfiguration getPaletteConfiguration(){

        return this.paletteC;
    }
    private void addPaletteConfiguration() {
        this.paletteC = new PaletteConfiguration(this);

        this.paletteC.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST; // A la izquierda
        c.fill = GridBagConstraints.NONE; // No se expande
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 0;
        c.insets = new Insets(2, 2, 2, 2); // pequeño margen

// Botón pequeño sin fondo
        JButton showButton = new JButton("Mostrar JTable");
        showButton.setBorderPainted(false);
        showButton.setContentAreaFilled(false);
        showButton.setFocusPainted(false);
        showButton.setOpaque(false);
        showButton.setMargin(new Insets(2, 2, 2, 2));
        showButton.addActionListener(this.getFireView());

// Agregar botón a la paleta
        this.paletteC.add(showButton, c);

// Agregar la paleta al ControlPanel debajo de los botones de AnimationControls
        GridBagConstraints cp = new GridBagConstraints();
        cp.anchor = GridBagConstraints.NORTHWEST;
        cp.fill = GridBagConstraints.NONE;
        cp.gridx = 0;
        cp.gridy = 7; // justo después de los otros botones
        cp.weightx = 0;
        cp.weighty = 0;
        cp.insets = new Insets(5, 0, 0, 0); // un poco de espacio encima
        this.add(this.paletteC, cp);


    }
    public JLabel getNombreLabel() {
        return nombreLabel;
    }

    public void setNombreLabel(JLabel nombreLabel) {
        this.nombreLabel = nombreLabel;
    }

    public JLabel getCarpetaLabel() {
        return carpetaLabel;
    }

    public void setCarpetaLabel(JLabel carpetaLabel) {
        this.carpetaLabel = carpetaLabel;
    }

    public JLabel getResolucionLabel() {
        return resolucionLabel;
    }

    public void setResolucionLabel(JLabel resolucionLabel) {
        this.resolucionLabel = resolucionLabel;
    }



    public FireView getFireView() {
        return this.fView;
    }
    public void seDefaultValuesToTheTextsFields() {

        animationControls.getFireX().setText("220");
        animationControls.getFireY().setText("320");
        animationControls.getFireWidth().setText("220");
        animationControls.getFireHeight().setText("100");

    }
}
