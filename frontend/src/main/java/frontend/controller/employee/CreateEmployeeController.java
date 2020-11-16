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
import frontend.view.View;
import frontend.view.employee.CreateEmployeeView;

/**
 * Controller of the "create employee"-view.
 * 
 * @author Michael
 */
public class CreateEmployeeController {
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
		Employee newEmployee = new Employee();
		
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
			newEmployee = this.getEmployeeFromViewInput();
			this.employeeWebServiceDao.insertEmpoyee(newEmployee);
			this.clearInputFields();
		}
		catch(Exception exception) {
			JOptionPane.showMessageDialog(this.createEmployeeView, exception.getMessage(), this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			logger.info("Saving employee failed: " +exception.getMessage());
		}
	}
	
	
	/**
	 * Validates user input and displays an error message if input is not valid.
	 * 
	 * @exception Exception Indicating failed validation.
	 */
	private void validateInput() throws Exception {
		//Check if first name is given
		if(this.createEmployeeView.getTextFieldFirstName().getText().length() == 0) 
			throw new Exception(this.resources.getString("gui.employee.error.firstNameValidation"));
		
		//Check if last name is given
		if(this.createEmployeeView.getTextFieldLastName().getText().length() == 0)
			throw new Exception(this.resources.getString("gui.employee.error.lastNameValidation"));
		
		//Check if a gender is selected
		ComboBoxItem selectedGender = (ComboBoxItem) this.createEmployeeView.getCbGender().getSelectedItem();
		if(selectedGender.getId().equals(""))
			throw new Exception(this.resources.getString("gui.employee.error.genderValidation"));
	}
	
	
	/**
	 * Gets an employee object based on the input fields of the application.
	 * 
	 * @return An employee.
	 */
	private Employee getEmployeeFromViewInput() {
		Employee newEmployee = new Employee();
		
		newEmployee.setFirstName(this.createEmployeeView.getTextFieldFirstName().getText());
		newEmployee.setLastName(this.createEmployeeView.getTextFieldLastName().getText());
		newEmployee.setGender(this.getSelectedGender());
		
		return newEmployee;
	}
	
	
	/**
	 * Gets the gender that is currently selected in the gender selection combo box.
	 * 
	 * @return The currently selected gender.
	 */
	private Gender getSelectedGender() {
		ComboBoxItem selectedGender = (ComboBoxItem) this.createEmployeeView.getCbGender().getSelectedItem();
		
		if(selectedGender.getId() == Gender.FEMALE.toString())
			return Gender.FEMALE;
		else if(selectedGender.getId() == Gender.MALE.toString())
			return Gender.MALE;
		else
			return null;
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
		this.mainViewController.navigateToView(View.STARTPAGE);
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
