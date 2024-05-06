package backend.controller;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backend.dao.DAOManager;
import backend.dao.ImageDao;
import backend.dao.MaterialDao;
import backend.dao.NativeSqlDao;
import backend.model.image.ImageMetaData;

/**
 * Looks for images in the database that are not referenced by any master data object. Those unreferenced images are
 * regarded as obsolete and are being deleted.
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
     * The DAO to execute native SQL commands.
     */
    private NativeSqlDao nativeSqlDAO;

    /**
     * Application logging.
     */
    public static final Logger LOGGER = LogManager.getLogger(ImageCleanupController.class);

    /**
     * Access to localized application resources.
     */
    private ResourceBundle resources;

    /**
     * Initializes the controller.
     */
    public ImageCleanupController() {
        this.resources = ResourceBundle.getBundle("backend");
        this.materialDAO = DAOManager.getInstance().getMaterialDAO();
        this.imageDAO = DAOManager.getInstance().getImageDAO();
        this.nativeSqlDAO = DAOManager.getInstance().getNativeSqlDAO();
    }

    /**
     * Cleans obsolete images from the database.
     */
    public void cleanup() {
        Set<Integer> imagesInUse = new HashSet<Integer>();

        try {
            imagesInUse = this.getAllImageIdsReferencedByMaterial();
            this.deleteImages(imagesInUse);
            this.executeCheckpointCommand();
        } catch (Exception exception) {
            LOGGER.error("imageCleanupController.cleanupFailed", exception.getMessage());
        }
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

        // Build the set with all image IDs.
        for (ImageMetaData tempImageMetaData : allImages) {
            allImageIds.add(tempImageMetaData.getId());
        }

        // Remove the IDs of images that are referenced by master data object and therefore are still in use.
        allImageIds.removeAll(imagesInUse);

        // Delete all images that are not referenced by any master data object.
        imageIdIterator = allImageIds.iterator();
        while (imageIdIterator.hasNext()) {
            imageId = imageIdIterator.next();
            this.imageDAO.deleteImage(imageId);
        }

        LOGGER.info(MessageFormat.format(this.resources.getString("imageCleanupController.imagesDeleted"),
                allImageIds.size()));
    }

    /**
     * Executes the HSQL "CHECKPOINT" command to remove obsolete data from the *.lobs file where image data are stored.
     *
     * @see http://hsqldb.org/doc/gui+de/management-chapt.html#mtc_large_objects
     *
     * @throws Exception In case the SQL checkpoint command execution fails.
     */
    private void executeCheckpointCommand() throws Exception {
        this.nativeSqlDAO.executeStatement("CHECKPOINT;");
    }
}
