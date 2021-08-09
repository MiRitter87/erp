package backend.dao;

import javax.persistence.EntityManagerFactory;

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
		// TODO Auto-generated method stub
		
	}


	@Override
	public void deletePurchaseOrder(PurchaseOrder purchaseOrder) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
