package backend.model.productionOrder;

import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import backend.model.Currency;
import backend.model.material.Material;
import backend.model.material.UnitOfMeasurement;

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
}
