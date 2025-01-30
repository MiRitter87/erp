package backend.servlet;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import backend.controller.MainController;

/**
 * Handles initialization and destruction of the application context.
 *
 * Initialization is performed on application startup. Destruction is performed on application shutdown.
 *
 * @author Michael
 */
public class BootstrapContextListener implements ServletContextListener {
    /**
     * The MainController of the application.
     */
    private MainController mainController = MainController.getInstance();

    /**
     * Receives notification that the web application initialization process is starting.
     */
    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        this.mainController.applicationStartup();
    }

    /**
     * Receives notification that the ServletContext is about to be shut down.
     */
    @Override
    public void contextDestroyed(final ServletContextEvent sce) {
        this.mainController.applicationShutdown();
    }
}
