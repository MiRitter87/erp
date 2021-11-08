package backend.dao;

import java.util.List;

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
		// TODO Auto-generated method stub
	}

	
	@Override
	public void deleteBillOfMaterial(BillOfMaterial billOfMaterial) throws Exception {
		// TODO Auto-generated method stub
	}

	
	@Override
	public List<BillOfMaterial> getBillOfMaterials() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public BillOfMaterial getBillOfMaterial(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public void updateBillOfMaterial(BillOfMaterial billOfMaterial) throws ObjectUnchangedException, Exception {
		// TODO Auto-generated method stub
	}
}
