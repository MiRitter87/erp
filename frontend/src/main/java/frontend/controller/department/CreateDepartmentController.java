package frontend.controller.department;

import java.awt.event.ActionEvent;
import java.text.MessageFormat;
import java.util.List;

import javax.swing.JOptionPane;
import jakarta.xml.ws.WebServiceException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import frontend.controller.MainViewController;
import frontend.controller.employee.EmployeeController;
import frontend.generated.ws.soap.employee.EmployeeHeadQueryParameter;
import frontend.model.ComboBoxItem;
import frontend.model.Department;
import frontend.model.EmployeeList;
import frontend.view.department.CreateDepartmentView;

/**
 * Controller of the "create department"-view.
 *
 * @author Michael
 */
public class CreateDepartmentController extends DepartmentController {
    /**
     * The view for department creation.
     */
    private CreateDepartmentView createDepartmentView;

    /**
     * The employees that are managed by the employee view. Those are candidates for the "head of department" ComboBox.
     */
    private EmployeeList employees;

    /**
     * Application logging.
     */
    public static final Logger LOGGER = LogManager.getLogger(CreateDepartmentController.class);

    /**
     * Initializes the controller.
     *
     * @param mainViewController The controller of the main view.
     * @throws WebServiceException In case the WebService is unavailable.
     */
    public CreateDepartmentController(final MainViewController mainViewController) throws WebServiceException {
        super(mainViewController);
        this.createDepartmentView = new CreateDepartmentView(this);
        this.employees = new EmployeeList();

        // Initialization of employee data for head selection combo box.
        try {
            this.employees.setEmployees(
                    this.getEmployeeWebServiceDao().getEmployees(EmployeeHeadQueryParameter.NO_HEAD_ONLY));
            this.initializeHeadComboBox();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.createDepartmentView, e.getMessage(),
                    this.getResources().getString("gui.error"), JOptionPane.ERROR_MESSAGE);
            LOGGER.debug("Error while trying to read employees from WebService: " + e.getMessage());
        }
    }

    /**
     * Initializes the ComboBox for head of department selection. All employees are being displayed by id, first name
     * and second name.
     */
    private void initializeHeadComboBox() {
        List<ComboBoxItem> items = EmployeeController.getEmployeeItemsForComboBox(this.employees);

        for (ComboBoxItem item : items) {
            this.createDepartmentView.getCbHead().addItem(item);
        }
    }

    /**
     * Handles the click at the cancel-button.
     *
     * @param e The action event of the button click.
     */
    public void cancelHandler(final ActionEvent e) {
        this.getMainViewController().switchToStartpage();
    }

    /**
     * Handles a click at the "save"-button.
     *
     * @param e The action event of the button click.
     */
    public void saveHandler(final ActionEvent e) {
        // Validation of user input
        try {
            this.validateInput();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this.createDepartmentView, exception.getMessage(),
                    this.getResources().getString("gui.error"), JOptionPane.ERROR_MESSAGE);
            LOGGER.debug("User failed to add employee due to a validation error: " + exception.getMessage());
            return;
        }

        // Validating succeeded - Try to persist new department
        try {
            Department newDepartment = this.getDepartmentFromViewInput();
            this.getDepartmentWebServiceDao().insertDepartment(newDepartment);
            this.clearInputFields();
            this.displaySaveSuccessPopUp(newDepartment.getName());
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this.createDepartmentView, exception.getMessage(),
                    this.getResources().getString("gui.error"), JOptionPane.ERROR_MESSAGE);
            LOGGER.debug("Saving department failed: " + exception.getMessage());
        }
    }

    /**
     * Validates the user input.
     *
     * @exception Exception Indicating failed validation.
     */
    private void validateInput() throws Exception {
        ComboBoxItem selectedHead = (ComboBoxItem) this.createDepartmentView.getCbHead().getSelectedItem();

        // Validation of input fields.
        this.validateInput(this.createDepartmentView.getTextFieldCode().getText());
        this.validateInput(this.getCreateDepartmentView().getTextFieldName().getText(), selectedHead);
    }

    /**
     * Validates the user input on fields that only exist in the creation view.
     *
     * @param code The code of the department
     * @throws Exception Indicating failed validation.
     */
    private void validateInput(final String code) throws Exception {
        // A department name has to be given
        if (code.length() == 0) {
            throw new Exception(this.getResources().getString("gui.dept.error.codeValidation"));
        }
    }

    /**
     * Provides a department object based on the given user input.
     *
     * @return The department object.
     */
    private Department getDepartmentFromViewInput() {
        Department newDepartment = new Department();
        ComboBoxItem selectedHead = (ComboBoxItem) this.createDepartmentView.getCbHead().getSelectedItem();

        newDepartment.setCode(this.createDepartmentView.getTextFieldCode().getText());
        newDepartment.setName(this.createDepartmentView.getTextFieldName().getText());
        newDepartment.setDescription(this.createDepartmentView.getTextAreaDescription().getText());
        newDepartment.setHead(this.employees.getEmployeeById(Integer.valueOf(selectedHead.getId())));

        return newDepartment;
    }

    /**
     * Clears the input fields of the department form.
     */
    private void clearInputFields() {
        this.createDepartmentView.getTextFieldCode().setText("");
        this.createDepartmentView.getTextFieldName().setText("");
        this.createDepartmentView.getTextAreaDescription().setText("");
        this.createDepartmentView.getCbHead().setSelectedIndex(0);
    }

    /**
     * Displays a PopUp informing the user that the create operation has been performed successfully.
     *
     * @param departmentName The name of the department that has been created.
     */
    private void displaySaveSuccessPopUp(final String departmentName) {
        JOptionPane.showMessageDialog(this.createDepartmentView,
                MessageFormat.format(this.getResources().getString("gui.dept.information.createSuccess"),
                        departmentName),
                this.getResources().getString("gui.information"), JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * @return the createDepartmentView
     */
    public CreateDepartmentView getCreateDepartmentView() {
        return createDepartmentView;
    }
}
