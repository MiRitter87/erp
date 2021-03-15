package backend.dao;

import java.util.List;

import javax.persistence.EntityManager;

import backend.exception.ObjectUnchangedException;
import backend.model.BusinessPartner;

/**
 * Provides access to business partner database persistence using Hibernate.
 * 
 * @author Michael
 */
public class BusinessPartnerHibernateDao extends HibernateDao implements BusinessPartnerDao {
	/**
	 * Default constructor.
	 */
	public BusinessPartnerHibernateDao() {
		
	}

	
	@Override
	public void insertBusinessPartner(BusinessPartner businessPartner) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		try {
			entityManager.persist(businessPartner);
			entityManager.flush();	//Assures, that the generated business partner ID is available.
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
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
		BusinessPartner businessPartner = entityManager.find(BusinessPartner.class, id);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return businessPartner;
	}

	
	@Override
	public void updateBusinessPartner(BusinessPartner businessPartner) throws ObjectUnchangedException, Exception {
		// TODO Auto-generated method stub

	}
}
