package frontend.controller.employee;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import frontend.controller.MainViewController;
import frontend.dao.EmployeeWebServiceDao;
import frontend.model.ComboBoxItem;
import frontend.model.Employee;
import frontend.model.Gender;
import frontend.view.employee.CreateEmployeeView;

/**
 * Controller of the "create employee"-view.
 * 
 * @author Michael
 */
public class CreateEmployeeController extends EmployeeController {
	/**
	 * The controller of the main view.
	 */
	private MainViewController mainViewController;
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources;
	
	/**
	 * The view for employee creation.
	 */
	private CreateEmployeeView createEmployeeView;
	
	/**
	 * Access to employee data using a WebService.
	 */
	private EmployeeWebServiceDao employeeWebServiceDao;
	
	/**
	 * Application logging.
	 */
	public static final Logger logger = LogManager.getLogger(CreateEmployeeController.class);
	
	
	/**
	 * Initializes the controller
	 * 
	 * @param mainViewController The controller of the main view.
	 */
	public CreateEmployeeController(final MainViewController mainViewController) {
		this.createEmployeeView = new CreateEmployeeView(this);
		this.mainViewController = mainViewController;
		this.resources = ResourceBundle.getBundle("frontend");
		this.initializeGenderComboBox();
		this.employeeWebServiceDao = new EmployeeWebServiceDao();
	}
	
	
	/**
	 * Provides the labels for the gender selection combo box.
	 */
	private void initializeGenderComboBox() {
		//Add an initial ComboBox entry.
		this.createEmployeeView.getCbGender().addItem(new ComboBoxItem("", ""));
		
		//Add a ComboBox entry for each available gender.
		for(Gender gender:Gender.values()) {
			switch(gender) {
				case FEMALE: {
					this.createEmployeeView.getCbGender().addItem(new ComboBoxItem(gender.toString(), this.resources.getString("gui.employee.gender.female")));
					break;
				}
				case MALE: {
					this.createEmployeeView.getCbGender().addItem(new ComboBoxItem(gender.toString(), this.resources.getString("gui.employee.gender.male")));
					break;
				}
			}
		}
	}
	
	
	/**
	 * Handles a click at the "add employee"-button.
	 * 
	 * @param e The action event of the button click.
	 */
	public void addEmployeeHandler(ActionEvent e) {
		//Validation of user input
		try {					
			this.validateInput();
		}
		catch(Exception exception) {
			JOptionPane.showMessageDialog(this.createEmployeeView, exception.getMessage(), 
					this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			logger.info("User failed to add employee due to a validation error: " +exception.getMessage());
			return;
		}
		
		//Validating succeeded - Try to persist new employee
		try {
			Employee newEmployee = this.getEmployeeFromViewInput();
			this.employeeWebServiceDao.insertEmpoyee(newEmployee);
			this.clearInputFields();
		}
		catch(Exception exception) {
			JOptionPane.showMessageDialog(this.createEmployeeView, exception.getMessage(), this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			logger.info("Saving employee failed: " +exception.getMessage());
		}
	}
	
	
	/**
	 * Validates the user input.
	 * 
	 * @exception Exception Indicating failed validation.
	 */
	private void validateInput() throws Exception {
		ComboBoxItem selectedGender = (ComboBoxItem) this.createEmployeeView.getCbGender().getSelectedItem();

		this.validateInput(this.createEmployeeView.getTextFieldFirstName().getText(), 
				this.createEmployeeView.getTextFieldLastName().getText(), selectedGender);
	}
	
	
	/**
	 * Gets an employee object based on the input fields of the application.
	 * 
	 * @return An employee.
	 */
	private Employee getEmployeeFromViewInput() {
		ComboBoxItem selectedGender = (ComboBoxItem) this.createEmployeeView.getCbGender().getSelectedItem();
		
		Employee newEmployee = new Employee(this.createEmployeeView.getTextFieldFirstName().getText(),
				this.createEmployeeView.getTextFieldLastName().getText(), this.getSelectedGender(selectedGender));
		
		return newEmployee;
	}
	
	
	/**
	 * Clears the input fields of the employee form.
	 */
	private void clearInputFields() {
		this.createEmployeeView.getTextFieldFirstName().setText("");
		this.createEmployeeView.getTextFieldLastName().setText("");
		this.createEmployeeView.getCbGender().setSelectedIndex(0);
	}
	
	
	/**
	 * Handles the click at the cancel-button.
	 * 
	 * @param e The action event of the button click.
	 */
	public void cancelHandler(ActionEvent e) {
		this.mainViewController.switchToStartpage();
	}


	/**
	 * @return the createEmployeeView
	 */
	public CreateEmployeeView getCreateEmployeeView() {
		return createEmployeeView;
	}


	/**
	 * @param createEmployeeView the createEmployeeView to set
	 */
	public void setCreateEmployeeView(CreateEmployeeView createEmployeeView) {
		this.createEmployeeView = createEmployeeView;
	}
}
