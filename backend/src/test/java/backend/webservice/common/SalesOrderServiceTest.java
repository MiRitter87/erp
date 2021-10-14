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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import backend.dao.AccountDao;
import backend.dao.BusinessPartnerDao;
import backend.dao.DAOManager;
import backend.dao.MaterialDao;
import backend.dao.SalesOrderDao;
import backend.model.Currency;
import backend.model.account.Account;
import backend.model.businessPartner.BusinessPartner;
import backend.model.businessPartner.BusinessPartnerType;
import backend.model.material.Material;
import backend.model.material.UnitOfMeasurement;
import backend.model.salesOrder.SalesOrder;
import backend.model.salesOrder.SalesOrderArray;
import backend.model.salesOrder.SalesOrderItem;
import backend.model.salesOrder.SalesOrderItemWS;
import backend.model.salesOrder.SalesOrderStatus;
import backend.model.salesOrder.SalesOrderWS;
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
	private static SalesOrderDao orderDAO;
	
	/**
	 * DAO to access material data.
	 */
	private static MaterialDao materialDAO;
	
	/**
	 * DAO to access business partner data.
	 */
	private static BusinessPartnerDao partnerDAO;
	
	/**
	 * DAO to access account data.
	 */
	private static AccountDao accountDAO;
	
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
	
	/**
	 * The account for payment settlement.
	 */
	private Account paymentAccount;
	
	
	@BeforeAll
	/**
	 * Tasks to be performed once at startup of test class.
	 */
	public static void setUpClass() {
		materialDAO = DAOManager.getInstance().getMaterialDAO();
		partnerDAO = DAOManager.getInstance().getBusinessPartnerDAO();
		orderDAO = DAOManager.getInstance().getSalesOrderDAO();
		accountDAO = DAOManager.getInstance().getAccountDAO();
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
		this.createDummyAccount();
		this.createDummyOrders();
	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	private void tearDown() {
		this.deleteDummyOrders();
		this.deleteDummyAccount();
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
		this.partner.addType(BusinessPartnerType.CUSTOMER);
		
		try {
			partnerDAO.insertBusinessPartner(this.partner);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Initializes the database with a dummy account.
	 */
	private void createDummyAccount() {
		this.paymentAccount = new Account();
		this.paymentAccount.setDescription("Account for order bill payment settlement");
		this.paymentAccount.setBalance(BigDecimal.valueOf(1000));
		this.paymentAccount.setCurrency(Currency.EUR);
		
		try {
			accountDAO.insertAccount(this.paymentAccount);
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
		this.order1.setPaymentAccount(this.paymentAccount);
		this.order1.setOrderDate(new Date());
		this.order1.setRequestedDeliveryDate(new Date());
		this.order1.setStatus(SalesOrderStatus.OPEN);
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
		this.order2.setPaymentAccount(this.paymentAccount);
		this.order2.setOrderDate(new Date());
		this.order2.setRequestedDeliveryDate(tomorrow.getTime());
		this.order2.setStatus(SalesOrderStatus.IN_PROCESS);
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
	 * Deletes the dummy account from the database.
	 */
	private void deleteDummyAccount() {
		try {
			accountDAO.deleteAccount(this.paymentAccount);
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
		
		//Assure that a sales order is returned
		assertTrue(getSalesOrderResult.getData() instanceof SalesOrder);
		
		salesOrder = (SalesOrder) getSalesOrderResult.getData();
		
		//Check each attribute of the sales order
		assertEquals(salesOrder.getId(), this.order1.getId());
		assertEquals(salesOrder.getSoldToParty(), this.order1.getSoldToParty());
		assertEquals(salesOrder.getShipToParty(), this.order1.getShipToParty());
		assertEquals(salesOrder.getBillToParty(), this.order1.getBillToParty());
		assertEquals(salesOrder.getPaymentAccount(), this.order1.getPaymentAccount());
		assertEquals(salesOrder.getOrderDate().getTime(), this.order1.getOrderDate().getTime());
		assertEquals(salesOrder.getRequestedDeliveryDate().getTime(), this.order1.getRequestedDeliveryDate().getTime());
		assertEquals(salesOrder.getStatus(), this.order1.getStatus());
		
		//The returned sales order should have one item.
		assertEquals(salesOrder.getItems().size(), this.order1.getItems().size());
		
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
		getSalesOrdersResult = service.getSalesOrders(null);
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
		assertEquals(salesOrder.getPaymentAccount(), this.order1.getPaymentAccount());
		assertEquals(salesOrder.getOrderDate().getTime(), this.order1.getOrderDate().getTime());
		assertEquals(salesOrder.getRequestedDeliveryDate().getTime(), this.order1.getRequestedDeliveryDate().getTime());
		assertEquals(salesOrder.getStatus(), this.order1.getStatus());
		
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
		assertEquals(salesOrder.getPaymentAccount(), this.order2.getPaymentAccount());
		assertEquals(salesOrder.getOrderDate().getTime(), this.order2.getOrderDate().getTime());
		assertEquals(salesOrder.getRequestedDeliveryDate().getTime(), this.order2.getRequestedDeliveryDate().getTime());
		assertEquals(salesOrder.getStatus(), this.order2.getStatus());
		
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
	 * Tests the retrieval of all sales orders that are in status "In Process".
	 */
	public void testGetSalesOrdersInProcess() {
		WebServiceResult getSalesOrdersResult;
		SalesOrderArray salesOrders;
		SalesOrder salesOrder;
		
		//Get the sales orders.
		SalesOrderService service = new SalesOrderService();
		getSalesOrdersResult = service.getSalesOrders(SalesOrderStatus.IN_PROCESS);
		salesOrders = (SalesOrderArray) getSalesOrdersResult.getData();
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(getSalesOrdersResult) == false);
		
		//Check if one sales order is returned.
		assertTrue(salesOrders.getSalesOrders().size() == 1);
		
		//Check the sales order.
		salesOrder = salesOrders.getSalesOrders().get(0);
		assertEquals(salesOrder.getId(), this.order2.getId());
		assertEquals(salesOrder.getSoldToParty(), this.order2.getSoldToParty());
		assertEquals(salesOrder.getShipToParty(), this.order2.getShipToParty());
		assertEquals(salesOrder.getBillToParty(), this.order2.getBillToParty());
		assertEquals(salesOrder.getPaymentAccount(), this.order2.getPaymentAccount());
		assertEquals(salesOrder.getOrderDate().getTime(), this.order2.getOrderDate().getTime());
		assertEquals(salesOrder.getRequestedDeliveryDate().getTime(), this.order2.getRequestedDeliveryDate().getTime());
		assertEquals(salesOrder.getStatus(), this.order2.getStatus());
	}
	
	
	@Test
	/**
	 * Tests getting all sales orders on an empty database.
	 */
	public void testGetAllSallesOrdersOnEmptyDB() {
		WebServiceResult getSalesOrdersResult;
		SalesOrderArray salesOrders;
		SalesOrderService service = new SalesOrderService();
		
		try {
			//Delete the existing orders first.
			orderDAO.deleteSalesOrder(this.order1);
			orderDAO.deleteSalesOrder(this.order2);
			
			getSalesOrdersResult = service.getSalesOrders(null);
			
			//There should be no error message
			assertTrue(WebServiceTools.resultContainsErrorMessage(getSalesOrdersResult) == false);
			
			//No sales order should be returned
			salesOrders = (SalesOrderArray) getSalesOrdersResult.getData();
			assertTrue(salesOrders.getSalesOrders().size() == 0);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		finally {
			//Add the previously deleted orders to allow proper functioning of following tearDown and setUp methods.
			try {
				this.order1.setId(null);
				//The items have to be re-initialized in order to prevent exception regarding orphan-removal.
				//org.hibernate.HibernateException: Don't change the reference to a collection with delete-orphan enabled : backend.model.SalesOrder.items
				this.order1.setItems(new ArrayList<SalesOrderItem>());
				this.order1.addItem(this.orderItem1);
				orderDAO.insertSalesOrder(this.order1);
				
				this.order2.setId(null);
				this.order2.setItems(new ArrayList<SalesOrderItem>());
				this.order2.addItem(this.orderItem21);
				this.order2.addItem(this.orderItem22);
				orderDAO.insertSalesOrder(this.order2);
			} catch (Exception e) {
				fail(e.getMessage());
			}
		}
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
		updateSalesOrderResult = orderService.updateSalesOrder(this.convertToWsOrder(this.order1));
		
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
		updateSalesOrderResult = orderService.updateSalesOrder(this.convertToWsOrder(this.order1));
		
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
		updateSalesOrderResult = orderService.updateSalesOrder(this.convertToWsOrder(this.order1));
		
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
		updateSalesOrderResult = orderService.updateSalesOrder(this.convertToWsOrder(this.order1));
		
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
		updateSalesOrderResult = orderService.updateSalesOrder(this.convertToWsOrder(this.order1));
		
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
		updateSalesOrderResult = orderService.updateSalesOrder(this.convertToWsOrder(this.order1));
		
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
		updateSalesOrderResult = orderService.updateSalesOrder(this.convertToWsOrder(this.order1));
		
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
		updateSalesOrderResult = orderService.updateSalesOrder(this.convertToWsOrder(this.order1));
		
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
		newSalesOrder.setPaymentAccount(this.paymentAccount);
		newSalesOrder.setStatus(SalesOrderStatus.OPEN);
		newSalesOrder.addItem(newSalesOrderItem);
		
		//Add a new sales order to the database via WebService
		addSalesOrderResult = orderService.addSalesOrder(this.convertToWsOrder(newSalesOrder));
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(addSalesOrderResult) == false);
		
		//There should be a success message
		assertTrue(addSalesOrderResult.getMessages().size() == 1);
		assertTrue(addSalesOrderResult.getMessages().get(0).getType() == WebServiceMessageType.S);
		
		//The ID of the newly created sales order should be provided in the data part of the WebService return.
		assertNotNull(addSalesOrderResult.getData());
		assertTrue(addSalesOrderResult.getData() instanceof Integer);
		newSalesOrder.setId((Integer) addSalesOrderResult.getData());
		
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
			assertEquals(newSalesOrder.getPaymentAccount(), addedSalesOrder.getPaymentAccount());
			assertEquals(newSalesOrder.getStatus(), addedSalesOrder.getStatus());
			
			//Checks at item level.
			assertEquals(newSalesOrder.getItems().size(), addedSalesOrder.getItems().size());
			addedSalesOrderItem = addedSalesOrder.getItems().get(0);
			assertEquals(newSalesOrderItem.getId(), addedSalesOrderItem.getId());
			assertEquals(newSalesOrderItem.getMaterial().getId(), addedSalesOrderItem.getMaterial().getId());
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
		newSalesOrder.setPaymentAccount(this.paymentAccount);
		newSalesOrder.setStatus(SalesOrderStatus.OPEN);
		
		//Add a new sales order to the database via WebService
		addSalesOrderResult = orderService.addSalesOrder(this.convertToWsOrder(newSalesOrder));
		
		//There should be a return message of type E.
		assertTrue(addSalesOrderResult.getMessages().size() == 1);
		assertTrue(addSalesOrderResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//The new sales order should not have been persisted
		assertNull(newSalesOrder.getId());
	}
	
	
	@Test
	/**
	 * Tests the serialization of the WebService result to JSON using the getSalesOrders operation of the REST service.
	 */
	public void testJSONSerializationOfGetSalesOrders() {
		WebServiceResult getSalesOrdersResult;
		ObjectMapper objectMapper = new ObjectMapper();
		
		//Get the sales order.
		SalesOrderService service = new SalesOrderService();
		getSalesOrdersResult = service.getSalesOrders(null);
		
		//Serialize the result to JSON.
		try {
			objectMapper.writeValueAsString(getSalesOrdersResult);
		} catch (JsonProcessingException e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests if the inventory of the ordered material is reduced when the order is created.
	 */
	public void testInventoryReducedOnOrderCreation() {
		Material rx570, g4560;
		Long rx570InventoryBefore = Long.valueOf(0), rx570InventoryAfter = Long.valueOf(0);
		Long g4560InventoryBefore = Long.valueOf(0), g4560InventoryAfter = Long.valueOf(0);
		SalesOrder newOrder = new SalesOrder();
		SalesOrderItem itemG4560, itemRX570;
		WebServiceResult addOrderResult;
		SalesOrderService orderService = new SalesOrderService();
		
		try {
			//Get the inventory of the ordered material before placing the order.
			rx570 = materialDAO.getMaterial(this.rx570.getId());
			g4560 = materialDAO.getMaterial(this.g4560.getId());
			
			rx570InventoryBefore = rx570.getInventory();
			g4560InventoryBefore = g4560.getInventory();	
		
			//Place the order
			itemRX570 = new SalesOrderItem();
			itemRX570.setId(1);
			itemRX570.setMaterial(this.rx570);
			itemRX570.setQuantity(Long.valueOf(1));
			
			itemG4560 = new SalesOrderItem();
			itemG4560.setId(2);
			itemG4560.setMaterial(this.g4560);
			itemG4560.setQuantity(Long.valueOf(1));
				
			newOrder.setSoldToParty(this.partner);
			newOrder.setShipToParty(this.partner);
			newOrder.setBillToParty(this.partner);
			newOrder.setPaymentAccount(this.paymentAccount);
			newOrder.setOrderDate(new Date());
			newOrder.setRequestedDeliveryDate(new Date());
			newOrder.setStatus(SalesOrderStatus.OPEN);
			newOrder.addItem(itemRX570);
			newOrder.addItem(itemG4560);
			
			addOrderResult = orderService.addSalesOrder(this.convertToWsOrder(newOrder));
			
			//Assure no error message exists
			assertTrue(WebServiceTools.resultContainsErrorMessage(addOrderResult) == false);
			
			//There should be a success message
			assertTrue(addOrderResult.getMessages().size() == 1);
			assertTrue(addOrderResult.getMessages().get(0).getType() == WebServiceMessageType.S);
			
			newOrder.setId((Integer) addOrderResult.getData());
		
			//Check if the material inventory is reduced by the ordered quantity
			rx570 = materialDAO.getMaterial(this.rx570.getId());
			g4560 = materialDAO.getMaterial(this.g4560.getId());
			
			rx570InventoryAfter = rx570.getInventory();
			g4560InventoryAfter = g4560.getInventory();
			
			assertTrue(rx570InventoryAfter == (rx570InventoryBefore - itemRX570.getQuantity()));
			assertTrue(g4560InventoryAfter == (g4560InventoryBefore - itemG4560.getQuantity()));			
		} catch (Exception e) {
			fail(e.getMessage());
		}
		finally {
			//Delete the newly added sales order.
			try {
				orderDAO.deleteSalesOrder(newOrder);
			} 
			catch (Exception e) {
				fail(e.getMessage());
			}
		}
	}
	
	
	@Test
	/**
	 * Tests if the inventory of the ordered material is added when the order is canceled.
	 */
	public void testInventoryAddedOnOrderCancelation() {
		Material rx570, g4560;
		Long rx570InventoryBefore = Long.valueOf(0), rx570InventoryAfter = Long.valueOf(0);
		Long g4560InventoryBefore = Long.valueOf(0), g4560InventoryAfter = Long.valueOf(0);
		WebServiceResult updateOrderResult;
		SalesOrderService orderService = new SalesOrderService();
		
		try {
			//Get the material inventory before order cancellation.
			rx570 = materialDAO.getMaterial(this.rx570.getId());
			g4560 = materialDAO.getMaterial(this.g4560.getId());
			
			rx570InventoryBefore = rx570.getInventory();
			g4560InventoryBefore = g4560.getInventory();
		
			//Update order with status canceled
			this.order2.setStatus(SalesOrderStatus.CANCELED);
			updateOrderResult = orderService.updateSalesOrder(this.convertToWsOrder(this.order2));
			
			//Assure no error message exists
			assertTrue(WebServiceTools.resultContainsErrorMessage(updateOrderResult) == false);
			
			//There should be a success message
			assertTrue(updateOrderResult.getMessages().size() == 1);
			assertTrue(updateOrderResult.getMessages().get(0).getType() == WebServiceMessageType.S);
			
			//Get the material inventory after order cancellation.
			rx570 = materialDAO.getMaterial(this.rx570.getId());
			g4560 = materialDAO.getMaterial(this.g4560.getId());
			
			rx570InventoryAfter = rx570.getInventory();
			g4560InventoryAfter = g4560.getInventory();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		//Check if the inventory is increased according to the canceled order.
		assertTrue(rx570InventoryAfter == (rx570InventoryBefore + this.orderItem21.getQuantity()));
		assertTrue(g4560InventoryAfter == (g4560InventoryBefore + this.orderItem22.getQuantity()));
	}
	
	
	@Test
	/**
	 * Tests if the inventory of the ordered materials is added if a sales order is deleted.
	 */
	public void testInventoryAddedOnOrderDeletion() {
		Material rx570, g4560;
		Long rx570InventoryBefore = Long.valueOf(0), rx570InventoryAfter = Long.valueOf(0);
		Long g4560InventoryBefore = Long.valueOf(0), g4560InventoryAfter = Long.valueOf(0);
		SalesOrderService orderService = new SalesOrderService();
		
		try {
			//Get the material inventory before order deletion.
			rx570 = materialDAO.getMaterial(this.rx570.getId());
			g4560 = materialDAO.getMaterial(this.g4560.getId());
			
			rx570InventoryBefore = rx570.getInventory();
			g4560InventoryBefore = g4560.getInventory();

			//Delete the sales order
			orderService.deleteSalesOrder(this.order2.getId());
		
			//Get the inventory of the items after deletion
			rx570 = materialDAO.getMaterial(this.rx570.getId());
			g4560 = materialDAO.getMaterial(this.g4560.getId());
			
			rx570InventoryAfter = rx570.getInventory();
			g4560InventoryAfter = g4560.getInventory();
			
			//Check if ordered quantity has been added to the inventory
			assertTrue(rx570InventoryAfter == (rx570InventoryBefore + this.orderItem21.getQuantity()));
			assertTrue(g4560InventoryAfter == (g4560InventoryBefore + this.orderItem22.getQuantity()));		
		} catch (Exception e) {
			fail(e.getMessage());
		} finally {
			try {
				//Restore old database state by adding the sales order that has been deleted previously.
				this.order2.setId(null);
				
				//The items have to be re-initialized in order to prevent exception regarding orphan-removal.
				//org.hibernate.HibernateException: Don't change the reference to a collection with delete-orphan enabled : backend.model.SalesOrder.items
				this.order2.setItems(new ArrayList<SalesOrderItem>());
				this.order2.addItem(this.orderItem21);
				this.order2.addItem(this.orderItem22);
				
				orderDAO.insertSalesOrder(this.order2);
			}
			catch (Exception e) {
				fail(e.getMessage());
			}
		}
	}
	
	
	@Test
	/**
	 * Tests if the material inventory is reduced if an additional item is added to an existing sales order.
	 */
	public void testInventoryReducedOnItemAdded() {
		Material g4560;
		Long g4560InventoryBefore = Long.valueOf(0), g4560InventoryAfter = Long.valueOf(0);
		SalesOrderService orderService = new SalesOrderService();
		SalesOrderItem newItem = new SalesOrderItem();;
		
		try {
			//Get material inventory before an additional item is added to an existing sales order.
			g4560 = materialDAO.getMaterial(this.g4560.getId());
			g4560InventoryBefore = g4560.getInventory();
		
			//Add a new item to an existing sales order.
			newItem.setId(2);
			newItem.setMaterial(this.g4560);
			newItem.setQuantity(Long.valueOf(1));
			this.order1.addItem(newItem);
			orderService.updateSalesOrder(this.convertToWsOrder(this.order1));
			
			//Get material inventory after the item has been added.
			g4560 = materialDAO.getMaterial(this.g4560.getId());
			g4560InventoryAfter = g4560.getInventory();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		//Check if the material inventory has been reduced by the quantity of the newly added item.
		assertTrue(g4560InventoryAfter == (g4560InventoryBefore - newItem.getQuantity()));
	}
	
	
	@Test
	/**
	 * Tests if the material inventory is reduced if the ordered quantity of an existing order item is increased.
	 */
	public void testInventoryReducedOnOrderedQuantityIncreased() {
		Material rx570;
		SalesOrderItem item;
		Long rx570InventoryBefore = Long.valueOf(0), rx570InventoryAfter = Long.valueOf(0);
		Long quantitiyAdded = Long.valueOf(1);
		SalesOrderService orderService = new SalesOrderService();
		
		try {
			//Get the material inventory before the ordered quantity is changed.
			rx570 = materialDAO.getMaterial(this.rx570.getId());
			rx570InventoryBefore = rx570.getInventory();

			//Change the ordered quantity
			item = this.order1.getItemWithId(this.orderItem1.getId());
			item.setQuantity(item.getQuantity() + quantitiyAdded);
			orderService.updateSalesOrder(this.convertToWsOrder(this.order1));
			
			//Get the material inventory after the ordered quantity has been changed.
			rx570 = materialDAO.getMaterial(this.rx570.getId());
			rx570InventoryAfter = rx570.getInventory();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		//Check if the material inventory has been reduced by the additionally ordered quantity.
		assertTrue(rx570InventoryAfter == (rx570InventoryBefore - quantitiyAdded));
	}
	
	
	@Test
	/**
	 * Tests if the material inventory is added if an item is removed from an existing sales order.
	 */
	public void testInventoryAddedOnItemRemoved() {
		Material g4560;
		Long g4560InventoryBefore = Long.valueOf(0), g4560InventoryAfter = Long.valueOf(0);
		SalesOrderService orderService = new SalesOrderService();
		
		//Get material inventory before an item is removed from an existing sales order.
		try {
			g4560 = materialDAO.getMaterial(this.g4560.getId());
			g4560InventoryBefore = g4560.getInventory();
		
			//Remove the item from an existing sales order
			this.order2.getItems().remove(1);
			orderService.updateSalesOrder(this.convertToWsOrder(this.order2));
			
			//Get material inventory after the item has been removed.
			g4560 = materialDAO.getMaterial(this.g4560.getId());
			g4560InventoryAfter = g4560.getInventory();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		//Check if the material inventory has been increased by the quantity of the removed item.
		assertTrue(g4560InventoryAfter == (g4560InventoryBefore + this.orderItem22.getQuantity()));
	}
	
	
	@Test
	/**
	 * Tests if the material inventory is increased if the ordered quantity of an existing order item is reduced.
	 */
	public void testInventoryAddedOnOrderedQuantityReduced() {
		Material rx570;
		SalesOrderItem item;
		Long rx570InventoryBefore = Long.valueOf(0), rx570InventoryAfter = Long.valueOf(0);
		Long quantitiyReduced = Long.valueOf(1);
		SalesOrderService orderService = new SalesOrderService();
		
		//Get the material inventory before the ordered quantity is changed.
		try {
			rx570 = materialDAO.getMaterial(this.rx570.getId());
			rx570InventoryBefore = rx570.getInventory();

			//Change the ordered quantity
			item = this.order2.getItemWithId(this.orderItem21.getId());
			item.setQuantity(item.getQuantity() - quantitiyReduced);
			orderService.updateSalesOrder(this.convertToWsOrder(this.order2));
			
			//Get the material inventory after the ordered quantity has been changed.
			rx570 = materialDAO.getMaterial(this.rx570.getId());
			rx570InventoryAfter = rx570.getInventory();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		//Check if the material inventory has been increased by the reduced ordered quantity.
		assertTrue(rx570InventoryAfter == (rx570InventoryBefore + quantitiyReduced));
	}
	
	
	/**
	 * Converts a sales order to the lean WebService representation.
	 * 
	 * @param order The sales order to be converted.
	 * @return The lean WebService representation of the sales order.
	 */
	private SalesOrderWS convertToWsOrder(SalesOrder order) {
		SalesOrderWS wsOrder = new SalesOrderWS();
		
		//Head level
		wsOrder.setSalesOrderId(order.getId());
		wsOrder.setOrderDate(order.getOrderDate());
		wsOrder.setRequestedDeliveryDate(order.getRequestedDeliveryDate());
		wsOrder.setStatus(order.getStatus());
		
		if(order.getSoldToParty() != null)
			wsOrder.setSoldToId(order.getSoldToParty().getId());
		
		if(order.getShipToParty() != null)
			wsOrder.setShipToId(order.getShipToParty().getId());
		
		if(order.getBillToParty() != null)
			wsOrder.setBillToId(order.getBillToParty().getId());
		
		if(order.getPaymentAccount() != null)
			wsOrder.setPaymentAccountId(order.getPaymentAccount().getId());
		
		//Item level
		for(SalesOrderItem orderItem:order.getItems()) {
			SalesOrderItemWS wsOrderItem = new SalesOrderItemWS();
			wsOrderItem.setItemId(orderItem.getId());
			wsOrderItem.setMaterialId(orderItem.getMaterial().getId());
			wsOrderItem.setQuantity(orderItem.getQuantity());
			wsOrderItem.setPriceTotal(orderItem.getPriceTotal());
			wsOrder.addItem(wsOrderItem);
		}
		
		return wsOrder;
	}
}
