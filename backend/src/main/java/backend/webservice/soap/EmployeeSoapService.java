package backend.webservice.soap;

import jakarta.jws.WebParam;
import jakarta.jws.WebService;

import backend.model.employee.Employee;
import backend.model.employee.EmployeeHeadQueryParameter;
import backend.model.webservice.WebServiceResult;

/**
 * WebService interface for employee access using SOAP.
 *
 * @author Michael
 */
@WebService(name = "employeeService")
public interface EmployeeSoapService {
    /**
     * Deletes the employee with the given id.
     *
     * @param id The id of the employee to be deleted.
     * @return The result of the delete function.
     */
    WebServiceResult deleteEmployee(@WebParam(name = "id") Integer id);

    /**
     * Provides the employee with the given id.
     *
     * @param id The id of the employee.
     * @return The employee with the given id, if found.
     */
    WebServiceResult getEmployee(@WebParam(name = "id") Integer id);

    /**
     * Provides a list of all employees.
     *
     * @param employeeHeadQuery Specifies the employees to be selected based on the head attribute.
     * @return A list of all employees that match the given query parameters.
     */
    WebServiceResult getEmployees(@WebParam(name = "employeeHeadQuery") EmployeeHeadQueryParameter employeeHeadQuery);

    /**
     * Adds an employee.
     *
     * @param employee The employee to be added.
     * @return The result of the add function.
     */
    WebServiceResult addEmployee(@WebParam(name = "employee") Employee employee);

    /**
     * Updates an existing employee.
     *
     * @param employee The employee to be updated.
     * @return The result of the update function.
     */
    WebServiceResult updateEmployee(@WebParam(name = "employee") Employee employee);
}
