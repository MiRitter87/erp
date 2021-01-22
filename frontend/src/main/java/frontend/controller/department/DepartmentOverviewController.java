package frontend.controller.department;

import java.awt.event.ActionEvent;
import java.text.MessageFormat;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import frontend.controller.MainViewController;
import frontend.model.Department;
import frontend.model.DepartmentList;
import frontend.view.components.DepartmentTableModel;
import frontend.view.department.DepartmentOverview;

/**
 * Controls all actions directly happening within the DepartmentOverview.
 * 
 * @author Michael
 */
public class DepartmentOverviewController extends DepartmentController {
	
	/**
	 * The view for department management.
	 */
	private DepartmentOverview departmentOverview;
	
	/**
	 * Application logging.
	 */
	public static final Logger logger = LogManager.getLogger(DepartmentOverviewController.class);
	
	/**
	 * The departments that are managed by the department view.
	 */
	private DepartmentList departments;
	
	
	/**
	 * Initializes the DepartmentController.
	 * 
	 * @param mainViewController The controller of the main view.
	 */
	public DepartmentOverviewController(final MainViewController mainViewController) {
		super(mainViewController);
		this.departmentOverview = new DepartmentOverview(this);
		this.departments = new DepartmentList();
		
		//Initialization of department data.
		try {
			this.departments.setDepartments(this.departmentWebServiceDao.getDepartments());
			this.initializeTableData();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(this.departmentOverview, e.getMessage(), 
					this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			logger.info("Error while trying to read departments from WebService: " +e.getMessage());
		}
	}
	
	
	/**
	 * Handles a click at the "remove selected department"-button.
	 * 
	 * @param e The action event of the button click.
	 */
	public void deleteSelectedDepartment(ActionEvent e) {
		Department department;
		String departmentCode;
		
		//Validation of delete action
		try {
			this.validateSelectedDepartment();
			departmentCode = this.getSelectedDepartmentCode();
			department = this.departments.getDepartmentByCode(departmentCode);
			
			//Department not contained in internal data model. Should never happen but is checked anyway.
			if(department == null)
				throw new Exception(MessageFormat.format(this.resources.getString("gui.dept.error.notFound"), departmentCode));	
		}
		catch(Exception exception) {
			JOptionPane.showMessageDialog(this.departmentOverview, exception.getMessage(), 
					this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			logger.info("User failed to delete department: " +exception.getMessage());
			return;
		}
		
		//Validation succeeded - Deleting department from database.
		try {
			this.departmentWebServiceDao.deleteDepartment(department);
			this.departments.deleteDepartment(department);
			this.deleteSelectedDepartmentFromTable();
		}
		catch(Exception exception) {
			JOptionPane.showMessageDialog(this.departmentOverview, exception.getMessage(), 
					this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			logger.info("Deleting department from database failed: " +exception.getMessage());
		}
	}
	
	
	/**
	 * Deletes the selected department from the view table.
	 * 
	 */
	private void deleteSelectedDepartmentFromTable() {
		int selectedRow = -1;
		
		selectedRow = this.departmentOverview.getTableDepartment().getSelectedRow();
		DepartmentTableModel tableModel = (DepartmentTableModel) this.departmentOverview.getTableDepartment().getModel();
		tableModel.removeDepartment(selectedRow);
	}
	
	
	/**
	 * Validates selection of a distinct department. 
	 * 
	 * @throws Exception Indicates no distinct department has been selected.
	 */
	private void validateSelectedDepartment() throws Exception {
		int selectedRowCount;
		
		selectedRowCount = this.departmentOverview.getTableDepartment().getSelectedRowCount();
		if(selectedRowCount != 1)
			throw new Exception(this.resources.getString("gui.dept.error.noDepartmentSelected"));
	}
	
	
	/**
	 * Gets the code of the selected department.
	 * 
	 * @return The code of the selected department.
	 */
	private String getSelectedDepartmentCode() {
		int selectedRow = -1;
		
		selectedRow = this.departmentOverview.getTableDepartment().getSelectedRow();
		return this.departmentOverview.getTableDepartment().getModel().getValueAt(selectedRow, 0).toString();
	}
	
	
	/**
	 * Initializes the view table with data to be displayed.
	 */
	private void initializeTableData() {
		for(Department tempDepartment:this.departments.getDepartments())
			this.addDepartmentToTable(tempDepartment);
	}
	
	
	/**
	 * Adds a department to the table for display.
	 */
	private void addDepartmentToTable(final Department department) {
		DepartmentTableModel tableModel = (DepartmentTableModel) this.departmentOverview.getTableDepartment().getModel();
		tableModel.addDepartment(department);
	}
	
	
	/**
	 * Handles the click at the cancel button in the department overview.
	 * 
	 * @param cancelEvent Event indicating cancel button clicked.
	 */
	public void cancelHandler(ActionEvent cancelEvent) {
		this.mainViewController.switchToStartpage();
	}

	
	/**
	 * @return The DepartmentOverview.
	 */
	public DepartmentOverview getDepartmentOverview() {
		return departmentOverview;
	}
}
