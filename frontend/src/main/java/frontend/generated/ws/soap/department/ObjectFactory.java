
package frontend.generated.ws.soap.department;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the frontend.generated.ws.soap.department package. 
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

    private final static QName _UpdateDepartment_QNAME = new QName("http://soap.webservice.backend/", "updateDepartment");
    private final static QName _AddDepartment_QNAME = new QName("http://soap.webservice.backend/", "addDepartment");
    private final static QName _UpdateDepartmentResponse_QNAME = new QName("http://soap.webservice.backend/", "updateDepartmentResponse");
    private final static QName _DeleteDepartment_QNAME = new QName("http://soap.webservice.backend/", "deleteDepartment");
    private final static QName _Departments_QNAME = new QName("http://soap.webservice.backend/", "departments");
    private final static QName _GetDepartments_QNAME = new QName("http://soap.webservice.backend/", "getDepartments");
    private final static QName _Department_QNAME = new QName("http://soap.webservice.backend/", "department");
    private final static QName _Employees_QNAME = new QName("http://soap.webservice.backend/", "employees");
    private final static QName _GetDepartmentResponse_QNAME = new QName("http://soap.webservice.backend/", "getDepartmentResponse");
    private final static QName _GetDepartmentsResponse_QNAME = new QName("http://soap.webservice.backend/", "getDepartmentsResponse");
    private final static QName _AddDepartmentResponse_QNAME = new QName("http://soap.webservice.backend/", "addDepartmentResponse");
    private final static QName _DeleteDepartmentResponse_QNAME = new QName("http://soap.webservice.backend/", "deleteDepartmentResponse");
    private final static QName _GetDepartment_QNAME = new QName("http://soap.webservice.backend/", "getDepartment");
    private final static QName _Employee_QNAME = new QName("http://soap.webservice.backend/", "employee");
    private final static QName _Salary_QNAME = new QName("http://soap.webservice.backend/", "salary");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: frontend.generated.ws.soap.department
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
     * Create an instance of {@link AddDepartmentResponse }
     * 
     */
    public AddDepartmentResponse createAddDepartmentResponse() {
        return new AddDepartmentResponse();
    }

    /**
     * Create an instance of {@link DeleteDepartmentResponse }
     * 
     */
    public DeleteDepartmentResponse createDeleteDepartmentResponse() {
        return new DeleteDepartmentResponse();
    }

    /**
     * Create an instance of {@link GetDepartment }
     * 
     */
    public GetDepartment createGetDepartment() {
        return new GetDepartment();
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
     * Create an instance of {@link UpdateDepartment }
     * 
     */
    public UpdateDepartment createUpdateDepartment() {
        return new UpdateDepartment();
    }

    /**
     * Create an instance of {@link AddDepartment }
     * 
     */
    public AddDepartment createAddDepartment() {
        return new AddDepartment();
    }

    /**
     * Create an instance of {@link UpdateDepartmentResponse }
     * 
     */
    public UpdateDepartmentResponse createUpdateDepartmentResponse() {
        return new UpdateDepartmentResponse();
    }

    /**
     * Create an instance of {@link DeleteDepartment }
     * 
     */
    public DeleteDepartment createDeleteDepartment() {
        return new DeleteDepartment();
    }

    /**
     * Create an instance of {@link GetDepartments }
     * 
     */
    public GetDepartments createGetDepartments() {
        return new GetDepartments();
    }

    /**
     * Create an instance of {@link Department }
     * 
     */
    public Department createDepartment() {
        return new Department();
    }

    /**
     * Create an instance of {@link GetDepartmentResponse }
     * 
     */
    public GetDepartmentResponse createGetDepartmentResponse() {
        return new GetDepartmentResponse();
    }

    /**
     * Create an instance of {@link GetDepartmentsResponse }
     * 
     */
    public GetDepartmentsResponse createGetDepartmentsResponse() {
        return new GetDepartmentsResponse();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDepartment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.backend/", name = "updateDepartment")
    public JAXBElement<UpdateDepartment> createUpdateDepartment(UpdateDepartment value) {
        return new JAXBElement<UpdateDepartment>(_UpdateDepartment_QNAME, UpdateDepartment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddDepartment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.backend/", name = "addDepartment")
    public JAXBElement<AddDepartment> createAddDepartment(AddDepartment value) {
        return new JAXBElement<AddDepartment>(_AddDepartment_QNAME, AddDepartment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDepartmentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.backend/", name = "updateDepartmentResponse")
    public JAXBElement<UpdateDepartmentResponse> createUpdateDepartmentResponse(UpdateDepartmentResponse value) {
        return new JAXBElement<UpdateDepartmentResponse>(_UpdateDepartmentResponse_QNAME, UpdateDepartmentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteDepartment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.backend/", name = "deleteDepartment")
    public JAXBElement<DeleteDepartment> createDeleteDepartment(DeleteDepartment value) {
        return new JAXBElement<DeleteDepartment>(_DeleteDepartment_QNAME, DeleteDepartment.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDepartments }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.backend/", name = "getDepartments")
    public JAXBElement<GetDepartments> createGetDepartments(GetDepartments value) {
        return new JAXBElement<GetDepartments>(_GetDepartments_QNAME, GetDepartments.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDepartmentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.backend/", name = "getDepartmentResponse")
    public JAXBElement<GetDepartmentResponse> createGetDepartmentResponse(GetDepartmentResponse value) {
        return new JAXBElement<GetDepartmentResponse>(_GetDepartmentResponse_QNAME, GetDepartmentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDepartmentsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.backend/", name = "getDepartmentsResponse")
    public JAXBElement<GetDepartmentsResponse> createGetDepartmentsResponse(GetDepartmentsResponse value) {
        return new JAXBElement<GetDepartmentsResponse>(_GetDepartmentsResponse_QNAME, GetDepartmentsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddDepartmentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.backend/", name = "addDepartmentResponse")
    public JAXBElement<AddDepartmentResponse> createAddDepartmentResponse(AddDepartmentResponse value) {
        return new JAXBElement<AddDepartmentResponse>(_AddDepartmentResponse_QNAME, AddDepartmentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteDepartmentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.backend/", name = "deleteDepartmentResponse")
    public JAXBElement<DeleteDepartmentResponse> createDeleteDepartmentResponse(DeleteDepartmentResponse value) {
        return new JAXBElement<DeleteDepartmentResponse>(_DeleteDepartmentResponse_QNAME, DeleteDepartmentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDepartment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.backend/", name = "getDepartment")
    public JAXBElement<GetDepartment> createGetDepartment(GetDepartment value) {
        return new JAXBElement<GetDepartment>(_GetDepartment_QNAME, GetDepartment.class, null, value);
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

}
