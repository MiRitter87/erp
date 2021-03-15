package backend.webservice.common;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ResourceBundle;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import backend.dao.BusinessPartnerHibernateDao;
import backend.model.BusinessPartner;

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
}
