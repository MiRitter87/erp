package backend.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
	}
}
