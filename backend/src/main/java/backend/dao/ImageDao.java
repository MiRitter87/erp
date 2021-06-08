package backend.dao;

import backend.model.Image;

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
	void insertImage(final Image image) throws Exception;
	
	
	/**
	 * Deletes an image.
	 * 
	 * @param image The image to be deleted.
	 * @throws Exception Deletion failed.
	 */
	void deleteImage(final Image image) throws Exception;
	
	
	/**
	 * Gets the image with the given id.
	 * 
	 * @param id The id of the image.
	 * @return The image with the given id.
	 * @throws Exception Image retrieval failed.
	 */
	Image getImage(final Integer id) throws Exception;
}
