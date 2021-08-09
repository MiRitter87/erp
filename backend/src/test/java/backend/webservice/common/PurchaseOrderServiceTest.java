package backend.webservice.common;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import backend.dao.BusinessPartnerDao;
import backend.dao.DAOManager;
import backend.dao.MaterialDao;
import backend.dao.PurchaseOrderDao;
import backend.model.BusinessPartner;
import backend.model.BusinessPartnerType;
import backend.model.Currency;
import backend.model.Material;
import backend.model.PurchaseOrder;
import backend.model.PurchaseOrderItem;
import backend.model.PurchaseOrderStatus;
import backend.model.UnitOfMeasurement;

/**
 * Tests the purchase order service.
 * 
 * @author Michael
 */
public class PurchaseOrderServiceTest {
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
		this.order1.setStatus(PurchaseOrderStatus.OPEN);
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
		this.order2.setStatus(PurchaseOrderStatus.OPEN);
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
}
