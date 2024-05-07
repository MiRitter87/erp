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

import backend.exception.ObjectInUseException;
import backend.exception.ObjectUnchangedException;
import backend.model.account.Posting;
import backend.model.businessPartner.BPTypeQueryParameter;
import backend.model.businessPartner.BusinessPartner;
import backend.model.businessPartner.BusinessPartnerType;
import backend.model.purchaseOrder.PurchaseOrder;
import backend.model.salesOrder.SalesOrder;

/**
 * Provides access to business partner database persistence using Hibernate.
 *
 * @author Michael
 */
public class BusinessPartnerHibernateDao implements BusinessPartnerDao {
    /**
     * Factory for database session.
     */
    private EntityManagerFactory sessionFactory;

    /**
     * Default constructor.
     *
     * @param sessionFactory The database session factory.
     */
    public BusinessPartnerHibernateDao(final EntityManagerFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Inserts a BusinessPartner.
     */
    @Override
    public void insertBusinessPartner(final BusinessPartner businessPartner) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            entityManager.persist(businessPartner);
            entityManager.flush(); // Assures, that the generated business partner ID is available.
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            // If something breaks a rollback is necessary!?
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw exception;
        } finally {
            entityManager.close();
        }
    }

    /**
     * Deletes a BusinessPartner.
     */
    @Override
    public void deleteBusinessPartner(final BusinessPartner businessPartner) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();

        this.checkBusinessPartnerInUse(businessPartner, entityManager);

        // In order to successfully delete an entity, it first has to be fetched from the database.
        BusinessPartner deleteBusinessPartner = entityManager.find(BusinessPartner.class, businessPartner.getId());

        entityManager.getTransaction().begin();

        try {
            entityManager.remove(deleteBusinessPartner);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            // If something breaks a rollback is necessary.
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw exception;
        } finally {
            entityManager.close();
        }
    }

    /**
     * Gets a BusinessPartner by a BPTypeQueryParameter.
     */
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
            criteriaQuery.orderBy(criteriaBuilder.asc(criteria.get("id"))); // Order by id ascending
            TypedQuery<BusinessPartner> typedQuery = entityManager.createQuery(criteriaQuery);
            businessPartners = typedQuery.getResultList();

            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            // If something breaks a rollback is necessary.
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw exception;
        } finally {
            entityManager.close();
        }

        return businessPartners;
    }

    /**
     * Gets a BusinessPartner by its id.
     */
    @Override
    public BusinessPartner getBusinessPartner(final Integer id) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();

        entityManager.getTransaction().begin();
        BusinessPartner businessPartner = entityManager.find(BusinessPartner.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();

        return businessPartner;
    }

    /**
     * Updates a BusinessPartner.
     */
    @Override
    public void updateBusinessPartner(final BusinessPartner businessPartner)
            throws ObjectUnchangedException, Exception {
        EntityManager entityManager;

        this.checkBusinessPartnerDataChanged(businessPartner);

        entityManager = this.sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(businessPartner);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * Checks if the data of the given business partner differ from the business partner that is persisted at database
     * level.
     *
     * @param businessPartner The business partner to be checked.
     * @throws ObjectUnchangedException In case the business partner has not been changed.
     * @throws Exception                In case an error occurred during determination of the business partner stored at
     *                                  the database.
     */
    private void checkBusinessPartnerDataChanged(final BusinessPartner businessPartner)
            throws ObjectUnchangedException, Exception {
        BusinessPartner databaseBusinessPartner = this.getBusinessPartner(businessPartner.getId());

        if (databaseBusinessPartner.equals(businessPartner)) {
            throw new ObjectUnchangedException();
        }
    }

    /**
     * Applies the type query parameter to the business partner query.
     *
     * @param bpTypeQuery     Specifies the business partners to be selected based on the type attribute.
     * @param bpCriteriaQuery The business partner criteria query.
     * @param criteriaBuilder The builder of criterias.
     * @param bpCriteria      Root type of the business partner in the from clause.
     */
    private void applyBPTypeQueryParameter(final BPTypeQueryParameter bpTypeQuery,
            final CriteriaQuery<BusinessPartner> bpCriteriaQuery, final CriteriaBuilder criteriaBuilder,
            final Root<BusinessPartner> bpCriteria) {

        Expression<Collection<BusinessPartnerType>> types = bpCriteria.get("types");
        BusinessPartnerType bpType = null;

        if (bpTypeQuery == null) {
            return;
        }

        // Convert the type that is used to define the query into the type that is used in the data model.
        switch (bpTypeQuery) {
        case CUSTOMER:
            bpType = BusinessPartnerType.CUSTOMER;
            break;
        case VENDOR:
            bpType = BusinessPartnerType.VENDOR;
            break;
        case ALL:
            return;
        default:
            return;
        }

        bpCriteriaQuery.where(criteriaBuilder.isMember(bpType, types));
    }

    /**
     * Checks if the business partner is referenced by another business object.
     *
     * @param businessPartner The business partner which is checked.
     * @param entityManager   The EntityManager used to create queries.
     * @throws ObjectInUseException In case the business partner is in use.
     */
    private void checkBusinessPartnerInUse(final BusinessPartner businessPartner, final EntityManager entityManager)
            throws ObjectInUseException {
        this.checkBusinessPartnerUsedInSalesOrder(businessPartner, entityManager);
        this.checkBusinessPartnerUsedInPurchaseOrder(businessPartner, entityManager);
        this.checkBusinessPartnerUsedInPosting(businessPartner, entityManager);
    }

    /**
     * Checks if the business partner is referenced in any sales order.
     *
     * @param businessPartner The business partner who is checked.
     * @param entityManager   The EntityManager used to create queries.
     * @throws ObjectInUseException In case the business partner is in use.
     */
    private void checkBusinessPartnerUsedInSalesOrder(final BusinessPartner businessPartner,
            final EntityManager entityManager) throws ObjectInUseException {
        SalesOrder salesOrder;
        List<SalesOrder> salesOrders;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SalesOrder> criteriaQuery = criteriaBuilder.createQuery(SalesOrder.class);

        Root<SalesOrder> criteria = criteriaQuery.from(SalesOrder.class);
        criteriaQuery.select(criteria).distinct(true);
        criteriaQuery.where(criteriaBuilder.or(criteriaBuilder.equal(criteria.get("soldToParty"), businessPartner),
                criteriaBuilder.equal(criteria.get("shipToParty"), businessPartner),
                criteriaBuilder.equal(criteria.get("billToParty"), businessPartner)));

        TypedQuery<SalesOrder> typedQuery = entityManager.createQuery(criteriaQuery);
        salesOrders = typedQuery.getResultList();

        if (salesOrders.size() > 0) {
            salesOrder = salesOrders.get(0);
            throw new ObjectInUseException(businessPartner.getId(), salesOrder.getId(), salesOrder);
        }
    }

    /**
     * Checks if the business partner is referenced in any purchase order.
     *
     * @param businessPartner The business partner who is checked.
     * @param entityManager   The EntityManager used to create queries.
     * @throws ObjectInUseException In case the business partner is in use.
     */
    private void checkBusinessPartnerUsedInPurchaseOrder(final BusinessPartner businessPartner,
            final EntityManager entityManager) throws ObjectInUseException {
        PurchaseOrder purchaseOrder;
        List<PurchaseOrder> purchaseOrders;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PurchaseOrder> criteriaQuery = criteriaBuilder.createQuery(PurchaseOrder.class);

        Root<PurchaseOrder> criteria = criteriaQuery.from(PurchaseOrder.class);
        criteriaQuery.select(criteria).distinct(true);
        criteriaQuery.where(criteriaBuilder.equal(criteria.get("vendor"), businessPartner));

        TypedQuery<PurchaseOrder> typedQuery = entityManager.createQuery(criteriaQuery);
        purchaseOrders = typedQuery.getResultList();

        if (purchaseOrders.size() > 0) {
            purchaseOrder = purchaseOrders.get(0);
            throw new ObjectInUseException(businessPartner.getId(), purchaseOrder.getId(), purchaseOrder);
        }
    }

    /**
     * Checks if the business partner is referenced in any posting.
     *
     * @param businessPartner The business partner who is checked.
     * @param entityManager   The EntityManager used to create queries.
     * @throws ObjectInUseException In case the business partner is in use.
     */
    private void checkBusinessPartnerUsedInPosting(final BusinessPartner businessPartner,
            final EntityManager entityManager) throws ObjectInUseException {
        Posting posting;
        List<Posting> postings;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Posting> criteriaQuery = criteriaBuilder.createQuery(Posting.class);

        Root<Posting> criteria = criteriaQuery.from(Posting.class);
        criteriaQuery.select(criteria).distinct(true);
        criteriaQuery.where(criteriaBuilder.equal(criteria.get("counterparty"), businessPartner));

        TypedQuery<Posting> typedQuery = entityManager.createQuery(criteriaQuery);
        postings = typedQuery.getResultList();

        if (postings.size() > 0) {
            posting = postings.get(0);
            throw new ObjectInUseException(businessPartner.getId(), posting.getId(), posting);
        }
    }
}
