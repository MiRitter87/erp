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
    private JTextArea textAreaDescription;

    /**
     * ComboBox for head of department selection.
     */
    private JComboBox<ComboBoxItem> cbHead;

    /**
     * Create the panel.
     *
     * @param createDepartmentController The controller of this view.
     */
    @SuppressWarnings({"checkstyle:MagicNumber", "checkstyle:MethodLength", "checkstyle:NoWhitespaceAfter"})
    public CreateDepartmentView(final CreateDepartmentController createDepartmentController) {
        this.resources = ResourceBundle.getBundle("frontend");
        this.createDepartmentController = createDepartmentController;
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0, 0, 80, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        JLabel lblHeader = new JLabel(this.resources.getString("gui.dept.header.create"));
        lblHeader.setFont(new Font("Tahoma", Font.BOLD, 13));
        GridBagConstraints gbcLblHeader = new GridBagConstraints();
        gbcLblHeader.gridwidth = 2;
        gbcLblHeader.anchor = GridBagConstraints.WEST;
        gbcLblHeader.insets = new Insets(5, 5, 5, 0);
        gbcLblHeader.gridx = 0;
        gbcLblHeader.gridy = 0;
        add(lblHeader, gbcLblHeader);

        JLabel lblCode = new JLabel(this.resources.getString("gui.dept.code"));
        GridBagConstraints gbcLblCode = new GridBagConstraints();
        gbcLblCode.insets = new Insets(0, 5, 5, 5);
        gbcLblCode.anchor = GridBagConstraints.WEST;
        gbcLblCode.gridx = 0;
        gbcLblCode.gridy = 1;
        add(lblCode, gbcLblCode);

        textFieldCode = new JTextField();
        ((AbstractDocument) textFieldCode.getDocument()).setDocumentFilter(new ExtendedDocumentFilter(5, false));
        GridBagConstraints gbcTextFieldCode = new GridBagConstraints();
        gbcTextFieldCode.insets = new Insets(0, 50, 5, 5);
        gbcTextFieldCode.fill = GridBagConstraints.HORIZONTAL;
        gbcTextFieldCode.gridx = 1;
        gbcTextFieldCode.gridy = 1;
        add(textFieldCode, gbcTextFieldCode);
        textFieldCode.setColumns(10);

        JLabel lblName = new JLabel(this.resources.getString("gui.dept.name"));
        GridBagConstraints gbcLblName = new GridBagConstraints();
        gbcLblName.insets = new Insets(0, 5, 5, 5);
        gbcLblName.anchor = GridBagConstraints.WEST;
        gbcLblName.gridx = 0;
        gbcLblName.gridy = 2;
        add(lblName, gbcLblName);

        textFieldName = new JTextField();
        ((AbstractDocument) textFieldName.getDocument()).setDocumentFilter(new ExtendedDocumentFilter(50, false));
        GridBagConstraints gbcTextFieldName = new GridBagConstraints();
        gbcTextFieldName.insets = new Insets(0, 50, 5, 5);
        gbcTextFieldName.fill = GridBagConstraints.HORIZONTAL;
        gbcTextFieldName.gridx = 1;
        gbcTextFieldName.gridy = 2;
        add(textFieldName, gbcTextFieldName);
        textFieldName.setColumns(10);

        JLabel lblDescription = new JLabel(this.resources.getString("gui.dept.description"));
        GridBagConstraints gbcLblDescription = new GridBagConstraints();
        gbcLblDescription.insets = new Insets(0, 5, 5, 5);
        gbcLblDescription.anchor = GridBagConstraints.WEST;
        gbcLblDescription.gridx = 0;
        gbcLblDescription.gridy = 3;
        add(lblDescription, gbcLblDescription);

        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbcScrollPane = new GridBagConstraints();
        gbcScrollPane.fill = GridBagConstraints.BOTH;
        gbcScrollPane.insets = new Insets(0, 50, 5, 5);
        gbcScrollPane.gridx = 1;
        gbcScrollPane.gridy = 3;
        add(scrollPane, gbcScrollPane);

        textAreaDescription = new JTextArea();
        ((AbstractDocument) textAreaDescription.getDocument())
                .setDocumentFilter(new ExtendedDocumentFilter(250, false));
        textAreaDescription.setLineWrap(true);
        scrollPane.setViewportView(textAreaDescription);

        JLabel lblHead = new JLabel(this.resources.getString("gui.dept.head"));
        GridBagConstraints gbcLblHead = new GridBagConstraints();
        gbcLblHead.insets = new Insets(0, 5, 5, 5);
        gbcLblHead.anchor = GridBagConstraints.WEST;
        gbcLblHead.gridx = 0;
        gbcLblHead.gridy = 4;
        add(lblHead, gbcLblHead);

        cbHead = new JComboBox<ComboBoxItem>();
        GridBagConstraints gbcCbHead = new GridBagConstraints();
        gbcCbHead.insets = new Insets(0, 50, 5, 5);
        gbcCbHead.anchor = GridBagConstraints.WEST;
        gbcCbHead.fill = GridBagConstraints.HORIZONTAL;
        gbcCbHead.gridx = 1;
        gbcCbHead.gridy = 4;
        add(cbHead, gbcCbHead);

        JButton btnSave = new JButton(this.resources.getString("gui.general.save"));
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                createDepartmentController.saveHandler(e);
            }
        });
        GridBagConstraints gbcBtnSave = new GridBagConstraints();
        gbcBtnSave.anchor = GridBagConstraints.WEST;
        gbcBtnSave.insets = new Insets(0, 5, 0, 5);
        gbcBtnSave.gridx = 0;
        gbcBtnSave.gridy = 5;
        add(btnSave, gbcBtnSave);

        JButton btnCancel = new JButton(this.resources.getString("gui.general.cancel"));
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                createDepartmentController.cancelHandler(e);
            }
        });
        GridBagConstraints gbcBtnCancel = new GridBagConstraints();
        gbcBtnCancel.insets = new Insets(0, 50, 0, 0);
        gbcBtnCancel.anchor = GridBagConstraints.WEST;
        gbcBtnCancel.gridx = 1;
        gbcBtnCancel.gridy = 5;
        add(btnCancel, gbcBtnCancel);

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
     * @param textAreaDescription the textAreaDescription to set
     */
    public void setTextAreaDescription(final JTextArea textAreaDescription) {
        this.textAreaDescription = textAreaDescription;
    }

    /**
     * @return the cbHead
     */
    public JComboBox<ComboBoxItem> getCbHead() {
        return cbHead;
    }

    /**
     * @param cbHead the cbHead to set
     */
    public void setCbHead(final JComboBox<ComboBoxItem> cbHead) {
        this.cbHead = cbHead;
    }
}
