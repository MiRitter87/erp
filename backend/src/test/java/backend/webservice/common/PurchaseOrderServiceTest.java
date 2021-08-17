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

import backend.dao.BusinessPartnerDao;
import backend.dao.DAOManager;
import backend.dao.MaterialDao;
import backend.dao.PurchaseOrderDao;
import backend.model.BusinessPartner;
import backend.model.BusinessPartnerType;
import backend.model.Currency;
import backend.model.Material;
import backend.model.PurchaseOrder;
import backend.model.PurchaseOrderArray;
import backend.model.PurchaseOrderItem;
import backend.model.PurchaseOrderStatus;
import backend.model.UnitOfMeasurement;
import backend.model.webservice.PurchaseOrderItemWS;
import backend.model.webservice.PurchaseOrderWS;
import backend.model.webservice.WebServiceMessageType;
import backend.model.webservice.WebServiceResult;
import backend.tools.WebServiceTools;
import backend.tools.test.ValidationMessageProvider;

/**
 * Tests the purchase order service.
 * 
 * @author Michael
 */
public class PurchaseOrderServiceTest {
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources = ResourceBundle.getBundle("backend");	
	
	/**
	 * DAO to access purchase order data.
	 */
	private static PurchaseOrderDao orderDAO;
	
	/**
	 * DAO to access material data.
	 */
	private static MaterialDao materialDAO;
	
	/**
	 * DAO to access business partner data.
	 */
	private static BusinessPartnerDao partnerDAO;
	
	/**
	 * The purchase order under test.
	 */
	private PurchaseOrder order1;
	
	/**
	 * The purchase order item under test.
	 */
	private PurchaseOrderItem orderItem1;
	
	/**
	 * Another purchase order for testing.
	 */
	private PurchaseOrder order2;
	
	/**
	 * The first purchase order item of order 2.
	 */
	private PurchaseOrderItem orderItem21;
	
	/**
	 * The second purchase order item of order 2.
	 */
	private PurchaseOrderItem orderItem22;
	
	/**
	 * The material that is being ordered.
	 */
	private Material rx570;
	
	/**
	 * Another material that can be ordered.
	 */
	private Material g4560;
	
