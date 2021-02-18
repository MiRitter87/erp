package backend.dao;

import java.util.List;

import backend.exception.ObjectUnchangedException;
import backend.model.Employee;
import backend.model.webservice.EmployeeHeadQueryParameter;

/**
 * Interface for employee persistence.
 * 
 * @author Michael
 */
public interface EmployeeDao {
	/**
	 * Inserts an employee.
	 * 
	 * @param employee The employee to be inserted.
	 * @throws Exception Insertion failed.
	 */
	void insertEmpoyee(final Employee employee) throws Exception;
	
	/**
	 * Deletes an employee.
	 * 
	 * @param employee The employee to be deleted.
	 * @throws Exception Deletion failed.
	 */
	void deleteEmployee(final Employee employee) throws Exception;
	
	/**
	 * Gets all employees that match the given query parameters.
	 * 
	 * @param employeeHeadQuery Specifies the employees to be selected based on the head attribute.
	 * @return A list of all employees that match the given query parameters.
	 * @throws Exception Employee retrieval failed.
	 */
	List<Employee> getEmployees(final EmployeeHeadQueryParameter employeeHeadQuery) throws Exception;
	
	/**
	 * Gets the employee with the given id.
	 * 
	 * @param id The id of the employee.
	 * @return The employee with the given id.
	 * @throws Exception Employee retrieval failed.
	 */
	Employee getEmployee(final Integer id) throws Exception;
	
	
	/**
	 *
	 * Updates the given employee
	 * 
	 * @param employee The employee to be updated.
	 * @throws ObjectUnchangedException The employee has not been changed.
	 * @throws Exception Employee update failed.
	 */
	void updateEmployee(final Employee employee) throws ObjectUnchangedException, Exception;
}
