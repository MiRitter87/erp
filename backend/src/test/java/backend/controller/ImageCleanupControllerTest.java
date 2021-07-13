package backend.controller;

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

import backend.dao.DAOManager;
import backend.dao.ImageDao;
import backend.model.ImageData;
import backend.model.ImageMetaData;

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
		this.dummyImageData.setData(this.readFile(DUMMY_IMAGE_FILE_PATH));
		
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
}
