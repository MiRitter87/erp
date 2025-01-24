package backend.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import backend.exception.ObjectUnchangedException;
import backend.model.account.Account;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

/**
 * Provides access to account database persistence using Hibernate.
 *
 * @author Michael
 */
public class AccountHibernateDao implements AccountDao {
    /**
     * Factory for database session.
     */
    private EntityManagerFactory sessionFactory;

    /**
     * Default constructor.
     *
     * @param sessionFactory The database session factory.
     */
    public AccountHibernateDao(final EntityManagerFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Inserts an Account.
     */
    @Override
    public void insertAccount(final Account account) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            entityManager.persist(account);
            entityManager.flush(); // Assures, that the generated account ID is available.
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
     * Deletes an Account.
     */
    @Override
    public void deleteAccount(final Account account) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();

        // In order to successfully delete an entity, it first has to be fetched from the database.
        Account deleteAccount = entityManager.find(Account.class, account.getId());

        entityManager.getTransaction().begin();

        try {
            entityManager.remove(deleteAccount);
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
     * Gets all accounts.
     */
    @Override
    public List<Account> getAccounts() throws Exception {
        List<Account> accounts = null;
        EntityManager entityManager = this.sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
            Root<Account> criteria = criteriaQuery.from(Account.class);
            criteriaQuery.select(criteria);
            criteriaQuery.orderBy(criteriaBuilder.asc(criteria.get("id"))); // Order by id ascending
            TypedQuery<Account> typedQuery = entityManager.createQuery(criteriaQuery);
            accounts = typedQuery.getResultList();

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

        return accounts;
    }

    /**
     * Gets an Account.
     */
    @Override
    public Account getAccount(final Integer id) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();

        // Use entity graphs to load data of referenced Posting instances.
        EntityGraph<Account> graph = entityManager.createEntityGraph(Account.class);
        graph.addAttributeNodes("postings");
        graph.addSubgraph("postings").addAttributeNodes("counterparty");
        Map<String, Object> hints = new HashMap<String, Object>();
        hints.put("jakarta.persistence.loadgraph", graph);

        entityManager.getTransaction().begin();
        Account account = entityManager.find(Account.class, id, hints);
        entityManager.getTransaction().commit();
        entityManager.close();

        return account;
    }

    /**
     * Updates an Account.
     */
    @Override
    public void updateAccount(final Account account) throws ObjectUnchangedException, Exception {
        EntityManager entityManager;

        this.checkAccountDataChanged(account);

        entityManager = this.sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(account);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * Checks if the data of the given account differ from the account that is persisted at database level.
     *
     * @param account The account to be checked.
     * @throws ObjectUnchangedException In case the account has not been changed.
     * @throws Exception                In case an error occurred during determination of the account stored at the
     *                                  database.
     */
    private void checkAccountDataChanged(final Account account) throws ObjectUnchangedException, Exception {
        Account databaseAccount = this.getAccount(account.getId());

        if (databaseAccount.equals(account)) {
            throw new ObjectUnchangedException();
        }
    }
}
