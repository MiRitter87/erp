package backend.dao;

import java.util.List;

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
		// TODO Auto-generated method stub
	}

	
	@Override
	public void deleteAccount(Account account) throws Exception {
		// TODO Auto-generated method stub
	}

	
	@Override
	public List<Account> getAccounts() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public Account getAccount(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void updateAccount(Account account) throws ObjectUnchangedException, Exception {
		// TODO Auto-generated method stub
	}
}
