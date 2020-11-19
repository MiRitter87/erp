package frontend.controller.employee;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import frontend.controller.MainViewController;
import frontend.dao.EmployeeWebServiceDao;
import frontend.exception.DataNotChangedException;
import frontend.model.ComboBoxItem;
import frontend.model.Employee;
import frontend.model.EmployeeList;
import frontend.model.Gender;
import frontend.view.employee.EditEmployeeView;

/**
 * Controller of the "change employee"-view.
 * 
 * @author Michael
 */
public class EditEmployeeController extends EmployeeController {
	/**
	 * The controller of the main view.
	 */
	private MainViewController mainViewController;
	
	/**
	 * The controller of the salary view.
	 */
	private EmployeeSalaryController employeeSalaryController;
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources;
	
	/**
	 * The view for employee edit.
	 */
	private EditEmployeeView editEmployeeView;
	
	/**
	 * Access to employee data using a WebService.
	 */
	private EmployeeWebServiceDao employeeWebServiceDao;
	
	/**
	 * The employees of the application. Those are candidates for the "edit"-function.
	 */
	private EmployeeList employees;
	
	/**
	 * Application logging.
	 */
	public static final Logger logger = LogManager.getLogger(EditEmployeeController.class);
	
	/**
	 * The currently selected employee for editing.
	 */
	private Employee selectedEmployee;

