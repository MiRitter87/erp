package backend.dao;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import backend.exception.ObjectUnchangedException;
import backend.model.department.Department;

/**
 * Provides access to department database persistence using Hibernate.
 *
 * @author Michael
 *
 */
public class DepartmentHibernateDao implements DepartmentDao {
    /**
     * Factory for database session.
     */
    private EntityManagerFactory sessionFactory;

    /**
     * Default constructor.
     *
     * @param sessionFactory The database session factory.
     */
    public DepartmentHibernateDao(final EntityManagerFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Inserts a Department.
     */
    @Override
    public void insertDepartment(final Department department) throws EntityExistsException, Exception {
        // Check if department already exists. Somehow persist() does not throw that exception as expected.
        if (this.getDepartmentByCode(department.getCode()) != null) {
            throw new EntityExistsException();
        }

        EntityManager entityManager = this.sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            entityManager.persist(department);
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
     * Deletes a Department.
     */
    @Override
    public void deleteDepartment(final Department department) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();

        // In order to successfully delete an entity, it first has to be fetched from the database.
        Department deleteDepartment = entityManager.find(Department.class, department.getCode());

        entityManager.getTransaction().begin();

        try {
            entityManager.remove(deleteDepartment);
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
     * Get all departments.
     */
    @Override
    public List<Department> getDepartments() throws Exception {
        List<Department> departments = null;
        EntityManager entityManager = this.sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Department> criteriaQuery = criteriaBuilder.createQuery(Department.class);
            Root<Department> criteria = criteriaQuery.from(Department.class);
            criteriaQuery.select(criteria);
            criteriaQuery.orderBy(criteriaBuilder.asc(criteria.get("code"))); // Order by id ascending
            TypedQuery<Department> typedQuery = entityManager.createQuery(criteriaQuery);
            departments = typedQuery.getResultList();

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

        return departments;
    }

    /**
     * Gets a Department by code.
     */
    @Override
    public Department getDepartmentByCode(final String code) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();

        entityManager.getTransaction().begin();
        Department department = entityManager.find(Department.class, code);
        entityManager.getTransaction().commit();
        entityManager.close();

        return department;
    }

    /**
     * Updates a Department.
     */
    @Override
    public void updateDepartment(final Department department) throws ObjectUnchangedException, Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();

        this.checkDepartmentDataChanged(department);

        entityManager.getTransaction().begin();
        entityManager.merge(department);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * Gets a Department by an employee id.
     */
    @Override
    public Department getDepartmentByEmployeeId(final Integer employeeId) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();

        // Build the query: Select the department where the field employee_id matches the given parameter.
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Department> criteriaQuery = builder.createQuery(Department.class);
        Root<Department> root = criteriaQuery.from(Department.class);
        ParameterExpression<Integer> pEmployeeId = builder.parameter(Integer.class);
        Path<String> pathEmployeeId = root.join("head").get("id"); // The employee_id is not at department level but at
                                                                   // employee ->join.
        criteriaQuery.select(root).where(builder.equal(pathEmployeeId, pEmployeeId));

        // Fill the query parameter and execute the query
        TypedQuery<Department> query = entityManager.createQuery(criteriaQuery);
        query.setParameter(pEmployeeId, employeeId);
        List<Department> results = query.getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        if (!results.isEmpty()) {
            return results.get(0);
        } else {
            return null;
        }
    }

    /**
     * Checks if the data of the given department differ from the department that is persisted at database level.
     *
     * @param department The department to be checked.
     * @throws ObjectUnchangedException In case the department has not been changed.
     * @throws Exception                In case an error occurred during determination of the department stored at the
     *                                  database.
     */
    private void checkDepartmentDataChanged(final Department department) throws ObjectUnchangedException, Exception {
        Department databaseDepartment = this.getDepartmentByCode(department.getCode());

        if (databaseDepartment.equals(department)) {
            throw new ObjectUnchangedException();
        }
    }
}
