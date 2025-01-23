package backend.webservice.soap;

import jakarta.jws.WebService;

import backend.model.department.Department;
import backend.model.webservice.WebServiceResult;
import backend.webservice.common.DepartmentService;

/**
 * WebService implementation for department access using SOAP technology.
 *
 * @author Michael
 */
@WebService(endpointInterface = "backend.webservice.soap.DepartmentSoapService", serviceName = "departmentService")
public class DepartmentSoapServiceImpl implements DepartmentSoapService {
    /**
     * The interface-independent department service.
     */
    private DepartmentService departmentService;

    /**
     * Initializes the Department Soap Service Implementation.
     */
    public DepartmentSoapServiceImpl() {
        this.departmentService = new DepartmentService();
    }

    /**
     * Deletes the department with the given code.
     */
    @Override
    public WebServiceResult deleteDepartment(final String code) {
        return departmentService.deleteDepartment(code);
    }

    /**
     * Provides the department with the given code.
     */
    @Override
    public WebServiceResult getDepartment(final String code) {
        return departmentService.getDepartment(code);
    }

    /**
     * Provides a list of all departments.
     */
    @Override
    public WebServiceResult getDepartments() {
        return departmentService.getDepartments();
    }

    /**
     * Adds a department.
     */
    @Override
    public WebServiceResult addDepartment(final Department department) {
        return departmentService.addDepartment(department);
    }

    /**
     * Updates an existing department.
     */
    @Override
    public WebServiceResult updateDepartment(final Department department) {
        return departmentService.updateDepartment(department);
    }

}
