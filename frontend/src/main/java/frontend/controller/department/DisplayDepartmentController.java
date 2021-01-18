package frontend.controller.department;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import frontend.controller.MainViewController;
import frontend.view.department.DisplayDepartmentView;

/**
 * Controller of the "display department"-view.
 * 
 * @author Michael
 */
public class DisplayDepartmentController {
	/**
	 * The view for department display.
	 */
	private DisplayDepartmentView displayDepartmentView;
	
	/**
	 * The controller of the main view.
	 */
	private MainViewController mainViewController;
	
	/**
	 * Application logging.
	 */
	public static final Logger logger = LogManager.getLogger(DisplayDepartmentController.class);
	
	
	/**
	 * Initializes the controller.
	 * 
	 * @param mainViewController The controller of the main view.
	 */
	public DisplayDepartmentController(final MainViewController mainViewController) {
		this.mainViewController = mainViewController;
		this.displayDepartmentView = new DisplayDepartmentView(this);
	}


	/**
	 * @return the displayDepartmentView
	 */
	public DisplayDepartmentView getDisplayDepartmentView() {
		return displayDepartmentView;
	}
}
