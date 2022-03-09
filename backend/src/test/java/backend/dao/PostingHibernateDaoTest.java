package backend.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.model.Currency;
import backend.model.account.Posting;
import backend.model.account.PostingType;
import backend.model.businessPartner.BusinessPartner;
import backend.model.businessPartner.BusinessPartnerType;

/**
 * Tests the PostingHibernateDao.
 * 
 * @author Michael
 */
public class PostingHibernateDaoTest {
	/**
	 * DAO to access posting data.
	 */
	private static PostingDao postingDao;
	
	/**
	 * DAO to access business partner data.
	 */
	private static BusinessPartnerDao partnerDao;
	
	/**
	 * The posting under test.
	 */
	private Posting posting;
	
	/**
	 * The counterparty of the posting.
	 */
	private BusinessPartner counterparty;
	
	
	@BeforeAll
	/**
	 * Tasks to be performed once at startup of test class.
	 */
	public static void setUpClass() {
		postingDao = DAOManager.getInstance().getPostingDAO();
		partnerDao = DAOManager.getInstance().getBusinessPartnerDAO();
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
		this.createDummyCounterparty();
		this.createDummyPosting();
	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	protected void tearDown() {
		this.deleteDummyPosting();
		this.deleteDummyCounterparty();
	}
	
	
	/**
	 * Initializes the database with a dummy counterparty.
	 */
	private void createDummyCounterparty() {
		this.counterparty = new BusinessPartner();
		this.counterparty.setCompanyName("A Company Making Everything");
		this.counterparty.setFirstName("Wile E.");
		this.counterparty.setLastName("Coyote");
		this.counterparty.setStreetName("Desert Road");
		this.counterparty.setHouseNumber("2");
		this.counterparty.setZipCode("1111");
		this.counterparty.setCityName("Durango");
		this.counterparty.setPhoneNumber("+1 456-125-7");
		this.counterparty.addType(BusinessPartnerType.VENDOR);
		
		try {
			partnerDao.insertBusinessPartner(this.counterparty);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Initializes the database with a dummy posting.
	 */
	private void createDummyPosting() {
		this.posting = new Posting();
		this.posting.setType(PostingType.DISBURSAL);
		this.posting.setTimestamp(new Date());
		this.posting.setCounterparty(this.counterparty);
		this.posting.setReferenceNumber("A1234");
		this.posting.setAmount(BigDecimal.valueOf(99.95));
		this.posting.setCurrency(Currency.EUR);
		
		try {
			postingDao.insertPosting(this.posting);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Removes the dummy counterparty from the database.
	 */
	private void deleteDummyCounterparty() {
		try {
			partnerDao.deleteBusinessPartner(this.counterparty);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Removes the dummy posting from the database.
	 */
	private void deleteDummyPosting() {
		try {
			postingDao.deletePosting(this.posting);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests getting a posting from the database.
	 */
	public void testGetPosting() {
		Posting databasePosting;
		
		try {
			databasePosting = postingDao.getPosting(this.posting.getId());
			
			assertNotNull(databasePosting);
			assertTrue(databasePosting instanceof Posting);
			
			assertEquals(this.posting.getId(), databasePosting.getId());
			assertEquals(this.posting.getType(), databasePosting.getType());
			assertEquals(this.posting.getTimestamp().getTime(), databasePosting.getTimestamp().getTime());
			assertEquals(this.posting.getCounterparty(), databasePosting.getCounterparty());
			assertEquals(this.posting.getReferenceNumber(), databasePosting.getReferenceNumber());
			assertTrue(this.posting.getAmount().compareTo(databasePosting.getAmount()) == 0);
			assertEquals(this.posting.getCurrency(), databasePosting.getCurrency());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests adding a posting to the database.
	 */
	public void testAddPosting() {
		Posting newPosting, databasePosting;
		
		//Define the posting to be added.
		newPosting = new Posting();
		newPosting.setType(PostingType.RECEIPT);
		newPosting.setTimestamp(new Date());
		newPosting.setCounterparty(this.counterparty);
		newPosting.setReferenceNumber("B3");
		newPosting.setAmount(BigDecimal.valueOf(10.00));
		newPosting.setCurrency(Currency.EUR);
		
		try {
			//Add new posting to database.
			postingDao.insertPosting(newPosting);
			
			//Read the added posting and compare the database state with the local object.
			databasePosting = postingDao.getPosting(newPosting.getId());
			
			assertNotNull(databasePosting);
			assertTrue(databasePosting instanceof Posting);
			
			assertEquals(newPosting.getId(), databasePosting.getId());
			assertEquals(newPosting.getType(), databasePosting.getType());
			assertEquals(newPosting.getTimestamp().getTime(), databasePosting.getTimestamp().getTime());
			assertEquals(newPosting.getCounterparty(), databasePosting.getCounterparty());
			assertEquals(newPosting.getReferenceNumber(), databasePosting.getReferenceNumber());
			assertTrue(newPosting.getAmount().compareTo(databasePosting.getAmount()) == 0);
			assertEquals(newPosting.getCurrency(), databasePosting.getCurrency());
			
			//Delete the newly added posting.
		} catch (Exception e) {
			fail(e.getMessage());
		}
		finally {
			try {
				postingDao.deletePosting(newPosting);
			} catch (Exception e) {
				fail(e.getMessage());
			}
		}
	}
	
	
	@Test
	/**
	 * Tests deleting a posting from the database.
	 */
	public void testDeletePosting() {
		Posting deletedPosting = null;
		
		try {
			//Delete posting
			postingDao.deletePosting(this.posting);
			
			//Check if deleted posting does not exist at database anymore.
			deletedPosting = postingDao.getPosting(this.posting.getId());
			assertNull(deletedPosting);
			
		} catch (Exception e) {
			fail(e.getMessage());
		
		}
		finally {
			//Re-insert the previously deleted posting into the database.
			try {
				this.posting.setId(null);
				postingDao.insertPosting(this.posting);
			} catch (Exception e) {
				fail(e.getMessage());
			}
		}	
	}
}
