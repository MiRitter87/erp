package frontend.controller;

import javax.swing.SwingUtilities;


/**
 * The main controller of the application.
 * 
 * @author Michael
 */
public class MainController implements Runnable {
	/**
	 * The controller of the main view.
	 */
	private MainViewController mainViewController;
	
	
	/**
	 * Entry point of the application
	 * 
	 * @param args Arguments given by the caller
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new MainController());
	}
	

	/**
	 * Initializes the controller.
	 */
	MainController() {
		//This property is used by log4j to find the configuration file
		System.setProperty("log4j.configurationFile","conf/log4j2.properties");
	}

	public void run() {
		this.mainViewController = new MainViewController();
	}

	public MainViewController getMainViewController() {
		return mainViewController;
	}

	public void setMainViewController(MainViewController mainViewController) {
		this.mainViewController = mainViewController;
	}
}
