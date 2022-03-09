package backend.model.businessPartner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.model.businessPartner.BusinessPartner;
import backend.model.businessPartner.BusinessPartnerType;
import backend.tools.test.ValidationMessageProvider;

/**
 * Tests the business partner model.
 * 
 * @author Michael
 */
public class BusinessPartnerTest {
	/**
	 * The business partner under test.
	 */
	protected BusinessPartner moose;
	
	
	@BeforeEach
	/**
	 * Tasks to be performed before each test is run.
	 */
	protected void setUp() {
		this.moose = new BusinessPartner();
		this.moose.setId(Integer.valueOf(1));
		this.moose.setCompanyName("Amalgamated Moose Pasture");
		this.moose.setFirstName("John");
		this.moose.setLastName("Doe");
		this.moose.setStreetName("Main Street");
		this.moose.setHouseNumber("1a");
		this.moose.setZipCode("12345");
		this.moose.setCityName("Moose City");
		this.moose.setPhoneNumber("+1 123-456-7890");
		this.moose.addType(BusinessPartnerType.CUSTOMER);

	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	protected void tearDown() {
		this.moose = null;
	}
	
	
	@Test
	/**
	 * Tests validation of a valid business partner.
	 */
	public void testValidationSuccess() {
		try {
			this.moose.validate();
		}
		catch(Exception exception) {
			fail(exception.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests the validation of a business partner whose ID is negative.
	 */
	public void testNegativeId() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.moose.setId(Integer.valueOf(-1));
		
		String expectedErrorMessage = messageProvider.getMinValidationMessage("businessPartner", "id", "1");
		String errorMessage = "";
		
		try {
			this.moose.validate();
			fail("Validation should have failed because ID is negative.");
		}
		catch(Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests the validation of a business partner whose company name is not given.
	 */
	public void testCompanyNameNotGiven() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.moose.setCompanyName("");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("businessPartner", "companyName", 
				String.valueOf(this.moose.getCompanyName().length()), "1", "100");
		String errorMessage = "";
		
		try {
			this.moose.validate();
			fail("Validation should have failed because company name is not given.");
		}
		catch(Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests the validation of a business partner whose company name is too long.
	 */
	public void testCompanyNameTooLong() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.moose.setCompanyName("This is a very long company name that exceeds the maximum amount of characters that can be used as a.");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("businessPartner", "companyName", 
				String.valueOf(this.moose.getCompanyName().length()), "1", "100");
		String errorMessage = "";
		
		try {
			this.moose.validate();
			fail("Validation should have failed because company name is too long.");
		}
		catch(Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests the validation of a business partner whose company name is null.
	 */
	public void testCompanyNameIsNull() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.moose.setCompanyName(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("businessPartner", "companyName");
		String errorMessage = "";
		
		try {
			this.moose.validate();
			fail("Validation should have failed because company name is null.");
		}
		catch(Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests the validation of a business partner whose first name is too long.
	 */
	public void testFirstNameTooLong() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.moose.setFirstName("This is a first name which is tooooooooooooooo long");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("businessPartner", "firstName", 
				String.valueOf(this.moose.getFirstName().length()), "0", "50");
		String errorMessage = "";
		
		try {
			this.moose.validate();
			fail("Validation should have failed because first name is too long.");
		}
		catch(Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests the validation of a business partner whose last name is too long.
	 */
	public void testLastNameTooLong() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.moose.setLastName("This is a last name which is toooooooooooooooo long");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("businessPartner", "lastName", 
				String.valueOf(this.moose.getLastName().length()), "0", "50");
		String errorMessage = "";
		
		try {
			this.moose.validate();
			fail("Validation should have failed because last name is too long.");
		}
		catch(Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests the validation of a business partner whose street name is null.
	 */
	public void testStreetNameIsNull() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.moose.setStreetName(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("businessPartner", "streetName");
		String errorMessage = "";
		
		try {
			this.moose.validate();
			fail("Validation should have failed because street name is null.");
		}
		catch(Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests the validation of a business partner whose street name is not given.
	 */
	public void testStreetNameNotGiven() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.moose.setStreetName("");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("businessPartner", "streetName", 
				String.valueOf(this.moose.getStreetName().length()), "1", "100");
		String errorMessage = "";
		
		try {
			this.moose.validate();
			fail("Validation should have failed because street name is not given.");
		}
		catch(Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests the validation of a business partner whose street name is too long.
	 */
	public void testStreetNameTooLong() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.moose.setStreetName("This is a street name that is waaaaaay toooooooooooooooooooo loooooooooooooooooooooooooooooooooooong.");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("businessPartner", "streetName", 
				String.valueOf(this.moose.getStreetName().length()), "1", "100");
		String errorMessage = "";
		
		try {
			this.moose.validate();
			fail("Validation should have failed because street name is too long.");
		}
		catch(Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests the validation of a business partner whose house number is null.
	 */
	public void testHouseNumberIsNull() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.moose.setHouseNumber(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("businessPartner", "houseNumber");
		String errorMessage = "";
		
		try {
			this.moose.validate();
			fail("Validation should have failed because house number is null.");
		}
		catch(Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests the validation of a business partner whose house number is not given.
	 */
	public void testHouseNumberNotGiven() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.moose.setHouseNumber("");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("businessPartner", "houseNumber", 
				String.valueOf(this.moose.getHouseNumber().length()), "1", "6");
		String errorMessage = "";
		
		try {
			this.moose.validate();
			fail("Validation should have failed because house number is not given.");
		}
		catch(Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests the validation of a business partner whose house number is too long.
	 */
	public void testHouseNumberTooLong() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.moose.setHouseNumber("123456a");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("businessPartner", "houseNumber", 
				String.valueOf(this.moose.getHouseNumber().length()), "1", "6");
		String errorMessage = "";
		
		try {
			this.moose.validate();
			fail("Validation should have failed because house number is too long.");
		}
		catch(Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests the validation of a business partner whose ZIP code is null.
	 */
	public void testZipCodeIsNull() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.moose.setZipCode(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("businessPartner", "zipCode");
		String errorMessage = "";
		
		try {
			this.moose.validate();
			fail("Validation should have failed because ZIP code is null.");
		}
		catch(Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests the validation of a business partner whose ZIP code is not given.
	 */
	public void testZipCodeNotGiven() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.moose.setZipCode("");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("businessPartner", "zipCode", 
				String.valueOf(this.moose.getZipCode().length()), "1", "5");
		String errorMessage = "";
		
		try {
			this.moose.validate();
			fail("Validation should have failed because ZIP code is not given.");
		}
		catch(Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests the validation of a business partner whose ZIP code is too long.
	 */
	public void testZipCodeTooLong() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.moose.setZipCode("123456");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("businessPartner", "zipCode", 
				String.valueOf(this.moose.getZipCode().length()), "1", "5");
		String errorMessage = "";
		
		try {
			this.moose.validate();
			fail("Validation should have failed because ZIP code is too long.");
		}
		catch(Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests the validation of a business partner whose city name is null.
	 */
	public void testCityNameIsNull() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.moose.setCityName(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("businessPartner", "cityName");
		String errorMessage = "";
		
		try {
			this.moose.validate();
			fail("Validation should have failed because city name is null.");
		}
		catch(Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests the validation of a business partner whose city name is not given.
	 */
	public void testCityNameNotGiven() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.moose.setCityName("");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("businessPartner", "cityName", 
				String.valueOf(this.moose.getCityName().length()), "1", "100");
		String errorMessage = "";
		
		try {
			this.moose.validate();
			fail("Validation should have failed because city name is not given.");
		}
		catch(Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests the validation of a business partner whose city name is too long.
	 */
	public void testCityNameTooLong() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.moose.setCityName("This is a city name that is waaaaaay toooooooooooooooooooo loooooooooooooooooooooooooooooooooooooong.");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("businessPartner", "cityName", 
				String.valueOf(this.moose.getCityName().length()), "1", "100");
		String errorMessage = "";
		
		try {
			this.moose.validate();
			fail("Validation should have failed because city name is too long.");
		}
		catch(Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests the validation of a business partner whose phone number is null.
	 */
	public void testPhoneNumberIsNull() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.moose.setPhoneNumber(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("businessPartner", "phoneNumber");
		String errorMessage = "";
		
		try {
			this.moose.validate();
			fail("Validation should have failed because phone number is null.");
		}
		catch(Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests the validation of a business partner whose phone number is not given.
	 */
	public void testPhoneNumberNotGiven() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.moose.setPhoneNumber("");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("businessPartner", "phoneNumber", 
				String.valueOf(this.moose.getPhoneNumber().length()), "1", "40");
		String errorMessage = "";
		
		try {
			this.moose.validate();
			fail("Validation should have failed because phone number is not given.");
		}
		catch(Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests the validation of a business partner whose phone number is too long.
	 */
	public void testPhoneNumberTooLong() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.moose.setPhoneNumber("1234-1234-1234-1234-1234-1234-1234-1234-1");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("businessPartner", "phoneNumber", 
				String.valueOf(this.moose.getPhoneNumber().length()), "1", "40");
		String errorMessage = "";
		
		try {
			this.moose.validate();
			fail("Validation should have failed because phone number is too long.");
		}
		catch(Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests the equals method by comparing two business partners that have different types defined.
	 */
	public void testEqualsWithDifferentTypes() {
		BusinessPartner testPartner = new BusinessPartner();
		
		//The test partner equals the moose partner in all attributes except "types".
		testPartner.setId(this.moose.getId());
		testPartner.setCompanyName(this.moose.getCompanyName());
		testPartner.setFirstName(this.moose.getFirstName());
		testPartner.setLastName(this.moose.getLastName());
		testPartner.setStreetName(this.moose.getStreetName());
		testPartner.setHouseNumber(this.moose.getHouseNumber());
		testPartner.setZipCode(this.moose.getZipCode());
		testPartner.setCityName(this.moose.getCityName());
		testPartner.setPhoneNumber(this.moose.getPhoneNumber());
		
		testPartner.addType(BusinessPartnerType.CUSTOMER);
		testPartner.addType(BusinessPartnerType.VENDOR);
		
		assertFalse(testPartner.equals(this.moose));
	}
	
	
	@Test
	/**
	 * Tests the validation of a business partner whose set of types is null.
	 */
	public void testTypesIsNull() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.moose.setTypes(null);
		
		String expectedErrorMessage = messageProvider.getNotEmptyValidationMessage("businessPartner", "types");
		String errorMessage = "";
		
		try {
			this.moose.validate();
			fail("Validation should have failed because types is null.");
		}
		catch(Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests the validation of a business partner whose set of types is empty.
	 */
	public void testTypesIsEmpty() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.moose.setTypes(new HashSet<BusinessPartnerType>());
		
		String expectedErrorMessage = messageProvider.getNotEmptyValidationMessage("businessPartner", "types");
		String errorMessage = "";
		
		try {
			this.moose.validate();
			fail("Validation should have failed because types is empty.");
		}
		catch(Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
}
