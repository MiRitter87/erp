package backend.model.productionOrder;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;

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
 * Tests the production order model.
 * 
 * @author Michael
 */
public class ProductionOrderTest {
	/**
	 * The production order under test.
	 */
	private ProductionOrder order;
	
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
		this.initProductionOrder();
		this.initProductionOrderItem();
		
		try {
			this.order.addItem(this.orderItem);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	private void tearDown() {
		this.order = null;
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
	 * Initializes the production order.
	 */
	private void initProductionOrder() {
		GregorianCalendar tomorrow = new GregorianCalendar();
		tomorrow.add(GregorianCalendar.DAY_OF_MONTH, 1);
		
		this.order = new ProductionOrder();
		this.order.setId(1);
		this.order.setOrderDate(new Date());
		this.order.setPlannedExecutionDate(tomorrow.getTime());
		this.order.setStatus(ProductionOrderStatus.OPEN);
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
	 * Tests validation of a valid production order.
	 */
	public void testValidationSuccess() {
		try {
			this.order.validate();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests validation of a production order that has no items given.
	 */
	public void testNoItemsGiven() {
		this.order.getItems().clear();
		
		try {
			this.order.validate();
			fail("Validation should have failed because production order has no items defined.");
		} catch (NoItemsException expected) {
			//All is well.
		} catch (Exception e) {
			fail("No general exception should have occurred. Just the NoItemsException.");
		}
	}
	
	
	@Test
	/**
	 * Tests validation of a production order which has items with duplicate IDs.
	 */
	public void testDuplicateItemId() {
		ProductionOrderItem newItem;
		
		//Add another item to the production order that has the same ID defined.
		newItem = new ProductionOrderItem();
		newItem.setId(this.orderItem.getId());
		newItem.setMaterial(this.material);
		newItem.setQuantity(Long.valueOf(1));
		this.order.addItem(newItem);
		
		try {
			this.order.validate();
			fail("Validation should have failed because multiple items have the same id defined.");
		} catch (DuplicateIdentifierException expected) {
			//All is well.
		} catch (Exception exception) {
			fail("No general exception should have occurred. Just the DuplicateIdentifierException.");
		}
	}
	
	
	@Test
	/**
	 * Tests validation of the production order whose ID is too low.
	 */
	public void testIdTooLow() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.order.setId(0);
		
		String expectedErrorMessage = messageProvider.getMinValidationMessage("productionOrder", "id", "1");
		String errorMessage = "";
		
		try {
			this.order.validate();
			fail("Validation should have failed because ID is too low.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of a production order that has no planned execution date defined.
	 */
	public void testNoPlannedExecutionDateDefined() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();	
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("productionOrder", "plannedExecutionDate");
		String actualErrorMessage = "";
		
		this.order.setPlannedExecutionDate(null);
		
		try {
			this.order.validate();
			fail("Validation should have failed because no planned execution date is defined.");
		} catch (Exception expected) {
			actualErrorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of a production order that has no status defined.
	 */
	public void testNoStatusDefined() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.order.setStatus(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("productionOrder", "status");
		String errorMessage = "";
		
		try {
			this.order.validate();
			fail("Validation should have failed because status is not set.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
}
