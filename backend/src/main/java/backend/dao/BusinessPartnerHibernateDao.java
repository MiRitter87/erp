package backend.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import backend.exception.ObjectUnchangedException;

import backend.model.BusinessPartner;
import backend.model.BusinessPartnerType;
import backend.model.webservice.BPTypeQueryParameter;

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
	public List<BusinessPartner> getBusinessPartners(final BPTypeQueryParameter bpTypeQuery) throws Exception {
		List<BusinessPartner> businessPartners = null;
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<BusinessPartner> criteriaQuery = criteriaBuilder.createQuery(BusinessPartner.class);
			Root<BusinessPartner> criteria = criteriaQuery.from(BusinessPartner.class);
			criteriaQuery.select(criteria);
			this.applyBPTypeQueryParameter(bpTypeQuery, criteriaQuery, criteriaBuilder, criteria);
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
	
	
	/**
	 * Applies the type query parameter to the business partner query.
	 * 
	 * @param bpTypeQuery Specifies the business partners to be selected based on the type attribute.
	 * @param bpCriteriaQuery The business partner criteria query.
	 * @param criteriaBuilder The builder of criterias.
	 * @param bpCriteria Root type of the business partner in the from clause.
	 */
	private void applyBPTypeQueryParameter(final BPTypeQueryParameter bpTypeQuery, CriteriaQuery<BusinessPartner> bpCriteriaQuery, 
			final CriteriaBuilder criteriaBuilder, final Root<BusinessPartner> bpCriteria) {
		
		Expression<Collection<BusinessPartnerType>> types = bpCriteria.get("types");
		BusinessPartnerType bpType = null;
		
		if(bpTypeQuery == null)
			return;
		
		//Convert the type that is used to define the query into the type that is used in the data model.
		switch(bpTypeQuery) {
			case CUSTOMER:
				bpType = BusinessPartnerType.CUSTOMER;
				break;
			case VENDOR:
				bpType = BusinessPartnerType.VENDOR;
				break;
			case ALL:
				return;
		}
		
		bpCriteriaQuery.where(criteriaBuilder.isMember(bpType, types));
	}
}
