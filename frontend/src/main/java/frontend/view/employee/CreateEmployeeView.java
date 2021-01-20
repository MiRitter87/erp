package frontend.view.employee;

import javax.swing.JPanel;

import frontend.controller.employee.CreateEmployeeController;
import frontend.model.ComboBoxItem;
import frontend.view.components.ExtendedDocumentFilter;

import java.awt.GridBagLayout;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * View to create new employees.
 * 
 * @author Michael
 */
public class CreateEmployeeView extends JPanel {
	/**
	 * Default serialization ID.
	 */
	private static final long serialVersionUID = 1527054951723750228L;
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources;
	
	/**
	 * Controller of this view.
	 */
	@SuppressWarnings("unused")
	private CreateEmployeeController createEmployeeController;
	
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
	 * @param createEmployeeController The controller of this view.
	 */
	public CreateEmployeeView(final CreateEmployeeController createEmployeeController) {
		this.resources = ResourceBundle.getBundle("frontend");
		this.createEmployeeController = createEmployeeController;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblHeader = new JLabel(this.resources.getString("gui.employee.header.create"));
		lblHeader.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblHeader = new GridBagConstraints();
		gbc_lblHeader.gridwidth = 2;
		gbc_lblHeader.anchor = GridBagConstraints.WEST;
		gbc_lblHeader.insets = new Insets(5, 5, 5, 5);
		gbc_lblHeader.gridx = 0;
		gbc_lblHeader.gridy = 0;
		add(lblHeader, gbc_lblHeader);
		
		JLabel lblFirstName = new JLabel(this.resources.getString("gui.employee.firstName"));
		GridBagConstraints gbc_lblFirstName = new GridBagConstraints();
		gbc_lblFirstName.anchor = GridBagConstraints.WEST;
		gbc_lblFirstName.insets = new Insets(0, 5, 5, 5);
		gbc_lblFirstName.gridx = 0;
		gbc_lblFirstName.gridy = 1;
		add(lblFirstName, gbc_lblFirstName);
		
		textFieldFirstName = new JTextField();
		((AbstractDocument)textFieldFirstName.getDocument()).setDocumentFilter(new ExtendedDocumentFilter(50, false));
		GridBagConstraints gbc_textFieldFirstName = new GridBagConstraints();
		gbc_textFieldFirstName.insets = new Insets(0, 50, 5, 5);
		gbc_textFieldFirstName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldFirstName.gridx = 1;
		gbc_textFieldFirstName.gridy = 1;
		add(textFieldFirstName, gbc_textFieldFirstName);
		textFieldFirstName.setColumns(10);
		
		JLabel lblLastName = new JLabel(this.resources.getString("gui.employee.lastName"));
		GridBagConstraints gbc_lblLastName = new GridBagConstraints();
		gbc_lblLastName.anchor = GridBagConstraints.WEST;
		gbc_lblLastName.insets = new Insets(0, 5, 5, 5);
		gbc_lblLastName.gridx = 0;
		gbc_lblLastName.gridy = 2;
		add(lblLastName, gbc_lblLastName);
		
		textFieldLastName = new JTextField();
		((AbstractDocument)textFieldLastName.getDocument()).setDocumentFilter(new ExtendedDocumentFilter(50, false));
		GridBagConstraints gbc_textFieldLastName = new GridBagConstraints();
		gbc_textFieldLastName.insets = new Insets(0, 50, 5, 5);
		gbc_textFieldLastName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldLastName.gridx = 1;
		gbc_textFieldLastName.gridy = 2;
		add(textFieldLastName, gbc_textFieldLastName);
		textFieldLastName.setColumns(10);
		
		JLabel lblGender = new JLabel(this.resources.getString("gui.employee.gender"));
		GridBagConstraints gbc_lblGender = new GridBagConstraints();
		gbc_lblGender.anchor = GridBagConstraints.WEST;
		gbc_lblGender.insets = new Insets(0, 5, 5, 5);
		gbc_lblGender.gridx = 0;
		gbc_lblGender.gridy = 3;
		add(lblGender, gbc_lblGender);
		
		cbGender = new JComboBox<ComboBoxItem>();
		GridBagConstraints gbc_cbGender = new GridBagConstraints();
		gbc_cbGender.insets = new Insets(0, 50, 5, 5);
		gbc_cbGender.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbGender.gridx = 1;
		gbc_cbGender.gridy = 3;
		add(cbGender, gbc_cbGender);
		
		JButton btnSave = new JButton(this.resources.getString("gui.general.save"));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent saveEvent) {
				createEmployeeController.addEmployeeHandler(saveEvent);
			}
		});
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.anchor = GridBagConstraints.WEST;
		gbc_btnSave.insets = new Insets(0, 5, 0, 5);
		gbc_btnSave.gridx = 0;
		gbc_btnSave.gridy = 4;
		add(btnSave, gbc_btnSave);
		
		JButton btnCancel = new JButton(this.resources.getString("gui.general.cancel"));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent cancelEvent) {
				createEmployeeController.cancelHandler(cancelEvent);
			}
		});
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.anchor = GridBagConstraints.WEST;
		gbc_btnCancel.insets = new Insets(0, 50, 0, 5);
		gbc_btnCancel.gridx = 1;
		gbc_btnCancel.gridy = 4;
		add(btnCancel, gbc_btnCancel);

	}


	/**
	 * @return the cbGender
	 */
	public JComboBox<ComboBoxItem> getCbGender() {
		return cbGender;
	}


	/**
	 * @param cbGender the cbGender to set
	 */
	public void setCbGender(JComboBox<ComboBoxItem> cbGender) {
		this.cbGender = cbGender;
	}


	/**
	 * @return the textFieldFirstName
	 */
	public JTextField getTextFieldFirstName() {
		return textFieldFirstName;
	}


	/**
	 * @param textFieldFirstName the textFieldFirstName to set
	 */
	public void setTextFieldFirstName(JTextField textFieldFirstName) {
		this.textFieldFirstName = textFieldFirstName;
	}


	/**
	 * @return the textFieldLastName
	 */
	public JTextField getTextFieldLastName() {
		return textFieldLastName;
	}


	/**
	 * @param textFieldLastName the textFieldLastName to set
	 */
	public void setTextFieldLastName(JTextField textFieldLastName) {
		this.textFieldLastName = textFieldLastName;
	}

}
