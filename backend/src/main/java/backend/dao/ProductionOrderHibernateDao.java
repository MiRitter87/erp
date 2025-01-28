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
import backend.model.productionOrder.ProductionOrder;
import backend.model.productionOrder.ProductionOrderStatus;

/**
 * Provides access to ProductionOrder database persistence using Hibernate.
 *
 * @author Michael
 */
public class ProductionOrderHibernateDao implements ProductionOrderDao {
    /**
     * Factory for database session.
     */
    private EntityManagerFactory sessionFactory;

    /**
     * Default constructor.
     *
     * @param sessionFactory The database session factory.
     */
    public ProductionOrderHibernateDao(final EntityManagerFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Inserts a ProductionOrder.
     */
    @Override
    public void insertProductionOrder(final ProductionOrder productionOrder) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            entityManager.persist(productionOrder);
            entityManager.flush(); // Assures, that the generated production order ID is available.
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
     * Deletes a ProductionOrder.
     */
    @Override
    public void deleteProductionOrder(final ProductionOrder productionOrder) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();

        // In order to successfully delete an entity, it first has to be fetched from the database.
        ProductionOrder deleteProductionOrder = entityManager.find(ProductionOrder.class, productionOrder.getId());

        entityManager.getTransaction().begin();

        try {
            entityManager.remove(deleteProductionOrder);
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
     * Gets all production orders.
     */
    @Override
    public List<ProductionOrder> getProductionOrders(final ProductionOrderStatus orderStatusQuery) throws Exception {
        List<ProductionOrder> productionOrders = null;
        EntityManager entityManager = this.sessionFactory.createEntityManager();

        // Use entity graphs to load data of referenced ProductionOrderItem instances.
        EntityGraph<ProductionOrder> graph = entityManager.createEntityGraph(ProductionOrder.class);
        graph.addAttributeNodes("items");
        graph.addSubgraph("items").addAttributeNodes("material");

        entityManager.getTransaction().begin();

        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<ProductionOrder> criteriaQuery = criteriaBuilder.createQuery(ProductionOrder.class);
            Root<ProductionOrder> criteria = criteriaQuery.from(ProductionOrder.class);
            criteriaQuery.select(criteria);
            this.applyOrderStatusQueryParameter(orderStatusQuery, criteriaQuery, criteriaBuilder, criteria);
            criteriaQuery.orderBy(criteriaBuilder.asc(criteria.get("id"))); // Order by id ascending
            TypedQuery<ProductionOrder> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setHint("jakarta.persistence.loadgraph", graph); // Also fetch all item data.
            productionOrders = typedQuery.getResultList();

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

        return productionOrders;
    }

    /**
     * Gets the ProductionOrder with the given id.
     */
    @Override
    public ProductionOrder getProductionOrder(final Integer id) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();

        // Use entity graphs to load data of referenced ProductionOrderItem instances.
        EntityGraph<ProductionOrder> graph = entityManager.createEntityGraph(ProductionOrder.class);
        graph.addAttributeNodes("items");
        graph.addSubgraph("items").addAttributeNodes("material");
        Map<String, Object> hints = new HashMap<String, Object>();
        hints.put("jakarta.persistence.loadgraph", graph);

        entityManager.getTransaction().begin();
        ProductionOrder productionOrder = entityManager.find(ProductionOrder.class, id, hints);
        entityManager.getTransaction().commit();
        entityManager.close();

        return productionOrder;
    }

    /**
     * Updates the given ProductionOrder.
     */
    @Override
    public void updateProductionOrder(final ProductionOrder productionOrder)
            throws ObjectUnchangedException, Exception {
        EntityManager entityManager;

        this.checkProductionOrderDataChanged(productionOrder);

        entityManager = this.sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(productionOrder);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * Checks if the data of the given production order differ from the production order that is persisted at database
     * level.
     *
     * @param productionOrder The production order to be checked.
     * @throws ObjectUnchangedException In case the production order has not been changed.
     * @throws Exception                In case an error occurred during determination of the production order stored at
     *                                  the database.
     */
    private void checkProductionOrderDataChanged(final ProductionOrder productionOrder)
            throws ObjectUnchangedException, Exception {
        ProductionOrder databaseProductionOrder = this.getProductionOrder(productionOrder.getId());

        if (databaseProductionOrder.equals(productionOrder)) {
            throw new ObjectUnchangedException();
        }
    }

    /**
     * Applies the order status query parameter to the production order query.
     *
     * @param orderStatusQuery   The query parameter for production order status.
     * @param orderCriteriaQuery The production order criteria query.
     * @param criteriaBuilder    The builder of criterias.
     * @param criteria           The root entity of the production order that is being queried.
     */
    private void applyOrderStatusQueryParameter(final ProductionOrderStatus orderStatusQuery,
            final CriteriaQuery<ProductionOrder> orderCriteriaQuery, final CriteriaBuilder criteriaBuilder,
            final Root<ProductionOrder> criteria) {

        if (orderStatusQuery == null) {
            return; // No further query restrictions needed.
        }

        orderCriteriaQuery.where(criteriaBuilder.equal(criteria.get("status"), orderStatusQuery));
    }
}
