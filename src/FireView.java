import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FireView extends JFrame implements ComponentListener, ActionListener, ItemListener {
    ControlPanel controlPanel;
    Viewer viewer;
    GeneralParameters generalParameters;
    FireController fireController;
    PaletteParameters dtoPalette;

    private JDialog dialog;


    public FireView(FireController fireController, int withViewer, int heightViewer) {
        this.fireController = fireController;



        this.controlPanel = new ControlPanel(this);

        // Inicializar viewer
        this.viewer = new Viewer(withViewer, heightViewer);

        // Inicializar fireController
        //he quitado esto para provar
        //this.fireController.dtoPalette.colorTarget = new ArrayList<>();
       // this.fireController.setFireModel(new FireModel()); // Si tu FireController necesita FireModel

        // Layout principal del JFrame
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        // Agregar controlPanel a la izquierda
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.VERTICAL;
        c.anchor = GridBagConstraints.NORTHWEST;
        //controlPanel.setPreferredSize(new Dimension(200, 600)); // ancho fijo
        this.add(this.controlPanel, c);

        // Agregar viewer a la derecha
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        this.add(this.viewer, c);

        // Inicializar generalParameters
        this.generalParameters = new GeneralParameters(
                Integer.parseInt(controlPanel.animationControls.getFireWidth().getText()),
                Integer.parseInt(controlPanel.animationControls.getFireHeight().getText()),
                Integer.parseInt(controlPanel.animationControls.getFireX().getText()),
                Integer.parseInt(controlPanel.animationControls.getFireY().getText()), 0, 0
        );

        // Agregar action listeners
        this.controlPanel.animationControls.getStop().addActionListener(this);
        this.controlPanel.animationControls.getRestart().addActionListener(this);
        this.controlPanel.animationControls.getFireWidth().addActionListener(this);
        this.controlPanel.animationControls.getFireHeight().addActionListener(this);
        this.controlPanel.animationControls.getFireX().addActionListener(this);
        this.controlPanel.animationControls.getFireY().addActionListener(this);

        // Configuración JFrame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setVisible(true);
    }
    public void crearDialogoPaleta() {

        JDialog dialog = new JDialog(this, "Tabla de Temperaturas", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        PaletteConfiguration paletteC = this.controlPanel.getPaletteConfiguration();

        // ---- FILA BOTONES ----
        c.gridy = 0;
        c.gridx = 0;

        dialog.add(paletteC.getModificarColorTarget(), c);

        c.gridx = 1;
        dialog.add(paletteC.getEliminarColorTarget(), c);

        c.gridx = 2;
        dialog.add(paletteC.getAñadirColorTarget(), c);

        // ---- FILA CAMPO DE TEXTO ----
        c.gridy = 1;
        c.gridx = 0;
        dialog.add(paletteC.getTemperaturaL(), c);

        c.gridx = 1;
        c.gridwidth = 1;

        dialog.add(paletteC.getTemperature(), c);
        c.gridwidth = 1;

        // ---- TABLA ----
        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 3;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;

        dialog.add(new JScrollPane(paletteC.getTemperaturesColorT()), c);

        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }


    private void setValuesIntoGeneralParameters() {
        this.generalParameters.setFireWidth(Integer.parseInt(controlPanel.animationControls.getFireWidth().getText()));
        this.generalParameters.setFireHeight(Integer.parseInt(controlPanel.animationControls.getFireHeight().getText()));
        this.generalParameters.setFireXPosition(Integer.parseInt(controlPanel.animationControls.getFireX().getText()));
        this.generalParameters.setFireYPosition(Integer.parseInt(controlPanel.animationControls.getFireY().getText()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        switch (str) {
            case "Stop":
                this.controlPanel.animationControls.getPlayPause().setSelected(false);
                this.viewer.paintForeGroundAndBackground(null, generalParameters);
                break;
            case "Reset":
                this.controlPanel.seDefaultValuesToTheTextsFields();
                setValuesIntoGeneralParameters();
                break;

            case "Mostrar JTable":
                System.out.println("Has pulsado");
                addRows();
                System.out.println("PaletteC = " + this.controlPanel.getPaletteConfiguration());

                crearDialogoPaleta();
                break;
            case "Cambiar Color":
                modificarColorTarget();
                break;

            case "Eliminar Temperatura":
                eliminarColorTarget();
                break;

            case "Añadir Temperatura":
                añadirColorTarget();
                break;
            case "Cambiar Imagen":
                System.out.println("Has cambiarimagen");

                this.changeIm();

                break;


            default:
                System.out.println(str);
                setValuesIntoGeneralParameters();
                System.err.println("Something was bad: " + e);
        }

    }

    private void añadirColorTarget() {
        int value = this.controlPanel.getPaletteConfiguration().getTemperatureValue();
        if (value == -1) return; // evitar que explote si está vacío

        Color color = new Color(255, 255, 255, 255);
        ColorTarget colorTarget = new ColorTarget(color, value);

        this.fireController.dtoPalette.colorTarget.add(colorTarget);
        this.fireController.getFireModel().getColorPalette().calc();

        addRows();
    }


    private void eliminarColorTarget() {
        int value = this.controlPanel.getPaletteConfiguration().getTemperatureValue();
        DefaultTableModel tableModel = (DefaultTableModel) this.controlPanel.getPaletteConfiguration().getTemperaturesColorT().getModel();

        for (int i = this.fireController.dtoPalette.colorTarget.size() - 1; i >= 0; i--) {
            System.out.println(i);
//tmeperatura 2025
            if (this.fireController.dtoPalette.colorTarget.get(i).getTemp() == value) {
                this.fireController.dtoPalette.colorTarget.remove(i);
            }
        }
        this.fireController.getFireModel().getColorPalette().calc();
        addRows();
    }

    private void modificarColorTarget() {
        while (!this.fireController.dtoPalette.colorTarget.isEmpty()) {
            this.fireController.dtoPalette.colorTarget.remove(0);
        }
        for (int i = 0; i < this.controlPanel.getPaletteConfiguration().getTemperaturesColorT().getRowCount(); i++) {
            String tempStr = this.controlPanel.getPaletteConfiguration().getTemperaturesColorT().getValueAt(i, 0).toString();

            if (!tempStr.isEmpty()) {
                try {
                    int temp = Integer.parseInt(tempStr);
                    Color color = (Color) this.controlPanel.getPaletteConfiguration().getTemperaturesColorT().getValueAt(i, 1);
                    ColorTarget colorTarget = new ColorTarget(color, temp);
                    this.fireController.dtoPalette.colorTarget.add(colorTarget);

                } catch (NumberFormatException e) {
                }
            }
            this.fireController.getFireModel().getColorPalette().setColorTargets();
        }
    }

    private void addRows() {
        System.out.println("PaletteC = " + this.controlPanel.getPaletteConfiguration());
        ColorPalette palette = new ColorPalette(dtoPalette);
        palette.calc1();
        this.controlPanel.getPaletteConfiguration().setTemperaturesColorT(this.fireController.dtoPalette.colorTarget);
    }
    public void changeIm() {

        JFileChooser chosfile = new JFileChooser();
        FileNameExtensionFilter control = new FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png", "gif");
        chosfile.setFileFilter(control);
        int fil = chosfile.showOpenDialog(null);

        if (fil == JFileChooser.APPROVE_OPTION) {
            File fileSelec = chosfile.getSelectedFile();

            try {
                generalParameters.setBackgroundImage(ImageIO.read(fileSelec));
                System.out.println(generalParameters.getBackgroundImage());

                BufferedImage backgroundImage = ImageIO.read(fileSelec);
                viewer.setBackgroundImage(backgroundImage);
                this.viewer.paintBackgroundd();

                System.out.println("imagen?");

                controlPanel.getNombreLabel().setText("Name: " + fileSelec.getName());
                controlPanel.getCarpetaLabel().setText("Folder: " + fileSelec.getParent());
                int ancho = generalParameters.getBackgroundImage().getWidth(null); // Cambia esto
                int alto = generalParameters.getBackgroundImage().getHeight(null); // Cambia esto
                controlPanel.getResolucionLabel().setText("Resolution: " + ancho + "x" + alto);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void componentResized(ComponentEvent componentEvent) {

    }

    @Override
    public void componentMoved(ComponentEvent componentEvent) {

    }

    @Override
    public void componentShown(ComponentEvent componentEvent) {

    }

    @Override
    public void componentHidden(ComponentEvent componentEvent) {

    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {

    }
}
