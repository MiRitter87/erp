package frontend.view.department;

import java.util.ResourceBundle;

import javax.swing.JPanel;

import frontend.controller.department.DisplayDepartmentController;
import frontend.model.ComboBoxItem;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JButton;

/**
 * The view for display of a department.
 *
 * @author Michael
 */
public class DisplayDepartmentView extends JPanel {
    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = -8946977428192423838L;

    /**
     * Access to localized application resources.
     */
    private ResourceBundle resources;

    /**
     * The controller of this view.
     */
    @SuppressWarnings("unused")
    private DisplayDepartmentController displayDepartmentController;

    /**
     * ComboBox for department selection.
     */
    private JComboBox<ComboBoxItem> cbDepartment;

    /**
     * Label for display of department code.
     */
    private JLabel lblCodeContent;

    /**
     * Label for display of department name.
     */
    private JLabel lblNameContent;

    /**
     * Label for display of department description.
     */
    private JLabel lblDescriptionConent;

    /**
     * Label for display of department head.
     */
    private JLabel lblHeadContent;

    /**
     * Create the panel.
     *
     * @param displayDepartmentController The controller of the view.
     */
    @SuppressWarnings({ "checkstyle:MagicNumber", "checkstyle:MethodLength", "checkstyle:NoWhitespaceAfter" })
    public DisplayDepartmentView(final DisplayDepartmentController displayDepartmentController) {
        this.resources = ResourceBundle.getBundle("frontend");
        this.displayDepartmentController = displayDepartmentController;
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        JLabel lblHeader = new JLabel(this.resources.getString("gui.dept.header.display"));
        lblHeader.setFont(new Font("Tahoma", Font.BOLD, 13));
        GridBagConstraints gbcLblHeader = new GridBagConstraints();
        gbcLblHeader.gridwidth = 2;
        gbcLblHeader.anchor = GridBagConstraints.WEST;
        gbcLblHeader.insets = new Insets(5, 5, 5, 5);
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
        cbDepartment.addItemListener(displayDepartmentController::cbDepartmentItemStateChanged);
        GridBagConstraints gbcCbDepartment = new GridBagConstraints();
        gbcCbDepartment.insets = new Insets(0, 50, 5, 5);
        gbcCbDepartment.fill = GridBagConstraints.HORIZONTAL;
        gbcCbDepartment.gridx = 1;
        gbcCbDepartment.gridy = 1;
        add(cbDepartment, gbcCbDepartment);

        JSeparator separator = new JSeparator();
        GridBagConstraints gbcSeparator = new GridBagConstraints();
        gbcSeparator.insets = new Insets(0, 0, 5, 0);
        gbcSeparator.gridwidth = 2;
        gbcSeparator.fill = GridBagConstraints.HORIZONTAL;
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
        gbcLblCodeContent.insets = new Insets(0, 50, 5, 0);
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

        lblNameContent = new JLabel("");
        GridBagConstraints gbcLblNameContent = new GridBagConstraints();
        gbcLblNameContent.fill = GridBagConstraints.HORIZONTAL;
        gbcLblNameContent.insets = new Insets(0, 50, 5, 0);
        gbcLblNameContent.gridx = 1;
        gbcLblNameContent.gridy = 4;
        add(lblNameContent, gbcLblNameContent);

        JLabel lblDescription = new JLabel(this.resources.getString("gui.dept.description"));
        GridBagConstraints gbcLblDescription = new GridBagConstraints();
        gbcLblDescription.anchor = GridBagConstraints.WEST;
        gbcLblDescription.insets = new Insets(0, 5, 5, 5);
        gbcLblDescription.gridx = 0;
        gbcLblDescription.gridy = 5;
        add(lblDescription, gbcLblDescription);

        lblDescriptionConent = new JLabel("");
        GridBagConstraints gbcLblDescriptionValue = new GridBagConstraints();
        gbcLblDescriptionValue.fill = GridBagConstraints.HORIZONTAL;
        gbcLblDescriptionValue.insets = new Insets(0, 50, 5, 0);
        gbcLblDescriptionValue.gridx = 1;
        gbcLblDescriptionValue.gridy = 5;
        add(lblDescriptionConent, gbcLblDescriptionValue);

        JLabel lblHead = new JLabel(this.resources.getString("gui.dept.head"));
        GridBagConstraints gbcLblHead = new GridBagConstraints();
        gbcLblHead.anchor = GridBagConstraints.WEST;
        gbcLblHead.insets = new Insets(0, 5, 5, 5);
        gbcLblHead.gridx = 0;
        gbcLblHead.gridy = 6;
        add(lblHead, gbcLblHead);

        lblHeadContent = new JLabel("");
        GridBagConstraints gbcLblHeadContent = new GridBagConstraints();
        gbcLblHeadContent.fill = GridBagConstraints.HORIZONTAL;
        gbcLblHeadContent.insets = new Insets(0, 50, 5, 0);
        gbcLblHeadContent.gridx = 1;
        gbcLblHeadContent.gridy = 6;
        add(lblHeadContent, gbcLblHeadContent);

        JButton btnCancel = new JButton(this.resources.getString("gui.general.cancel"));
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                displayDepartmentController.cancelHandler(e);
            }
        });
        GridBagConstraints gbcBtnCancel = new GridBagConstraints();
        gbcBtnCancel.anchor = GridBagConstraints.WEST;
        gbcBtnCancel.insets = new Insets(0, 5, 0, 5);
        gbcBtnCancel.gridx = 0;
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
     * @return the lblCodeContent
     */
    public JLabel getLblCodeContent() {
        return lblCodeContent;
    }

    /**
     * @return the lblNameContent
     */
    public JLabel getLblNameContent() {
        return lblNameContent;
    }

    /**
     * @return the lblDescriptionConent
     */
    public JLabel getLblDescriptionConent() {
        return lblDescriptionConent;
    }

    /**
     * @return the lblHeadContent
     */
    public JLabel getLblHeadContent() {
        return lblHeadContent;
    }
}
