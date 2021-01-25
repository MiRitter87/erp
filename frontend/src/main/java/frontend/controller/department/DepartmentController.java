package frontend.controller.department;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.ws.WebServiceException;

import frontend.controller.MainViewController;
import frontend.dao.DepartmentWebServiceDao;
import frontend.dao.EmployeeWebServiceDao;
import frontend.model.ComboBoxItem;
import frontend.model.Department;
import frontend.model.DepartmentList;

/**
 * Controls functions of the department management, that are shared between different views.
 * 
 * @author Michael
 */
public class DepartmentController {
	/**
	 * The controller of the main view.
	 */
	protected MainViewController mainViewController;
	
	/**
	 * Access to localized application resources.
	 */
	protected ResourceBundle resources;
	
	/**
	 * Access to department data using a WebService.
	 */
	protected DepartmentWebServiceDao departmentWebServiceDao;
	
	/**
	 * Access to employee data using a WebService.
	 */
	protected EmployeeWebServiceDao employeeWebServiceDao;
	
	
	/**
	 * Initializes the controller.
	 * 
	 * @param mainViewController The controller of the main view.
	 * @throws WebServiceException In case the WebService is unavailable.
	 */
	public DepartmentController(final MainViewController mainViewController) throws WebServiceException {
		this.mainViewController = mainViewController;
		this.resources = ResourceBundle.getBundle("frontend");
		this.departmentWebServiceDao = new DepartmentWebServiceDao();
		this.employeeWebServiceDao = new EmployeeWebServiceDao();
	}
	
	
	/**
	 * Provides a list with ComboBoxItems for the given departments. An empty item to de-select an entry in the ComboBox is provided first.
	 * 
	 * @param departments The departments for which the ComboBoxItems are being created.
	 * @return A list with ComboBoxItems for all departments.
	 */
	protected List<ComboBoxItem> getDepartmentItemsForComboBox(final DepartmentList departments) {
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
	 * Validates the user input of common fields that are shared between both the creation and editing view.
	 * 
	 * @param name The department name.
	 * @param selectedHead The selected head of department.
	 * @exception Exception Indicating failed validation.
	 */
	protected void validateInput(final String name, final ComboBoxItem selectedHead) throws Exception {
		//A department name has to be given
		if(name.length() == 0)
			throw new Exception(this.resources.getString("gui.dept.error.nameValidation"));
		
		//A head of department has to be selected
		if(selectedHead.getId().equals(""))
			throw new Exception(this.resources.getString("gui.dept.error.noHeadSelected"));
	}
}
