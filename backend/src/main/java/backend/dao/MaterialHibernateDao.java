package backend.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import backend.exception.ObjectUnchangedException;
import backend.model.Material;

/**
 * Provides access to material database persistence using Hibernate.
 * 
 * @author Michael
 */
public class MaterialHibernateDao implements MaterialDao {
	/**
	 * Factory for database session.
	 */
	protected EntityManagerFactory sessionFactory;
	
	
	/**
	 * Default constructor.
	 * 
	 * @param sessionFactory The database session factory.
	 */
	public MaterialHibernateDao(final EntityManagerFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	@Override
	public void insertMaterial(Material material) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		try {
			entityManager.persist(material);
			entityManager.flush();	//Assures, that the generated material ID is available.
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
	public void deleteMaterial(Material material) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		
		//In order to successfully delete an entity, it first has to be fetched from the database.
		Material deleteMaterial = entityManager.find(Material.class, material.getId());
		
		entityManager.getTransaction().begin();
		
		try {
			entityManager.remove(deleteMaterial);
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
	public List<Material> getMaterials() throws Exception {
		List<Material> materials = null;
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Material> criteriaQuery = criteriaBuilder.createQuery(Material.class);
			Root<Material> criteria = criteriaQuery.from(Material.class);
			criteriaQuery.select(criteria);
			criteriaQuery.orderBy(criteriaBuilder.asc(criteria.get("id")));	//Order by id ascending
			TypedQuery<Material> typedQuery = entityManager.createQuery(criteriaQuery);
			materials = typedQuery.getResultList();
			
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
		
		return materials;
	}

	
	@Override
	public Material getMaterial(Integer id) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
		Material material = entityManager.find(Material.class, id);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return material;
	}

	
	@Override
	public void updateMaterial(Material material) throws ObjectUnchangedException, Exception {
		EntityManager entityManager;
		
		this.checkMaterialDataChanged(material);
		
		entityManager = this.sessionFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.merge(material);
		entityManager.getTransaction().commit();
		entityManager.close();	
	}
	
	
	@Override
	public Set<Integer> getAllImageIds() throws Exception {
		List<Material> materials = null;
		Set<Integer> imageIds = new HashSet<Integer>();
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Material> criteriaQuery = criteriaBuilder.createQuery(Material.class);
			Root<Material> criteria = criteriaQuery.from(Material.class);
			criteriaQuery.select(criteria);
			criteriaQuery.where(criteria.get("image").isNotNull());
			TypedQuery<Material> typedQuery = entityManager.createQuery(criteriaQuery);
			materials = typedQuery.getResultList();
			
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
		
		//Get the image IDs of all materials that have an image defined.
		for(Material tempMaterial:materials)
			imageIds.add(tempMaterial.getImage().getId());
		
		return imageIds;
	}
	
	
	/**
	 * Checks if the data of the given material differ from the material that is persisted at database level.
	 * 
	 * @param material The material to be checked.
	 * @throws ObjectUnchangedException In case the material has not been changed.
	 * @throws Exception In case an error occurred during determination of the material stored at the database.
	 */
	private void checkMaterialDataChanged(final Material material) throws ObjectUnchangedException, Exception {
		Material databaseMaterial = this.getMaterial(material.getId());
		
		if(databaseMaterial.equals(material))
			throw new ObjectUnchangedException();
	}
}
