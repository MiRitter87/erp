package backend.model.salesOrder;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.exception.QuantityExceedsInventoryException;
import backend.model.Currency;
import backend.model.material.Material;
import backend.model.material.UnitOfMeasurement;
import backend.model.salesOrder.SalesOrderItem;
import backend.tools.test.ValidationMessageProvider;

/**
 * Tests the sales order item model.
 * 
 * @author Michael
 */
public class SalesOrderItemTest {
	/**
	 * The sales order item under test.
	 */
	private SalesOrderItem orderItem;
	
	/**
	 * The material that is being ordered.
	 */
	private Material material;
	
	
	@BeforeEach
	/**
	 * Tasks to be performed before each test is run.
	 */
	private void setUp() {
		this.initMaterial();
		this.initSalesOrderItem();	
	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	private void tearDown() {
		this.orderItem = null;
	}
	
	
	/**
	 * Initializes the material that is being ordered.
	 */
	private void initMaterial() {
		this.material = new Material();
		this.material.setId(1);
		this.material.setName("AMD RX570");
		this.material.setDescription("Graphic card released in april 2017. It uses the Polaris 20 XL chip.");
		this.material.setUnit(UnitOfMeasurement.ST);
		this.material.setPricePerUnit(BigDecimal.valueOf(Double.valueOf(189.99)));
		this.material.setCurrency(Currency.EUR);
		this.material.setInventory(Long.valueOf(10));
	}
	
	
	/**
	 * Initializes the sales order item.
	 */
	private void initSalesOrderItem() {
		this.orderItem = new SalesOrderItem();
		this.orderItem.setId(1);
		this.orderItem.setMaterial(this.material);
		this.orderItem.setQuantity(Long.valueOf(2));
	}
	
	
	@Test
	/**
	 * Tests validation of a valid sales order item.
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
	 * Tests validation of a sales order item whose ID is not given.
	 */
	public void testIdNotGiven() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.orderItem.setId(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("salesOrderItem", "id");
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
	 * Tests validation of a sales order item whose ID is too low.
	 */
	public void testIdTooLow() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.orderItem.setId(0);
		
		String expectedErrorMessage = messageProvider.getMinValidationMessage("salesOrderItem", "id", "1");
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
	 * Tests validation of a sales order item whose material is not given.
	 */
	public void testMaterialNotGiven() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.orderItem.setMaterial(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("salesOrderItem", "material");
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
	 * Tests validation of a sales order item whose quantity is not given.
	 */
	public void testQuantityNotGiven() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.orderItem.setQuantity(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("salesOrderItem", "quantity");
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
	 * Tests validation of a sales order item whose quantity is too low.
	 */
	public void testQuantityTooLow() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.orderItem.setQuantity(Long.valueOf(0));
		
		String expectedErrorMessage = messageProvider.getMinValidationMessage("salesOrderItem", "quantity", "1");
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
	
	
	@Test
	/**
	 * Tests validation of a sales order item whose quantity has changed. The price should be updated automatically.
	 */
	public void testPriceUpdateOnQuantityChange() {
		this.orderItem.setQuantity(Long.valueOf(3));
		BigDecimal expectedPrice = this.material.getPricePerUnit().multiply(new BigDecimal(this.orderItem.getQuantity()));
		
		assertEquals(expectedPrice, this.orderItem.getPriceTotal());
	}
	
	
	@Test
	/**
	 * Tests validation of a sales order item whose material has changed. The price should be updated automatically.
	 */
	public void testPriceUpdateOnMaterialChange() {		
		Material otherMaterial = new Material();
		BigDecimal expectedPrice;
		
		otherMaterial.setId(2);
		otherMaterial.setName("Water");
		otherMaterial.setDescription("Tap water.");
		otherMaterial.setUnit(UnitOfMeasurement.L);
		otherMaterial.setPricePerUnit(BigDecimal.valueOf(Double.valueOf(0.02)));
		otherMaterial.setCurrency(Currency.EUR);
		otherMaterial.setInventory(Long.valueOf(1000));
		
		expectedPrice = otherMaterial.getPricePerUnit().multiply(new BigDecimal(this.orderItem.getQuantity()));
		this.orderItem.setMaterial(otherMaterial);
		
		assertEquals(expectedPrice, this.orderItem.getPriceTotal());
	}
	
	
	@Test
	/**
	 * Tests validation of a sales order item whose ordered quantity exceeds the inventory of the given material.
	 */
	public void testOrderQuantityExceedsInventory() {
		this.orderItem.setQuantity(this.orderItem.getMaterial().getInventory() + 1);
		
		try {
			this.orderItem.validate();
			fail("Validation should have failed because the ordered quantity exceeds the inventory.");
		} catch (QuantityExceedsInventoryException expected) {
			//All is well.
		}
		catch (Exception e) {
			fail("No general exception should have occurred. Just the QuantityExceedsInventoryException.");
		}
	}
}
