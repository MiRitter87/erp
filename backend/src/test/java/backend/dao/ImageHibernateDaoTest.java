package backend.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.model.image.ImageData;
import backend.model.image.ImageMetaData;
import backend.tools.test.FileReader;

/**
 * Tests the ImageHibernateDao
 * 
 * @author Michael
 */
public class ImageHibernateDaoTest {
	/**
	 * DAO to access image data.
	 */
	private static ImageDao imageDAO;
	
	/**
	 * Data of a dummy image for testing purpose.
	 */
	private ImageData dummyImageData1;
	
	/**
	 * Meta data of a dummy image for testing purpose.
	 */
	private ImageMetaData dummyImageMetaData1;
	
	/**
	 * Data of a dummy image for testing purpose.
	 */
	private ImageData dummyImageData2;
	
	/**
	 * Meta data of a dummy image for testing purpose.
	 */
	private ImageMetaData dummyImageMetaData2;
	
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
		this.createDummyImages();
	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	protected void tearDown() {
		this.deleteDummyImages();
	}
	
	
	/**
	 * Initializes the database with a dummy image.
	 */
	private void createDummyImages() {
		this.dummyImageData1 = new ImageData();
		this.dummyImageData2 = new ImageData();
		
		try {
			this.dummyImageData1.setData(FileReader.readFile(DUMMY_IMAGE_FILE_PATH));
			this.dummyImageData2.setData(FileReader.readFile(DUMMY_IMAGE_FILE_PATH));
			
			this.dummyImageMetaData1 = new ImageMetaData();
			this.dummyImageMetaData1.setMimeType("image/png");
			this.dummyImageMetaData2 = new ImageMetaData();
			this.dummyImageMetaData2.setMimeType("image/png");
			
			imageDAO.insertImage(this.dummyImageData1);
			imageDAO.insertImage(this.dummyImageData2);
			
			this.dummyImageMetaData1.setId(this.dummyImageData1.getId());
			imageDAO.updateImageMetaData(this.dummyImageMetaData1);
			this.dummyImageMetaData2.setId(this.dummyImageData2.getId());
			imageDAO.updateImageMetaData(this.dummyImageMetaData2);
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
	private void deleteDummyImages() {
		try {
			imageDAO.deleteImage(this.dummyImageData1.getId());
			imageDAO.deleteImage(this.dummyImageData2.getId());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests the retrieval of all image meta data.
	 */
	public void testGetAllImageMetaData() {
		List<ImageMetaData> allImageMetaData = null;
		ImageMetaData imageMetaData = null;
		
		try {
			//Get the meta data of all images.
			allImageMetaData = imageDAO.getAllImageMetaData();
			
			//Verify that meta data of two images are returned.
			assertNotNull(allImageMetaData);
			assertEquals(2, allImageMetaData.size());
			
			//Verify the data by attribute.
			imageMetaData = allImageMetaData.get(0);
			assertEquals(this.dummyImageMetaData1.getId(), imageMetaData.getId());
			assertEquals(this.dummyImageMetaData1.getMimeType(), imageMetaData.getMimeType());
			
			imageMetaData = allImageMetaData.get(1);
			assertEquals(this.dummyImageMetaData2.getId(), imageMetaData.getId());
			assertEquals(this.dummyImageMetaData2.getMimeType(), imageMetaData.getMimeType());
		} catch (Exception exception) {
			fail(exception.getMessage());
		}
	}
}
