package backend.webservice.rest;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import backend.model.department.Department;
import backend.model.webservice.WebServiceResult;
import backend.webservice.common.DepartmentService;

/**
 * WebService for department access using REST technology.
 *
 * @author Michael
 */
@Path("/departments")
public class DepartmentRestService {
    /**
     * Provides the department with the given code.
     *
     * @param code The code of the department.
     * @return The department with the given code, if found.
     */
    @GET
    @Path("/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult getDepartment(@PathParam("code") final String code) {
        DepartmentService departmentService = new DepartmentService();
        return departmentService.getDepartment(code);
    }

    /**
     * Provides a list of all departments.
     *
     * @return A list of all departments.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult getDepartments() {
        DepartmentService departmentService = new DepartmentService();
        return departmentService.getDepartments();
    }

    /**
     * Adds a department.
     *
     * @param department The department to be added.
     * @return The result of the add function.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult addDepartment(final Department department) {
        DepartmentService departmentService = new DepartmentService();
        return departmentService.addDepartment(department);
    }

    /**
     * Updates an existing department.
     *
     * @param department The department to be updated.
     * @return The result of the update function.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult updateDepartment(final Department department) {
        DepartmentService departmentService = new DepartmentService();
        return departmentService.updateDepartment(department);
    }

    /**
     * Deletes the department with the given code.
     *
     * @param code The code of the department.
     * @return The result of the delete function.
     */
    @DELETE
    @Path("/{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult deleteDepartment(@PathParam("code") final String code) {
        DepartmentService departmentService = new DepartmentService();
        return departmentService.deleteDepartment(code);
    }
}
