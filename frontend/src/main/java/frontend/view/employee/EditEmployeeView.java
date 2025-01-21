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
    @SuppressWarnings({ "checkstyle:MagicNumber", "checkstyle:MethodLength", "checkstyle:NoWhitespaceAfter" })
    public EditEmployeeView(final EditEmployeeController editEmployeeController) {
        this.resources = ResourceBundle.getBundle("frontend");
        this.editEmployeeController = editEmployeeController;
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        JLabel lblHeader = new JLabel(this.resources.getString("gui.employee.header.edit"));
        lblHeader.setFont(new Font("Tahoma", Font.BOLD, 13));
        GridBagConstraints gbcLblHeader = new GridBagConstraints();
        gbcLblHeader.gridwidth = 2;
        gbcLblHeader.anchor = GridBagConstraints.WEST;
        gbcLblHeader.insets = new Insets(5, 5, 5, 5);
        gbcLblHeader.gridx = 0;
        gbcLblHeader.gridy = 0;
        add(lblHeader, gbcLblHeader);

        JLabel lblEmployee = new JLabel(this.resources.getString("gui.employee"));
        GridBagConstraints gbcLblEmployee = new GridBagConstraints();
        gbcLblEmployee.anchor = GridBagConstraints.WEST;
        gbcLblEmployee.insets = new Insets(0, 5, 5, 5);
        gbcLblEmployee.gridx = 0;
        gbcLblEmployee.gridy = 1;
        add(lblEmployee, gbcLblEmployee);

        cbEmployee = new JComboBox<ComboBoxItem>();
        cbEmployee.addItemListener(editEmployeeController::cbEmployeeItemStateChanged);
        GridBagConstraints gbcCbEmployee = new GridBagConstraints();
        gbcCbEmployee.insets = new Insets(0, 50, 5, 5);
        gbcCbEmployee.fill = GridBagConstraints.HORIZONTAL;
        gbcCbEmployee.gridx = 1;
        gbcCbEmployee.gridy = 1;
        gbcCbEmployee.gridwidth = 2;
        add(cbEmployee, gbcCbEmployee);

        JSeparator separator = new JSeparator();
        GridBagConstraints gbcSeparator = new GridBagConstraints();
        gbcSeparator.gridwidth = 3;
        gbcSeparator.insets = new Insets(0, 0, 5, 0);
        gbcSeparator.fill = GridBagConstraints.HORIZONTAL;
        gbcSeparator.gridx = 0;
        gbcSeparator.gridy = 2;
        add(separator, gbcSeparator);

        JLabel lblFirstName = new JLabel(this.resources.getString("gui.employee.firstName"));
        GridBagConstraints gbcLblFirstName = new GridBagConstraints();
        gbcLblFirstName.anchor = GridBagConstraints.WEST;
        gbcLblFirstName.insets = new Insets(0, 5, 5, 5);
        gbcLblFirstName.gridx = 0;
        gbcLblFirstName.gridy = 3;
        add(lblFirstName, gbcLblFirstName);

        textFieldFirstName = new JTextField();
        ((AbstractDocument) textFieldFirstName.getDocument()).setDocumentFilter(new ExtendedDocumentFilter(50, false));
        GridBagConstraints gbcTextFieldFirstName = new GridBagConstraints();
        gbcTextFieldFirstName.insets = new Insets(0, 50, 5, 5);
        gbcTextFieldFirstName.fill = GridBagConstraints.HORIZONTAL;
        gbcTextFieldFirstName.gridx = 1;
        gbcTextFieldFirstName.gridy = 3;
        gbcTextFieldFirstName.gridwidth = 2;
        add(textFieldFirstName, gbcTextFieldFirstName);
        textFieldFirstName.setColumns(10);

        JLabel lblLastName = new JLabel(this.resources.getString("gui.employee.lastName"));
        GridBagConstraints gbcLblLastName = new GridBagConstraints();
        gbcLblLastName.anchor = GridBagConstraints.WEST;
        gbcLblLastName.insets = new Insets(0, 5, 5, 5);
        gbcLblLastName.gridx = 0;
        gbcLblLastName.gridy = 4;
        add(lblLastName, gbcLblLastName);

        textFieldLastName = new JTextField();
        ((AbstractDocument) textFieldLastName.getDocument()).setDocumentFilter(new ExtendedDocumentFilter(50, false));
        GridBagConstraints gbcTextFieldLastName = new GridBagConstraints();
        gbcTextFieldLastName.gridwidth = 2;
        gbcTextFieldLastName.insets = new Insets(0, 50, 5, 5);
        gbcTextFieldLastName.fill = GridBagConstraints.HORIZONTAL;
        gbcTextFieldLastName.gridx = 1;
        gbcTextFieldLastName.gridy = 4;
        add(textFieldLastName, gbcTextFieldLastName);
        textFieldLastName.setColumns(10);

        JLabel lblGender = new JLabel(this.resources.getString("gui.employee.gender"));
        GridBagConstraints gbcLblGender = new GridBagConstraints();
        gbcLblGender.anchor = GridBagConstraints.WEST;
        gbcLblGender.insets = new Insets(0, 5, 5, 5);
        gbcLblGender.gridx = 0;
        gbcLblGender.gridy = 5;
        add(lblGender, gbcLblGender);

        cbGender = new JComboBox<ComboBoxItem>();
        GridBagConstraints gbcCbGender = new GridBagConstraints();
        gbcCbGender.gridwidth = 2;
        gbcCbGender.insets = new Insets(0, 50, 5, 5);
        gbcCbGender.fill = GridBagConstraints.HORIZONTAL;
        gbcCbGender.gridx = 1;
        gbcCbGender.gridy = 5;
        add(cbGender, gbcCbGender);

        JButton btnSave = new JButton(this.resources.getString("gui.general.save"));
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent saveEvent) {
                editEmployeeController.saveEmployeeHandler(saveEvent);
            }
        });
        GridBagConstraints gbcBtnSave = new GridBagConstraints();
        gbcBtnSave.anchor = GridBagConstraints.WEST;
        gbcBtnSave.insets = new Insets(0, 5, 5, 5);
        gbcBtnSave.gridx = 0;
        gbcBtnSave.gridy = 6;
        add(btnSave, gbcBtnSave);

        JButton btnCancel = new JButton(this.resources.getString("gui.general.cancel"));
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent cancelEvent) {
                editEmployeeController.cancelHandler(cancelEvent);
            }
        });
        GridBagConstraints gbcBtnCancel = new GridBagConstraints();
        gbcBtnCancel.anchor = GridBagConstraints.WEST;
        gbcBtnCancel.insets = new Insets(0, 50, 5, 0);
        gbcBtnCancel.gridx = 1;
        gbcBtnCancel.gridy = 6;
        add(btnCancel, gbcBtnCancel);

        JButton btnSalary = new JButton(this.resources.getString("gui.employee.salary.toolTip"));
        btnSalary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent salaryDataEvent) {
                editEmployeeController.btnSalaryDataHandler(salaryDataEvent);
            }
        });
        GridBagConstraints gbcBtnSalary = new GridBagConstraints();
        gbcBtnSalary.anchor = GridBagConstraints.WEST;
        gbcBtnSalary.insets = new Insets(0, 0, 5, 5);
        gbcBtnSalary.gridx = 2;
        gbcBtnSalary.gridy = 6;
        add(btnSalary, gbcBtnSalary);
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
