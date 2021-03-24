package backend.webservice.common;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import backend.dao.BusinessPartnerHibernateDao;
import backend.dao.MaterialHibernateDao;
import backend.dao.SalesOrderHibernateDao;
import backend.model.BusinessPartner;
import backend.model.Currency;
import backend.model.Material;
import backend.model.SalesOrder;
import backend.model.SalesOrderItem;
import backend.model.UnitOfMeasurement;

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
	private SalesOrder order;
	
	/**
	 * The sales order item under test.
	 */
	private SalesOrderItem orderItem;
	
	/**
	 * The material that is being ordered.
	 */
	private Material rx570;
	
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
		this.createDummyMaterial();
		this.createDummyPartner();
		this.createDummyOrder();
	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	private void tearDown() {
		this.deleteDummyOrder();
		this.deleteDummyPartner();
		this.deleteDummyMaterial();
	}
	
	
	/**
	 * Initializes the database with a dummy material.
	 */
	private void createDummyMaterial() {
		this.rx570 = new Material();
		this.rx570.setName("AMD RX570");
		this.rx570.setDescription("Graphic card released in april 2017. It uses the Polaris 20 XL chip.");
		this.rx570.setUnit(UnitOfMeasurement.ST);
		this.rx570.setPricePerUnit(BigDecimal.valueOf(Double.valueOf(189.99)));
		this.rx570.setCurrency(Currency.EUR);
		this.rx570.setInventory(Long.valueOf(10));
		
		try {
			materialDAO.insertMaterial(this.rx570);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Initializes the database with a dummy business partner.
	 */
	private void createDummyPartner() {
		this.partner = new BusinessPartner();
		this.partner.setId(Integer.valueOf(1));
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
	private void createDummyOrder() {
		this.orderItem = new SalesOrderItem();
		this.orderItem.setId(1);
		this.orderItem.setMaterial(this.rx570);
		this.orderItem.setQuantity(Long.valueOf(1));
		
		this.order = new SalesOrder();
		this.order.setSoldToParty(this.partner);
		this.order.setShipToParty(this.partner);
		this.order.setBillToParty(this.partner);
		this.order.setOrderDate(new Date());
		this.order.setRequestedDeliveryDate(new Date());
		this.order.addItem(this.orderItem);
		
		try {
			orderDAO.insertSalesOrder(this.order);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Deletes the dummy sales order from the database.
	 */
	private void deleteDummyOrder() {
		try {
			orderDAO.deleteSalesOrder(this.order);
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
	private void deleteDummyMaterial() {
		try {
			materialDAO.deleteMaterial(this.rx570);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
