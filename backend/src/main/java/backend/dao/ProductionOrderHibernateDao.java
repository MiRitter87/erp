package backend.dao;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import backend.exception.ObjectUnchangedException;
import backend.model.productionOrder.ProductionOrder;

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
		// TODO Auto-generated method stub

	}

	
	@Override
	public void deleteProductionOrder(ProductionOrder productionOrder) throws Exception {
		// TODO Auto-generated method stub

	}

	
	@Override
	public List<ProductionOrder> getProductionOrders() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public ProductionOrder getProductionOrder(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void updateProductionOrder(ProductionOrder productionOrder) throws ObjectUnchangedException, Exception {
		// TODO Auto-generated method stub

	}
}
