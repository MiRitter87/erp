package backend.model;

import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

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
	
	
	/**
	 * TODO Implement test cases:
	 * 
	 * Validate a valid sales order
	 * Validate a sales order that has no items given
	 * Validate a sales order that has no sold-to party defined
	 * Validate a sales order that has no ship-to party defined
	 * Validate a sales order that has no bill-to party defined
	 * 
	 * The following tests should be performed at order item level. Move to SalesOrderItemtest.java
	 * Set quantity of item: priceTotal should be updated automatically if material is set
	 * Set material of item: priceTotal should be updated automatically if quantity is set
	 */
}
