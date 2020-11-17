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
import frontend.model.Gender;
import frontend.view.View;
import frontend.view.employee.EditEmployeeView;

/**
 * Controller of the "change employee"-view.
 * 
 * @author Michael
 */
public class EditEmployeeController {
	/**
	 * The controller of the main view.
	 */
	private MainViewController mainViewController;
	
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
		//TODO Implement
	}
	
	
	/**
	 * Handles a click at the "cancel"-button.
	 * 
	 * @param cancelEvent The action event of the button click.
	 */
	public void cancelHandler(ActionEvent cancelEvent) {
		this.mainViewController.navigateToView(View.STARTPAGE);
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
	    		this.editEmployeeView.getTextFieldFirstName().setText("");
	    		this.editEmployeeView.getTextFieldLastName().setText("");
	    		this.editEmployeeView.getCbGender().setSelectedIndex(0);
	    	}
	    	else {
	    		//Employee selected: Fill input fields accordingly.
	    		Employee selectedEmployee = this.employees.getEmployeeById(Integer.valueOf(selectedItem.getId()));
	    		
	    		if(selectedEmployee != null) {
	    			this.editEmployeeView.getTextFieldFirstName().setText(selectedEmployee.getFirstName());
	    			this.editEmployeeView.getTextFieldLastName().setText(selectedEmployee.getLastName());
	    			this.setCbGender(selectedEmployee);
	    		}
	    	}
	    }
	}
	
	
	/**
	 * Sets the gender combo box to the gender of the given employee.
	 * 
	 * @param selectedEmployee The employee providing the gender to be set.
	 */
	public void setCbGender(final Employee selectedEmployee) {
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
