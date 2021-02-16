package backend.webservice.soap;

import javax.jws.WebService;

import backend.model.Employee;
import backend.model.webservice.EmployeeHeadQueryParameter;
import backend.model.webservice.WebServiceResult;
import backend.webservice.common.EmployeeService;

/**
 * WebService implementation for employee access using SOAP technology.
 * 
 * @author Michael
 */
@WebService(endpointInterface="backend.webservice.soap.EmployeeSoapService",
		serviceName = "employeeService")
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
	
	
	@Override
	public WebServiceResult getEmployees(EmployeeHeadQueryParameter employeeHeadQuery) {
		return this.employeeService.getEmployees(employeeHeadQuery);
	}

	@Override
	public WebServiceResult addEmployee(Employee employee) {
		return this.employeeService.addEmployee(employee);
	}

	@Override
	public WebServiceResult deleteEmployee(Integer id) {
		return this.employeeService.deleteEmployee(id);
	}

	@Override
	public WebServiceResult getEmployee(Integer id) {
		return this.employeeService.getEmployee(id);		
	}
	
	@Override
	public WebServiceResult updateEmployee(Employee employee) {
		return this.employeeService.updateEmployee(employee);
	}
}
