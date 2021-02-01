package backend.webservice.common;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.persistence.EntityExistsException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backend.dao.DepartmentHibernateDao;
import backend.exception.ObjectUnchangedException;
import backend.model.Department;
import backend.model.DepartmentArray;
import backend.model.WebServiceMessage;
import backend.model.WebServiceMessageType;
import backend.model.WebServiceResult;

/**
 * Common implementation of the Department WebService that is used by the SOAP as well as the REST service.
 * 
 * @author Michael
 */
public class DepartmentService {
	/**
	 * DAO for department access.
	 */
	private DepartmentHibernateDao departmentDAO;
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources = ResourceBundle.getBundle("backend");
	
	/**
	 * Application logging.
	 */
	public static final Logger logger = LogManager.getLogger(DepartmentService.class);
	
	
	/**
	 * Provides the department with the given code.
	 * 
	 * @param cd The code of the department.
	 * @return The department with the given code, if found.
	 */
	public WebServiceResult getDepartment(final String code) {
		Department department = null;
		WebServiceResult getDepartmentResult = new WebServiceResult(null);
		
		try {
			this.departmentDAO = new DepartmentHibernateDao();
			department = this.departmentDAO.getDepartmentByCode(code);
			
			if(department != null) {
				//Department found
				getDepartmentResult.setData(department);
			}
			else {
				//Department not found
				getDepartmentResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
						MessageFormat.format(this.resources.getString("department.notFound"), code)));
			}
		}
		catch (Exception e) {
			getDepartmentResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
					MessageFormat.format(this.resources.getString("department.getError"), code)));
			
			logger.error(MessageFormat.format(this.resources.getString("department.getError"), code), e);
		}
		finally {
			this.closeDepartmentDAO();
		}
		
		return getDepartmentResult;
	}
	
	
	/**
	 * Provides a list of all departments.
	 * 
	 * @return A list of all departments.
	 */
	public WebServiceResult getDepartments() {
		DepartmentArray departments = new DepartmentArray();
		WebServiceResult getDepartmentsResult = new WebServiceResult(null);
		
		try {
			this.departmentDAO = new DepartmentHibernateDao();
			departments.setDepartments(this.departmentDAO.getDepartments());
			getDepartmentsResult.setData(departments);
		} catch (Exception e) {
			getDepartmentsResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, this.resources.getString("department.getDepartmentsError")));
			
			logger.error(this.resources.getString("department.getDepartmentsError"), e);
		}
		finally {
			this.closeDepartmentDAO();
		}
		
		return getDepartmentsResult;
	}
	
	
	/**
	 * Adds a department.
	 * 
	 * @param department The department to be added.
	 * @return The result of the add function.
	 */
	public WebServiceResult addDepartment(final Department department) {
		WebServiceResult addDepartmentResult = new WebServiceResult(null);
		this.departmentDAO = new DepartmentHibernateDao();
		
		//At first validate the given department
		try {
			this.validateAddDepartment(department);
		}
		catch(Exception e) {
			addDepartmentResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, e.getMessage()));
			this.closeDepartmentDAO();
			return addDepartmentResult;
		}
		
		try {
			this.departmentDAO.insertDepartment(department);
			addDepartmentResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.S, this.resources.getString("department.addSuccess")));
		}
		catch(EntityExistsException existsException) {
			addDepartmentResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
					MessageFormat.format(this.resources.getString("department.addExistsError"), department.getCode())));
		} 
		catch (Exception e) {
			addDepartmentResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, this.resources.getString("department.addError")));
			
			logger.error(this.resources.getString("department.addError"), e);
		}
		finally {
			this.closeDepartmentDAO();
		}
		
		return addDepartmentResult;
	}
	
	
	/**
	 * Deletes the department with the given code.
	 * 
	 * @param code The code of the department.
	 * @return The result of the delete function.
	 */
	public WebServiceResult deleteDepartment(final String code) {
		Department department = null;
		WebServiceResult deleteDepartmentResult = new WebServiceResult(null);
		
		//Check if department with the given code exists.
		try {
			this.departmentDAO = new DepartmentHibernateDao();
			department = this.departmentDAO.getDepartmentByCode(code);
			
			if(department != null) {
				//Delete department if exists.
				this.departmentDAO.deleteDepartment(department);
				deleteDepartmentResult.addMessage(new WebServiceMessage(WebServiceMessageType.S, 
						MessageFormat.format(this.resources.getString("department.deleteSuccess"), code)));
			}
			else {
				//Department not found 
				deleteDepartmentResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
						MessageFormat.format(this.resources.getString("department.notFound"), code)));
			}
		}
		catch (Exception e) {
			deleteDepartmentResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
					MessageFormat.format(this.resources.getString("department.deleteError"), code)));
			
			logger.error(MessageFormat.format(this.resources.getString("department.deleteError"), code), e);
		}
		finally {
			this.closeDepartmentDAO();
		}
		
		return deleteDepartmentResult;
	}
	
	
	/**
	 * Updates an existing department.
	 * 
	 * @param department The department to be updated.
	 * @return The result of the update function.
	 */
	public WebServiceResult updateDepartment(final Department department) {
		WebServiceResult updateDepartmentResult = new WebServiceResult(null);
		this.departmentDAO = new DepartmentHibernateDao();
		
		//At first validate the given department.
		try {
			this.validateUpdateDepartment(department);
		}
		catch(Exception e) {
			updateDepartmentResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, e.getMessage()));
			this.closeDepartmentDAO();
			return updateDepartmentResult;
		}
				
		try {
			this.departmentDAO.updateDepartment(department);
			updateDepartmentResult.addMessage(new WebServiceMessage(WebServiceMessageType.S, 
					MessageFormat.format(this.resources.getString("department.updateSuccess"), department.getCode())));
		} 
		catch(ObjectUnchangedException objectUnchangedException) {
			updateDepartmentResult.addMessage(new WebServiceMessage(WebServiceMessageType.I, 
					this.resources.getString("department.updateUnchanged")));
		}
		catch (Exception e) {
			updateDepartmentResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
					MessageFormat.format(this.resources.getString("department.updateError"), department.getCode())));
			
			logger.error(MessageFormat.format(this.resources.getString("department.updateError"), department.getCode()), e);
		}
		finally {
			this.closeDepartmentDAO();
		}
		
		return updateDepartmentResult;
	}
	
	
	/**
	 * Validates the department data for the "add"-function.
	 * 
	 * @param department The department to be validated.
	 * @exception Exception In case the department is invalid.
	 */
	private void validateAddDepartment(final Department department) throws Exception {
		department.validate();

		this.validateAddHeadOfDepartment(department);
	}
	
	
	/**
	 * Validates if an update of this department is allowed.
	 * 
	 * @param department The department to be validated.
	 * @throws Exception In case the department is invalid.
	 */
	private void validateUpdateDepartment(final Department department) throws Exception {
		department.validate();
		
		this.validateUpdateDepartmentExists(department);
		this.validateUpdateHeadOfDepartment(department);
	}
	
	
	/**
	 * Validates, if the department with the given code exists. If not an Exception is thrown.
	 * 
	 * @param department The department that is validated.
	 * @throws Exception In case the department does not exist and therefore can not be updated.
	 */
	private void validateUpdateDepartmentExists(final Department department) throws Exception {
		Department existingDepartment = null;
		
		existingDepartment = this.departmentDAO.getDepartmentByCode(department.getCode());
		
		if(existingDepartment == null) {
			throw new Exception(this.resources.getString("department.codeChangeError"));
		}
	}
	
	
	/**
	 * Validates the head of the department for the add-operation.
	 * 
	 * @param department The department to be added.
	 * @throws Exception In case the head of the department is already head of another department.
	 */
	private void validateAddHeadOfDepartment(final Department department) throws Exception {
		Department existingDepartmentWithHead = null;
		
		existingDepartmentWithHead = this.departmentDAO.getDepartmentByEmployeeId(department.getHead().getId());
		
		//Check if there is already a department that has the given employee set as head.
		if(existingDepartmentWithHead != null) {
			throw new Exception(MessageFormat.format(this.resources.getString("department.validation.employeeAlreadyHead"), 
					department.getHead().getId(), existingDepartmentWithHead.getCode()));
		}
	}
	
	
	/**
	 * Validates the head of the department for the update-operation.
	 * 
	 * @param department The department to be updated.
	 * @throws Exception In case the head of the department is already head of another department.
	 */
	private void validateUpdateHeadOfDepartment(final Department department) throws Exception {
		Department existingDepartmentWithHead = null;
		
		existingDepartmentWithHead = this.departmentDAO.getDepartmentByEmployeeId(department.getHead().getId());
		
		if(existingDepartmentWithHead != null && !department.getCode().equals(existingDepartmentWithHead.getCode())) {
			//There is already another department which has the given employee set as head of department.
			throw new Exception(MessageFormat.format(this.resources.getString("department.validation.employeeAlreadyHead"), 
					department.getHead().getId(), department.getCode()));
		}
	}
	
	
	/**
	 * Closes the department DAO.
	 */
	private void closeDepartmentDAO() {
		try {
			this.departmentDAO.close();
		} catch (IOException e) {
			logger.error(this.resources.getString("department.closeFailed"), e);
		}
	}
}
