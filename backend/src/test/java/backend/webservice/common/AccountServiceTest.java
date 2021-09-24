package backend.webservice.common;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.dao.AccountDao;
import backend.dao.DAOManager;
import backend.model.Currency;
import backend.model.account.Account;
import backend.model.webservice.WebServiceMessageType;
import backend.model.webservice.WebServiceResult;
import backend.tools.WebServiceTools;

/**
 * Tests the account service.
 * 
 * @author Michael
 */
public class AccountServiceTest {
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources = ResourceBundle.getBundle("backend");	
	
	/**
	 * DAO to access account data.
	 */
	private static AccountDao accountDAO;
	
	/**
	 * The first account under test.
	 */
	private Account account1;
	
	/**
	 * The second account under test.
	 */
	private Account account2;
	
	
	@BeforeAll
	/**
	 * Tasks to be performed once at startup of test class.
	 */
	public static void setUpClass() {
		accountDAO = DAOManager.getInstance().getAccountDAO();
	}
	
	
	@AfterAll
	/**
	 * Tasks to be performed once at end of test class.
	 */
	public static void tearDownClass() {
		try {
			DAOManager.getInstance().close();
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	
	@BeforeEach
	/**
	 * Tasks to be performed before each test is run.
	 */
	private void setUp() {
		this.createDummyAccounts();
	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	private void tearDown() {
		this.deleteDummyAccounts();
	}
	
	
	/**
	 * Initializes the database with dummy accounts.
	 */
	private void createDummyAccounts() {
		this.account1 = new Account();
		this.account1.setDescription("Account 1");
		this.account1.setBalance(BigDecimal.valueOf(1500.00));
		this.account1.setCurrency(Currency.EUR);
		
		this.account2 = new Account();
		this.account2.setDescription("Account 2");
		this.account2.setBalance(BigDecimal.valueOf(9999.23));
		this.account2.setCurrency(Currency.EUR);
		
		try {
			accountDAO.insertAccount(this.account1);
			accountDAO.insertAccount(this.account2);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Deletes the dummy accounts from the database.
	 */
	private void deleteDummyAccounts() {
		try {
			accountDAO.deleteAccount(this.account2);
			accountDAO.deleteAccount(this.account1);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests the retrieval of an account.
	 */
	public void testGetAccount() {
		WebServiceResult getAccountResult;
		Account account;
		
		//Get the account
		AccountService service = new AccountService();
		getAccountResult = service.getAccount(this.account2.getId());
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(getAccountResult) == false);
		
		//Assure that an account is returned
		assertTrue(getAccountResult.getData() instanceof Account);
		
		account = (Account) getAccountResult.getData();
		
		//Check each attribute of the account
		assertEquals(account.getId(), this.account2.getId());
		assertEquals(account.getDescription(), this.account2.getDescription());
		assertEquals(account.getBalance(), this.account2.getBalance());
		assertEquals(account.getCurrency(), this.account2.getCurrency());
	}
	
	
	@Test
	/**
	 * Tests the retrieval of an account that does not exist.
	 */
	public void testGetNonExistentAccount() {
		Integer accountId = this.account2.getId()+1;
		WebServiceResult getAccountResult;
		String expectedErrorMessage, actualErrorMessage;
		
		//Get the account
		AccountService service = new AccountService();
		getAccountResult = service.getAccount(accountId);
		
		//Assure that no account is returned
		assertNull(getAccountResult.getData());
		
		//There should be a return message of type E.
		assertTrue(getAccountResult.getMessages().size() == 1);
		assertTrue(getAccountResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//Verify the expected error message.
		expectedErrorMessage = MessageFormat.format(this.resources.getString("account.notFound"), accountId);
		actualErrorMessage = getAccountResult.getMessages().get(0).getText();
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
}
