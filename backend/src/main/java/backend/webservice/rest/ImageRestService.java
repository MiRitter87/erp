package backend.webservice.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import backend.model.image.ImageData;
import backend.model.image.ImageMetaData;
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
    public static final Logger LOGGER = LogManager.getLogger(ImageRestService.class);

    /**
     * Access to localized application resources.
     */
    private ResourceBundle resources = ResourceBundle.getBundle("backend");

    /**
     * Adds an image.
     *
     * @param uploadedInputStream The image data.
     * @param fileDetail          Details of the file.
     * @return The result of the add function.
     */
    @POST
    @Path("/data")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult addImage(@FormDataParam("file") final InputStream uploadedInputStream,
            @FormDataParam("file") final FormDataContentDisposition fileDetail) {
        WebServiceResult addImageResult = new WebServiceResult();
        ImageService imageService = new ImageService();
        ImageData image;

        try {
            image = this.getImageObject(uploadedInputStream);
            addImageResult = imageService.addImage(image);
        } catch (IOException e) {
            addImageResult.addMessage(
                    new WebServiceMessage(WebServiceMessageType.E, this.resources.getString("image.addError")));

            LOGGER.error(this.resources.getString("image.addError"), e);
        }

        return addImageResult;
    }

    /**
     * Deletes the image with the given id.
     *
     * @param id The id of the image to be deleted.
     * @return The result of the delete function.
     */
    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult deleteImage(@PathParam("id") final Integer id) {
        ImageService imageService = new ImageService();
        return imageService.deleteImage(id);
    }

    /**
     * Provides the image with the given ID.
     *
     * @param id The ID of the image.
     * @return The image with the given ID.
     */
    @GET
    @Path("/data/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult getImage(@PathParam("id") final Integer id) {
        ImageService imageService = new ImageService();
        return imageService.getImage(id);
    }

    /**
     * Updates meta data of an existing image.
     *
     * @param imageMetaData The ImageMetaData to be updated.
     * @return The result of the update function.
     */
    @PUT
    @Path("/metaData")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult updateImageMetaData(final ImageMetaData imageMetaData) {
        ImageService imageService = new ImageService();
        return imageService.updateImageMetaData(imageMetaData);
    }

    /**
     * Provides the meta data of the image with the given id.
     *
     * @param id The ID of the image.
     * @return The meta data of the image with the given ID.
     */
    @GET
    @Path("/metaData/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult getImageMetaData(@PathParam("id") final Integer id) {
        ImageService imageService = new ImageService();
        return imageService.getImageMetaData(id);
    }

    /**
     * Provides an image object based on the given input stream.
     *
     * @param uploadedInputStream The input stream containing the image data.
     * @return An image object with the data of the given input stream.
     * @throws IOException If an I/O error occurs.
     */
    private ImageData getImageObject(final InputStream uploadedInputStream) throws IOException {
        ImageData image = new ImageData();
        image.setData(IOUtils.toByteArray(uploadedInputStream));

        return image;
    }
}
