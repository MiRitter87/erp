package frontend.controller;


import javax.swing.JPanel;

import frontend.controller.employee.EditEmployeeController;
import frontend.controller.employee.CreateEmployeeController;
import frontend.view.MainView;
import frontend.view.View;

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
	 * The controller of the employee basic data view.
	 */
	private EmployeeBasicDataController employeeBasicDataController;
	
	/**
	 * The controller of the department view.
	 */
	private DepartmentController departmentController;
	
		
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
		this.employeeBasicDataController = new EmployeeBasicDataController(this);
		this.mainView.getContentPane().removeAll();
		this.mainView.getContentPane().add(this.employeeBasicDataController.getEmployeeBasicDataView());
		this.mainView.revalidate();
	}
	
	
	/**
	 * Switches the currently displayed content area to the employee salary view.
	 */
	public void switchToSalaryView() {
		this.mainView.getContentPane().removeAll();
		this.mainView.getContentPane().add(this.employeeBasicDataController.getEmployeeSalaryController().getEmployeeSalaryView());
		this.mainView.revalidate();
	}
	
	
	/**
	 * Switches the currently displayed content area to the department view.
	 */
	public void switchToDepartmentView() {
		this.departmentController = new DepartmentController();
		this.mainView.getContentPane().removeAll();
		this.mainView.getContentPane().add(this.departmentController.getDepartmentView());
		this.mainView.revalidate();
	}
	
	
	/**
	 * Navigates to the given target view.
	 * 
	 * @param targetView The enumeration of the navigation target.
	 */
	public void navigateToView(final View targetView) {
		switch(targetView) {
			case STARTPAGE: {
				this.switchToStartpage();
				break;
			}
			case EMPLOYEE_CREATE: {
				this.switchToCreateEmployeeView();
				break;
			}
			case EMPLOYEE_EDIT: {
				this.switchToEditEmployeeView();
				break;
			}
		}
	}
	
	
	/**
	 * Switches the currently displayed content area to the start page.
	 */
	protected void switchToStartpage() {
		this.mainView.getContentPane().removeAll();
		this.mainView.getContentPane().add(new JPanel());
		this.mainView.revalidate();
	}
	
	
	/**
	 * Switches the currently displayed content area to the employee creation view.
	 */
	protected void switchToCreateEmployeeView() {
		CreateEmployeeController controller = new CreateEmployeeController(this);
		this.mainView.getContentPane().removeAll();
		this.mainView.getContentPane().add(controller.getCreateEmployeeView());
		this.mainView.revalidate();
	}
	
	
	/**
	 * Switches the currently displayed content area to the employee edit view.
	 */
	protected void switchToEditEmployeeView() {
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
