package backend.webservice.common;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.dao.AccountDao;
import backend.dao.BusinessPartnerDao;
import backend.dao.DAOManager;
import backend.dao.MaterialDao;
import backend.dao.PurchaseOrderDao;
import backend.dao.SalesOrderDao;
import backend.model.Currency;
import backend.model.account.Account;
import backend.model.businessPartner.BPTypeQueryParameter;
import backend.model.businessPartner.BusinessPartner;
import backend.model.businessPartner.BusinessPartnerArray;
import backend.model.businessPartner.BusinessPartnerType;
import backend.model.material.Material;
import backend.model.material.UnitOfMeasurement;
import backend.model.purchaseOrder.PurchaseOrder;
import backend.model.purchaseOrder.PurchaseOrderItem;
import backend.model.salesOrder.SalesOrder;
import backend.model.salesOrder.SalesOrderItem;
import backend.model.salesOrder.SalesOrderStatus;
import backend.model.webservice.WebServiceMessageType;
import backend.model.webservice.WebServiceResult;
import backend.tools.WebServiceTools;
import backend.tools.test.ValidationMessageProvider;

/**
 * Tests the BusinessPartnerService.
 * 
 * @author Michael
 */
public class BusinessPartnerServiceTest {
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources = ResourceBundle.getBundle("backend");
	
	/**
	 * DAO to access business partner data.
	 */
	private static BusinessPartnerDao businessPartnerDAO;
	
	/**
	 * DAO to access material data.
	 */
	private static MaterialDao materialDAO;
	
	/**
	 * DAO to access sales order data.
	 */
	private static SalesOrderDao salesOrderDAO;
	
	/**
	 * DAO to access account data.
	 */
	private static AccountDao accountDAO;
	
	/**
	 * DAO to access purchase order data.
	 */
	private static PurchaseOrderDao purchaseOrderDAO;
	
	/**
	 * Test business partner: Amalgamated Moose Pasture.
	 */
	private BusinessPartner moose;
	
	/**
	 * Test business partner: A Company Making Everything.
	 */
	private BusinessPartner acme;
	
	/**
	 * Test material.
	 */
	private Material g4560;
	
	/**
	 * Test sales order.
	 */
	private SalesOrder salesOrder;
	
	/**
	 * Test sales order item.
	 */
	private SalesOrderItem salesOrderItem;
	
	/**
	 * The account for payment settlement.
	 */
	private Account paymentAccount;
	
	/**
	 * The purchase order that is used for certain test cases.
	 */
	private PurchaseOrder purchaseOrder;
	
