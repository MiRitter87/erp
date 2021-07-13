package backend.dao;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.model.Currency;
import backend.model.ImageData;
import backend.model.ImageMetaData;
import backend.model.Material;
import backend.model.UnitOfMeasurement;
import backend.tools.test.FileReader;

/**
 * Tests the MaterialHibernateDao;
 * 
 * @author Michael
 */
public class MaterialHibernateDaoTest {
	/**
	 * DAO to access material data.
	 */
	private static MaterialDao materialDAO;
	
	/**
	 * DAO to access image data.
	 */
	private static ImageDao imageDAO;
	
	/**
	 * Test material: AMD RX570 GPU.
	 */
	protected Material rx570;
	
	/**
	 * Test material: Intel Pentium G4560.
	 */
	protected Material g4560;
	
	/**
	 * Data of a dummy image for testing purpose.
	 */
	private ImageData dummyImageData;
	
	/**
	 * Meta data of a dummy image for testing purpose.
	 */
	private ImageMetaData dummyImageMetaData;
	
	/**
	 * Path to the dummy image file.
	 */
	private static final String DUMMY_IMAGE_FILE_PATH = "src/test/resources/dummyImage.png";
	
	
	@BeforeAll
	/**
	 * Tasks to be performed once at startup of test class.
	 */
	public static void setUpClass() {
		imageDAO = DAOManager.getInstance().getImageDAO();
		materialDAO = DAOManager.getInstance().getMaterialDAO();
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
		this.createDummyImage();
		this.createDummyMaterials();
	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	protected void tearDown() {
		this.deleteDummyImage();
		this.deleteDummyMaterials();
	}
	
	
	/**
	 * Initializes the database with a dummy image.
	 */
	private void createDummyImage() {
		this.dummyImageData = new ImageData();
		
		try {
			this.dummyImageData.setData(FileReader.readFile(DUMMY_IMAGE_FILE_PATH));
		} catch (FileNotFoundException fileNotFoundException) {
			fail(fileNotFoundException.getMessage());
		} catch (IOException ioException) {
			fail(ioException.getMessage());
		}
		
		this.dummyImageMetaData = new ImageMetaData();
		this.dummyImageMetaData.setMimeType("image/png");
		
		try {
			imageDAO.insertImage(this.dummyImageData);
			
			this.dummyImageMetaData.setId(this.dummyImageData.getId());
			imageDAO.updateImageMetaData(this.dummyImageMetaData);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Removes the dummy image from the database.
	 */
	private void deleteDummyImage() {
		try {
			//Remove the material->image reference. The image is then deleted automatically afterwards because of orphanRemoval.
			this.g4560.setImage(null);
			materialDAO.updateMaterial(this.g4560);
		} catch (Exception e) {
			fail(e.getMessage());
		}
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
		this.g4560.setImage(this.dummyImageMetaData);
		
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
	 * Tests the retrieval of all image IDs that are linked to any material.
	 */
	public void testGetAllImageIds() {
		
	}
}
