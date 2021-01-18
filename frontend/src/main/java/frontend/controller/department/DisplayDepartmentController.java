package frontend.controller.department;

import java.util.List;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import frontend.controller.MainViewController;
import frontend.model.ComboBoxItem;
import frontend.model.Department;
import frontend.model.DepartmentList;
import frontend.view.department.DisplayDepartmentView;

/**
 * Controller of the "display department"-view.
 * 
 * @author Michael
 */
public class DisplayDepartmentController extends DepartmentController {
	/**
	 * The view for department display.
	 */
	private DisplayDepartmentView displayDepartmentView;
	
	/**
	 * The departments of the application. Those are candidates for the "display"-function.
	 */
	private DepartmentList departments;
	
	/**
	 * The currently selected department for display.
	 */
	private Department selectedDepartment;
	
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
		super(mainViewController);
		this.displayDepartmentView = new DisplayDepartmentView(this);
		this.departments = new DepartmentList();
		this.selectedDepartment = null;
		
		//Initialize the departments for the selection.
		try {
			this.departments.setDepartments(this.departmentWebServiceDao.getDepartments());
			this.initializeDepartmentComboBox();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(this.displayDepartmentView, e.getMessage(), 
					this.resources.getString("gui.error"), JOptionPane.ERROR_MESSAGE);
			logger.info("Error while trying to read departments from WebService: " +e.getMessage());
		}
	}
	
	
	/**
	 * Initializes the combo box for department selection.
	 * All departments are being displayed by code and name.
	 */
	private void initializeDepartmentComboBox() {
		List<ComboBoxItem> items = this.getDepartmentItemsForComboBox(this.departments);
		
		for(ComboBoxItem item:items) {
			this.displayDepartmentView.getCbDepartment().addItem(item);
		}
		
		this.displayDepartmentView.getCbDepartment().setSelectedIndex(0);
	}


	/**
	 * @return the displayDepartmentView
	 */
	public DisplayDepartmentView getDisplayDepartmentView() {
		return displayDepartmentView;
	}
}
