package frontend.view.components;

import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.table.AbstractTableModel;

import frontend.model.Employee;
import frontend.model.Gender;

/**
 * Table model of an employee.
 *
 * @author Michael
 */
public class EmployeeTableModel extends AbstractTableModel {
    /**
     * Default serialization - not needed.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Column names being displayed.
     */
    private ArrayList<String> columnNames = new ArrayList<String>();;

    /**
     * Table content.
     */
    private ArrayList<Employee> employeeData = new ArrayList<Employee>();

    /**
     * Access to localized application resources.
     */
    private ResourceBundle resources;

    /**
     * Initialization.
     */
    public EmployeeTableModel() {
        this.resources = ResourceBundle.getBundle("frontend");

        this.columnNames.add(this.resources.getString("gui.employee.id"));
        this.columnNames.add(this.resources.getString("gui.employee.firstName"));
        this.columnNames.add(this.resources.getString("gui.employee.lastName"));
        this.columnNames.add(this.resources.getString("gui.employee.gender"));
    }

    /**
     * Returns the number of columns in the model.
     */
    @Override
    public int getColumnCount() {
        return this.columnNames.size();
    }

    /**
     * Returns the number of rows in the model.
     */
    @Override
    public int getRowCount() {
        return employeeData.size();
    }

    /**
     * Returns a default name for the column using spreadsheet conventions:A, B, C, ... Z, AA, AB, etc.
     */
    @Override
    public String getColumnName(final int col) {
        return this.columnNames.get(col);
    }

    /**
     * Gets table cell content for a given row and column. The content is extracted from the custom data model.
     */
    @Override
    public Object getValueAt(final int row, final int col) {
        Employee employee = employeeData.get(row);
        final int indexThree = 3;

        switch (col) {
        case 0:
            return employee.getId();
        case 1:
            return employee.getFirstName();
        case 2:
            return employee.getLastName();
        case indexThree:
            if (employee.getGender() == Gender.FEMALE) {
                return this.resources.getString("gui.employee.gender.female");
            } else {
                return this.resources.getString("gui.employee.gender.male");
            }
        default:
            return null;
        }
    }

    /**
     * Returns false. This is the default implementation for all cells.
     */
    @Override
    public boolean isCellEditable(final int row, final int col) {
        // The whole table is not editable for now.
        return false;
    }

    /**
     * Adds an employee to the data model.
     *
     * @param newEmployee The new employee.
     */
    public void addEmployee(final Employee newEmployee) {
        this.employeeData.add(newEmployee);
        fireTableRowsInserted(getRowCount(), getRowCount());
    }

    /**
     * Removes the employee that resides at the given index.
     *
     * @param rowIndex The index of the row.
     */
    public void removeEmployee(final int rowIndex) {
        this.employeeData.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
}
