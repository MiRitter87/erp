package frontend.controller.employee;

import java.awt.event.ActionEvent;
import java.text.MessageFormat;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import frontend.controller.MainViewController;
import frontend.model.Employee;
import frontend.model.EmployeeList;
import frontend.view.components.EmployeeTableModel;
import frontend.view.employee.EmployeeOverview;

/**
 * Controls all actions directly happening within the employee overview.
 * 
 * @author Michael
 */
public class EmployeeOverviewController extends EmployeeController {	
	/**
	 * The controller of the salary view.
	 */
	private DisplayEmployeeSalaryController employeeSalaryController;
	
	/**
	 * The view for the employee overview.
	 */
	private EmployeeOverview employeeOverview;
	
	/**
	 * The employees that are managed by the employee view.
	 */
	private EmployeeList employees;
	
	/**
	 * Application logging.
	 */
	public static final Logger logger = LogManager.getLogger(EmployeeOverviewController.class);
	
	
	/**
	 * Initializes the controller.
	 * 
	 * @param mainViewController The controller of the main view.
	 */
	public EmployeeOverviewController(final MainViewController mainViewController) {
		super(mainViewController);
		this.employeeOverview = new EmployeeOverview(this);
		this.employees = new EmployeeList();
		
		try {
			this.employees.setEmployees(this.employeeWebServiceDao.getEmployees());
			
			this.initializeTableData();
		}
		catch(Exception exception) {
			JOptionPane.showMessageDialog(this.employeeOverview, exception.getMessage(), 
					this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			logger.info("Error while trying to read employees from WebService: " +exception.getMessage());
		}
	}
	
	
	/**
	 * Validates selection of a distinct employee. 
	 * 
	 * @throws Exception Indicates no distinct employee has been selected.
	 */
	private void validateSelectedEmployee() throws Exception {
		int selectedRowCount;
		
		selectedRowCount = this.employeeOverview.getTableEmployee().getSelectedRowCount();
		if(selectedRowCount != 1)
			throw new Exception(this.resources.getString("gui.employee.error.noEmployeeSelected"));
	}
	
	
	/**
	 * Gets the ID of the selected employee.
	 * 
	 * @return The ID of the selected employee.
	 */
	private Integer getSelectedEmployeeId() {
		int selectedRow = -1;
		
		selectedRow = this.employeeOverview.getTableEmployee().getSelectedRow();
		return Integer.valueOf(this.employeeOverview.getTableEmployee().getModel().getValueAt(selectedRow, 0).toString());
	}
	
	
	/**
	 * Adds an employee to the table for display.
	 */
	private void addEmployeeToTable(final Employee employee) {
		EmployeeTableModel tableModel = (EmployeeTableModel) this.employeeOverview.getTableEmployee().getModel();
		tableModel.addEmployee(employee);
	}
	
	
	/**
	 * Handles a click at the "remove selected employee"-button.
	 * 
	 * @param e The action event of the button click.
	 */
	public void deleteSelectedEmployee(ActionEvent e) {
		Employee employee;
		Integer employeeId;
		
		//Validation of delete action.
		try {
			this.validateSelectedEmployee();
			employeeId = this.getSelectedEmployeeId();
			employee = this.employees.getEmployeeById(employeeId);
			
			//Employee not contained in internal data model. Should never happen but is checked anyway.
			if(employee == null)
				throw new Exception(MessageFormat.format(this.resources.getString("gui.employee.error.notFound"), employeeId));		
		}
		catch(Exception exception) {
			JOptionPane.showMessageDialog(this.employeeOverview, exception.getMessage(), 
					this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			logger.info("User failed to delete employee: " +exception.getMessage());
			return;
		}
		
		//Validation succeeded - Deleting employee from database.
		try {
			this.employeeWebServiceDao.deleteEmployee(employee);
			this.employees.deleteEmployee(employee);
			this.deleteSelectedEmployeeFromTable();
		}
		catch(Exception exception) {
			JOptionPane.showMessageDialog(this.employeeOverview, exception.getMessage(), 
					this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			logger.info("Deleting employee from database failed: " +exception.getMessage());
		}
	}
	
	
	/**
	 * Deletes the selected employee from the view table.
	 * 
	 */
	private void deleteSelectedEmployeeFromTable() {
		int selectedRow = -1;
		
		selectedRow = this.employeeOverview.getTableEmployee().getSelectedRow();
		EmployeeTableModel tableModel = (EmployeeTableModel) this.employeeOverview.getTableEmployee().getModel();
		tableModel.removeEmployee(selectedRow);
	}
	
	
	/**
	 * Initializes the view table with data to be displayed.
	 */
	private void initializeTableData() {
		for(Employee tempEmployee:this.employees.getEmployees())
			this.addEmployeeToTable(tempEmployee);
	}
	
	
	/**
	 * Switches to the view for employee salary data.
	 */
	public void switchToSalaryView() {
		Employee employee;
		Integer employeeId;
		
		try {
			this.validateSelectedEmployee();
			
			employeeId = this.getSelectedEmployeeId();
			employee = this.employees.getEmployeeById(employeeId);
			
			//Employee not contained in internal data model. Should never happen but is checked anyway.
			if(employee == null)
				throw new Exception(MessageFormat.format(this.resources.getString("gui.employee.error.notFound"), employeeId));	
			
			this.employeeSalaryController = new DisplayEmployeeSalaryController(this.mainViewController, employee);
			this.employeeSalaryController.setEmployeeOverviewController(this);
			this.mainViewController.switchToDisplaySalaryView(this.employeeSalaryController);
		}
		catch(Exception exception) {
			JOptionPane.showMessageDialog(this.employeeOverview, exception.getMessage(), 
					this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	
	
	/**
	 * Handles a click at the "cancel"-button.
	 * 
	 * @param cancelEvent The action event of the button click.
	 */
	public void cancelHandler(ActionEvent cancelEvent) {
		this.mainViewController.switchToStartpage();
	}
	
	
	/**
	 * 
	 * @return The EmployeeOverview
	 */
	public EmployeeOverview getEmployeeOverview() {
		return employeeOverview;
	}
}
