package frontend.view.employee;

import javax.swing.JPanel;

import frontend.controller.employee.DisplayEmployeeController;
import frontend.model.ComboBoxItem;

import java.awt.GridBagLayout;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * View to display employee data.
 *
 * @author Michael
 */
public class DisplayEmployeeView extends JPanel {

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = 6053886693075244052L;

    /**
     * Access to localized application resources.
     */
    private ResourceBundle resources;

    /**
     * Controller of this view.
     */
    @SuppressWarnings("unused")
    private DisplayEmployeeController displayEmployeeController;

    /**
     * The combo box for employee selection.
     */
    private JComboBox<ComboBoxItem> cbEmployee;

    /**
     * Label for display of employee first name.
     */
    private JLabel lblFirstNameContent;

    /**
     * Label for display of employee last name.
     */
    private JLabel lblLastNameContent;

    /**
     * Label for display of employee gender.
     */
    private JLabel lblGenderContent;

    /**
     * Create the panel.
     *
     * @param displayEmployeeController The controller of this view.
     */
    @SuppressWarnings({ "checkstyle:MagicNumber", "checkstyle:MethodLength", "checkstyle:NoWhitespaceAfter" })
    public DisplayEmployeeView(final DisplayEmployeeController displayEmployeeController) {
        this.resources = ResourceBundle.getBundle("frontend");
        this.displayEmployeeController = displayEmployeeController;

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        JLabel lblHeader = new JLabel(this.resources.getString("gui.employee.header.display"));
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
        cbEmployee.addItemListener(displayEmployeeController::cbEmployeeItemStateChanged);
        GridBagConstraints gbcCbEmployee = new GridBagConstraints();
        gbcCbEmployee.insets = new Insets(0, 50, 5, 5);
        gbcCbEmployee.fill = GridBagConstraints.HORIZONTAL;
        gbcCbEmployee.gridx = 1;
        gbcCbEmployee.gridy = 1;
        add(cbEmployee, gbcCbEmployee);

        JSeparator separator = new JSeparator();
        GridBagConstraints gbcSeparator = new GridBagConstraints();
        gbcSeparator.insets = new Insets(0, 0, 5, 0);
        gbcSeparator.gridwidth = 2;
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

        lblFirstNameContent = new JLabel("");
        GridBagConstraints gbcLblFirstNameContent = new GridBagConstraints();
        gbcLblFirstNameContent.insets = new Insets(0, 50, 5, 0);
        gbcLblFirstNameContent.fill = GridBagConstraints.HORIZONTAL;
        gbcLblFirstNameContent.gridx = 1;
        gbcLblFirstNameContent.gridy = 3;
        add(lblFirstNameContent, gbcLblFirstNameContent);

        JLabel lblLastName = new JLabel(this.resources.getString("gui.employee.lastName"));
        GridBagConstraints gbcLblLastName = new GridBagConstraints();
        gbcLblLastName.anchor = GridBagConstraints.WEST;
        gbcLblLastName.insets = new Insets(0, 5, 5, 5);
        gbcLblLastName.gridx = 0;
        gbcLblLastName.gridy = 4;
        add(lblLastName, gbcLblLastName);

        lblLastNameContent = new JLabel("");
        GridBagConstraints gbcLblLastNameContent = new GridBagConstraints();
        gbcLblLastNameContent.insets = new Insets(0, 50, 5, 0);
        gbcLblLastNameContent.fill = GridBagConstraints.HORIZONTAL;
        gbcLblLastNameContent.gridx = 1;
        gbcLblLastNameContent.gridy = 4;
        add(lblLastNameContent, gbcLblLastNameContent);

        JLabel lblGender = new JLabel(this.resources.getString("gui.employee.gender"));
        GridBagConstraints gbcLblGender = new GridBagConstraints();
        gbcLblGender.anchor = GridBagConstraints.WEST;
        gbcLblGender.insets = new Insets(0, 5, 5, 5);
        gbcLblGender.gridx = 0;
        gbcLblGender.gridy = 5;
        add(lblGender, gbcLblGender);

        lblGenderContent = new JLabel("");
        GridBagConstraints gbcLblGenderContent = new GridBagConstraints();
        gbcLblGenderContent.insets = new Insets(0, 50, 5, 0);
        gbcLblGenderContent.fill = GridBagConstraints.HORIZONTAL;
        gbcLblGenderContent.gridx = 1;
        gbcLblGenderContent.gridy = 5;
        add(lblGenderContent, gbcLblGenderContent);

        JButton btnCancel = new JButton(this.resources.getString("gui.general.cancel"));
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                displayEmployeeController.cancelHandler(arg0);
            }
        });
        GridBagConstraints gbcBtnCancel = new GridBagConstraints();
        gbcBtnCancel.anchor = GridBagConstraints.WEST;
        gbcBtnCancel.insets = new Insets(0, 5, 0, 5);
        gbcBtnCancel.gridx = 0;
        gbcBtnCancel.gridy = 6;
        add(btnCancel, gbcBtnCancel);

        JButton btnSalary = new JButton(this.resources.getString("gui.employee.salary.toolTip"));
        btnSalary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                displayEmployeeController.btnSalaryDataHandler(e);
            }
        });
        GridBagConstraints gbcBtnSalary = new GridBagConstraints();
        gbcBtnSalary.anchor = GridBagConstraints.WEST;
        gbcBtnSalary.insets = new Insets(0, 50, 0, 5);
        gbcBtnSalary.gridx = 1;
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
     * @return the lblFirstNameContent
     */
    public JLabel getLblFirstNameContent() {
        return lblFirstNameContent;
    }

    /**
     * @return the lblLastNameContent
     */
    public JLabel getLblLastNameContent() {
        return lblLastNameContent;
    }

    /**
     * @return the lblGenderContent
     */
    public JLabel getLblGenderContent() {
        return lblGenderContent;
    }
}
