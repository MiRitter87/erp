package frontend.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.text.AbstractDocument;

import frontend.controller.EmployeeBasicDataController;
import frontend.model.ComboBoxItem;
import frontend.view.components.ExtendedDocumentFilter;
import frontend.view.components.EmployeeTableModel;


/**
 * This view provides means to view, add and delete employees.
 * 
 * @author Michael
 */
public class EmployeeBasicDataView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Input field for employee id.
	 */	
	private JTextField textFieldEmployeeId;
	
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
	 * Table for display of employees.
	 */
	private JTable tableEmployee;
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources;
	
	/**
	 * Controller of this view.
	 */
	@SuppressWarnings("unused")
	private EmployeeBasicDataController employeeBasicDataController;
	

	/**
	 * Create the frame.
	 * 
	 * @param employeeBasicDataController The controller of this view.
	 */
	public EmployeeBasicDataView(final EmployeeBasicDataController employeeBasicDataController) {
		this.resources = ResourceBundle.getBundle("frontend");
		this.employeeBasicDataController = employeeBasicDataController;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{161, 0};
		gridBagLayout.rowHeights = new int[]{16, 0, 0, 0, 0, 39, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		this.setLayout(gridBagLayout);
		
		JLabel textLabel = new JLabel(this.resources.getString("gui.employee.header.create"));
		textLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_textLabel = new GridBagConstraints();
		gbc_textLabel.anchor = GridBagConstraints.NORTH;
		gbc_textLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_textLabel.insets = new Insets(0, 0, 6, 6);
		gbc_textLabel.gridx = 0;
		gbc_textLabel.gridy = 0;
		this.add(textLabel, gbc_textLabel);
		
		JButton btnAddEmployee = new JButton(this.resources.getString("gui.employee.addButton"));
		btnAddEmployee.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						employeeBasicDataController.addEmployeeHandler(e);						
					}
				});
		
		JLabel lblEmployeeId = new JLabel(this.resources.getString("gui.employee.id"));
		GridBagConstraints gbc_lblEmployeeId = new GridBagConstraints();
		gbc_lblEmployeeId.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmployeeId.gridx = 0;
		gbc_lblEmployeeId.gridy = 1;
		this.add(lblEmployeeId, gbc_lblEmployeeId);
		
		textFieldEmployeeId = new JTextField();
		((AbstractDocument)textFieldEmployeeId.getDocument()).setDocumentFilter(new ExtendedDocumentFilter(4, true));
		GridBagConstraints gbc_textFieldEmployeeId = new GridBagConstraints();
		gbc_textFieldEmployeeId.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldEmployeeId.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldEmployeeId.gridx = 1;
		gbc_textFieldEmployeeId.gridy = 1;
		this.add(textFieldEmployeeId, gbc_textFieldEmployeeId);
		
		
		JLabel lblFirstName = new JLabel(this.resources.getString("gui.employee.firstName"));
		GridBagConstraints gbc_lblFirstName = new GridBagConstraints();
		gbc_lblFirstName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFirstName.gridx = 0;
		gbc_lblFirstName.gridy = 2;
		this.add(lblFirstName, gbc_lblFirstName);
		
		textFieldFirstName = new JTextField();
		((AbstractDocument)textFieldFirstName.getDocument()).setDocumentFilter(new ExtendedDocumentFilter(50, false));
		GridBagConstraints gbc_textFieldFirstName = new GridBagConstraints();
		gbc_textFieldFirstName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldFirstName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldFirstName.gridx = 1;
		gbc_textFieldFirstName.gridy = 2;
		this.add(textFieldFirstName, gbc_textFieldFirstName);
		
		JLabel lblLastName = new JLabel(this.resources.getString("gui.employee.lastName"));
		GridBagConstraints gbc_lblLastName = new GridBagConstraints();
		gbc_lblLastName.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastName.gridx = 0;
		gbc_lblLastName.gridy = 3;
		this.add(lblLastName, gbc_lblLastName);
		
		textFieldLastName = new JTextField();
		((AbstractDocument)textFieldLastName.getDocument()).setDocumentFilter(new ExtendedDocumentFilter(50, false));
		GridBagConstraints gbc_textFieldLastName = new GridBagConstraints();
		gbc_textFieldLastName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldLastName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldLastName.gridx = 1;
		gbc_textFieldLastName.gridy = 3;
		this.add(textFieldLastName, gbc_textFieldLastName);
		
		JLabel lblGender = new JLabel(this.resources.getString("gui.employee.gender"));
		GridBagConstraints gbc_lblGender = new GridBagConstraints();
		gbc_lblGender.insets = new Insets(0, 0, 5, 5);
		gbc_lblGender.gridx = 0;
		gbc_lblGender.gridy = 4;
		this.add(lblGender, gbc_lblGender);
		
		cbGender = new JComboBox<ComboBoxItem>();
		GridBagConstraints gbc_cbGender = new GridBagConstraints();
		gbc_cbGender.insets = new Insets(0, 0, 5, 0);
		gbc_cbGender.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbGender.gridx = 1;
		gbc_cbGender.gridy = 4;
		this.add(cbGender, gbc_cbGender);
		
		GridBagConstraints gbc_btnAddEmployee = new GridBagConstraints();
		gbc_btnAddEmployee.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddEmployee.anchor = GridBagConstraints.NORTH;
		gbc_btnAddEmployee.gridx = 0;
		gbc_btnAddEmployee.gridy = 5;
		this.add(btnAddEmployee, gbc_btnAddEmployee);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		GridBagConstraints gbc_toolBar = new GridBagConstraints();
		gbc_toolBar.insets = new Insets(0, 0, 5, 0);
		gbc_toolBar.gridx = 1;
		gbc_toolBar.gridy = 5;
		add(toolBar, gbc_toolBar);
		
		URL imgMoneyURL = getClass().getResource("/icons/money.png");
		JButton btnSalary = new JButton("", new ImageIcon(imgMoneyURL));
		toolBar.add(btnSalary);
		btnSalary.setToolTipText(this.resources.getString("gui.employee.salary.toolTip"));
		btnSalary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				employeeBasicDataController.switchToSalaryView();
			}
		});
		
		URL imgDeleteURL = getClass().getResource("/icons/delete.png");
		JButton btnDeleteEmployee = new JButton("", new ImageIcon(imgDeleteURL));
		toolBar.add(btnDeleteEmployee);
		btnDeleteEmployee.setToolTipText(this.resources.getString("gui.employee.deleteButton"));
		btnDeleteEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				employeeBasicDataController.deleteSelectedEmployee(arg0);
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 6;
		this.add(scrollPane, gbc_scrollPane);
		
		tableEmployee = new JTable(3,3);
		scrollPane.setViewportView(tableEmployee);
		tableEmployee.setModel(new EmployeeTableModel());
	}

	
	public JTextField getTextFieldEmployeeId() {
		return textFieldEmployeeId;
	}


	public void setTextFieldEmployeeId(JTextField textFieldEmployeeId) {
		this.textFieldEmployeeId = textFieldEmployeeId;
	}


	public JTextField getTextFieldFirstName() {
		return textFieldFirstName;
	}


	public void setTextFieldFirstName(JTextField textFieldFirstName) {
		this.textFieldFirstName = textFieldFirstName;
	}


	public JTextField getTextFieldLastName() {
		return textFieldLastName;
	}


	public void setTextFieldLastName(JTextField textFieldLastName) {
		this.textFieldLastName = textFieldLastName;
	}


	public JTable getTableEmployee() {
		return tableEmployee;
	}


	public void setTableEmployee(JTable tableEmployee) {
		this.tableEmployee = tableEmployee;
	}


	public JComboBox<ComboBoxItem> getCbGender() {
		return cbGender;
	}


	public void setCbGender(JComboBox<ComboBoxItem> cbGender) {
		this.cbGender = cbGender;
	}
}
