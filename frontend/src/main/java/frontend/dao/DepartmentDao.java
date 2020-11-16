package frontend.dao;

import java.util.List;

import frontend.model.Department;

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
	 * @exception Exception Insertion failed.
	 */
	void insertDepartment(final Department department) throws Exception;
	
	/**
	 * Deletes a department.
	 * 
	 * @param department The department to be deleted.
	 * @exception Exception Deletion failed.
	 */
	void deleteDepartment(final Department department) throws Exception;
	
	/**
	 * Gets all departments.
	 * 
	 * @return All departments.
	 * @exception Exception Department retrieval failed.
	 */
	List<Department> getDepartments() throws Exception;
	
	/**
	 * Updates an existing department.
	 * 
	 * @param department The department to be updated.
	 * @throws Exception Update failed.
	 */
	void updateDepartment(final Department department) throws Exception;
}
