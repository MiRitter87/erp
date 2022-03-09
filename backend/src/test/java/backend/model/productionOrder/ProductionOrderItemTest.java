package backend.model.productionOrder;

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
 * Tests the production order item model.
 * 
 * @author Michael
 */
public class ProductionOrderItemTest {
	/**
	 * The item of the order.
	 */
	private ProductionOrderItem orderItem;
	
	/**
	 * The material that is being produced.
	 */
	private Material material;
	
	
	@BeforeEach
	/**
	 * Tasks to be performed before each test is run.
	 */
	private void setUp() {
		this.initMaterial();
		this.initProductionOrderItem();	
	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	private void tearDown() {
		this.orderItem = null;
		this.material = null;
	}
	
	
	/**
	 * Initializes the material that is being produced.
	 */
	private void initMaterial() {
		this.material = new Material();
		this.material.setName("AMD RX570");
		this.material.setDescription("Graphic card released in april 2017. It uses the Polaris 20 XL chip.");
		this.material.setUnit(UnitOfMeasurement.ST);
		this.material.setPricePerUnit(BigDecimal.valueOf(Double.valueOf(189.99)));
		this.material.setCurrency(Currency.EUR);
		this.material.setInventory(Long.valueOf(10));
	}
	
	
	/**
	 * Initializes the production order item.
	 */
	private void initProductionOrderItem() {
		this.orderItem = new ProductionOrderItem();
		this.orderItem.setId(1);
		this.orderItem.setMaterial(this.material);
		this.orderItem.setQuantity(Long.valueOf(1));
	}
	
	
	@Test
	/**
	 * Tests validation of a valid production order item.
	 */
	public void testValidationSuccess() {
		try {
			this.orderItem.validate();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests validation of a production order item whose ID is not given.
	 */
	public void testIdNotGiven() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.orderItem.setId(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("productionOrderItem", "id");
		String errorMessage = "";
		
		try {
			this.orderItem.validate();
			fail("Validation should have failed because ID is null.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of a production order item whose ID is too low.
	 */
	public void testIdTooLow() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.orderItem.setId(0);
		
		String expectedErrorMessage = messageProvider.getMinValidationMessage("productionOrderItem", "id", "1");
		String errorMessage = "";
		
		try {
			this.orderItem.validate();
			fail("Validation should have failed because ID is too low.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of a production order item whose material is not given.
	 */
	public void testMaterialNotGiven() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.orderItem.setMaterial(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("productionOrderItem", "material");
		String errorMessage = "";
		
		try {
			this.orderItem.validate();
			fail("Validation should have failed because material is null.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of a production order item whose quantity is not given.
	 */
	public void testQuantityNotGiven() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.orderItem.setQuantity(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("productionOrderItem", "quantity");
		String errorMessage = "";
		
		try {
			this.orderItem.validate();
			fail("Validation should have failed because quantity is null.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of a production order item whose quantity is too low.
	 */
	public void testQuantityTooLow() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.orderItem.setQuantity(Long.valueOf(0));
		
		String expectedErrorMessage = messageProvider.getMinValidationMessage("productionOrderItem", "quantity", "1");
		String errorMessage = "";
		
		try {
			this.orderItem.validate();
			fail("Validation should have failed because quantity is too low.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
}
