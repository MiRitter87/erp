package frontend.controller;


import javax.swing.JPanel;

import frontend.controller.employee.EditEmployeeController;
import frontend.controller.employee.EmployeeSalaryController;
import frontend.controller.employee.CreateEmployeeController;
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
	 */
	public void switchToEmployeeBasicDataView() {
		EmployeeBasicDataController employeeBasicDataController = new EmployeeBasicDataController(this);
		this.mainView.getContentPane().removeAll();
		this.mainView.getContentPane().add(employeeBasicDataController.getEmployeeBasicDataView());
		this.mainView.revalidate();
	}
	
	
	/**
	 * Switches the currently displayed content area to the employee salary view.
	 * 
	 * @param salaryController The controller of the employee salary view.
	 */
	public void switchToSalaryView(final EmployeeSalaryController salaryController) {
		this.mainView.getContentPane().removeAll();
		this.mainView.getContentPane().add(salaryController.getEmployeeSalaryView());
		this.mainView.revalidate();
	}
	
	
	/**
	 * Switches the currently displayed content area to the department view.
	 */
	public void switchToDepartmentView() {
		DepartmentController controller = new DepartmentController();
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
	 */
	public void switchToEditEmployeeView() {
		EditEmployeeController controller = new EditEmployeeController(this);
		this.mainView.getContentPane().removeAll();
		this.mainView.getContentPane().add(controller.getEditEmployeeView());
		this.mainView.revalidate();
	}
	
	
	public MainView getMainView() {
		return mainView;
	}

	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}
}
