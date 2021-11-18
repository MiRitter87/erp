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

import backend.exception.ObjectUnchangedException;
import backend.model.billOfMaterial.BillOfMaterial;

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
	public void insertBillOfMaterial(BillOfMaterial billOfMaterial) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
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
	public List<BillOfMaterial> getBillOfMaterials() throws Exception {
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
}
