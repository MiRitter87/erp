package backend.webservice.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import backend.model.Department;
import backend.model.WebServiceResult;
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
	@Path("/")
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
	@Path("/")
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
	@Path("/")
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
