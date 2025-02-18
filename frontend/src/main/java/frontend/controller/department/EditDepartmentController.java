package frontend.controller.department;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
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
import frontend.model.DepartmentList;
import frontend.model.Employee;
import frontend.model.EmployeeList;
import frontend.view.department.EditDepartmentView;

/**
 * Controller of the "edit department"-view.
 *
 * @author Michael
 */
public class EditDepartmentController extends DepartmentController {
    /**
     * The view for department editing.
     */
    private EditDepartmentView editDepartmentView;

    /**
     * The departments of the application. Those are candidates for the "edit"-function.
     */
    private DepartmentList departments;

    /**
     * The employees that are managed by the employee view. Those are candidates for the "head of department" ComboBox.
     */
    private EmployeeList employees;

    /**
     * The currently selected department for editing.
     */
    private Department selectedDepartment;

    /**
     * Application logging.
     */
    public static final Logger LOGGER = LogManager.getLogger(EditDepartmentController.class);

    /**
     * Initializes the controller.
     *
     * @param mainViewController The controller of the main view.
     * @throws WebServiceException In case the WebService is unavailable.
     */
    public EditDepartmentController(final MainViewController mainViewController) throws WebServiceException {
        super(mainViewController);
        this.editDepartmentView = new EditDepartmentView(this);
        this.departments = new DepartmentList();
        this.employees = new EmployeeList();
        this.selectedDepartment = null;

        // Initialize the departments for the selection.
        try {
            this.departments.setDepartments(this.getDepartmentWebServiceDao().getDepartments());
            this.initializeDepartmentComboBox();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.editDepartmentView, e.getMessage(),
                    this.getResources().getString("gui.error"), JOptionPane.ERROR_MESSAGE);
            LOGGER.error("Error while trying to read departments from WebService: " + e.getMessage());
        }

