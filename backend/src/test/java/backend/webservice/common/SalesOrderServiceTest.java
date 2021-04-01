package backend.webservice.common;

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

import backend.dao.BusinessPartnerHibernateDao;
import backend.dao.MaterialHibernateDao;
import backend.dao.SalesOrderHibernateDao;
import backend.model.BusinessPartner;
import backend.model.Currency;
import backend.model.Material;
import backend.model.SalesOrder;
import backend.model.SalesOrderArray;
import backend.model.SalesOrderItem;
import backend.model.UnitOfMeasurement;
import backend.model.webservice.WebServiceMessageType;
import backend.model.webservice.WebServiceResult;
import backend.tools.WebServiceTools;
import backend.tools.test.ValidationMessageProvider;

/**
 * Tests the sales order service.
 * 
 * @author Michael
 */
public class SalesOrderServiceTest {
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources = ResourceBundle.getBundle("backend");	
	
	/**
	 * DAO to access sales order data.
	 */
	private static SalesOrderHibernateDao orderDAO;
	
	/**
	 * DAO to access material data.
	 */
	private static MaterialHibernateDao materialDAO;
	
	/**
	 * DAO to access business partner data.
	 */
	private static BusinessPartnerHibernateDao partnerDAO;
	
	/**
	 * The sales order under test.
	 */
	private SalesOrder order1;
	
	/**
	 * The sales order item under test.
	 */
	private SalesOrderItem orderItem1;
	
	/**
	 * Another sales order for testing.
	 */
	private SalesOrder order2;
	
	/**
	 * The first sales order item of order 2.
	 */
	private SalesOrderItem orderItem21;
	
