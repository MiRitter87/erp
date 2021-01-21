package frontend.controller.department;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import frontend.controller.MainViewController;
import frontend.view.department.CreateDepartmentView;

/**
 * Controller of the "create department"-view.
 * 
 * @author Michael
 */
public class CreateDepartmentController extends DepartmentController {
	/**
	 * The view for department creation.
	 */
	private CreateDepartmentView createDepartmentView;
	
	/**
	 * Application logging.
	 */
	public static final Logger logger = LogManager.getLogger(CreateDepartmentController.class);
	
	
	/**
	 * Initializes the controller
	 * 
	 * @param mainViewController The controller of the main view.
	 */
	public CreateDepartmentController(MainViewController mainViewController) {
		super(mainViewController);
		this.createDepartmentView = new CreateDepartmentView(this);
	}


	/**
	 * @return the createDepartmentView
	 */
	public CreateDepartmentView getCreateDepartmentView() {
		return createDepartmentView;
	}

}
