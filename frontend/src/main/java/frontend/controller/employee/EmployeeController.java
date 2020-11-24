package frontend.controller.employee;

import java.util.ResourceBundle;

import frontend.model.ComboBoxItem;
import frontend.model.Gender;

/**
 * Controls functions of the employee management, that are shared between different views.
 * 
 * @author Michael
 */
public class EmployeeController {
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources;
	

	/**
	 * Initializes the controller.
	 */
	public EmployeeController() {
		this.resources = ResourceBundle.getBundle("frontend");
	}
	
	/**
	 * Validates the user input.
	 * 
	 * @exception Exception Indicating failed validation.
	 */
	protected void validateInput(final String firstName, final String lastName, final ComboBoxItem selectedGender) throws Exception {
		//Check if first name is given
		if(firstName.length() == 0)
			throw new Exception(this.resources.getString("gui.employee.error.firstNameValidation"));
		
		//Check if last name is given
		if(lastName.length() == 0)
			throw new Exception(this.resources.getString("gui.employee.error.lastNameValidation"));
		
		//Check if a gender is selected
		if(selectedGender.getId().equals(""))
			throw new Exception(this.resources.getString("gui.employee.error.genderValidation"));
	}
	
	
	/**
	 * Gets the gender that is currently selected in a gender selection combo box.
	 * 
	 * @return The currently selected gender.
	 */
	protected Gender getSelectedGender(final ComboBoxItem selectedGender) {
		if(selectedGender.getId() == Gender.FEMALE.toString())
			return Gender.FEMALE;
		else if(selectedGender.getId() == Gender.MALE.toString())
			return Gender.MALE;
		else
			return null;
	}
	
	
	/**
	 * Gets the localized text of the given gender.
	 * 
	 * @param gender The gender.
	 * @return The localized gender text.
	 */
	protected String getGenderText(final Gender gender) {
		switch(gender) {
			case FEMALE: {
				return this.resources.getString("gui.employee.gender.female");
			}
			case MALE: {
				return this.resources.getString("gui.employee.gender.male");
			}
		}

		return "";
	}
}
