package backend.webservice.common;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backend.dao.ImageDao;
import backend.model.Image;
import backend.model.webservice.WebServiceResult;

/**
 * Common implementation of the Image WebService that is used by the SOAP as well as the REST service.
 * 
 * @author Michael
 */
public class ImageService {
	/**
	 * DAO for image access.
	 */
	private ImageDao imageDAO;
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources = ResourceBundle.getBundle("backend");
	
	/**
	 * Application logging.
	 */
	public static final Logger logger = LogManager.getLogger(ImageService.class);
	
	
	/**
	 * Provides the image with the given id.
	 * 
	 * @param id The id of the image.
	 * @return The image with the given id, if found.
	 */
	public WebServiceResult getImage(final Integer id) {
		return null;
	}
	
	
	/**
	 * Adds an image.
	 * 
	 * @param image The image to be added.
	 * @return The result of the add function.
	 */
	public WebServiceResult addImage(final Image image) {
		return null;
	}
	
	
	/**
	 * Deletes the image with the given id.
	 * 
	 * @param id The id of the image to be deleted.
	 * @return The result of the delete function.
	 */
	public WebServiceResult deleteImage(final Integer id) {
		return null;
	}
}
