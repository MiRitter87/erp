package frontend.controller.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import jakarta.xml.ws.WebServiceException;

import frontend.controller.MainViewController;
import frontend.dao.EmployeeWebServiceDao;
import frontend.model.ComboBoxItem;
import frontend.model.Employee;
import frontend.model.EmployeeList;
import frontend.model.Gender;

/**
 * Controls functions of the employee management, that are shared between different views.
 *
 * @author Michael
 */
public class EmployeeController {
    /**
     * The controller of the main view.
     */
    private MainViewController mainViewController;

    /**
     * Access to localized application resources.
     */
    private ResourceBundle resources;

    /**
     * Access to employee data using a WebService.
     */
    private EmployeeWebServiceDao employeeWebServiceDao;

    /**
     * Initializes the controller.
     *
     * @param mainViewController The controller of the main view.
     * @throws WebServiceException In case the WebService is unavailable.
     */
    public EmployeeController(final MainViewController mainViewController) throws WebServiceException {
        this.mainViewController = mainViewController;
        this.resources = ResourceBundle.getBundle("frontend");
        this.employeeWebServiceDao = new EmployeeWebServiceDao();
    }

    /**
     * Validates the user input.
     *
     * @param firstName      The first name of the employee.
     * @param lastName       The last name of the employee.
     * @param selectedGender The selected gender of the employee.
     * @exception Exception Indicating failed validation.
     */
    protected void validateInput(final String firstName, final String lastName, final ComboBoxItem selectedGender)
            throws Exception {
        // Check if first name is given
        if (firstName.length() == 0) {
            throw new Exception(this.resources.getString("gui.employee.error.firstNameValidation"));
        }

        // Check if last name is given
        if (lastName.length() == 0) {
            throw new Exception(this.resources.getString("gui.employee.error.lastNameValidation"));
        }

        // Check if a gender is selected
        if (selectedGender.getId().equals("")) {
            throw new Exception(this.resources.getString("gui.employee.error.genderValidation"));
        }
    }

    /**
     * Gets the gender that is currently selected in a gender selection combo box.
     *
     * @param selectedGender The ComboBoxItem of the selected gender.
     * @return The currently selected gender.
     */
    protected Gender getSelectedGender(final ComboBoxItem selectedGender) {
        if (selectedGender.getId() == Gender.FEMALE.toString()) {
            return Gender.FEMALE;
        } else if (selectedGender.getId() == Gender.MALE.toString()) {
            return Gender.MALE;
        } else {
            return null;
        }
    }

    /**
     * Gets the localized text of the given gender.
     *
     * @param gender The gender.
     * @return The localized gender text.
     */
    protected String getGenderText(final Gender gender) {
        switch (gender) {
        case FEMALE:
            return this.resources.getString("gui.employee.gender.female");
        case MALE:
            return this.resources.getString("gui.employee.gender.male");
        default:
            return "";
        }
    }

    /**
     * Provides a list with ComboBoxItems for each gender. An empty item to de-select an entry in the ComboBox is
     * provided first.
     *
     * @return A list with ComboBoxItems for each gender.
     */
    protected List<ComboBoxItem> getGenderItemsForComboBox() {
        List<ComboBoxItem> items = new ArrayList<ComboBoxItem>();

        // Add an initial ComboBox entry.
        items.add(new ComboBoxItem("", ""));

        // Add an item for each available gender.
        for (Gender gender : Gender.values()) {
            switch (gender) {
            case FEMALE:
                items.add(new ComboBoxItem(gender.toString(), this.resources.getString("gui.employee.gender.female")));
                break;
            case MALE:
                items.add(new ComboBoxItem(gender.toString(), this.resources.getString("gui.employee.gender.male")));
                break;
            default:
                break;
            }
        }

        return items;
    }

    /**
     * Provides a list with ComboBoxItems for the given employees. An empty item to de-select an entry in the ComboBox
     * is provided first.
     *
     * @param employees The employees for which the ComboBoxItems are being created.
     * @return A list with ComboBoxItems for all employees.
     */
    public static List<ComboBoxItem> getEmployeeItemsForComboBox(final EmployeeList employees) {
        List<ComboBoxItem> items = new ArrayList<ComboBoxItem>();
        StringBuilder builder;

        // An initial ComboBox entry allowing the user to de-select.
        items.add(new ComboBoxItem("", ""));

        // One ComboBox entry for each employee.
        for (Employee tempEmployee : employees.getEmployees()) {
            builder = new StringBuilder();
            builder.append(tempEmployee.getId().toString());
            builder.append(" - ");
            builder.append(tempEmployee.getFirstName());
            builder.append(" ");
            builder.append(tempEmployee.getLastName());

            items.add(new ComboBoxItem(tempEmployee.getId().toString(), builder.toString()));
        }

        return items;
    }

    /**
     * @return the mainViewController
     */
    public MainViewController getMainViewController() {
        return mainViewController;
    }

    /**
     * @param mainViewController the mainViewController to set
     */
    public void setMainViewController(final MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    /**
     * @return the resources
     */
    public ResourceBundle getResources() {
        return resources;
    }

    /**
     * @param resources the resources to set
     */
    public void setResources(final ResourceBundle resources) {
        this.resources = resources;
    }

    /**
     * @return the employeeWebServiceDao
     */
    public EmployeeWebServiceDao getEmployeeWebServiceDao() {
        return employeeWebServiceDao;
    }

    /**
     * @param employeeWebServiceDao the employeeWebServiceDao to set
     */
    public void setEmployeeWebServiceDao(final EmployeeWebServiceDao employeeWebServiceDao) {
        this.employeeWebServiceDao = employeeWebServiceDao;
    }
}
