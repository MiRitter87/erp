package backend.webservice.common;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.dao.DAOManager;
import backend.dao.MaterialDao;
import backend.dao.ProductionOrderDao;
import backend.model.Currency;
import backend.model.material.Material;
import backend.model.material.UnitOfMeasurement;
import backend.model.productionOrder.ProductionOrder;
import backend.model.productionOrder.ProductionOrderItem;
import backend.model.productionOrder.ProductionOrderStatus;
import backend.model.webservice.WebServiceResult;
import backend.tools.WebServiceTools;

/**
 * Tests the production order service.
 * 
 * @author Michael
 */
public class ProductionOrderServiceTest {
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources = ResourceBundle.getBundle("backend");
	
	/**
	 * DAO to access production order data.
	 */
	private static ProductionOrderDao orderDAO;
	
	/**
	 * DAO to access material data.
	 */
	private static MaterialDao materialDAO;
	
	/**
	 * The first production order under test.
	 */
	private ProductionOrder order1;
	
	/**
	 * The second production order under test.
	 */
	private ProductionOrder order2;
	
	/**
	 * The item of the first order.
	 */
	private ProductionOrderItem orderItem11;
	
	/**
	 * The first item of the second order.
	 */
	private ProductionOrderItem orderItem21;
	
	/**
	 * The second item of the second order.
	 */
	private ProductionOrderItem orderItem22;
	
	/**
	 * The first material that is being produced.
	 */
	private Material rx570;
	
	/**
	 * The second material that is being produced.
	 */
	private Material g4560;
	
	
	@BeforeAll
	/**
	 * Tasks to be performed once at startup of test class.
	 */
	public static void setUpClass() {
		materialDAO = DAOManager.getInstance().getMaterialDAO();
		orderDAO = DAOManager.getInstance().getProductionOrderDAO();
	}
	
	
	@AfterAll
	/**
	 * Tasks to be performed once at end of test class.
	 */
	public static void tearDownClass() {
		try {
			DAOManager.getInstance().close();
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	
	@BeforeEach
	/**
	 * Tasks to be performed before each test is run.
	 */
	private void setUp() {
		this.createDummyMaterials();
		this.createDummyOrders();
	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	private void tearDown() {
		this.deleteDummyOrders();
		this.deleteDummyMaterials();
	}
	
	
	/**
	 * Initializes the database with a dummy material.
	 */
	private void createDummyMaterials() {
		this.rx570 = new Material();
		this.rx570.setName("AMD RX570");
		this.rx570.setDescription("Graphic card released in april 2017. It uses the Polaris 20 XL chip.");
		this.rx570.setUnit(UnitOfMeasurement.ST);
		this.rx570.setPricePerUnit(BigDecimal.valueOf(Double.valueOf(189.99)));
		this.rx570.setCurrency(Currency.EUR);
		this.rx570.setInventory(Long.valueOf(10));
		
		this.g4560 = new Material();
		this.g4560.setName("Pentium G4560");
		this.g4560.setDescription("Desktop processor that has 2 cores / 4 threads. Released in january 2017. Has 54W TDP.");
		this.g4560.setUnit(UnitOfMeasurement.ST);
		this.g4560.setPricePerUnit(BigDecimal.valueOf(Double.valueOf(54.99)));
		this.g4560.setCurrency(Currency.EUR);
		this.g4560.setInventory(Long.valueOf(25));
		
		try {
			materialDAO.insertMaterial(this.rx570);
			materialDAO.insertMaterial(this.g4560);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Initializes the database with dummy production orders.
	 */
	private void createDummyOrders() {
		GregorianCalendar tomorrow = new GregorianCalendar();
		tomorrow.add(GregorianCalendar.DAY_OF_MONTH, 1);
		
		this.orderItem11 = new ProductionOrderItem();
		this.orderItem11.setId(1);
		this.orderItem11.setMaterial(this.rx570);
		this.orderItem11.setQuantity(Long.valueOf(1));
		
		this.order1 = new ProductionOrder();
		this.order1.setPlannedExecutionDate(tomorrow.getTime());
		this.order1.setStatus(ProductionOrderStatus.OPEN);
		this.order1.addItem(this.orderItem11);
		
		this.orderItem21 = new ProductionOrderItem();
		this.orderItem21.setId(1);
		this.orderItem21.setMaterial(this.rx570);
		this.orderItem21.setQuantity(Long.valueOf(2));
		
		this.orderItem22 = new ProductionOrderItem();
		this.orderItem22.setId(2);
		this.orderItem22.setMaterial(this.g4560);
		this.orderItem22.setQuantity(Long.valueOf(1));
		
		this.order2 = new ProductionOrder();
		this.order2.setPlannedExecutionDate(tomorrow.getTime());
		this.order2.setStatus(ProductionOrderStatus.IN_PROCESS);
		this.order2.addItem(this.orderItem21);
		this.order2.addItem(this.orderItem22);
		
		try {
			orderDAO.insertProductionOrder(this.order1);
			orderDAO.insertProductionOrder(this.order2);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Deletes the dummy production orders from the database.
	 */
	private void deleteDummyOrders() {
		try {
			orderDAO.deleteProductionOrder(this.order2);
			orderDAO.deleteProductionOrder(this.order1);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Deletes the dummy materials from the database.
	 */
	private void deleteDummyMaterials() {
		try {
			materialDAO.deleteMaterial(this.g4560);
			materialDAO.deleteMaterial(this.rx570);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests the retrieval of a production order.
	 */
	public void testGetSalesOrder() {
		WebServiceResult getProductionOrderResult;
		ProductionOrder productionOrder;
		ProductionOrderItem productionOrderItem;
		
		//Get the production order.
		ProductionOrderService service = new ProductionOrderService();
		getProductionOrderResult = service.getProductionOrder(this.order1.getId());
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(getProductionOrderResult) == false);
		
		//Assure that a production order is returned
		assertTrue(getProductionOrderResult.getData() instanceof ProductionOrder);
		
		productionOrder = (ProductionOrder) getProductionOrderResult.getData();
		
		//Check each attribute of the production order
		assertEquals(this.order1.getId(), productionOrder.getId());
		assertEquals(this.order1.getOrderDate().getTime(), productionOrder.getOrderDate().getTime());
		assertEquals(this.order1.getPlannedExecutionDate().getTime(), productionOrder.getPlannedExecutionDate().getTime());
		assertEquals(this.order1.getExecutionDate(), productionOrder.getExecutionDate());
		assertEquals(this.order1.getStatus(), productionOrder.getStatus());
		
		if(this.order1.getExecutionDate() != null && productionOrder.getExecutionDate() != null)
			assertEquals(this.order1.getExecutionDate().getTime(), productionOrder.getExecutionDate().getTime());
		
		//The returned production order should have one item.
		assertEquals(this.order1.getItems().size(), productionOrder.getItems().size());
		
		productionOrderItem = productionOrder.getItems().get(0);
		
		//Check the attributes of the production order item
		assertEquals( this.orderItem11.getId(), productionOrderItem.getId());
		assertEquals( this.orderItem11.getMaterial(), productionOrderItem.getMaterial());
		assertEquals( this.orderItem11.getQuantity(), productionOrderItem.getQuantity());
	}
}
