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
    @SuppressWarnings({ "checkstyle:MagicNumber", "checkstyle:MethodLength", "checkstyle:NoWhitespaceAfter" })
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
        GridBagConstraints gbcTextLabel = new GridBagConstraints();
        gbcTextLabel.gridwidth = 3;
        gbcTextLabel.anchor = GridBagConstraints.WEST;
        gbcTextLabel.insets = new Insets(5, 5, 5, 5);
        gbcTextLabel.gridx = 0;
        gbcTextLabel.gridy = 0;
        add(textLabel, gbcTextLabel);

        JLabel lblMonthlySalary = new JLabel(this.resources.getString("gui.employee.salary.monthlySalary"));
        GridBagConstraints gbcLblMonthlySalary = new GridBagConstraints();
        gbcLblMonthlySalary.anchor = GridBagConstraints.WEST;
        gbcLblMonthlySalary.insets = new Insets(0, 5, 5, 5);
        gbcLblMonthlySalary.gridx = 0;
        gbcLblMonthlySalary.gridy = 1;
        add(lblMonthlySalary, gbcLblMonthlySalary);

        JLabel lblCurrency = new JLabel("€");
        GridBagConstraints gbcLblCurrency = new GridBagConstraints();
        gbcLblCurrency.anchor = GridBagConstraints.WEST;
        gbcLblCurrency.insets = new Insets(0, 0, 5, 5);
        gbcLblCurrency.gridx = 2;
        gbcLblCurrency.gridy = 1;
        add(lblCurrency, gbcLblCurrency);

        lblMonthlySalaryContent = new JLabel("");
        lblMonthlySalaryContent.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbcLblMonthlySalaryContent = new GridBagConstraints();
        gbcLblMonthlySalaryContent.insets = new Insets(0, 50, 5, 0);
        gbcLblMonthlySalaryContent.fill = GridBagConstraints.HORIZONTAL;
        gbcLblMonthlySalaryContent.gridx = 1;
        gbcLblMonthlySalaryContent.gridy = 1;
        add(lblMonthlySalaryContent, gbcLblMonthlySalaryContent);

        JLabel lblLastChange = new JLabel(this.resources.getString("gui.employee.salary.lastChange"));
        GridBagConstraints gbcLblLastChange = new GridBagConstraints();
        gbcLblLastChange.anchor = GridBagConstraints.WEST;
        gbcLblLastChange.insets = new Insets(0, 5, 5, 5);
        gbcLblLastChange.gridx = 0;
        gbcLblLastChange.gridy = 2;
        add(lblLastChange, gbcLblLastChange);

        lblLastChangeValue = new JLabel("");
        lblLastChangeValue.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbcLblLastChangeValue = new GridBagConstraints();
        gbcLblLastChangeValue.insets = new Insets(0, 50, 5, 5);
        gbcLblLastChangeValue.gridx = 1;
        gbcLblLastChangeValue.gridy = 2;
        add(lblLastChangeValue, gbcLblLastChangeValue);

        JButton btnBack = new JButton(this.resources.getString("gui.general.back"));
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                displayEmployeeSalaryController.btnBackHandler(e);
            }
        });
        GridBagConstraints gbcBtnBack = new GridBagConstraints();
        gbcBtnBack.anchor = GridBagConstraints.WEST;
        gbcBtnBack.insets = new Insets(0, 5, 0, 5);
        gbcBtnBack.gridx = 0;
        gbcBtnBack.gridy = 9;
        add(btnBack, gbcBtnBack);

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
