package backend.model.image;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.model.image.ImageMetaData;
import backend.tools.test.ValidationMessageProvider;

/**
 * Tests the image meta data model.
 * 
 * @author Michael
 */
public class ImageMetaDataTest {
	/**
	 * The image meta data under test.
	 */
	private ImageMetaData imageMetaData;
	
	
	@BeforeEach
	/**
	 * Tasks to be performed before each test is run.
	 */
	protected void setUp() {
		this.imageMetaData = new ImageMetaData();
		this.imageMetaData.setId(1);
		this.imageMetaData.setMimeType("image/png");
	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	protected void tearDown() {
		this.imageMetaData = null;
	}
	
	
	@Test
	/**
	 * Tests validation of valid image meta data.
	 */
	public void testValidationSuccess() {
		try {
			this.imageMetaData.validate();
		}
		catch(Exception exception) {
			fail(exception.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests validation of image meta data if the file type is too long.
	 */
	public void testFileTypeTooLong() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.imageMetaData.setMimeType("abcde/abcdfghijklmnopqrstu");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("image", "mimeType", 
				String.valueOf(this.imageMetaData.getMimeType().length()), "1", "25");
		String errorMessage = "";
		
		try {
			this.imageMetaData.validate();
			fail("Validation should have failed because mime type is too long.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of image meta data whose mime type is not given.
	 */
	public void testMimeTypeNotGiven() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.imageMetaData.setMimeType("");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("image", "mimeType", 
				String.valueOf(this.imageMetaData.getMimeType().length()), "1", "25");
		String errorMessage = "";
		
		try {
			this.imageMetaData.validate();
			fail("Validation should have failed because mime type is not given");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
}
