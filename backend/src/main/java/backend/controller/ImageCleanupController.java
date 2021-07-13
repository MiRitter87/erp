package backend.controller;

/**
 * Looks for images in the database that are not referenced by any master data object.
 * Those unreferenced images are regarded as obsolete and are being deleted.
 * 
 * @author Michael
 */
public class ImageCleanupController {
	/**
	 * Cleans obsolete images from the database.
	 * 
	 * @throws Exception In case the cleanup process fails.
	 */
	public void cleanup() throws Exception {
		
	}
}
