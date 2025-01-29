package frontend.controller.department;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.List;

import javax.swing.JOptionPane;
import jakarta.xml.ws.WebServiceException;

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
    public static final Logger LOGGER = LogManager.getLogger(DisplayDepartmentController.class);

    /**
     * Initializes the controller.
     *
     * @param mainViewController The controller of the main view.
     * @throws WebServiceException In case the WebService is unavailable.
     */
    public DisplayDepartmentController(final MainViewController mainViewController) throws WebServiceException {
        super(mainViewController);
        this.displayDepartmentView = new DisplayDepartmentView(this);
        this.departments = new DepartmentList();
        this.selectedDepartment = null;

        // Initialize the departments for the selection.
        try {
            this.departments.setDepartments(this.getDepartmentWebServiceDao().getDepartments());
            this.initializeDepartmentComboBox();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.displayDepartmentView, e.getMessage(),
                    this.getResources().getString("gui.error"), JOptionPane.ERROR_MESSAGE);
            LOGGER.error("Error while trying to read departments from WebService: " + e.getMessage());
        }
    }

    /**
     * Initializes the combo box for department selection. All departments are being displayed by code and name.
     */
    private void initializeDepartmentComboBox() {
        List<ComboBoxItem> items = this.getDepartmentItemsForComboBox(this.departments);

        for (ComboBoxItem item : items) {
            this.displayDepartmentView.getCbDepartment().addItem(item);
        }

        this.displayDepartmentView.getCbDepartment().setSelectedIndex(0);
    }

    /**
     * Handles selections performed at the department selection combo box.
     *
     * @param itemEvent Indicates ComboBox item changed.
     */
    public void cbDepartmentItemStateChanged(final ItemEvent itemEvent) {
        if (itemEvent.getStateChange() != ItemEvent.SELECTED) {
            return;
        }

        ComboBoxItem selectedItem = (ComboBoxItem) itemEvent.getItem();

        // Selection of empty department: Clear input fields
        if (selectedItem.getId() == "") {
            this.selectedDepartment = null;
            this.displayDepartmentView.getLblCodeContent().setText("");
            this.displayDepartmentView.getLblNameContent().setText("");
            this.displayDepartmentView.getLblDescriptionConent().setText("");
            this.displayDepartmentView.getLblHeadContent().setText("");
        } else {
            // Department selected: Fill input fields accordingly.
            this.selectedDepartment = this.departments.getDepartmentByCode(selectedItem.getId());

            if (this.selectedDepartment != null) {
                this.displayDepartmentView.getLblCodeContent().setText(this.selectedDepartment.getCode());
                this.displayDepartmentView.getLblNameContent().setText(this.selectedDepartment.getName());
                this.displayDepartmentView.getLblDescriptionConent().setText(this.selectedDepartment.getDescription());
                this.displayDepartmentView.getLblHeadContent().setText(this.selectedDepartment.getHead().getFullName());
            }
        }
    }

    /**
     * Handles a click at the "cancel"-button.
     *
     * @param cancelEvent The action event of the button click.
     */
    public void cancelHandler(final ActionEvent cancelEvent) {
        this.getMainViewController().switchToStartpage();
    }

    /**
     * @return the displayDepartmentView
     */
    public DisplayDepartmentView getDisplayDepartmentView() {
        return displayDepartmentView;
    }
}
