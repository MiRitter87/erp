package frontend.view.employee;

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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import frontend.controller.employee.EmployeeOverviewController;
import frontend.view.components.EmployeeTableModel;


/**
 * This view provides means to view, add and delete employees.
 * 
 * @author Michael
 */
public class EmployeeOverview extends JPanel {

	private static final long serialVersionUID = 1L;
	
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
	private EmployeeOverviewController employeeOverviewController;
	

	/**
	 * Create the frame.
	 * 
	 * @param employeeOverviewController The controller of this view.
	 */
	public EmployeeOverview(final EmployeeOverviewController employeeOverviewController) {
		this.resources = ResourceBundle.getBundle("frontend");
		this.employeeOverviewController = employeeOverviewController;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{161, 0};
		gridBagLayout.rowHeights = new int[] {0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0};
		this.setLayout(gridBagLayout);
		
		JLabel textLabel = new JLabel(this.resources.getString("gui.employee.header.overview"));
		textLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_textLabel = new GridBagConstraints();
		gbc_textLabel.insets = new Insets(0, 0, 6, 6);
		gbc_textLabel.gridx = 0;
		gbc_textLabel.gridy = 0;
		this.add(textLabel, gbc_textLabel);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		GridBagConstraints gbc_toolBar = new GridBagConstraints();
		gbc_toolBar.insets = new Insets(0, 0, 5, 0);
		gbc_toolBar.gridx = 1;
		gbc_toolBar.gridy = 1;
		add(toolBar, gbc_toolBar);
		
		URL imgMoneyURL = getClass().getResource("/icons/money.png");
		JButton btnSalary = new JButton("", new ImageIcon(imgMoneyURL));
		toolBar.add(btnSalary);
		btnSalary.setToolTipText(this.resources.getString("gui.employee.salary.toolTip"));
		btnSalary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				employeeOverviewController.switchToSalaryView();
			}
		});
		
		URL imgDeleteURL = getClass().getResource("/icons/delete.png");
		JButton btnDeleteEmployee = new JButton("", new ImageIcon(imgDeleteURL));
		toolBar.add(btnDeleteEmployee);
		btnDeleteEmployee.setToolTipText(this.resources.getString("gui.employee.deleteButton"));
		btnDeleteEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				employeeOverviewController.deleteSelectedEmployee(arg0);
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		this.add(scrollPane, gbc_scrollPane);
		
		tableEmployee = new JTable(3,3);
		scrollPane.setViewportView(tableEmployee);
		tableEmployee.setModel(new EmployeeTableModel());
		
		JButton btnCancel = new JButton(this.resources.getString("gui.general.cancel"));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				employeeOverviewController.cancelHandler(arg0);
			}
		});
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.gridx = 0;
		gbc_btnCancel.gridy = 3;
		add(btnCancel, gbc_btnCancel);
	}


	public JTable getTableEmployee() {
		return tableEmployee;
	}


	public void setTableEmployee(JTable tableEmployee) {
		this.tableEmployee = tableEmployee;
	}
}
