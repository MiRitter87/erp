package frontend.view.employee;

import javax.swing.JPanel;

import frontend.controller.employee.DisplayEmployeeController;
import frontend.model.ComboBoxItem;

import java.awt.GridBagLayout;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DisplayEmployeeView extends JPanel {

	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = 6053886693075244052L;
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources;
	
	/**
	 * Controller of this view.
	 */
	@SuppressWarnings("unused")
	private DisplayEmployeeController displayEmployeeController;
	
	/**
	 * The combo box for employee selection.
	 */
	private JComboBox<ComboBoxItem> cbEmployee;
	
	/**
	 * Label for display of employee first name.
	 */
	private JLabel lblFirstNameContent;
	
	/**
	 * Label for display of employee last name.
	 */
	private JLabel lblLastNameContent;
	
	/**
	 * Label for display of employee gender.
	 */
	private JLabel lblGenderContent;

	
	/**
	 * Create the panel.
	 */
	public DisplayEmployeeView(final DisplayEmployeeController displayEmployeeController) {
		this.resources = ResourceBundle.getBundle("frontend");
		this.displayEmployeeController = displayEmployeeController;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblHeader = new JLabel(this.resources.getString("gui.employee.header.display"));
		lblHeader.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblHeader = new GridBagConstraints();
		gbc_lblHeader.gridwidth = 2;
		gbc_lblHeader.anchor = GridBagConstraints.WEST;
		gbc_lblHeader.insets = new Insets(5, 5, 5, 5);
		gbc_lblHeader.gridx = 0;
		gbc_lblHeader.gridy = 0;
		add(lblHeader, gbc_lblHeader);
		
		JLabel lblEmployee = new JLabel(this.resources.getString("gui.employee"));
		GridBagConstraints gbc_lblEmployee = new GridBagConstraints();
		gbc_lblEmployee.anchor = GridBagConstraints.WEST;
		gbc_lblEmployee.insets = new Insets(0, 5, 5, 5);
		gbc_lblEmployee.gridx = 0;
		gbc_lblEmployee.gridy = 1;
		add(lblEmployee, gbc_lblEmployee);
		
		cbEmployee = new JComboBox<ComboBoxItem>();
		cbEmployee.addItemListener(displayEmployeeController::cbEmployeeItemStateChanged);
		GridBagConstraints gbc_cbEmployee = new GridBagConstraints();
		gbc_cbEmployee.insets = new Insets(0, 50, 5, 5);
		gbc_cbEmployee.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbEmployee.gridx = 1;
		gbc_cbEmployee.gridy = 1;
		add(cbEmployee, gbc_cbEmployee);
		
		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridwidth = 2;
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 2;
		add(separator, gbc_separator);
		
		JLabel lblFirstName = new JLabel(this.resources.getString("gui.employee.firstName"));
		GridBagConstraints gbc_lblFirstName = new GridBagConstraints();
		gbc_lblFirstName.anchor = GridBagConstraints.WEST;
		gbc_lblFirstName.insets = new Insets(0, 5, 5, 5);
		gbc_lblFirstName.gridx = 0;
		gbc_lblFirstName.gridy = 3;
		add(lblFirstName, gbc_lblFirstName);
		
		lblFirstNameContent = new JLabel("");
		GridBagConstraints gbc_lblFirstNameContent = new GridBagConstraints();
		gbc_lblFirstNameContent.insets = new Insets(0, 50, 5, 0);
		gbc_lblFirstNameContent.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblFirstNameContent.gridx = 1;
		gbc_lblFirstNameContent.gridy = 3;
		add(lblFirstNameContent, gbc_lblFirstNameContent);
		
		JLabel lblLastName = new JLabel(this.resources.getString("gui.employee.lastName"));
		GridBagConstraints gbc_lblLastName = new GridBagConstraints();
		gbc_lblLastName.anchor = GridBagConstraints.WEST;
		gbc_lblLastName.insets = new Insets(0, 5, 5, 5);
		gbc_lblLastName.gridx = 0;
		gbc_lblLastName.gridy = 4;
		add(lblLastName, gbc_lblLastName);
		
		lblLastNameContent = new JLabel("");
		GridBagConstraints gbc_lblLastNameContent = new GridBagConstraints();
		gbc_lblLastNameContent.insets = new Insets(0, 50, 5, 0);
		gbc_lblLastNameContent.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblLastNameContent.gridx = 1;
		gbc_lblLastNameContent.gridy = 4;
		add(lblLastNameContent, gbc_lblLastNameContent);
		
		JLabel lblGender = new JLabel(this.resources.getString("gui.employee.gender"));
		GridBagConstraints gbc_lblGender = new GridBagConstraints();
		gbc_lblGender.anchor = GridBagConstraints.WEST;
		gbc_lblGender.insets = new Insets(0, 5, 5, 5);
		gbc_lblGender.gridx = 0;
		gbc_lblGender.gridy = 5;
		add(lblGender, gbc_lblGender);
		
		lblGenderContent = new JLabel("");
		GridBagConstraints gbc_lblGenderContent = new GridBagConstraints();
		gbc_lblGenderContent.insets = new Insets(0, 50, 5, 0);
		gbc_lblGenderContent.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblGenderContent.gridx = 1;
		gbc_lblGenderContent.gridy = 5;
		add(lblGenderContent, gbc_lblGenderContent);
		
		JButton btnCancel = new JButton(this.resources.getString("gui.general.cancel"));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayEmployeeController.cancelHandler(arg0);
			}
		});
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.anchor = GridBagConstraints.WEST;
		gbc_btnCancel.insets = new Insets(0, 5, 0, 5);
		gbc_btnCancel.gridx = 0;
		gbc_btnCancel.gridy = 6;
		add(btnCancel, gbc_btnCancel);
		
		JButton btnSalary = new JButton(this.resources.getString("gui.employee.salary.toolTip"));
		btnSalary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayEmployeeController.btnSalaryDataHandler(e);
			}
		});
		GridBagConstraints gbc_btnSalary = new GridBagConstraints();
		gbc_btnSalary.anchor = GridBagConstraints.WEST;
		gbc_btnSalary.insets = new Insets(0, 50, 0, 5);
		gbc_btnSalary.gridx = 1;
		gbc_btnSalary.gridy = 6;
		add(btnSalary, gbc_btnSalary);

	}


	/**
	 * @return the cbEmployee
	 */
	public JComboBox<ComboBoxItem> getCbEmployee() {
		return cbEmployee;
	}


	/**
	 * @return the lblFirstNameContent
	 */
	public JLabel getLblFirstNameContent() {
		return lblFirstNameContent;
	}


	/**
	 * @return the lblLastNameContent
	 */
	public JLabel getLblLastNameContent() {
		return lblLastNameContent;
	}


	/**
	 * @return the lblGenderContent
	 */
	public JLabel getLblGenderContent() {
		return lblGenderContent;
	}
}
