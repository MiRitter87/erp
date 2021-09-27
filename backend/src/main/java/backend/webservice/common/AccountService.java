package backend.webservice.common;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backend.dao.AccountDao;
import backend.dao.DAOManager;
import backend.model.account.Account;
import backend.model.account.AccountArray;
import backend.model.webservice.WebServiceMessage;
import backend.model.webservice.WebServiceMessageType;
import backend.model.webservice.WebServiceResult;

/**
 * Common implementation of the account WebService that is used by the SOAP as well as the REST service.
 * 
 * @author Michael
 */
public class AccountService {
	/**
	 * DAO for account access.
	 */
	private AccountDao accountDAO;
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources = ResourceBundle.getBundle("backend");
	
	/**
	 * Application logging.
	 */
	public static final Logger logger = LogManager.getLogger(AccountService.class);
	
	
	/**
	 * Initializes the account service.
	 */
	public AccountService() {
		this.accountDAO = DAOManager.getInstance().getAccountDAO();
	}
	
	
	/**
	 * Provides the account with the given id.
	 * 
	 * @param id The id of the account.
	 * @return The account with the given id, if found.
	 */
	public WebServiceResult getAccount(final Integer id) {
		Account account = null;
		WebServiceResult getAccountResult = new WebServiceResult(null);
		
		try {
			account = this.accountDAO.getAccount(id);
			
			if(account != null) {
				//Account found
				getAccountResult.setData(account);
			}
			else {
				//Account not found
				getAccountResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
						MessageFormat.format(this.resources.getString("account.notFound"), id)));
			}
		}
		catch (Exception e) {
			getAccountResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
					MessageFormat.format(this.resources.getString("account.getError"), id)));
			
			logger.error(MessageFormat.format(this.resources.getString("account.getError"), id), e);
		}
		
		return getAccountResult;
	}
	
	
	/**
	 * Provides a list of all accounts.
	 * 
	 * @return A list of all accounts.
	 */
	public WebServiceResult getAccounts() {
		AccountArray accounts = new AccountArray();
		WebServiceResult getAccountsResult = new WebServiceResult(null);
		
		try {
			accounts.setAccounts(this.accountDAO.getAccounts());
			getAccountsResult.setData(accounts);
		} catch (Exception e) {
			getAccountsResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, this.resources.getString("account.getAccountsError")));
			
			logger.error(this.resources.getString("account.getAccountsError"), e);
		}
		
		return getAccountsResult;
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
