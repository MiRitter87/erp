package frontend.view.department;

import java.util.ResourceBundle;

import javax.swing.JPanel;

import frontend.controller.department.EditDepartmentController;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;

/**
 * View to edit department data.
 * 
 * @author Michael
 */
public class EditDepartmentView extends JPanel {
	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = -1197741571105021632L;
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources;
	
	/**
	 * The controller of this view.
	 */
	private EditDepartmentController editDepartmentController;

	/**
	 * Create the panel.
	 * 
	 * @param editDepartmentController The controller of this view.
	 */
	public EditDepartmentView(final EditDepartmentController editDepartmentController) {
		this.resources = ResourceBundle.getBundle("frontend");
		this.editDepartmentController = editDepartmentController;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblHeader = new JLabel(this.resources.getString("gui.dept.header.edit"));
		GridBagConstraints gbc_lblHeader = new GridBagConstraints();
		gbc_lblHeader.gridx = 0;
		gbc_lblHeader.gridy = 0;
		add(lblHeader, gbc_lblHeader);
	}

}
