package frontend.view.department;

import javax.swing.JPanel;

import frontend.controller.department.CreateDepartmentController;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ResourceBundle;
import java.awt.Font;

/**
 * View to create new departments.
 * 
 * @author Michael
 */
public class CreateDepartmentView extends JPanel {

	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = -5277004031390897415L;
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources;
	
	/**
	 * The controller of this view.
	 */
	private CreateDepartmentController createDepartmentController;

	
	/**
	 * Create the panel.
	 * 
	 * @param createDepartmentController The controller of this view.
	 */
	public CreateDepartmentView(final CreateDepartmentController createDepartmentController) {
		this.resources = ResourceBundle.getBundle("frontend");
		this.createDepartmentController = createDepartmentController;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblHeader = new JLabel(this.resources.getString("gui.dept.header.create"));
		lblHeader.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblHeader = new GridBagConstraints();
		gbc_lblHeader.anchor = GridBagConstraints.WEST;
		gbc_lblHeader.insets = new Insets(5, 5, 0, 0);
		gbc_lblHeader.gridx = 0;
		gbc_lblHeader.gridy = 0;
		add(lblHeader, gbc_lblHeader);

	}

}
