package backend.webservice.common;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backend.dao.DAOManager;
import backend.dao.ImageDao;
import backend.exception.ObjectUnchangedException;
import backend.model.image.ImageData;
import backend.model.image.ImageMetaData;
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
		ImageData image = null;
		WebServiceResult getImageResult = new WebServiceResult(null);
		
		try {
			this.imageDAO = DAOManager.getInstance().getImageDAO();
			image = this.imageDAO.getImageData(id);
			
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
	public WebServiceResult addImage(final ImageData image) {
		WebServiceResult addImageResult = new WebServiceResult(null);
		this.imageDAO = DAOManager.getInstance().getImageDAO();
		
		//Validate the given image.
		try {
			image.validate();
		} catch (Exception validationException) {
			addImageResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, validationException.getMessage()));
			return addImageResult;
		}
		
		//Insert image if validation is successful.
		try {
			this.imageDAO.insertImage(image);
			addImageResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.S, this.resources.getString("image.addSuccess")));
			addImageResult.setData(image.getId());
		} catch (Exception e) {
			addImageResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, this.resources.getString("image.addError")));
			
			logger.error(this.resources.getString("image.addError"), e);
		}
		
		return addImageResult;
	}
	
	
	/**
	 * Deletes the image with the given id.
	 * 
	 * @param id The id of the image to be deleted.
	 * @return The result of the delete function.
	 */
	public WebServiceResult deleteImage(final Integer id) {
		ImageData image = null;
		WebServiceResult deleteImageResult = new WebServiceResult(null);
		
		//Check if a material with the given code exists.
		try {
			this.imageDAO = DAOManager.getInstance().getImageDAO();
			image = this.imageDAO.getImageData(id);
			
			if(image != null) {
				//Delete image if exists.
				this.imageDAO.deleteImage(id);
				deleteImageResult.addMessage(new WebServiceMessage(WebServiceMessageType.S, 
						MessageFormat.format(this.resources.getString("image.deleteSuccess"), id)));
			}
			else {
				//Image not found.
				deleteImageResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
						MessageFormat.format(this.resources.getString("image.notFound"), id)));
			}
		}
		catch (Exception e) {
			deleteImageResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
					MessageFormat.format(this.resources.getString("image.deleteError"), id)));
			
			logger.error(MessageFormat.format(this.resources.getString("image.deleteError"), id), e);
		}
				
		return deleteImageResult;
	}
	
	
	/**
	 * Updates existing image meta data.
	 * 
	 * @param imageMetaData The image meta data to be updated.
	 * @return The result of the update function.
	 */
	public WebServiceResult updateImageMetaData(final ImageMetaData imageMetaData) {
		WebServiceResult updateImageMetaDataResult = new WebServiceResult(null);
		this.imageDAO = DAOManager.getInstance().getImageDAO();
		
		//At first validate the given image meta data.
		try {
			imageMetaData.validate();
		}
		catch(Exception e) {
			updateImageMetaDataResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, e.getMessage()));
			return updateImageMetaDataResult;
		}
		
		try {
			this.imageDAO.updateImageMetaData(imageMetaData);
			updateImageMetaDataResult.addMessage(new WebServiceMessage(WebServiceMessageType.S, 
					MessageFormat.format(this.resources.getString("image.metaData.updateSuccess"), imageMetaData.getId())));
		} 
		catch(ObjectUnchangedException objectUnchangedException) {
			updateImageMetaDataResult.addMessage(new WebServiceMessage(WebServiceMessageType.I, 
					MessageFormat.format(this.resources.getString("image.metaData.updateUnchanged"), imageMetaData.getId())));
		}
		catch (Exception e) {
			updateImageMetaDataResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
					MessageFormat.format(this.resources.getString("image.metaData.updateError"), imageMetaData.getId())));
			
			logger.error(MessageFormat.format(this.resources.getString("image.metaData.updateError"), imageMetaData.getId()), e);
		}
		
		return updateImageMetaDataResult;
	}
	
	
	/**
	 * Provides the meta data of the image with the given ID.
	 * 
	 * @param id The ID of the image whose meta data are requested.
	 * @return The meta data of the image with the given ID.
	 */
	public WebServiceResult getImageMetaData(final Integer id) {
		ImageMetaData imageMetaData = null;
		WebServiceResult getImageMetaDataResult = new WebServiceResult(null);
		
		try {
			this.imageDAO = DAOManager.getInstance().getImageDAO();
			imageMetaData = this.imageDAO.getImageMetaData(id);
			
			if(imageMetaData != null) {
				//Image meta data found
				getImageMetaDataResult.setData(imageMetaData);
			}
			else {
				//Image meta data not found
				getImageMetaDataResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
						MessageFormat.format(this.resources.getString("image.metaData.notFound"), id)));
			}
		}
		catch (Exception e) {
			getImageMetaDataResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
					MessageFormat.format(this.resources.getString("image.metaData.getError"), id)));
			
			logger.error(MessageFormat.format(this.resources.getString("image.metaData.getError"), id), e);
		}
		
		return getImageMetaDataResult;
	}
}
