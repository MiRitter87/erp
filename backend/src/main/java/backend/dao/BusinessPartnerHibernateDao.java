package backend.dao;

import java.util.List;

import backend.exception.ObjectUnchangedException;
import backend.model.BusinessPartner;

/**
 * Provides access to business partner database persistence using Hibernate.
 * 
 * @author Michael
 */
public class BusinessPartnerHibernateDao implements BusinessPartnerDao {
	/**
	 * Default constructor.
	 */
	public BusinessPartnerHibernateDao() {
		
	}

	
	@Override
	public void insertBusinessPartner(BusinessPartner businessPartner) throws Exception {
		// TODO Auto-generated method stub

	}

	
	@Override
	public void deleteBusinessPartner(BusinessPartner businessPartner) throws Exception {
		// TODO Auto-generated method stub

	}

	
	@Override
	public List<BusinessPartner> getBusinessPartners() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public BusinessPartner getBusinessPartner(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void updateBusinessPartner(BusinessPartner businessPartner) throws ObjectUnchangedException, Exception {
		// TODO Auto-generated method stub

	}
}
