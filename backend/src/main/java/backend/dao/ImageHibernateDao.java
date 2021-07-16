package backend.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import backend.exception.ObjectUnchangedException;
import backend.model.ImageData;
import backend.model.ImageMetaData;

/**
 * Provides access to image database persistence using Hibernate.
 * 
 * @author Michael
 */
public class ImageHibernateDao implements ImageDao {
	/**
	 * Factory for database session.
	 */
	protected EntityManagerFactory sessionFactory;
	
	
	/**
	 * Default constructor.
	 * 
	 * @param sessionFactory The database session factory.
	 */
	public ImageHibernateDao(final EntityManagerFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	

	@Override
	public void insertImage(ImageData image) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		try {
			entityManager.persist(image);
			entityManager.flush();	//Assures, that the generated image ID is available.
			entityManager.getTransaction().commit();
		}
		catch(Exception exception) {
			//If something breaks a rollback is necessary!?
			if(entityManager.getTransaction().isActive())
				entityManager.getTransaction().rollback();
			throw exception;
		}
		finally {
			entityManager.close();			
		}
	}

	
	@Override
	public void deleteImage(Integer imageId) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		
		//In order to successfully delete an entity, it first has to be fetched from the database.
		ImageData deleteImage = entityManager.find(ImageData.class, imageId);
		
		entityManager.getTransaction().begin();
		
		try {
			entityManager.remove(deleteImage);
			entityManager.getTransaction().commit();			
		}
		catch(Exception exception) {
			//If something breaks a rollback is necessary.
			if(entityManager.getTransaction().isActive())
				entityManager.getTransaction().rollback();
			throw exception;
		}
		finally {
			entityManager.close();			
		}
	}

	
	@Override
	public ImageData getImageData(Integer id) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
		ImageData image = entityManager.find(ImageData.class, id);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return image;
	}


	@Override
	public ImageMetaData getImageMetaData(Integer id) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
		ImageMetaData image = entityManager.find(ImageMetaData.class, id);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return image;
	}


	@Override
	public void updateImageMetaData(ImageMetaData imageMetaData) throws Exception {
		EntityManager entityManager;
		
		this.checkImageMetaDataChanged(imageMetaData);
		
		entityManager = this.sessionFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.merge(imageMetaData);
		entityManager.getTransaction().commit();
		entityManager.close();		
	}
	
	
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
			criteriaQuery.orderBy(criteriaBuilder.asc(criteria.get("id")));	//Order by id ascending
			TypedQuery<ImageMetaData> typedQuery = entityManager.createQuery(criteriaQuery);
			allImageMetaData = typedQuery.getResultList();
			
			entityManager.getTransaction().commit();			
		}
		catch(Exception exception) {
			//If something breaks a rollback is necessary.
			if(entityManager.getTransaction().isActive())
				entityManager.getTransaction().rollback();
			throw exception;
		}
		finally {
			entityManager.close();			
		}
		
		return allImageMetaData;
	}
	
	
	/**
	 * Checks if the given image meta data differ from the image meta data that are persisted at database level.
	 * 
	 * @param imageMetaData The image meta data to be checked.
	 * @throws ObjectUnchangedException In case the image meta data have not been changed.
	 * @throws Exception In case an error occurred during determination of the image meta data stored at the database.
	 */
	private void checkImageMetaDataChanged(final ImageMetaData imageMetaData) throws ObjectUnchangedException, Exception {
		ImageMetaData databaseImageMetaData = this.getImageMetaData(imageMetaData.getId());
		
		if(databaseImageMetaData.equals(imageMetaData))
			throw new ObjectUnchangedException();
	}
}
