package backend.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import backend.exception.ObjectUnchangedException;
import backend.model.Employee;

/**
 * Provides access to employee database persistence using hibernate.
 * 
 * @author Michael
 *
 */
public class EmployeeHibernateDao extends HibernateDao implements EmployeeDao {
	/**
	 * Default constructor.
	 */
	public EmployeeHibernateDao() {

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
	public List<Employee> getEmployees() throws Exception {
		List<Employee> employees = null;
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
			Root<Employee> criteria = criteriaQuery.from(Employee.class);
			criteriaQuery.select(criteria);
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
}
