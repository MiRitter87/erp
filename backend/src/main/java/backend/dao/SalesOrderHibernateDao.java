package backend.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import backend.exception.ObjectUnchangedException;
import backend.model.salesOrder.SalesOrder;
import backend.model.salesOrder.SalesOrderStatus;

/**
 * Provides access to sales order database persistence using Hibernate.
 *
 * @author Michael
 */
public class SalesOrderHibernateDao implements SalesOrderDao {
    /**
     * Factory for database session.
     */
    private EntityManagerFactory sessionFactory;

    /**
     * Default constructor.
     *
     * @param sessionFactory The database session factory.
     */
    public SalesOrderHibernateDao(final EntityManagerFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Inserts a SalesOrder.
     */
    @Override
    public void insertSalesOrder(final SalesOrder salesOrder) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            entityManager.persist(salesOrder);
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
     * Deletes a SalesOrder.
     */
    @Override
    public void deleteSalesOrder(final SalesOrder salesOrder) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();

        // In order to successfully delete an entity, it first has to be fetched from the database.
        SalesOrder deleteSalesOrder = entityManager.find(SalesOrder.class, salesOrder.getId());

        entityManager.getTransaction().begin();

        try {
            entityManager.remove(deleteSalesOrder);
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
     * Gets sales orders with the given status.
     */
    @Override
    public List<SalesOrder> getSalesOrders(final SalesOrderStatus orderStatusQuery) throws Exception {
        List<SalesOrder> salesOrders = null;
        EntityManager entityManager = this.sessionFactory.createEntityManager();

        // Use entity graphs to load data of referenced SalesOrderItem instances.
        EntityGraph<SalesOrder> graph = entityManager.createEntityGraph(SalesOrder.class);
        graph.addAttributeNodes("items", "soldToParty", "shipToParty", "billToParty", "paymentAccount");
        graph.addSubgraph("items").addAttributeNodes("material");

        entityManager.getTransaction().begin();

        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<SalesOrder> criteriaQuery = criteriaBuilder.createQuery(SalesOrder.class);
            Root<SalesOrder> criteria = criteriaQuery.from(SalesOrder.class);
            criteriaQuery.select(criteria);
            this.applyOrderStatusQueryParameter(orderStatusQuery, criteriaQuery, criteriaBuilder, criteria);
            criteriaQuery.orderBy(criteriaBuilder.asc(criteria.get("id"))); // Order by id ascending
            TypedQuery<SalesOrder> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setHint("jakarta.persistence.loadgraph", graph); // Also fetch all item data.
            salesOrders = typedQuery.getResultList();

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

        return salesOrders;
    }

    /**
     * Gets the SalesOrder with the given ID.
     */
    @Override
    public SalesOrder getSalesOrder(final Integer id) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();

        // Use entity graphs to load data of referenced SalesOrderItem instances.
        EntityGraph<SalesOrder> graph = entityManager.createEntityGraph(SalesOrder.class);
        graph.addAttributeNodes("items", "soldToParty", "shipToParty", "billToParty", "paymentAccount");
        graph.addSubgraph("items").addAttributeNodes("material");
        Map<String, Object> hints = new HashMap<String, Object>();
        hints.put("jakarta.persistence.loadgraph", graph);

        entityManager.getTransaction().begin();
        SalesOrder salesOrder = entityManager.find(SalesOrder.class, id, hints);
        entityManager.getTransaction().commit();
        entityManager.close();

        return salesOrder;
    }

    /**
     * Updates a SalesOrder.
     */
    @Override
    public void updateSalesOrder(final SalesOrder salesOrder) throws ObjectUnchangedException, Exception {
        EntityManager entityManager;

        this.checkSalesOrderDataChanged(salesOrder);

        entityManager = this.sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(salesOrder);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * Checks if the data of the given sales order differ from the sales order that is persisted at database level.
     *
     * @param salesOrder The sales order to be checked.
     * @throws ObjectUnchangedException In case the sales order has not been changed.
     * @throws Exception                In case an error occurred during determination of the sales order stored at the
     *                                  database.
     */
    private void checkSalesOrderDataChanged(final SalesOrder salesOrder) throws ObjectUnchangedException, Exception {
        SalesOrder databaseSalesOrder = this.getSalesOrder(salesOrder.getId());

        if (databaseSalesOrder.equals(salesOrder)) {
            throw new ObjectUnchangedException();
        }
    }

    /**
     * Applies the order status query parameter to the sales order query.
     *
     * @param orderStatusQuery   The query parameter for sales order status.
     * @param orderCriteriaQuery The sales order criteria query.
     * @param criteriaBuilder    The builder of criterias.
     * @param criteria           The root entity of the sales order that is being queried.
     */
    private void applyOrderStatusQueryParameter(final SalesOrderStatus orderStatusQuery,
            final CriteriaQuery<SalesOrder> orderCriteriaQuery, final CriteriaBuilder criteriaBuilder,
            final Root<SalesOrder> criteria) {

        if (orderStatusQuery == null) {
            return; // No further query restrictions needed.
        }

        orderCriteriaQuery.where(criteriaBuilder.equal(criteria.get("status"), orderStatusQuery));
    }
}
