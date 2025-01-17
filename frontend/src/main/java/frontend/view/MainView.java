package frontend.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import frontend.controller.MainViewController;

/**
 * The entry point view of the application. It provides a menu as well as a content area.
 *
 * @author Michael
 */
public class MainView extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * The width of the MainView.
     */
    private static final int VIEW_WIDTH = 640;

    /**
     * The height of the MainView.
     */
    private static final int VIEW_HEIGHT = 480;

    /**
     * The controller of this view.
     */
    @SuppressWarnings("unused")
    private MainViewController mainViewController;

    /**
     * Access to localized application resources.
     */
    private ResourceBundle resources;

    /**
     * Creates the main view.
     *
     * @param mainViewController The controller of this view
     */
    public MainView(final MainViewController mainViewController) {
        this.resources = ResourceBundle.getBundle("frontend");
        this.mainViewController = mainViewController;
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(VIEW_WIDTH, VIEW_HEIGHT));
        setTitle(this.resources.getString("gui.title"));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(final WindowEvent arg0) {
                // Perform additional tasks on closing of view.
                mainViewController.onMainViewClose();
            }
        });

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuHr = new JMenu(this.resources.getString("gui.mainMenu.hr"));
        menuBar.add(menuHr);

        JMenu menuEmployee = new JMenu(this.resources.getString("gui.mainMenu.hr.employee"));
        menuHr.add(menuEmployee);

        JMenuItem miEmployeeCreate = new JMenuItem(this.resources.getString("gui.mainMenu.object.create"));
        miEmployeeCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                mainViewController.switchToCreateEmployeeView();
            }
        });
        menuEmployee.add(miEmployeeCreate);

        JMenuItem miEmployeeEdit = new JMenuItem(this.resources.getString("gui.mainMenu.object.edit"));
        miEmployeeEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                mainViewController.switchToEditEmployeeView(null);
            }
        });
        menuEmployee.add(miEmployeeEdit);

        JMenuItem miEmployeeDisplay = new JMenuItem(this.resources.getString("gui.mainMenu.object.display"));
        miEmployeeDisplay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                mainViewController.switchToDisplayEmployeeView(null);
            }
        });
        menuEmployee.add(miEmployeeDisplay);

        JMenuItem miEmployeeOverview = new JMenuItem(this.resources.getString("gui.mainMenu.object.overview"));
        miEmployeeOverview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                mainViewController.switchToEmployeeOverview(null);
            }
        });
        menuEmployee.add(miEmployeeOverview);

        JMenu menuDepartment = new JMenu(this.resources.getString("gui.mainMenu.hr.dept"));
        menuHr.add(menuDepartment);

        JMenuItem miDepartmentCreate = new JMenuItem(this.resources.getString("gui.mainMenu.object.create"));
        miDepartmentCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                mainViewController.switchToCreateDepartmentView();
            }
        });
        menuDepartment.add(miDepartmentCreate);

        JMenuItem miDepartmentEdit = new JMenuItem(this.resources.getString("gui.mainMenu.object.edit"));
        miDepartmentEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                mainViewController.switchToEditDepartmentView();
            }
        });
        menuDepartment.add(miDepartmentEdit);

        JMenuItem miDepartmentDisplay = new JMenuItem(this.resources.getString("gui.mainMenu.object.display"));
        miDepartmentDisplay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                mainViewController.switchToDisplayDepartmentView();
            }
        });
        menuDepartment.add(miDepartmentDisplay);

        JMenuItem miDepartmentOverview = new JMenuItem(this.resources.getString("gui.mainMenu.object.overview"));
        miDepartmentOverview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                mainViewController.switchToDepartmentOverview();
            }
        });
        menuDepartment.add(miDepartmentOverview);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
