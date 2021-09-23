package backend.model.account;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.model.Currency;
import backend.tools.test.ValidationMessageProvider;

/**
 * Tests the account model.
 * 
 * @author Michael
 */
public class AccountTest {
	/**
	 * The account under test.
	 */
	protected Account account;
	
	
	@BeforeEach
	/**
	 * Tasks to be performed before each test is run.
	 */
	protected void setUp() {
		this.account = new Account();
		this.account.setId(Integer.valueOf(1));
		this.account.setDescription("Geschäftskonto - Europa");
		this.account.setBalance(BigDecimal.valueOf(Double.valueOf(189000)));
		this.account.setCurrency(Currency.EUR);
	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	protected void tearDown() {
		this.account = null;
	}
	
	
	@Test
	/**
	 * Tests validation of a valid account.
	 */
	public void testValidationSuccess() {
		try {
			this.account.validate();
		}
		catch(Exception exception) {
			fail(exception.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests validation of an account whose ID is null.
	 */
	public void testIdIsNull() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.account.setId(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("account", "id");
		String errorMessage = "";
		
		try {
			this.account.validate();
			fail("Validation should have failed because Id is null.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of an account whose ID is too low.
	 */
	public void testIdTooLow() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.account.setId(0);
		
		String expectedErrorMessage = messageProvider.getMinValidationMessage("account", "id", "1");
		String errorMessage = "";
		
		try {
			this.account.validate();
			fail("Validation should have failed because Id is too low.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of an account whose description is null.
	 */
	public void testDescriptionIsNull() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.account.setDescription(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("account", "description");
		String errorMessage = "";
		
		try {
			this.account.validate();
			fail("Validation should have failed because description is null.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of an account whose description is not given.
	 */
	public void testDescriptionNotGiven() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.account.setDescription("");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("account", "description", 
				String.valueOf(this.account.getDescription().length()), "1", "100");
		String errorMessage = "";
		
		try {
			this.account.validate();
			fail("Validation should have failed because description is not given.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of an account whose description is too long.
	 */
	public void testDescriptionTooLong() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.account.setDescription("Das ist eine Beschreibung des Kontos, die länger als erlaubt ist. Es soll eine Fehlermeldung ausgegeg");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("account", "description", 
				String.valueOf(this.account.getDescription().length()), "1", "100");
		String errorMessage = "";
		
		try {
			this.account.validate();
			fail("Validation should have failed because description is too long.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of an account whose balance is null.
	 */
	public void testBalanceIsNull() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.account.setBalance(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("account", "balance");
		String errorMessage = "";
		
		try {
			this.account.validate();
			fail("Validation should have failed because balance is null.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of an account whose currency is null.
	 */
	public void testCurrencyIsNull() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.account.setCurrency(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("account", "currency");
		String errorMessage = "";
		
		try {
			this.account.validate();
			fail("Validation should have failed because currency is null.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
}
