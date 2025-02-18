package backend.dao;

import java.util.List;

import backend.exception.ObjectUnchangedException;
import backend.model.employee.Employee;
import backend.model.employee.EmployeeHeadQueryParameter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

/**
 * Provides access to employee database persistence using Hibernate.
 *
 * @author Michael
 *
 */
public class EmployeeHibernateDao implements EmployeeDao {
    /**
     * Factory for database session.
     */
    private EntityManagerFactory sessionFactory;

    /**
     * Default constructor.
     *
     * @param sessionFactory The database session factory.
     */
    public EmployeeHibernateDao(final EntityManagerFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Inserts an Employee.
     */
    @Override
    public void insertEmpoyee(final Employee employee) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            entityManager.persist(employee);
            entityManager.flush(); // Assures, that the generated employee ID is available.
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
     * Deletes an Employee.
     */
    @Override
    public void deleteEmployee(final Employee employee) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();

        // In order to successfully delete an entity, it first has to be fetched from the database.
        Employee deleteEmployee = entityManager.find(Employee.class, employee.getId());

        entityManager.getTransaction().begin();

        try {
            entityManager.remove(deleteEmployee);
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
     * Gets all employees that match the given query parameters.
     */
    @Override
    public List<Employee> getEmployees(final EmployeeHeadQueryParameter employeeHeadQuery) throws Exception {
        List<Employee> employees = null;
        EntityManager entityManager = this.sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
            Root<Employee> criteria = criteriaQuery.from(Employee.class);
            criteriaQuery.select(criteria);
            this.applyEmployeeHeadQueryParameter(employeeHeadQuery, criteriaQuery, criteria);
            criteriaQuery.orderBy(criteriaBuilder.asc(criteria.get("id"))); // Order by id ascending
            TypedQuery<Employee> typedQuery = entityManager.createQuery(criteriaQuery);
            employees = typedQuery.getResultList();

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

        return employees;
    }

    /**
     * Gets the employee with the given id.
     */
    @Override
    public Employee getEmployee(final Integer id) throws Exception {
        EntityManager entityManager = this.sessionFactory.createEntityManager();

        entityManager.getTransaction().begin();
        Employee employee = entityManager.find(Employee.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();

        return employee;
    }

    /**
     * Updates the given employee.
     */
    @Override
    public void updateEmployee(final Employee employee) throws ObjectUnchangedException, Exception {
        EntityManager entityManager;

        this.checkEmployeeDataChanged(employee);

        entityManager = this.sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(employee);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * Checks if the data of the given employee differ from the employee that is persisted at database level.
     *
     * @param employee The employee to be checked.
     * @throws ObjectUnchangedException In case the employee has not been changed.
     * @throws Exception                In case an error occurred during determination of the employee stored at the
     *                                  database.
     */
    private void checkEmployeeDataChanged(final Employee employee) throws ObjectUnchangedException, Exception {
        Employee databaseEmployee = this.getEmployee(employee.getId());

        if (databaseEmployee.equals(employee)) {
            throw new ObjectUnchangedException();
        }
    }

    /**
     * Applies the employee head query parameter to the employee query.
     *
     * @param employeeHeadQuery     Specifies the employees to be selected based on the head attribute.
     * @param employeeCriteriaQuery The employee criteria query.
     * @param employeeCriteria      Root type of the employee in the from clause.
     */
    private void applyEmployeeHeadQueryParameter(final EmployeeHeadQueryParameter employeeHeadQuery,
            final CriteriaQuery<Employee> employeeCriteriaQuery, final Root<Employee> employeeCriteria) {

        if (employeeHeadQuery == EmployeeHeadQueryParameter.ALL || employeeHeadQuery == null) {
            return; // No further query restrictions needed.
        }

        if (employeeHeadQuery == EmployeeHeadQueryParameter.NO_HEAD_ONLY) {
            // Select only those employees that have no head defined.
            employeeCriteriaQuery.where(employeeCriteria.get("headOfDepartment").isNull());
        }

        if (employeeHeadQuery == EmployeeHeadQueryParameter.HEAD_ONLY) {
            // Select only those employees that are head of any department.
            employeeCriteriaQuery.where(employeeCriteria.get("headOfDepartment").isNotNull());
        }
    }
}
