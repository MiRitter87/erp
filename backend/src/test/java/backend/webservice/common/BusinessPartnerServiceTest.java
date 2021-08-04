package backend.webservice.common;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.dao.BusinessPartnerDao;
import backend.dao.DAOManager;
import backend.model.BusinessPartner;
import backend.model.BusinessPartnerArray;
import backend.model.BusinessPartnerType;
import backend.model.webservice.BPTypeQueryParameter;
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
	 * Test business partner: Amalgamated Moose Pasture.
	 */
	protected BusinessPartner moose;
	
	/**
	 * Test business partner: A Company Making Everything.
	 */
	protected BusinessPartner acme;
	
	
	@BeforeAll
	/**
	 * Tasks to be performed once at startup of test class.
	 */
	public static void setUpClass() {
		businessPartnerDAO = DAOManager.getInstance().getBusinessPartnerDAO();
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
}
