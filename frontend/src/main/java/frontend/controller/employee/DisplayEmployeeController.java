package frontend.controller.employee;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
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
import frontend.view.employee.DisplayEmployeeView;

/**
 * Controller of the "display employee"-view.
 *
 * @author Michael
 */
public class DisplayEmployeeController extends EmployeeController {
    /**
     * The controller of the salary view.
     */
    private DisplayEmployeeSalaryController displayEmployeeSalaryController;

    /**
     * The view for employee display.
     */
    private DisplayEmployeeView displayEmployeeView;

    /**
     * The employees of the application. Those are candidates for the "display"-function.
     */
    private EmployeeList employees;

    /**
     * The currently selected employee for display.
     */
    private Employee selectedEmployee;

    /**
     * Application logging.
     */
    public static final Logger LOGGER = LogManager.getLogger(DisplayEmployeeController.class);

    /**
     * Initializes the controller.
     *
     * @param mainViewController The controller of the main view.
     * @throws WebServiceException In case the WebService is unavailable.
     */
    public DisplayEmployeeController(final MainViewController mainViewController) throws WebServiceException {
        super(mainViewController);
        this.displayEmployeeView = new DisplayEmployeeView(this);
        this.employees = new EmployeeList();
        this.selectedEmployee = null;

        // Initialize the employees for the selection.
        try {
            this.employees.setEmployees(this.getEmployeeWebServiceDao().getEmployees(EmployeeHeadQueryParameter.ALL));

            this.initializeEmployeeComboBox();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.displayEmployeeView, e.getMessage(),
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
            this.displayEmployeeView.getCbEmployee().addItem(item);
        }

        this.displayEmployeeView.getCbEmployee().setSelectedIndex(0);
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
                this.displayEmployeeView.getLblFirstNameContent().setText("");
                this.displayEmployeeView.getLblLastNameContent().setText("");
                this.displayEmployeeView.getLblGenderContent().setText("");
            } else {
                // Employee selected: Fill input fields accordingly.
                this.selectedEmployee = this.employees.getEmployeeById(Integer.valueOf(selectedItem.getId()));

                if (this.selectedEmployee != null) {
                    this.displayEmployeeView.getLblFirstNameContent().setText(this.selectedEmployee.getFirstName());
                    this.displayEmployeeView.getLblLastNameContent().setText(this.selectedEmployee.getLastName());
                    this.displayEmployeeView.getLblGenderContent()
                            .setText(this.getGenderText(this.selectedEmployee.getGender()));
                }
            }
        }
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
     * Handles a click at the "salary data"-button.
     *
     * @param salaryDataButtonClick The action event of the button click.
     */
    public void btnSalaryDataHandler(final ActionEvent salaryDataButtonClick) {
        if (this.selectedEmployee == null) {
            JOptionPane.showMessageDialog(this.displayEmployeeView,
                    this.getResources().getString("gui.employee.error.noEmployeeSelected"),
                    this.getResources().getString("gui.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.displayEmployeeSalaryController = new DisplayEmployeeSalaryController(this.getMainViewController(),
                this.selectedEmployee);
        this.displayEmployeeSalaryController.setDisplayEmployeeController(this);
        this.getMainViewController().switchToDisplaySalaryView(this.displayEmployeeSalaryController);
    }

    /**
     * @return the displayEmployeeView
     */
    public DisplayEmployeeView getDisplayEmployeeView() {
        return displayEmployeeView;
    }
}
