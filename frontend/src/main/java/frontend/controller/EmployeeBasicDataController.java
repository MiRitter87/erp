package frontend.controller;

import java.awt.event.ActionEvent;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import frontend.controller.employee.EditEmployeeSalaryController;
import frontend.dao.EmployeeWebServiceDao;
import frontend.exception.EntityAlreadyExistsException;
import frontend.model.ComboBoxItem;
import frontend.model.Employee;
import frontend.model.EmployeeList;
import frontend.model.Gender;
import frontend.view.EmployeeBasicDataView;
import frontend.view.components.EmployeeTableModel;

/**
 * Controls all actions directly happening within the EmployeeBasicDataView.
 * 
 * @author Michael
 */
public class EmployeeBasicDataController {
	/**
	 * The controller of the main view.
	 */
	private MainViewController mainViewController;
	
	/**
	 * The controller of the salary view.
	 */
	private EditEmployeeSalaryController employeeSalaryController;
	
	/**
	 * The view for employee basic data management.
	 */
	private EmployeeBasicDataView employeeBasicDataView;
	
	/**
	 * The employees that are managed by the employee view.
	 */
	private EmployeeList employees;
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources;
	
	/**
	 * Application logging.
	 */
	public static final Logger logger = LogManager.getLogger(EmployeeBasicDataController.class);
	
