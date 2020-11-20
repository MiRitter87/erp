package frontend.controller;


import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
	 * 
	 * @param employeeOverviewController The controller of the employee overview. Can be null, if the view has not been called before or should not be recycled.
	 */
	public void switchToEmployeeBasicDataView(final EmployeeBasicDataController employeeOverviewController) {
		EmployeeBasicDataController controller;
		
		if(employeeOverviewController != null)
			controller = employeeOverviewController;
		else
			controller = new EmployeeBasicDataController(this);
			
		this.mainView.getContentPane().removeAll();
		this.mainView.getContentPane().add(controller.getEmployeeBasicDataView());
		SwingUtilities.updateComponentTreeUI(this.mainView);
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
	 * 
	 * @param editEmployeeController The controller of the edit employee view. Can be null, if the view has not been called before should not be recycled.
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
	
	
	public MainView getMainView() {
		return mainView;
	}

	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}
}
