package backend.webservice.common;

import backend.model.account.Account;
import backend.model.webservice.WebServiceResult;

/**
 * Common implementation of the account WebService that is used by the SOAP as well as the REST service.
 * 
 * @author Michael
 */
public class AccountService {
	/**
	 * Provides the account with the given id.
	 * 
	 * @param id The id of the account.
	 * @return The account with the given id, if found.
	 */
	public WebServiceResult getAccount(final Integer id) {
		return null;
	}
	
	
	/**
	 * Provides a list of all accounts.
	 * 
	 * @return A list of all accounts.
	 */
	public WebServiceResult getAccounts() {
		return null;
	}
	
	
	/**
	 * Adds an account.
	 * 
	 * @param account The account to be added.
	 * @return The result of the add function.
	 */
	public WebServiceResult addAccount(final Account account) {
		return null;
	}
	
	
	/**
	 * Deletes the account with the given id.
	 * 
	 * @param id The id of the account to be deleted.
	 * @return The result of the delete function.
	 */
	public WebServiceResult deleteAccount(final Integer id) {
		return null;
	}
	
	
	/**
	 * Updates an existing account.
	 * 
	 * @param account The account to be updated.
	 * @return The result of the update function.
	 */
	public WebServiceResult updateAccount(final Account account) {
		return null;
	}
}