	/**
	 * Initializes the controller
	 * 
	 * @param mainViewController The controller of the main view.
	 */
	public EditEmployeeController(final MainViewController mainViewController) {
		this.editEmployeeView = new EditEmployeeView(this);
		this.mainViewController = mainViewController;
		this.resources = ResourceBundle.getBundle("frontend");
		this.employeeWebServiceDao = new EmployeeWebServiceDao();
		this.employees = new EmployeeList();
		this.initializeGenderComboBox();
		this.selectedEmployee = null;
		
		//Initialize the employees for the selection.
		try {
			this.employeeWebServiceDao = new EmployeeWebServiceDao();
			this.employees.setEmployees(this.employeeWebServiceDao.getEmployees());
			
			this.initializeEmployeeComboBox();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(this.editEmployeeView, e.getMessage(), 
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
		this.editEmployeeView.getCbEmployee().addItem(new ComboBoxItem("", ""));
		
		//One ComboBox entry for each employee.
		for(Employee tempEmployee:this.employees.getEmployees()) {
			builder = new StringBuilder();
			builder.append(tempEmployee.getId().toString());
			builder.append(" - ");
			builder.append(tempEmployee.getFirstName());
			builder.append(" ");
			builder.append(tempEmployee.getLastName());
			
			this.editEmployeeView.getCbEmployee().addItem(new ComboBoxItem(tempEmployee.getId().toString(), builder.toString()));
		}
		
		this.editEmployeeView.getCbEmployee().setSelectedIndex(0);
	}
	
	
	/**
	 * Provides the labels for the gender selection combo box.
	 */
	private void initializeGenderComboBox() {
		//Add an initial ComboBox entry.
		this.editEmployeeView.getCbGender().addItem(new ComboBoxItem("", ""));
		
		//Add a ComboBox entry for each available gender.
		for(Gender gender:Gender.values()) {
			switch(gender) {
				case FEMALE: {
					this.editEmployeeView.getCbGender().addItem(new ComboBoxItem(gender.toString(), this.resources.getString("gui.employee.gender.female")));
					break;
				}
				case MALE: {
					this.editEmployeeView.getCbGender().addItem(new ComboBoxItem(gender.toString(), this.resources.getString("gui.employee.gender.male")));
					break;
				}
			}
		}
	}
	
	
	/**
	 * Handles a click at the "save employee"-button.
	 * 
	 * @param saveEvent The action event of the button click.
	 */
	public void saveEmployeeHandler(ActionEvent saveEvent) {
		//Validation of user input
		try {					
			this.validateInput();
		}
		catch(DataNotChangedException notChangedException) {
			JOptionPane.showMessageDialog(this.editEmployeeView, this.resources.getString("gui.employee.error.notEdited"), 
					this.resources.getString("gui.information"), JOptionPane.INFORMATION_MESSAGE);
			logger.info("User tried to save an employee without any changes made.");
			return;
		}
		catch(Exception exception) {
			JOptionPane.showMessageDialog(this.editEmployeeView, exception.getMessage(), 
					this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			logger.info("User failed to edit employee due to a validation error: " +exception.getMessage());
			return;
		}
		
		//Validating succeeded - Try to persist changes
		try {
			this.updateEmployeeFromViewInput();
			this.employeeWebServiceDao.updateEmployee(this.selectedEmployee);
			this.clearInputFields();
			this.selectedEmployee = null;
			
			//The combo box for employee selection needs to be re-initialized in order to show the changes.
			this.editEmployeeView.getCbEmployee().removeAllItems();
			this.initializeEmployeeComboBox();
		}
		catch(Exception exception) {
			JOptionPane.showMessageDialog(this.editEmployeeView, exception.getMessage(), this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			logger.info("Updating employee failed: " +exception.getMessage());
		}
	}
	
	
	/**
	 * Updates the data of the currently selected employee with the data from the input fields.
	 */
	private void updateEmployeeFromViewInput() {
		ComboBoxItem selectedGender = (ComboBoxItem) this.editEmployeeView.getCbGender().getSelectedItem();
		
		this.selectedEmployee.setFirstName(this.editEmployeeView.getTextFieldFirstName().getText());
		this.selectedEmployee.setLastName(this.editEmployeeView.getTextFieldLastName().getText());
		this.selectedEmployee.setGender(this.getSelectedGender(selectedGender));
	}


	/**
	 * Validates the user input.
	 * 
	 * @exception Exception Indicating failed validation.
	 */
	private void validateInput() throws Exception {
		ComboBoxItem selectedGender = (ComboBoxItem) this.editEmployeeView.getCbGender().getSelectedItem();
		
		//Assure that an employee to be edited is selected in the ComboBox.		
		if(this.selectedEmployee == null) {
			throw new Exception(this.resources.getString("gui.employee.error.noEmployeeSelected"));
		}
		
		//Validation of input fields.
		this.validateInput(this.editEmployeeView.getTextFieldFirstName().getText(), 
				this.editEmployeeView.getTextFieldLastName().getText(), selectedGender);
		
		//Check if anything has changed.
		if(!this.selectedEmployee.isEdited())
			throw new DataNotChangedException();
	}
	
	
	/**
	 * Clears the input fields of the employee form.
	 */
	private void clearInputFields() {
		this.editEmployeeView.getCbEmployee().setSelectedIndex(0);
		this.editEmployeeView.getTextFieldFirstName().setText("");
		this.editEmployeeView.getTextFieldLastName().setText("");
		this.editEmployeeView.getCbGender().setSelectedIndex(0);
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
	    		this.editEmployeeView.getTextFieldFirstName().setText("");
	    		this.editEmployeeView.getTextFieldLastName().setText("");
	    		this.editEmployeeView.getCbGender().setSelectedIndex(0);
	    	}
	    	else {
	    		//Employee selected: Fill input fields accordingly.
	    		this.selectedEmployee = this.employees.getEmployeeById(Integer.valueOf(selectedItem.getId()));
	    		
	    		if(this.selectedEmployee != null) {
	    			this.editEmployeeView.getTextFieldFirstName().setText(selectedEmployee.getFirstName());
	    			this.editEmployeeView.getTextFieldLastName().setText(selectedEmployee.getLastName());
	    			this.setCbGender(this.selectedEmployee);
	    		}
	    	}
	    }
	}
	
	
	/**
	 * Sets the gender combo box to the gender of the given employee.
	 * 
	 * @param selectedEmployee The employee providing the gender to be set.
	 */
	private void setCbGender(final Employee selectedEmployee) {
		int numberOfItems = this.editEmployeeView.getCbGender().getItemCount();
		int currentIndex = 0;
		ComboBoxItem currentItem;
		
		while(currentIndex < numberOfItems) {
			currentItem = this.editEmployeeView.getCbGender().getItemAt(currentIndex);
			
			if(currentItem.getId() == selectedEmployee.getGender().toString()) {
				this.editEmployeeView.getCbGender().setSelectedIndex(currentIndex);
				return;
			}
			
			currentIndex++;
		}

		this.editEmployeeView.getCbGender().setSelectedIndex(0);
	}
	
	
	/**
	 * Handles a click at the "salary data"-button.
	 * 
	 * @param salaryDataButtonClick The action event of the button click.
	 */
	public void btnSalaryDataHandler(ActionEvent salaryDataButtonClick) {
		if(this.selectedEmployee == null) {
			JOptionPane.showMessageDialog(this.editEmployeeView, this.resources.getString("gui.employee.error.noEmployeeSelected"), 
					this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
		}
		
		this.employeeSalaryController = new EmployeeSalaryController(this.mainViewController, this.selectedEmployee, this.employeeWebServiceDao);
		this.mainViewController.switchToSalaryView(this.employeeSalaryController);
	}
	

	/**
	 * @return the editEmployeeView
	 */
	public EditEmployeeView getEditEmployeeView() {
		return editEmployeeView;
	}

	/**
	 * @param editEmployeeView the changeEmployeeView to set
	 */
	public void setEditEmployeeView(EditEmployeeView changeEmployeeView) {
		this.editEmployeeView = changeEmployeeView;
	}
}
