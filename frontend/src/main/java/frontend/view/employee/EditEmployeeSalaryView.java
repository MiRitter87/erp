package frontend.view.employee;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import frontend.controller.employee.EditEmployeeSalaryController;
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
public class EditEmployeeSalaryView extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * Controller of this view.
     */
    private EditEmployeeSalaryController editEmployeeSalaryController;

    /**
     * Access to localized application resources.
     */
    private ResourceBundle resources;

    /**
     * Text field for salary data.
     */
    private JTextField textFieldSalary;

    /**
     * The label displaying the date of the last salary change.
     */
    private JLabel lblLastChangeValue;

    /**
     * Create the panel.
     *
     * @param editEmployeeSalaryController The controller of the view.
     */
    @SuppressWarnings({ "checkstyle:MagicNumber", "checkstyle:MethodLength", "checkstyle:NoWhitespaceAfter" })
    public EditEmployeeSalaryView(final EditEmployeeSalaryController editEmployeeSalaryController) {
        this.resources = ResourceBundle.getBundle("frontend");
        this.editEmployeeSalaryController = editEmployeeSalaryController;

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        JLabel textLabel = new JLabel(MessageFormat.format(this.resources.getString("gui.employee.salary.header"),
                this.editEmployeeSalaryController.getSelectedEmployee().getFirstName(),
                this.editEmployeeSalaryController.getSelectedEmployee().getLastName()));
        textLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        GridBagConstraints gbcTextLabel = new GridBagConstraints();
        gbcTextLabel.gridwidth = 2;
        gbcTextLabel.anchor = GridBagConstraints.WEST;
        gbcTextLabel.insets = new Insets(5, 5, 5, 5);
        gbcTextLabel.gridx = 0;
        gbcTextLabel.gridy = 0;
        add(textLabel, gbcTextLabel);

        JButton btnBack = new JButton(this.resources.getString("gui.general.back"));
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                editEmployeeSalaryController.btnBackHandler(e);
            }
        });

        JLabel lblMonthlySalary = new JLabel(this.resources.getString("gui.employee.salary.monthlySalary"));
        GridBagConstraints gbcLblMonthlySalary = new GridBagConstraints();
        gbcLblMonthlySalary.anchor = GridBagConstraints.WEST;
        gbcLblMonthlySalary.insets = new Insets(0, 5, 5, 5);
        gbcLblMonthlySalary.gridx = 0;
        gbcLblMonthlySalary.gridy = 1;
        add(lblMonthlySalary, gbcLblMonthlySalary);

        textFieldSalary = new JTextField();
        ((AbstractDocument) textFieldSalary.getDocument()).setDocumentFilter(new ExtendedDocumentFilter(6, true));
        GridBagConstraints gbcTextFieldSalary = new GridBagConstraints();
        gbcTextFieldSalary.insets = new Insets(0, 50, 5, 0);
        gbcTextFieldSalary.fill = GridBagConstraints.HORIZONTAL;
        gbcTextFieldSalary.gridx = 1;
        gbcTextFieldSalary.gridy = 1;
        add(textFieldSalary, gbcTextFieldSalary);
        textFieldSalary.setColumns(10);

        JLabel lblLastChange = new JLabel(this.resources.getString("gui.employee.salary.lastChange"));
        GridBagConstraints gbcLblLastChange = new GridBagConstraints();
        gbcLblLastChange.anchor = GridBagConstraints.WEST;
        gbcLblLastChange.insets = new Insets(0, 5, 5, 5);
        gbcLblLastChange.gridx = 0;
        gbcLblLastChange.gridy = 2;
        add(lblLastChange, gbcLblLastChange);

        lblLastChangeValue = new JLabel("");
        GridBagConstraints gbcLblLastChangeValue = new GridBagConstraints();
        gbcLblLastChangeValue.anchor = GridBagConstraints.WEST;
        gbcLblLastChangeValue.insets = new Insets(0, 50, 5, 0);
        gbcLblLastChangeValue.gridx = 1;
        gbcLblLastChangeValue.gridy = 2;
        add(lblLastChangeValue, gbcLblLastChangeValue);

        JLabel lblCurrency = new JLabel("€");
        GridBagConstraints gbcLblCurrency = new GridBagConstraints();
        gbcLblCurrency.insets = new Insets(0, 5, 5, 5);
        gbcLblCurrency.gridx = 2;
        gbcLblCurrency.gridy = 1;
        add(lblCurrency, gbcLblCurrency);
        GridBagConstraints gbcBtnzurck = new GridBagConstraints();
        gbcBtnzurck.anchor = GridBagConstraints.WEST;
        gbcBtnzurck.insets = new Insets(0, 5, 0, 5);
        gbcBtnzurck.gridx = 0;
        gbcBtnzurck.gridy = 9;
        add(btnBack, gbcBtnzurck);

        JButton btnSave = new JButton(this.resources.getString("gui.general.save"));
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                editEmployeeSalaryController.saveSalaryHandler(e);
            }
        });
        GridBagConstraints gbcBtnSave = new GridBagConstraints();
        gbcBtnSave.anchor = GridBagConstraints.WEST;
        gbcBtnSave.insets = new Insets(0, 50, 0, 5);
        gbcBtnSave.gridx = 1;
        gbcBtnSave.gridy = 9;
        add(btnSave, gbcBtnSave);
    }

    /**
     * @return the textFieldSalary
     */
    public JTextField getTextFieldSalary() {
        return textFieldSalary;
    }

    /**
     * @param textFieldSalary the textFieldSalary to set
     */
    public void setTextFieldSalary(final JTextField textFieldSalary) {
        this.textFieldSalary = textFieldSalary;
    }

    /**
     * @return the lblLastChangeValue
     */
    public JLabel getLblLastChangeValue() {
        return lblLastChangeValue;
    }

    /**
     * @param lblLastChangeValue the lblLastChangeValue to set
     */
    public void setLblLastChangeValue(final JLabel lblLastChangeValue) {
        this.lblLastChangeValue = lblLastChangeValue;
    }
}
