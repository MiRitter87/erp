package backend.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import backend.exception.ObjectUnchangedException;
import backend.model.PurchaseOrder;

/**
 * Provides access to purchase order database persistence using Hibernate.
 * 
 * @author Michael
 */
public class PurchaseOrderHibernateDao implements PurchaseOrderDao {
	/**
	 * Factory for database session.
	 */
	protected EntityManagerFactory sessionFactory;
	
	
	/**
	 * Default constructor.
	 * 
	 * @param sessionFactory The database session factory.
	 */
	public PurchaseOrderHibernateDao(final EntityManagerFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	@Override
	public void insertPurchaseOrder(PurchaseOrder purchaseOrder) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		try {
			entityManager.persist(purchaseOrder);
			entityManager.flush();	//Assures, that the generated sales order ID is available.
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
	public void deletePurchaseOrder(PurchaseOrder purchaseOrder) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		
		//In order to successfully delete an entity, it first has to be fetched from the database.
		PurchaseOrder deletePurchaseOrder = entityManager.find(PurchaseOrder.class, purchaseOrder.getId());
		
		entityManager.getTransaction().begin();
		
		try {
			entityManager.remove(deletePurchaseOrder);
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
	public List<PurchaseOrder> getPurchaseOrders() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public PurchaseOrder getPurchaseOrder(Integer id) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		
		//Use entity graphs to load data of referenced PurchaseOrderItem instances.
		EntityGraph<PurchaseOrder> graph = entityManager.createEntityGraph(PurchaseOrder.class);
		graph.addAttributeNodes("items");
		Map<String, Object> hints = new HashMap<String, Object>();
		hints.put("javax.persistence.loadgraph", graph);
		
		entityManager.getTransaction().begin();
		PurchaseOrder purchaseOrder = entityManager.find(PurchaseOrder.class, id, hints);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return purchaseOrder;
	}


	@Override
	public void updatePurchaseOrder(PurchaseOrder purchaseOrder) throws ObjectUnchangedException, Exception {
		// TODO Auto-generated method stub
		
	}
}
