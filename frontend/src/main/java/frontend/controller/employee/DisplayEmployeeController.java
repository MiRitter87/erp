package frontend.controller.employee;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import frontend.controller.MainViewController;
import frontend.dao.EmployeeWebServiceDao;
import frontend.model.ComboBoxItem;
import frontend.model.Employee;
import frontend.model.EmployeeList;
import frontend.view.employee.DisplayEmployeeView;

/**
 * Controller of the "display employee"-view.
 * 
 * @author Michael
 */
public class DisplayEmployeeController extends EmployeeController {
	/**
	 * The controller of the main view.
	 */
	private MainViewController mainViewController;
	
	/**
	 * The controller of the salary view.
	 */
	private DisplayEmployeeSalaryController displayEmployeeSalaryController;
	
	/**
	 * The view for employee display.
	 */
	private DisplayEmployeeView displayEmployeeView;
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources;
	
	/**
	 * Access to employee data using a WebService.
	 */
	private EmployeeWebServiceDao employeeWebServiceDao;
	
	/**
	 * The employees of the application. Those are candidates for the "display"-function.
	 */
	private EmployeeList employees;
	
	/**
	 * The currently selected employee for display.
	 */
	private Employee selectedEmployee;
	
	/**
	 * Application logging.
	 */
	public static final Logger logger = LogManager.getLogger(EditEmployeeController.class);

	
	/**
	 * Initializes the controller
	 * 
	 * @param mainViewController The controller of the main view.
	 */
	public DisplayEmployeeController(final MainViewController mainViewController) {
		this.displayEmployeeView = new DisplayEmployeeView(this);
		this.mainViewController = mainViewController;
		this.resources = ResourceBundle.getBundle("frontend");
		this.employeeWebServiceDao = new EmployeeWebServiceDao();
		this.employees = new EmployeeList();
		this.selectedEmployee = null;
		
		//Initialize the employees for the selection.
		try {
			this.employeeWebServiceDao = new EmployeeWebServiceDao();
			this.employees.setEmployees(this.employeeWebServiceDao.getEmployees());
			
			this.initializeEmployeeComboBox();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(this.displayEmployeeView, e.getMessage(), 
					this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			logger.info("Error while trying to read employees from WebService: " +e.getMessage());
		}
	}
	
	
	/**
	 * Initializes the combo box for employee selection.
	 * All employees are being displayed by ID, first name and last name.
	 */
	private void initializeEmployeeComboBox() {
		StringBuilder builder;
		
		//An initial ComboBox entry allowing the user to de-select.
		this.displayEmployeeView.getCbEmployee().addItem(new ComboBoxItem("", ""));
		
		//One ComboBox entry for each employee.
		for(Employee tempEmployee:this.employees.getEmployees()) {
			builder = new StringBuilder();
			builder.append(tempEmployee.getId().toString());
			builder.append(" - ");
			builder.append(tempEmployee.getFirstName());
			builder.append(" ");
			builder.append(tempEmployee.getLastName());
			
			this.displayEmployeeView.getCbEmployee().addItem(new ComboBoxItem(tempEmployee.getId().toString(), builder.toString()));
		}
		
		this.displayEmployeeView.getCbEmployee().setSelectedIndex(0);
	}
	
	
	/**
	 * Handles selections performed at the employee selection combo box.
	 * 
	 * @param itemEvent Indicates ComboBox item changed.
	 */
	public void cbEmployeeItemStateChanged(ItemEvent itemEvent) {
	    if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
	    	ComboBoxItem selectedItem = (ComboBoxItem) itemEvent.getItem();
	        
	    	//Selection of empty employee: Clear input fields
	    	if(selectedItem.getId() == "") {
	    		this.selectedEmployee = null;
	    		this.displayEmployeeView.getLblFirstNameContent().setText("");
	    		this.displayEmployeeView.getLblLastNameContent().setText("");
	    		this.displayEmployeeView.getLblGenderContent().setText("");
	    	}
	    	else {
	    		//Employee selected: Fill input fields accordingly.
	    		this.selectedEmployee = this.employees.getEmployeeById(Integer.valueOf(selectedItem.getId()));
	    		
	    		if(this.selectedEmployee != null) {
	    			this.displayEmployeeView.getLblFirstNameContent().setText(this.selectedEmployee.getFirstName());
	    			this.displayEmployeeView.getLblLastNameContent().setText(this.selectedEmployee.getLastName());
	    			this.displayEmployeeView.getLblGenderContent().setText(this.getGenderText(this.selectedEmployee.getGender()));
	    		}
	    	}
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
	 * Handles a click at the "salary data"-button.
	 * 
	 * @param salaryDataButtonClick The action event of the button click.
	 */
	public void btnSalaryDataHandler(ActionEvent salaryDataButtonClick) {
		if(this.selectedEmployee == null) {
			JOptionPane.showMessageDialog(this.displayEmployeeView, this.resources.getString("gui.employee.error.noEmployeeSelected"), 
					this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
		}
		
		this.displayEmployeeSalaryController = new DisplayEmployeeSalaryController(this.mainViewController, this.selectedEmployee);
		this.displayEmployeeSalaryController.setDisplayEmployeeController(this);
		this.mainViewController.switchToDisplaySalaryView(this.displayEmployeeSalaryController);
	}
	
	
	/**
	 * @return the displayEmployeeView
	 */
	public DisplayEmployeeView getDisplayEmployeeView() {
		return displayEmployeeView;
	}
}
