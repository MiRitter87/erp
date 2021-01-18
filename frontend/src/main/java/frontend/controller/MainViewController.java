package frontend.controller;


import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import frontend.controller.employee.EditEmployeeController;
import frontend.controller.employee.EditEmployeeSalaryController;
import frontend.controller.employee.EmployeeOverviewController;
import frontend.controller.department.DepartmentOverviewController;
import frontend.controller.department.DisplayDepartmentController;
import frontend.controller.employee.CreateEmployeeController;
import frontend.controller.employee.DisplayEmployeeController;
import frontend.controller.employee.DisplayEmployeeSalaryController;
import frontend.view.MainView;

/**
 * Controls all actions directly happening within the MainView.
 * 
 * @author Michael
 */
public class MainViewController {
	/**
	 * The main view of the application
	 */
	private MainView mainView;
	
		
	/**
	 * Creates the main view.
	 */
	public MainViewController() {
		this.mainView = new MainView(this);
	}
		
	
	/**
	 * Performs tasks on closing of the main view.
	 */
	public void onMainViewClose() {

	}
	
	
	/**
	 * Switches the currently displayed content area to the employee basic data view.
	 * 
	 * @param employeeOverviewController The controller of the employee overview. Can be null, if the view has not been called before or should not be reused.
	 */
	public void switchToEmployeeOverview(final EmployeeOverviewController employeeOverviewController) {
		EmployeeOverviewController controller;
		
		if(employeeOverviewController != null)
			controller = employeeOverviewController;
		else
			controller = new EmployeeOverviewController(this);
			
		this.mainView.getContentPane().removeAll();
		this.mainView.getContentPane().add(controller.getEmployeeOverview());
		SwingUtilities.updateComponentTreeUI(this.mainView);
	}
	
	
	/**
	 * Switches the currently displayed content area to the edit employee salary view.
	 * 
	 * @param salaryController The controller of the edit employee salary view.
	 */
	public void switchToEditSalaryView(final EditEmployeeSalaryController editSalaryController) {
		this.mainView.getContentPane().removeAll();
		this.mainView.getContentPane().add(editSalaryController.getEditEmployeeSalaryView());
		this.mainView.revalidate();
	}
	
	
	/**
	 * Switches the currently displayed content area to the display employee salary view.
	 * 
	 * @param displaySalaryController The controller of the display employee salary view.
	 */
	public void switchToDisplaySalaryView(final DisplayEmployeeSalaryController displaySalaryController) {
		this.mainView.getContentPane().removeAll();
		this.mainView.getContentPane().add(displaySalaryController.getDisplayEmployeeSalaryView());
		this.mainView.revalidate();
	}
	
	
	/**
	 * Switches the currently displayed content area to the display department view.
	 */
	public void switchToDisplayDepartmentView() {
		DisplayDepartmentController controller = new DisplayDepartmentController(this);
		this.mainView.getContentPane().removeAll();
		this.mainView.getContentPane().add(controller.getDisplayDepartmentView());
		this.mainView.revalidate();
	}
	
	
	/**
	 * Switches the currently displayed content area to the department view.
	 */
	public void switchToDepartmentView() {
		DepartmentOverviewController controller = new DepartmentOverviewController();
		this.mainView.getContentPane().removeAll();
		this.mainView.getContentPane().add(controller.getDepartmentView());
		this.mainView.revalidate();
	}
	
	
	/**
	 * Switches the currently displayed content area to the start page.
	 */
	public void switchToStartpage() {
		this.mainView.getContentPane().removeAll();
		this.mainView.getContentPane().add(new JPanel());
		this.mainView.revalidate();
	}
	
	
	/**
	 * Switches the currently displayed content area to the employee creation view.
	 */
	public void switchToCreateEmployeeView() {
		CreateEmployeeController controller = new CreateEmployeeController(this);
		this.mainView.getContentPane().removeAll();
		this.mainView.getContentPane().add(controller.getCreateEmployeeView());
		this.mainView.revalidate();
	}
	
	
	/**
	 * Switches the currently displayed content area to the employee edit view. 
	 * 
	 * @param editEmployeeController The controller of the edit employee view. Can be null, if the view has not been called before or should not be reused.
	 */
	public void switchToEditEmployeeView(final EditEmployeeController editEmployeeController) {
		EditEmployeeController controller;
		
		if(editEmployeeController != null)
			controller = editEmployeeController;
		else
			controller = new EditEmployeeController(this);
		
		this.mainView.getContentPane().removeAll();
		this.mainView.getContentPane().add(controller.getEditEmployeeView());
		SwingUtilities.updateComponentTreeUI(this.mainView);
	}
	
	
	/**
	 * Switches the currently displayed content area to the employee display view.
	 * 
	 * @param displayEmployeeController The controller of the display employee view. Can be null, if the view has not been called before or should not be reused.
	 */
	public void switchToDisplayEmployeeView(final DisplayEmployeeController displayEmployeeController) {
		DisplayEmployeeController controller;
		
		if(displayEmployeeController != null)
			controller = displayEmployeeController;
		else
			controller = new DisplayEmployeeController(this);
		
		this.mainView.getContentPane().removeAll();
		this.mainView.getContentPane().add(controller.getDisplayEmployeeView());
		SwingUtilities.updateComponentTreeUI(this.mainView);
	}
	
	
	public MainView getMainView() {
		return mainView;
	}

	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}
}
