package frontend.model;

import java.util.ArrayList;
import java.util.List;

import frontend.exception.EntityAlreadyExistsException;

/**
 * Encapsulates a list of multiple employees and provides convenience methods to work with that list.
 * 
 * @author Michael
 */
public class EmployeeList {
	/**
	 * The employees that are managed.
	 */
	private List<Employee> employees;
	
	
	/**
	 * The default constructor initializing an empty list.
	 */
	public EmployeeList() {
		this.employees = new ArrayList<Employee>();
	}
	
	
	/**
	 * A constructor creating a new EmployeeList with the given employees.
	 * 
	 * @param employees An ArrayList of employees the EmployeeList is being initialized with.
	 */
	public EmployeeList(final List<Employee> employees) {
		this.employees = employees;
	}
	
	
	/**
	 * @return the employees
	 */
	public List<Employee> getEmployees() {
		return employees;
	}


	/**
	 * @param employees the employees to set
	 */
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}


	/**
	 * Gets the employee with the given id.
	 * 
	 * @param id The id of the employee to be searched.
	 * @return The employee with the given ID. Null, if no employee with the given ID could be found.
	 */
	public Employee getEmployeeById(final Integer id) {
		for(Employee tempEmployee:this.employees) {
			if(tempEmployee.getId().equals(id))
				return tempEmployee;
		}
		
		return null;
	}
	
	
	/**
	 * Adds an employee to the employee list.
	 * 
	 * @param employee The employee to be added
	 * @throws EntityAlreadyExistsException In case the employee already exists.
	 */
	public void addEmployee(final Employee employee) throws EntityAlreadyExistsException {
		//Check if an employee with the given id already exists
		for(Employee tempEmployee:this.employees) {
			if(tempEmployee.getId().equals(employee.getId()))
				throw new EntityAlreadyExistsException();
		}
		
		this.employees.add(employee);
	}
	
	
	/**
	 * Deletes the given employee from the list.
	 * 
	 * @param employee The employee to be deleted.
	 */
	public void deleteEmployee(final Employee employee) {
		if(employee != null)
			this.employees.remove(employee);
	}
}
