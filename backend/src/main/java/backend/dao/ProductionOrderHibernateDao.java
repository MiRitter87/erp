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
import backend.model.productionOrder.ProductionOrder;
import backend.model.productionOrder.ProductionOrderStatus;

/**
 * Provides access to production order database persistence using Hibernate.
 * 
 * @author Michael
 */
public class ProductionOrderHibernateDao implements ProductionOrderDao {
	/**
	 * Factory for database session.
	 */
	protected EntityManagerFactory sessionFactory;
	
	
	/**
	 * Default constructor.
	 * 
	 * @param sessionFactory The database session factory.
	 */
	public ProductionOrderHibernateDao(final EntityManagerFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	

	@Override
	public void insertProductionOrder(ProductionOrder productionOrder) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		try {
			entityManager.persist(productionOrder);
			entityManager.flush();	//Assures, that the generated production order ID is available.
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
	public void deleteProductionOrder(ProductionOrder productionOrder) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		
		//In order to successfully delete an entity, it first has to be fetched from the database.
		ProductionOrder deleteProductionOrder = entityManager.find(ProductionOrder.class, productionOrder.getId());
		
		entityManager.getTransaction().begin();
		
		try {
			entityManager.remove(deleteProductionOrder);
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
	public List<ProductionOrder> getProductionOrders(final ProductionOrderStatus orderStatusQuery) throws Exception {
		List<ProductionOrder> productionOrders = null;
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		
		//Use entity graphs to load data of referenced ProductionOrderItem instances.
		EntityGraph<ProductionOrder> graph = entityManager.createEntityGraph(ProductionOrder.class);
		graph.addAttributeNodes("items");
		
		entityManager.getTransaction().begin();
		
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<ProductionOrder> criteriaQuery = criteriaBuilder.createQuery(ProductionOrder.class);
			Root<ProductionOrder> criteria = criteriaQuery.from(ProductionOrder.class);
			criteriaQuery.select(criteria);		
			criteriaQuery.orderBy(criteriaBuilder.asc(criteria.get("id")));	//Order by id ascending
			TypedQuery<ProductionOrder> typedQuery = entityManager.createQuery(criteriaQuery);
			typedQuery.setHint("javax.persistence.loadgraph", graph);	//Also fetch all item data.
			productionOrders = typedQuery.getResultList();
			
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
		
		return productionOrders;
	}

	
	@Override
	public ProductionOrder getProductionOrder(Integer id) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		
		//Use entity graphs to load data of referenced ProductionOrderItem instances.
		EntityGraph<ProductionOrder> graph = entityManager.createEntityGraph(ProductionOrder.class);
		graph.addAttributeNodes("items");
		Map<String, Object> hints = new HashMap<String, Object>();
		hints.put("javax.persistence.loadgraph", graph);
		
		entityManager.getTransaction().begin();
		ProductionOrder productionOrder = entityManager.find(ProductionOrder.class, id, hints);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return productionOrder;
	}

	
	@Override
	public void updateProductionOrder(ProductionOrder productionOrder) throws ObjectUnchangedException, Exception {
		EntityManager entityManager;
		
		this.checkProductionOrderDataChanged(productionOrder);
		
		entityManager = this.sessionFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.merge(productionOrder);
		entityManager.getTransaction().commit();
		entityManager.close();	
	}
	
	
	/**
	 * Checks if the data of the given production order differ from the production order that is persisted at database level.
	 * 
	 * @param productionOrder The production order to be checked.
	 * @throws ObjectUnchangedException In case the production order has not been changed.
	 * @throws Exception In case an error occurred during determination of the production order stored at the database.
	 */
	private void checkProductionOrderDataChanged(final ProductionOrder productionOrder) throws ObjectUnchangedException, Exception {
		ProductionOrder databaseProductionOrder = this.getProductionOrder(productionOrder.getId());
		
		if(databaseProductionOrder.equals(productionOrder))
			throw new ObjectUnchangedException();
	}
}
