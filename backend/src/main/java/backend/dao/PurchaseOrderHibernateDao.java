package backend.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;

import backend.exception.ObjectUnchangedException;
import backend.model.purchaseOrder.PurchaseOrder;
import backend.model.purchaseOrder.PurchaseOrderStatus;

/**
 * Provides access to purchase order database persistence using Hibernate.
 *
 * @author Michael
 */
public class PurchaseOrderHibernateDao implements PurchaseOrderDao {
    /**
     * Factory for database session.
     */
    private EntityManagerFactory sessionFactory;

    /**
     * Default constructor.
     *
     * @param sessionFactory The database session factory.
     */
    public PurchaseOrderHibernateDao(final EntityManagerFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Inserts a PurchaseOrder.
     */
    @Override
    public void insertPurchaseOrder(final PurchaseOrder purchaseOrder) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            entityManager.persist(purchaseOrder);
            entityManager.flush(); // Assures, that the generated sales order ID is available.
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
     * Deletes a PurchaseOrder.
     */
    @Override
    public void deletePurchaseOrder(final PurchaseOrder purchaseOrder) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();

        // In order to successfully delete an entity, it first has to be fetched from the database.
        PurchaseOrder deletePurchaseOrder = entityManager.find(PurchaseOrder.class, purchaseOrder.getId());

        entityManager.getTransaction().begin();

        try {
            entityManager.remove(deletePurchaseOrder);
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
     * Gets purchase orders with the given status.
     */
    @Override
    public List<PurchaseOrder> getPurchaseOrders(final PurchaseOrderStatus orderStatusQuery) throws Exception {
        List<PurchaseOrder> purchaseOrders = null;
        EntityManager entityManager = this.sessionFactory.createEntityManager();

        // Use entity graphs to load data of referenced PurchaseOrderItem instances.
        EntityGraph<PurchaseOrder> graph = entityManager.createEntityGraph(PurchaseOrder.class);
        graph.addAttributeNodes("items", "status", "vendor", "paymentAccount");

        graph.addSubgraph("paymentAccount").addAttributeNodes("postings");
        graph.addSubgraph("paymentAccount").addSubgraph("postings").addAttributeNodes("counterparty");

        graph.addSubgraph("items").addAttributeNodes("material");

        entityManager.getTransaction().begin();

        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<PurchaseOrder> criteriaQuery = criteriaBuilder.createQuery(PurchaseOrder.class);
            Root<PurchaseOrder> criteria = criteriaQuery.from(PurchaseOrder.class);
            criteriaQuery.select(criteria);
            this.applyOrderStatusQueryParameter(orderStatusQuery, criteriaQuery, criteriaBuilder, criteria);
            criteriaQuery.orderBy(criteriaBuilder.asc(criteria.get("id"))); // Order by id ascending
            TypedQuery<PurchaseOrder> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setHint("jakarta.persistence.loadgraph", graph); // Also fetch all item data.
            purchaseOrders = typedQuery.getResultList();

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

        return purchaseOrders;
    }

    /**
     * Gets a PurchaseOrder by its ID.
     */
    @Override
    public PurchaseOrder getPurchaseOrder(final Integer id) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();

        // Use entity graphs to load data of referenced PurchaseOrderItem instances.
        EntityGraph<PurchaseOrder> graph = entityManager.createEntityGraph(PurchaseOrder.class);
        graph.addAttributeNodes("items", "status", "vendor", "paymentAccount");
        Map<String, Object> hints = new HashMap<String, Object>();
        hints.put("jakarta.persistence.loadgraph", graph);

        entityManager.getTransaction().begin();
        PurchaseOrder purchaseOrder = entityManager.find(PurchaseOrder.class, id, hints);
        entityManager.getTransaction().commit();
        entityManager.close();

        return purchaseOrder;
    }

    /**
     * Updates a PurchaseOrder.
     */
    @Override
    public void updatePurchaseOrder(final PurchaseOrder purchaseOrder) throws ObjectUnchangedException, Exception {
        EntityManager entityManager;

        this.checkPurchaseOrderDataChanged(purchaseOrder);

        entityManager = this.sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(purchaseOrder);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * Checks if the data of the given purchase order differ from the purchase order that is persisted at database
     * level.
     *
     * @param purchaseOrder The purchase order to be checked.
     * @throws ObjectUnchangedException In case the purchase order has not been changed.
     * @throws Exception                In case an error occurred during determination of the purchase order stored at
     *                                  the database.
     */
    private void checkPurchaseOrderDataChanged(final PurchaseOrder purchaseOrder)
            throws ObjectUnchangedException, Exception {
        PurchaseOrder databasePurchaseOrder = this.getPurchaseOrder(purchaseOrder.getId());

        if (databasePurchaseOrder.equals(purchaseOrder)) {
            throw new ObjectUnchangedException();
        }
    }

    /**
     * Applies the order status query parameter to the purchase order query.
     *
     * @param orderStatusQuery   The query parameter for purchase order status.
     * @param orderCriteriaQuery The purchase order criteria query.
     * @param criteriaBuilder    The builder of criterias.
     * @param criteria           The root entity of the purchase order that is being queried.
     */
    private void applyOrderStatusQueryParameter(final PurchaseOrderStatus orderStatusQuery,
            final CriteriaQuery<PurchaseOrder> orderCriteriaQuery, final CriteriaBuilder criteriaBuilder,
            final Root<PurchaseOrder> criteria) {

        Expression<Collection<PurchaseOrderStatus>> status = criteria.get("status");

        if (orderStatusQuery == null) {
            return; // No further query restrictions needed.
        }

        orderCriteriaQuery.where(criteriaBuilder.isMember(orderStatusQuery, status));
    }
}
