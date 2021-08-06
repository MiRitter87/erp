package backend.model;

import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

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
		this.order.setStatus(PurchaseOrderStatus.OPEN);
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
}
