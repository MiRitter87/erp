package backend.dao;

import java.util.List;

import backend.exception.ObjectUnchangedException;
import backend.model.account.Account;

/**
 * Provides access to account database persistence using Hibernate.
 * 
 * @author Michael
 */
public class AccountHibernateDao implements AccountDao {
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
