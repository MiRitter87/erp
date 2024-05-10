package backend.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 * Provides native SQL command execution using Hibernate.
 *
 * @author Michael
 */
public class NativeSqlHibernateDao implements NativeSqlDao {
    /**
     * Factory for database session.
     */
    private EntityManagerFactory sessionFactory;

    /**
     * Default constructor.
     *
     * @param sessionFactory The database session factory.
     */
    public NativeSqlHibernateDao(final EntityManagerFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Executes the given SQL statement.
     */
    @Override
    public void executeStatement(final String sqlStatement) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Query q = entityManager.createNativeQuery(sqlStatement);

        try {
            q.executeUpdate();
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
}
