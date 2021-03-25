package backend.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;

import backend.exception.ObjectUnchangedException;
import backend.model.SalesOrder;

/**
 * Provides access to sales order database persistence using Hibernate.
 * 
 * @author Michael
 */
public class SalesOrderHibernateDao extends HibernateDao implements SalesOrderDao {
	/**
	 * Default constructor.
	 */
	public SalesOrderHibernateDao() {
		
	}
	
	
	@Override
	public void insertSalesOrder(SalesOrder salesOrder) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		try {
			entityManager.persist(salesOrder);
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
	public void deleteSalesOrder(SalesOrder salesOrder) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		
		//In order to successfully delete an entity, it first has to be fetched from the database.
		SalesOrder deleteSalesOrder = entityManager.find(SalesOrder.class, salesOrder.getId());
		
		entityManager.getTransaction().begin();
		
		try {
			entityManager.remove(deleteSalesOrder);
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
	public List<SalesOrder> getSalesOrders() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public SalesOrder getSalesOrder(Integer id) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		
		//Use entity graphs to load data of referenced SalesOrderItem instances.
		EntityGraph<SalesOrder> graph = entityManager.createEntityGraph(SalesOrder.class);
		graph.addAttributeNodes("items");
		Map<String, Object> hints = new HashMap<String, Object>();
		hints.put("javax.persistence.loadgraph", graph);
		
		entityManager.getTransaction().begin();
		SalesOrder salesOrder = entityManager.find(SalesOrder.class, id, hints);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return salesOrder;
	}
	

	@Override
	public void updateSalesOrder(SalesOrder salesOrder) throws ObjectUnchangedException, Exception {
		// TODO Auto-generated method stub

	}
}
