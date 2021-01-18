package frontend.view.department;

import java.util.ResourceBundle;

import javax.swing.JPanel;

import frontend.controller.department.DisplayDepartmentController;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;

/**
 * The view for display of a department.
 * 
 * @author Michael
 */
public class DisplayDepartmentView extends JPanel {
	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = -8946977428192423838L;
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources;
	
	/**
	 * The controller of this view.
	 */
	private DisplayDepartmentController displayDepartmentController;

	/**
	 * Create the panel.
	 */
	public DisplayDepartmentView(final DisplayDepartmentController displayDepartmentController) {
		this.resources = ResourceBundle.getBundle("frontend");
		this.displayDepartmentController = displayDepartmentController;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblHeader = new JLabel(this.resources.getString("gui.dept.header.display"));
		lblHeader.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblHeader = new GridBagConstraints();
		gbc_lblHeader.gridx = 0;
		gbc_lblHeader.gridy = 0;
		add(lblHeader, gbc_lblHeader);
	}

}