	/**
	 * Access to employee data using a WebService.
	 */
	private EmployeeWebServiceDao employeeWebServiceDao;
	
	
	/**
	 * Initializes the controller.
	 * 
	 * @param mainViewController The controller of the main view.
	 */
	public EmployeeBasicDataController(final MainViewController mainViewController) {
		this.employeeBasicDataView = new EmployeeBasicDataView(this);
		this.mainViewController = mainViewController;
		this.resources = ResourceBundle.getBundle("frontend");
		this.employees = new EmployeeList();
		
		try {
			this.employeeWebServiceDao = new EmployeeWebServiceDao();
			this.employees.setEmployees(employeeWebServiceDao.getEmployees());
			
			this.initializeTableData();
			this.initializeGenderComboBox();
		}
		catch(Exception exception) {
			JOptionPane.showMessageDialog(this.employeeBasicDataView, exception.getMessage(), 
					this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			logger.info("Error while trying to read employees from WebService: " +exception.getMessage());
		}
	}
	

	/**
	 * Handles a click at the "add employee"-button.
	 * 
	 * @param e The action event of the button click.
	 */
	public void addEmployeeHandler(ActionEvent e) {
		String employeeId = this.employeeBasicDataView.getTextFieldEmployeeId().getText();
		
		//Validation of user input
		try {					
			this.validateInput();
		}
		catch(Exception exception) {
			JOptionPane.showMessageDialog(this.employeeBasicDataView, exception.getMessage(), 
					this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			logger.info("User failed to add employee: " +exception.getMessage());
			return;
		}
		
		//Validating succeeded - Try to persist new employee
		try {
			Employee newEmployee = this.getEmployeeFromViewInput();
			this.employeeWebServiceDao.insertEmpoyee(newEmployee);
			this.clearInputFields();
			this.employees.addEmployee(newEmployee);
			this.addEmployeeToTable(newEmployee);
		}
		catch(EntityAlreadyExistsException alreadyExistsException ) {
			String message = MessageFormat.format(this.resources.getString("gui.employee.error.idExists"), employeeId);
			JOptionPane.showMessageDialog(this.employeeBasicDataView, message, this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			logger.error("Failed to add employee to internal table: ", message);
		}
		catch(Exception exception) {
			JOptionPane.showMessageDialog(this.employeeBasicDataView, exception.getMessage(), this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			logger.info("Adding employee to database failed: " +exception.getMessage());
		}
	}
	
	/**
	 * Validates user input and displays an error message if input is not valid.
	 * 
	 * @exception Exception Indicating failed validation.
	 */
	private void validateInput() throws Exception {
		//Check if the employee ID is given
		if(this.employeeBasicDataView.getTextFieldEmployeeId().getText().length() == 0)
			throw new Exception(this.resources.getString("gui.employee.error.idValidatation"));
		
		//Check if first name is given
		if(this.employeeBasicDataView.getTextFieldFirstName().getText().length() == 0) 
			throw new Exception(this.resources.getString("gui.employee.error.firstNameValidation"));
		
		//Check if last name is given
		if(this.employeeBasicDataView.getTextFieldLastName().getText().length() == 0)
			throw new Exception(this.resources.getString("gui.employee.error.lastNameValidation"));
	}
	
	
	/**
	 * Gets an employee object based on the input fields of the application.
	 * 
	 * @return An employee.
	 */
	private Employee getEmployeeFromViewInput() {
		Employee newEmployee = new Employee(this.employeeBasicDataView.getTextFieldFirstName().getText(), 
				this.employeeBasicDataView.getTextFieldLastName().getText(), this.getSelectedGender());
		
		return newEmployee;
	}
	
	
	/**
	 * Validates selection of a distinct employee. 
	 * 
	 * @throws Exception Indicates no distinct employee has been selected.
	 */
	private void validateSelectedEmployee() throws Exception {
		int selectedRowCount;
		
		selectedRowCount = this.employeeBasicDataView.getTableEmployee().getSelectedRowCount();
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
		
		selectedRow = this.employeeBasicDataView.getTableEmployee().getSelectedRow();
		return Integer.valueOf(this.employeeBasicDataView.getTableEmployee().getModel().getValueAt(selectedRow, 0).toString());
	}
	
	
	/**
	 * Gets the gender that is currently selected in the gender selection combo box.
	 * 
	 * @return The currently selected gender.
	 */
	private Gender getSelectedGender() {
		ComboBoxItem selectedGender = (ComboBoxItem) this.employeeBasicDataView.getCbGender().getSelectedItem();
		
		if(selectedGender.getId() == Gender.FEMALE.toString())
			return Gender.FEMALE;
		else
			return Gender.MALE;
	}
	
	
	/**
	 * Clears the input fields of the employee form.
	 */
	private void clearInputFields() {
		this.employeeBasicDataView.getTextFieldEmployeeId().setText("");
		this.employeeBasicDataView.getTextFieldFirstName().setText("");
		this.employeeBasicDataView.getTextFieldLastName().setText("");
	}
	
	
	/**
	 * Adds an employee to the table for display.
	 */
	private void addEmployeeToTable(final Employee employee) {
		EmployeeTableModel tableModel = (EmployeeTableModel) this.employeeBasicDataView.getTableEmployee().getModel();
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
			JOptionPane.showMessageDialog(this.employeeBasicDataView, exception.getMessage(), 
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
			JOptionPane.showMessageDialog(this.employeeBasicDataView, exception.getMessage(), 
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
		
		selectedRow = this.employeeBasicDataView.getTableEmployee().getSelectedRow();
		EmployeeTableModel tableModel = (EmployeeTableModel) this.employeeBasicDataView.getTableEmployee().getModel();
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
	 * Provides the labels for the gender selection combo box.
	 */
	private void initializeGenderComboBox() {
		for(Gender gender:Gender.values()) {
			switch(gender) {
				case FEMALE: {
					this.employeeBasicDataView.getCbGender().addItem(new ComboBoxItem(gender.toString(), this.resources.getString("gui.employee.gender.female")));
					break;
				}
				case MALE: {
					this.employeeBasicDataView.getCbGender().addItem(new ComboBoxItem(gender.toString(), this.resources.getString("gui.employee.gender.male")));
					break;
				}
			}
		}
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
			
			this.employeeSalaryController = new EditEmployeeSalaryController(this.mainViewController, employee, this.employeeWebServiceDao);
			this.employeeSalaryController.setEmployeeOverviewController(this);
			this.mainViewController.switchToEditSalaryView(this.employeeSalaryController);
		}
		catch(Exception exception) {
			JOptionPane.showMessageDialog(this.employeeBasicDataView, exception.getMessage(), 
					this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	
	
	public EmployeeBasicDataView getEmployeeBasicDataView() {
		return employeeBasicDataView;
	}


	public void setEmployeeBasicDataView(EmployeeBasicDataView employeeBasicDataView) {
		this.employeeBasicDataView = employeeBasicDataView;
	}


	public EditEmployeeSalaryController getEmployeeSalaryController() {
		return employeeSalaryController;
	}


	public void setEmployeeSalaryController(EditEmployeeSalaryController employeeSalaryController) {
		this.employeeSalaryController = employeeSalaryController;
	}
}
