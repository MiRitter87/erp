package backend.model.employee;

/**
 * Query Parameter for employee selection. Specifies the employees to be selected based on the head attribute.
 * 
 * @author Michael
 */
public enum EmployeeHeadQueryParameter {
	/**
	 * Select all employees.
	 */
	ALL,
	
	/**
	 * Select only employees that are head of any department.
	 */
	HEAD_ONLY,
	
	/**
	 * Select only employees that are not head of any department.
	 */
	NO_HEAD_ONLY
}
