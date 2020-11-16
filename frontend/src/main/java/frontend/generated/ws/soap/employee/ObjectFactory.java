
package frontend.generated.ws.soap.employee;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the frontend.generated.ws.soap.employee package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DeleteEmployeeResponse_QNAME = new QName("http://soap.webservice.backend/", "deleteEmployeeResponse");
    private final static QName _UpdateEmployee_QNAME = new QName("http://soap.webservice.backend/", "updateEmployee");
    private final static QName _GetEmployee_QNAME = new QName("http://soap.webservice.backend/", "getEmployee");
    private final static QName _Departments_QNAME = new QName("http://soap.webservice.backend/", "departments");
    private final static QName _Department_QNAME = new QName("http://soap.webservice.backend/", "department");
    private final static QName _Employees_QNAME = new QName("http://soap.webservice.backend/", "employees");
    private final static QName _DeleteEmployee_QNAME = new QName("http://soap.webservice.backend/", "deleteEmployee");
    private final static QName _UpdateEmployeeResponse_QNAME = new QName("http://soap.webservice.backend/", "updateEmployeeResponse");
    private final static QName _GetEmployeesResponse_QNAME = new QName("http://soap.webservice.backend/", "getEmployeesResponse");
    private final static QName _AddEmployee_QNAME = new QName("http://soap.webservice.backend/", "addEmployee");
    private final static QName _GetEmployees_QNAME = new QName("http://soap.webservice.backend/", "getEmployees");
    private final static QName _AddEmployeeResponse_QNAME = new QName("http://soap.webservice.backend/", "addEmployeeResponse");
    private final static QName _Employee_QNAME = new QName("http://soap.webservice.backend/", "employee");
    private final static QName _Salary_QNAME = new QName("http://soap.webservice.backend/", "salary");
    private final static QName _GetEmployeeResponse_QNAME = new QName("http://soap.webservice.backend/", "getEmployeeResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: frontend.generated.ws.soap.employee
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link WebServiceResult }
     * 
     */
    public WebServiceResult createWebServiceResult() {
        return new WebServiceResult();
    }

    /**
     * Create an instance of {@link EmployeeArray }
     * 
     */
    public EmployeeArray createEmployeeArray() {
        return new EmployeeArray();
    }

    /**
     * Create an instance of {@link DepartmentArray }
     * 
     */
    public DepartmentArray createDepartmentArray() {
        return new DepartmentArray();
    }

    /**
     * Create an instance of {@link GetEmployees }
     * 
     */
    public GetEmployees createGetEmployees() {
        return new GetEmployees();
    }

    /**
     * Create an instance of {@link AddEmployeeResponse }
     * 
     */
    public AddEmployeeResponse createAddEmployeeResponse() {
        return new AddEmployeeResponse();
    }

    /**
     * Create an instance of {@link Employee }
     * 
     */
    public Employee createEmployee() {
        return new Employee();
    }

    /**
     * Create an instance of {@link EmployeeSalary }
     * 
     */
    public EmployeeSalary createEmployeeSalary() {
        return new EmployeeSalary();
    }

    /**
     * Create an instance of {@link GetEmployeeResponse }
     * 
     */
    public GetEmployeeResponse createGetEmployeeResponse() {
        return new GetEmployeeResponse();
    }

    /**
     * Create an instance of {@link DeleteEmployeeResponse }
     * 
     */
    public DeleteEmployeeResponse createDeleteEmployeeResponse() {
        return new DeleteEmployeeResponse();
    }

    /**
     * Create an instance of {@link UpdateEmployee }
     * 
     */
    public UpdateEmployee createUpdateEmployee() {
        return new UpdateEmployee();
    }

    /**
     * Create an instance of {@link GetEmployee }
     * 
     */
    public GetEmployee createGetEmployee() {
        return new GetEmployee();
    }

    /**
     * Create an instance of {@link Department }
     * 
     */
    public Department createDepartment() {
        return new Department();
    }

    /**
     * Create an instance of {@link DeleteEmployee }
     * 
     */
    public DeleteEmployee createDeleteEmployee() {
        return new DeleteEmployee();
    }

    /**
     * Create an instance of {@link UpdateEmployeeResponse }
     * 
     */
    public UpdateEmployeeResponse createUpdateEmployeeResponse() {
        return new UpdateEmployeeResponse();
    }

    /**
     * Create an instance of {@link GetEmployeesResponse }
     * 
     */
    public GetEmployeesResponse createGetEmployeesResponse() {
        return new GetEmployeesResponse();
    }

    /**
     * Create an instance of {@link AddEmployee }
     * 
     */
    public AddEmployee createAddEmployee() {
        return new AddEmployee();
    }

    /**
     * Create an instance of {@link WebServiceMessage }
     * 
     */
    public WebServiceMessage createWebServiceMessage() {
        return new WebServiceMessage();
    }

    /**
     * Create an instance of {@link WebServiceResult.Messages }
     * 
     */
    public WebServiceResult.Messages createWebServiceResultMessages() {
        return new WebServiceResult.Messages();
    }

    /**
     * Create an instance of {@link EmployeeArray.Employees }
     * 
     */
    public EmployeeArray.Employees createEmployeeArrayEmployees() {
        return new EmployeeArray.Employees();
    }

    /**
     * Create an instance of {@link DepartmentArray.Departments }
     * 
     */
    public DepartmentArray.Departments createDepartmentArrayDepartments() {
        return new DepartmentArray.Departments();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteEmployeeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.backend/", name = "deleteEmployeeResponse")
    public JAXBElement<DeleteEmployeeResponse> createDeleteEmployeeResponse(DeleteEmployeeResponse value) {
        return new JAXBElement<DeleteEmployeeResponse>(_DeleteEmployeeResponse_QNAME, DeleteEmployeeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateEmployee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.backend/", name = "updateEmployee")
    public JAXBElement<UpdateEmployee> createUpdateEmployee(UpdateEmployee value) {
        return new JAXBElement<UpdateEmployee>(_UpdateEmployee_QNAME, UpdateEmployee.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmployee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.backend/", name = "getEmployee")
    public JAXBElement<GetEmployee> createGetEmployee(GetEmployee value) {
        return new JAXBElement<GetEmployee>(_GetEmployee_QNAME, GetEmployee.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DepartmentArray }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.backend/", name = "departments")
    public JAXBElement<DepartmentArray> createDepartments(DepartmentArray value) {
        return new JAXBElement<DepartmentArray>(_Departments_QNAME, DepartmentArray.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Department }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.backend/", name = "department")
    public JAXBElement<Department> createDepartment(Department value) {
        return new JAXBElement<Department>(_Department_QNAME, Department.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmployeeArray }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.backend/", name = "employees")
    public JAXBElement<EmployeeArray> createEmployees(EmployeeArray value) {
        return new JAXBElement<EmployeeArray>(_Employees_QNAME, EmployeeArray.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteEmployee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.backend/", name = "deleteEmployee")
    public JAXBElement<DeleteEmployee> createDeleteEmployee(DeleteEmployee value) {
        return new JAXBElement<DeleteEmployee>(_DeleteEmployee_QNAME, DeleteEmployee.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateEmployeeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.backend/", name = "updateEmployeeResponse")
    public JAXBElement<UpdateEmployeeResponse> createUpdateEmployeeResponse(UpdateEmployeeResponse value) {
        return new JAXBElement<UpdateEmployeeResponse>(_UpdateEmployeeResponse_QNAME, UpdateEmployeeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmployeesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.backend/", name = "getEmployeesResponse")
    public JAXBElement<GetEmployeesResponse> createGetEmployeesResponse(GetEmployeesResponse value) {
        return new JAXBElement<GetEmployeesResponse>(_GetEmployeesResponse_QNAME, GetEmployeesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddEmployee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.backend/", name = "addEmployee")
    public JAXBElement<AddEmployee> createAddEmployee(AddEmployee value) {
        return new JAXBElement<AddEmployee>(_AddEmployee_QNAME, AddEmployee.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmployees }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.backend/", name = "getEmployees")
    public JAXBElement<GetEmployees> createGetEmployees(GetEmployees value) {
        return new JAXBElement<GetEmployees>(_GetEmployees_QNAME, GetEmployees.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddEmployeeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.backend/", name = "addEmployeeResponse")
    public JAXBElement<AddEmployeeResponse> createAddEmployeeResponse(AddEmployeeResponse value) {
        return new JAXBElement<AddEmployeeResponse>(_AddEmployeeResponse_QNAME, AddEmployeeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Employee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.backend/", name = "employee")
    public JAXBElement<Employee> createEmployee(Employee value) {
        return new JAXBElement<Employee>(_Employee_QNAME, Employee.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmployeeSalary }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.backend/", name = "salary")
    public JAXBElement<EmployeeSalary> createSalary(EmployeeSalary value) {
        return new JAXBElement<EmployeeSalary>(_Salary_QNAME, EmployeeSalary.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmployeeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.backend/", name = "getEmployeeResponse")
    public JAXBElement<GetEmployeeResponse> createGetEmployeeResponse(GetEmployeeResponse value) {
        return new JAXBElement<GetEmployeeResponse>(_GetEmployeeResponse_QNAME, GetEmployeeResponse.class, null, value);
    }

}
