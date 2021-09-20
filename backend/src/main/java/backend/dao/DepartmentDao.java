package backend.dao;

import java.util.List;

import javax.persistence.EntityExistsException;

import backend.exception.ObjectUnchangedException;
import backend.model.department.Department;

/**
 * Interface for department persistence.
 * 
 * @author Michael
 */
public interface DepartmentDao {
	/**
	 * Inserts a department.
	 * 
	 * @param department The department to be inserted.
	 * @throws EntityExistsException Department already exists.
	 * @throws Exception Insertion failed.
	 */
	void insertDepartment(final Department department) throws EntityExistsException, Exception;
	
	
	/**
	 * Deletes a department.
	 * 
	 * @param department The department to be deleted.
	 * @throws Exception Deletion failed.
	 */
	void deleteDepartment(final Department department) throws Exception;
	
	
	/**
	 * Gets all departments.
	 * 
	 * @return All departments.
	 * @throws Exception Department retrieval failed.
	 */
	List<Department> getDepartments() throws Exception;
	
	
	/**
	 * Gets the department with the given code.
	 * 
	 * @param code The code of the department.
	 * @return The department with the given code.
	 * @throws Exception Department retrieval failed.
	 */
	Department getDepartmentByCode(final String code) throws Exception;
	
	/**
	 * Get the department with the given employee ID as head of department.
	 * 
	 * @param employeeId The employee ID of the department head.
	 * @return The department of which the given employee is head.
	 * @throws Exception Department retrieval failed.
	 */
	Department getDepartmentByEmployeeId(final Integer employeeId) throws Exception;
	
	
	/**
	 * Updates the given department.
	 * 
	 * @param department The department to be updated.
	 * @throws ObjectUnchangedException The department has not been changed.
	 * @throws Exception Department update failed.
	 */
	void updateDepartment(final Department department) throws ObjectUnchangedException, Exception;
}
