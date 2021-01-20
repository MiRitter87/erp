package frontend.view.department;

import java.util.ResourceBundle;

import javax.swing.JPanel;

import frontend.controller.department.EditDepartmentController;
import frontend.model.ComboBoxItem;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JButton;

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
	@SuppressWarnings("unused")
	private EditDepartmentController editDepartmentController;
	
	/**
	 * ComboBox for department selection.
	 */
	private JComboBox<ComboBoxItem> cbDepartment;
	
	/**
	 * ComboBox for head of department selection.
	 */
	private JComboBox<ComboBoxItem> cbHead;
	
	/**
	 * Label for display of department code.
	 */
	private JLabel lblCodeContent;
	
	/**
	 * Input field for department name.
	 */
	private JTextField textFieldName;
	
	/**
	 * Input field for department description
	 */
	private JTextField textFieldDescription;

	
	/**
	 * Create the panel.
	 * 
	 * @param editDepartmentController The controller of this view.
	 */
	public EditDepartmentView(final EditDepartmentController editDepartmentController) {
		this.resources = ResourceBundle.getBundle("frontend");
		this.editDepartmentController = editDepartmentController;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblHeader = new JLabel(this.resources.getString("gui.dept.header.edit"));
		lblHeader.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblHeader = new GridBagConstraints();
		gbc_lblHeader.gridwidth = 2;
		gbc_lblHeader.anchor = GridBagConstraints.WEST;
		gbc_lblHeader.insets = new Insets(5, 5, 5, 5);
		gbc_lblHeader.gridx = 0;
		gbc_lblHeader.gridy = 0;
		add(lblHeader, gbc_lblHeader);
		
		JLabel lblDepartment = new JLabel(this.resources.getString("gui.dept.department"));
		GridBagConstraints gbc_lblDepartment = new GridBagConstraints();
		gbc_lblDepartment.anchor = GridBagConstraints.WEST;
		gbc_lblDepartment.insets = new Insets(0, 5, 5, 5);
		gbc_lblDepartment.gridx = 0;
		gbc_lblDepartment.gridy = 1;
		add(lblDepartment, gbc_lblDepartment);
		
		cbDepartment = new JComboBox<ComboBoxItem>();
		cbDepartment.addItemListener(editDepartmentController::cbDepartmentItemStateChanged);
		GridBagConstraints gbc_cbDepartment = new GridBagConstraints();
		gbc_cbDepartment.insets = new Insets(0, 50, 5, 5);
		gbc_cbDepartment.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbDepartment.gridx = 1;
		gbc_cbDepartment.gridy = 1;
		add(cbDepartment, gbc_cbDepartment);
		
		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.gridwidth = 2;
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 2;
		add(separator, gbc_separator);
		
		JLabel lblCode = new JLabel(this.resources.getString("gui.dept.code"));
		GridBagConstraints gbc_lblCode = new GridBagConstraints();
		gbc_lblCode.anchor = GridBagConstraints.WEST;
		gbc_lblCode.insets = new Insets(0, 5, 5, 5);
		gbc_lblCode.gridx = 0;
		gbc_lblCode.gridy = 3;
		add(lblCode, gbc_lblCode);
		
		lblCodeContent = new JLabel("");
		GridBagConstraints gbc_lblCodeContent = new GridBagConstraints();
		gbc_lblCodeContent.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblCodeContent.insets = new Insets(0, 50, 5, 5);
		gbc_lblCodeContent.gridx = 1;
		gbc_lblCodeContent.gridy = 3;
		add(lblCodeContent, gbc_lblCodeContent);
		
		JLabel lblName = new JLabel(this.resources.getString("gui.dept.name"));
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.WEST;
		gbc_lblName.insets = new Insets(0, 5, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 4;
		add(lblName, gbc_lblName);
		
		textFieldName = new JTextField();
		GridBagConstraints gbc_textFieldName = new GridBagConstraints();
		gbc_textFieldName.insets = new Insets(0, 50, 5, 5);
		gbc_textFieldName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldName.gridx = 1;
		gbc_textFieldName.gridy = 4;
		add(textFieldName, gbc_textFieldName);
		textFieldName.setColumns(10);
		
		JLabel lblDescription = new JLabel(this.resources.getString("gui.dept.description"));
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.anchor = GridBagConstraints.WEST;
		gbc_lblDescription.insets = new Insets(0, 5, 5, 5);
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 5;
		add(lblDescription, gbc_lblDescription);
		
		textFieldDescription = new JTextField();
		GridBagConstraints gbc_textFieldDescription = new GridBagConstraints();
		gbc_textFieldDescription.insets = new Insets(0, 50, 5, 5);
		gbc_textFieldDescription.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDescription.gridx = 1;
		gbc_textFieldDescription.gridy = 5;
		add(textFieldDescription, gbc_textFieldDescription);
		textFieldDescription.setColumns(10);
		
		JLabel lblHead = new JLabel(this.resources.getString("gui.dept.head"));
		GridBagConstraints gbc_lblHead = new GridBagConstraints();
		gbc_lblHead.anchor = GridBagConstraints.WEST;
		gbc_lblHead.insets = new Insets(0, 5, 5, 5);
		gbc_lblHead.gridx = 0;
		gbc_lblHead.gridy = 6;
		add(lblHead, gbc_lblHead);
		
		cbHead = new JComboBox<ComboBoxItem>();
		GridBagConstraints gbc_cbHead = new GridBagConstraints();
		gbc_cbHead.insets = new Insets(0, 50, 5, 5);
		gbc_cbHead.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbHead.gridx = 1;
		gbc_cbHead.gridy = 6;
		add(cbHead, gbc_cbHead);
		
		JButton btnSave = new JButton(this.resources.getString("gui.general.save"));
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.anchor = GridBagConstraints.WEST;
		gbc_btnSave.insets = new Insets(0, 5, 0, 5);
		gbc_btnSave.gridx = 0;
		gbc_btnSave.gridy = 7;
		add(btnSave, gbc_btnSave);
		
		JButton btnCancel = new JButton(this.resources.getString("gui.general.cancel"));
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.anchor = GridBagConstraints.WEST;
		gbc_btnCancel.insets = new Insets(0, 50, 0, 5);
		gbc_btnCancel.gridx = 1;
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
	 * @return the cbHead
	 */
	public JComboBox<ComboBoxItem> getCbHead() {
		return cbHead;
	}

	
	/**
	 * @return the lblCodeContent
	 */
	public JLabel getLblCodeContent() {
		return lblCodeContent;
	}


	/**
	 * @return the textFieldName
	 */
	public JTextField getTextFieldName() {
		return textFieldName;
	}

	
	/**
	 * @return the textFieldDescription
	 */
	public JTextField getTextFieldDescription() {
		return textFieldDescription;
	}
}
