import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorEditor extends AbstractCellEditor implements TableCellEditor {

    private JButton colorButton;
    private Color selectedColor;

    public ColorEditor() {
        colorButton = new JButton();
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(colorButton, "Selecciona un color", selectedColor);
                if (newColor != null) {
                    selectedColor = newColor;
                    colorButton.setBackground(newColor);
                }
            }
        });
    }

    @Override
    public Object getCellEditorValue() {
        return selectedColor; // Devuelve el color seleccionado
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value instanceof Color) {
            selectedColor = (Color) value;
            colorButton.setBackground(selectedColor);
        } else {
            selectedColor = null;
            colorButton.setBackground(Color.WHITE);
        }
        return colorButton;
    }
}
