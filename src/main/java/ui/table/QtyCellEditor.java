package ui.table;

import ui.spinner.Spinner;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class QtyCellEditor extends DefaultCellEditor {

    private Spinner input;
    private EventCellInputChange event;

    public QtyCellEditor(EventCellInputChange event) {
        super(new JCheckBox());
        this.event = event;
        input = new Spinner();
        SpinnerNumberModel numberModel = (SpinnerNumberModel) input.getModel();
        numberModel.setMinimum(1);
        input.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                inputChange();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        super.getTableCellEditorComponent(table, value, isSelected, row, column);
        int qty = Integer.parseInt(value.toString());
        input.setValue(qty);
        return input;
    }

    @Override
    public Object getCellEditorValue() {
        return input.getValue();
    }

    private void inputChange(){
        event.inputChanged();
    }
}
