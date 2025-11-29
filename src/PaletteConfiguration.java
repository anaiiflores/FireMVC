import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class PaletteConfiguration extends JPanel {


    private ControlPanel controlP;
    private JPanel paletteP;
    private JTable temperaturesColorsT;
    private JButton showImageB;
    private DefaultTableModel model;
    private JFrame frame;
    private Color[] palette;
    private JButton cambiarColorB;
    private JButton eliminarTemperaturaB;
    private JTextField valorTemperatura;
    private JButton añadirTemperaturaB;
    private JLabel valorTemperaturaL;

    // Constructor
    public PaletteConfiguration(ControlPanel controlP) {
        this.controlP = controlP;
        this.addPaletteConfiguration();
    }

    // Getters y Setters
    public JTable getTemperaturesColorT() {
        return this.temperaturesColorsT;
    }

    public int getTemperatureValue() {
        String texto = this.valorTemperatura.getText().trim();

        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Debes introducir un valor numérico para la temperatura.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return -1;
        }

        try {
            int valor = Integer.parseInt(texto);
            this.valorTemperatura.setText("");
            return valor;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "El valor debe ser un número entero.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    public JButton getModificarColorTarget() {
        return this.cambiarColorB;
    }

    public JButton getEliminarColorTarget() {
        return this.eliminarTemperaturaB;
    }

    public JButton getAñadirColorTarget() {
        return this.añadirTemperaturaB;
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public JTextField getTemperature() {
        return this.valorTemperatura;
    }

    public JLabel getTemperaturaL() {
        return this.valorTemperaturaL;
    }

    public void setTemperaturesColorT(ArrayList<ColorTarget> colorTargets) {
        if (this.temperaturesColorsT == null) return;

        this.model = (DefaultTableModel) this.temperaturesColorsT.getModel();
        int rowCount = this.model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            this.model.removeRow(i);
        }

        for (ColorTarget ct : colorTargets) {
            Color color = new Color(ct.getR(), ct.getG(), ct.getB(), ct.getA());
            this.model.addRow(new Object[]{ct.getTemp(), color});
        }

        temperaturesColorsT.getColumnModel().getColumn(1).setCellRenderer(new ColorRenderer());
        temperaturesColorsT.getColumnModel().getColumn(1).setCellEditor(new ColorEditor());
    }

    // Métodos privados
    private void addPaletteConfiguration() {

        this.paletteP = new JPanel();
        this.paletteP.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 0;
        c.gridx = 0;
        c.weightx = 0;
        c.weighty = 0;
        c.gridheight = 1;
        c.gridwidth = 1;

        this.model = new DefaultTableModel(0, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // columna 1 inmutable
            }
        };

        model.addColumn("Temperatures");
        model.addColumn("Color");

        this.temperaturesColorsT = new JTable(model);
        temperaturesColorsT.getColumnModel().getColumn(1).setCellRenderer(new ColorRenderer());
        temperaturesColorsT.getColumnModel().getColumn(1).setCellEditor(new ColorEditor());

        this.cambiarColorB = new JButton("Cambiar Color");
        this.cambiarColorB.addActionListener(this.controlP.getFireView());

        this.eliminarTemperaturaB = new JButton("Eliminar Temperatura");
        this.eliminarTemperaturaB.addActionListener(this.controlP.getFireView());

        this.añadirTemperaturaB = new JButton("Añadir Temperatura");
        this.añadirTemperaturaB.addActionListener(this.controlP.getFireView());

        this.valorTemperaturaL = new JLabel("Valor Temperatura: ");
        this.valorTemperatura = new JTextField("   ");

        this.showImageB = new JButton("Mostrar JTable");
        this.showImageB.addActionListener(this.controlP.getFireView());
        this.paletteP.add(showImageB, c);

        this.add(paletteP);
        paletteP.validate();
        paletteP.repaint();
    }


}
