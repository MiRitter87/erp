package backend.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
	public void deleteImage(ImageData image) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		
		//In order to successfully delete an entity, it first has to be fetched from the database.
		ImageData deleteImage = entityManager.find(ImageData.class, image.getId());
		
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
}
