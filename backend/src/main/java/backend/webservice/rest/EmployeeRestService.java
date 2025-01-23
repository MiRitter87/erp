package backend.webservice.rest;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

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
     * @param employeeHeadQuery The type of employees to be queried.
     * @return A list of all employees.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult getEmployees(
            @QueryParam("employeeHeadQuery") final EmployeeHeadQueryParameter employeeHeadQuery) {
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
