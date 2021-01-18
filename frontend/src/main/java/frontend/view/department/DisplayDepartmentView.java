package frontend.view.department;

import java.util.ResourceBundle;

import javax.swing.JPanel;

import frontend.controller.department.DisplayDepartmentController;
import frontend.model.ComboBoxItem;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JButton;

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
	 * ComboBox for department selection.
	 */
	private JComboBox<ComboBoxItem> cbDepartment;
	
	/**
	 * Label for display of department code.
	 */
	private JLabel lblCodeContent;
	
	/**
	 * Label for display of department name.
	 */
	private JLabel lblNameContent;
	
	/**
	 * Label for display of department description.
	 */
	private JLabel lblDescriptionConent;
	
	/**
	 * Label for display of department head.
	 */
	private JLabel lblHeadContent;
	

	/**
	 * Create the panel.
	 */
	public DisplayDepartmentView(final DisplayDepartmentController displayDepartmentController) {
		this.resources = ResourceBundle.getBundle("frontend");
		this.displayDepartmentController = displayDepartmentController;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblHeader = new JLabel(this.resources.getString("gui.dept.header.display"));
		lblHeader.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblHeader = new GridBagConstraints();
		gbc_lblHeader.insets = new Insets(0, 0, 5, 5);
		gbc_lblHeader.gridx = 0;
		gbc_lblHeader.gridy = 0;
		add(lblHeader, gbc_lblHeader);
		
		JLabel lblDepartment = new JLabel(this.resources.getString("gui.dept.department"));
		GridBagConstraints gbc_lblDepartment = new GridBagConstraints();
		gbc_lblDepartment.insets = new Insets(0, 0, 5, 5);
		gbc_lblDepartment.gridx = 0;
		gbc_lblDepartment.gridy = 1;
		add(lblDepartment, gbc_lblDepartment);
		
		cbDepartment = new JComboBox<ComboBoxItem>();
		GridBagConstraints gbc_cbDepartment = new GridBagConstraints();
		gbc_cbDepartment.insets = new Insets(0, 0, 5, 0);
		gbc_cbDepartment.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbDepartment.gridx = 1;
		gbc_cbDepartment.gridy = 1;
		add(cbDepartment, gbc_cbDepartment);
		
		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridwidth = 2;
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 2;
		add(separator, gbc_separator);
		
		JLabel lblCode = new JLabel(this.resources.getString("gui.dept.code"));
		GridBagConstraints gbc_lblCode = new GridBagConstraints();
		gbc_lblCode.insets = new Insets(0, 0, 5, 5);
		gbc_lblCode.gridx = 0;
		gbc_lblCode.gridy = 3;
		add(lblCode, gbc_lblCode);
		
		lblCodeContent = new JLabel("");
		GridBagConstraints gbc_lblCodeContent = new GridBagConstraints();
		gbc_lblCodeContent.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblCodeContent.insets = new Insets(0, 0, 5, 0);
		gbc_lblCodeContent.gridx = 1;
		gbc_lblCodeContent.gridy = 3;
		add(lblCodeContent, gbc_lblCodeContent);
		
		JLabel lblName = new JLabel(this.resources.getString("gui.dept.name"));
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 4;
		add(lblName, gbc_lblName);
		
		lblNameContent = new JLabel("");
		GridBagConstraints gbc_lblNameContent = new GridBagConstraints();
		gbc_lblNameContent.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNameContent.insets = new Insets(0, 0, 5, 0);
		gbc_lblNameContent.gridx = 1;
		gbc_lblNameContent.gridy = 4;
		add(lblNameContent, gbc_lblNameContent);
		
		JLabel lblDescription = new JLabel(this.resources.getString("gui.dept.description"));
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 5;
		add(lblDescription, gbc_lblDescription);
		
		lblDescriptionConent = new JLabel("");
		GridBagConstraints gbc_lblDescriptionValue = new GridBagConstraints();
		gbc_lblDescriptionValue.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDescriptionValue.insets = new Insets(0, 0, 5, 0);
		gbc_lblDescriptionValue.gridx = 1;
		gbc_lblDescriptionValue.gridy = 5;
		add(lblDescriptionConent, gbc_lblDescriptionValue);
		
		JLabel lblHead = new JLabel(this.resources.getString("gui.dept.head"));
		GridBagConstraints gbc_lblHead = new GridBagConstraints();
		gbc_lblHead.insets = new Insets(0, 0, 5, 5);
		gbc_lblHead.gridx = 0;
		gbc_lblHead.gridy = 6;
		add(lblHead, gbc_lblHead);
		
		lblHeadContent = new JLabel("");
		GridBagConstraints gbc_lblHeadContent = new GridBagConstraints();
		gbc_lblHeadContent.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblHeadContent.insets = new Insets(0, 0, 5, 0);
		gbc_lblHeadContent.gridx = 1;
		gbc_lblHeadContent.gridy = 6;
		add(lblHeadContent, gbc_lblHeadContent);
		
		JButton btnCancel = new JButton(this.resources.getString("gui.general.cancel"));
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.gridx = 0;
		gbc_btnCancel.gridy = 7;
		add(btnCancel, gbc_btnCancel);
	}


	/**
	 * @return the cbDepartment
	 */
	public JComboBox<ComboBoxItem> getCbDepartment() {
		return cbDepartment;
	}


	/**
	 * @return the lblCodeContent
	 */
	public JLabel getLblCodeContent() {
		return lblCodeContent;
	}


	/**
	 * @return the lblNameContent
	 */
	public JLabel getLblNameContent() {
		return lblNameContent;
	}


	/**
	 * @return the lblDescriptionConent
	 */
	public JLabel getLblDescriptionConent() {
		return lblDescriptionConent;
	}


	/**
	 * @return the lblHeadContent
	 */
	public JLabel getLblHeadContent() {
		return lblHeadContent;
	}
}
