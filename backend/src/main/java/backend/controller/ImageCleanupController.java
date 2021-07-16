package backend.controller;

import java.util.HashSet;
import java.util.Iterator;
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
		this.imageDAO = DAOManager.getInstance().getImageDAO();
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
		List<ImageMetaData> allImages = this.imageDAO.getAllImageMetaData();
		Set<Integer> allImageIds = new HashSet<Integer>();
		Iterator<Integer> imageIdIterator;
		Integer imageId;
		
		//Build the set with all image IDs.
		for(ImageMetaData tempImageMetaData:allImages) {
			allImageIds.add(tempImageMetaData.getId());			
		}
		
		//Remove the IDs of images that are referenced by master data objects.
		allImageIds.removeAll(imagesInUse);
		
		//Delete all images that are not referenced by any master data object.
		imageIdIterator = allImageIds.iterator();
		while(imageIdIterator.hasNext()) {
			imageId = imageIdIterator.next();
			this.imageDAO.deleteImage(imageId);
		}
	}
}
