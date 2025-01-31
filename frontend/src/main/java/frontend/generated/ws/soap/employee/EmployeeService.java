
package frontend.generated.ws.soap.employee;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.ws.RequestWrapper;
import jakarta.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 *
 */
@WebService(name = "employeeService", targetNamespace = "http://soap.webservice.backend/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface EmployeeService {


    /**
     *
     * @param employeeHeadQuery
     * @return
     *     returns frontend.generated.ws.soap.employee.WebServiceResult
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getEmployees", targetNamespace = "http://soap.webservice.backend/", className = "frontend.generated.ws.soap.employee.GetEmployees")
    @ResponseWrapper(localName = "getEmployeesResponse", targetNamespace = "http://soap.webservice.backend/", className = "frontend.generated.ws.soap.employee.GetEmployeesResponse")
    public WebServiceResult getEmployees(
        @WebParam(name = "employeeHeadQuery", targetNamespace = "")
        EmployeeHeadQueryParameter employeeHeadQuery);

    /**
     *
     * @param id
     * @return
     *     returns frontend.generated.ws.soap.employee.WebServiceResult
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getEmployee", targetNamespace = "http://soap.webservice.backend/", className = "frontend.generated.ws.soap.employee.GetEmployee")
    @ResponseWrapper(localName = "getEmployeeResponse", targetNamespace = "http://soap.webservice.backend/", className = "frontend.generated.ws.soap.employee.GetEmployeeResponse")
    public WebServiceResult getEmployee(
        @WebParam(name = "id", targetNamespace = "")
        Integer id);

    /**
     *
     * @param id
     * @return
     *     returns frontend.generated.ws.soap.employee.WebServiceResult
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "deleteEmployee", targetNamespace = "http://soap.webservice.backend/", className = "frontend.generated.ws.soap.employee.DeleteEmployee")
    @ResponseWrapper(localName = "deleteEmployeeResponse", targetNamespace = "http://soap.webservice.backend/", className = "frontend.generated.ws.soap.employee.DeleteEmployeeResponse")
    public WebServiceResult deleteEmployee(
        @WebParam(name = "id", targetNamespace = "")
        Integer id);

    /**
     *
     * @param employee
     * @return
     *     returns frontend.generated.ws.soap.employee.WebServiceResult
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "addEmployee", targetNamespace = "http://soap.webservice.backend/", className = "frontend.generated.ws.soap.employee.AddEmployee")
    @ResponseWrapper(localName = "addEmployeeResponse", targetNamespace = "http://soap.webservice.backend/", className = "frontend.generated.ws.soap.employee.AddEmployeeResponse")
    public WebServiceResult addEmployee(
        @WebParam(name = "employee", targetNamespace = "")
        Employee employee);

    /**
     *
     * @param employee
     * @return
     *     returns frontend.generated.ws.soap.employee.WebServiceResult
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "updateEmployee", targetNamespace = "http://soap.webservice.backend/", className = "frontend.generated.ws.soap.employee.UpdateEmployee")
    @ResponseWrapper(localName = "updateEmployeeResponse", targetNamespace = "http://soap.webservice.backend/", className = "frontend.generated.ws.soap.employee.UpdateEmployeeResponse")
    public WebServiceResult updateEmployee(
        @WebParam(name = "employee", targetNamespace = "")
        Employee employee);

}
