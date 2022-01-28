package backend.webservice.common;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
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
import backend.model.productionOrder.ProductionOrderArray;
import backend.model.productionOrder.ProductionOrderItem;
import backend.model.productionOrder.ProductionOrderItemWS;
import backend.model.productionOrder.ProductionOrderStatus;
import backend.model.productionOrder.ProductionOrderWS;
import backend.model.webservice.WebServiceMessageType;
import backend.model.webservice.WebServiceResult;
import backend.tools.WebServiceTools;
import backend.tools.test.ValidationMessageProvider;

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
	
	/**
	 * A processor chip that is used as raw material for end-products.
	 */
	private Material processorChip;
	
	/**
	 * A memory chip that is used as raw material for end-products.
	 */
	private Material memoryChip;
	
	/**
	 * A display interface for graphic cards that is used in the production process.
	 */
	private Material displayInterface;
	
	
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
		
		this.processorChip = new Material();
		this.processorChip.setName("Multi purpose processor chip");
		this.processorChip.setDescription("A processing unit that can be used by GPUs as well as CPUs");
		this.processorChip.setUnit(UnitOfMeasurement.ST);
		this.processorChip.setPricePerUnit(BigDecimal.valueOf(Double.valueOf(12.89)));
		this.processorChip.setCurrency(Currency.EUR);
		this.processorChip.setInventory(Long.valueOf(150));
		
		this.memoryChip = new Material();
		this.memoryChip.setName("Multi purpose memory chip");
		this.memoryChip.setDescription("A memory chip that can be used by GPUs as well as CPUs");
		this.memoryChip.setUnit(UnitOfMeasurement.ST);
		this.memoryChip.setPricePerUnit(BigDecimal.valueOf(Double.valueOf(3.17)));
		this.memoryChip.setCurrency(Currency.EUR);
		this.memoryChip.setInventory(Long.valueOf(450));
		
		this.displayInterface = new Material();
		this.displayInterface.setName("A display interface device");
		this.displayInterface.setDescription("Hardware device to connect external devices like a monitor to the graphic adapter.");
		this.displayInterface.setUnit(UnitOfMeasurement.ST);
		this.displayInterface.setPricePerUnit(BigDecimal.valueOf(Double.valueOf(7.89)));
		this.displayInterface.setCurrency(Currency.EUR);
		this.displayInterface.setInventory(Long.valueOf(180));
		
		try {
			materialDAO.insertMaterial(this.rx570);
			materialDAO.insertMaterial(this.g4560);
			materialDAO.insertMaterial(this.processorChip);
			materialDAO.insertMaterial(this.memoryChip);
			materialDAO.insertMaterial(this.displayInterface);
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
			materialDAO.deleteMaterial(this.displayInterface);
			materialDAO.deleteMaterial(this.memoryChip);
			materialDAO.deleteMaterial(this.processorChip);
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
	public void testGetProductionOrder() {
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
	
	
	@Test
	/**
	 * Tests the retrieval of a production order with an unknown ID.
	 */
	public void testGetUnknownProductionOrder() {
		WebServiceResult getProductionOrderResult;
		ProductionOrderService service = new ProductionOrderService();
		String actualErrorMessage, expectedErrorMessage;
		Integer unknownId = 0;	//Production order IDs at database start at 1.
		
		//Try to get a production order with an unknown ID.
		getProductionOrderResult = service.getProductionOrder(unknownId);
		
		//There should be a return message of type E.
		assertTrue(getProductionOrderResult.getMessages().size() == 1);
		assertTrue(getProductionOrderResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//A proper message should be provided.
		expectedErrorMessage = MessageFormat.format(this.resources.getString("productionOrder.notFound"), unknownId);
		actualErrorMessage = getProductionOrderResult.getMessages().get(0).getText();
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
	
	
	@Test
	/**
	 * Tests the retrieval of all production orders.
	 */
	public void testGetAllProductionOrders() {
		WebServiceResult getProductionOrdersResult;
		ProductionOrderArray productionOrders;
		ProductionOrder productionOrder;
		ProductionOrderItem productionOrderItem;
		ProductionOrderService service = new ProductionOrderService();
		
		//Get the production orders.
		getProductionOrdersResult = service.getProductionOrders();
		productionOrders = (ProductionOrderArray) getProductionOrdersResult.getData();
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(getProductionOrdersResult) == false);
		
		//Check if two production orders are returned.
		assertTrue(productionOrders.getProductionOrders().size() == 2);
		
		//Check both production orders by each attribute
		//First order.
		productionOrder = productionOrders.getProductionOrders().get(0);
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
		
		//Second order.
		productionOrder = productionOrders.getProductionOrders().get(1);
		assertEquals(this.order2.getId(), productionOrder.getId());
		assertEquals(this.order2.getOrderDate().getTime(), productionOrder.getOrderDate().getTime());
		assertEquals(this.order2.getPlannedExecutionDate().getTime(), productionOrder.getPlannedExecutionDate().getTime());
		assertEquals(this.order2.getExecutionDate(), productionOrder.getExecutionDate());
		assertEquals(this.order2.getStatus(), productionOrder.getStatus());
		
		if(this.order2.getExecutionDate() != null && productionOrder.getExecutionDate() != null)
			assertEquals(this.order2.getExecutionDate().getTime(), productionOrder.getExecutionDate().getTime());
		
		//The returned production order should have two items.
		assertEquals(this.order2.getItems().size(), productionOrder.getItems().size());
		
		productionOrderItem = productionOrder.getItems().get(0);
		
		//Check the attributes of the production order item
		assertEquals( this.orderItem21.getId(), productionOrderItem.getId());
		assertEquals( this.orderItem21.getMaterial(), productionOrderItem.getMaterial());
		assertEquals( this.orderItem21.getQuantity(), productionOrderItem.getQuantity());
		
		productionOrderItem = productionOrder.getItems().get(1);
		
		//Check the attributes of the production order item
		assertEquals( this.orderItem22.getId(), productionOrderItem.getId());
		assertEquals( this.orderItem22.getMaterial(), productionOrderItem.getMaterial());
		assertEquals( this.orderItem22.getQuantity(), productionOrderItem.getQuantity());
	}
	
	
	@Test
	/**
	 * Tests deletion of a production order.
	 */
	public void testDeleteProductionOrder() {
		WebServiceResult deleteProductionOrderResult;
		ProductionOrder deletedProductionOrder;
		ProductionOrderService service = new ProductionOrderService();
		
		try {
			//Delete production order 1 using the service.
			deleteProductionOrderResult = service.deleteProductionOrder(this.order1.getId());
			
			//There should be no error messages
			assertTrue(WebServiceTools.resultContainsErrorMessage(deleteProductionOrderResult) == false);
			
			//There should be a success message
			assertTrue(deleteProductionOrderResult.getMessages().size() == 1);
			assertTrue(deleteProductionOrderResult.getMessages().get(0).getType() == WebServiceMessageType.S);
			
			//Check if order 1 is missing using the DAO.
			deletedProductionOrder = orderDAO.getProductionOrder(this.order1.getId());
			
			if(deletedProductionOrder != null)
				fail("Production order 1 is still persisted but should have been deleted by the WebService operation 'deleteProductionOrder'.");
		} 
		catch (Exception e) {
			fail(e.getMessage());
		}
		finally {
			//Restore old database state by adding the production order that has been deleted previously.
			try {
				this.order1.setId(null);
				
				//The items have to be re-initialized in order to prevent exception regarding orphan-removal.
				//org.hibernate.HibernateException: Don't change the reference to a collection with delete-orphan enabled : backend.model.SalesOrder.items
				this.order1.setItems(new ArrayList<ProductionOrderItem>());
				this.order1.addItem(this.orderItem11);
				
				orderDAO.insertProductionOrder(this.order1);
			} 
			catch (Exception e) {
				fail(e.getMessage());
			}
		}
	}
	
	
	@Test
	/**
	 * Tests updating a production order with valid data.
	 */
	public void testUpdateValidProductionOrder() {
		WebServiceResult updateProductionOrderResult;
		ProductionOrder updatedProductionOrder;
		ProductionOrderService service = new ProductionOrderService();
		
		//Update the planned execution date.
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(GregorianCalendar.DAY_OF_MONTH, 14);
		this.order1.setPlannedExecutionDate(calendar.getTime());
		updateProductionOrderResult = service.updateProductionOrder(this.convertToWsOrder(this.order1));
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(updateProductionOrderResult) == false);
		
		//There should be a success message
		assertTrue(updateProductionOrderResult.getMessages().size() == 1);
		assertTrue(updateProductionOrderResult.getMessages().get(0).getType() == WebServiceMessageType.S);
		
		//Retrieve the updated production order and check if the changes have been persisted.
		try {
			updatedProductionOrder = orderDAO.getProductionOrder(this.order1.getId());
			assertEquals(this.order1.getPlannedExecutionDate().getTime(), updatedProductionOrder.getPlannedExecutionDate().getTime());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests updating a production order with valid item data.
	 */
	public void testUpdateValidProductionOrderItem() {
		WebServiceResult updateProductionOrderResult;
		ProductionOrder updatedProductionOrder;
		ProductionOrderService service = new ProductionOrderService();
		
		//Update the ordered quantity of an item.
		this.order1.getItems().get(0).setQuantity(Long.valueOf(2));
		updateProductionOrderResult = service.updateProductionOrder(this.convertToWsOrder(this.order1));
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(updateProductionOrderResult) == false);
		
		//There should be a success message
		assertTrue(updateProductionOrderResult.getMessages().size() == 1);
		assertTrue(updateProductionOrderResult.getMessages().get(0).getType() == WebServiceMessageType.S);
		
		//Retrieve the updated production order and check if the changes have been persisted.
		try {
			updatedProductionOrder = orderDAO.getProductionOrder(this.order1.getId());
			assertEquals(this.order1.getItems().get(0).getQuantity(), updatedProductionOrder.getItems().get(0).getQuantity());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests updating a production order without items.
	 */
	public void testUpdateProductionOrderWithoutItems() {
		WebServiceResult updateProductionOrderResult;
		ProductionOrderService service = new ProductionOrderService();
		String actualErrorMessage, expectedErrorMessage;
		
		//Remove the item and try to update the production order.
		this.order1.getItems().clear();
		updateProductionOrderResult = service.updateProductionOrder(this.convertToWsOrder(this.order1));
		
		//There should be a return message of type E.
		assertTrue(updateProductionOrderResult.getMessages().size() == 1);
		assertTrue(updateProductionOrderResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//A proper message should be provided.
		expectedErrorMessage = this.resources.getString("productionOrder.noItemsGiven");
		actualErrorMessage = updateProductionOrderResult.getMessages().get(0).getText();
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
	
	
	@Test
	/**
	 * Tests updating a production order that has multiple items with the same item id.
	 */
	public void testUpdateWithDuplicateItemKey() {
		WebServiceResult updateProductionOrderResult;
		ProductionOrderService service = new ProductionOrderService();
		String actualErrorMessage, expectedErrorMessage;
		ProductionOrderItem newItem = new ProductionOrderItem();;
		
		//Add a new item to the production order.
		newItem.setId(this.orderItem11.getId());
		newItem.setMaterial(this.g4560);
		newItem.setQuantity(Long.valueOf(1));
		this.order1.addItem(newItem);
		updateProductionOrderResult = service.updateProductionOrder(this.convertToWsOrder(this.order1));
		
		//There should be a return message of type E.
		assertTrue(updateProductionOrderResult.getMessages().size() == 1);
		assertTrue(updateProductionOrderResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//A proper message should be provided.
		expectedErrorMessage =  MessageFormat.format(this.resources.getString("productionOrder.duplicateItemKey"), 
				this.order1.getId(), this.orderItem11.getId());
		actualErrorMessage = updateProductionOrderResult.getMessages().get(0).getText();
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
	
	
	@Test
	/**
	 * Tests updating a production order without changing any data.
	 */
	public void testUpdateUnchangedProductionOrder() {
		WebServiceResult updateProductionOrderResult;
		ProductionOrderService service = new ProductionOrderService();
		String actualErrorMessage, expectedErrorMessage;
		
		//Update production order without changing any data.
		updateProductionOrderResult = service.updateProductionOrder(this.convertToWsOrder(this.order1));
		
		//There should be a return message of type I
		assertTrue(updateProductionOrderResult.getMessages().size() == 1);
		assertTrue(updateProductionOrderResult.getMessages().get(0).getType() == WebServiceMessageType.I);
		
		//A proper message should be provided.
		expectedErrorMessage = MessageFormat.format(this.resources.getString("productionOrder.updateUnchanged"), this.order1.getId());
		actualErrorMessage = updateProductionOrderResult.getMessages().get(0).getText();
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
	
	
	@Test
	/**
	 * Tests updating a production order with invalid data.
	 */
	public void testUpdateInvalidProductionOrder() {
		WebServiceResult updateProductionOrderResult;
		ProductionOrderService service = new ProductionOrderService();
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		String actualErrorMessage, expectedErrorMessage;
		
		//Remove the planned execution date.
		this.order1.setPlannedExecutionDate(null);
		updateProductionOrderResult = service.updateProductionOrder(this.convertToWsOrder(this.order1));
		
		//There should be a return message of type E.
		assertTrue(updateProductionOrderResult.getMessages().size() == 1);
		assertTrue(updateProductionOrderResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//A proper message should be provided.
		expectedErrorMessage = messageProvider.getNotNullValidationMessage("productionOrder", "plannedExecutionDate");
		actualErrorMessage = updateProductionOrderResult.getMessages().get(0).getText();
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
	
	
	@Test
	/**
	 * Tests updating a production order item with invalid data.
	 */
	public void testUpdateInvalidProductionOrderItem() {
		WebServiceResult updateProductionOrderResult;
		ProductionOrderService service = new ProductionOrderService();
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		String actualErrorMessage, expectedErrorMessage;
		
		//Update order item with quantity of zero.
		this.order1.getItems().get(0).setQuantity(Long.valueOf(0));
		updateProductionOrderResult = service.updateProductionOrder(this.convertToWsOrder(this.order1));
		
		//There should be a return message of type E.
		assertTrue(updateProductionOrderResult.getMessages().size() == 1);
		assertTrue(updateProductionOrderResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//A proper message should be provided.
		expectedErrorMessage = messageProvider.getMinValidationMessage("productionOrderItem", "quantity", "1");
		actualErrorMessage = updateProductionOrderResult.getMessages().get(0).getText();
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
	
	
//	@Test
	/**
	 * Tests updating a production order to status "IN_PROCESS" when at least one material without corresponding bill of material exists.
	 */
	public void testUpdateStatusWithoutBOM() {
		WebServiceResult updateProductionOrderResult;
		ProductionOrderService service = new ProductionOrderService();
		String actualErrorMessage, expectedErrorMessage;
		
		//Update order status
		this.order1.setStatus(ProductionOrderStatus.IN_PROCESS);
		updateProductionOrderResult = service.updateProductionOrder(this.convertToWsOrder(this.order1));
		
		//There should be a return message of type E.
		assertTrue(updateProductionOrderResult.getMessages().size() == 1);
		assertTrue(updateProductionOrderResult.getMessages().get(0).getType() == WebServiceMessageType.E);
	}
	
	
	@Test
	/**
	 * Tests adding of a new production order.
	 */
	public void testAddValidProductionOrder() {
		ProductionOrder newProductionOrder = new ProductionOrder();
		ProductionOrderItem newProductionOrderItem = new ProductionOrderItem();
		ProductionOrder addedProductionOrder;
		ProductionOrderItem addedProductionOrderItem;
		WebServiceResult addProductionOrderResult;
		ProductionOrderService service = new ProductionOrderService();
		
		//Define the new production order
		newProductionOrderItem.setId(1);
		newProductionOrderItem.setMaterial(this.g4560);
		newProductionOrderItem.setQuantity(Long.valueOf(1));
		
		newProductionOrder.setPlannedExecutionDate(new Date());
		newProductionOrder.setStatus(ProductionOrderStatus.OPEN);
		newProductionOrder.addItem(newProductionOrderItem);
		
		//Add a new production order to the database via WebService
		addProductionOrderResult = service.addProductionOrder(this.convertToWsOrder(newProductionOrder));
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(addProductionOrderResult) == false);
		
		//There should be a success message
		assertTrue(addProductionOrderResult.getMessages().size() == 1);
		assertTrue(addProductionOrderResult.getMessages().get(0).getType() == WebServiceMessageType.S);
		
		//The ID of the newly created production order should be provided in the data part of the WebService return.
		assertNotNull(addProductionOrderResult.getData());
		assertTrue(addProductionOrderResult.getData() instanceof Integer);
		newProductionOrder.setId((Integer) addProductionOrderResult.getData());
		
		//Read the persisted production order via DAO
		try {
			addedProductionOrder = orderDAO.getProductionOrder(newProductionOrder.getId());
			
			//Check if the production order read by the DAO equals the production order inserted using the WebService in each attribute.
			assertEquals(newProductionOrder.getId(), addedProductionOrder.getId());
			assertEquals(newProductionOrder.getOrderDate().getTime(), addedProductionOrder.getOrderDate().getTime());
			assertEquals(newProductionOrder.getPlannedExecutionDate().getTime(), addedProductionOrder.getPlannedExecutionDate().getTime());
			assertEquals(newProductionOrder.getExecutionDate(), addedProductionOrder.getExecutionDate());
			assertEquals(newProductionOrder.getStatus(), addedProductionOrder.getStatus());
			
			//Checks at item level.
			assertEquals(newProductionOrder.getItems().size(), addedProductionOrder.getItems().size());
			addedProductionOrderItem = addedProductionOrder.getItems().get(0);
			assertEquals(newProductionOrderItem.getId(), addedProductionOrderItem.getId());
			assertEquals(newProductionOrderItem.getMaterial().getId(), addedProductionOrderItem.getMaterial().getId());
			assertEquals(newProductionOrderItem.getQuantity(), addedProductionOrderItem.getQuantity());
		} catch (Exception e) {
			fail(e.getMessage());
		}
		finally {
			//Delete the newly added production order.
			try {
				orderDAO.deleteProductionOrder(newProductionOrder);
			} 
			catch (Exception e) {
				fail(e.getMessage());
			}
		}
	}
	
	
	@Test
	/**
	 * Tests adding of an invalid production order.
	 */
	public void testAddInvalidProductionOrder() {
		ProductionOrder newProductionOrder = new ProductionOrder();
		WebServiceResult addProductionOrderResult;
		ProductionOrderService service = new ProductionOrderService();
		String actualErrorMessage, expectedErrorMessage;
		
		//Define the new production order without an item.
		newProductionOrder.setPlannedExecutionDate(new Date());
		newProductionOrder.setStatus(ProductionOrderStatus.OPEN);
		
		//Add a new production order to the database via WebService
		addProductionOrderResult = service.addProductionOrder(this.convertToWsOrder(newProductionOrder));
		
		//There should be a return message of type E.
		assertTrue(addProductionOrderResult.getMessages().size() == 1);
		assertTrue(addProductionOrderResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//The new production order should not have been persisted
		assertNull(newProductionOrder.getId());
		
		//A proper message should be provided.
		expectedErrorMessage = this.resources.getString("productionOrder.noItemsGiven");
		actualErrorMessage = addProductionOrderResult.getMessages().get(0).getText();
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
	
	
	/**
	 * Tests if the material inventory is updated if the production order changes to status "FINISHED".
	 * 
	 * The quantity of the materials that are produced should be increased.
	 * The quantity of the materials that are consumed by production should be decreased.
	 */
	public void testInventoryUpdatedOnFinishedActive() {
		// TODO
	}
	
	
	/*
	 * TODO Implement further test cases
	 * 
	 * testUpdateStatusWithoutBOM (error when no BOM exists)
	 * testUpdateStatusWithBOM (should succeed without further message)
	 * 
	 * testInventoryUpdatedOnFinishedActive	(unfinished -> finished)
	 * testInventoryUpdatedOnFinishedInactive (finished -> unfinished)
	 * testInventoryUpdatedOnFinishedDeleted (delete finished order)
	 * testInventoryUpdatedOnFinishedCanceled (cancel finished order)
	 * testChangeQuantityOfFinishedOrder (do not allow, check error message)
	 * testChangeQuantityOfCanceledOrder (do not allow, check error message)
	 */
	
	
	/**
	 * Converts a production order to the lean WebService representation.
	 * 
	 * @param order The production order to be converted.
	 * @return The lean WebService representation of the production order.
	 */
	private ProductionOrderWS convertToWsOrder(ProductionOrder order) {
		ProductionOrderWS wsOrder = new ProductionOrderWS();
		
		//Head level
		wsOrder.setProductionOrderId(order.getId());
		wsOrder.setOrderDate(order.getOrderDate());
		wsOrder.setPlannedExecutionDate(order.getPlannedExecutionDate());
		wsOrder.setExecutionDate(order.getExecutionDate());
		wsOrder.setStatus(order.getStatus());
		
		//Item level
		for(ProductionOrderItem orderItem:order.getItems()) {
			ProductionOrderItemWS wsOrderItem = new ProductionOrderItemWS();
			wsOrderItem.setItemId(orderItem.getId());
			wsOrderItem.setMaterialId(orderItem.getMaterial().getId());
			wsOrderItem.setQuantity(orderItem.getQuantity());
			wsOrder.addItem(wsOrderItem);
		}
		
		return wsOrder;
	}
}
