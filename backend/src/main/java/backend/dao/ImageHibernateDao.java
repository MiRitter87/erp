package backend.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import backend.exception.ObjectUnchangedException;
import backend.model.image.ImageData;
import backend.model.image.ImageMetaData;

/**
 * Provides access to image database persistence using Hibernate.
 *
 * @author Michael
 */
public class ImageHibernateDao implements ImageDao {
    /**
     * Factory for database session.
     */
    private EntityManagerFactory sessionFactory;

    /**
     * Default constructor.
     *
     * @param sessionFactory The database session factory.
     */
    public ImageHibernateDao(final EntityManagerFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Inserts an image.
     */
    @Override
    public void insertImage(final ImageData image) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            entityManager.persist(image);
            entityManager.flush(); // Assures, that the generated image ID is available.
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            // If something breaks a rollback is necessary!?
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw exception;
        } finally {
            entityManager.close();
        }
    }

    /**
     * Delete an image.
     */
    @Override
    public void deleteImage(final Integer imageId) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();

        // In order to successfully delete an entity, it first has to be fetched from the database.
        ImageData deleteImage = entityManager.find(ImageData.class, imageId);

        entityManager.getTransaction().begin();

        try {
            entityManager.remove(deleteImage);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            // If something breaks a rollback is necessary.
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw exception;
        } finally {
            entityManager.close();
        }
    }

    /**
     * Gets the data of the image with the given id.
     */
    @Override
    public ImageData getImageData(final Integer id) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();

        entityManager.getTransaction().begin();
        ImageData image = entityManager.find(ImageData.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();

        return image;
    }

    /**
     * Gets the metadata of the image with the given id.
     */
    @Override
    public ImageMetaData getImageMetaData(final Integer id) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();

        entityManager.getTransaction().begin();
        ImageMetaData image = entityManager.find(ImageMetaData.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();

        return image;
    }

    /**
     * Updates the meta data of an image.
     */
    @Override
    public void updateImageMetaData(final ImageMetaData imageMetaData) throws Exception {
        EntityManager entityManager;

        this.checkImageMetaDataChanged(imageMetaData);

        entityManager = this.sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(imageMetaData);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * Gets the meta data of all images.
     */
    @Override
    public List<ImageMetaData> getAllImageMetaData() throws Exception {
        List<ImageMetaData> allImageMetaData = null;
        EntityManager entityManager = this.sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<ImageMetaData> criteriaQuery = criteriaBuilder.createQuery(ImageMetaData.class);
            Root<ImageMetaData> criteria = criteriaQuery.from(ImageMetaData.class);
            criteriaQuery.select(criteria);
            criteriaQuery.orderBy(criteriaBuilder.asc(criteria.get("id"))); // Order by id ascending
            TypedQuery<ImageMetaData> typedQuery = entityManager.createQuery(criteriaQuery);
            allImageMetaData = typedQuery.getResultList();

            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            // If something breaks a rollback is necessary.
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw exception;
        } finally {
            entityManager.close();
        }

        return allImageMetaData;
    }

    /**
     * Checks if the given image meta data differ from the image meta data that are persisted at database level.
     *
     * @param imageMetaData The image meta data to be checked.
     * @throws ObjectUnchangedException In case the image meta data have not been changed.
     * @throws Exception                In case an error occurred during determination of the image meta data stored at
     *                                  the database.
     */
    private void checkImageMetaDataChanged(final ImageMetaData imageMetaData)
            throws ObjectUnchangedException, Exception {
        ImageMetaData databaseImageMetaData = this.getImageMetaData(imageMetaData.getId());

        if (databaseImageMetaData.equals(imageMetaData)) {
            throw new ObjectUnchangedException();
        }
    }
}
