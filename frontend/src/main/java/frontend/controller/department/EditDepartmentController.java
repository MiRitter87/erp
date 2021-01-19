package frontend.controller.department;

import java.awt.event.ItemEvent;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import frontend.controller.MainViewController;
import frontend.model.ComboBoxItem;
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
		
		//Initialize the departments for the selection.
		try {
			this.departments.setDepartments(this.departmentWebServiceDao.getDepartments());
			this.initializeDepartmentComboBox();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(this.editDepartmentView, e.getMessage(), 
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
			this.editDepartmentView.getCbDepartment().addItem(item);
		}
		
		this.editDepartmentView.getCbDepartment().setSelectedIndex(0);
	}
	
	
	/**
	 * Handles selections performed at the department selection combo box.
	 * 
	 * @param itemEvent Indicates ComboBox item changed.
	 */
	public void cbDepartmentItemStateChanged(ItemEvent itemEvent) {
		if (itemEvent.getStateChange() != ItemEvent.SELECTED)
			return;
		
    	ComboBoxItem selectedItem = (ComboBoxItem) itemEvent.getItem();
        
    	//Selection of empty department: Clear input fields
    	if(selectedItem.getId() == "") {
    		this.selectedDepartment = null;
    		this.editDepartmentView.getLblCodeContent().setText("");
    		this.editDepartmentView.getTextFieldName().setText("");
    		this.editDepartmentView.getTextFieldDescription().setText("");
    		//TODO Set Head ComboBox
    	}
    	else {
    		//Department selected: Fill input fields accordingly.
    		this.selectedDepartment = this.departments.getDepartmentByCode(selectedItem.getId());
    		
    		if(this.selectedDepartment != null) {
    			this.editDepartmentView.getLblCodeContent().setText(this.selectedDepartment.getCode());
    			this.editDepartmentView.getTextFieldName().setText(this.selectedDepartment.getName());
    			this.editDepartmentView.getTextFieldDescription().setText(this.selectedDepartment.getDescription());
    			//TODO Set Head ComboBox
    		}
    	}
	}


	/**
	 * @return the editDepartmentView
	 */
	public EditDepartmentView getEditDepartmentView() {
		return editDepartmentView;
	}
}
