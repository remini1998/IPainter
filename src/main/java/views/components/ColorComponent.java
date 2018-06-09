package views.components;

import javax.swing.*;
import java.awt.*;

public class ColorComponent extends JComboBox<Color> {

    private ColorComponent that = this;

    private Color color;

    private class Render extends DefaultListCellRenderer{

        /**
         * Return a component that has been configured to display the specified
         * value. That component's <code>paint</code> method is then called to
         * "render" the cell.  If it is necessary to compute the dimensions
         * of a list because the list cells do not have a fixed size, this method
         * is called to generate a component on which <code>getPreferredSize</code>
         * can be invoked.
         *
         * @param list         The JList we're painting.
         * @param value        The value returned by list.getModel().getElementAt(index).
         * @param index        The cells index.
         * @param isSelected   True if the specified cell was selected.
         * @param cellHasFocus True if the specified cell has the focus.
         * @return A component whose paint() method will render the specified value.
         * @see JList
         * @see ListSelectionModel
         * @see ListModel
         */
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            Color c = (Color) value;
            setBackground(c);
            this.setText("#"+Integer.toHexString(c.getRGB()).substring(2).toUpperCase());

            return this;
        }
    }

    public ColorComponent(Color initColor){
        this.renderer = new Render();
        this.addItem(initColor);
        setSelectedIndex(0);
    }

    public ColorComponent(){
        this(Color.black);
    }

}