	/**
	 * The second sales order item of order 2.
	 */
	private SalesOrderItem orderItem22;
	
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
		materialDAO = new MaterialHibernateDao();
		partnerDAO = new BusinessPartnerHibernateDao();
		orderDAO = new SalesOrderHibernateDao();
	}
	
	
	@AfterAll
	/**
	 * Tasks to be performed once at end of test class.
	 */
	public static void tearDownClass() {
		try {
			orderDAO.close();
			partnerDAO.close();
			materialDAO.close();
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
		
		try {
			partnerDAO.insertBusinessPartner(this.partner);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	
	/**
	 * Initializes the database with a dummy sales order.
	 */
	private void createDummyOrders() {
		GregorianCalendar tomorrow = new GregorianCalendar();
		tomorrow.add(GregorianCalendar.DAY_OF_MONTH, 1);
		
		this.orderItem1 = new SalesOrderItem();
		this.orderItem1.setId(1);
		this.orderItem1.setMaterial(this.rx570);
		this.orderItem1.setQuantity(Long.valueOf(1));
		
		this.order1 = new SalesOrder();
		this.order1.setSoldToParty(this.partner);
		this.order1.setShipToParty(this.partner);
		this.order1.setBillToParty(this.partner);
		this.order1.setOrderDate(new Date());
		this.order1.setRequestedDeliveryDate(new Date());
		this.order1.addItem(this.orderItem1);
		
		this.orderItem21 = new SalesOrderItem();
		this.orderItem21.setId(1);
		this.orderItem21.setMaterial(this.rx570);
		this.orderItem21.setQuantity(Long.valueOf(2));
		
		this.orderItem22 = new SalesOrderItem();
		this.orderItem22.setId(2);
		this.orderItem22.setMaterial(this.g4560);
		this.orderItem22.setQuantity(Long.valueOf(1));
		
		this.order2 = new SalesOrder();
		this.order2.setSoldToParty(this.partner);
		this.order2.setShipToParty(this.partner);
		this.order2.setBillToParty(this.partner);
		this.order2.setOrderDate(new Date());
		this.order2.setRequestedDeliveryDate(tomorrow.getTime());
		this.order2.addItem(this.orderItem21);
		this.order2.addItem(this.orderItem22);
		
		try {
			orderDAO.insertSalesOrder(this.order1);
			orderDAO.insertSalesOrder(this.order2);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Deletes the dummy sales order from the database.
	 */
	private void deleteDummyOrders() {
		try {
			orderDAO.deleteSalesOrder(this.order2);
			orderDAO.deleteSalesOrder(this.order1);
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
	 * Tests the retrieval of a sales order.
	 */
	public void testGetSalesOrder() {
		WebServiceResult getSalesOrderResult;
		SalesOrder salesOrder;
		SalesOrderItem salesOrderItem;
		
		//Get the sales order.
		SalesOrderService service = new SalesOrderService();
		getSalesOrderResult = service.getSalesOrder(this.order1.getId());
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(getSalesOrderResult) == false);
		
		//Assure that an employee is returned
		assertTrue(getSalesOrderResult.getData() instanceof SalesOrder);
		
		salesOrder = (SalesOrder) getSalesOrderResult.getData();
		
		//Check each attribute of the sales order
		assertEquals(salesOrder.getId(), this.order1.getId());
		assertEquals(salesOrder.getSoldToParty(), this.order1.getSoldToParty());
		assertEquals(salesOrder.getShipToParty(), this.order1.getShipToParty());
		assertEquals(salesOrder.getBillToParty(), this.order1.getBillToParty());
		assertEquals(salesOrder.getOrderDate().getTime(), this.order1.getOrderDate().getTime());
		assertEquals(salesOrder.getRequestedDeliveryDate().getTime(), this.order1.getRequestedDeliveryDate().getTime());
		
		salesOrderItem = salesOrder.getItems().get(0);
		
		//Check the attributes of the sales order item
		assertEquals(salesOrderItem.getId(), this.orderItem1.getId());
		assertEquals(salesOrderItem.getMaterial(), this.orderItem1.getMaterial());
		assertEquals(salesOrderItem.getQuantity(), this.orderItem1.getQuantity());
	}
	
	
	@Test
	/**
	 * Tests the retrieval of all sales orders.
	 */
	public void testGetAllSalesOrders() {
		WebServiceResult getSalesOrdersResult;
		SalesOrderArray salesOrders;
		SalesOrder salesOrder;
		SalesOrderItem salesOrderItem;
		
		//Get the sales orders.
		SalesOrderService service = new SalesOrderService();
		getSalesOrdersResult = service.getSalesOrders();
		salesOrders = (SalesOrderArray) getSalesOrdersResult.getData();
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(getSalesOrdersResult) == false);
		
		//Check if two sales orders are returned.
		assertTrue(salesOrders.getSalesOrders().size() == 2);
		
		//Check both sales orders by each attribute
		salesOrder = salesOrders.getSalesOrders().get(0);
		assertEquals(salesOrder.getId(), this.order1.getId());
		assertEquals(salesOrder.getSoldToParty(), this.order1.getSoldToParty());
		assertEquals(salesOrder.getShipToParty(), this.order1.getShipToParty());
		assertEquals(salesOrder.getBillToParty(), this.order1.getBillToParty());
		assertEquals(salesOrder.getOrderDate().getTime(), this.order1.getOrderDate().getTime());
		assertEquals(salesOrder.getRequestedDeliveryDate().getTime(), this.order1.getRequestedDeliveryDate().getTime());
		
		assertEquals(salesOrder.getItems().size(), this.order1.getItems().size());
		salesOrderItem = salesOrder.getItems().get(0);
		assertEquals(salesOrderItem.getId(), this.orderItem1.getId());
		assertEquals(salesOrderItem.getMaterial(), this.orderItem1.getMaterial());
		assertEquals(salesOrderItem.getQuantity(), this.orderItem1.getQuantity());
		assertEquals(salesOrderItem.getPriceTotal(), this.orderItem1.getPriceTotal());
		
		salesOrder = salesOrders.getSalesOrders().get(1);
		assertEquals(salesOrder.getId(), this.order2.getId());
		assertEquals(salesOrder.getSoldToParty(), this.order2.getSoldToParty());
		assertEquals(salesOrder.getShipToParty(), this.order2.getShipToParty());
		assertEquals(salesOrder.getBillToParty(), this.order2.getBillToParty());
		assertEquals(salesOrder.getOrderDate().getTime(), this.order2.getOrderDate().getTime());
		assertEquals(salesOrder.getRequestedDeliveryDate().getTime(), this.order2.getRequestedDeliveryDate().getTime());
		
		assertEquals(salesOrder.getItems().size(), this.order2.getItems().size());
		salesOrderItem = salesOrder.getItems().get(0);
		assertEquals(salesOrderItem.getId(), this.orderItem21.getId());
		assertEquals(salesOrderItem.getMaterial(), this.orderItem21.getMaterial());
		assertEquals(salesOrderItem.getQuantity(), this.orderItem21.getQuantity());
		assertEquals(salesOrderItem.getPriceTotal(), this.orderItem21.getPriceTotal());
		
		salesOrderItem = salesOrder.getItems().get(1);
		assertEquals(salesOrderItem.getId(), this.orderItem22.getId());
		assertEquals(salesOrderItem.getMaterial(), this.orderItem22.getMaterial());
		assertEquals(salesOrderItem.getQuantity(), this.orderItem22.getQuantity());
		assertEquals(salesOrderItem.getPriceTotal(), this.orderItem22.getPriceTotal());
	}

	
	@Test
	/**
	 * Tests deletion of a sales order.
	 */
	public void testDeleteSalesOrder() {
		WebServiceResult deleteSalesOrderResult;
		SalesOrder deletedSalesOrder;
		
		try {
			//Delete sales order 1 using the service.
			SalesOrderService service = new SalesOrderService();
			deleteSalesOrderResult = service.deleteSalesOrder(this.order1.getId());
			
			//There should be no error messages
			assertTrue(WebServiceTools.resultContainsErrorMessage(deleteSalesOrderResult) == false);
			
			//There should be a success message
			assertTrue(deleteSalesOrderResult.getMessages().size() == 1);
			assertTrue(deleteSalesOrderResult.getMessages().get(0).getType() == WebServiceMessageType.S);
			
			//Check if order 1 is missing using the DAO.
			deletedSalesOrder = orderDAO.getSalesOrder(this.order1.getId());
			
			if(deletedSalesOrder != null)
				fail("Sales order 1 is still persisted but should have been deleted by the WebService operation 'deleteSalesOrder'.");
		} 
		catch (Exception e) {
			fail(e.getMessage());
		}
		finally {
			//Restore old database state by adding the sales order that has been deleted previously.
			try {
				this.order1.setId(null);
				
				//The items have to be re-initialized in order to prevent exception regarding orphan-removal.
				//org.hibernate.HibernateException: Don't change the reference to a collection with delete-orphan enabled : backend.model.SalesOrder.items
				this.order1.setItems(new ArrayList<SalesOrderItem>());
				this.order1.addItem(this.orderItem1);
				
				orderDAO.insertSalesOrder(this.order1);
			} 
			catch (Exception e) {
				fail(e.getMessage());
			}
		}
	}
	
	
	@Test
	/**
	 * Tests updating a sales order with valid data.
	 */
	public void testUpdateValidSalesOrder() {
		WebServiceResult updateSalesOrderResult;
		SalesOrder updatedSalesOrder;
		SalesOrderService orderService = new SalesOrderService();
		
		//Update the requested delivery date.
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(GregorianCalendar.DAY_OF_MONTH, 14);
		this.order1.setRequestedDeliveryDate(calendar.getTime());
		updateSalesOrderResult = orderService.updateSalesOrder(this.order1);
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(updateSalesOrderResult) == false);
		
		//There should be a success message
		assertTrue(updateSalesOrderResult.getMessages().size() == 1);
		assertTrue(updateSalesOrderResult.getMessages().get(0).getType() == WebServiceMessageType.S);
		
		//Retrieve the updated sales order and check if the changes have been persisted.
		try {
			updatedSalesOrder = orderDAO.getSalesOrder(this.order1.getId());
			assertEquals(this.order1.getRequestedDeliveryDate().getTime(), updatedSalesOrder.getRequestedDeliveryDate().getTime());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests updating a sales order with valid item data.
	 */
	public void testUpdateValidSalesOrderItem() {
		WebServiceResult updateSalesOrderResult;
		SalesOrder updatedSalesOrder;
		SalesOrderService orderService = new SalesOrderService();
		
		//Update the ordered quantity of an item.
		this.order1.getItems().get(0).setQuantity(Long.valueOf(2));
		updateSalesOrderResult = orderService.updateSalesOrder(this.order1);
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(updateSalesOrderResult) == false);
		
		//There should be a success message
		assertTrue(updateSalesOrderResult.getMessages().size() == 1);
		assertTrue(updateSalesOrderResult.getMessages().get(0).getType() == WebServiceMessageType.S);
		
		//Retrieve the updated sales order and check if the changes have been persisted.
		try {
			updatedSalesOrder = orderDAO.getSalesOrder(this.order1.getId());
			assertEquals(this.order1.getItems().get(0).getQuantity(), updatedSalesOrder.getItems().get(0).getQuantity());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests updating a sales order without items.
	 */
	public void testUpdateSalesOrderWithoutItems() {
		WebServiceResult updateSalesOrderResult;
		SalesOrderService orderService = new SalesOrderService();
		String actualErrorMessage, expectedErrorMessage;
		
		//Remove the item and try to update the sales order.
		this.order1.getItems().clear();
		updateSalesOrderResult = orderService.updateSalesOrder(this.order1);
		
		//There should be a return message of type E.
		assertTrue(updateSalesOrderResult.getMessages().size() == 1);
		assertTrue(updateSalesOrderResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//A proper message should be provided.
		expectedErrorMessage = this.resources.getString("salesOrder.noItemsGiven");
		actualErrorMessage = updateSalesOrderResult.getMessages().get(0).getText();
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
	
	
	@Test
	/**
	 * Tests updating a sales order with invalid data.
	 */
	public void testUpdateInvalidSalesOrder() {
		WebServiceResult updateSalesOrderResult;
		SalesOrderService orderService = new SalesOrderService();
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		String actualErrorMessage, expectedErrorMessage;
		
		//Remove the bill-to party.
		this.order1.setBillToParty(null);
		updateSalesOrderResult = orderService.updateSalesOrder(this.order1);
		
		//There should be a return message of type E.
		assertTrue(updateSalesOrderResult.getMessages().size() == 1);
		assertTrue(updateSalesOrderResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//A proper message should be provided.
		expectedErrorMessage = messageProvider.getNotNullValidationMessage("salesOrder", "billToParty");
		actualErrorMessage = updateSalesOrderResult.getMessages().get(0).getText();
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
	
	
	@Test
	/**
	 * Tests updating a sales order item with invalid data.
	 */
	public void testUpdateInvalidSalesOrderItem() {
		WebServiceResult updateSalesOrderResult;
		SalesOrderService orderService = new SalesOrderService();
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		String actualErrorMessage, expectedErrorMessage;
		
		//Update order item with quantity of zero.
		this.order1.getItems().get(0).setQuantity(Long.valueOf(0));
		updateSalesOrderResult = orderService.updateSalesOrder(this.order1);
		
		//There should be a return message of type E.
		assertTrue(updateSalesOrderResult.getMessages().size() == 1);
		assertTrue(updateSalesOrderResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//A proper message should be provided.
		expectedErrorMessage = messageProvider.getMinValidationMessage("salesOrderItem", "quantity", "1");
		actualErrorMessage = updateSalesOrderResult.getMessages().get(0).getText();
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
	
	
	@Test
	/**
	 * Tests updating a sales order without changing any data.
	 */
	public void testUpdateUnchangedSalesOrder() {
		WebServiceResult updateSalesOrderResult;
		SalesOrderService orderService = new SalesOrderService();
		String actualErrorMessage, expectedErrorMessage;
		
		//Update sales order without changing any data.
		updateSalesOrderResult = orderService.updateSalesOrder(this.order1);
		
		//There should be a return message of type I
		assertTrue(updateSalesOrderResult.getMessages().size() == 1);
		assertTrue(updateSalesOrderResult.getMessages().get(0).getType() == WebServiceMessageType.I);
		
		//A proper message should be provided.
		expectedErrorMessage = MessageFormat.format(this.resources.getString("salesOrder.updateUnchanged"), this.order1.getId());
		actualErrorMessage = updateSalesOrderResult.getMessages().get(0).getText();
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
	
	@Test
	/**
	 * Tests updating a sales order that has multiple items with the same item id.
	 */
	public void testUpdateWithDuplicateItemKey() {
		WebServiceResult updateSalesOrderResult;
		SalesOrderService orderService = new SalesOrderService();
		String actualErrorMessage, expectedErrorMessage;
		SalesOrderItem newItem = new SalesOrderItem();;
		
		//Add a new item to the sales order.
		newItem.setId(this.orderItem1.getId());
		newItem.setMaterial(this.g4560);
		newItem.setQuantity(Long.valueOf(1));
		this.order1.addItem(newItem);
		updateSalesOrderResult = orderService.updateSalesOrder(this.order1);
		
		//There should be a return message of type E.
		assertTrue(updateSalesOrderResult.getMessages().size() == 1);
		assertTrue(updateSalesOrderResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//A proper message should be provided.
		expectedErrorMessage =  MessageFormat.format(this.resources.getString("salesOrder.duplicateItemKey"), 
				this.order1.getId(), this.orderItem1.getId());
		actualErrorMessage = updateSalesOrderResult.getMessages().get(0).getText();
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
	
	
	@Test
	/**
	 * Tests updating a sales order where the ordered quantity exceeds the inventory.
	 */
	public void testUpdateQuantityExceedsInventory() {
		WebServiceResult updateSalesOrderResult;
		SalesOrderService orderService = new SalesOrderService();
		String actualErrorMessage, expectedErrorMessage;
		
		//Update the quantity of the order.
		this.order1.getItems().get(0).setQuantity(this.rx570.getInventory()+1);
		updateSalesOrderResult = orderService.updateSalesOrder(this.order1);
		
		//There should be a return message of type E.
		assertTrue(updateSalesOrderResult.getMessages().size() == 1);
		assertTrue(updateSalesOrderResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//A proper message should be provided.
		expectedErrorMessage =  MessageFormat.format(this.resources.getString("salesOrder.QuantityExceedsInventory"), 
				this.orderItem1.getMaterial().getId(), this.orderItem1.getMaterial().getInventory(), this.orderItem1.getMaterial().getUnit());
		actualErrorMessage = updateSalesOrderResult.getMessages().get(0).getText();
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
	
	
	@Test
	/**
	 * Tests adding of a new sales order.
	 */
	public void testAddValidSalesOrder() {
		SalesOrder newSalesOrder = new SalesOrder();
		SalesOrderItem newSalesOrderItem = new SalesOrderItem();
		SalesOrder addedSalesOrder;
		SalesOrderItem addedSalesOrderItem;
		WebServiceResult addSalesOrderResult;
		SalesOrderService orderService = new SalesOrderService();
		
		//Define the new salesOrder
		newSalesOrderItem.setId(1);
		newSalesOrderItem.setMaterial(this.g4560);
		newSalesOrderItem.setQuantity(Long.valueOf(1));
		
		newSalesOrder.setSoldToParty(this.partner);
		newSalesOrder.setShipToParty(this.partner);
		newSalesOrder.setBillToParty(this.partner);
		newSalesOrder.addItem(newSalesOrderItem);
		
		//Add a new sales order to the database via WebService
		addSalesOrderResult = orderService.addSalesOrder(newSalesOrder);
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(addSalesOrderResult) == false);
		
		//There should be a success message
		assertTrue(addSalesOrderResult.getMessages().size() == 1);
		assertTrue(addSalesOrderResult.getMessages().get(0).getType() == WebServiceMessageType.S);
		
		//Read the persisted sales order via DAO
		try {
			addedSalesOrder = orderDAO.getSalesOrder(newSalesOrder.getId());
			
			//Check if the sales order read by the DAO equals the sales order inserted using the WebService in each attribute.
			assertEquals(newSalesOrder.getId(), addedSalesOrder.getId());
			assertEquals(newSalesOrder.getOrderDate().getTime(), addedSalesOrder.getOrderDate().getTime());
			assertEquals(newSalesOrder.getRequestedDeliveryDate(), addedSalesOrder.getRequestedDeliveryDate());
			assertEquals(newSalesOrder.getSoldToParty(), addedSalesOrder.getSoldToParty());
			assertEquals(newSalesOrder.getShipToParty(), addedSalesOrder.getShipToParty());
			assertEquals(newSalesOrder.getBillToParty(), addedSalesOrder.getBillToParty());
			
			//Checks at item level.
			assertEquals(newSalesOrder.getItems().size(), addedSalesOrder.getItems().size());
			addedSalesOrderItem = addedSalesOrder.getItems().get(0);
			assertEquals(newSalesOrderItem.getId(), addedSalesOrderItem.getId());
			assertEquals(newSalesOrderItem.getMaterial(), addedSalesOrderItem.getMaterial());
			assertEquals(newSalesOrderItem.getQuantity(), addedSalesOrderItem.getQuantity());
			assertEquals(newSalesOrderItem.getPriceTotal(), addedSalesOrderItem.getPriceTotal());
		} catch (Exception e) {
			fail(e.getMessage());
		}
		finally {
			//Delete the newly added sales order.
			try {
				orderDAO.deleteSalesOrder(newSalesOrder);
			} 
			catch (Exception e) {
				fail(e.getMessage());
			}
		}
	}
	
	
	@Test
	/**
	 * Tests adding of an invalid sales order.
	 */
	public void testAddInvalidSalesOrder() {
		SalesOrder newSalesOrder = new SalesOrder();
		WebServiceResult addSalesOrderResult;
		SalesOrderService orderService = new SalesOrderService();
		
		//Define the new salesOrder without an item.
		newSalesOrder.setSoldToParty(this.partner);
		newSalesOrder.setShipToParty(this.partner);
		newSalesOrder.setBillToParty(this.partner);
		
		//Add a new sales order to the database via WebService
		addSalesOrderResult = orderService.addSalesOrder(newSalesOrder);
		
		//There should be a return message of type E.
		assertTrue(addSalesOrderResult.getMessages().size() == 1);
		assertTrue(addSalesOrderResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//The new sales order should not have been persisted
		assertNull(newSalesOrder.getId());
	}
}