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
     * Entry point of the application.
     *
     * @param args Arguments given by the caller
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new MainController());
    }

    /**
     * Initializes the controller.
     */
    public MainController() {
        // This property is used by log4j to find the configuration file
        System.setProperty("log4j.configurationFile", "conf/log4j2.properties");
    }

    /**
     * The run method is being executed when the MainController thread is started.
     */
    @Override
    public void run() {
        this.mainViewController = new MainViewController();
    }

    /**
     * @return the mainViewController
     */
    public MainViewController getMainViewController() {
        return mainViewController;
    }

    /**
     * @param mainViewController the mainViewController to set
     */
    public void setMainViewController(final MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }
}
