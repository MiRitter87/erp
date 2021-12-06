package backend.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import backend.exception.EntityExistsException;
import backend.exception.ObjectUnchangedException;
import backend.model.billOfMaterial.BillOfMaterial;
import backend.model.material.Material;

/**
 * Provides access to BillOfMaterial database persistence using Hibernate.
 * 
 * @author Michael
 */
public class BillOfMaterialHibernateDao implements BillOfMaterialDao {
	/**
	 * Factory for database session.
	 */
	protected EntityManagerFactory sessionFactory;
	
	
	/**
	 * Default constructor.
	 * 
	 * @param sessionFactory The database session factory.
	 */
	public BillOfMaterialHibernateDao(final EntityManagerFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	@Override
	public void insertBillOfMaterial(BillOfMaterial billOfMaterial) throws EntityExistsException, Exception {
		EntityManager entityManager;
		
		this.checkAnotherBomOfMaterialExists(billOfMaterial);
		
		entityManager = this.sessionFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		try {
			entityManager.persist(billOfMaterial);
			entityManager.flush();	//Assures, that the generated BillOfMaterial ID is available.
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
	public void deleteBillOfMaterial(BillOfMaterial billOfMaterial) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		
		//In order to successfully delete an entity, it first has to be fetched from the database.
		BillOfMaterial deleteBillOfMaterial = entityManager.find(BillOfMaterial.class, billOfMaterial.getId());
		
		entityManager.getTransaction().begin();
		
		try {
			entityManager.remove(deleteBillOfMaterial);
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
	public List<BillOfMaterial> getBillOfMaterials(final Material material) throws Exception {
		List<BillOfMaterial> billOfMaterials = null;
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		
		//Use entity graphs to load data of referenced BillOfMaterialItem instances.
		EntityGraph<BillOfMaterial> graph = entityManager.createEntityGraph(BillOfMaterial.class);
		graph.addAttributeNodes("items");
		
		entityManager.getTransaction().begin();
		
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<BillOfMaterial> criteriaQuery = criteriaBuilder.createQuery(BillOfMaterial.class);
			Root<BillOfMaterial> criteria = criteriaQuery.from(BillOfMaterial.class);
			criteriaQuery.select(criteria);
			this.applyBomMaterialQueryParameter(material, criteriaQuery, criteriaBuilder, criteria);
			criteriaQuery.orderBy(criteriaBuilder.asc(criteria.get("id")));	//Order by id ascending
			TypedQuery<BillOfMaterial> typedQuery = entityManager.createQuery(criteriaQuery);
			typedQuery.setHint("javax.persistence.loadgraph", graph);	//Also fetch all item data.
			billOfMaterials = typedQuery.getResultList();
			
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
		
		return billOfMaterials;
	}
	

	@Override
	public BillOfMaterial getBillOfMaterial(Integer id) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		
		//Use entity graphs to load data of referenced BillOfMaterialItem instances.
		EntityGraph<BillOfMaterial> graph = entityManager.createEntityGraph(BillOfMaterial.class);
		graph.addAttributeNodes("items");
		Map<String, Object> hints = new HashMap<String, Object>();
		hints.put("javax.persistence.fetchgraph", graph);
		
		entityManager.getTransaction().begin();
		BillOfMaterial billOfMaterial = entityManager.find(BillOfMaterial.class, id, hints);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return billOfMaterial;
	}
	

	@Override
	public void updateBillOfMaterial(BillOfMaterial billOfMaterial) throws ObjectUnchangedException, Exception {
		EntityManager entityManager;
		
		this.checkBillOfMaterialDataChanged(billOfMaterial);
		
		entityManager = this.sessionFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.merge(billOfMaterial);
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
	
	/**
	 * Applies the material query parameter to the BillOfMaterial query.
	 * 
	 * @param material The query parameter for BillOfMaterial material.
	 * @param criteriaQuery The BillOfMaterial criteria query.
	 * @param criteriaBuilder The builder of criterias.
	 * @param criteria The root entity of the BillOfMaterial that is being queried.
	 */
	private void applyBomMaterialQueryParameter(final Material material, final CriteriaQuery<BillOfMaterial> criteriaQuery,
			final CriteriaBuilder criteriaBuilder, final Root<BillOfMaterial> criteria) {
		
		if(material == null)
			return;	//No further query restrictions needed.
		
		criteriaQuery.where(criteriaBuilder.equal(criteria.get("material"), material));
	}
	
	
	/**
	 * Checks if the data of the given BillOfMaterial differ from the BillOfMaterial that is persisted at database level.
	 * 
	 * @param billOfMaterial The BillOfMaterial to be checked.
	 * @throws ObjectUnchangedException In case the BillOfMaterial has not been changed.
	 * @throws Exception In case an error occurred during determination of the BillOfMaterial stored at the database.
	 */
	private void checkBillOfMaterialDataChanged(final BillOfMaterial billOfMaterial) throws ObjectUnchangedException, Exception {
		BillOfMaterial databaseBillOfMaterial = this.getBillOfMaterial(billOfMaterial.getId());
		
		if(databaseBillOfMaterial.equals(billOfMaterial))
			throw new ObjectUnchangedException();
	}
	
	
	
	
	/**
	 * Checks if another BillOfMaterial for the same material exists.
	 * 
	 * @param billOfMaterial The BillOfMaterial which the user tries to save or update.
	 * @throws EntityExistsException Another BillOfMaterial with the same material exists.
	 * @throws Exception Determination of BillOfMaterials with given material failed.
	 */
	private void checkAnotherBomOfMaterialExists(final BillOfMaterial billOfMaterial) throws EntityExistsException, Exception {
		List<BillOfMaterial> billOfMaterials = null;
		
		billOfMaterials = this.getBillOfMaterials(billOfMaterial.getMaterial());
		
		for(BillOfMaterial databaseBillOfMaterial:billOfMaterials) {
			//Ignore 'self'. Relevant when updating an existing BillOfMaterial.
			if(databaseBillOfMaterial.getId() == billOfMaterial.getId())
				continue;
			
			if(databaseBillOfMaterial.getMaterial().getId() == billOfMaterial.getMaterial().getId()) {
				throw new EntityExistsException(databaseBillOfMaterial.getId());
			}
		}
	}
}
