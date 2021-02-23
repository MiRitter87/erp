package backend.model;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.tools.test.ValidationMessageProvider;

/**
 * Tests the material model.
 * 
 * @author Michael
 */
public class MaterialTest {
	/**
	 * The material under test.
	 */
	protected Material rx570;
	
	
	@BeforeEach
	/**
	 * Tasks to be performed before each test is run.
	 */
	protected void setUp() {
		this.rx570 = new Material();
		this.rx570.setName("AMD RX570");
		this.rx570.setDescription("Graphic card released in april 2017. It uses the Polaris 20 XL chip.");
		this.rx570.setUnit(UnitOfMeasurement.ST);
		this.rx570.setPricePerUnit(BigDecimal.valueOf(Double.valueOf(189.99)));
		this.rx570.setCurrency(Currency.EUR);
		this.rx570.setInventory(Long.valueOf(10));
	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	protected void tearDown() {
		this.rx570 = null;
	}
	
	
	@Test
	/**
	 * Tests validation of a valid material.
	 */
	public void testValidationSuccess() {
		try {
			this.rx570.validate();
		}
		catch(Exception exception) {
			fail(exception.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests validation of a material whose name is not given.
	 */
	public void testNameNotGiven() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.rx570.setName("");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("material", "name", 
				String.valueOf(this.rx570.getName().length()), "1", "50");
		String errorMessage = "";
		
		try {
			this.rx570.validate();
			fail("Validation should have failed because name is not given.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of a Material whose name is too long.
	 */
	public void testNameTooLong() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.rx570.setName("Dies ist ein Materialname der zu lang ist. Die Vali");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("material", "name", 
				String.valueOf(this.rx570.getName().length()), "1", "50");
		String errorMessage = "";
		
		try {
			this.rx570.validate();
			fail("Validation should have failed because name is too long.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of a material whose name is null.
	 */
	public void testNameIsNull() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.rx570.setName(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("material", "name");
		String errorMessage = "";
		
		try {
			this.rx570.validate();
			fail("Validation should have failed because name is null.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of the material whose description is not given.
	 */
	public void testDescriptionNotGiven() {
		this.rx570.setDescription("");
		
		try {
			this.rx570.validate();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests validation of a material whose description is null.
	 */
	public void testDescriptionIsNull() {
		this.rx570.setDescription(null);
		
		try {
			this.rx570.validate();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests validation of the material whose description is too long.
	 */
	public void testDescriptionTooLong() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		
		this.rx570.setDescription("Das ist ein Beschreibungstext. Das ist ein Beschreibungstext. Das ist ein Beschreibungstext. "
				+ "Das ist ein Beschreibungstext. Das ist ein Beschreibungstext. Das ist ein Beschreibungstext. "
				+ "Das ist ein Beschreibungstext. Das ist ein Beschreibungstext. Das");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("material", "description", 
				String.valueOf(this.rx570.getDescription().length()), "0", "250");
		String errorMessage = "";
		
		try {
			this.rx570.validate();
			fail("Validation should have failed because description is too long.");
		} catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of the material whose ID is too low.
	 */
	public void testIdTooLow() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.rx570.setId(0);
		
		String expectedErrorMessage = messageProvider.getMinValidationMessage("material", "id", "1");
		String errorMessage = "";
		
		try {
			this.rx570.validate();
			fail("Validation should have failed because ID is too low.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of the material if the unit of measurement is not set.
	 */
	public void testUnitNotSet() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.rx570.setUnit(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("material", "unit");
		String errorMessage = "";
		
		try {
			this.rx570.validate();
			fail("Validation should have failed because unit is not set.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of the material if the price per unit is not set.
	 */
	public void testPricePerUnitNotSet() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.rx570.setPricePerUnit(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("material", "pricePerUnit");
		String errorMessage = "";
		
		try {
			this.rx570.validate();
			fail("Validation should have failed because price per unit is not set.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of the material if the currency is not set.
	 */
	public void testCurrencyNotSet() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.rx570.setCurrency(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("material", "currency");
		String errorMessage = "";
		
		try {
			this.rx570.validate();
			fail("Validation should have failed because currency is not set.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of the material if the inventory is not 0 or greater.
	 */
	public void testInventoryNegative() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.rx570.setInventory(Long.valueOf(-1));
		
		String expectedErrorMessage = messageProvider.getMinValidationMessage("material", "inventory", "0");
		String errorMessage = "";
		
		try {
			this.rx570.validate();
			fail("Validation should have failed because inventory is negative.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of the material if the inventory is null.
	 */
	public void testInventoryIsNull( ) {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();	
		this.rx570.setInventory(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("material", "inventory");
		String errorMessage = "";
		
		try {
			this.rx570.validate();
			fail("Validation should have failed because inventory is null.");
		} catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
}
