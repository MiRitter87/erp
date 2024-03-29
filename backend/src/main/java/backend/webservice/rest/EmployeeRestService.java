package backend.webservice.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import backend.model.employee.Employee;
import backend.model.employee.EmployeeHeadQueryParameter;
import backend.model.webservice.WebServiceResult;
import backend.webservice.common.EmployeeService;

/**
 * WebService for employee access using REST technology.
 * 
 * @author Michael
 */
@Path("/employees")
public class EmployeeRestService {
	/**
	 * Provides the employee with the given id.
	 * 
	 * @param id The id of the employee.
	 * @return The employee with the given id, if found.
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public WebServiceResult getEmployee(@PathParam("id") final Integer id) {
		EmployeeService employeeService = new EmployeeService();
		return employeeService.getEmployee(id);
	}
	
	
	/**
	 * Provides a list of all employees.
	 * 
	 * @return All employees
	 * @return A list of all employees.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public WebServiceResult getEmployees(@QueryParam("employeeHeadQuery") final EmployeeHeadQueryParameter employeeHeadQuery) {
		EmployeeService employeeService = new EmployeeService();
		return employeeService.getEmployees(employeeHeadQuery);
	}
	
	
	/**
	 * Adds an employee.
	 * 
	 * @param employee The employee to be added.
	 * @return The result of the add function.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WebServiceResult addEmployee(final Employee employee) {
		EmployeeService employeeService = new EmployeeService();
		return employeeService.addEmployee(employee);
	}
	
	
	/**
	 * Updates an existing employee.
	 * 
	 * @param employee The employee to be updated.
	 * @return The result of the update function.
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WebServiceResult updateEmployee(final Employee employee) {
		EmployeeService employeeService = new EmployeeService();
		return employeeService.updateEmployee(employee);
	}
	
	
	/**
	 * Deletes the employee with the given id.
	 * 
	 * @param id The id of the employee to be deleted.
	 * @return The result of the delete function.
	 */
	@DELETE
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WebServiceResult deleteEmployee(@PathParam("id") final Integer id) {
		EmployeeService employeeService = new EmployeeService();
		return employeeService.deleteEmployee(id);
	}
}
