package backend.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.dao.DAOManager;
import backend.dao.ImageDao;
import backend.dao.MaterialDao;
import backend.model.ImageMetaData;

/**
 * Looks for images in the database that are not referenced by any master data object.
 * Those unreferenced images are regarded as obsolete and are being deleted.
 * 
 * @author Michael
 */
public class ImageCleanupController {
	/**
	 * The DAO to access material data.
	 */
	private MaterialDao materialDAO;
	
	/**
	 * The DAO to access image data.
	 */
	private ImageDao imageDAO;
	
	
	/**
	 * Initializes the controller.
	 */
	public ImageCleanupController() {
		this.materialDAO = DAOManager.getInstance().getMaterialDAO();
	}
	
	
	/**
	 * Cleans obsolete images from the database.
	 * 
	 * @throws Exception In case the cleanup process fails.
	 */
	public void cleanup() throws Exception {
		Set<Integer> imagesInUse = new HashSet<Integer>();
		
		imagesInUse = this.getAllImageIdsReferencedByMaterial();
		this.deleteImages(imagesInUse);
	}
	
	
	/**
	 * Returns the IDs of all images that are referenced by a material.
	 * 
	 * @return A set containing the IDs of all images that are referenced by a material.
	 * @throws Exception In case the determination of referenced image IDs failed.
	 */
	private Set<Integer> getAllImageIdsReferencedByMaterial() throws Exception {
		return this.materialDAO.getAllImageIds();
	}

	
	/**
	 * Deletes all images except those with the given IDs.
	 * 
	 * @param imagesInUse The IDs of all images that are still in use.
	 * @throws Exception In case the deletion fails.
	 */
	private void deleteImages(final Set<Integer> imagesInUse) throws Exception {
		//TODO: Get all images. Remove those images from the list, that are referenced by a material. Delete the remaining images without a reference.
		List<ImageMetaData> allImages = this.imageDAO.getAllImageMetaData();
	}
}