        // Initialization of employee data for head selection combo box.
        try {
            this.employees.setEmployees(this.getEmployeeWebServiceDao().getEmployees(EmployeeHeadQueryParameter.ALL));
            this.initializeHeadComboBox();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.editDepartmentView, e.getMessage(),
                    this.getResources().getString("gui.error"), JOptionPane.ERROR_MESSAGE);
            LOGGER.error("Error while trying to read employees from WebService: " + e.getMessage());
        }
    }

    /**
     * Initializes the combo box for department selection. All departments are being displayed by code and name.
     */
    private void initializeDepartmentComboBox() {
        List<ComboBoxItem> items = this.getDepartmentItemsForComboBox(this.departments);

        for (ComboBoxItem item : items) {
            this.editDepartmentView.getCbDepartment().addItem(item);
        }

        this.editDepartmentView.getCbDepartment().setSelectedIndex(0);
    }

    /**
     * Initializes the ComboBox for head of department selection. All employees are being displayed by id, first name
     * and second name.
     */
    private void initializeHeadComboBox() {
        List<ComboBoxItem> items = EmployeeController.getEmployeeItemsForComboBox(this.employees);

        for (ComboBoxItem item : items) {
            this.editDepartmentView.getCbHead().addItem(item);
        }

        this.editDepartmentView.getCbHead().setSelectedIndex(0);
    }

    /**
     * Handles selections performed at the department selection combo box.
     *
     * @param itemEvent Indicates ComboBox item changed.
     */
    public void cbDepartmentItemStateChanged(final ItemEvent itemEvent) {
        if (itemEvent.getStateChange() != ItemEvent.SELECTED) {
            return;
        }

        ComboBoxItem selectedItem = (ComboBoxItem) itemEvent.getItem();

        // Selection of empty department: Clear input fields
        if (selectedItem.getId() == "") {
            this.selectedDepartment = null;
            this.editDepartmentView.getLblCodeContent().setText("");
            this.editDepartmentView.getTextFieldName().setText("");
            this.editDepartmentView.getTextAreaDescription().setText("");

            if (this.editDepartmentView.getCbHead().getItemCount() > 0) {
                this.editDepartmentView.getCbHead().setSelectedIndex(0);
            }
        } else {
            // Department selected: Fill input fields accordingly.
            this.selectedDepartment = this.departments.getDepartmentByCode(selectedItem.getId());

            if (this.selectedDepartment != null) {
                this.editDepartmentView.getLblCodeContent().setText(this.selectedDepartment.getCode());
                this.editDepartmentView.getTextFieldName().setText(this.selectedDepartment.getName());
                this.editDepartmentView.getTextAreaDescription().setText(this.selectedDepartment.getDescription());
                this.setCbHead(this.selectedDepartment.getHead());
            }
        }
    }

    /**
     * Sets the head combo box to the given employee.
     *
     * @param selectedEmployee The employee to be set.
     */
    private void setCbHead(final Employee selectedEmployee) {
        int numberOfItems = this.editDepartmentView.getCbHead().getItemCount();
        int currentIndex = 0;
        ComboBoxItem currentItem;

        while (currentIndex < numberOfItems) {
            currentItem = this.editDepartmentView.getCbHead().getItemAt(currentIndex);

            if (currentItem.getId().equals(String.valueOf(selectedEmployee.getId()))) {
                this.editDepartmentView.getCbHead().setSelectedIndex(currentIndex);
                return;
            }

            currentIndex++;
        }

        this.editDepartmentView.getCbHead().setSelectedIndex(0);
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
     * Handles a click at the "save department"-button.
     *
     * @param saveEvent The action event of the button click.
     */
    public void saveDepartmentHandler(final ActionEvent saveEvent) {
        Department originalDepartment = new Department(this.selectedDepartment);

        // Validation of user input
        try {
            this.validateInput();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this.editDepartmentView, exception.getMessage(),
                    this.getResources().getString("gui.error"), JOptionPane.ERROR_MESSAGE);
            LOGGER.debug("User failed to edit department due to a validation error: " + exception.getMessage());
            return;
        }

        // Validation successful - Update model with new data from input fields and check if anything has changed
        this.updateDepartmentFromViewInput();
        if (!this.selectedDepartment.isEdited()) {
            JOptionPane.showMessageDialog(this.editDepartmentView,
                    this.getResources().getString("gui.dept.error.notEdited"),
                    this.getResources().getString("gui.information"), JOptionPane.INFORMATION_MESSAGE);
            LOGGER.debug("User tried to save a department without any changes made.");
            return;
        }

        // Changes exist - Try to persist those changes
        try {
            this.getDepartmentWebServiceDao().updateDepartment(this.selectedDepartment);
            this.displaySaveSuccessPopUp(this.selectedDepartment.getName());
            this.selectedDepartment.reHash(); // The hash of the department in the local list of all departments is
                                              // updated.
            this.clearInputFields();
            this.selectedDepartment = null;

            // The combo box for department selection needs to be re-initialized in order to show the changes.
            this.editDepartmentView.getCbDepartment().removeAllItems();
            this.initializeDepartmentComboBox();
        } catch (Exception exception) {
            // Restore the old state of the selected department because the saving failed.
            this.setSelectedDeparmentData(originalDepartment);
            JOptionPane.showMessageDialog(this.editDepartmentView, exception.getMessage(),
                    this.getResources().getString("gui.error"), JOptionPane.ERROR_MESSAGE);
            LOGGER.debug("Updating department failed: " + exception.getMessage());
        }
    }

    /**
     * Validates the user input.
     *
     * @exception Exception Indicating failed validation.
     */
    private void validateInput() throws Exception {
        ComboBoxItem selectedHead = (ComboBoxItem) this.editDepartmentView.getCbHead().getSelectedItem();

        // Assure that the department to be edited is selected in the ComboBox.
        if (this.selectedDepartment == null) {
            throw new Exception(this.getResources().getString("gui.dept.error.noDepartmentSelected"));
        }

        // Validation of input fields.
        this.validateInput(this.getEditDepartmentView().getTextFieldName().getText(), selectedHead);
    }

    /**
     * Updates the data of the currently selected department with the data from the input fields.
     */
    private void updateDepartmentFromViewInput() {
        ComboBoxItem selectedHead = (ComboBoxItem) this.editDepartmentView.getCbHead().getSelectedItem();

        this.selectedDepartment.setName(this.editDepartmentView.getTextFieldName().getText());
        this.selectedDepartment.setDescription(this.editDepartmentView.getTextAreaDescription().getText());
        this.selectedDepartment.setHead(this.employees.getEmployeeById(Integer.valueOf(selectedHead.getId())));
    }

    /**
     * Clears the input fields of the department form.
     */
    private void clearInputFields() {
        this.editDepartmentView.getCbDepartment().setSelectedIndex(0);
        this.editDepartmentView.getLblCodeContent().setText("");
        this.editDepartmentView.getTextFieldName().setText("");
        this.editDepartmentView.getTextAreaDescription().setText("");
        this.editDepartmentView.getCbHead().setSelectedIndex(0);
    }

    /**
     * Displays a PopUp informing the user that the save operation has been performed successfully.
     *
     * @param departmentName The name of the department that has been saved.
     */
    private void displaySaveSuccessPopUp(final String departmentName) {
        JOptionPane.showMessageDialog(this.editDepartmentView,
                MessageFormat.format(this.getResources().getString("gui.dept.information.saveSuccess"), departmentName),
                this.getResources().getString("gui.information"), JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Sets the editable data of the selected department with the data of the given department.
     *
     * @param department The department which data are used.
     */
    private void setSelectedDeparmentData(final Department department) {
        this.selectedDepartment.setName(department.getName());
        this.selectedDepartment.setDescription(department.getDescription());
        this.selectedDepartment.setHead(department.getHead());
    }

    /**
     * @return the editDepartmentView
     */
    public EditDepartmentView getEditDepartmentView() {
        return editDepartmentView;
    }
}
