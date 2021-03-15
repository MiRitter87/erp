package backend.webservice.common;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ResourceBundle;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.dao.BusinessPartnerHibernateDao;
import backend.model.BusinessPartner;
import backend.model.webservice.WebServiceMessageType;
import backend.model.webservice.WebServiceResult;
import backend.tools.test.WebServiceTestTools;

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
	private static BusinessPartnerHibernateDao businessPartnerDAO;
	
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
		businessPartnerDAO = new BusinessPartnerHibernateDao();
	}
	
	
	@AfterAll
	/**
	 * Tasks to be performed once at end of test class.
	 */
	public static void tearDownClass() {
		try {
			businessPartnerDAO.close();
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
		
		this.acme = new BusinessPartner();
		this.acme.setCompanyName("A Company Making Everything");
		this.acme.setFirstName("Wile E.");
		this.acme.setLastName("Coyote");
		this.acme.setStreetName("Desert Road");
		this.acme.setHouseNumber("2");
		this.acme.setZipCode("1111");
		this.acme.setCityName("Durango");
		this.acme.setPhoneNumber("+1 456-125-7");
			
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
		
		//Add a new business partner to the database via WebService
		BusinessPartnerService businessPartnerService = new BusinessPartnerService();
		addBusinessPartnerResult = businessPartnerService.addBusinessPartner(newBusinessPartner);
		
		//Assure no error message exists
		assertTrue(WebServiceTestTools.resultContainsErrorMessage(addBusinessPartnerResult) == false);
		
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
		assertTrue(WebServiceTestTools.resultContainsErrorMessage(deleteBusinessPartnerResult) == false);
		
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
		assertTrue(WebServiceTestTools.resultContainsErrorMessage(getBusinessPartnerResult) == false);
		
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
	}
}
