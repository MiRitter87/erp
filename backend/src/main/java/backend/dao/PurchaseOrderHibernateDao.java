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
		List<PurchaseOrder> purchaseOrders = null;
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		
		//Use entity graphs to load data of referenced PurchaseOrderItem instances.
		EntityGraph<PurchaseOrder> graph = entityManager.createEntityGraph(PurchaseOrder.class);
		graph.addAttributeNodes("items");
		
		entityManager.getTransaction().begin();
		
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<PurchaseOrder> criteriaQuery = criteriaBuilder.createQuery(PurchaseOrder.class);
			Root<PurchaseOrder> criteria = criteriaQuery.from(PurchaseOrder.class);
			criteriaQuery.select(criteria);			
			criteriaQuery.orderBy(criteriaBuilder.asc(criteria.get("id")));	//Order by id ascending
			TypedQuery<PurchaseOrder> typedQuery = entityManager.createQuery(criteriaQuery);
			typedQuery.setHint("javax.persistence.loadgraph", graph);	//Also fetch all item data.
			purchaseOrders = typedQuery.getResultList();
			
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
		
		return purchaseOrders;
	}


	@Override
	public PurchaseOrder getPurchaseOrder(Integer id) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		
		//Use entity graphs to load data of referenced PurchaseOrderItem instances.
		EntityGraph<PurchaseOrder> graph = entityManager.createEntityGraph(PurchaseOrder.class);
		graph.addAttributeNodes("items");
		Map<String, Object> hints = new HashMap<String, Object>();
		hints.put("javax.persistence.fetchgraph", graph);
		
		entityManager.getTransaction().begin();
		PurchaseOrder purchaseOrder = entityManager.find(PurchaseOrder.class, id, hints);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return purchaseOrder;
	}


	@Override
	public void updatePurchaseOrder(PurchaseOrder purchaseOrder) throws ObjectUnchangedException, Exception {
		EntityManager entityManager;
		
		this.checkPurchaseOrderDataChanged(purchaseOrder);
		
		entityManager = this.sessionFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.merge(purchaseOrder);
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
	
	/**
	 * Checks if the data of the given purchase order differ from the purchase order that is persisted at database level.
	 * 
	 * @param purchaseOrder The purchase order to be checked.
	 * @throws ObjectUnchangedException In case the purchase order has not been changed.
	 * @throws Exception In case an error occurred during determination of the purchase order stored at the database.
	 */
	private void checkPurchaseOrderDataChanged(final PurchaseOrder purchaseOrder) throws ObjectUnchangedException, Exception {
		PurchaseOrder databasePurchaseOrder = this.getPurchaseOrder(purchaseOrder.getId());
		
		if(databasePurchaseOrder.equals(purchaseOrder))
			throw new ObjectUnchangedException();
	}
}
