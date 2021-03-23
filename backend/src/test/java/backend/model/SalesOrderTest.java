package backend.model;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.exception.NoItemsException;
import backend.tools.test.ValidationMessageProvider;

/**
 * Tests the sales order model.
 * 
 * @author Michael
 */
public class SalesOrderTest {
	/**
	 * The sales order under test.
	 */
	private SalesOrder order;
	
	/**
	 * The item of the order.
	 */
	private SalesOrderItem orderItem;
	
	/**
	 * The business partner ordering the materials.
	 */
	private BusinessPartner businessPartner;
	
	/**
	 * The material that is being ordered.
	 */
	private Material material;
	
	
	@BeforeEach
	/**
	 * Tasks to be performed before each test is run.
	 */
	private void setUp() {
		this.initBusinessPartner();
		this.initMaterial();
		this.initSalesOrder();
		this.initSalesOrderItem();
		
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
		this.businessPartner = null;
		this.material = null;
	}
	
	
	/**
	 * Initializes the business partner.
	 */
	private void initBusinessPartner() {
		this.businessPartner = new BusinessPartner();
		this.businessPartner.setId(1);
		this.businessPartner.setCompanyName("Amalgamated Moose Pasture");
		this.businessPartner.setFirstName("John");
		this.businessPartner.setLastName("Doe");
		this.businessPartner.setStreetName("Main Street");
		this.businessPartner.setHouseNumber("1a");
		this.businessPartner.setZipCode("12345");
		this.businessPartner.setCityName("Moose City");
		this.businessPartner.setPhoneNumber("+1 123-456-7890");
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
	 * Initializes the sales order.
	 */
	private void initSalesOrder() {
		this.order = new SalesOrder();
		this.order.setId(1);
		this.order.setSoldToParty(this.businessPartner);
		this.order.setShipToParty(this.businessPartner);
		this.order.setBillToParty(this.businessPartner);
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
	 * Tests validation of a valid sales order.
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
	 * Tests validation of a sales order that has no items given.
	 */
	public void testNoItemsGiven() {
		this.order.getItems().clear();
		
		try {
			this.order.validate();
			fail("Validation should have failed because sales order has no items defined.");
		} catch (NoItemsException expected) {
			//All is well.
		} catch (Exception e) {
			fail("No general exception should have occurred. Just the NoItemsException.");
		}
	}
	
	
	@Test
	/**
	 * Tests validation of a sales order that has no sold-to party defined.
	 */
	public void testNoSoldToPartyDefined() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();	
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("salesOrder", "soldToParty");
		String actualErrorMessage = "";

		this.order.setSoldToParty(null);
		
		try {
			this.order.validate();
			fail("Validation should have failed because no sold-to party is defined.");
		} catch (Exception expected) {
			actualErrorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of a sales order that has no ship-to party defined.
	 */
	public void testNoShipToPartyDefined() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();	
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("salesOrder", "shipToParty");
		String actualErrorMessage = "";
		
		this.order.setShipToParty(null);
		
		try {
			this.order.validate();
			fail("Validation should have failed because no ship-to party is defined.");
		} catch (Exception expected) {
			actualErrorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of a sales order that has no bill-to party defined.
	 */
	public void testNoBillToPartyDefined() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();	
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("salesOrder", "billToParty");
		String actualErrorMessage = "";
		
		this.order.setBillToParty(null);
		
		try {
			this.order.validate();
			fail("Validation should have failed because no bill-to party is defined.");
		} catch (Exception expected) {
			actualErrorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
}
