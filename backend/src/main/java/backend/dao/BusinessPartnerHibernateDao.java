package backend.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import backend.exception.ObjectUnchangedException;
import backend.model.BusinessPartner;

/**
 * Provides access to business partner database persistence using Hibernate.
 * 
 * @author Michael
 */
public class BusinessPartnerHibernateDao implements BusinessPartnerDao {
	/**
	 * Factory for database session.
	 */
	protected EntityManagerFactory sessionFactory;
	
	
	/**
	 * Default constructor.
	 * 
	 * @param sessionFactory The database session factory.
	 */
	public BusinessPartnerHibernateDao(final EntityManagerFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
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
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		
		//In order to successfully delete an entity, it first has to be fetched from the database.
		BusinessPartner deleteBusinessPartner = entityManager.find(BusinessPartner.class, businessPartner.getId());
		
		entityManager.getTransaction().begin();
		
		try {
			entityManager.remove(deleteBusinessPartner);
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
	public List<BusinessPartner> getBusinessPartners() throws Exception {
		List<BusinessPartner> businessPartners = null;
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<BusinessPartner> criteriaQuery = criteriaBuilder.createQuery(BusinessPartner.class);
			Root<BusinessPartner> criteria = criteriaQuery.from(BusinessPartner.class);
			criteriaQuery.select(criteria);
			criteriaQuery.orderBy(criteriaBuilder.asc(criteria.get("id")));	//Order by id ascending
			TypedQuery<BusinessPartner> typedQuery = entityManager.createQuery(criteriaQuery);
			businessPartners = typedQuery.getResultList();
			
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
		
		return businessPartners;
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
		EntityManager entityManager;
		
		this.checkBusinessPartnerDataChanged(businessPartner);
		
		entityManager = this.sessionFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.merge(businessPartner);
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
	
	/**
	 * Checks if the data of the given business partner differ from the business partner that is persisted at database level.
	 * 
	 * @param businessPartner The business partner to be checked.
	 * @throws ObjectUnchangedException In case the business partner has not been changed.
	 * @throws Exception In case an error occurred during determination of the business partner stored at the database.
	 */
	private void checkBusinessPartnerDataChanged(final BusinessPartner businessPartner) throws ObjectUnchangedException, Exception {
		BusinessPartner databaseBusinessPartner = this.getBusinessPartner(businessPartner.getId());
		
		if(databaseBusinessPartner.equals(businessPartner))
			throw new ObjectUnchangedException();
	}
}
