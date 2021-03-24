package backend.dao;

import java.util.List;

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
		// TODO Auto-generated method stub

	}

	
	@Override
	public void deleteSalesOrder(SalesOrder salesOrder) throws Exception {
		// TODO Auto-generated method stub

	}

	
	@Override
	public List<SalesOrder> getSalesOrders() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public SalesOrder getSalesOrder(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public void updateSalesOrder(SalesOrder salesOrder) throws ObjectUnchangedException, Exception {
		// TODO Auto-generated method stub

	}
}
