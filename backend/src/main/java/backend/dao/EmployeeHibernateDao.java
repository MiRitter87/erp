package backend.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import backend.exception.ObjectUnchangedException;
import backend.model.Department;
import backend.model.Employee;
import backend.model.webservice.EmployeeHeadQueryParameter;

/**
 * Provides access to employee database persistence using hibernate.
 * 
 * @author Michael
 *
 */
public class EmployeeHibernateDao implements EmployeeDao {
	/**
	 * Factory for database session.
	 */
	protected EntityManagerFactory sessionFactory;
	
	/**
	 * Default constructor.
	 * 
	 * @param sessionFactory The database session factory.
	 */
	public EmployeeHibernateDao(final EntityManagerFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	

	@Override
	public void insertEmpoyee(Employee employee) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		try {
			entityManager.persist(employee);
			entityManager.flush();	//Assures, that the generated employee ID is available.
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
	public void deleteEmployee(Employee employee) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		
		//In order to successfully delete an entity, it first has to be fetched from the database.
		Employee deleteEmployee = entityManager.find(Employee.class, employee.getId());
		
		entityManager.getTransaction().begin();
		
		try {
			entityManager.remove(deleteEmployee);
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
			criteriaQuery.orderBy(criteriaBuilder.asc(criteria.get("id")));	//Order by id ascending
			TypedQuery<Employee> typedQuery = entityManager.createQuery(criteriaQuery);
			employees = typedQuery.getResultList();
			
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
		
		return employees;
	}
	
	
	@Override
	public Employee getEmployee(Integer id) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
		Employee employee = entityManager.find(Employee.class, id);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return employee;
	}
	
	
	@Override
	public void updateEmployee(Employee employee) throws ObjectUnchangedException, Exception {
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
	 * @throws Exception In case an error occurred during determination of the employee stored at the database.
	 */
	private void checkEmployeeDataChanged(final Employee employee) throws ObjectUnchangedException, Exception {
		Employee databaseEmployee = this.getEmployee(employee.getId());
		
		if(databaseEmployee.equals(employee))
			throw new ObjectUnchangedException();
	}
	
	
	/**
	 * Applies the employee head query parameter to the employee query.
	 * 
	 * @param employeeHeadQuery Specifies the employees to be selected based on the head attribute.
	 * @param employeeCriteriaQuery The employee criteria query.
	 * @param employeeCriteria Root type of the employee in the from clause.
	 */
	private void applyEmployeeHeadQueryParameter(final EmployeeHeadQueryParameter employeeHeadQuery, 
			CriteriaQuery<Employee> employeeCriteriaQuery, final Root<Employee> employeeCriteria) {
		
		Subquery<Department> subQuery;
		Root<Department> subRoot;
		
		if(employeeHeadQuery == EmployeeHeadQueryParameter.ALL || employeeHeadQuery == null)
			return;	//No further query restrictions needed.
		
		if(employeeHeadQuery == EmployeeHeadQueryParameter.NO_HEAD_ONLY) {
			//Use SubQuery: Get all departments - each department has to have a head.
			subQuery = employeeCriteriaQuery.subquery(Department.class);
			subRoot = subQuery.from(Department.class);
			subQuery.select(subRoot.get("head"));
			
			//Select only those employees whose head is not in the set of all existing heads -> Employee has no head defined.
			employeeCriteriaQuery.where(employeeCriteria.get("headOfDepartment").in(subQuery).not());
		}
		
		if(employeeHeadQuery == EmployeeHeadQueryParameter.HEAD_ONLY) {
			//Use SubQuery: Get all departments - each department has to have a head.
			
			subQuery = employeeCriteriaQuery.subquery(Department.class);
			subRoot = subQuery.from(Department.class);
			subQuery.select(subRoot.get("head"));
			
			//Select only those employees whose head is not in the set of all existing heads.
			employeeCriteriaQuery.where(employeeCriteria.get("headOfDepartment").in(subQuery));
		}
	}
}
