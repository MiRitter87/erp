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
    @SuppressWarnings({ "checkstyle:MagicNumber", "checkstyle:MethodLength", "checkstyle:NoWhitespaceAfter" })
    public CreateEmployeeView(final CreateEmployeeController createEmployeeController) {
        this.resources = ResourceBundle.getBundle("frontend");
        this.createEmployeeController = createEmployeeController;

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        JLabel lblHeader = new JLabel(this.resources.getString("gui.employee.header.create"));
        lblHeader.setFont(new Font("Tahoma", Font.BOLD, 13));
        GridBagConstraints gbcLblHeader = new GridBagConstraints();
        gbcLblHeader.gridwidth = 2;
        gbcLblHeader.anchor = GridBagConstraints.WEST;
        gbcLblHeader.insets = new Insets(5, 5, 5, 5);
        gbcLblHeader.gridx = 0;
        gbcLblHeader.gridy = 0;
        add(lblHeader, gbcLblHeader);

        JLabel lblFirstName = new JLabel(this.resources.getString("gui.employee.firstName"));
        GridBagConstraints gbcLblFirstName = new GridBagConstraints();
        gbcLblFirstName.anchor = GridBagConstraints.WEST;
        gbcLblFirstName.insets = new Insets(0, 5, 5, 5);
        gbcLblFirstName.gridx = 0;
        gbcLblFirstName.gridy = 1;
        add(lblFirstName, gbcLblFirstName);

        textFieldFirstName = new JTextField();
        ((AbstractDocument) textFieldFirstName.getDocument()).setDocumentFilter(new ExtendedDocumentFilter(50, false));
        GridBagConstraints gbcTextFieldFirstName = new GridBagConstraints();
        gbcTextFieldFirstName.insets = new Insets(0, 50, 5, 5);
        gbcTextFieldFirstName.fill = GridBagConstraints.HORIZONTAL;
        gbcTextFieldFirstName.gridx = 1;
        gbcTextFieldFirstName.gridy = 1;
        add(textFieldFirstName, gbcTextFieldFirstName);
        textFieldFirstName.setColumns(10);

        JLabel lblLastName = new JLabel(this.resources.getString("gui.employee.lastName"));
        GridBagConstraints gbcLblLastName = new GridBagConstraints();
        gbcLblLastName.anchor = GridBagConstraints.WEST;
        gbcLblLastName.insets = new Insets(0, 5, 5, 5);
        gbcLblLastName.gridx = 0;
        gbcLblLastName.gridy = 2;
        add(lblLastName, gbcLblLastName);

        textFieldLastName = new JTextField();
        ((AbstractDocument) textFieldLastName.getDocument()).setDocumentFilter(new ExtendedDocumentFilter(50, false));
        GridBagConstraints gbcTextFieldLastName = new GridBagConstraints();
        gbcTextFieldLastName.insets = new Insets(0, 50, 5, 5);
        gbcTextFieldLastName.fill = GridBagConstraints.HORIZONTAL;
        gbcTextFieldLastName.gridx = 1;
        gbcTextFieldLastName.gridy = 2;
        add(textFieldLastName, gbcTextFieldLastName);
        textFieldLastName.setColumns(10);

        JLabel lblGender = new JLabel(this.resources.getString("gui.employee.gender"));
        GridBagConstraints gbcLblGender = new GridBagConstraints();
        gbcLblGender.anchor = GridBagConstraints.WEST;
        gbcLblGender.insets = new Insets(0, 5, 5, 5);
        gbcLblGender.gridx = 0;
        gbcLblGender.gridy = 3;
        add(lblGender, gbcLblGender);

        cbGender = new JComboBox<ComboBoxItem>();
        GridBagConstraints gbcCbGender = new GridBagConstraints();
        gbcCbGender.insets = new Insets(0, 50, 5, 5);
        gbcCbGender.fill = GridBagConstraints.HORIZONTAL;
        gbcCbGender.gridx = 1;
        gbcCbGender.gridy = 3;
        add(cbGender, gbcCbGender);

        JButton btnSave = new JButton(this.resources.getString("gui.general.save"));
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent saveEvent) {
                createEmployeeController.addEmployeeHandler(saveEvent);
            }
        });
        GridBagConstraints gbcBtnSave = new GridBagConstraints();
        gbcBtnSave.anchor = GridBagConstraints.WEST;
        gbcBtnSave.insets = new Insets(0, 5, 0, 5);
        gbcBtnSave.gridx = 0;
        gbcBtnSave.gridy = 4;
        add(btnSave, gbcBtnSave);

        JButton btnCancel = new JButton(this.resources.getString("gui.general.cancel"));
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent cancelEvent) {
                createEmployeeController.cancelHandler(cancelEvent);
            }
        });
        GridBagConstraints gbcBtnCancel = new GridBagConstraints();
        gbcBtnCancel.anchor = GridBagConstraints.WEST;
        gbcBtnCancel.insets = new Insets(0, 50, 0, 5);
        gbcBtnCancel.gridx = 1;
        gbcBtnCancel.gridy = 4;
        add(btnCancel, gbcBtnCancel);

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
    public void setCbGender(final JComboBox<ComboBoxItem> cbGender) {
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
    public void setTextFieldFirstName(final JTextField textFieldFirstName) {
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
    public void setTextFieldLastName(final JTextField textFieldLastName) {
        this.textFieldLastName = textFieldLastName;
    }

}