	/**
	 * The order partner.
	 */
	private BusinessPartner partner;
	
	
	@BeforeAll
	/**
	 * Tasks to be performed once at startup of test class.
	 */
	public static void setUpClass() {
		materialDAO = DAOManager.getInstance().getMaterialDAO();
		partnerDAO = DAOManager.getInstance().getBusinessPartnerDAO();
		orderDAO = DAOManager.getInstance().getPurchaseOrderDAO();
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
		this.createDummyPartner();
		this.createDummyOrders();
	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	private void tearDown() {
		this.deleteDummyOrders();
		this.deleteDummyPartner();
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
	 * Initializes the database with a dummy business partner.
	 */
	private void createDummyPartner() {
		this.partner = new BusinessPartner();
		this.partner.setCompanyName("Amalgamated Moose Pasture");
		this.partner.setFirstName("John");
		this.partner.setLastName("Doe");
		this.partner.setStreetName("Main Street");
		this.partner.setHouseNumber("1a");
		this.partner.setZipCode("12345");
		this.partner.setCityName("Moose City");
		this.partner.setPhoneNumber("+1 123-456-7890");
		this.partner.addType(BusinessPartnerType.VENDOR);
		
		try {
			partnerDAO.insertBusinessPartner(this.partner);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Initializes the database with dummy purchase orders.
	 */
	private void createDummyOrders() {
		GregorianCalendar tomorrow = new GregorianCalendar();
		tomorrow.add(GregorianCalendar.DAY_OF_MONTH, 1);
		
		this.orderItem1 = new PurchaseOrderItem();
		this.orderItem1.setId(1);
		this.orderItem1.setMaterial(this.rx570);
		this.orderItem1.setQuantity(Long.valueOf(1));
		
		this.order1 = new PurchaseOrder();
		this.order1.setVendor(this.partner);
		this.order1.setOrderDate(new Date());
		this.order1.setRequestedDeliveryDate(new Date());
		this.order1.addItem(this.orderItem1);
		
		this.orderItem21 = new PurchaseOrderItem();
		this.orderItem21.setId(1);
		this.orderItem21.setMaterial(this.rx570);
		this.orderItem21.setQuantity(Long.valueOf(2));
		
		this.orderItem22 = new PurchaseOrderItem();
		this.orderItem22.setId(2);
		this.orderItem22.setMaterial(this.g4560);
		this.orderItem22.setQuantity(Long.valueOf(1));
		
		this.order2 = new PurchaseOrder();
		this.order2.setVendor(this.partner);
		this.order2.setOrderDate(new Date());
		this.order2.setRequestedDeliveryDate(tomorrow.getTime());
		this.order2.addItem(this.orderItem21);
		this.order2.addItem(this.orderItem22);
		
		try {
			orderDAO.insertPurchaseOrder(this.order1);
			orderDAO.insertPurchaseOrder(this.order2);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Deletes the dummy purchase order from the database.
	 */
	private void deleteDummyOrders() {
		try {
			orderDAO.deletePurchaseOrder(this.order2);
			orderDAO.deletePurchaseOrder(this.order1);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Deletes the dummy business partner from the database.
	 */
	private void deleteDummyPartner() {
		try {
			partnerDAO.deleteBusinessPartner(this.partner);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Deletes the dummy material from the database.
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
	 * Tests the retrieval of a purchase order.
	 */
	public void testGetPurchaseOrder() {
		WebServiceResult getPurchaseOrderResult;
		PurchaseOrder purchaseOrder;
		PurchaseOrderItem purchaseOrderItem;
		
		//Get the purchase order.
		PurchaseOrderService service = new PurchaseOrderService();
		getPurchaseOrderResult = service.getPurchaseOrder(this.order1.getId());
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(getPurchaseOrderResult) == false);
		
		//Assure that a purchase order is returned
		assertTrue(getPurchaseOrderResult.getData() instanceof PurchaseOrder);
		
		purchaseOrder = (PurchaseOrder) getPurchaseOrderResult.getData();
		
		//Check each attribute of the purchase order
		assertEquals(purchaseOrder.getId(), this.order1.getId());
		assertEquals(purchaseOrder.getVendor(), this.order1.getVendor());
		assertEquals(purchaseOrder.getOrderDate().getTime(), this.order1.getOrderDate().getTime());
		assertEquals(purchaseOrder.getRequestedDeliveryDate().getTime(), this.order1.getRequestedDeliveryDate().getTime());
		assertEquals(purchaseOrder.getStatus(), this.order1.getStatus());
		
		//The returned purchase order should have one item.
		assertEquals(purchaseOrder.getItems().size(), this.order1.getItems().size());
		
		purchaseOrderItem = purchaseOrder.getItems().get(0);
		
		//Check the attributes of the purchase order item
		assertEquals(purchaseOrderItem.getId(), this.orderItem1.getId());
		assertEquals(purchaseOrderItem.getMaterial(), this.orderItem1.getMaterial());
		assertEquals(purchaseOrderItem.getQuantity(), this.orderItem1.getQuantity());
	}
	
	
	@Test
	/**
	 * Tests the retrieval of all purchase orders.
	 */
	public void testGetAllPurchaseOrders() {
		WebServiceResult getPurchaseOrdersResult;
		PurchaseOrderArray purchaseOrders;
		PurchaseOrder purchaseOrder;
		PurchaseOrderItem purchaseOrderItem;
		
		//Get the purchase orders.
		PurchaseOrderService service = new PurchaseOrderService();
		getPurchaseOrdersResult = service.getPurchaseOrders();
		purchaseOrders = (PurchaseOrderArray) getPurchaseOrdersResult.getData();
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(getPurchaseOrdersResult) == false);
		
		//Check if two purchase orders are returned.
		assertTrue(purchaseOrders.getPurchaseOrders().size() == 2);
		
		//Check both purchase orders by each attribute
		purchaseOrder = purchaseOrders.getPurchaseOrders().get(0);
		assertEquals(purchaseOrder.getId(), this.order1.getId());
		assertEquals(purchaseOrder.getVendor(), this.order1.getVendor());
		assertEquals(purchaseOrder.getOrderDate().getTime(), this.order1.getOrderDate().getTime());
		assertEquals(purchaseOrder.getRequestedDeliveryDate().getTime(), this.order1.getRequestedDeliveryDate().getTime());
		assertEquals(purchaseOrder.getStatus(), this.order1.getStatus());
		
		assertEquals(purchaseOrder.getItems().size(), this.order1.getItems().size());
		purchaseOrderItem = purchaseOrder.getItems().get(0);
		assertEquals(purchaseOrderItem.getId(), this.orderItem1.getId());
		assertEquals(purchaseOrderItem.getMaterial(), this.orderItem1.getMaterial());
		assertEquals(purchaseOrderItem.getQuantity(), this.orderItem1.getQuantity());
		assertEquals(purchaseOrderItem.getPriceTotal(), this.orderItem1.getPriceTotal());
		
		purchaseOrder = purchaseOrders.getPurchaseOrders().get(1);
		assertEquals(purchaseOrder.getId(), this.order2.getId());
		assertEquals(purchaseOrder.getVendor(), this.order2.getVendor());
		assertEquals(purchaseOrder.getOrderDate().getTime(), this.order2.getOrderDate().getTime());
		assertEquals(purchaseOrder.getRequestedDeliveryDate().getTime(), this.order2.getRequestedDeliveryDate().getTime());
		assertEquals(purchaseOrder.getStatus(), this.order2.getStatus());
		
		assertEquals(purchaseOrder.getItems().size(), this.order2.getItems().size());
		purchaseOrderItem = purchaseOrder.getItems().get(0);
		assertEquals(purchaseOrderItem.getId(), this.orderItem21.getId());
		assertEquals(purchaseOrderItem.getMaterial(), this.orderItem21.getMaterial());
		assertEquals(purchaseOrderItem.getQuantity(), this.orderItem21.getQuantity());
		assertEquals(purchaseOrderItem.getPriceTotal(), this.orderItem21.getPriceTotal());
		
		purchaseOrderItem = purchaseOrder.getItems().get(1);
		assertEquals(purchaseOrderItem.getId(), this.orderItem22.getId());
		assertEquals(purchaseOrderItem.getMaterial(), this.orderItem22.getMaterial());
		assertEquals(purchaseOrderItem.getQuantity(), this.orderItem22.getQuantity());
		assertEquals(purchaseOrderItem.getPriceTotal(), this.orderItem22.getPriceTotal());
	}
	
	
	@Test
	/**
	 * Tests deletion of a purchase order.
	 */
	public void testDeletePurchaseOrder() {
		WebServiceResult deletePurchaseOrderResult;
		PurchaseOrder deletedPurchaseOrder;
		
		try {
			//Delete purchase order 1 using the service.
			PurchaseOrderService service = new PurchaseOrderService();
			deletePurchaseOrderResult = service.deletePurchaseOrder(this.order1.getId());
			
			//There should be no error messages
			assertTrue(WebServiceTools.resultContainsErrorMessage(deletePurchaseOrderResult) == false);
			
			//There should be a success message
			assertTrue(deletePurchaseOrderResult.getMessages().size() == 1);
			assertTrue(deletePurchaseOrderResult.getMessages().get(0).getType() == WebServiceMessageType.S);
			
			//Check if order 1 is missing using the DAO.
			deletedPurchaseOrder = orderDAO.getPurchaseOrder(this.order1.getId());
			
			if(deletedPurchaseOrder != null)
				fail("Purchase order 1 is still persisted but should have been deleted by the WebService operation 'deletePurchaseOrder'.");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		finally {
			//Restore old database state by adding the purchase order that has been deleted previously.
			try {
				this.order1.setId(null);
				
				//The items have to be re-initialized in order to prevent exception regarding orphan-removal.
				//org.hibernate.HibernateException: Don't change the reference to a collection with delete-orphan enabled : backend.model.PurchaseOrder.items
				this.order1.setItems(new ArrayList<PurchaseOrderItem>());
				this.order1.addItem(this.orderItem1);
				
				orderDAO.insertPurchaseOrder(this.order1);
			} 
			catch (Exception e) {
				fail(e.getMessage());
			}
		}
	}
	
	
	@Test
	/**
	 * Tests updating a purchase order with valid data.
	 */
	public void testUpdateValidPurchaseOrder() {
		WebServiceResult updatePurchaseOrderResult;
		PurchaseOrder updatedPurchaseOrder;
		PurchaseOrderService orderService = new PurchaseOrderService();
		
		//Update the requested delivery date.
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(GregorianCalendar.DAY_OF_MONTH, 14);
		this.order1.setRequestedDeliveryDate(calendar.getTime());
		updatePurchaseOrderResult = orderService.updatePurchaseOrder(this.convertToWsOrder(this.order1));
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(updatePurchaseOrderResult) == false);
		
		//There should be a success message
		assertTrue(updatePurchaseOrderResult.getMessages().size() == 1);
		assertTrue(updatePurchaseOrderResult.getMessages().get(0).getType() == WebServiceMessageType.S);
		
		//Retrieve the updated purchase order and check if the changes have been persisted.
		try {
			updatedPurchaseOrder = orderDAO.getPurchaseOrder(this.order1.getId());
			assertEquals(this.order1.getRequestedDeliveryDate().getTime(), updatedPurchaseOrder.getRequestedDeliveryDate().getTime());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests updating a purchase order with valid item data.
	 */
	public void testUpdateValidPurchaseOrderItem() {
		WebServiceResult updatePurchaseOrderResult;
		PurchaseOrder updatedPurchaseOrder;
		PurchaseOrderService orderService = new PurchaseOrderService();
		
		//Update the ordered quantity of an item.
		this.order1.getItems().get(0).setQuantity(Long.valueOf(2));
		updatePurchaseOrderResult = orderService.updatePurchaseOrder(this.convertToWsOrder(this.order1));
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(updatePurchaseOrderResult) == false);
		
		//There should be a success message
		assertTrue(updatePurchaseOrderResult.getMessages().size() == 1);
		assertTrue(updatePurchaseOrderResult.getMessages().get(0).getType() == WebServiceMessageType.S);
		
		//Retrieve the updated purchase order and check if the changes have been persisted.
		try {
			updatedPurchaseOrder = orderDAO.getPurchaseOrder(this.order1.getId());
			assertEquals(this.order1.getItems().get(0).getQuantity(), updatedPurchaseOrder.getItems().get(0).getQuantity());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests updating a purchase order without items.
	 */
	public void testUpdatePurchaseOrderWithoutItems() {
		WebServiceResult updatePurchaseOrderResult;
		PurchaseOrderService orderService = new PurchaseOrderService();
		String actualErrorMessage, expectedErrorMessage;
		
		//Remove the item and try to update the purchase order.
		this.order1.getItems().clear();
		updatePurchaseOrderResult = orderService.updatePurchaseOrder(this.convertToWsOrder(this.order1));
		
		//There should be a return message of type E.
		assertTrue(updatePurchaseOrderResult.getMessages().size() == 1);
		assertTrue(updatePurchaseOrderResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//A proper message should be provided.
		expectedErrorMessage = this.resources.getString("purchaseOrder.noItemsGiven");
		actualErrorMessage = updatePurchaseOrderResult.getMessages().get(0).getText();
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
	
	
	@Test
	/**
	 * Tests updating a purchase order with invalid data.
	 */
	public void testUpdateInvalidSalesOrder() {
		WebServiceResult updatePurchaseOrderResult;
		PurchaseOrderService orderService = new PurchaseOrderService();
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		String actualErrorMessage, expectedErrorMessage;
		
		//Remove the vendor.
		this.order1.setVendor(null);
		updatePurchaseOrderResult = orderService.updatePurchaseOrder(this.convertToWsOrder(this.order1));
		
		//There should be a return message of type E.
		assertTrue(updatePurchaseOrderResult.getMessages().size() == 1);
		assertTrue(updatePurchaseOrderResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//A proper message should be provided.
		expectedErrorMessage = messageProvider.getNotNullValidationMessage("purchaseOrder", "vendor");
		actualErrorMessage = updatePurchaseOrderResult.getMessages().get(0).getText();
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
	
	
	@Test
	/**
	 * Tests updating a sales order item with invalid data.
	 */
	public void testUpdateInvalidSalesOrderItem() {
		WebServiceResult updatePurchaseOrderResult;
		PurchaseOrderService orderService = new PurchaseOrderService();
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		String actualErrorMessage, expectedErrorMessage;
		
		//Update order item with quantity of zero.
		this.order1.getItems().get(0).setQuantity(Long.valueOf(0));
		updatePurchaseOrderResult = orderService.updatePurchaseOrder(this.convertToWsOrder(this.order1));
		
		//There should be a return message of type E.
		assertTrue(updatePurchaseOrderResult.getMessages().size() == 1);
		assertTrue(updatePurchaseOrderResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//A proper message should be provided.
		expectedErrorMessage = messageProvider.getMinValidationMessage("purchaseOrderItem", "quantity", "1");
		actualErrorMessage = updatePurchaseOrderResult.getMessages().get(0).getText();
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
	
	
	@Test
	/**
	 * Tests updating a purchase order without changing any data.
	 */
	public void testUpdateUnchangedPurchaseOrder() {
		WebServiceResult updatePurchaseOrderResult;
		PurchaseOrderService orderService = new PurchaseOrderService();
		String actualErrorMessage, expectedErrorMessage;
		
		//Update purchase order without changing any data.
		updatePurchaseOrderResult = orderService.updatePurchaseOrder(this.convertToWsOrder(this.order1));
		
		//There should be a return message of type I
		assertTrue(updatePurchaseOrderResult.getMessages().size() == 1);
		assertTrue(updatePurchaseOrderResult.getMessages().get(0).getType() == WebServiceMessageType.I);
		
		//A proper message should be provided.
		expectedErrorMessage = MessageFormat.format(this.resources.getString("purchaseOrder.updateUnchanged"), this.order1.getId());
		actualErrorMessage = updatePurchaseOrderResult.getMessages().get(0).getText();
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
	
	
	@Test
	/**
	 * Tests updating a purchase order that has multiple items with the same item id.
	 */
	public void testUpdateWithDuplicateItemKey() {
		WebServiceResult updatePurchaseOrderResult;
		PurchaseOrderService orderService = new PurchaseOrderService();
		String actualErrorMessage, expectedErrorMessage;
		PurchaseOrderItem newItem = new PurchaseOrderItem();
		
		//Add a new item to the purchase order.
		newItem.setId(this.orderItem1.getId());
		newItem.setMaterial(this.g4560);
		newItem.setQuantity(Long.valueOf(1));
		this.order1.addItem(newItem);
		updatePurchaseOrderResult = orderService.updatePurchaseOrder(this.convertToWsOrder(this.order1));
		
		//There should be a return message of type E.
		assertTrue(updatePurchaseOrderResult.getMessages().size() == 1);
		assertTrue(updatePurchaseOrderResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//A proper message should be provided.
		expectedErrorMessage =  MessageFormat.format(this.resources.getString("purchaseOrder.duplicateItemKey"), 
				this.order1.getId(), this.orderItem1.getId());
		actualErrorMessage = updatePurchaseOrderResult.getMessages().get(0).getText();
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
	
	
	@Test
	/**
	 * Tests adding of a new purchase order.
	 */
	public void testAddValidPurchaseOrder() {
		PurchaseOrder newPurchaseOrder = new PurchaseOrder();
		PurchaseOrderItem newPurchaseOrderItem = new PurchaseOrderItem();
		PurchaseOrder addedPurchaseOrder;
		PurchaseOrderItem addedPurchaseOrderItem;
		WebServiceResult addPurchaseOrderResult;
		PurchaseOrderService orderService = new PurchaseOrderService();
		
		//Define the new salesOrder
		newPurchaseOrderItem.setId(1);
		newPurchaseOrderItem.setMaterial(this.g4560);
		newPurchaseOrderItem.setQuantity(Long.valueOf(1));
		
		newPurchaseOrder.setVendor(this.partner);
		newPurchaseOrder.addItem(newPurchaseOrderItem);
		
		//Add a new sales order to the database via WebService
		addPurchaseOrderResult = orderService.addPurchaseOrder(this.convertToWsOrder(newPurchaseOrder));
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(addPurchaseOrderResult) == false);
		
		//There should be a success message
		assertTrue(addPurchaseOrderResult.getMessages().size() == 1);
		assertTrue(addPurchaseOrderResult.getMessages().get(0).getType() == WebServiceMessageType.S);
		
		//The ID of the newly created purchase order should be provided in the data part of the WebService return.
		assertNotNull(addPurchaseOrderResult.getData());
		assertTrue(addPurchaseOrderResult.getData() instanceof Integer);
		newPurchaseOrder.setId((Integer) addPurchaseOrderResult.getData());
		
		//Read the persisted purchase order via DAO
		try {
			addedPurchaseOrder = orderDAO.getPurchaseOrder(newPurchaseOrder.getId());
			
			//Check if the purchase order read by the DAO equals the purchase order inserted using the WebService in each attribute.
			assertEquals(newPurchaseOrder.getId(), addedPurchaseOrder.getId());
			assertEquals(newPurchaseOrder.getOrderDate().getTime(), addedPurchaseOrder.getOrderDate().getTime());
			assertEquals(newPurchaseOrder.getRequestedDeliveryDate(), addedPurchaseOrder.getRequestedDeliveryDate());
			assertEquals(newPurchaseOrder.getVendor(), addedPurchaseOrder.getVendor());
			assertEquals(newPurchaseOrder.getStatus(), addedPurchaseOrder.getStatus());
			
			//Checks at item level.
			assertEquals(newPurchaseOrder.getItems().size(), addedPurchaseOrder.getItems().size());
			addedPurchaseOrderItem = addedPurchaseOrder.getItems().get(0);
			assertEquals(newPurchaseOrderItem.getId(), addedPurchaseOrderItem.getId());
			assertEquals(newPurchaseOrderItem.getMaterial().getId(), addedPurchaseOrderItem.getMaterial().getId());
			assertEquals(newPurchaseOrderItem.getQuantity(), addedPurchaseOrderItem.getQuantity());
			assertEquals(newPurchaseOrderItem.getPriceTotal(), addedPurchaseOrderItem.getPriceTotal());
		} catch (Exception e) {
			fail(e.getMessage());
		}
		finally {
			//Delete the newly added purchase order.
			try {
				orderDAO.deletePurchaseOrder(newPurchaseOrder);
			} 
			catch (Exception e) {
				fail(e.getMessage());
			}
		}		
	}
	
	
	@Test
	/**
	 * Tests adding of an invalid purchase order.
	 */
	public void testAddInvalidPurchaseOrder() {
		PurchaseOrder newPurchaseOrder = new PurchaseOrder();
		WebServiceResult addPurchaseOrderResult;
		PurchaseOrderService orderService = new PurchaseOrderService();
		
		//Define the new purchase order without an item.
		newPurchaseOrder.setVendor(this.partner);
		
		//Add a new purchase order to the database via WebService
		addPurchaseOrderResult = orderService.addPurchaseOrder(this.convertToWsOrder(newPurchaseOrder));
		
		//There should be a return message of type E.
		assertTrue(addPurchaseOrderResult.getMessages().size() == 1);
		assertTrue(addPurchaseOrderResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//The new purchase order should not have been persisted
		assertNull(newPurchaseOrder.getId());
	}
	
	
	@Test
	/**
	 * Tests if the ordered material quantity is added to the material inventory if status GOODS_RECEIPT is set to active.
	 */
	public void testInventoryAddedOnGoodsReceiptActive() {
		Material rx570, g4560;
		Long rx570InventoryBefore = Long.valueOf(0), rx570InventoryAfter = Long.valueOf(0);
		Long g4560InventoryBefore = Long.valueOf(0), g4560InventoryAfter = Long.valueOf(0);
		PurchaseOrderService orderService = new PurchaseOrderService();
		
		//Get material inventory before the goods have been received.
		try {
			rx570 = materialDAO.getMaterial(this.rx570.getId());
			g4560 = materialDAO.getMaterial(this.g4560.getId());
			
			rx570InventoryBefore = rx570.getInventory();
			g4560InventoryBefore = g4560.getInventory();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		//Set the order status indicating goods have been received.
		this.order2.setStatus(PurchaseOrderStatus.GOODS_RECEIPT, true);
		orderService.updatePurchaseOrder(this.convertToWsOrder(this.order2));
		
		//Get material inventory after the order status has been updated.
		try {
			rx570 = materialDAO.getMaterial(this.rx570.getId());
			g4560 = materialDAO.getMaterial(this.g4560.getId());
			
			rx570InventoryAfter = rx570.getInventory();
			g4560InventoryAfter = g4560.getInventory();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		//Check if the material inventory has been increased by the quantity of the ordered item.
		assertTrue(rx570InventoryAfter == (rx570InventoryBefore + this.orderItem21.getQuantity()));
		assertTrue(g4560InventoryAfter == (g4560InventoryBefore + this.orderItem22.getQuantity()));	
	}
	
	
	@Test
	/**
	 * Tests if the ordered material quantity is reduced from the material inventory if the status GOODS_RECEIPT is set from active to inactive.
	 */
	public void testInventoryOnGoodsReceiptInactive() {
		Material rx570, g4560;
		Long rx570InventoryBefore = Long.valueOf(0), rx570InventoryAfter = Long.valueOf(0);
		Long g4560InventoryBefore = Long.valueOf(0), g4560InventoryAfter = Long.valueOf(0);
		PurchaseOrderService orderService = new PurchaseOrderService();
		
		//At first set the GOODS_RECEIPT status to active to trigger inbound booking of material inventory.
		try {
			this.order2.setStatus(PurchaseOrderStatus.GOODS_RECEIPT, true);
			orderDAO.updatePurchaseOrder(this.order2);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		//Get material inventory before the status GOODS_RECEIPT is reverted.
		try {
			rx570 = materialDAO.getMaterial(this.rx570.getId());
			g4560 = materialDAO.getMaterial(this.g4560.getId());
			
			rx570InventoryBefore = rx570.getInventory();
			g4560InventoryBefore = g4560.getInventory();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		//Set the order status indicating goods have not been received.
		this.order2.setStatus(PurchaseOrderStatus.GOODS_RECEIPT, false);
		orderService.updatePurchaseOrder(this.convertToWsOrder(this.order2));
		
		//Get material inventory after the order status has been updated.
		try {
			rx570 = materialDAO.getMaterial(this.rx570.getId());
			g4560 = materialDAO.getMaterial(this.g4560.getId());
			
			rx570InventoryAfter = rx570.getInventory();
			g4560InventoryAfter = g4560.getInventory();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		//Check if the material inventory has been reduced by the quantity of the ordered item.
		assertTrue(rx570InventoryAfter == (rx570InventoryBefore - this.orderItem21.getQuantity()));
		assertTrue(g4560InventoryAfter == (g4560InventoryBefore - this.orderItem22.getQuantity()));	
	}
	
	
	/*
	 * TODO Add additional tests
	 *
	 * -test ordered material quantity removed from inventory if status GOODS_RECEIPT is active and order status CANCELED is set from inactive to active
	 * -test ordered material quantity removed from inventory if status GOODS_RECEIPT is active and order is being deleted
	 * 
	 * -test ordered material quantity of item removed from inventory if status GOODS_RECEIPT is active and item is being deleted
	 */
	
	
	/**
	 * Converts a purchase order to the lean WebService representation.
	 * 
	 * @param order The purchase order to be converted.
	 * @return The lean WebService representation of the purchase order.
	 */
	private PurchaseOrderWS convertToWsOrder(PurchaseOrder order) {
		PurchaseOrderWS wsOrder = new PurchaseOrderWS();
		
		//Head level
		wsOrder.setPurchaseOrderId(order.getId());
		wsOrder.setOrderDate(order.getOrderDate());
		wsOrder.setRequestedDeliveryDate(order.getRequestedDeliveryDate());
		wsOrder.setStatus(order.getStatus());
		
		if(order.getVendor() != null)
			wsOrder.setVendorId(order.getVendor().getId());
		
		//Item level
		for(PurchaseOrderItem orderItem:order.getItems()) {
			PurchaseOrderItemWS wsOrderItem = new PurchaseOrderItemWS();
			wsOrderItem.setItemId(orderItem.getId());
			wsOrderItem.setMaterialId(orderItem.getMaterial().getId());
			wsOrderItem.setQuantity(orderItem.getQuantity());
			wsOrderItem.setPriceTotal(orderItem.getPriceTotal());
			wsOrder.addItem(wsOrderItem);
		}
		
		return wsOrder;
	}
}
