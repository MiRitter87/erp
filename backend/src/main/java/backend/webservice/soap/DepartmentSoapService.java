package backend.webservice.soap;

import javax.jws.WebParam;
import javax.jws.WebService;

import backend.model.Department;
import backend.model.webservice.WebServiceResult;

/**
 * WebService interface for department access using SOAP.
 * 
 * @author Michael
 */
@WebService(name="departmentService")
public interface DepartmentSoapService {
	/**
	 * Deletes the department with the given code.
	 * 
	 * @param code The code of the department.
	 * @return The result of the delete function.
	 */
	WebServiceResult deleteDepartment(@WebParam(name="code") final String code);
	
	
	/**
	 * Provides the department with the given code.
	 * 
	 * @param code The code of the department.
	 * @return The department with the given code, if found.
	 */
	WebServiceResult getDepartment(@WebParam(name="code") final String code);
	
	
	/**
	 * Provides a list of all departments.
	 * 	
	 * @return A list of all departments.
	 */
	WebServiceResult getDepartments();
	
	
	/**
	 * Adds a department.
	 * 
	 * @param department The department to be added.
	 * @return The result of the add function.
	 */
	WebServiceResult addDepartment(@WebParam(name="department") final Department department);
	
	
	/**
	 * Updates an existing department.
	 * 
	 * @param department The department to be updated.
	 * @return The result of the update function.
	 */
	WebServiceResult updateDepartment(@WebParam(name="department") final Department department);
}
