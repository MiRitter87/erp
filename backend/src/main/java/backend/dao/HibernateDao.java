package backend.dao;

import java.io.Closeable;
import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateDao implements Closeable {
	/**
	 * Factory for database session.
	 */
	protected EntityManagerFactory sessionFactory;
	
	
	public HibernateDao() {
		this.sessionFactory = this.getSessionFactory();
		
		//TODO: Create database if not exists. Currently Hibernate uses automatic schema creation not suitable for production.
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

	
	@Override
	public void close() throws IOException {
		try {
			this.sessionFactory.close();		
		}
		catch(IllegalStateException exception) {
			throw new IOException(exception.getMessage());
		}
	}

}
