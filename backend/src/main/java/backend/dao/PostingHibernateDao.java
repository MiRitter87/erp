package backend.dao;

import java.util.HashMap;
import java.util.Map;

import backend.model.account.Posting;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

/**
 * Provides access to posting database persistence using Hibernate.
 *
 * @author Michael
 */
public class PostingHibernateDao implements PostingDao {
    /**
     * Factory for database session.
     */
    private EntityManagerFactory sessionFactory;

    /**
     * Default constructor.
     *
     * @param sessionFactory The database session factory.
     */
    public PostingHibernateDao(final EntityManagerFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Inserts a Posting.
     */
    @Override
    public void insertPosting(final Posting posting) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            entityManager.persist(posting);
            entityManager.flush(); // Assures, that the generated posting ID is available.
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
     * Deletes a Posting.
     */
    @Override
    public void deletePosting(final Posting posting) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();

        // In order to successfully delete an entity, it first has to be fetched from the database.
        Posting deletePosting = entityManager.find(Posting.class, posting.getId());

        entityManager.getTransaction().begin();

        try {
            entityManager.remove(deletePosting);
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
     * Gets the Posting with the given id.
     */
    @Override
    public Posting getPosting(final Integer id) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();

        // Use entity graphs to load data of referenced Posting instances.
        EntityGraph<Posting> graph = entityManager.createEntityGraph(Posting.class);
        graph.addAttributeNodes("counterparty");
        Map<String, Object> hints = new HashMap<String, Object>();
        hints.put("jakarta.persistence.loadgraph", graph);

        entityManager.getTransaction().begin();
        Posting posting = entityManager.find(Posting.class, id, hints);
        entityManager.getTransaction().commit();
        entityManager.close();

        return posting;
    }
}
