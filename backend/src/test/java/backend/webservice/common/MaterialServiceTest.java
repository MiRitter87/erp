package backend.webservice.common;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.dao.MaterialHibernateDao;
import backend.model.Currency;
import backend.model.Material;
import backend.model.MaterialArray;
import backend.model.UnitOfMeasurement;
import backend.model.webservice.WebServiceMessageType;
import backend.model.webservice.WebServiceResult;
import backend.tools.test.SoapTestTools;

/**
 * Tests the MaterialService.
 * 
 * @author Michael
 */
public class MaterialServiceTest {
	/**
	 * DAO to access material data.
	 */
	private static MaterialHibernateDao materialDAO;
	
	/**
	 * Test material: AMD RX570 GPU.
	 */
	protected Material rx570;
	
	/**
	 * Test material: Intel Pentium G4560.
	 */
	protected Material g4560;
	
	
	@BeforeAll
	/**
	 * Tasks to be performed once at startup of test class.
	 */
	public static void setUpClass() {
		materialDAO = new MaterialHibernateDao();
	}
	
	
	@AfterAll
	/**
	 * Tasks to be performed once at end of test class.
	 */
	public static void tearDownClass() {
		try {
			materialDAO.close();
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
		
		//Add a new material to the database via WebService
		MaterialService materialService = new MaterialService();
		addMaterialResult = materialService.addMaterial(newMaterial);
		
		//Assure no error message exists
		assertTrue(SoapTestTools.resultContainsErrorMessage(addMaterialResult) == false);
		
		//There should be a success message
		assertTrue(addMaterialResult.getMessages().size() == 1);
		assertTrue(addMaterialResult.getMessages().get(0).getType() == WebServiceMessageType.S);
		
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
		assertTrue(SoapTestTools.resultContainsErrorMessage(deleteMaterialResult) == false);
		
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
	 * Tests updating of an existing material.
	 */
	public void testUpdateMaterial() {
		WebServiceResult updateMaterialResult;
		Material updatedMaterial;
		MaterialService materialService = new MaterialService();
		
		//Update the material rx570
		this.rx570.setInventory(Long.valueOf(15));
		updateMaterialResult = materialService.updateMaterial(this.rx570);
		
		//Assure no error message exists
		assertTrue(SoapTestTools.resultContainsErrorMessage(updateMaterialResult) == false);
		
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
		assertTrue(SoapTestTools.resultContainsErrorMessage(getMaterialResult) == false);
		
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
		assertTrue(SoapTestTools.resultContainsErrorMessage(getMaterialsResult) == false);
		
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
	
	
	/* TODO Add further test cases
	 * 
	 * add material where obligatory attributes are not set
	 * add material where attributes exceed the maximum length
	 * update material where obligatory attributes are not set
	 * update material where attributes exceed the maximum length
	 * update unchanged material
	 */
}
