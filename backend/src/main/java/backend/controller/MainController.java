package backend.controller;

import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backend.dao.DAOManager;


/**
 * The main controller of the web application.
 * 
 * @author Michael
 */
public class MainController {
	/**
	 * Instance of the main controller.
	 */
	private static MainController instance;
	
	/**
	 * Application logging.
	 */
	public static final Logger logger = LogManager.getLogger(MainController.class);
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources;
	
	
	/**
	 * Creates and initializes the main controller.
	 */
	private MainController() {
		this.resources = ResourceBundle.getBundle("backend");
	}
	
	/**
	 * Provides the instance of the MainController.
	 * 
	 * @return The instance of the MainController.
	 */
	public static MainController getInstance() {
		if(instance == null)
			instance = new MainController();
		
		return instance;
	}
	
	
	/**
	 * Performs tasks on application startup.
	 */
	public void applicationStartup() {
		DAOManager.getInstance();
		logger.info(this.resources.getString("status.started"));
	}

	/**
	 * Performs tasks on application shutdown.
	 */
	public void applicationShutdown() {
		try {
			DAOManager.getInstance().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//The logger is already disabled here. Workaround: Therefore log to std-out.		
		System.out.println(this.resources.getString("status.stopped"));
	}
}
