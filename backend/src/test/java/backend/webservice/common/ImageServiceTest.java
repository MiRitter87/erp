package backend.webservice.common;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.dao.DAOManager;
import backend.dao.ImageDao;
import backend.model.ImageData;
import backend.model.ImageMetaData;
import backend.model.webservice.WebServiceMessageType;
import backend.model.webservice.WebServiceResult;
import backend.tools.WebServiceTools;
import backend.tools.test.FileReader;
import backend.tools.test.ValidationMessageProvider;

/**
 * Tests the ImageService.
 * 
 * @author Michael
 */
public class ImageServiceTest {
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
			imageDAO.deleteImage(this.dummyImageData);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
    
    
    @Test
    /**
     * Tests the retrieval of an image by its ID.
     */
    public void testGetImageData() {
    	WebServiceResult getImageResult;
		ImageData image;
		
		//Get the dummy image using the service.
		ImageService imageService = new ImageService();
		getImageResult = imageService.getImage(this.dummyImageData.getId());
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(getImageResult) == false);
		
		//Assure that an image is returned
		assertTrue(getImageResult.getData() instanceof ImageData);
		
		image = (ImageData) getImageResult.getData();
		
		//Check each attribute of the image
		assertEquals(this.dummyImageData.getId(), image.getId());
		assertArrayEquals(this.dummyImageData.getData(), image.getData());
    }
    
    
    @Test
    /**
     * Tests the deletion of an image by its ID.
     */
    public void testDeleteImage() {
    	WebServiceResult deleteImageResult;
		ImageData deletedImageData;
		ImageMetaData deletedImageMetaData;
		
		//Delete dummy image using the WebService
		ImageService imageService = new ImageService();
		deleteImageResult = imageService.deleteImage(this.dummyImageData.getId());
		
		//There should be no error messages
		assertTrue(WebServiceTools.resultContainsErrorMessage(deleteImageResult) == false);
		
		//There should be a success message
		assertTrue(deleteImageResult.getMessages().size() == 1);
		assertTrue(deleteImageResult.getMessages().get(0).getType() == WebServiceMessageType.S);
		
		//Check if dummy image is missing using the DAO.
		try {
			deletedImageData = imageDAO.getImageData(this.dummyImageData.getId());
			deletedImageMetaData = imageDAO.getImageMetaData(this.dummyImageData.getId());
			
			if(deletedImageData != null || deletedImageMetaData != null) {
				fail("Dummy image is still persisted but should have been deleted by the WebService operation 'deleteImage'.");				
			}
			else {
				//If the image has been successfully deleted then add it again for subsequent test cases.
				this.dummyImageData.setId(null);
				imageDAO.insertImage(this.dummyImageData);
			}
				
		} catch (Exception e) {
			fail(e.getMessage());
		}
    }
    
    
    @Test
    /**
     * Tests adding of an image that is invalid (data part being null).
     */
    public void testAddInvalidImage() {
    	ImageData newImage = new ImageData();
		WebServiceResult addImageResult;
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		String actualErrorMessage, expectedErrorMessage;
		
		//Define the new image
		newImage.setData(null);
		
		//Add a new image to the database via WebService
		ImageService imageService = new ImageService();
		addImageResult = imageService.addImage(newImage);
		
		//There should be a return message of type E
		assertTrue(addImageResult.getMessages().size() == 1);
		assertTrue(addImageResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//A proper message should be provided.
		actualErrorMessage = addImageResult.getMessages().get(0).getText();
		expectedErrorMessage = messageProvider.getNotNullValidationMessage("image", "data");
		assertEquals(expectedErrorMessage, actualErrorMessage);
		
		//The new image should not have been persisted
		assertNull(newImage.getId());
    }
    
    
    @Test
    /**
     * Tests adding of a valid image.
     */
    public void testAddValidImage() {
    	WebServiceResult addImageResult;
    	ImageData newImage = new ImageData();
    	ImageData addedImageData;
    	
    	//Define the new image.
    	try {
			newImage.setData(FileReader.readFile(DUMMY_IMAGE_FILE_PATH));
    	} catch (FileNotFoundException fileNotFoundException) {
			fail(fileNotFoundException.getMessage());
		} catch (IOException ioException) {
			fail(ioException.getMessage());
		}
    	
    	//Add a new image to the database via WebService
    	ImageService imageService = new ImageService();
		addImageResult = imageService.addImage(newImage);
    	
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(addImageResult) == false);
		
		//There should be a success message
		assertTrue(addImageResult.getMessages().size() == 1);
		assertTrue(addImageResult.getMessages().get(0).getType() == WebServiceMessageType.S);
		
		//The ID of the newly created image should be provided in the data part of the result message.
		assertNotNull(addImageResult.getData());
		assertTrue(addImageResult.getData() instanceof Integer);
		assertEquals(newImage.getId(), addImageResult.getData());
		
		//Read the persisted image via DAO
		try {
			addedImageData = imageDAO.getImageData(newImage.getId());
			
			//Check if the image read by the DAO equals the image inserted using the WebService in each attribute.
			assertEquals(newImage.getId(), addedImageData.getId());
			assertArrayEquals(newImage.getData(), addedImageData.getData());
		} catch (Exception e) {
			fail(e.getMessage());
		}
		finally {
			//Delete the newly added image
			try {
				imageDAO.deleteImage(newImage);
			} 
			catch (Exception e) {
				fail(e.getMessage());
			}
		}
    }
    
    
    @Test
    /**
     * Tests updating of image meta data.
     */
    public void testUpdateMetaData() {
    	WebServiceResult updateMetaDataResult;
    	ImageMetaData imageMetaData;
    	ImageMetaData updatedImageMetaData;
    	ImageService imageService = new ImageService();
    	
    	//Update the meta data.
    	imageMetaData = new ImageMetaData();
    	imageMetaData.setId(this.dummyImageData.getId());
    	imageMetaData.setMimeType("image/jpg");
    	updateMetaDataResult = imageService.updateImageMetaData(imageMetaData);
    	
    	//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(updateMetaDataResult) == false);
		
		//There should be a success message
		assertTrue(updateMetaDataResult.getMessages().size() == 1);
		assertTrue(updateMetaDataResult.getMessages().get(0).getType() == WebServiceMessageType.S);
		
		//Retrieve the updated image meta data and check if the changes have been persisted.
		try {
			updatedImageMetaData = imageDAO.getImageMetaData(this.dummyImageData.getId());
			assertEquals(imageMetaData.getMimeType(), updatedImageMetaData.getMimeType());
		} catch (Exception e) {
			fail(e.getMessage());
		}
    }
    
    
    @Test
    /**
     * Tests the retrieval of image meta data.
     */
    public void testGetImageMetaData() {
    	WebServiceResult getMetaDataResult;
    	ImageMetaData imageMetaData;
    	ImageService imageService = new ImageService();
    	
    	//Get image meta data via WebService
    	getMetaDataResult = imageService.getImageMetaData(this.dummyImageData.getId());
    	
    	//Assure no error message exists
    	assertTrue(WebServiceTools.resultContainsErrorMessage(getMetaDataResult) == false);
    	
    	//Assure that image meta data are returned.
    	assertTrue(getMetaDataResult.getData() instanceof ImageMetaData);
    	
    	imageMetaData = (ImageMetaData) getMetaDataResult.getData();
    	
    	//Check each attribute of the image meta data.
		assertEquals(this.dummyImageMetaData.getId(), imageMetaData.getId());
		assertEquals(this.dummyImageMetaData.getMimeType(), imageMetaData.getMimeType());
    }
}
