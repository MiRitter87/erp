package backend.model;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
		this.imageMetaData.setFileType("png");
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
	 * Tests validation of the image meta data if the file type is null.
	 */
	public void testFileTypeIsNull() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();	
		this.imageMetaData.setFileType(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("image", "fileType");
		String errorMessage = "";
		
		try {
			this.imageMetaData.validate();
			fail("Validation should have failed because file type is null.");
		} catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of the image meta data if the file type is not given.
	 */
	public void testFileTypeNotGiven() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();	
		this.imageMetaData.setFileType("");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("image", "fileType", 
				String.valueOf(this.imageMetaData.getFileType().length()), "1", "3");
		String errorMessage = "";
		
		try {
			this.imageMetaData.validate();
			fail("Validation should have failed because file type is not given.");
		} catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
}
