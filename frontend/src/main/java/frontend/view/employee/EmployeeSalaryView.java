package frontend.view.employee;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import frontend.controller.employee.EmployeeSalaryController;
import frontend.view.components.ExtendedDocumentFilter;

import java.awt.GridBagLayout;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.JButton;

import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This view allows the user to set, modify and delete salary data for an employee.
 * 
 * @author Michael
 */
public class EmployeeSalaryView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Controller of this view.
	 */
	private EmployeeSalaryController employeeSalaryController;
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources;
	
	/**
	 * Text field for salary data.
	 */
	private JTextField textFieldSalary;
	
	/**
	 * The label displaying the date of the last salary change
	 */
	private JLabel lblLastChangeValue;
	

	/**
	 * Create the panel.
	 */
	public EmployeeSalaryView(final EmployeeSalaryController employeeSalaryController) {
		this.resources = ResourceBundle.getBundle("frontend");
		this.employeeSalaryController = employeeSalaryController;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel textLabel = new JLabel(MessageFormat.format(this.resources.getString("gui.employee.salary.header"),
				this.employeeSalaryController.getSelectedEmployee().getFirstName(),
				this.employeeSalaryController.getSelectedEmployee().getLastName()));
		textLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_textLabel = new GridBagConstraints();
		gbc_textLabel.insets = new Insets(0, 0, 5, 5);
		gbc_textLabel.gridx = 0;
		gbc_textLabel.gridy = 0;
		add(textLabel, gbc_textLabel);
		
		JButton btnBack = new JButton(this.resources.getString("gui.general.back"));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Switch back to the calling view. This can be employee overview or edit view.
				employeeSalaryController.getMainViewController().switchToEmployeeBasicDataView();
			}
		});
		
		JLabel lblMonthlySalary = new JLabel(this.resources.getString("gui.employee.salary.monthlySalary"));
		GridBagConstraints gbc_lblMonthlySalary = new GridBagConstraints();
		gbc_lblMonthlySalary.insets = new Insets(0, 0, 5, 5);
		gbc_lblMonthlySalary.gridx = 0;
		gbc_lblMonthlySalary.gridy = 1;
		add(lblMonthlySalary, gbc_lblMonthlySalary);
		
		textFieldSalary = new JTextField();
		((AbstractDocument)textFieldSalary.getDocument()).setDocumentFilter(new ExtendedDocumentFilter(6, true));
		GridBagConstraints gbc_textFieldSalary = new GridBagConstraints();
		gbc_textFieldSalary.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldSalary.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldSalary.gridx = 1;
		gbc_textFieldSalary.gridy = 1;
		add(textFieldSalary, gbc_textFieldSalary);
		textFieldSalary.setColumns(10);
		
		JLabel lblLastChange = new JLabel(this.resources.getString("gui.employee.salary.lastChange"));
		GridBagConstraints gbc_lblLastChange = new GridBagConstraints();
		gbc_lblLastChange.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastChange.gridx = 0;
		gbc_lblLastChange.gridy = 2;
		add(lblLastChange, gbc_lblLastChange);
		
		lblLastChangeValue = new JLabel("");
		GridBagConstraints gbc_lblLastChangeValue = new GridBagConstraints();
		gbc_lblLastChangeValue.insets = new Insets(0, 0, 5, 0);
		gbc_lblLastChangeValue.gridx = 1;
		gbc_lblLastChangeValue.gridy = 2;
		add(lblLastChangeValue, gbc_lblLastChangeValue);
		
		JLabel lblCurrency = new JLabel("€");
		GridBagConstraints gbc_lblCurrency = new GridBagConstraints();
		gbc_lblCurrency.insets = new Insets(0, 5, 5, 5);
		gbc_lblCurrency.gridx = 2;
		gbc_lblCurrency.gridy = 1;
		add(lblCurrency, gbc_lblCurrency);
		GridBagConstraints gbc_btnzurck = new GridBagConstraints();
		gbc_btnzurck.insets = new Insets(0, 0, 0, 5);
		gbc_btnzurck.gridx = 0;
		gbc_btnzurck.gridy = 9;
		add(btnBack, gbc_btnzurck);
		
		JButton btnSave = new JButton(this.resources.getString("gui.general.save"));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				employeeSalaryController.saveSalaryHandler(e);
			}
		});
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.gridx = 1;
		gbc_btnSave.gridy = 9;
		add(btnSave, gbc_btnSave);
	}


	public JTextField getTextFieldSalary() {
		return textFieldSalary;
	}


	public void setTextFieldSalary(JTextField textFieldSalary) {
		this.textFieldSalary = textFieldSalary;
	}


	public JLabel getLblLastChangeValue() {
		return lblLastChangeValue;
	}


	public void setLblLastChangeValue(JLabel lblLastChangeValue) {
		this.lblLastChangeValue = lblLastChangeValue;
	}
}
