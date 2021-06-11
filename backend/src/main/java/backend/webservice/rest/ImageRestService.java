package backend.webservice.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import backend.model.Image;
import backend.model.webservice.WebServiceMessage;
import backend.model.webservice.WebServiceMessageType;
import backend.model.webservice.WebServiceResult;
import backend.webservice.common.ImageService;

/**
 * WebService for material access using REST technology.
 * 
 * @author Michael
 */
@Path("/images")
public class ImageRestService {
	/**
	 * Application logging.
	 */
	public static final Logger logger = LogManager.getLogger(ImageRestService.class);
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources = ResourceBundle.getBundle("backend");
	
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public WebServiceResult addImage(@FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("file") FormDataContentDisposition fileDetail) {
		WebServiceResult addImageResult = new WebServiceResult();
		ImageService imageService = new ImageService();		
		Image image;
		
		try {
			image = this.getImageObject(uploadedInputStream);
			addImageResult = imageService.addImage(image);
		} catch (IOException e) {
			addImageResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, this.resources.getString("image.addError")));
			
			logger.error(this.resources.getString("image.addError"), e);
		}
		
		return addImageResult;
	}
	
	
	/**
	 * Provides an image object based on the given input stream.
	 * 
	 * @param uploadedInputStream The input stream containing the image data.
	 * @return An image object with the data of the given input stream.
	 * @throws IOException If an I/O error occurs.
	 */
	private Image getImageObject(final InputStream uploadedInputStream) throws IOException {
		Image image = new Image();
		image.setData(IOUtils.toByteArray(uploadedInputStream));
		
		return image;
	}
}
