package backend.webservice.soap;

import jakarta.jws.WebService;

import backend.model.employee.Employee;
import backend.model.employee.EmployeeHeadQueryParameter;
import backend.model.webservice.WebServiceResult;
import backend.webservice.common.EmployeeService;

/**
 * WebService implementation for employee access using SOAP technology.
 *
 * @author Michael
 */
@WebService(endpointInterface = "backend.webservice.soap.EmployeeSoapService", serviceName = "employeeService")
public class EmployeeSoapServiceImpl implements EmployeeSoapService {
    /**
     * The interface-independent employee service.
     */
    private EmployeeService employeeService;

    /**
     * Initializes the Employee Soap Service Implementation.
     */
    public EmployeeSoapServiceImpl() {
        this.employeeService = new EmployeeService();
    }

    /**
     * Deletes the employee with the given id.
     */
    @Override
    public WebServiceResult deleteEmployee(final Integer id) {
        return this.employeeService.deleteEmployee(id);
    }

    /**
     * Provides the employee with the given id.
     */
    @Override
    public WebServiceResult getEmployee(final Integer id) {
        return this.employeeService.getEmployee(id);
    }

    /**
     * Provides a list of all employees.
     */
    @Override
    public WebServiceResult getEmployees(final EmployeeHeadQueryParameter employeeHeadQuery) {
        return this.employeeService.getEmployees(employeeHeadQuery);
    }

    /**
     * Adds an employee.
     */
    @Override
    public WebServiceResult addEmployee(final Employee employee) {
        return this.employeeService.addEmployee(employee);
    }

    /**
     * Updates an existing employee.
     */
    @Override
    public WebServiceResult updateEmployee(final Employee employee) {
        return this.employeeService.updateEmployee(employee);
    }
}
