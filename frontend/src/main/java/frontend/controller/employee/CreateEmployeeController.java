package frontend.controller.employee;

import java.awt.event.ActionEvent;
import java.text.MessageFormat;
import java.util.List;

import javax.swing.JOptionPane;
import javax.xml.ws.WebServiceException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import frontend.controller.MainViewController;
import frontend.model.ComboBoxItem;
import frontend.model.Employee;
import frontend.view.employee.CreateEmployeeView;

/**
 * Controller of the "create employee"-view.
 * 
 * @author Michael
 */
public class CreateEmployeeController extends EmployeeController {
		/**
	 * The view for employee creation.
	 */
	private CreateEmployeeView createEmployeeView;
	
	/**
	 * Application logging.
	 */
	public static final Logger logger = LogManager.getLogger(CreateEmployeeController.class);
	
	
	/**
	 * Initializes the controller
	 * 
	 * @param mainViewController The controller of the main view.
	 * @throws WebServiceException In case the WebService is unavailable.
	 */
	public CreateEmployeeController(final MainViewController mainViewController) throws WebServiceException {
		super(mainViewController);
		this.createEmployeeView = new CreateEmployeeView(this);
		this.initializeGenderComboBox();
	}
	
	
	/**
	 * Provides the labels for the gender selection combo box.
	 */
	private void initializeGenderComboBox() {
		List<ComboBoxItem> items = this.getGenderItemsForComboBox();
		
		for(ComboBoxItem item:items) {
			this.createEmployeeView.getCbGender().addItem(item);
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
			logger.debug("User failed to add employee due to a validation error: " +exception.getMessage());
			return;
		}
		
		//Validating succeeded - Try to persist new employee
		try {
			Employee newEmployee = this.getEmployeeFromViewInput();
			this.employeeWebServiceDao.insertEmpoyee(newEmployee);
			this.clearInputFields();
			this.displaySaveSuccessPopUp(newEmployee.getFullName());
		}
		catch(Exception exception) {
			JOptionPane.showMessageDialog(this.createEmployeeView, exception.getMessage(), this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			logger.debug("Saving employee failed: " +exception.getMessage());
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
	 * Displays a PopUp informing the user that the save operation has been performed successfully.
	 * 
	 * @param fullName The full name of the employee.
	 */
	private void displaySaveSuccessPopUp(final String fullName) {
		JOptionPane.showMessageDialog(this.createEmployeeView, 
				MessageFormat.format(this.resources.getString("gui.employee.information.createSuccess"), fullName),
				this.resources.getString("gui.information"), 
				JOptionPane.INFORMATION_MESSAGE);
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
