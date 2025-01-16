package frontend.dao;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.WebServiceException;

import frontend.generated.ws.soap.employee.EmployeeArray;
import frontend.generated.ws.soap.employee.EmployeeHeadQueryParameter;
import frontend.generated.ws.soap.employee.EmployeeService;
import frontend.generated.ws.soap.employee.EmployeeService_Service;
import frontend.generated.ws.soap.employee.WebServiceResult;
import frontend.model.Employee;

/**
 * Data Access Object that provides access to employee data via WebService.
 *
 * @author Michael
 */
public class EmployeeWebServiceDao extends WebServiceDao implements EmployeeDao {

    /**
     * WebService to access employee data.
     */
    private EmployeeService employeeService;

    /**
     * Initializes the DAO.
     *
     * @throws WebServiceException In case the WebService is unavailable.
     */
    public EmployeeWebServiceDao() throws WebServiceException {
        this.employeeService = new EmployeeService_Service().getEmployeeSoapServiceImplPort();
    }

    /**
     * Inserts an employee.
     */
    @Override
    public void insertEmpoyee(final Employee employee) throws Exception {
        WebServiceResult webServiceResult;

        webServiceResult = this.employeeService.addEmployee(employee.getWebServiceEmployeeForEmployeeService());
        this.raiseExceptionForErrors(webServiceResult.getMessages());
    }

    /**
     * Deletes an employee.
     */
    @Override
    public void deleteEmployee(final Employee employee) throws Exception {
        WebServiceResult webServiceResult;

        webServiceResult = this.employeeService.deleteEmployee(employee.getId());
        this.raiseExceptionForErrors(webServiceResult.getMessages());
    }

    /**
     * Gets all employees that match the given query parameters.
     */
    @Override
    public List<Employee> getEmployees(final EmployeeHeadQueryParameter employeeHeadQueryParameter) throws Exception {
        WebServiceResult webServiceResult;
        EmployeeArray employeeArray;
        List<Employee> employees = new ArrayList<Employee>();

        webServiceResult = this.employeeService.getEmployees(employeeHeadQueryParameter);
        this.raiseExceptionForErrors(webServiceResult.getMessages());

        // Check WebService result data
        if (webServiceResult.getData() instanceof EmployeeArray) {
            employeeArray = (EmployeeArray) webServiceResult.getData();

            // Wrap WebService model of employee to local model of employee
            for (frontend.generated.ws.soap.employee.Employee wsEmployee : employeeArray.getEmployees().getEmployee()) {
                employees.add(new Employee(wsEmployee));
            }
        }

        return employees;
    }

    /**
     * Updates an existing employee.
     */
    @Override
    public void updateEmployee(final Employee employee) throws Exception {
        WebServiceResult webServiceResult;

        webServiceResult = this.employeeService.updateEmployee(employee.getWebServiceEmployeeForEmployeeService());
        this.raiseExceptionForErrors(webServiceResult.getMessages());
    }
}
