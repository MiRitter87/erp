package frontend.controller.department;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import frontend.controller.MainViewController;
import frontend.model.Department;
import frontend.model.DepartmentList;
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
	 * The currently selected department for editing.
	 */
	private Department selectedDepartment;
	
	/**
	 * Application logging.
	 */
	public static final Logger logger = LogManager.getLogger(EditDepartmentController.class);
	
	
	/**
	 * Initializes the controller
	 * 
	 * @param mainViewController The controller of the main view.
	 */
	public EditDepartmentController(MainViewController mainViewController) {
		super(mainViewController);
		this.editDepartmentView = new EditDepartmentView(this);
		this.departments = new DepartmentList();
		this.selectedDepartment = null;
	}


	/**
	 * @return the editDepartmentView
	 */
	public EditDepartmentView getEditDepartmentView() {
		return editDepartmentView;
	}
}
