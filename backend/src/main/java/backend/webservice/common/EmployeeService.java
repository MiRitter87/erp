package backend.webservice.common;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backend.dao.DAOManager;
import backend.dao.EmployeeDao;
import backend.exception.ObjectUnchangedException;
import backend.model.employee.Employee;
import backend.model.employee.EmployeeArray;
import backend.model.employee.EmployeeHeadQueryParameter;
import backend.model.webservice.WebServiceMessage;
import backend.model.webservice.WebServiceMessageType;
import backend.model.webservice.WebServiceResult;

/**
 * Common implementation of the Employee WebService that is used by the SOAP as well as the REST service.
 * 
 * @author Michael
 */
public class EmployeeService {
	/**
	 * DAO for employee access
	 */
	private EmployeeDao employeeDAO;
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources = ResourceBundle.getBundle("backend");
	
	/**
	 * Application logging.
	 */
	public static final Logger logger = LogManager.getLogger(EmployeeService.class);
	
	
	/**
	 * Provides the employee with the given id.
	 * 
	 * @param id The id of the employee.
	 * @return The employee with the given id, if found.
	 */
	public WebServiceResult getEmployee(final Integer id)	{
		Employee employee = null;
		WebServiceResult getEmployeeResult = new WebServiceResult(null);
		
		try {
			this.employeeDAO = DAOManager.getInstance().getEmployeeDAO();
			employee = this.employeeDAO.getEmployee(id);
			
			if(employee != null) {
				//Employee found
				getEmployeeResult.setData(employee);				
			}
			else {
				//Employee not found
				getEmployeeResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
						MessageFormat.format(this.resources.getString("employee.notFound"), id)));
			}
		} catch (Exception e) {
			getEmployeeResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
					MessageFormat.format(this.resources.getString("employee.getError"), id)));
			
			logger.error(MessageFormat.format(this.resources.getString("employee.getError"), id), e);
		}
		
		return getEmployeeResult;
	}
	
	
	/**
	 * Provides a list of all employees that match the given query parameters.
	 * 
	 * @param employeeHeadQuery Specifies the employees to be selected based on the head attribute.
	 * @return A list of all employees that match the given query parameters.
	 */
	public WebServiceResult getEmployees(final EmployeeHeadQueryParameter employeeHeadQuery) {
		EmployeeArray employees = new EmployeeArray();
		WebServiceResult getEmployeesResult = new WebServiceResult(null);
		
		try {
			this.employeeDAO = DAOManager.getInstance().getEmployeeDAO();
			employees.setEmployees(this.employeeDAO.getEmployees(employeeHeadQuery));
			getEmployeesResult.setData(employees);
		} catch (Exception e) {
			getEmployeesResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, this.resources.getString("employee.getEmployeesError")));
			
			logger.error(this.resources.getString("employee.getEmployeesError"), e);
		}
		
		return getEmployeesResult;
	}
	
	
	/**
	 * Adds an employee.
	 * 
	 * @param employee The employee to be added.
	 * @return The result of the add function.
	 */
	public WebServiceResult addEmployee(final Employee employee) {
		WebServiceResult addEmployeeResult = new WebServiceResult(null);
		
		//At first validate the given employee.
		try {
			employee.validate();
		}
		catch(Exception e) {
			addEmployeeResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, e.getMessage()));
			return addEmployeeResult;
		}
		
		try {
			this.employeeDAO = DAOManager.getInstance().getEmployeeDAO();
			this.employeeDAO.insertEmpoyee(employee);
			addEmployeeResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.S, this.resources.getString("employee.addSuccess")));
		} 
		catch (Exception e) {			
			addEmployeeResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, this.resources.getString("employee.addError")));
			
			logger.error(this.resources.getString("employee.addError"), e);
		}
		
		return addEmployeeResult;
	}
	
	
	/**
	 * Updates an existing employee.
	 * 
	 * @param employee The employee to be updated.
	 * @return The result of the update function.
	 */
	public WebServiceResult updateEmployee(final Employee employee) {
		WebServiceResult updateEmployeeResult = new WebServiceResult(null);
		
		//At first validate the given employee.
		try {
			employee.validate();
		}
		catch(Exception e) {
			updateEmployeeResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, e.getMessage()));
			return updateEmployeeResult;
		}
				
		try {
			this.employeeDAO = DAOManager.getInstance().getEmployeeDAO();
			this.employeeDAO.updateEmployee(employee);
			updateEmployeeResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.S, this.resources.getString("employee.updateSuccess")));
		} 
		catch(ObjectUnchangedException objectUnchangedException) {
			updateEmployeeResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.I, this.resources.getString("employee.updateUnchanged")));
		}
		catch (Exception e) {
			updateEmployeeResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, this.resources.getString("employee.updateError")));
			
			logger.error(this.resources.getString("employee.updateError"), e);
		}
		
		return updateEmployeeResult;
	}
	
	
	/**
	 * Deletes the employee with the given id.
	 * 
	 * @param id The id of the employee to be deleted.
	 * @return The result of the delete function.
	 */
	public WebServiceResult deleteEmployee(final Integer id) {
		Employee employee = null;
		WebServiceResult deleteEmployeeResult = new WebServiceResult(null);
		
		//Validate the employee before deletion
		try {
			this.validateDeleteEmployee(id);	
		}
		catch(Exception e) {
			deleteEmployeeResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, e.getMessage()));
			return deleteEmployeeResult;
		}
	
		//Employee is valid, try to delete.
		try {
			employee = this.employeeDAO.getEmployee(id);
			this.employeeDAO.deleteEmployee(employee);
			deleteEmployeeResult.addMessage(new WebServiceMessage(WebServiceMessageType.S, 
					MessageFormat.format(this.resources.getString("employee.deleteSuccess"), id)));
		} catch (Exception e) {
			deleteEmployeeResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
					MessageFormat.format(this.resources.getString("employee.deleteError"), id)));
			
			logger.error(MessageFormat.format(this.resources.getString("employee.deleteError"), id), e);
		}
		
		return deleteEmployeeResult;
	}
	

	/**
	 * Validates the employee data before trying to delete the employee.
	 * 
	 * @param id The ID of the employee.
	 * @throws Exception In case the employee can not be deleted.
	 */
	private void validateDeleteEmployee(final Integer id) throws Exception {
		Employee foundEmployee = null;
		
		this.employeeDAO = DAOManager.getInstance().getEmployeeDAO();
		
		//Check if employee exists.
		foundEmployee = this.employeeDAO.getEmployee(id);
		
		if(foundEmployee == null) {
			//Employee not found
			throw new Exception(MessageFormat.format(this.resources.getString("employee.notFound"), id));
		}	
		
		//An employee can only be deleted if he is not a head of any department.
		if(foundEmployee.getHeadOfDepartment() != null) {
			throw new Exception(MessageFormat.format(this.resources.getString("employee.validation.headDeletion"), 
					foundEmployee.getId(), foundEmployee.getHeadOfDepartment().getCode()));
		}
	}
}
