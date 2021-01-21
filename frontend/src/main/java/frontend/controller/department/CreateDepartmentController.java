package frontend.controller.department;

import java.util.List;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import frontend.controller.MainViewController;
import frontend.controller.employee.EmployeeController;
import frontend.model.ComboBoxItem;
import frontend.model.EmployeeList;
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
	 * The employees that are managed by the employee view. Those are candidates for the "head of department" ComboBox.
	 */
	private EmployeeList employees;
	
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
		this.employees = new EmployeeList();
		
		//Initialization of employee data for head selection combo box.
		try {
			this.employees.setEmployees(this.employeeWebServiceDao.getEmployees());
			this.initializeHeadComboBox();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(this.createDepartmentView, e.getMessage(), 
					this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			logger.error("Error while trying to read employees from WebService: " +e.getMessage());
		}
	}
	
	
	/**
	 * Initializes the ComboBox for head of department selection.
	 * All employees are being displayed by id, first name and second name.
	 */
	private void initializeHeadComboBox() {
		List<ComboBoxItem> items = EmployeeController.getEmployeeItemsForComboBox(this.employees);
		
		for(ComboBoxItem item:items) {
			this.createDepartmentView.getCbHead().addItem(item);
		}
	}


	/**
	 * @return the createDepartmentView
	 */
	public CreateDepartmentView getCreateDepartmentView() {
		return createDepartmentView;
	}

}
