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
import backend.model.businessPartner.BusinessPartner;
import backend.model.businessPartner.BusinessPartnerType;
import backend.model.material.Material;
import backend.model.material.MaterialArray;
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
 * Tests the MaterialService.
 * 
 * @author Michael
 */
public class MaterialServiceTest {
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources = ResourceBundle.getBundle("backend");	
	
	/**
	 * DAO to access material data.
	 */
	private static MaterialDao materialDAO;
	
	/**
	 * DAO to access business partner data.
	 */
	private static BusinessPartnerDao partnerDAO;
	
	/**
	 * DAO to access sales order data.
	 */
	private static SalesOrderDao salesOrderDAO;
	
	/**
	 * DAO to access purchase order data.
	 */
	private static PurchaseOrderDao purchaseOrderDAO;
	
	/**
	 * DAO to access account data.
	 */
	private static AccountDao accountDAO;
	
	/**
	 * The sales order that is used for certain test cases.
	 */
	private SalesOrder salesOrder;
	
	/**
	 * The sales order item under test.
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
	
	/**
	 * Test material: AMD RX570 GPU.
	 */
	protected Material rx570;
	
	/**
	 * Test material: Intel Pentium G4560.
	 */
	protected Material g4560;
	
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
		accountDAO = DAOManager.getInstance().getAccountDAO();
		salesOrderDAO = DAOManager.getInstance().getSalesOrderDAO();
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
		this.createDummyMaterials();
	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	protected void tearDown() {
		this.deleteDummyMaterials();
	}
	
	
	/**
	 * Initializes the database with dummy materials.
	 */
	private void createDummyMaterials() {
		this.rx570 = new Material();
		this.rx570.setName("AMD RX570");
		this.rx570.setDescription("Graphic card released in april 2017. It uses the Polaris 20 XL chip.");
		this.rx570.setUnit(UnitOfMeasurement.ST);
		this.rx570.setPricePerUnit(BigDecimal.valueOf(Double.valueOf(189.99)));
		this.rx570.setCurrency(Currency.EUR);
		this.rx570.setInventory(Long.valueOf(10));
		this.rx570.setImage(null);
		
		this.g4560 = new Material();
		this.g4560.setName("Pentium G4560");
		this.g4560.setDescription("Desktop processor that has 2 cores / 4 threads. Released in january 2017. Has 54W TDP.");
		this.g4560.setUnit(UnitOfMeasurement.ST);
		this.g4560.setPricePerUnit(BigDecimal.valueOf(Double.valueOf(54.99)));
		this.g4560.setCurrency(Currency.EUR);
		this.g4560.setInventory(Long.valueOf(25));
		this.g4560.setImage(null);
		
		try {
			materialDAO.insertMaterial(this.rx570);
			materialDAO.insertMaterial(this.g4560);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Removes dummy materials from the database.
	 */
	private void deleteDummyMaterials() {
		try {
			materialDAO.deleteMaterial(this.rx570);
			materialDAO.deleteMaterial(this.g4560);
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
		this.salesOrder.setSoldToParty(this.partner);
		this.salesOrder.setShipToParty(this.partner);
		this.salesOrder.setBillToParty(this.partner);
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
		this.purchaseOrder.setVendor(this.partner);
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
	 * Tests adding of a new material.
	 */
	public void testAddValidMaterial() {
		Material newMaterial = new Material();
		Material addedMaterial;
		WebServiceResult addMaterialResult;
		
		//Define the new material
		newMaterial.setName("New Material");
		newMaterial.setDescription("A new material that is used in this test");
		newMaterial.setUnit(UnitOfMeasurement.KG);
		newMaterial.setPricePerUnit(BigDecimal.valueOf(Double.valueOf(0.29)));
		newMaterial.setCurrency(Currency.EUR);
		newMaterial.setInventory(Long.valueOf(2000));
		newMaterial.setImage(null);
		
		//Add a new material to the database via WebService
		MaterialService materialService = new MaterialService();
		addMaterialResult = materialService.addMaterial(newMaterial.getWsMaterial());
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(addMaterialResult) == false);
		
		//There should be a success message
		assertTrue(addMaterialResult.getMessages().size() == 1);
		assertTrue(addMaterialResult.getMessages().get(0).getType() == WebServiceMessageType.S);
		
		//The ID of the newly created material order should be provided in the data part of the WebService return.
		assertNotNull(addMaterialResult.getData());
		assertTrue(addMaterialResult.getData() instanceof Integer);
		newMaterial.setId((Integer) addMaterialResult.getData());
		
		//Read the persisted material via DAO
		try {
			addedMaterial = materialDAO.getMaterial(newMaterial.getId());
			
			//Check if the material read by the DAO equals the material inserted using the WebService in each attribute.
			assertEquals(newMaterial.getId(), addedMaterial.getId());
			assertEquals(newMaterial.getName(), addedMaterial.getName());
			assertEquals(newMaterial.getDescription(), addedMaterial.getDescription());
			assertEquals(newMaterial.getUnit(), addedMaterial.getUnit());
			assertEquals(newMaterial.getPricePerUnit(), addedMaterial.getPricePerUnit());
			assertEquals(newMaterial.getCurrency(), addedMaterial.getCurrency());
			assertEquals(newMaterial.getInventory(), addedMaterial.getInventory());
		} catch (Exception e) {
			fail(e.getMessage());
		}
		finally {
			//Delete the newly added material
			try {
				materialDAO.deleteMaterial(newMaterial);
			} 
			catch (Exception e) {
				fail(e.getMessage());
			}
		}
	}
	
	
	@Test
	/**
	 * Tests deletion of a material.
	 */
	public void testDeleteMaterial() {
		WebServiceResult deleteMaterialResult;
		Material deletedMaterial;
		
		//Delete material rx570 using the WebService
		MaterialService materialService = new MaterialService();
		deleteMaterialResult = materialService.deleteMaterial(this.rx570.getId());
		
		//There should be no error messages
		assertTrue(WebServiceTools.resultContainsErrorMessage(deleteMaterialResult) == false);
		
		//There should be a success message
		assertTrue(deleteMaterialResult.getMessages().size() == 1);
		assertTrue(deleteMaterialResult.getMessages().get(0).getType() == WebServiceMessageType.S);
		
		//Check if material rx570 is missing using the DAO.
		try {
			deletedMaterial = materialDAO.getMaterial(this.rx570.getId());
			
			if(deletedMaterial != null) {
				fail("Material 'rx570' is still persisted but should have been deleted by the WebService operation 'deleteMaterial'.");				
			}
			else {
				//If the material has been successfully deleted then add it again for subsequent test cases.
				this.rx570.setId(null);
				materialDAO.insertMaterial(this.rx570);
			}
				
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests deletion of a material that is used in at least one sales order.
	 */
	public void testDeleteMaterialUsedInSo() {
		String expectedErrorMessage, actualErrorMessage;
		WebServiceResult deleteMaterialResult;
		Material deletedMaterial = null;
		MaterialService materialService = new MaterialService();
		
		try {
			//Create account, partner and sales order that are used for this test case.
			this.createDummyAccount();
			this.createDummyPartner();
			this.createDummySalesOrder();
						
			//Try to delete the material used in the sales order.
			deleteMaterialResult = materialService.deleteMaterial(this.g4560.getId());
			
			//There should be a return message of type E.
			assertTrue(deleteMaterialResult.getMessages().size() == 1);
			assertTrue(deleteMaterialResult.getMessages().get(0).getType() == WebServiceMessageType.E);
			
			//Check the correct error message.
			expectedErrorMessage = MessageFormat.format(this.resources.getString("material.deleteUsedInSalesOrder"), 
					this.g4560.getId(), this.salesOrder.getId());
			actualErrorMessage = deleteMaterialResult.getMessages().get(0).getText();
			assertEquals(expectedErrorMessage, actualErrorMessage);
			
			//Try to read the material that is used in the sales order.
			deletedMaterial = materialDAO.getMaterial(this.g4560.getId());
			
			//Check if the material has not been deleted.
			assertNotNull(deletedMaterial);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		finally {
			//Delete account, partner and sales order that are used for this test case.
			this.deleteDummySalesOrder();
			this.deleteDummyPartner();
			this.deleteDummyAccount();
		}
	}
	
	
	@Test
	/**
	 * Tests deletion of a material that is used in at least one purchase order.
	 */
	public void testDeleteMaterialUsedInPo() {
		String expectedErrorMessage, actualErrorMessage;
		WebServiceResult deleteMaterialResult;
		Material deletedMaterial = null;
		MaterialService materialService = new MaterialService();
		
		try {
			//Create account, partner and purchase order that are used for this test case.
			this.createDummyAccount();
			this.createDummyPartner();
			this.createDummyPurchaseOrder();
						
			//Try to delete the material used in the purchase order.
			deleteMaterialResult = materialService.deleteMaterial(this.g4560.getId());
			
			//There should be a return message of type E.
			assertTrue(deleteMaterialResult.getMessages().size() == 1);
			assertTrue(deleteMaterialResult.getMessages().get(0).getType() == WebServiceMessageType.E);
			
			//Check the correct error message.
			expectedErrorMessage = MessageFormat.format(this.resources.getString("material.deleteUsedInPurchaseOrder"), 
					this.g4560.getId(), this.purchaseOrder.getId());
			actualErrorMessage = deleteMaterialResult.getMessages().get(0).getText();
			assertEquals(expectedErrorMessage, actualErrorMessage);
			
			//Try to read the material that is used in the purchase order.
			deletedMaterial = materialDAO.getMaterial(this.g4560.getId());
			
			//Check if the material has not been deleted.
			assertNotNull(deletedMaterial);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		finally {
			//Delete account, partner and purchase order that are used for this test case.
			this.deleteDummyPurchaseOrder();
			this.deleteDummyPartner();
			this.deleteDummyAccount();
		}
	}

	
	@Test
	/**
	 * Tests updating of an existing material.
	 */
	public void testUpdateMaterial() {
		WebServiceResult updateMaterialResult;
		Material updatedMaterial;
		MaterialService materialService = new MaterialService();
		
		//Update the material rx570
		this.rx570.setInventory(Long.valueOf(15));
		updateMaterialResult = materialService.updateMaterial(this.rx570.getWsMaterial());
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(updateMaterialResult) == false);
		
		//There should be a success message
		assertTrue(updateMaterialResult.getMessages().size() == 1);
		assertTrue(updateMaterialResult.getMessages().get(0).getType() == WebServiceMessageType.S);
		
		//Retrieve the updated material and check if the changes have been persisted.
		try {
			updatedMaterial = materialDAO.getMaterial(this.rx570.getId());
			assertEquals(this.rx570.getInventory(), updatedMaterial.getInventory());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests the retrieval of a material by its ID.
	 */
	public void testGetMaterial() {
		WebServiceResult getMaterialResult;
		Material material;
		
		//Get the material "rx570"
		MaterialService materialService = new MaterialService();
		getMaterialResult = materialService.getMaterial(this.rx570.getId());
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(getMaterialResult) == false);
		
		//Assure that a material is returned
		assertTrue(getMaterialResult.getData() instanceof Material);
		
		material = (Material) getMaterialResult.getData();
		
		//Check each attribute of the material
		assertEquals(material.getId(), this.rx570.getId());
		assertEquals(material.getName(), this.rx570.getName());
		assertEquals(material.getDescription(), this.rx570.getDescription());
		assertEquals(material.getPricePerUnit(), this.rx570.getPricePerUnit());
		assertEquals(material.getCurrency(), this.rx570.getCurrency());
		assertEquals(material.getInventory(), this.rx570.getInventory());
		assertEquals(material.getUnit(), this.rx570.getUnit());
	}
	
	
	@Test
	/**
	 * Tests the retrieval of all materials.
	 */
	public void testGetMaterials() {
		WebServiceResult getMaterialsResult;
		MaterialArray materials;
		Material material;

		//Get all materials
		MaterialService materialService = new MaterialService();
		getMaterialsResult = materialService.getMaterials();
		materials = (MaterialArray) getMaterialsResult.getData();
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(getMaterialsResult) == false);
		
		//Check if two materials are returned
		assertTrue(materials.getMaterials().size() == 2);
		
		//Check both materials by each attribute
		material = materials.getMaterials().get(0);
		assertEquals(material.getId(), this.rx570.getId());
		assertEquals(material.getName(), this.rx570.getName());
		assertEquals(material.getDescription(), this.rx570.getDescription());
		assertEquals(material.getPricePerUnit(), this.rx570.getPricePerUnit());
		assertEquals(material.getCurrency(), this.rx570.getCurrency());
		assertEquals(material.getInventory(), this.rx570.getInventory());
		assertEquals(material.getUnit(), this.rx570.getUnit());
		
		material = materials.getMaterials().get(1);
		assertEquals(material.getId(), this.g4560.getId());
		assertEquals(material.getName(), this.g4560.getName());
		assertEquals(material.getDescription(), this.g4560.getDescription());
		assertEquals(material.getPricePerUnit(), this.g4560.getPricePerUnit());
		assertEquals(material.getCurrency(), this.g4560.getCurrency());
		assertEquals(material.getInventory(), this.g4560.getInventory());
		assertEquals(material.getUnit(), this.g4560.getUnit());;
	}
	
	
	@Test
	/**
	 * Tests adding a material which is invalid.
	 */
	public void testAddInvalidMaterial() {
		Material newMaterial = new Material();
		WebServiceResult addMaterialResult;
		
		//Define the new material
		newMaterial.setName("New Material");
		newMaterial.setDescription("A new material that is used in this test. The length of the description exceeds the maximum length. "
				+ "Therefore adding the material should not be allowed and an exception has to be thrown during the validation of the material. "
				+ "The WebService should prov");
		newMaterial.setUnit(UnitOfMeasurement.KG);
		newMaterial.setPricePerUnit(BigDecimal.valueOf(Double.valueOf(0.29)));
		newMaterial.setCurrency(Currency.EUR);
		newMaterial.setInventory(Long.valueOf(2000));
		
		//Add a new material to the database via WebService
		MaterialService materialService = new MaterialService();
		addMaterialResult = materialService.addMaterial(newMaterial.getWsMaterial());
		
		//There should be a return message of type E
		assertTrue(addMaterialResult.getMessages().size() == 1);
		assertTrue(addMaterialResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//The new Material should not have been persisted
		assertNull(newMaterial.getId());
	}
	
	
	@Test
	/**
	 * Tests updating of a material whose data have not been changed.
	 */
	public void testUpdateUnchangedMaterial() {
		WebServiceResult updateMaterialResult;
		String expectedInfoMessage = MessageFormat.format(this.resources.getString("material.updateUnchanged"), this.g4560.getId());
		String actualInfoMessage;
		
		//Update the unchanged material.
		MaterialService materialService = new MaterialService();
		updateMaterialResult = materialService.updateMaterial(this.g4560.getWsMaterial());
		
		//There should be a return message of type I
		assertTrue(updateMaterialResult.getMessages().size() == 1);
		assertTrue(updateMaterialResult.getMessages().get(0).getType() == WebServiceMessageType.I);
		
		//A proper message should be provided.
		actualInfoMessage = updateMaterialResult.getMessages().get(0).getText();
		assertEquals(expectedInfoMessage, actualInfoMessage);
	}
	
	
	@Test
	/**
	 * Tests updating an existing material with invalid data.
	 */
	public void testUpdateMaterialWithInvalidData() {
		WebServiceResult updateMaterialResult;
		MaterialService materialService = new MaterialService();
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		String actualErrorMessage, expectedErrorMessage;
		
		//Update the material
		this.g4560.setDescription("A new material that is used in this test. The length of the description exceeds the maximum length. "
				+ "Therefore adding the material should not be allowed and an exception has to be thrown during the validation of the material. "
				+ "The WebService should prov");
		expectedErrorMessage = messageProvider.getSizeValidationMessage("material", "description", String.valueOf(this.g4560.getDescription().length()), "0", "250");
		updateMaterialResult = materialService.updateMaterial(this.g4560.getWsMaterial());
		
		//There should be a return message of type E.
		assertTrue(updateMaterialResult.getMessages().size() == 1);
		assertTrue(updateMaterialResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//A proper message should be provided.
		actualErrorMessage = updateMaterialResult.getMessages().get(0).getText();
		assertEquals(expectedErrorMessage, actualErrorMessage);
	}
}