	/**
	 * The purchase order item under test.
	 */
	private PurchaseOrderItem purchaseOrderItem;
	
	
	@BeforeAll
	/**
	 * Tasks to be performed once at startup of test class.
	 */
	public static void setUpClass() {
		businessPartnerDAO = DAOManager.getInstance().getBusinessPartnerDAO();
		materialDAO = DAOManager.getInstance().getMaterialDAO();
		salesOrderDAO = DAOManager.getInstance().getSalesOrderDAO();
		accountDAO = DAOManager.getInstance().getAccountDAO();
		purchaseOrderDAO = DAOManager.getInstance().getPurchaseOrderDAO();
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
	protected void setUp() {
		this.createDummyBusinessPartners();
	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	protected void tearDown() {
		this.deleteDummyBusinessPartners();
	}
	
	
	/**
	 * Initializes the database with dummy business partners.
	 */
	private void createDummyBusinessPartners() {
		this.moose = new BusinessPartner();
		this.moose.setCompanyName("Amalgamated Moose Pasture");
		this.moose.setFirstName("John");
		this.moose.setLastName("Doe");
		this.moose.setStreetName("Main Street");
		this.moose.setHouseNumber("1a");
		this.moose.setZipCode("12345");
		this.moose.setCityName("Moose City");
		this.moose.setPhoneNumber("+1 123-456-7890");
		this.moose.addType(BusinessPartnerType.CUSTOMER);
		
		this.acme = new BusinessPartner();
		this.acme.setCompanyName("A Company Making Everything");
		this.acme.setFirstName("Wile E.");
		this.acme.setLastName("Coyote");
		this.acme.setStreetName("Desert Road");
		this.acme.setHouseNumber("2");
		this.acme.setZipCode("1111");
		this.acme.setCityName("Durango");
		this.acme.setPhoneNumber("+1 456-125-7");
		this.acme.addType(BusinessPartnerType.VENDOR);
			
		try {
			businessPartnerDAO.insertBusinessPartner(this.moose);
			businessPartnerDAO.insertBusinessPartner(this.acme);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Removes dummy business partners from the database.
	 */
	private void deleteDummyBusinessPartners() {
		try {
			businessPartnerDAO.deleteBusinessPartner(this.moose);
			businessPartnerDAO.deleteBusinessPartner(this.acme);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Initializes the database with dummy materials.
	 */
	private void createDummyMaterial() {
		this.g4560 = new Material();
		this.g4560.setName("Pentium G4560");
		this.g4560.setDescription("Desktop processor that has 2 cores / 4 threads. Released in january 2017. Has 54W TDP.");
		this.g4560.setUnit(UnitOfMeasurement.ST);
		this.g4560.setPricePerUnit(BigDecimal.valueOf(Double.valueOf(54.99)));
		this.g4560.setCurrency(Currency.EUR);
		this.g4560.setInventory(Long.valueOf(25));
		this.g4560.setImage(null);
		
		try {
			materialDAO.insertMaterial(this.g4560);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Removes dummy materials from the database.
	 */
	private void deleteDummyMaterial() {
		try {
			materialDAO.deleteMaterial(this.g4560);
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
	 * Initializes the database with a dummy sales order.
	 */
	private void createDummySalesOrder() {
		GregorianCalendar tomorrow = new GregorianCalendar();
		tomorrow.add(GregorianCalendar.DAY_OF_MONTH, 1);
		
		this.salesOrderItem = new SalesOrderItem();
		this.salesOrderItem.setId(1);
		this.salesOrderItem.setMaterial(this.g4560);
		this.salesOrderItem.setQuantity(Long.valueOf(1));
		
		this.salesOrder = new SalesOrder();
		this.salesOrder.setSoldToParty(this.moose);
		this.salesOrder.setShipToParty(this.acme);
		this.salesOrder.setBillToParty(this.moose);
		this.salesOrder.setPaymentAccount(this.paymentAccount);
		this.salesOrder.setOrderDate(new Date());
		this.salesOrder.setRequestedDeliveryDate(new Date());
		this.salesOrder.setStatus(SalesOrderStatus.OPEN);
		this.salesOrder.addItem(this.salesOrderItem);
		
		try {
			salesOrderDAO.insertSalesOrder(this.salesOrder);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Deletes the dummy sales order from the database.
	 */
	private void deleteDummySalesOrder() {
		try {
			salesOrderDAO.deleteSalesOrder(this.salesOrder);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Initializes the database with a dummy purchase order.
	 */
	private void createDummyPurchaseOrder() {
		GregorianCalendar tomorrow = new GregorianCalendar();
		tomorrow.add(GregorianCalendar.DAY_OF_MONTH, 1);
		
		this.purchaseOrderItem = new PurchaseOrderItem();
		this.purchaseOrderItem.setId(1);
		this.purchaseOrderItem.setMaterial(this.g4560);
		this.purchaseOrderItem.setQuantity(Long.valueOf(1));
		
		this.purchaseOrder = new PurchaseOrder();
		this.purchaseOrder.setVendor(this.acme);
		this.purchaseOrder.setOrderDate(new Date());
		this.purchaseOrder.setRequestedDeliveryDate(new Date());
		this.purchaseOrder.addItem(this.purchaseOrderItem);
		
		try {
			purchaseOrderDAO.insertPurchaseOrder(this.purchaseOrder);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Deletes the dummy purchase order from the database.
	 */
	private void deleteDummyPurchaseOrder() {
		try {
			purchaseOrderDAO.deletePurchaseOrder(this.purchaseOrder);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests adding of a new business partner.
	 */
	public void testAddValidBusinessPartner() {
		BusinessPartner newBusinessPartner = new BusinessPartner();
		BusinessPartner addedBusinessPartner;
		WebServiceResult addBusinessPartnerResult;
		
		//Define the new business partner
		newBusinessPartner.setCompanyName("New BP");
		newBusinessPartner.setFirstName("Max");
		newBusinessPartner.setLastName("Mustermann");
		newBusinessPartner.setStreetName("Musterstraße");
		newBusinessPartner.setHouseNumber("1");
		newBusinessPartner.setZipCode("02345");
		newBusinessPartner.setCityName("Musterstadt");
		newBusinessPartner.setPhoneNumber("04567 1263-0");
		newBusinessPartner.addType(BusinessPartnerType.CUSTOMER);
		
		//Add a new business partner to the database via WebService
		BusinessPartnerService businessPartnerService = new BusinessPartnerService();
		addBusinessPartnerResult = businessPartnerService.addBusinessPartner(newBusinessPartner);
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(addBusinessPartnerResult) == false);
		
		//There should be a success message
		assertTrue(addBusinessPartnerResult.getMessages().size() == 1);
		assertTrue(addBusinessPartnerResult.getMessages().get(0).getType() == WebServiceMessageType.S);
		
		//Read the persisted business partner via DAO
		try {
			addedBusinessPartner = businessPartnerDAO.getBusinessPartner(newBusinessPartner.getId());
			
			//Check if the business partner read by the DAO equals the business partner inserted using the WebService in each attribute.
			assertEquals(newBusinessPartner.getId(), addedBusinessPartner.getId());
			assertEquals(newBusinessPartner.getCompanyName(), addedBusinessPartner.getCompanyName());
			assertEquals(newBusinessPartner.getFirstName(), addedBusinessPartner.getFirstName());
			assertEquals(newBusinessPartner.getLastName(), addedBusinessPartner.getLastName());
			assertEquals(newBusinessPartner.getStreetName(), addedBusinessPartner.getStreetName());
			assertEquals(newBusinessPartner.getHouseNumber(), addedBusinessPartner.getHouseNumber());
			assertEquals(newBusinessPartner.getZipCode(), addedBusinessPartner.getZipCode());
			assertEquals(newBusinessPartner.getCityName(), addedBusinessPartner.getCityName());
			assertEquals(newBusinessPartner.getPhoneNumber(), addedBusinessPartner.getPhoneNumber());
			assertEquals(newBusinessPartner.getTypes(), addedBusinessPartner.getTypes());
		} catch (Exception e) {
			fail(e.getMessage());
		}
		finally {
			//Delete the newly added business partner
			try {
				businessPartnerDAO.deleteBusinessPartner(newBusinessPartner);
			} 
			catch (Exception e) {
				fail(e.getMessage());
			}
		}
	}
	
	
	@Test
	/**
	 * Tests adding a business partner who is invalid.
	 */
	public void testAddInvalidBusinessPartner() {
		BusinessPartner newBusinessPartner = new BusinessPartner();
		WebServiceResult addBusinesPartnerResult;
		
		//Define the new business partner
		newBusinessPartner.setCompanyName("");
		newBusinessPartner.setFirstName("Max");
		newBusinessPartner.setLastName("Mustermann");
		newBusinessPartner.setStreetName("Musterstraße");
		newBusinessPartner.setHouseNumber("1");
		newBusinessPartner.setZipCode("02345");
		newBusinessPartner.setCityName("Musterstadt");
		newBusinessPartner.setPhoneNumber("04567 1263-0");
		
		//Add the new business partner to the database via WebService
		BusinessPartnerService businessPartnerService = new BusinessPartnerService();
		addBusinesPartnerResult = businessPartnerService.addBusinessPartner(newBusinessPartner);
		
		//There should be a return message of type E
		assertTrue(addBusinesPartnerResult.getMessages().size() == 1);
		assertTrue(addBusinesPartnerResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//The new business partner should not have been persisted
		assertNull(newBusinessPartner.getId());
	}
	
	
	@Test
	/**
	 * Tests deletion of a business partner.
	 */
	public void testDeleteBusinessPartner() {
		WebServiceResult deleteBusinessPartnerResult;
		BusinessPartner deletedBusinessPartner;
		
		//Delete business partner 'acme' using the WebService
		BusinessPartnerService businessPartnerService = new BusinessPartnerService();
		deleteBusinessPartnerResult = businessPartnerService.deleteBusinessPartner(this.acme.getId());
		
		//There should be no error messages
		assertTrue(WebServiceTools.resultContainsErrorMessage(deleteBusinessPartnerResult) == false);
		
		//There should be a success message
		assertTrue(deleteBusinessPartnerResult.getMessages().size() == 1);
		assertTrue(deleteBusinessPartnerResult.getMessages().get(0).getType() == WebServiceMessageType.S);
		
		//Check if business partner 'acme' is missing using the DAO.
		try {
			deletedBusinessPartner = businessPartnerDAO.getBusinessPartner(this.acme.getId());
			
			if(deletedBusinessPartner != null) {
				fail("Business partner 'acme' is still persisted but should have been deleted by the WebService operation 'deleteBusinessPartner'.");				
			}
			else {
				//If the business partner has been successfully deleted then add it again for subsequent test cases.
				this.acme.setId(null);
				businessPartnerDAO.insertBusinessPartner(this.acme);
			}
				
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests the retrieval of a business partner by his ID.
	 */
	public void testGetBusinessPartner() {
		WebServiceResult getBusinessPartnerResult;
		BusinessPartner businessPartner;
		
		//Get the business partner 'moose'.
		BusinessPartnerService businessPartnerService = new BusinessPartnerService();
		getBusinessPartnerResult = businessPartnerService.getBusinessPartner(this.moose.getId());
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(getBusinessPartnerResult) == false);
		
		//Assure that a business partner is returned
		assertTrue(getBusinessPartnerResult.getData() instanceof BusinessPartner);
		
		businessPartner = (BusinessPartner) getBusinessPartnerResult.getData();
		
		//Check each attribute of the business partner
		assertEquals(businessPartner.getId(), this.moose.getId());
		assertEquals(businessPartner.getCompanyName(), this.moose.getCompanyName());
		assertEquals(businessPartner.getFirstName(), this.moose.getFirstName());
		assertEquals(businessPartner.getLastName(), this.moose.getLastName());
		assertEquals(businessPartner.getStreetName(), this.moose.getStreetName());
		assertEquals(businessPartner.getHouseNumber(), this.moose.getHouseNumber());
		assertEquals(businessPartner.getZipCode(), this.moose.getZipCode());
		assertEquals(businessPartner.getCityName(), this.moose.getCityName());
		assertEquals(businessPartner.getPhoneNumber(), this.moose.getPhoneNumber());
		assertEquals(businessPartner.getTypes(), this.moose.getTypes());
	}
	
	
	@Test
	/**
	 * Tests the retrieval of all business partners.
	 */
	public void testGetAllBusinessPartners() {
		WebServiceResult getBusinessPartnersResult;
		BusinessPartnerArray businessPartners;
		BusinessPartner businessPartner;

		//Get all business partners.
		BusinessPartnerService businessPartnerService = new BusinessPartnerService();
		getBusinessPartnersResult = businessPartnerService.getBusinessPartners(BPTypeQueryParameter.ALL);
		businessPartners = (BusinessPartnerArray) getBusinessPartnersResult.getData();
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(getBusinessPartnersResult) == false);
		
		//Check if two business partners are returned
		assertTrue(businessPartners.getBusinessPartners().size() == 2);
		
		//Check both business partners by each attribute
		businessPartner = businessPartners.getBusinessPartners().get(0);
		assertEquals(businessPartner.getId(), this.moose.getId());
		assertEquals(businessPartner.getCompanyName(), this.moose.getCompanyName());
		assertEquals(businessPartner.getFirstName(), this.moose.getFirstName());
		assertEquals(businessPartner.getLastName(), this.moose.getLastName());
		assertEquals(businessPartner.getStreetName(), this.moose.getStreetName());
		assertEquals(businessPartner.getHouseNumber(), this.moose.getHouseNumber());
		assertEquals(businessPartner.getZipCode(), this.moose.getZipCode());
		assertEquals(businessPartner.getCityName(), this.moose.getCityName());
		assertEquals(businessPartner.getPhoneNumber(), this.moose.getPhoneNumber());
		assertEquals(businessPartner.getTypes(), this.moose.getTypes());
		
		businessPartner = businessPartners.getBusinessPartners().get(1);
		assertEquals(businessPartner.getId(), this.acme.getId());
		assertEquals(businessPartner.getCompanyName(), this.acme.getCompanyName());
		assertEquals(businessPartner.getFirstName(), this.acme.getFirstName());
		assertEquals(businessPartner.getLastName(), this.acme.getLastName());
		assertEquals(businessPartner.getStreetName(), this.acme.getStreetName());
		assertEquals(businessPartner.getHouseNumber(), this.acme.getHouseNumber());
		assertEquals(businessPartner.getZipCode(), this.acme.getZipCode());
		assertEquals(businessPartner.getCityName(), this.acme.getCityName());
		assertEquals(businessPartner.getPhoneNumber(), this.acme.getPhoneNumber());
		assertEquals(businessPartner.getTypes(), this.acme.getTypes());
	}
	
	
	@Test
	/**
	 * Tests the retrieval of all business partners that are of type customer.
	 */
	public void testGetAllBusinessPartnersCustomer() {
		WebServiceResult getBusinessPartnersResult;
		BusinessPartnerArray businessPartners;
		BusinessPartner businessPartner;

		//Get all business partners of type customer.
		BusinessPartnerService businessPartnerService = new BusinessPartnerService();
		getBusinessPartnersResult = businessPartnerService.getBusinessPartners(BPTypeQueryParameter.CUSTOMER);
		businessPartners = (BusinessPartnerArray) getBusinessPartnersResult.getData();
		
		//Assure no error message exists.
		assertTrue(WebServiceTools.resultContainsErrorMessage(getBusinessPartnersResult) == false);
		
		//Check if one business partner is returned.
		assertTrue(businessPartners.getBusinessPartners().size() == 1);
		
		//Check the business partners by each attribute
		businessPartner = businessPartners.getBusinessPartners().get(0);
		assertEquals(businessPartner.getId(), this.moose.getId());
		assertEquals(businessPartner.getCompanyName(), this.moose.getCompanyName());
		assertEquals(businessPartner.getFirstName(), this.moose.getFirstName());
		assertEquals(businessPartner.getLastName(), this.moose.getLastName());
		assertEquals(businessPartner.getStreetName(), this.moose.getStreetName());
		assertEquals(businessPartner.getHouseNumber(), this.moose.getHouseNumber());
		assertEquals(businessPartner.getZipCode(), this.moose.getZipCode());
		assertEquals(businessPartner.getCityName(), this.moose.getCityName());
		assertEquals(businessPartner.getPhoneNumber(), this.moose.getPhoneNumber());
		assertEquals(businessPartner.getTypes(), this.moose.getTypes());
	}
	
	
	@Test
	/**
	 * Tests the retrieval of all business partners that are of type vendor.
	 */
	public void testGetAllBusinessPartnersVendor() {
		WebServiceResult getBusinessPartnersResult;
		BusinessPartnerArray businessPartners;
		BusinessPartner businessPartner;

		//Get all business partners of type customer.
		BusinessPartnerService businessPartnerService = new BusinessPartnerService();
		getBusinessPartnersResult = businessPartnerService.getBusinessPartners(BPTypeQueryParameter.VENDOR);
		businessPartners = (BusinessPartnerArray) getBusinessPartnersResult.getData();
		
		//Assure no error message exists.
		assertTrue(WebServiceTools.resultContainsErrorMessage(getBusinessPartnersResult) == false);
		
		//Check if one business partner is returned.
		assertTrue(businessPartners.getBusinessPartners().size() == 1);
		
		//Check the business partners by each attribute
		businessPartner = businessPartners.getBusinessPartners().get(0);
		assertEquals(businessPartner.getId(), this.acme.getId());
		assertEquals(businessPartner.getCompanyName(), this.acme.getCompanyName());
		assertEquals(businessPartner.getFirstName(), this.acme.getFirstName());
		assertEquals(businessPartner.getLastName(), this.acme.getLastName());
		assertEquals(businessPartner.getStreetName(), this.acme.getStreetName());
		assertEquals(businessPartner.getHouseNumber(), this.acme.getHouseNumber());
		assertEquals(businessPartner.getZipCode(), this.acme.getZipCode());
		assertEquals(businessPartner.getCityName(), this.acme.getCityName());
		assertEquals(businessPartner.getPhoneNumber(), this.acme.getPhoneNumber());
		assertEquals(businessPartner.getTypes(), this.acme.getTypes());
	}
	
	
	@Test
	/**
	 * Tests updating of an existing business partner.
	 */
	public void testUpdateBusinessPartner() {
		WebServiceResult updateBusinessPartnerResult;
		BusinessPartner updatedBusinessPartner;
		BusinessPartnerService businessPartnerService = new BusinessPartnerService();
		
		//Update the business partner 'acme'.
		this.acme.setHouseNumber("3");
		updateBusinessPartnerResult = businessPartnerService.updateBusinessPartner(this.acme);
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(updateBusinessPartnerResult) == false);
		
		//There should be a success message
		assertTrue(updateBusinessPartnerResult.getMessages().size() == 1);
		assertTrue(updateBusinessPartnerResult.getMessages().get(0).getType() == WebServiceMessageType.S);
		
		//Retrieve the updated business partner and check if the changes have been persisted.
		try {
			updatedBusinessPartner = businessPartnerDAO.getBusinessPartner(this.acme.getId());
			assertEquals(this.acme.getHouseNumber(), updatedBusinessPartner.getHouseNumber());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests updating an existing business partner with invalid data.
	 */
	public void testUpdateBusinessPartnerWithInvalidData() {
		WebServiceResult updateBusinessPartnerResult;
		BusinessPartnerService businessPartnerService = new BusinessPartnerService();
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		String actualErrorMessage, expectedErrorMessage;
		
		//Update the business partner with invalid data.
		this.acme.setCompanyName("");
		expectedErrorMessage = messageProvider.getSizeValidationMessage("businessPartner", "companyName", 
				String.valueOf(this.acme.getCompanyName().length()), "1", "100");
		updateBusinessPartnerResult = businessPartnerService.updateBusinessPartner(this.acme);
		
		//There should be a return message of type E.
		assertTrue(updateBusinessPartnerResult.getMessages().size() == 1);
		assertTrue(updateBusinessPartnerResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//A proper message should be provided.
		actualErrorMessage = updateBusinessPartnerResult.getMessages().get(0).getText();
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
	
	
	@Test
	/**
	 * Tests updating of a business partner whose data have not been changed.
	 */
	public void testUpdateUnchangedBusinessPartner() {
		WebServiceResult updateBusinessPartnerResult;
		String expectedInfoMessage = MessageFormat.format(this.resources.getString("businessPartner.updateUnchanged"), this.acme.getId());
		String actualInfoMessage;
		
		//Update the unchanged business partner.
		BusinessPartnerService businessPartnerService = new BusinessPartnerService();
		updateBusinessPartnerResult = businessPartnerService.updateBusinessPartner(this.acme);
		
		//There should be a return message of type I
		assertTrue(updateBusinessPartnerResult.getMessages().size() == 1);
		assertTrue(updateBusinessPartnerResult.getMessages().get(0).getType() == WebServiceMessageType.I);
		
		//A proper message should be provided.
		actualInfoMessage = updateBusinessPartnerResult.getMessages().get(0).getText();
		assertEquals(expectedInfoMessage, actualInfoMessage);
	}
	
	
	@Test
	/**
	 * Tests deletion of a business partner that is used in at least one sales order.
	 */
	public void testDeleteBpUsedInSalesOrder() {
		String expectedErrorMessage, actualErrorMessage;
		WebServiceResult deletePartnerResult;
		BusinessPartner deletedPartner = null;
		BusinessPartnerService partnerService = new BusinessPartnerService();
		
		try {
			//Create account, material and sales order that are used for this test case.
			this.createDummyAccount();
			this.createDummyMaterial();
			this.createDummySalesOrder();
						
			//Try to delete the partner used in the sales order.
			deletePartnerResult = partnerService.deleteBusinessPartner(this.acme.getId());
			
			//There should be a return message of type E.
			assertTrue(deletePartnerResult.getMessages().size() == 1);
			assertTrue(deletePartnerResult.getMessages().get(0).getType() == WebServiceMessageType.E);
			
			//Check the correct error message.
			expectedErrorMessage = MessageFormat.format(this.resources.getString("businessPartner.deleteUsedInSalesOrder"), 
					this.acme.getId(), this.salesOrder.getId());
			actualErrorMessage = deletePartnerResult.getMessages().get(0).getText();
			assertEquals(expectedErrorMessage, actualErrorMessage);
			
			//Try to read the business partner that is used in the sales order.
			deletedPartner = businessPartnerDAO.getBusinessPartner(this.acme.getId());
			
			//Check if the business partner has not been deleted.
			assertNotNull(deletedPartner);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		finally {
			//Delete account, material and sales order that are used for this test case.
			this.deleteDummySalesOrder();
			this.deleteDummyMaterial();
			this.deleteDummyAccount();
		}
	}
	
	
	@Test
	/**
	 * Tests deletion of a business partner that is used in at least one purchase order.
	 */
	public void testDeleteBpUsedInPurchaseOrder() {
		String expectedErrorMessage, actualErrorMessage;
		WebServiceResult deletePartnerResult;
		BusinessPartner deletedPartner = null;
		BusinessPartnerService partnerService = new BusinessPartnerService();
		
		try {
			//Create account, material and purchase order that are used for this test case.
			this.createDummyAccount();
			this.createDummyMaterial();
			this.createDummyPurchaseOrder();
						
			//Try to delete the business partner used in the purchase order.
			deletePartnerResult = partnerService.deleteBusinessPartner(this.acme.getId());
			
			//There should be a return message of type E.
			assertTrue(deletePartnerResult.getMessages().size() == 1);
			assertTrue(deletePartnerResult.getMessages().get(0).getType() == WebServiceMessageType.E);
			
			//Check the correct error message.
			expectedErrorMessage = MessageFormat.format(this.resources.getString("businessPartner.deleteUsedInPurchaseOrder"), 
					this.acme.getId(), this.purchaseOrder.getId());
			actualErrorMessage = deletePartnerResult.getMessages().get(0).getText();
			assertEquals(expectedErrorMessage, actualErrorMessage);
			
			//Try to read the business partner that is used in the purchase order.
			deletedPartner = businessPartnerDAO.getBusinessPartner(this.acme.getId());
			
			//Check if the business partner has not been deleted.
			assertNotNull(deletedPartner);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		finally {
			//Delete account, partner and purchase order that are used for this test case.
			this.deleteDummyPurchaseOrder();
			this.deleteDummyMaterial();
			this.deleteDummyAccount();
		}
	}
	
	
	/*
	 * TODO Implement additional tests
	 * 
	 * testDeleteBpUsedInBooking 
	 */
}
