package frontend.view.employee;

import javax.swing.JPanel;

import frontend.controller.employee.DisplayEmployeeSalaryController;

import java.awt.GridBagLayout;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

/**
 * This view allows the user to view salary data of an employee.
 *
 * @author Michael
 */
public class DisplayEmployeeSalaryView extends JPanel {

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = 4469080807937463395L;

    /**
     * Controller of this view.
     */
    private DisplayEmployeeSalaryController displayEmployeeSalaryController;

    /**
     * Access to localized application resources.
     */
    private ResourceBundle resources;

    /**
     * A label displaying the salary of the employee.
     */
    private JLabel lblMonthlySalaryContent;

    /**
     * A label displaying the date of the last salary change.
     */
    private JLabel lblLastChangeValue;

    /**
     * Create the panel.
     *
     * @param displayEmployeeSalaryController The controller of this view.
     */
    public DisplayEmployeeSalaryView(final DisplayEmployeeSalaryController displayEmployeeSalaryController) {
        this.resources = ResourceBundle.getBundle("frontend");
        this.displayEmployeeSalaryController = displayEmployeeSalaryController;

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        JLabel textLabel = new JLabel(MessageFormat.format(this.resources.getString("gui.employee.salary.header"),
                this.displayEmployeeSalaryController.getSelectedEmployee().getFirstName(),
                this.displayEmployeeSalaryController.getSelectedEmployee().getLastName()));
        textLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        GridBagConstraints gbc_textLabel = new GridBagConstraints();
        gbc_textLabel.gridwidth = 3;
        gbc_textLabel.anchor = GridBagConstraints.WEST;
        gbc_textLabel.insets = new Insets(5, 5, 5, 5);
        gbc_textLabel.gridx = 0;
        gbc_textLabel.gridy = 0;
        add(textLabel, gbc_textLabel);

        JLabel lblMonthlySalary = new JLabel(this.resources.getString("gui.employee.salary.monthlySalary"));
        GridBagConstraints gbc_lblMonthlySalary = new GridBagConstraints();
        gbc_lblMonthlySalary.anchor = GridBagConstraints.WEST;
        gbc_lblMonthlySalary.insets = new Insets(0, 5, 5, 5);
        gbc_lblMonthlySalary.gridx = 0;
        gbc_lblMonthlySalary.gridy = 1;
        add(lblMonthlySalary, gbc_lblMonthlySalary);

        JLabel lblCurrency = new JLabel("€");
        GridBagConstraints gbc_lblCurrency = new GridBagConstraints();
        gbc_lblCurrency.anchor = GridBagConstraints.WEST;
        gbc_lblCurrency.insets = new Insets(0, 0, 5, 5);
        gbc_lblCurrency.gridx = 2;
        gbc_lblCurrency.gridy = 1;
        add(lblCurrency, gbc_lblCurrency);

        lblMonthlySalaryContent = new JLabel("");
        lblMonthlySalaryContent.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblMonthlySalaryContent = new GridBagConstraints();
        gbc_lblMonthlySalaryContent.insets = new Insets(0, 50, 5, 0);
        gbc_lblMonthlySalaryContent.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblMonthlySalaryContent.gridx = 1;
        gbc_lblMonthlySalaryContent.gridy = 1;
        add(lblMonthlySalaryContent, gbc_lblMonthlySalaryContent);

        JLabel lblLastChange = new JLabel(this.resources.getString("gui.employee.salary.lastChange"));
        GridBagConstraints gbc_lblLastChange = new GridBagConstraints();
        gbc_lblLastChange.anchor = GridBagConstraints.WEST;
        gbc_lblLastChange.insets = new Insets(0, 5, 5, 5);
        gbc_lblLastChange.gridx = 0;
        gbc_lblLastChange.gridy = 2;
        add(lblLastChange, gbc_lblLastChange);

        lblLastChangeValue = new JLabel("");
        lblLastChangeValue.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblLastChangeValue = new GridBagConstraints();
        gbc_lblLastChangeValue.insets = new Insets(0, 50, 5, 5);
        gbc_lblLastChangeValue.gridx = 1;
        gbc_lblLastChangeValue.gridy = 2;
        add(lblLastChangeValue, gbc_lblLastChangeValue);

        JButton btnBack = new JButton(this.resources.getString("gui.general.back"));
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                displayEmployeeSalaryController.btnBackHandler(e);
            }
        });
        GridBagConstraints gbc_btnBack = new GridBagConstraints();
        gbc_btnBack.anchor = GridBagConstraints.WEST;
        gbc_btnBack.insets = new Insets(0, 5, 0, 5);
        gbc_btnBack.gridx = 0;
        gbc_btnBack.gridy = 9;
        add(btnBack, gbc_btnBack);

    }

    /**
     * @return the lblMonthlySalaryContent
     */
    public JLabel getLblMonthlySalaryContent() {
        return lblMonthlySalaryContent;
    }

    /**
     * @return the lblLastChangeValue
     */
    public JLabel getLblLastChangeValue() {
        return lblLastChangeValue;
    }
}
