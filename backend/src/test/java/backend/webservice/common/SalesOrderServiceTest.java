package backend.webservice.common;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;

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
import backend.model.SalesOrderItem;
import backend.model.UnitOfMeasurement;
import backend.model.webservice.WebServiceResult;
import backend.tools.test.WebServiceTestTools;

/**
 * Tests the sales order service.
 * 
 * @author Michael
 */
public class SalesOrderServiceTest {
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
		assertTrue(WebServiceTestTools.resultContainsErrorMessage(getSalesOrderResult) == false);
		
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
	
	
	
	/**
	 * Tests the retrieval of all sales orders.
	 */
	public void testGetAllSalesOrders() {
		WebServiceResult getSalesOrderResult;
	}

}
