package frontend.view.components;

import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.table.AbstractTableModel;

import frontend.model.Department;

/**
 * Table model of an department.
 * 
 * @author Michael
 */
public class DepartmentTableModel extends AbstractTableModel {
	/**
	 * Default serialization - not needed
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Column names being displayed.
	 */
	private ArrayList<String> columnNames = new ArrayList<String>();
	
	/**
     * Table content.
     */
	private ArrayList<Department> departmentData = new ArrayList<Department>();
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources;
	
	
	/**
	 * Initialization.
	 */
	public DepartmentTableModel() {
		this.resources = ResourceBundle.getBundle("frontend");
		
		this.columnNames.add(this.resources.getString("gui.dept.code"));
		this.columnNames.add(this.resources.getString("gui.dept.name"));
		this.columnNames.add(this.resources.getString("gui.dept.description"));
		this.columnNames.add(this.resources.getString("gui.dept.head"));
	}

	
	@Override
	public int getRowCount() {
		return this.departmentData.size();
	}

	
	@Override
	public int getColumnCount() {
		return this.columnNames.size();
	}
	
	
	@Override
	public String getColumnName(int col) {
		return this.columnNames.get(col);
	}
	

	/*
	 * Gets table cell content for a given row and column.
	 * The content is extracted from the custom data model.
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Department department = this.departmentData.get(rowIndex);
		
		switch(columnIndex) {
			case 0:
				return department.getCode();
			case 1:
				return department.getName();
			case 2:
				return department.getDescription();
			case 3:
				return department.getHead().getFullName();
		}
		
		return null;
	}
	
	
	@Override
	public boolean isCellEditable(int row, int col) {
		//The whole table is not editable for now.
		return false;
	}
	
	
	/**
	 * Adds a department to the data model.
	 * 
	 * @param newDepartment The new department.
	 */
	public void addDepartment(final Department newDepartment) {
		this.departmentData.add(newDepartment);
		fireTableRowsInserted(getRowCount(), getRowCount());
	}
	
	
	/**
	 * Removes the department that resides at the given index.
	 * 
	 * @param rowIndex The index of the row.
	 */
	public void removeDepartment(final int rowIndex) {
		this.departmentData.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
	}
}
