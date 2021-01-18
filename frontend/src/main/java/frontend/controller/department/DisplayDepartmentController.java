package frontend.controller.department;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import frontend.controller.MainViewController;
import frontend.dao.DepartmentWebServiceDao;
import frontend.model.ComboBoxItem;
import frontend.model.Department;
import frontend.model.DepartmentList;
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
	 * Access to localized application resources.
	 */
	private ResourceBundle resources;
	
	/**
	 * Access to department data using a WebService.
	 */
	private DepartmentWebServiceDao departmentWebServiceDao;
	
	
	/**
	 * Initializes the controller.
	 * 
	 * @param mainViewController The controller of the main view.
	 */
	public DisplayDepartmentController(final MainViewController mainViewController) {
		this.mainViewController = mainViewController;
		this.displayDepartmentView = new DisplayDepartmentView(this);
		this.resources = ResourceBundle.getBundle("frontend");
		this.departments = new DepartmentList();
		this.selectedDepartment = null;
		
		//Initialize the departments for the selection.
		try {
			this.departmentWebServiceDao = new DepartmentWebServiceDao();
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
	 * Provides a list with ComboBoxItems for the given departments. An empty item to de-select an entry in the ComboBox is provided first.
	 * 
	 * @param departments The departments for which the ComboBoxItems are being created.
	 * @return A list with ComboBoxItems for all departments.
	 */
	private List<ComboBoxItem> getDepartmentItemsForComboBox(final DepartmentList departments) {
		List<ComboBoxItem> items = new ArrayList<ComboBoxItem>();
		StringBuilder builder;
		
		//An initial ComboBox entry allowing the user to de-select.
		items.add(new ComboBoxItem("", ""));
		
		//One ComboBox entry for each department.
		for(Department tempDepartment:departments.getDepartments()) {
			builder = new StringBuilder();
			builder.append(tempDepartment.getCode());
			builder.append(" - ");
			builder.append(tempDepartment.getName());
			
			items.add(new ComboBoxItem(tempDepartment.getCode(), builder.toString()));
		}
		
		return items;
	}


	/**
	 * @return the displayDepartmentView
	 */
	public DisplayDepartmentView getDisplayDepartmentView() {
		return displayDepartmentView;
	}
}
