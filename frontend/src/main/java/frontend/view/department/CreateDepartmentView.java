package frontend.view.department;

import javax.swing.JPanel;

import frontend.controller.department.CreateDepartmentController;
import frontend.model.ComboBoxItem;
import frontend.view.components.ExtendedDocumentFilter;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JButton;

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
	@SuppressWarnings("unused")
	private CreateDepartmentController createDepartmentController;
	
	/**
	 * Input field for department code.
	 */
	private JTextField textFieldCode;
	
	/**
	 * Input field for department name.
	 */
	private JTextField textFieldName;
	
	/**
	 * Input field for department description.
	 */
	JTextArea textAreaDescription;
	
	/**
	 * ComboBox for head of department selection.
	 */
	JComboBox<ComboBoxItem> cbHead;

	
	/**
	 * Create the panel.
	 * 
	 * @param createDepartmentController The controller of this view.
	 */
	public CreateDepartmentView(final CreateDepartmentController createDepartmentController) {
		this.resources = ResourceBundle.getBundle("frontend");
		this.createDepartmentController = createDepartmentController;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[] {0, 0, 0, 80, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblHeader = new JLabel(this.resources.getString("gui.dept.header.create"));
		lblHeader.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblHeader = new GridBagConstraints();
		gbc_lblHeader.gridwidth = 2;
		gbc_lblHeader.anchor = GridBagConstraints.WEST;
		gbc_lblHeader.insets = new Insets(5, 5, 5, 0);
		gbc_lblHeader.gridx = 0;
		gbc_lblHeader.gridy = 0;
		add(lblHeader, gbc_lblHeader);
		
		JLabel lblCode = new JLabel(this.resources.getString("gui.dept.code"));
		GridBagConstraints gbc_lblCode = new GridBagConstraints();
		gbc_lblCode.insets = new Insets(0, 5, 5, 5);
		gbc_lblCode.anchor = GridBagConstraints.WEST;
		gbc_lblCode.gridx = 0;
		gbc_lblCode.gridy = 1;
		add(lblCode, gbc_lblCode);
		
		textFieldCode = new JTextField();
		((AbstractDocument)textFieldCode.getDocument()).setDocumentFilter(new ExtendedDocumentFilter(5, false));
		GridBagConstraints gbc_textFieldCode = new GridBagConstraints();
		gbc_textFieldCode.insets = new Insets(0, 50, 5, 5);
		gbc_textFieldCode.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCode.gridx = 1;
		gbc_textFieldCode.gridy = 1;
		add(textFieldCode, gbc_textFieldCode);
		textFieldCode.setColumns(10);
		
		JLabel lblName = new JLabel(this.resources.getString("gui.dept.name"));
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 5, 5, 5);
		gbc_lblName.anchor = GridBagConstraints.WEST;
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 2;
		add(lblName, gbc_lblName);
		
		textFieldName = new JTextField();
		((AbstractDocument)textFieldName.getDocument()).setDocumentFilter(new ExtendedDocumentFilter(50, false));
		GridBagConstraints gbc_textFieldName = new GridBagConstraints();
		gbc_textFieldName.insets = new Insets(0, 50, 5, 5);
		gbc_textFieldName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldName.gridx = 1;
		gbc_textFieldName.gridy = 2;
		add(textFieldName, gbc_textFieldName);
		textFieldName.setColumns(10);
		
		JLabel lblDescription = new JLabel(this.resources.getString("gui.dept.description"));
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.insets = new Insets(0, 5, 5, 5);
		gbc_lblDescription.anchor = GridBagConstraints.WEST;
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 3;
		add(lblDescription, gbc_lblDescription);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 50, 5, 5);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 3;
		add(scrollPane, gbc_scrollPane);
		
		textAreaDescription = new JTextArea();
		((AbstractDocument)textAreaDescription.getDocument()).setDocumentFilter(new ExtendedDocumentFilter(250, false));
		textAreaDescription.setLineWrap(true);
		scrollPane.setViewportView(textAreaDescription);
		
		JLabel lblHead = new JLabel(this.resources.getString("gui.dept.head"));
		GridBagConstraints gbc_lblHead = new GridBagConstraints();
		gbc_lblHead.insets = new Insets(0, 5, 5, 5);
		gbc_lblHead.anchor = GridBagConstraints.WEST;
		gbc_lblHead.gridx = 0;
		gbc_lblHead.gridy = 4;
		add(lblHead, gbc_lblHead);
		
		cbHead = new JComboBox<ComboBoxItem>();
		GridBagConstraints gbc_cbHead = new GridBagConstraints();
		gbc_cbHead.insets = new Insets(0, 50, 5, 5);
		gbc_cbHead.anchor = GridBagConstraints.WEST;
		gbc_cbHead.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbHead.gridx = 1;
		gbc_cbHead.gridy = 4;
		add(cbHead, gbc_cbHead);
		
		JButton btnSave = new JButton(this.resources.getString("gui.general.save"));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createDepartmentController.saveHandler(e);
			}
		});
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.anchor = GridBagConstraints.WEST;
		gbc_btnSave.insets = new Insets(0, 5, 0, 5);
		gbc_btnSave.gridx = 0;
		gbc_btnSave.gridy = 5;
		add(btnSave, gbc_btnSave);
		
		JButton btnCancel = new JButton(this.resources.getString("gui.general.cancel"));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createDepartmentController.cancelHandler(e);
			}
		});
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 50, 0, 0);
		gbc_btnCancel.anchor = GridBagConstraints.WEST;
		gbc_btnCancel.gridx = 1;
		gbc_btnCancel.gridy = 5;
		add(btnCancel, gbc_btnCancel);

	}


	/**
	 * @return the textFieldCode
	 */
	public JTextField getTextFieldCode() {
		return textFieldCode;
	}


	/**
	 * @return the textFieldName
	 */
	public JTextField getTextFieldName() {
		return textFieldName;
	}


	/**
	 * @return the textAreaDescription
	 */
	public JTextArea getTextAreaDescription() {
		return textAreaDescription;
	}


	/**
	 * @return the cbHead
	 */
	public JComboBox<ComboBoxItem> getCbHead() {
		return cbHead;
	}
}
