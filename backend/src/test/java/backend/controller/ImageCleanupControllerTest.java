package backend.controller;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.dao.DAOManager;
import backend.dao.ImageDao;
import backend.dao.MaterialDao;
import backend.model.Currency;
import backend.model.image.ImageData;
import backend.model.image.ImageMetaData;
import backend.model.material.Material;
import backend.model.material.UnitOfMeasurement;
import backend.tools.test.FileReader;

public class ImageCleanupControllerTest {
	/**
	 * DAO to access image data.
	 */
	private static ImageDao imageDAO;

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
	}


	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	protected void tearDown() {
		this.deleteDummyImage();
	}


	/**
	 * Initializes the database with a dummy image.
	 */
	private void createDummyImage() {
		this.dummyImageData = new ImageData();

		try {
			this.dummyImageData.setData(FileReader.readFile(DUMMY_IMAGE_FILE_PATH));

			this.dummyImageMetaData = new ImageMetaData();
			this.dummyImageMetaData.setMimeType("image/png");

			imageDAO.insertImage(this.dummyImageData);

			this.dummyImageMetaData.setId(this.dummyImageData.getId());
			imageDAO.updateImageMetaData(this.dummyImageMetaData);
		} catch (FileNotFoundException fileNotFoundException) {
			fail(fileNotFoundException.getMessage());
		} catch (IOException ioException) {
			fail(ioException.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}


	/**
	 * Removes the dummy image from the database.
	 */
	private void deleteDummyImage() {
		try {
			imageDAO.deleteImage(this.dummyImageData.getId());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}


    @Test
    /**
     * Tests the cleanup method when every image is referenced to a master data object.
     */
    public void testCleanupWithoutObsoleteImages() {
    	Material newMaterial = new Material();
    	ImageCleanupController imageCleanupController = new ImageCleanupController();
    	ImageMetaData databaseImage = null;
    	MaterialDao materialDAO = DAOManager.getInstance().getMaterialDAO();

    	//Define the new material that has the dummy image referenced.
		newMaterial.setName("New Material");
		newMaterial.setDescription("A new material that is used in this test");
		newMaterial.setUnit(UnitOfMeasurement.KG);
		newMaterial.setPricePerUnit(BigDecimal.valueOf(Double.valueOf(0.29)));
		newMaterial.setCurrency(Currency.EUR);
		newMaterial.setInventory(Long.valueOf(2000));
		newMaterial.setImage(this.dummyImageMetaData);

		try {
			//Add the new material to the database via DAO.
			materialDAO.insertMaterial(newMaterial);

			//Cleanup obsolete images. There is only one image and it is referenced by a material. Therefore it should not be cleaned up.
			imageCleanupController.cleanup();

			//Try to get the dummy image via DAO.
			databaseImage = imageDAO.getImageMetaData(this.dummyImageMetaData.getId());

			//Assure that the image is still persisted and has not been deleted by the cleanup method.
			assertTrue(databaseImage != null);
			assertEquals(this.dummyImageMetaData.getId(), databaseImage.getId());
		} catch (Exception exception) {
			fail(exception.getMessage());
		}
		finally {
			try {
				materialDAO.deleteMaterial(newMaterial);
				//Assures tearDown() is executed correctly afterwards because deleting material automatically deletes the referenced image.
				this.createDummyImage();
			} catch (Exception e) {
				fail(e.getMessage());
			}
		}
    }


    @Test
    /**
     * Tests the cleanup method when an image exists that is not referenced to any master data object.
     */
    public void testCleanupWithObsoleteImage() {
    	ImageCleanupController imageCleanupController = new ImageCleanupController();
    	ImageMetaData databaseImage = null;

    	try {
    		//Cleanup obsolete images. The dummy image should be deleted because it is not referenced to any master data object.
			imageCleanupController.cleanup();

			//Try to get the dummy image from the database.
			databaseImage = imageDAO.getImageMetaData(this.dummyImageMetaData.getId());

			//Assure that the dummy image has been deleted.
			assertNull(databaseImage);

			//Add the previously deleted image back to the database to assure the tearDown method can run correctly.
    		this.createDummyImage();
		} catch (Exception exception) {
			fail(exception.getMessage());
		}
    }
}
