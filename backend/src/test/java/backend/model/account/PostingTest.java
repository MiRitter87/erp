package backend.model.account;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.model.Currency;
import backend.model.businessPartner.BusinessPartner;
import backend.tools.test.ValidationMessageProvider;

/**
 * Tests the posting model.
 * 
 * @author Michael
 */
public class PostingTest {
	/**
	 * The posting under test.
	 */
	protected Posting posting;
	
	/**
	 * The counterparty of the posting.
	 */
	protected BusinessPartner counterparty;
	
	
	@BeforeEach
	/**
	 * Tasks to be performed before each test is run.
	 */
	protected void setUp() {
		this.initCounterparty();
		this.initPosting();		
	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	protected void tearDown() {
		this.posting = null;
		this.counterparty = null;
	}
	
	
	/**
	 * Initializes the counterparty.
	 */
	private void initCounterparty() {
		this.counterparty = new BusinessPartner();
		this.counterparty.setId(1);
		this.counterparty.setCompanyName("Amalgamated Moose Pasture");
		this.counterparty.setFirstName("John");
		this.counterparty.setLastName("Doe");
		this.counterparty.setStreetName("Main Street");
		this.counterparty.setHouseNumber("1a");
		this.counterparty.setZipCode("12345");
		this.counterparty.setCityName("Moose City");
		this.counterparty.setPhoneNumber("+1 123-456-7890");
	}
	
	
	/**
	 * Initializes the posting.
	 */
	private void initPosting() {
		this.posting = new Posting();
		this.posting.setId(Integer.valueOf(1));
		this.posting.setType(PostingType.RECEIPT);
		this.posting.setTimestamp(new Date());
		this.posting.setCounterparty(this.counterparty);
		this.posting.setReferenceNumber("SO4711");
		this.posting.setAmount(BigDecimal.valueOf(Double.valueOf(500)));
		this.posting.setCurrency(Currency.EUR);
	}
	
	
	@Test
	/**
	 * Tests validation of a valid posting.
	 */
	public void testValidationSuccess() {
		try {
			this.posting.validate();
		}
		catch(Exception exception) {
			fail(exception.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests validation of a posting whose ID is too low.
	 */
	public void testIdTooLow() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.posting.setId(0);
		
		String expectedErrorMessage = messageProvider.getMinValidationMessage("posting", "id", "1");
		String errorMessage = "";
		
		try {
			this.posting.validate();
			fail("Validation should have failed because Id is too low.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of a posting whose type is null.
	 */
	public void testTypeIsNull() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.posting.setType(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("posting", "type");
		String errorMessage = "";
		
		try {
			this.posting.validate();
			fail("Validation should have failed because type is null.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of a posting that has no counterparty defined.
	 */
	public void testNoCounterpartyDefined() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();	
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("posting", "counterparty");
		String actualErrorMessage = "";
		
		this.posting.setCounterparty(null);
		
		try {
			this.posting.validate();
			fail("Validation should have failed because no counterparty is defined.");
		} catch (Exception expected) {
			actualErrorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of a posting whose reference number is too long.
	 */
	public void testReferenceNumberTooLong() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.posting.setReferenceNumber("Das ist eine Referenznummer der Buchung, die zu lang ist");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("posting", "referenceNumber", 
				String.valueOf(this.posting.getReferenceNumber().length()), "0", "54");
		String errorMessage = "";
		
		try {
			this.posting.validate();
			fail("Validation should have failed because the reference number is too long.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of a posting whose amount is null.
	 */
	public void testAmountIsNull() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.posting.setAmount(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("posting", "amount");
		String errorMessage = "";
		
		try {
			this.posting.validate();
			fail("Validation should have failed because amount is null.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of a posting whose amount is too low.
	 */
	public void testAmountTooLow() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.posting.setAmount(BigDecimal.valueOf(Double.valueOf(0.009)));
		
		String expectedErrorMessage = messageProvider.getDecimalMinValidationMessage("posting", "amount", "0.01");
		String errorMessage = "";
		
		try {
			this.posting.validate();
			fail("Validation should have failed because amount is too low.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of a posting whose currency is null.
	 */
	public void testCurrencyIsNull() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.posting.setCurrency(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("posting", "currency");
		String errorMessage = "";
		
		try {
			this.posting.validate();
			fail("Validation should have failed because currency is null.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
}
