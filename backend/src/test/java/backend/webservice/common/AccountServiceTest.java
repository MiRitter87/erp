package backend.webservice.common;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import backend.dao.AccountDao;
import backend.dao.DAOManager;
import backend.model.Currency;
import backend.model.account.Account;

/**
 * Tests the account service.
 * 
 * @author Michael
 */
public class AccountServiceTest {
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
}
