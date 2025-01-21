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
    @SuppressWarnings({ "checkstyle:MagicNumber", "checkstyle:MethodLength", "checkstyle:NoWhitespaceAfter" })
    public EmployeeOverview(final EmployeeOverviewController employeeOverviewController) {
        this.resources = ResourceBundle.getBundle("frontend");
        this.employeeOverviewController = employeeOverviewController;

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 161, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 1.0 };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0 };
        this.setLayout(gridBagLayout);

        JLabel textLabel = new JLabel(this.resources.getString("gui.employee.header.overview"));
        textLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        GridBagConstraints gbcTextLabel = new GridBagConstraints();
        gbcTextLabel.anchor = GridBagConstraints.WEST;
        gbcTextLabel.insets = new Insets(5, 5, 5, 5);
        gbcTextLabel.gridx = 0;
        gbcTextLabel.gridy = 0;
        this.add(textLabel, gbcTextLabel);

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        GridBagConstraints gbcToolBar = new GridBagConstraints();
        gbcToolBar.anchor = GridBagConstraints.EAST;
        gbcToolBar.insets = new Insets(0, 0, 5, 5);
        gbcToolBar.gridx = 1;
        gbcToolBar.gridy = 1;
        add(toolBar, gbcToolBar);

        URL imgMoneyURL = getClass().getResource("/icons/money.png");
        JButton btnSalary = new JButton("", new ImageIcon(imgMoneyURL));
        toolBar.add(btnSalary);
        btnSalary.setToolTipText(this.resources.getString("gui.employee.salary.toolTip"));
        btnSalary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                employeeOverviewController.switchToSalaryView();
            }
        });

        URL imgDeleteURL = getClass().getResource("/icons/delete.png");
        JButton btnDeleteEmployee = new JButton("", new ImageIcon(imgDeleteURL));
        toolBar.add(btnDeleteEmployee);
        btnDeleteEmployee.setToolTipText(this.resources.getString("gui.employee.deleteButton"));
        btnDeleteEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                employeeOverviewController.deleteSelectedEmployee(arg0);
            }
        });

        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbcScrollPane = new GridBagConstraints();
        gbcScrollPane.insets = new Insets(0, 5, 5, 5);
        gbcScrollPane.gridwidth = 2;
        gbcScrollPane.fill = GridBagConstraints.BOTH;
        gbcScrollPane.gridx = 0;
        gbcScrollPane.gridy = 2;
        this.add(scrollPane, gbcScrollPane);

        tableEmployee = new JTable(3, 3);
        scrollPane.setViewportView(tableEmployee);
        tableEmployee.setModel(new EmployeeTableModel());

        JButton btnCancel = new JButton(this.resources.getString("gui.general.cancel"));
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                employeeOverviewController.cancelHandler(arg0);
            }
        });
        GridBagConstraints gbcBtnCancel = new GridBagConstraints();
        gbcBtnCancel.anchor = GridBagConstraints.WEST;
        gbcBtnCancel.insets = new Insets(0, 5, 5, 5);
        gbcBtnCancel.gridx = 0;
        gbcBtnCancel.gridy = 3;
        add(btnCancel, gbcBtnCancel);
    }

    /**
     * @return the tableEmployee
     */
    public JTable getTableEmployee() {
        return tableEmployee;
    }

    /**
     * @param tableEmployee the tableEmployee to set
     */
    public void setTableEmployee(final JTable tableEmployee) {
        this.tableEmployee = tableEmployee;
    }
}
