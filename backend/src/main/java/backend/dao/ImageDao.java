package backend.dao;

import java.util.List;

import backend.exception.ObjectUnchangedException;
import backend.model.ImageData;
import backend.model.ImageMetaData;

/**
 * Interface for image persistence.
 * 
 * @author Michael
 */
public interface ImageDao {
	/**
	 * Inserts an image.
	 * 
	 * @param image The image to be inserted.
	 * @throws Exception Insertion failed.
	 */
	void insertImage(final ImageData image) throws Exception;
	
	
	/**
	 * Deletes an image.
	 * 
	 * @param imageId The ID of the image to be deleted.
	 * @throws Exception Deletion failed.
	 */
	void deleteImage(final Integer imageId) throws Exception;
	
	
	/**
	 * Updates the meta data of an image.
	 * 
	 * @param imageMetaData The meta data of the image.
	 * @throws ObjectUnchangedException The image meta data have not been changed.
	 * @throws Exception Update failed.
	 */
	void updateImageMetaData(final ImageMetaData imageMetaData) throws ObjectUnchangedException, Exception;
	
	
	/**
	 * Gets the data of the image with the given id.
	 * 
	 * @param id The id of the image.
	 * @return The data of the image with the given id.
	 * @throws Exception Image data retrieval failed.
	 */
	ImageData getImageData(final Integer id) throws Exception;
	
	
	/**
	 * Gets the metadata of the image with the given id.
	 * 
	 * @param id The id of the image.
	 * @return The metadata of the image with the given id.
	 * @throws Exception Image metadata retrieval failed.
	 */
	ImageMetaData getImageMetaData(final Integer id) throws Exception;
	
	
	/**
	 * Gets the meta data of all images.
	 * 
	 * @return The meta data of all images.
	 * @throws Exception Image metadata retrieval failed.
	 */
	List<ImageMetaData> getAllImageMetaData() throws Exception;
}
