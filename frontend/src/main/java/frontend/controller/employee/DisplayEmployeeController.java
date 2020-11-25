package frontend.controller.employee;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.List;

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
	 * The controller of the salary view.
	 */
	private DisplayEmployeeSalaryController displayEmployeeSalaryController;
	
	/**
	 * The view for employee display.
	 */
	private DisplayEmployeeView displayEmployeeView;
	
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
	public static final Logger logger = LogManager.getLogger(DisplayEmployeeController.class);

	
	/**
	 * Initializes the controller
	 * 
	 * @param mainViewController The controller of the main view.
	 */
	public DisplayEmployeeController(final MainViewController mainViewController) {
		super(mainViewController);
		this.displayEmployeeView = new DisplayEmployeeView(this);
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
		List<ComboBoxItem> items = this.getEmployeeItemsForComboBox(this.employees);
		
		for(ComboBoxItem item:items) {
			this.displayEmployeeView.getCbEmployee().addItem(item);
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
