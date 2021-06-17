package backend.dao;

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
	 * @param image The image to be deleted.
	 * @throws Exception Deletion failed.
	 */
	void deleteImage(final ImageData image) throws Exception;
	
	
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
}
