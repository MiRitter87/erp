package backend.model.billOfMaterial;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.model.Currency;
import backend.model.material.Material;
import backend.model.material.UnitOfMeasurement;
import backend.tools.test.ValidationMessageProvider;

/**
 * Tests the BillOfMaterialItem model.
 * 
 * @author Michael
 */
public class BillOfMaterialItemTest {
	/**
	 * The BillOfMaterialItem under test.
	 */
	private BillOfMaterialItem billOfMaterialItem;
	
	/**
	 * The material that is part of the BillOfMaterial.
	 */
	private Material material;
	
	
	@BeforeEach
	/**
	 * Tasks to be performed before each test is run.
	 */
	private void setUp() {
		this.initMaterial();
		this.initBillOfMaterialItem();
	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	private void tearDown() {
		this.billOfMaterialItem = null;
		this.material = null;
	}
	
	
	/**
	 * Initializes the material that is part of the BillOfMaterial.
	 */
	private void initMaterial() {
		this.material = new Material();
		this.material.setName("Screw - 50mm");
		this.material.setDescription("A single screw. 50mm length.");
		this.material.setUnit(UnitOfMeasurement.ST);
		this.material.setPricePerUnit(BigDecimal.valueOf(Double.valueOf(0.02)));
		this.material.setCurrency(Currency.EUR);
		this.material.setInventory(Long.valueOf(10000));
	}
	
	
	/**
	 * Initializes the BillOfMaterialItem.
	 */
	private void initBillOfMaterialItem() {
		this.billOfMaterialItem = new BillOfMaterialItem();
		this.billOfMaterialItem.setId(1);
		this.billOfMaterialItem.setMaterial(this.material);
		this.billOfMaterialItem.setQuantity(1000);
	}
	
	
	@Test
	/**
	 * Tests validation of a valid BillOfMaterialItem.
	 */
	public void testValidationSuccess() {
		try {
			this.billOfMaterialItem.validate();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests validation of a BillOfMaterialItem whose ID is not given.
	 */
	public void testIdNotGiven() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.billOfMaterialItem.setId(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("billOfMaterialItem", "id");
		String errorMessage = "";
		
		try {
			this.billOfMaterialItem.validate();
			fail("Validation should have failed because ID is null.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of a BillOfMaterialItem whose ID is too low.
	 */
	public void testIdTooLow() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.billOfMaterialItem.setId(0);
		
		String expectedErrorMessage = messageProvider.getMinValidationMessage("billOfMaterialItem", "id", "1");
		String errorMessage = "";
		
		try {
			this.billOfMaterialItem.validate();
			fail("Validation should have failed because ID is too low.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of a BillOfMaterialItem whose material is not given.
	 */
	public void testMaterialNotGiven() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.billOfMaterialItem.setMaterial(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("billOfMaterialItem", "material");
		String errorMessage = "";
		
		try {
			this.billOfMaterialItem.validate();
			fail("Validation should have failed because material is null.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of a BillOfMaterialItem whose quantity is not given.
	 */
	public void testQuantityNotGiven() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.billOfMaterialItem.setQuantity(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("billOfMaterialItem", "quantity");
		String errorMessage = "";
		
		try {
			this.billOfMaterialItem.validate();
			fail("Validation should have failed because quantity is null.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of a BillOfMaterialItem whose quantity is too low.
	 */
	public void testQuantityTooLow() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.billOfMaterialItem.setQuantity(0);
		
		String expectedErrorMessage = messageProvider.getMinValidationMessage("billOfMaterialItem", "quantity", "1");
		String errorMessage = "";
		
		try {
			this.billOfMaterialItem.validate();
			fail("Validation should have failed because quantity is too low.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
}
