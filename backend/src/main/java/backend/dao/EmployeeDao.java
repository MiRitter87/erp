package backend.dao;

import java.util.List;

import backend.model.Employee;

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
	 * @exception Exception Insertion failed.
	 */
	void insertEmpoyee(final Employee employee) throws Exception;
	
	/**
	 * Deletes an employee.
	 * 
	 * @param employee The employee to be deleted.
	 * @exception Exception Deletion failed.
	 */
	void deleteEmployee(final Employee employee) throws Exception;
	
	/**
	 * Gets all employees.
	 * 
	 * @return All employees.
	 * @exception Exception Employee retrieval failed.
	 */
	List<Employee> getEmployees() throws Exception;
	
	/**
	 * Gets the employee with the given id.
	 * 
	 * @param id The id of the employee.
	 * @return The employee with the given id.
	 * @throws Exception Employee retrieval failed.
	 */
	Employee getEmployee(final Integer id) throws Exception;
	
	/**
	 * Updates the given employee
	 * 
	 * @param employee The employee to be updated.
	 * @throws Exception Employee update failed.
	 */
	void updateEmployee(final Employee employee) throws Exception;
}
