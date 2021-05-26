package backend.dao;

import java.io.Closeable;
import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Manages a central database connection and provides DAOs for database access.
 * 
 * @author Michael
 */
public class DAOManager implements Closeable {
	/**
	 * Instance of this class.
	 */
	private static DAOManager instance;
	
	/**
	 * Factory for database session.
	 */
	protected EntityManagerFactory sessionFactory;
	
	/**
	 * DAO to manage employee data.
	 */
	protected EmployeeDao employeeDao;
	
	
	/**
	 * Initializes the DAOManager.
	 */
	private DAOManager() {
		this.sessionFactory = this.getSessionFactory();
	}
	
	
	/**
	 * Provides the instance of the DAOManager.
	 * 
	 * @return The instance of the DAOManager.
	 */
	public static DAOManager getInstance() {
		if(instance == null)
			instance = new DAOManager();
		
		return instance;
	}
	
	
	/**
	 * Builds a session factory for database access.
	 * 
	 * @return Session factory for database access.
	 */
	private EntityManagerFactory getSessionFactory() {
		//The given string must match with the persistence unit defined in the persistence.xml file.
		return Persistence.createEntityManagerFactory("my-persistence-unit");
	}
	
	
	/**
	 * Returns a DAO to manage employee data.
	 * 
	 * @return
	 */
	public EmployeeDao getEmployeeDAO() {
		if(this.employeeDao == null)
			this.employeeDao = new EmployeeHibernateDao(this.sessionFactory);
		
		return this.employeeDao;
	}

	
	@Override
	public void close() throws IOException {
		try {
			this.sessionFactory.close();
			instance = null;
		}
		catch(IllegalStateException exception) {
			throw new IOException(exception.getMessage());
		}
	}
}
