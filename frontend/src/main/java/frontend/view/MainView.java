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
 * The entry point view of the application.
 * It provides a menu as well as a content area.
 * 
 * @author Michael
 */
public class MainView extends JFrame {	

	private static final long serialVersionUID = 1L;

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
		this.setSize(new Dimension(640, 480));
		setTitle(this.resources.getString("gui.title"));
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				//Perform additional tasks on closing of view.
				mainViewController.onMainViewClose();
			}
		});
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuHr = new JMenu(this.resources.getString("gui.mainMenu.hr"));
		menuBar.add(menuHr);
		
		JMenuItem menuDepartment = new JMenuItem(this.resources.getString("gui.mainMenu.hr.dept"));
		menuDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainViewController.switchToDepartmentView();				
			}
		});
		
		JMenu menuEmployee = new JMenu(this.resources.getString("gui.mainMenu.hr.employee"));
		menuHr.add(menuEmployee);
		
		JMenuItem miEmployeeCreate = new JMenuItem(this.resources.getString("gui.mainMenu.object.create"));
		menuEmployee.add(miEmployeeCreate);
		
		JMenuItem miEmployeeOverview = new JMenuItem(this.resources.getString("gui.mainMenu.object.overview"));
		miEmployeeOverview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainViewController.switchToEmployeeBasicDataView(null);	
			}
		});
		
		JMenuItem miEmployeeEdit = new JMenuItem(this.resources.getString("gui.mainMenu.object.edit"));
		miEmployeeEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainViewController.switchToEditEmployeeView(null);
			}
		});
		menuEmployee.add(miEmployeeEdit);
		
		JMenuItem miEmployeeDisplay = new JMenuItem(this.resources.getString("gui.mainMenu.object.display"));
		miEmployeeDisplay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainViewController.switchToDisplayEmployeeView(null);
			}
		});
		menuEmployee.add(miEmployeeDisplay);
		menuEmployee.add(miEmployeeOverview);
		miEmployeeCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainViewController.switchToCreateEmployeeView();
			}
		});
		menuHr.add(menuDepartment);
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
