package frontend.dao;

import java.util.List;

import frontend.model.Employee;

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
	 * Updates an existing employee.
	 * 
	 * @param employee The employee to be updated.
	 * @throws Exception Update failed.
	 */
	void updateEmployee(final Employee employee) throws Exception;
}
