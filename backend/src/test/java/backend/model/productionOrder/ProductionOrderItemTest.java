package backend.model.productionOrder;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import backend.model.Currency;
import backend.model.material.Material;
import backend.model.material.UnitOfMeasurement;

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
}
