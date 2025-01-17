package frontend.view.components;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * A table cell renderer that displays a simple text using a label just like the default renderer of JTable.
 *
 * Additionally a ToolTip is provided containing the whole text of the cell. This is especially useful if a large chunk
 * of text is being displayed in a tiny cell and the text is being clipped.
 *
 * @author Michael
 */
public class ToolTipTableCellRenderer extends DefaultTableCellRenderer {

    /**
     * Default serialization - not needed.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Returns the default table cell renderer.
     */
    @Override
    public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected,
            final boolean hasFocus, final int row, final int column) {

        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        setToolTipText(value.toString());

        return this;
    }
}
