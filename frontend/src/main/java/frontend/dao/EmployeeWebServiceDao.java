package frontend.dao;

import java.util.ArrayList;
import java.util.List;

import frontend.generated.ws.soap.employee.EmployeeArray;
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
	 * WebService to access employee data
	 */
	private EmployeeService employeeService;

	
	/**
	 * Initializes the DAO.
	 */
	public EmployeeWebServiceDao() {
		this.employeeService = new EmployeeService_Service().getEmployeeSoapServiceImplPort();
	}

	@Override
	public void insertEmpoyee(Employee employee) throws Exception {
		WebServiceResult webServiceResult;

		webServiceResult = this.employeeService.addEmployee(employee.getWebServiceEmployeeForEmployeeService());
		this.raiseExceptionForErrors(webServiceResult.getMessages());
	}

	@Override
	public void deleteEmployee(Employee employee) throws Exception {
		WebServiceResult webServiceResult;
		
		webServiceResult = this.employeeService.deleteEmployee(employee.getId());
		this.raiseExceptionForErrors(webServiceResult.getMessages());
	}

	@Override
	public List<Employee> getEmployees() throws Exception {
		WebServiceResult webServiceResult;
		EmployeeArray employeeArray;
		List<Employee> employees = new ArrayList<Employee>();
		
		webServiceResult = this.employeeService.getEmployees();
		this.raiseExceptionForErrors(webServiceResult.getMessages());
		
		//Check WebService result data
		if(webServiceResult.getData() instanceof EmployeeArray) {
			employeeArray = (EmployeeArray) webServiceResult.getData();
			
			//Wrap WebService model of employee to local model of employee
			for(frontend.generated.ws.soap.employee.Employee wsEmployee:employeeArray.getEmployees().getEmployee()) {
				employees.add(new Employee(wsEmployee));
			}
		}
		
		return employees;
	}
	
	@Override
	public void updateEmployee(Employee employee) throws Exception {
		WebServiceResult webServiceResult;
		
		webServiceResult = this.employeeService.updateEmployee(employee.getWebServiceEmployeeForEmployeeService());
		this.raiseExceptionForErrors(webServiceResult.getMessages());
	}
}
