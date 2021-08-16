package backend.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.exception.NoItemsException;
import backend.tools.test.ValidationMessageProvider;

/**
 * Tests the purchase order model.
 * 
 * @author Michael
 */
public class PurchaseOrderTest {
	/**
	 * The purchase order under test.
	 */
	private PurchaseOrder order;
	
	/**
	 * The item of the order.
	 */
	private PurchaseOrderItem orderItem;
	
	/**
	 * The vendor of the ordered material.
	 */
	private BusinessPartner vendor;
	
	/**
	 * The material that is being ordered.
	 */
	private Material material;
	
	
	@BeforeEach
	/**
	 * Tasks to be performed before each test is run.
	 */
	private void setUp() {
		this.initVendor();
		this.initMaterial();
		this.initPurchaseOrder();
		this.initPurchaseOrderItem();
		
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
		this.vendor = null;
		this.material = null;
	}
	
	
	/**
	 * Initializes the vendor.
	 */
	private void initVendor() {
		this.vendor = new BusinessPartner();
		this.vendor.setId(1);
		this.vendor.setCompanyName("Amalgamated Moose Pasture");
		this.vendor.setFirstName("John");
		this.vendor.setLastName("Doe");
		this.vendor.setStreetName("Main Street");
		this.vendor.setHouseNumber("1a");
		this.vendor.setZipCode("12345");
		this.vendor.setCityName("Moose City");
		this.vendor.setPhoneNumber("+1 123-456-7890");
	}
	
	
	/**
	 * Initializes the material that is being ordered.
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
	 * Initializes the purchase order.
	 */
	private void initPurchaseOrder() {
		this.order = new PurchaseOrder();
		this.order.setId(1);
		this.order.setVendor(this.vendor);
	}
	
	
	/**
	 * Initializes the purchase order item.
	 */
	private void initPurchaseOrderItem() {
		this.orderItem = new PurchaseOrderItem();
		this.orderItem.setId(1);
		this.orderItem.setMaterial(this.material);
		this.orderItem.setQuantity(Long.valueOf(2));
	}
	
	
	@Test
	/**
	 * Tests validation of a valid purchase order.
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
	 * Tests validation of a purchase order that has no items given.
	 */
	public void testNoItemsGiven() {
		this.order.getItems().clear();
		
		try {
			this.order.validate();
			fail("Validation should have failed because the purchase order has no items defined.");
		} catch (NoItemsException expected) {
			//All is well.
		} catch (Exception e) {
			fail("No general exception should have occurred. Just the NoItemsException.");
		}
	}
	
	
	@Test
	/**
	 * Tests validation of the purchase order whose ID is too low.
	 */
	public void testIdTooLow() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.order.setId(0);
		
		String expectedErrorMessage = messageProvider.getMinValidationMessage("purchaseOrder", "id", "1");
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
	 * Tests validation of a purchase order that has no vendor defined.
	 */
	public void testNoVendorDefined() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();	
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("purchaseOrder", "vendor");
		String actualErrorMessage = "";

		this.order.setVendor(null);
		
		try {
			this.order.validate();
			fail("Validation should have failed because no vendor is defined.");
		} catch (Exception expected) {
			actualErrorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
	
	
	@Test
	/**
	 * Tests the validation of a purchase order whose set of status is null.
	 */
	public void testTypesIsNull() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.order.setStatus(null);
		
		String expectedErrorMessage = messageProvider.getNotEmptyValidationMessage("purchaseOrder", "status");
		String errorMessage = "";
		
		try {
			this.order.validate();
			fail("Validation should have failed because status is null.");
		}
		catch(Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests if the status OPEN is set initially after the purchase order has been created and has not been processed yet.
	 */
	public void testStatusOpenSetInitially() {
		//Verify that status OPEN is set initially.
		assertTrue(this.order.getStatus().contains(PurchaseOrderStatus.OPEN));
		//Verify that status FINISHED is not set initialy.
		assertFalse(this.order.getStatus().contains(PurchaseOrderStatus.FINISHED));
	}
	
	
	@Test
	/**
	 * Tests if the status FINISHED is set automatically if all required status are set.
	 */
	public void testStatusFinishedSet() {
		//Set all status to active that are required for the purchase order to be regarded as finished.
		this.order.setStatus(PurchaseOrderStatus.GOODS_RECEIPT, true, true);
		this.order.setStatus(PurchaseOrderStatus.INVOICE_RECEIPT, true, true);
		this.order.setStatus(PurchaseOrderStatus.INVOICE_SETTLED, true, true);
		
		//Verify that status OPEN is not set anymore.
		assertFalse(this.order.getStatus().contains(PurchaseOrderStatus.OPEN));
		//Verify that status FINISHED has been set.
		assertTrue(this.order.getStatus().contains(PurchaseOrderStatus.FINISHED));
	}
}
