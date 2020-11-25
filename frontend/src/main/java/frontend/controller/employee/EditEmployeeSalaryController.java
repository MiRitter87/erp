package frontend.controller.employee;

import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import frontend.controller.MainViewController;
import frontend.dao.EmployeeWebServiceDao;
import frontend.model.Employee;
import frontend.model.EmployeeSalary;
import frontend.view.employee.EditEmployeeSalaryView;

/**
 * Controls all actions directly happening within the edit employee salary view.
 * 
 * @author Michael
 */
public class EditEmployeeSalaryController {
	/**
	 * The controller of the main view.
	 */
	private MainViewController mainViewController;
	
	/**
	 * The view for employee salary management.
	 */
	private EditEmployeeSalaryView editEmployeeSalaryView;
	
	/**
	 * The employee whose salary data are being edited.
	 */
	private Employee selectedEmployee;
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources;
	
	/**
	 * Access to employee data using a WebService.
	 */
	private EmployeeWebServiceDao employeeWebServiceDao;
	
	/**
	 * The controller of the employee overview.
	 */
	private EmployeeOverviewController employeeOverviewController;
	
	/**
	 * The controller of the edit employee view.
	 */
	private EditEmployeeController editEmployeeController;
	
	/**
	 * Application logging.
	 */
	public static final Logger logger = LogManager.getLogger(EditEmployeeSalaryController.class);

	
	/**
	 * Initializes the controller.
	 * 
	 * @param mainViewController The controller of the main view.
	 * @param selectedEmployee The employee that has been selected to show the salary data.
	 * @param employeeWebServiceDao The WebService DAO for employee access.
	 */
	public EditEmployeeSalaryController(final MainViewController mainViewController, final Employee selectedEmployee,
			final EmployeeWebServiceDao employeeWebServiceDao) {
		
		this.selectedEmployee = selectedEmployee;
		this.mainViewController = mainViewController;
		this.editEmployeeSalaryView = new EditEmployeeSalaryView(this);
		this.resources = ResourceBundle.getBundle("frontend");
		this.employeeWebServiceDao = employeeWebServiceDao;
		
		//Initially the controller of the potentially calling views are set to null.
		//The calling controller has to be explicitly set afterwards.
		//TODO Refactoring: The edit salary function can only be called from the edit employee view and not from the employee overview.
		this.editEmployeeController = null;
		this.employeeOverviewController = null;
		
		this.initializeViewData();
	}
	
	
	/**
	 * Validates the user input of salary data.
	 * 
	 * @exception Exception Indicating failed validation.
	 */
	private void validateInput() throws Exception {
		//Check if salary is not empty
		if("".equals(this.editEmployeeSalaryView.getTextFieldSalary().getText())) {
			throw new Exception(this.resources.getString("gui.employee.error.salaryEmpty"));
		}
		
		//Check if salary contains only numbers
		try {
			Integer.parseInt(this.editEmployeeSalaryView.getTextFieldSalary().getText());
		}
		catch(NumberFormatException exception) {
			throw new Exception(this.resources.getString("gui.employee.error.salaryNotNumeric"));
		}
	}
	
	
	/**
	 * Handles a click at the "save"-button.
	 * 
	 * @param e The action event of the button click.
	 */
	public void saveSalaryHandler(ActionEvent e) {
		//Validation of user input
		try {					
			this.validateInput();
		}
		catch(Exception exception) {
			JOptionPane.showMessageDialog(this.editEmployeeSalaryView, exception.getMessage(), 
					this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		//Validation successful - Update existing salary and check if anything has changed.
		if(this.selectedEmployee.getSalaryData() != null) {
			int salary = Integer.parseInt(this.editEmployeeSalaryView.getTextFieldSalary().getText());
			this.selectedEmployee.getSalaryData().setMonthlySalary(salary);
			
			if(this.selectedEmployee.getSalaryData().isEdited() == false) {
				JOptionPane.showMessageDialog(this.editEmployeeSalaryView,this.resources.getString("gui.employee.information.salaryNotChanged"), 
						this.resources.getString("gui.information"), JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		
		//Validation successful - No salary exists yet. Create new one.
		if(this.selectedEmployee.getSalaryData() == null) {
			//No salary exists yet
			int salary = Integer.parseInt(this.editEmployeeSalaryView.getTextFieldSalary().getText());
			this.selectedEmployee.setSalaryData(new EmployeeSalary(this.selectedEmployee.getId(), salary));
		}

		//Changes exist - Try to persist those changes.
		try {
			this.employeeWebServiceDao.updateEmployee(this.selectedEmployee);
			this.initializeViewData();		//Update view to show the new change date
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(this.editEmployeeSalaryView, this.resources.getString("gui.employee.error.updateSalary"), 
					this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			logger.error("Updating salary data of employee failed: " +e1.getMessage());
		}

	}
	
	
	/**
	 * Handles a click at the "back"-button of the employee salary view.
	 * 
	 * @param e The action event of the button click.
	 */
	public void btnBackHandler(ActionEvent e) {	
		if(this.employeeOverviewController != null && this.editEmployeeController == null) {
			this.mainViewController.switchToEmployeeOverview(this.employeeOverviewController);
		}
		
		if(this.employeeOverviewController == null && this.editEmployeeController != null) {
			this.mainViewController.switchToEditEmployeeView(this.editEmployeeController);
		}
	}
	
	
	/**
	 * Initializes the display elements of the view.
	 */
	public void initializeViewData() {
		EmployeeSalary salary = this.selectedEmployee.getSalaryData();
		
		if(salary == null)
			return;	
		
		this.editEmployeeSalaryView.getTextFieldSalary().setText(String.valueOf(salary.getMonthlySalary()));
		this.editEmployeeSalaryView.getLblLastChangeValue().setText(DateFormat.getInstance().format(salary.getSalaryLastChange()));
	}
	
	
	public EditEmployeeSalaryView getEditEmployeeSalaryView() {
		return editEmployeeSalaryView;
	}
	
	
	public void setEmployeeSalaryView(EditEmployeeSalaryView employeeSalaryView) {
		this.editEmployeeSalaryView = employeeSalaryView;
	}


	public Employee getSelectedEmployee() {
		return selectedEmployee;
	}


	public void setSelectedEmployee(Employee selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}

	
	/**
	 * @param employeeOverviewController the employeeOverviewController to set
	 */
	public void setEmployeeOverviewController(EmployeeOverviewController employeeOverviewController) {
		this.employeeOverviewController = employeeOverviewController;
	}


	/**
	 * @param editEmployeeController the editEmployeeController to set
	 */
	public void setEditEmployeeController(EditEmployeeController editEmployeeController) {
		this.editEmployeeController = editEmployeeController;
	}
}
