package frontend.controller.employee;

import frontend.controller.MainViewController;
import frontend.view.employee.DisplayEmployeeView;

/**
 * Controller of the "display employee"-view.
 * 
 * @author Michael
 */
public class DisplayEmployeeController {
	/**
	 * The controller of the main view.
	 */
	private MainViewController mainViewController;
	
	/**
	 * The view for employee display.
	 */
	private DisplayEmployeeView displayEmployeeView;

	
	/**
	 * Initializes the controller
	 * 
	 * @param mainViewController The controller of the main view.
	 */
	public DisplayEmployeeController(final MainViewController mainViewController) {
		this.displayEmployeeView = new DisplayEmployeeView(this);
		this.mainViewController = mainViewController;
	}
	
	
	/**
	 * @return the displayEmployeeView
	 */
	public DisplayEmployeeView getDisplayEmployeeView() {
		return displayEmployeeView;
	}
}
