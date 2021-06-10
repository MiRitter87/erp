package backend.webservice.common;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.dao.DAOManager;
import backend.dao.ImageDao;
import backend.model.Image;
import backend.model.webservice.WebServiceMessageType;
import backend.model.webservice.WebServiceResult;
import backend.tools.WebServiceTools;
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
	 * A dummy image for testing purpose.
	 */
	private Image dummyImage;
	
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
		this.dummyImage = new Image();
		this.dummyImage.setData(this.readFile(DUMMY_IMAGE_FILE_PATH));
		
		try {
			imageDAO.insertImage(this.dummyImage);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Removes the dummy image from the database.
	 */
	private void deleteDummyImage() {
		try {
			imageDAO.deleteImage(this.dummyImage);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
     * Reads the file with the given path and returns the byte array.
     * 
     * @param path The path to the file.
     * @return the bytes of the file.
     */
    private byte[] readFile(String path) {
        ByteArrayOutputStream bos = null;
        FileInputStream fis = null;
        
        try {
            File file = new File(path);
            fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fail(fileNotFoundException.getMessage());
        } catch (IOException ioException) {
            fail(ioException.getMessage());
        } finally {
        	if(fis != null) {
        		try {
        			fis.close();
        		} catch (IOException e) {
        			fail(e.getMessage());
        		}        		
        	}
        }
        
        return bos != null ? bos.toByteArray() : null;
    }
    
    
    @Test
    /**
     * Tests the retrieval of an image by its ID.
     */
    public void testGetImage() {
    	WebServiceResult getImageResult;
		Image image;
		
		//Get the dummy image using the service.
		ImageService imageService = new ImageService();
		getImageResult = imageService.getImage(this.dummyImage.getId());
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(getImageResult) == false);
		
		//Assure that an image is returned
		assertTrue(getImageResult.getData() instanceof Image);
		
		image = (Image) getImageResult.getData();
		
		//Check each attribute of the image
		assertEquals(this.dummyImage.getId(), image.getId());
		assertArrayEquals(this.dummyImage.getData(), image.getData());
    }
    
    
    @Test
    /**
     * Tests the deletion of an image by its ID.
     */
    public void testDeleteImage() {
    	WebServiceResult deleteImageResult;
		Image deletedImage;
		
		//Delete dummy image using the WebService
		ImageService imageService = new ImageService();
		deleteImageResult = imageService.deleteImage(this.dummyImage.getId());
		
		//There should be no error messages
		assertTrue(WebServiceTools.resultContainsErrorMessage(deleteImageResult) == false);
		
		//There should be a success message
		assertTrue(deleteImageResult.getMessages().size() == 1);
		assertTrue(deleteImageResult.getMessages().get(0).getType() == WebServiceMessageType.S);
		
		//Check if dummy image is missing using the DAO.
		try {
			deletedImage = imageDAO.getImage(this.dummyImage.getId());
			
			if(deletedImage != null) {
				fail("Dummy image is still persisted but should have been deleted by the WebService operation 'deleteImage'.");				
			}
			else {
				//If the image has been successfully deleted then add it again for subsequent test cases.
				this.dummyImage.setId(null);
				imageDAO.insertImage(this.dummyImage);
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
    	Image newImage = new Image();
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
    	Image newImage = new Image();
    	Image addedImage;
    	
    	//Define the new image.
    	newImage.setData(this.readFile(DUMMY_IMAGE_FILE_PATH));
    	
    	//Add a new image to the database via WebService
    	ImageService imageService = new ImageService();
		addImageResult = imageService.addImage(newImage);
    	
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(addImageResult) == false);
		
		//There should be a success message
		assertTrue(addImageResult.getMessages().size() == 1);
		assertTrue(addImageResult.getMessages().get(0).getType() == WebServiceMessageType.S);
		
		//Read the persisted image via DAO
		try {
			addedImage = imageDAO.getImage(newImage.getId());
			
			//Check if the image read by the DAO equals the image inserted using the WebService in each attribute.
			assertEquals(newImage.getId(), addedImage.getId());
			assertArrayEquals(newImage.getData(), addedImage.getData());
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
}
