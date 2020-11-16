package backend.webservice.soap;

import javax.jws.*;

import backend.model.Employee;
import backend.model.WebServiceResult;

/**
 * WebService interface for employee access using SOAP.
 * 
 * @author Michael
 */
@WebService(name="employeeService")
public interface EmployeeSoapService {
	/**
	 * Deletes the employee with the given id.
	 * 
	 * @param id The id of the employee to be deleted.
	 * @return The result of the delete function.
	 */
	WebServiceResult deleteEmployee(@WebParam(name="id") final Integer id);
	
	/**
	 * Provides the employee with the given id.
	 * 
	 * @param id The id of the employee.
	 * @return The employee with the given id, if found.
	 */
	WebServiceResult getEmployee(@WebParam(name="id") final Integer id);
	
	/**
	 * Provides a list of all employees.
	 * 
	 * @return A list of all employees.
	 */
	WebServiceResult getEmployees();
	
	/**
	 * Adds an employee.
	 * 
	 * @param employee The employee to be added.
	 * @return The result of the add function.
	 */
	WebServiceResult addEmployee(@WebParam(name="employee") final Employee employee);
	
	/**
	 * Updates an existing employee.
	 * 
	 * @param employee The employee to be updated.
	 * @return The result of the update function.
	 */
	WebServiceResult updateEmployee(@WebParam(name="employee") final Employee employee);
}
