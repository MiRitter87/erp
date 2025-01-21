package frontend.view.department;

import java.util.ResourceBundle;

import javax.swing.JPanel;

import frontend.controller.department.EditDepartmentController;
import frontend.model.ComboBoxItem;
import frontend.view.components.ExtendedDocumentFilter;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

/**
 * View to edit department data.
 *
 * @author Michael
 */
public class EditDepartmentView extends JPanel {
    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = -1197741571105021632L;

    /**
     * Access to localized application resources.
     */
    private ResourceBundle resources;

    /**
     * The controller of this view.
     */
    @SuppressWarnings("unused")
    private EditDepartmentController editDepartmentController;

    /**
     * ComboBox for department selection.
     */
    private JComboBox<ComboBoxItem> cbDepartment;

    /**
     * ComboBox for head of department selection.
     */
    private JComboBox<ComboBoxItem> cbHead;

    /**
     * Label for display of department code.
     */
    private JLabel lblCodeContent;

    /**
     * Input field for department name.
     */
    private JTextField textFieldName;

    /**
     * Input for description.
     */
    private JTextArea textAreaDescription;

    /**
     * Create the panel.
     *
     * @param editDepartmentController The controller of this view.
     */
    @SuppressWarnings({ "checkstyle:MagicNumber", "checkstyle:MethodLength", "checkstyle:NoWhitespaceAfter" })
    public EditDepartmentView(final EditDepartmentController editDepartmentController) {
        this.resources = ResourceBundle.getBundle("frontend");
        this.editDepartmentController = editDepartmentController;
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 80, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        JLabel lblHeader = new JLabel(this.resources.getString("gui.dept.header.edit"));
        lblHeader.setFont(new Font("Tahoma", Font.BOLD, 13));
        GridBagConstraints gbcLblHeader = new GridBagConstraints();
        gbcLblHeader.gridwidth = 2;
        gbcLblHeader.anchor = GridBagConstraints.WEST;
        gbcLblHeader.insets = new Insets(5, 5, 5, 0);
        gbcLblHeader.gridx = 0;
        gbcLblHeader.gridy = 0;
        add(lblHeader, gbcLblHeader);

        JLabel lblDepartment = new JLabel(this.resources.getString("gui.dept.department"));
        GridBagConstraints gbcLblDepartment = new GridBagConstraints();
        gbcLblDepartment.anchor = GridBagConstraints.WEST;
        gbcLblDepartment.insets = new Insets(0, 5, 5, 5);
        gbcLblDepartment.gridx = 0;
        gbcLblDepartment.gridy = 1;
        add(lblDepartment, gbcLblDepartment);

        cbDepartment = new JComboBox<ComboBoxItem>();
        cbDepartment.addItemListener(editDepartmentController::cbDepartmentItemStateChanged);
        GridBagConstraints gbcCbDepartment = new GridBagConstraints();
        gbcCbDepartment.insets = new Insets(0, 50, 5, 5);
        gbcCbDepartment.fill = GridBagConstraints.HORIZONTAL;
        gbcCbDepartment.gridx = 1;
        gbcCbDepartment.gridy = 1;
        add(cbDepartment, gbcCbDepartment);

        JSeparator separator = new JSeparator();
        GridBagConstraints gbcSeparator = new GridBagConstraints();
        gbcSeparator.insets = new Insets(0, 0, 5, 0);
        gbcSeparator.fill = GridBagConstraints.HORIZONTAL;
        gbcSeparator.gridwidth = 2;
        gbcSeparator.gridx = 0;
        gbcSeparator.gridy = 2;
        add(separator, gbcSeparator);

        JLabel lblCode = new JLabel(this.resources.getString("gui.dept.code"));
        GridBagConstraints gbcLblCode = new GridBagConstraints();
        gbcLblCode.anchor = GridBagConstraints.WEST;
        gbcLblCode.insets = new Insets(0, 5, 5, 5);
        gbcLblCode.gridx = 0;
        gbcLblCode.gridy = 3;
        add(lblCode, gbcLblCode);

        lblCodeContent = new JLabel("");
        GridBagConstraints gbcLblCodeContent = new GridBagConstraints();
        gbcLblCodeContent.fill = GridBagConstraints.HORIZONTAL;
        gbcLblCodeContent.insets = new Insets(0, 50, 5, 5);
        gbcLblCodeContent.gridx = 1;
        gbcLblCodeContent.gridy = 3;
        add(lblCodeContent, gbcLblCodeContent);

        JLabel lblName = new JLabel(this.resources.getString("gui.dept.name"));
        GridBagConstraints gbcLblName = new GridBagConstraints();
        gbcLblName.anchor = GridBagConstraints.WEST;
        gbcLblName.insets = new Insets(0, 5, 5, 5);
        gbcLblName.gridx = 0;
        gbcLblName.gridy = 4;
        add(lblName, gbcLblName);

        textFieldName = new JTextField();
        ((AbstractDocument) textFieldName.getDocument()).setDocumentFilter(new ExtendedDocumentFilter(50, false));
        GridBagConstraints gbcTextFieldName = new GridBagConstraints();
        gbcTextFieldName.insets = new Insets(0, 50, 5, 5);
        gbcTextFieldName.fill = GridBagConstraints.HORIZONTAL;
        gbcTextFieldName.gridx = 1;
        gbcTextFieldName.gridy = 4;
        add(textFieldName, gbcTextFieldName);
        textFieldName.setColumns(10);

        JLabel lblDescription = new JLabel(this.resources.getString("gui.dept.description"));
        GridBagConstraints gbcLblDescription = new GridBagConstraints();
        gbcLblDescription.anchor = GridBagConstraints.WEST;
        gbcLblDescription.insets = new Insets(0, 5, 5, 5);
        gbcLblDescription.gridx = 0;
        gbcLblDescription.gridy = 5;
        add(lblDescription, gbcLblDescription);

        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbcScrollPane = new GridBagConstraints();
        gbcScrollPane.fill = GridBagConstraints.BOTH;
        gbcScrollPane.insets = new Insets(0, 50, 5, 5);
        gbcScrollPane.gridx = 1;
        gbcScrollPane.gridy = 5;
        add(scrollPane, gbcScrollPane);

        textAreaDescription = new JTextArea();
        ((AbstractDocument) textAreaDescription.getDocument())
                .setDocumentFilter(new ExtendedDocumentFilter(250, false));
        textAreaDescription.setLineWrap(true);
        scrollPane.setViewportView(textAreaDescription);

        JLabel lblHead = new JLabel(this.resources.getString("gui.dept.head"));
        GridBagConstraints gbcLblHead = new GridBagConstraints();
        gbcLblHead.anchor = GridBagConstraints.WEST;
        gbcLblHead.insets = new Insets(0, 5, 5, 5);
        gbcLblHead.gridx = 0;
        gbcLblHead.gridy = 6;
        add(lblHead, gbcLblHead);

        cbHead = new JComboBox<ComboBoxItem>();
        GridBagConstraints gbcCbHead = new GridBagConstraints();
        gbcCbHead.insets = new Insets(0, 50, 5, 5);
        gbcCbHead.fill = GridBagConstraints.HORIZONTAL;
        gbcCbHead.gridx = 1;
        gbcCbHead.gridy = 6;
        add(cbHead, gbcCbHead);

        JButton btnSave = new JButton(this.resources.getString("gui.general.save"));
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent saveEvent) {
                editDepartmentController.saveDepartmentHandler(saveEvent);
            }
        });
        GridBagConstraints gbcBtnSave = new GridBagConstraints();
        gbcBtnSave.anchor = GridBagConstraints.WEST;
        gbcBtnSave.insets = new Insets(0, 5, 0, 5);
        gbcBtnSave.gridx = 0;
        gbcBtnSave.gridy = 7;
        add(btnSave, gbcBtnSave);

        JButton btnCancel = new JButton(this.resources.getString("gui.general.cancel"));
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent cancelEvent) {
                editDepartmentController.cancelHandler(cancelEvent);
            }
        });
        GridBagConstraints gbcBtnCancel = new GridBagConstraints();
        gbcBtnCancel.anchor = GridBagConstraints.WEST;
        gbcBtnCancel.insets = new Insets(0, 50, 0, 0);
        gbcBtnCancel.gridx = 1;
        gbcBtnCancel.gridy = 7;
        add(btnCancel, gbcBtnCancel);
    }

    /**
     * @return the cbDepartment
     */
    public JComboBox<ComboBoxItem> getCbDepartment() {
        return cbDepartment;
    }

    /**
     * @return the cbHead
     */
    public JComboBox<ComboBoxItem> getCbHead() {
        return cbHead;
    }

    /**
     * @return the lblCodeContent
     */
    public JLabel getLblCodeContent() {
        return lblCodeContent;
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
}
