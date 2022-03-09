package backend.model.billOfMaterial;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.exception.DuplicateIdentifierException;
import backend.exception.NoItemsException;
import backend.model.Currency;
import backend.model.material.Material;
import backend.model.material.UnitOfMeasurement;
import backend.tools.test.ValidationMessageProvider;

/**
 * Tests the BillOfMaterial model.
 * 
 * @author Michael
 */
public class BillOfMaterialTest {
	/**
	 * The BillOfMaterial under test.
	 */
	private BillOfMaterial billOfMaterial;
	
	/**
	 * The item of the BillOfMaterial.
	 */
	private BillOfMaterialItem billOfMaterialItem;
	
	/**
	 * The parent material as defined in the BillOfMaterial.
	 */
	private Material parentMaterial;
	
	/**
	 * The child material of which the parent material is made of.
	 */
	private Material childMaterial;
	
	
	@BeforeEach
	/**
	 * Tasks to be performed before each test is run.
	 */
	private void setUp() {
		this.initMaterials();
		this.initBillOfMaterial();
	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	private void tearDown() {
		this.billOfMaterialItem = null;
		this.billOfMaterial = null;
		this.childMaterial = null;
		this.parentMaterial = null;
	}
	
	
	/**
	 * Initializes the materials.
	 */
	private void initMaterials() {
		this.parentMaterial = new Material();
		this.parentMaterial.setName("Package of Screws - 50mm");
		this.parentMaterial.setDescription("A package of screw. 50mm length. 1000 pieces.");
		this.parentMaterial.setUnit(UnitOfMeasurement.ST);
		this.parentMaterial.setPricePerUnit(BigDecimal.valueOf(Double.valueOf(14.99)));
		this.parentMaterial.setCurrency(Currency.EUR);
		this.parentMaterial.setInventory(Long.valueOf(10));
		
		this.childMaterial = new Material();
		this.childMaterial.setName("Screw - 50mm");
		this.childMaterial.setDescription("A single screw. 50mm length.");
		this.childMaterial.setUnit(UnitOfMeasurement.ST);
		this.childMaterial.setPricePerUnit(BigDecimal.valueOf(Double.valueOf(0.02)));
		this.childMaterial.setCurrency(Currency.EUR);
		this.childMaterial.setInventory(Long.valueOf(10000));
	}
	
	
	/**
	 * Initializes the BillOfMaterial.
	 */
	private void initBillOfMaterial() {
		this.billOfMaterialItem = new BillOfMaterialItem();
		this.billOfMaterialItem.setId(1);
		this.billOfMaterialItem.setMaterial(this.childMaterial);
		this.billOfMaterialItem.setQuantity(1000);
		
		this.billOfMaterial = new BillOfMaterial();
		this.billOfMaterial.setId(1);
		this.billOfMaterial.setName("Package of Screws - 50mm");
		this.billOfMaterial.setDescription("A package of screw. 50mm length. 1000 pieces.");
		this.billOfMaterial.setMaterial(this.parentMaterial);
		this.billOfMaterial.addItem(this.billOfMaterialItem);
	}
	
	
	@Test
	/**
	 * Tests validation of a valid BillOfMaterial.
	 */
	public void testValidationSuccess() {
		try {
			this.billOfMaterial.validate();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests validation of a BillOfMaterial that has no items given.
	 */
	public void testNoItemsGiven() {
		this.billOfMaterial.getItems().clear();
		
		try {
			this.billOfMaterial.validate();
			fail("Validation should have failed because the BillOfMaterial has no items defined.");
		} catch (NoItemsException expected) {
			//All is well.
		} catch (Exception e) {
			fail("No general exception should have occurred. Just the NoItemsException.");
		}
	}
	
	
	@Test
	/**
	 * Tests validation of the BillOfMaterial whose ID is too low.
	 */
	public void testIdTooLow() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.billOfMaterial.setId(0);
		
		String expectedErrorMessage = messageProvider.getMinValidationMessage("billOfMaterial", "id", "1");
		String errorMessage = "";
		
		try {
			this.billOfMaterial.validate();
			fail("Validation should have failed because ID is too low.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of a BillOfMAterial whose name is not given.
	 */
	public void testNameNotGiven() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.billOfMaterial.setName("");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("billOfMaterial", "name", 
				String.valueOf(this.billOfMaterial.getName().length()), "1", "50");
		String errorMessage = "";
		
		try {
			this.billOfMaterial.validate();
			fail("Validation should have failed because name is not given.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of a BillOfMaterial whose name is too long.
	 */
	public void testNameTooLong() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.billOfMaterial.setName("Dies ist der Name einer Stückliste der zu lang ist.");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("billOfMaterial", "name", 
				String.valueOf(this.billOfMaterial.getName().length()), "1", "50");
		String errorMessage = "";
		
		try {
			this.billOfMaterial.validate();
			fail("Validation should have failed because name is too long.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of a BillOfMaterial whose name is null.
	 */
	public void testNameIsNull() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.billOfMaterial.setName(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("billOfMaterial", "name");
		String errorMessage = "";
		
		try {
			this.billOfMaterial.validate();
			fail("Validation should have failed because name is null.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of a BillOfMaterial whose description is not given.
	 */
	public void testDescriptionNotGiven() {
		this.billOfMaterial.setDescription("");
		
		try {
			this.billOfMaterial.validate();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests validation of a BillOfMaterial whose description is null.
	 */
	public void testDescriptionIsNull() {
		this.billOfMaterial.setDescription(null);
		
		try {
			this.billOfMaterial.validate();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests validation of a BillOfMaterial whose description is too long.
	 */
	public void testDescriptionTooLong() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		
		this.billOfMaterial.setDescription("Das ist ein Beschreibungstext. Das ist ein Beschreibungstext. Das ist ein Beschreibungstext. "
				+ "Das ist ein Beschreibungstext. Das ist ein Beschreibungstext. Das ist ein Beschreibungstext. "
				+ "Das ist ein Beschreibungstext. Das ist ein Beschreibungstext. Das");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("billOfMaterial", "description", 
				String.valueOf(this.billOfMaterial.getDescription().length()), "0", "250");
		String errorMessage = "";
		
		try {
			this.billOfMaterial.validate();
			fail("Validation should have failed because description is too long.");
		} catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of a BillOfMaterial that has no material defined.
	 */
	public void testNoMaterialDefined() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();	
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("billOfMaterial", "material");
		String actualErrorMessage = "";

		this.billOfMaterial.setMaterial(null);
		
		try {
			this.billOfMaterial.validate();
			fail("Validation should have failed because no material is defined.");
		} catch (Exception expected) {
			actualErrorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of a BillOfMaterial having multiple items with the same ID.
	 */
	public void testDuplicateItemIds() {
		BillOfMaterialItem secondItem;
		
		secondItem = new BillOfMaterialItem();
		secondItem.setId(1);
		secondItem.setMaterial(this.childMaterial);
		secondItem.setQuantity(2000);
		
		this.billOfMaterial.addItem(secondItem);
		
		try {
			this.billOfMaterial.validate();
			fail("Validation should have failed because the BillOfMaterial has multiple items with the same ID defined.");
		} catch (DuplicateIdentifierException expected) {
			//All is well.
		} catch (Exception e) {
			fail("No general exception should have occurred. Just the DuplicateIdentifierException.");
		}
	}
	
	
	@Test
	/**
	 * Tests validation of a BillOfMaterial having an invalid item defined.
	 */
	public void testItemInvalid() {
		this.billOfMaterialItem.setId(0);
		
		try {
			this.billOfMaterial.validate();
			fail("Validation should have failed because the item ID is invalid.");
		} catch (Exception e) {
			//All is well.
		}
	}
}
