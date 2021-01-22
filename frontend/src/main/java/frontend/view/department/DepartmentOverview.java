package frontend.view.department;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;

import frontend.controller.department.DepartmentOverviewController;
import frontend.view.components.DepartmentTableModel;
import frontend.view.components.ToolTipTableCellRenderer;

import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JToolBar;

/**
 * This view provides means to view, create and delete departments.
 * 
 * @author Michael
 */
public class DepartmentOverview extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources;
	
	/**
	 * Table for department display.
	 */
	private JTable tableDepartment;

	/**
	 * Controller of this view.
	 */
	@SuppressWarnings("unused")
	private DepartmentOverviewController departmentOverviewController;
	
	
	/**
	 * Create the panel.
	 * 
	 * @param departmentOverviewController The controller of this view.
	 */
	public DepartmentOverview(final DepartmentOverviewController departmentOverviewController) {
		this.resources = ResourceBundle.getBundle("frontend");
		this.departmentOverviewController = departmentOverviewController;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {161, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0};
		setLayout(gridBagLayout);
		
		JLabel textLabel = new JLabel(this.resources.getString("gui.dept.header.overview"));
		textLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_textLabel = new GridBagConstraints();
		gbc_textLabel.anchor = GridBagConstraints.WEST;
		gbc_textLabel.insets = new Insets(5, 5, 5, 5);
		gbc_textLabel.gridx = 0;
		gbc_textLabel.gridy = 0;
		add(textLabel, gbc_textLabel);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		GridBagConstraints gbc_toolBar = new GridBagConstraints();
		gbc_toolBar.anchor = GridBagConstraints.EAST;
		gbc_toolBar.insets = new Insets(0, 0, 5, 5);
		gbc_toolBar.gridx = 1;
		gbc_toolBar.gridy = 1;
		add(toolBar, gbc_toolBar);
		
		URL imgDeleteURL = getClass().getResource("/icons/delete.png");
		JButton btnDeleteDepartment = new JButton("", new ImageIcon(imgDeleteURL));
		btnDeleteDepartment.setToolTipText(this.resources.getString("gui.dept.deleteButton"));
		toolBar.add(btnDeleteDepartment);
		btnDeleteDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				departmentOverviewController.deleteSelectedDepartment(e);
			}
		});
		
		JScrollPane scrollPaneTable = new JScrollPane();
		GridBagConstraints gbc_scrollPaneTable = new GridBagConstraints();
		gbc_scrollPaneTable.insets = new Insets(0, 5, 5, 5);
		gbc_scrollPaneTable.gridwidth = 2;
		gbc_scrollPaneTable.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneTable.gridx = 0;
		gbc_scrollPaneTable.gridy = 2;
		add(scrollPaneTable, gbc_scrollPaneTable);
		
		this.tableDepartment = new JTable(3,3);
		scrollPaneTable.setViewportView(tableDepartment);
		this.tableDepartment.setModel(new DepartmentTableModel());
		
		JButton btnCancel = new JButton(this.resources.getString("gui.general.cancel"));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				departmentOverviewController.cancelHandler(e);
			}
		});
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.anchor = GridBagConstraints.WEST;
		gbc_btnCancel.insets = new Insets(0, 5, 5, 5);
		gbc_btnCancel.gridx = 0;
		gbc_btnCancel.gridy = 3;
		add(btnCancel, gbc_btnCancel);
		this.tableDepartment.getColumnModel().getColumn(2).setCellRenderer(new ToolTipTableCellRenderer());
	}


	/**
	 * @return the tableDepartment
	 */
	public JTable getTableDepartment() {
		return tableDepartment;
	}


	/**
	 * @param tableDepartment the tableDepartment to set
	 */
	public void setTableDepartment(JTable tableDepartment) {
		this.tableDepartment = tableDepartment;
	}
}
