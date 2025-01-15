package frontend.controller.employee;

import java.awt.event.ActionEvent;
import java.text.DateFormat;

import frontend.controller.MainViewController;
import frontend.model.Employee;
import frontend.model.EmployeeSalary;
import frontend.view.employee.DisplayEmployeeSalaryView;

/**
 * Controls all actions directly happening within the display employee salary view.
 *
 * @author Michael
 */
public class DisplayEmployeeSalaryController {
    /**
     * The controller of the main view.
     */
    private MainViewController mainViewController;

    /**
     * The view for employee salary display.
     */
    private DisplayEmployeeSalaryView displayEmployeeSalaryView;

    /**
     * The employee whose salary data are being edited.
     */
    private Employee selectedEmployee;

    /**
     * The controller of the employee overview.
     */
    private EmployeeOverviewController employeeOverviewController;

    /**
     * The controller of the display employee view.
     */
    private DisplayEmployeeController displayEmployeeController;

    /**
     * Initializes the controller.
     *
     * @param mainViewController The controller of the main view.
     * @param selectedEmployee   The employee that has been selected to show the salary data.
     */
    public DisplayEmployeeSalaryController(final MainViewController mainViewController,
            final Employee selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
        this.mainViewController = mainViewController;
        this.displayEmployeeSalaryView = new DisplayEmployeeSalaryView(this);

        // Initially the controller of the potentially calling views are set to null.
        // The calling controller has to be explicitly set afterwards.
        this.employeeOverviewController = null;
        this.displayEmployeeController = null;

        this.initializeViewData();
    }

    /**
     * Initializes the display elements of the view.
     */
    public void initializeViewData() {
        EmployeeSalary salary = this.selectedEmployee.getSalaryData();

        if (salary == null) {
            return;
        }

        this.displayEmployeeSalaryView.getLblMonthlySalaryContent().setText(String.valueOf(salary.getMonthlySalary()));
        this.displayEmployeeSalaryView.getLblLastChangeValue()
                .setText(DateFormat.getInstance().format(salary.getSalaryLastChange()));
    }

    /**
     * Handles a click at the "back"-button of the display employee salary view.
     *
     * @param e The action event of the button click.
     */
    public void btnBackHandler(final ActionEvent e) {
        if (this.employeeOverviewController != null && this.displayEmployeeController == null) {
            this.mainViewController.switchToEmployeeOverview(this.employeeOverviewController);
        }

        if (this.employeeOverviewController == null && this.displayEmployeeController != null) {
            this.mainViewController.switchToDisplayEmployeeView(this.displayEmployeeController);
        }
    }

    /**
     * @return the selectedEmployee
     */
    public Employee getSelectedEmployee() {
        return selectedEmployee;
    }

    /**
     * @param employeeOverviewController the employeeOverviewController to set
     */
    public void setEmployeeOverviewController(final EmployeeOverviewController employeeOverviewController) {
        this.employeeOverviewController = employeeOverviewController;
    }

    /**
     * @param displayEmployeeController the displayEmployeeController to set
     */
    public void setDisplayEmployeeController(final DisplayEmployeeController displayEmployeeController) {
        this.displayEmployeeController = displayEmployeeController;
    }

    /**
     * @return the displayEmployeeSalaryView
     */
    public DisplayEmployeeSalaryView getDisplayEmployeeSalaryView() {
        return displayEmployeeSalaryView;
    }
}
