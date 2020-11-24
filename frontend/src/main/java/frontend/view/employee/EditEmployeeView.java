package frontend.view.employee;

import java.util.ResourceBundle;

import javax.swing.JPanel;

import frontend.controller.employee.EditEmployeeController;
import frontend.model.ComboBoxItem;
import frontend.view.components.ExtendedDocumentFilter;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * View to change employee data.
 * 
 * @author Michael
 */
public class EditEmployeeView extends JPanel {

	/**
	 * Default serialization ID.
	 */
	private static final long serialVersionUID = -2686971631450822227L;
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources;
	
	/**
	 * Controller of this view.
	 */
	@SuppressWarnings("unused")
	private EditEmployeeController editEmployeeController;
	
	/**
	 * The combo box for employee selection.
	 */
	private JComboBox<ComboBoxItem> cbEmployee;
	
	/**
	 * Input field for first name of employee.
	 */	
	private JTextField textFieldFirstName;
	
	/**
	 * Input field for last name of employee.
	 */	
	private JTextField textFieldLastName;
	
	/**
	 * ComboBox for gender selection.
	 */
	private JComboBox<ComboBoxItem> cbGender;

	
	/**
	 * Create the panel.
	 * 
	 * @param editEmployeeController The controller of this view.
	 */
	public EditEmployeeView(final EditEmployeeController editEmployeeController) {
		this.resources = ResourceBundle.getBundle("frontend");
		this.editEmployeeController = editEmployeeController;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblHeader = new JLabel(this.resources.getString("gui.employee.header.edit"));
		lblHeader.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblHeader = new GridBagConstraints();
		gbc_lblHeader.insets = new Insets(0, 0, 5, 5);
		gbc_lblHeader.gridx = 0;
		gbc_lblHeader.gridy = 0;
		add(lblHeader, gbc_lblHeader);
		
		JLabel lblEmployee = new JLabel(this.resources.getString("gui.employee"));
		GridBagConstraints gbc_lblEmployee = new GridBagConstraints();
		gbc_lblEmployee.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmployee.gridx = 0;
		gbc_lblEmployee.gridy = 1;
		add(lblEmployee, gbc_lblEmployee);
		
		cbEmployee = new JComboBox<ComboBoxItem>();
		cbEmployee.addItemListener(editEmployeeController::cbEmployeeItemStateChanged);
		GridBagConstraints gbc_cbEmployee = new GridBagConstraints();
		gbc_cbEmployee.insets = new Insets(0, 0, 5, 0);
		gbc_cbEmployee.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbEmployee.gridx = 1;
		gbc_cbEmployee.gridy = 1;
		gbc_cbEmployee.gridwidth = 2;
		add(cbEmployee, gbc_cbEmployee);
		
		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.gridwidth = 3;
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 2;
		add(separator, gbc_separator);
		
		JLabel lblFirstName = new JLabel(this.resources.getString("gui.employee.firstName"));
		GridBagConstraints gbc_lblFirstName = new GridBagConstraints();
		gbc_lblFirstName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFirstName.gridx = 0;
		gbc_lblFirstName.gridy = 3;
		add(lblFirstName, gbc_lblFirstName);
		
		textFieldFirstName = new JTextField();
		((AbstractDocument)textFieldFirstName.getDocument()).setDocumentFilter(new ExtendedDocumentFilter(50, false));
		GridBagConstraints gbc_textFieldFirstName = new GridBagConstraints();
		gbc_textFieldFirstName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldFirstName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldFirstName.gridx = 1;
		gbc_textFieldFirstName.gridy = 3;
		gbc_textFieldFirstName.gridwidth = 2;
		add(textFieldFirstName, gbc_textFieldFirstName);
		textFieldFirstName.setColumns(10);
		
		JLabel lblLastName = new JLabel(this.resources.getString("gui.employee.lastName"));
		GridBagConstraints gbc_lblLastName = new GridBagConstraints();
		gbc_lblLastName.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastName.gridx = 0;
		gbc_lblLastName.gridy = 4;
		add(lblLastName, gbc_lblLastName);
		
		textFieldLastName = new JTextField();
		((AbstractDocument)textFieldLastName.getDocument()).setDocumentFilter(new ExtendedDocumentFilter(50, false));
		GridBagConstraints gbc_textFieldLastName = new GridBagConstraints();
		gbc_textFieldLastName.gridwidth = 2;
		gbc_textFieldLastName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldLastName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldLastName.gridx = 1;
		gbc_textFieldLastName.gridy = 4;
		add(textFieldLastName, gbc_textFieldLastName);
		textFieldLastName.setColumns(10);
		
		JLabel lblGender = new JLabel(this.resources.getString("gui.employee.gender"));
		GridBagConstraints gbc_lblGender = new GridBagConstraints();
		gbc_lblGender.insets = new Insets(0, 0, 5, 5);
		gbc_lblGender.gridx = 0;
		gbc_lblGender.gridy = 5;
		add(lblGender, gbc_lblGender);
		
		cbGender = new JComboBox<ComboBoxItem>();
		GridBagConstraints gbc_cbGender = new GridBagConstraints();
		gbc_cbGender.gridwidth = 2;
		gbc_cbGender.insets = new Insets(0, 0, 5, 0);
		gbc_cbGender.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbGender.gridx = 1;
		gbc_cbGender.gridy = 5;
		add(cbGender, gbc_cbGender);
		
		JButton btnSave = new JButton(this.resources.getString("gui.general.save"));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent saveEvent) {
				editEmployeeController.saveEmployeeHandler(saveEvent);
			}
		});
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.insets = new Insets(0, 0, 5, 5);
		gbc_btnSave.gridx = 0;
		gbc_btnSave.gridy = 6;
		add(btnSave, gbc_btnSave);
		
		JButton btnCancel = new JButton(this.resources.getString("gui.general.cancel"));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent cancelEvent) {
				editEmployeeController.cancelHandler(cancelEvent);
			}
		});
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 5, 0);
		gbc_btnCancel.gridx = 1;
		gbc_btnCancel.gridy = 6;
		add(btnCancel, gbc_btnCancel);
		
		JButton btnSalary = new JButton(this.resources.getString("gui.employee.salary.toolTip"));
		btnSalary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent salaryDataEvent) {
				editEmployeeController.btnSalaryDataHandler(salaryDataEvent);
			}
		});
		GridBagConstraints gbc_btnSalary = new GridBagConstraints();
		gbc_btnSalary.gridx = 2;
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
	 * @return the cbGender
	 */
	public JComboBox<ComboBoxItem> getCbGender() {
		return cbGender;
	}

	/**
	 * @return the textFieldFirstName
	 */
	public JTextField getTextFieldFirstName() {
		return textFieldFirstName;
	}

	/**
	 * @return the textFieldLastName
	 */
	public JTextField getTextFieldLastName() {
		return textFieldLastName;
	}
}
