package frontend.controller.employee;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.text.MessageFormat;
import java.util.List;

import javax.swing.JOptionPane;
import jakarta.xml.ws.WebServiceException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import frontend.controller.MainViewController;
import frontend.generated.ws.soap.employee.EmployeeHeadQueryParameter;
import frontend.model.ComboBoxItem;
import frontend.model.Employee;
import frontend.model.EmployeeList;
import frontend.view.employee.EditEmployeeView;

/**
 * Controller of the "change employee"-view.
 *
 * @author Michael
 */
public class EditEmployeeController extends EmployeeController {
    /**
     * The controller of the salary view.
     */
    private EditEmployeeSalaryController editEmployeeSalaryController;

    /**
     * The view for employee edit.
     */
    private EditEmployeeView editEmployeeView;

    /**
     * The employees of the application. Those are candidates for the "edit"-function.
     */
    private EmployeeList employees;

    /**
     * Application logging.
     */
    public static final Logger LOGGER = LogManager.getLogger(EditEmployeeController.class);

    /**
     * The currently selected employee for editing.
     */
    private Employee selectedEmployee;

    /**
     * Initializes the controller.
     *
     * @param mainViewController The controller of the main view.
     * @throws WebServiceException In case the WebService is unavailable.
     */
    public EditEmployeeController(final MainViewController mainViewController) throws WebServiceException {
        super(mainViewController);
        this.editEmployeeView = new EditEmployeeView(this);
        this.employees = new EmployeeList();
        this.initializeGenderComboBox();
        this.selectedEmployee = null;

        // Initialize the employees for the selection.
        try {
            this.employees.setEmployees(this.getEmployeeWebServiceDao().getEmployees(EmployeeHeadQueryParameter.ALL));

            this.initializeEmployeeComboBox();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.editEmployeeView, e.getMessage(),
                    this.getResources().getString("gui.error"), JOptionPane.ERROR_MESSAGE);
            LOGGER.error("Error while trying to read employees from WebService: " + e.getMessage());
        }
    }

    /**
     * Initializes the combo box for employee selection. All employees are being displayed by ID, first name and last
     * name.
     */
    private void initializeEmployeeComboBox() {
        List<ComboBoxItem> items = EmployeeController.getEmployeeItemsForComboBox(this.employees);

        for (ComboBoxItem item : items) {
            this.editEmployeeView.getCbEmployee().addItem(item);
        }

        this.editEmployeeView.getCbEmployee().setSelectedIndex(0);
    }

    /**
     * Provides the labels for the gender selection combo box.
     */
    private void initializeGenderComboBox() {
        List<ComboBoxItem> items = this.getGenderItemsForComboBox();

        for (ComboBoxItem item : items) {
            this.editEmployeeView.getCbGender().addItem(item);
        }
    }

    /**
     * Handles a click at the "save employee"-button.
     *
     * @param saveEvent The action event of the button click.
     */
    public void saveEmployeeHandler(final ActionEvent saveEvent) {
        // Validation of user input
        try {
            this.validateInput();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this.editEmployeeView, exception.getMessage(),
                    this.getResources().getString("gui.error"), JOptionPane.ERROR_MESSAGE);
            LOGGER.debug("User failed to edit employee due to a validation error: " + exception.getMessage());
            return;
        }

        // Validation successful - Update model with new data from input fields and check if anything has changed
        this.updateEmployeeFromViewInput();
        if (!this.selectedEmployee.isEdited()) {
            JOptionPane.showMessageDialog(this.editEmployeeView,
                    this.getResources().getString("gui.employee.error.notEdited"),
                    this.getResources().getString("gui.information"), JOptionPane.INFORMATION_MESSAGE);
            LOGGER.debug("User tried to save an employee without any changes made.");
            return;
        }

        // Changes exist - Try to persist those changes
        try {
            this.getEmployeeWebServiceDao().updateEmployee(this.selectedEmployee);
            this.displaySaveSuccessPopUp(this.selectedEmployee.getFullName());
            this.selectedEmployee.reHash(); // The hash of the employee in the local list of all employees is updated.
            this.clearInputFields();
            this.selectedEmployee = null;

            // The combo box for employee selection needs to be re-initialized in order to show the changes.
            this.editEmployeeView.getCbEmployee().removeAllItems();
            this.initializeEmployeeComboBox();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this.editEmployeeView, exception.getMessage(),
                    this.getResources().getString("gui.error"), JOptionPane.ERROR_MESSAGE);
            LOGGER.debug("Updating employee failed: " + exception.getMessage());
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

        // Assure that an employee to be edited is selected in the ComboBox.
        if (this.selectedEmployee == null) {
            throw new Exception(this.getResources().getString("gui.employee.error.noEmployeeSelected"));
        }

        // Validation of input fields.
        this.validateInput(this.editEmployeeView.getTextFieldFirstName().getText(),
                this.editEmployeeView.getTextFieldLastName().getText(), selectedGender);
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
     * Displays a PopUp informing the user that the save operation has been performed successfully.
     *
     * @param fullName The full name of the employee.
     */
    private void displaySaveSuccessPopUp(final String fullName) {
        JOptionPane.showMessageDialog(this.editEmployeeView,
                MessageFormat.format(this.getResources().getString("gui.employee.information.saveSuccess"), fullName),
                this.getResources().getString("gui.information"), JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Handles a click at the "cancel"-button.
     *
     * @param cancelEvent The action event of the button click.
     */
    public void cancelHandler(final ActionEvent cancelEvent) {
        this.getMainViewController().switchToStartpage();
    }

    /**
     * Handles selections performed at the employee selection combo box.
     *
     * @param itemEvent Indicates ComboBox item changed.
     */
    public void cbEmployeeItemStateChanged(final ItemEvent itemEvent) {
        if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
            ComboBoxItem selectedItem = (ComboBoxItem) itemEvent.getItem();

            // Selection of empty employee: Clear input fields
            if (selectedItem.getId() == "") {
                this.selectedEmployee = null;
                this.editEmployeeView.getTextFieldFirstName().setText("");
                this.editEmployeeView.getTextFieldLastName().setText("");
                this.editEmployeeView.getCbGender().setSelectedIndex(0);
            } else {
                // Employee selected: Fill input fields accordingly.
                this.selectedEmployee = this.employees.getEmployeeById(Integer.valueOf(selectedItem.getId()));

                if (this.selectedEmployee != null) {
                    this.editEmployeeView.getTextFieldFirstName().setText(selectedEmployee.getFirstName());
                    this.editEmployeeView.getTextFieldLastName().setText(selectedEmployee.getLastName());
                    this.setCbGender();
                }
            }
        }
    }

    /**
     * Sets the gender combo box to the gender of the given employee.
     */
    private void setCbGender() {
        int numberOfItems = this.editEmployeeView.getCbGender().getItemCount();
        int currentIndex = 0;
        ComboBoxItem currentItem;

        while (currentIndex < numberOfItems) {
            currentItem = this.editEmployeeView.getCbGender().getItemAt(currentIndex);

            if (currentItem.getId() == selectedEmployee.getGender().toString()) {
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
    public void btnSalaryDataHandler(final ActionEvent salaryDataButtonClick) {
        if (this.selectedEmployee == null) {
            JOptionPane.showMessageDialog(this.editEmployeeView,
                    this.getResources().getString("gui.employee.error.noEmployeeSelected"),
                    this.getResources().getString("gui.error"), JOptionPane.ERROR_MESSAGE);
        }

        this.editEmployeeSalaryController = new EditEmployeeSalaryController(this.getMainViewController(),
                this.selectedEmployee, this.getEmployeeWebServiceDao());
        this.editEmployeeSalaryController.setEditEmployeeController(this);
        this.getMainViewController().switchToEditSalaryView(this.editEmployeeSalaryController);
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
    public void setEditEmployeeView(final EditEmployeeView editEmployeeView) {
        this.editEmployeeView = editEmployeeView;
    }
}
