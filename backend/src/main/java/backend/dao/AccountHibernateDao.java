package backend.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import backend.exception.ObjectUnchangedException;
import backend.model.account.Account;

/**
 * Provides access to account database persistence using Hibernate.
 * 
 * @author Michael
 */
public class AccountHibernateDao implements AccountDao {
	/**
	 * Factory for database session.
	 */
	protected EntityManagerFactory sessionFactory;
	
	
	/**
	 * Default constructor.
	 * 
	 * @param sessionFactory The database session factory.
	 */
	public AccountHibernateDao(final EntityManagerFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	@Override
	public void insertAccount(Account account) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		try {
			entityManager.persist(account);
			entityManager.flush();	//Assures, that the generated account ID is available.
			entityManager.getTransaction().commit();
		}
		catch(Exception exception) {
			//If something breaks a rollback is necessary!?
			if(entityManager.getTransaction().isActive())
				entityManager.getTransaction().rollback();
			throw exception;
		}
		finally {
			entityManager.close();			
		}
	}

	
	@Override
	public void deleteAccount(Account account) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		
		//In order to successfully delete an entity, it first has to be fetched from the database.
		Account deleteAccount = entityManager.find(Account.class, account.getId());
		
		entityManager.getTransaction().begin();
		
		try {
			entityManager.remove(deleteAccount);
			entityManager.getTransaction().commit();			
		}
		catch(Exception exception) {
			//If something breaks a rollback is necessary.
			if(entityManager.getTransaction().isActive())
				entityManager.getTransaction().rollback();
			throw exception;
		}
		finally {
			entityManager.close();			
		}
	}

	
	@Override
	public List<Account> getAccounts() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public Account getAccount(Integer id) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
		Account account = entityManager.find(Account.class, id);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return account;
	}

	
	@Override
	public void updateAccount(Account account) throws ObjectUnchangedException, Exception {
		// TODO Auto-generated method stub
	}
}
