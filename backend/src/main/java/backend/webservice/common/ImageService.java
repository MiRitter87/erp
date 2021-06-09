package backend.webservice.common;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backend.dao.DAOManager;
import backend.dao.ImageDao;
import backend.model.Image;
import backend.model.webservice.WebServiceMessage;
import backend.model.webservice.WebServiceMessageType;
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
		Image image = null;
		WebServiceResult getImageResult = new WebServiceResult(null);
		
		try {
			this.imageDAO = DAOManager.getInstance().getImageDAO();
			image = this.imageDAO.getImage(id);
			
			if(image != null) {
				//Image found
				getImageResult.setData(image);
			}
			else {
				//Image not found
				getImageResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
						MessageFormat.format(this.resources.getString("image.notFound"), id)));
			}
		}
		catch (Exception e) {
			getImageResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
					MessageFormat.format(this.resources.getString("image.getError"), id)));
			
			logger.error(MessageFormat.format(this.resources.getString("image.getError"), id), e);
		}
		
		return getImageResult;
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