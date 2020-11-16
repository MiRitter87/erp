package frontend.view;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JTextField;

import frontend.controller.DepartmentController;
import frontend.model.ComboBoxItem;
import frontend.view.components.DepartmentTableModel;
import frontend.view.components.ExtendedDocumentFilter;
import frontend.view.components.ToolTipTableCellRenderer;

import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.text.AbstractDocument;

/**
 * This view provides means to view, create and delete departments.
 * 
 * @author Michael
 */
public class DepartmentView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources;
	
	/**
	 * Input field for department code.
	 */
	private JTextField textFieldDeptCode;
	
	/**
	 * Input field for department name.
	 */
	private JTextField textFieldName;
	
	/**
	 * Text area for department description text.
	 */
	private JTextArea textAreaDescription;
	
	/**
	 * Combo Box for selection of department manager.
	 */
	private JComboBox<ComboBoxItem> cbHead;
	
	/**
	 * Table for department display.
	 */
	private JTable tableDepartment;

	/**
	 * Controller of this view.
	 */
	@SuppressWarnings("unused")
	private DepartmentController departmentController;
	
	
	/**
	 * Create the panel.
	 * 
	 * @param departmentController The controller of this view.
	 */
	public DepartmentView(final DepartmentController departmentController) {
		this.resources = ResourceBundle.getBundle("frontend");
		this.departmentController = departmentController;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {161, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 80, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel textLabel = new JLabel(this.resources.getString("gui.dept.header"));
		textLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_textLabel = new GridBagConstraints();
		gbc_textLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_textLabel.anchor = GridBagConstraints.NORTH;
		gbc_textLabel.insets = new Insets(0, 0, 5, 5);
		gbc_textLabel.gridx = 0;
		gbc_textLabel.gridy = 0;
		add(textLabel, gbc_textLabel);
		
		JButton btnAddDept = new JButton(this.resources.getString("gui.dept.addButton"));
		btnAddDept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				departmentController.addDepartmentHandler(e);
			}
		});
		GridBagConstraints gbc_btnAddDept = new GridBagConstraints();
		gbc_btnAddDept.anchor = GridBagConstraints.NORTH;
		gbc_btnAddDept.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddDept.gridx = 0;
		gbc_btnAddDept.gridy = 5;
		add(btnAddDept, gbc_btnAddDept);
		
		JLabel lblDeptCode = new JLabel(this.resources.getString("gui.dept.code"));
		GridBagConstraints gbc_lblDeptToken = new GridBagConstraints();
		gbc_lblDeptToken.insets = new Insets(0, 0, 5, 5);
		gbc_lblDeptToken.gridx = 0;
		gbc_lblDeptToken.gridy = 1;
		add(lblDeptCode, gbc_lblDeptToken);
		
		textFieldDeptCode = new JTextField();
		((AbstractDocument)textFieldDeptCode.getDocument()).setDocumentFilter(new ExtendedDocumentFilter(5, false));
		GridBagConstraints gbc_textFieldDeptCode = new GridBagConstraints();
		gbc_textFieldDeptCode.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDeptCode.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldDeptCode.gridx = 1;
		gbc_textFieldDeptCode.gridy = 1;
		add(textFieldDeptCode, gbc_textFieldDeptCode);
		
		JLabel lblName = new JLabel(this.resources.getString("gui.dept.name"));
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 2;
		add(lblName, gbc_lblName);
		
		textFieldName = new JTextField();
		((AbstractDocument)textFieldName.getDocument()).setDocumentFilter(new ExtendedDocumentFilter(50, false));
		GridBagConstraints gbc_textFieldName = new GridBagConstraints();
		gbc_textFieldName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldName.gridx = 1;
		gbc_textFieldName.gridy = 2;
		add(textFieldName, gbc_textFieldName);
		
		JLabel lblDescription = new JLabel(this.resources.getString("gui.dept.description"));
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 3;
		add(lblDescription, gbc_lblDescription);
		
		JScrollPane scrollPaneDescription = new JScrollPane();
		GridBagConstraints gbc_scrollPaneDescription = new GridBagConstraints();
		gbc_scrollPaneDescription.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneDescription.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPaneDescription.gridx = 1;
		gbc_scrollPaneDescription.gridy = 3;
		add(scrollPaneDescription, gbc_scrollPaneDescription);
		
		textAreaDescription = new JTextArea();
		((AbstractDocument)textAreaDescription.getDocument()).setDocumentFilter(new ExtendedDocumentFilter(250, false));
		textAreaDescription.setLineWrap(true);
		scrollPaneDescription.setViewportView(textAreaDescription);
		
		JLabel lblHead = new JLabel(this.resources.getString("gui.dept.head"));
		GridBagConstraints gbc_lblHead = new GridBagConstraints();
		gbc_lblHead.insets = new Insets(0, 0, 5, 5);
		gbc_lblHead.gridx = 0;
		gbc_lblHead.gridy = 4;
		add(lblHead, gbc_lblHead);
		
		cbHead = new JComboBox<ComboBoxItem>();
		GridBagConstraints gbc_cbHead = new GridBagConstraints();
		gbc_cbHead.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbHead.insets = new Insets(0, 0, 5, 0);
		gbc_cbHead.gridx = 1;
		gbc_cbHead.gridy = 4;
		add(cbHead, gbc_cbHead);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		GridBagConstraints gbc_toolBar = new GridBagConstraints();
		gbc_toolBar.insets = new Insets(0, 0, 5, 0);
		gbc_toolBar.gridx = 1;
		gbc_toolBar.gridy = 5;
		add(toolBar, gbc_toolBar);
		
		URL imgDeleteURL = getClass().getResource("/icons/delete.png");
		JButton btnDeleteDepartment = new JButton("", new ImageIcon(imgDeleteURL));
		btnDeleteDepartment.setToolTipText(this.resources.getString("gui.dept.deleteButton"));
		toolBar.add(btnDeleteDepartment);
		btnDeleteDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				departmentController.deleteSelectedDepartment(e);
			}
		});
		
		JScrollPane scrollPaneTable = new JScrollPane();
		GridBagConstraints gbc_scrollPaneTable = new GridBagConstraints();
		gbc_scrollPaneTable.gridwidth = 2;
		gbc_scrollPaneTable.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneTable.gridx = 0;
		gbc_scrollPaneTable.gridy = 6;
		add(scrollPaneTable, gbc_scrollPaneTable);
		
		this.tableDepartment = new JTable(3,3);
		scrollPaneTable.setViewportView(tableDepartment);
		this.tableDepartment.setModel(new DepartmentTableModel());
		this.tableDepartment.getColumnModel().getColumn(2).setCellRenderer(new ToolTipTableCellRenderer());
	}


	public JComboBox<ComboBoxItem> getCbHead() {
		return cbHead;
	}


	public void setCbHead(JComboBox<ComboBoxItem> cbHead) {
		this.cbHead = cbHead;
	}


	public JTextField getTextFieldDeptCode() {
		return textFieldDeptCode;
	}


	public void setTextFieldDeptCode(JTextField textFieldDeptCode) {
		this.textFieldDeptCode = textFieldDeptCode;
	}


	public JTextField getTextFieldName() {
		return textFieldName;
	}


	public void setTextFieldName(JTextField textFieldName) {
		this.textFieldName = textFieldName;
	}

	
	/**
	 * @return the textAreaDescription
	 */
	public JTextArea getTextAreaDescription() {
		return textAreaDescription;
	}


	/**
	 * @param textAreaDescription the textAreaDescription to set
	 */
	public void setTextAreaDescription(JTextArea textAreaDescription) {
		this.textAreaDescription = textAreaDescription;
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
