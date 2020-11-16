package frontend.controller;

import java.awt.event.ActionEvent;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import frontend.dao.DepartmentWebServiceDao;
import frontend.dao.EmployeeWebServiceDao;
import frontend.exception.EntityAlreadyExistsException;
import frontend.model.ComboBoxItem;
import frontend.model.Department;
import frontend.model.DepartmentList;
import frontend.model.Employee;
import frontend.model.EmployeeList;
import frontend.view.DepartmentView;
import frontend.view.components.DepartmentTableModel;

/**
 * Controls all actions directly happening within the DepartmentView.
 * 
 * @author Michael
 */
public class DepartmentController {
	
	/**
	 * The view for department management.
	 */
	private DepartmentView departmentView;
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources;
	
	/**
	 * Application logging.
	 */
	public static final Logger logger = LogManager.getLogger(DepartmentController.class);
	
	/**
	 * The departments that are managed by the department view.
	 */
	private DepartmentList departments;
	
	/**
	 * The employees that are managed by the employee view. Those are candidates for the "head of department" ComboBox.
	 */
	private EmployeeList employees;
	
	/**
	 * Access to employee data using a WebService.
	 */
	private EmployeeWebServiceDao employeeWebServiceDao;
	
	/**
	 * Access to department data using a WebService.
	 */
	private DepartmentWebServiceDao departmentWebServiceDao;
	
	
	/**
	 * Initializes the DepartmentController.
	 */
	public DepartmentController() {
		this.departmentView = new DepartmentView(this);
		this.resources = ResourceBundle.getBundle("frontend");
		this.employees = new EmployeeList();
		this.departments = new DepartmentList();
		
		//Initialization of employee data for head selection combo box.
		try {
			this.employeeWebServiceDao = new EmployeeWebServiceDao();
			this.employees.setEmployees(this.employeeWebServiceDao.getEmployees());
			
			this.initializeHeadComboBox();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(this.departmentView, e.getMessage(), 
					this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			logger.info("Error while trying to read employees from WebService: " +e.getMessage());
		}
		
		//Initialization of department data.
		try {
			this.departmentWebServiceDao = new DepartmentWebServiceDao();
			this.departments.setDepartments(this.departmentWebServiceDao.getDepartments());
			this.initializeTableData();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(this.departmentView, e.getMessage(), 
					this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			logger.info("Error while trying to read departments from WebService: " +e.getMessage());
		}
	}
	
	
	/**
	 * Handles a click at the "add department"-button.
	 * 
	 * @param e The action event of the button click.
	 */
	public void addDepartmentHandler(ActionEvent e) {
		Department newDepartment = new Department();
		
		//Validation of user input
		try {
			this.validateInput();
			newDepartment = this.getDepartmentFromViewInput();
		}
		catch(Exception exception) {
			JOptionPane.showMessageDialog(this.departmentView, exception.getMessage(), 
					this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			logger.info("User failed to add department: " +exception.getMessage());
			return;
		}
		
		//Validating succeeded - Try to persist new department
		try {
			this.departmentWebServiceDao = new DepartmentWebServiceDao();
			this.departmentWebServiceDao.insertDepartment(newDepartment);
			this.clearInputFields();
			this.departments.addDepartment(newDepartment);
			this.addDepartmentToTable(newDepartment);
		}
		catch(EntityAlreadyExistsException alreadyExistsException) {
			String message = MessageFormat.format(this.resources.getString("gui.dept.error.codeExists"), newDepartment.getCode());
			JOptionPane.showMessageDialog(this.departmentView, message, this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			logger.error("Failed to add department to internal table: ", message);
		}
		catch(Exception exception) {
			JOptionPane.showMessageDialog(this.departmentView, exception.getMessage(), this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			logger.info("Adding employee to database failed: " +exception.getMessage());
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
			JOptionPane.showMessageDialog(this.departmentView, exception.getMessage(), 
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
			JOptionPane.showMessageDialog(this.departmentView, exception.getMessage(), 
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
		
		selectedRow = this.departmentView.getTableDepartment().getSelectedRow();
		DepartmentTableModel tableModel = (DepartmentTableModel) this.departmentView.getTableDepartment().getModel();
		tableModel.removeDepartment(selectedRow);
	}
	
	
	/**
	 * Validates selection of a distinct department. 
	 * 
	 * @throws Exception Indicates no distinct department has been selected.
	 */
	private void validateSelectedDepartment() throws Exception {
		int selectedRowCount;
		
		selectedRowCount = this.departmentView.getTableDepartment().getSelectedRowCount();
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
		
		selectedRow = this.departmentView.getTableDepartment().getSelectedRow();
		return this.departmentView.getTableDepartment().getModel().getValueAt(selectedRow, 0).toString();
	}
	
	
	/**
	 * Initializes the ComboBox for head of department selection using first and last name of each employee.
	 */
	private void initializeHeadComboBox() {
		for(Employee tempEmployee:this.employees.getEmployees()) {
			String fullName = tempEmployee.getFirstName() + " " + tempEmployee.getLastName();
			this.departmentView.getCbHead().addItem(new ComboBoxItem(tempEmployee.getId().toString(), fullName));
		}
		
		this.departmentView.getCbHead().setSelectedIndex(-1);
	}
	
	
	/**
	 * Validates user input and displays an error message if input is not valid.
	 * 
	 * @exception Exception Indicating failed validation.
	 */
	private void validateInput() throws Exception {
		//A department code has to be given
		if(this.departmentView.getTextFieldDeptCode().getText().length() == 0)
			throw new Exception(this.resources.getString("gui.dept.error.codeValidation"));
		
		//A department name has to be given
		if(this.departmentView.getTextFieldName().getText().length() == 0)
			throw new Exception(this.resources.getString("gui.dept.error.nameValidation"));
		
		//A head of department has to be selected
		if(this.departmentView.getCbHead().getSelectedIndex() == -1)
			throw new Exception(this.resources.getString("gui.dept.error.noHeadSelected"));
	}
	
	
	/**
	 * Gets a department object based on the input fields of the application.
	 * Requires a previously validated input.
	 * 
	 * @return A department.
	 */
	private Department getDepartmentFromViewInput() {
		Department newDepartment = new Department();
		ComboBoxItem selectedHead;
		
		newDepartment.setCode(this.getDepartmentView().getTextFieldDeptCode().getText());
		newDepartment.setName(this.getDepartmentView().getTextFieldName().getText());
		newDepartment.setDescription(this.getDepartmentView().getTextAreaDescription().getText());
		
		selectedHead = (ComboBoxItem) this.getDepartmentView().getCbHead().getSelectedItem();
		newDepartment.setHead(this.employees.getEmployeeById(Integer.valueOf(selectedHead.getId())));
		
		return newDepartment;
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
		DepartmentTableModel tableModel = (DepartmentTableModel) this.departmentView.getTableDepartment().getModel();
		tableModel.addDepartment(department);
	}
	
	
	/**
	 * Clears the input fields of the department form.
	 */
	private void clearInputFields() {
		this.departmentView.getTextFieldDeptCode().setText("");
		this.departmentView.getTextFieldName().setText("");
		this.departmentView.getTextAreaDescription().setText("");
		this.departmentView.getCbHead().setSelectedIndex(-1);
	}

	
	public DepartmentView getDepartmentView() {
		return departmentView;
	}

	
	public void setDepartmentView(DepartmentView departmentView) {
		this.departmentView = departmentView;
	}
}
