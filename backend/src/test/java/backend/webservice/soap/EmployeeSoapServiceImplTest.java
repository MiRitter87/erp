package backend.webservice.soap;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javax.persistence.EntityExistsException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.dao.DAOManager;
import backend.dao.DepartmentDao;
import backend.dao.EmployeeDao;
import backend.model.Department;
import backend.model.Employee;
import backend.model.EmployeeArray;
import backend.model.EmployeeSalary;
import backend.model.Gender;
import backend.model.webservice.EmployeeHeadQueryParameter;
import backend.model.webservice.WebServiceMessageType;
import backend.model.webservice.WebServiceResult;
import backend.tools.WebServiceTools;
import backend.tools.test.ValidationMessageProvider;
import backend.webservice.soap.EmployeeSoapServiceImpl;

public class EmployeeSoapServiceImplTest {
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources = ResourceBundle.getBundle("backend");
	
	/**
	 * DAO to access employee data.
	 */
	private static EmployeeDao employeeDAO;
	
	/**
	 * DAO to access department data.
	 */
	private static DepartmentDao departmentDAO;
	
	/**
	 * An employee named Olaf that is used in several test cases.
	 */
	protected Employee olaf;
	
	/**
	 * An employee named Jim that is used in several test cases.
	 */
	protected Employee jim;
	
	/**
	 * A department "SD - Software Development" that is used in tests.
	 */
	protected Department departmentSd;
	
	
	@BeforeAll
	/**
	 * Tasks to be performed once at startup of test class.
	 */
	public static void setUpClass() {
		employeeDAO = DAOManager.getInstance().getEmployeeDAO();
		departmentDAO = DAOManager.getInstance().getDepartmentDAO();
	}
	
	
	@AfterAll
	/**
	 * Tasks to be performed once at end of test class.
	 */
	public static void tearDownClass() {
		try {
			DAOManager.getInstance().close();			
		}
		catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	
	@BeforeEach
	/**
	 * Tasks to be performed before each test is run.
	 */
	protected void setUp() {
		this.createDummyEmployees();
		this.createDummyDepartment();
		this.updateObjectReferences();
	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	protected void tearDown() {
		this.deleteDummyDepartment();
		this.deleteDummyEmployees();
	} 
	
	
	/**
	 * Initializes the database with dummy employees.
	 */
	protected void createDummyEmployees() {
		this.olaf = new Employee();
		this.olaf.setFirstName("Olaf");
		this.olaf.setLastName("Utan");
		this.olaf.setGender(Gender.MALE);
		
		this.jim = new Employee();
		this.jim.setFirstName("Jim");
		this.jim.setLastName("Panzko");
		this.jim.setGender(Gender.MALE);
		
		try {
			employeeDAO.insertEmpoyee(this.olaf);
			employeeDAO.insertEmpoyee(this.jim);
		} 
		catch (EntityExistsException e) {
			fail(e.getMessage());
		} 
		catch (Exception e) {
			fail(e.getMessage());
		}
		
		/*
		 * After the employees have been persisted, their ID is generated and available.
		 * Now we can add salary data, that use the generated ID as primary key, too.
		 */
		this.jim.setSalaryData(new EmployeeSalary(1200));
		
		//Set a fixed date in the past. This prevents circumstances where the date does not differ because object
		//initialization and alteration happen within milliseconds during a test case.
		Calendar pastDate = Calendar.getInstance();
		pastDate.set(2020, 2, 2, 8, 23, 54);
		this.jim.getSalaryData().setSalaryLastChange(pastDate.getTime());
		
		try {
			employeeDAO.updateEmployee(this.jim);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Removes dummy employees from the database.
	 */
	protected void deleteDummyEmployees() {
		try {
			employeeDAO.deleteEmployee(this.olaf);
			employeeDAO.deleteEmployee(this.jim);
		} 
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Initializes the database with dummy departments.
	 */
	protected void createDummyDepartment() {
		this.departmentSd = new Department();
		this.departmentSd.setCode("SD");
		this.departmentSd.setName("Software Development");
		this.departmentSd.setDescription("A department that develops new and enhances existing software during projects.");
		this.departmentSd.setHead(this.jim);
		
		try {
			departmentDAO.insertDepartment(this.departmentSd);
		} 
		catch (EntityExistsException e) {
			fail(e.getMessage());
		} 
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Removes dummy departments from the database.
	 */
	protected void deleteDummyDepartment() {
		try {
			departmentDAO.deleteDepartment(this.departmentSd);
		} 
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Updates references between objects after the objects have been created.
	 */
	protected void updateObjectReferences() {
		this.jim.setHeadOfDepartment(this.departmentSd);
	}
	
	
	@Test
	/**
	 * Tests the retrieval of all employees.
	 */
	public void testGetAllEmployees() {
		WebServiceResult getEmployeesResult;
		EmployeeArray employees;
		Employee employee;

		//Get all employees
		EmployeeSoapServiceImpl service = new EmployeeSoapServiceImpl();
		getEmployeesResult = service.getEmployees(EmployeeHeadQueryParameter.ALL);
		employees = (EmployeeArray) getEmployeesResult.getData();
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(getEmployeesResult) == false);
		
		//Check if two employees are returned
		assertTrue(employees.getEmployees().size() == 2);
		
		//Check both employees by each attribute
		employee = employees.getEmployees().get(0);
		assertEquals(employee.getId(), this.olaf.getId());
		assertEquals(employee.getFirstName(), this.olaf.getFirstName());
		assertEquals(employee.getLastName(), this.olaf.getLastName());
		assertEquals(employee.getGender(), this.olaf.getGender());
		
		employee = employees.getEmployees().get(1);
		assertEquals(employee.getId(), this.jim.getId());
		assertEquals(employee.getFirstName(), this.jim.getFirstName());
		assertEquals(employee.getLastName(), this.jim.getLastName());
		assertEquals(employee.getGender(), this.jim.getGender());
		assertEquals(employee.getSalaryData().getMonthlySalary(), this.jim.getSalaryData().getMonthlySalary());
	}
	
	
	@Test
	/**
	 * Tests the retrieval of all employees that are not head of any department.
	 */
	public void testGetAllEmployeesNotHead() {
		WebServiceResult getEmployeesResult;
		EmployeeArray employees;
		Employee employee;
		
		//Get all employees
		EmployeeSoapServiceImpl service = new EmployeeSoapServiceImpl();
		getEmployeesResult = service.getEmployees(EmployeeHeadQueryParameter.NO_HEAD_ONLY);
		employees = (EmployeeArray) getEmployeesResult.getData();
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(getEmployeesResult) == false);
		
		//Check if one employee is returned
		assertTrue(employees.getEmployees().size() == 1);
		
		employee = employees.getEmployees().get(0);
		
		//Check that the employee is not head of a department.
		assertNull(employee.getHeadOfDepartment());
		
		//Check that Olaf has been retrieved. Olaf is not head of any department.
		assertEquals(employee.getId(), this.olaf.getId());
	}
	
	
	@Test
	/**
	 * Tests the retrieval of all employees that are head of any department.
	 */
	public void testGetAllEmployeesHead() {
		WebServiceResult getEmployeesResult;
		EmployeeArray employees;
		Employee employee;
		
		//Get all employees
		EmployeeSoapServiceImpl service = new EmployeeSoapServiceImpl();
		getEmployeesResult = service.getEmployees(EmployeeHeadQueryParameter.HEAD_ONLY);
		employees = (EmployeeArray) getEmployeesResult.getData();
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(getEmployeesResult) == false);
		
		//Check if one employee is returned
		assertTrue(employees.getEmployees().size() == 1);
		
		employee = employees.getEmployees().get(0);
		
		//Check that the employee is head of a department.
		assertNotNull(employee.getHeadOfDepartment());
		
		//Check that Jim has been retrieved. Jim is head of a department.
		assertEquals(employee.getId(), this.jim.getId());
	}

	
	@Test
	/**
	 * Tests the retrieval of a distinct employee defined by the ID.
	 */
	public void testGetEmployee() {
		WebServiceResult getEmployeeResult;
		Employee employee;
		
		//Get the employee "Jim"
		EmployeeSoapServiceImpl service = new EmployeeSoapServiceImpl();
		getEmployeeResult = service.getEmployee(this.jim.getId());
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(getEmployeeResult) == false);
		
		//Assure that an employee is returned
		assertTrue(getEmployeeResult.getData() instanceof Employee);
		
		employee = (Employee) getEmployeeResult.getData();
		
		//Check each attribute of the employee
		assertEquals(employee.getId(), this.jim.getId());
		assertEquals(employee.getFirstName(), this.jim.getFirstName());
		assertEquals(employee.getLastName(), this.jim.getLastName());
		assertEquals(employee.getGender(), this.jim.getGender());
		assertTrue(employee.getSalaryData().equals(this.jim.getSalaryData()));
		assertEquals(employee.getHeadOfDepartment().getCode(), this.jim.getHeadOfDepartment().getCode());
	}
	
	
	@Test
	/**
	 * Tests adding of a new employee.
	 */
	public void testAddValidEmployee() {
		Employee newEmployee = new Employee();
		Employee addedEmployee;
		WebServiceResult addEmployeeResult;
		
		//Define the new employee
		newEmployee.setFirstName("Max");
		newEmployee.setLastName("Mustermann");
		newEmployee.setGender(Gender.MALE);
		
		try {
			//Add a new employee to the database via WebService
			EmployeeSoapServiceImpl service = new EmployeeSoapServiceImpl();
			addEmployeeResult = service.addEmployee(newEmployee);
			
			//Assure no error message exists
			assertTrue(WebServiceTools.resultContainsErrorMessage(addEmployeeResult) == false);
						
			//Read the employee via DAO
			addedEmployee = employeeDAO.getEmployee(newEmployee.getId());

			//Check if the employee read by the DAO equals the employee inserted using the WebService in each attribute
			assertEquals(newEmployee.getId(), addedEmployee.getId());
			assertEquals(newEmployee.getFirstName(), addedEmployee.getFirstName());
			assertEquals(newEmployee.getLastName(), addedEmployee.getLastName());
			assertEquals(newEmployee.getGender(), addedEmployee.getGender());
		} 
		catch (Exception e) {
			fail(e.getMessage());
		}
		finally {
			//Delete the newly added employee
			try {
				employeeDAO.deleteEmployee(newEmployee);
			} 
			catch (Exception e) {
				fail(e.getMessage());
			}
		}		
	}
	
	
	@Test
	/**
	 * Tests adding salary data to an existing employee.
	 */
	public void testAddSalaryToEmployee() {
		EmployeeSalary salary = new EmployeeSalary(1100);
		EmployeeSoapServiceImpl service = new EmployeeSoapServiceImpl();
		WebServiceResult updateEmployeeResult;
		Employee updatedEmployee;
		
		//Define a new salary
		this.olaf.setSalaryData(salary);
		
		try {
			//Update the employee using the webservice
			updateEmployeeResult = service.updateEmployee(this.olaf);
			
			//Assure no error message exists
			assertTrue(WebServiceTools.resultContainsErrorMessage(updateEmployeeResult) == false);
			
			//Retrieve the updated employee
			updatedEmployee = employeeDAO.getEmployee(this.olaf.getId());
			
			assertNotNull(updatedEmployee.getSalaryData());
			assertEquals(updatedEmployee.getId(), updatedEmployee.getSalaryData().getId());
			assertEquals(updatedEmployee.getSalaryData().getMonthlySalary(), salary.getMonthlySalary());
		} 
		catch (Exception e) {
			fail(e.getMessage());
		}		
	}
	
	@Test
	/**
	 * Tests editing of an employees salary data.
	 */
	public void testEditSalaryData() {
		EmployeeSoapServiceImpl service = new EmployeeSoapServiceImpl();
		WebServiceResult updateEmployeeResult;
		Employee updatedEmployee;
		
		Date oldSalaryDate = this.jim.getSalaryData().getSalaryLastChange();
		EmployeeSalary newSalary = this.jim.getSalaryData();
		newSalary.setMonthlySalary(1300);
		
		try {
			//Update the employee using the webservice
			updateEmployeeResult = service.updateEmployee(this.jim);
			
			//Assure no error message exists
			assertTrue(WebServiceTools.resultContainsErrorMessage(updateEmployeeResult) == false);
			
			//Retrieve the updated employee
			updatedEmployee = employeeDAO.getEmployee(this.jim.getId());
			
			assertNotNull(updatedEmployee.getSalaryData());
			assertEquals(newSalary.getMonthlySalary(), updatedEmployee.getSalaryData().getMonthlySalary());
			assertNotEquals(oldSalaryDate, updatedEmployee.getSalaryData().getSalaryLastChange());
			assertEquals(newSalary.getSalaryLastChange(), updatedEmployee.getSalaryData().getSalaryLastChange());
		} 
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests saving of an employee whose data have not changed. The backend should not save and provide an information message instead.
	 */
	public void testUpdateUnchangedEmployee() {
		EmployeeSoapServiceImpl service = new EmployeeSoapServiceImpl();
		WebServiceResult updateEmployeeResult;
		String expectedInfoMessage = this.resources.getString("employee.updateUnchanged");
		String actualInfoMessage;
		
		try {
			//Update the unchanged employee using the WebService.
			updateEmployeeResult = service.updateEmployee(this.jim);
			
			//Assure that a proper return message is provided.
			assertTrue(updateEmployeeResult.getMessages().size() == 1);
			assertTrue(updateEmployeeResult.getMessages().get(0).getType() == WebServiceMessageType.I);

			actualInfoMessage = updateEmployeeResult.getMessages().get(0).getText();
			assertEquals(expectedInfoMessage, actualInfoMessage);
			
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests adding an employee without first name.
	 */
	public void testAddEmployeeWithoutFirstName() {
		Employee employee = new Employee();
		EmployeeSoapServiceImpl service = new EmployeeSoapServiceImpl();
		WebServiceResult addEmployeeResult;
		
		employee.setFirstName("");
		employee.setLastName("Mustermann");
		employee.setGender(Gender.MALE);
		
		addEmployeeResult = service.addEmployee(employee);
		
		//There should be a return message of type E
		assertTrue(addEmployeeResult.getMessages().size() == 1);
		assertTrue(addEmployeeResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//The employee should not have been persisted.
		assertNull(employee.getId());
	}
	
	
	@Test
	/**
	 * Tests adding an employee without last name.
	 */
	public void testAddEmployeeWithoutLastName() {
		Employee employee = new Employee();
		EmployeeSoapServiceImpl service = new EmployeeSoapServiceImpl();
		WebServiceResult addEmployeeResult;		
		
		employee.setFirstName("Max");
		employee.setLastName("");
		employee.setGender(Gender.MALE);
		
		addEmployeeResult = service.addEmployee(employee);
		
		//There should be a return message of type E
		assertTrue(addEmployeeResult.getMessages().size() == 1);
		assertTrue(addEmployeeResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//The employee should not have been persisted.
		assertNull(employee.getId());
	}
	
	
	@Test
	/**
	 * Tests adding an employee without gender.
	 */
	public void testAddEmployeeWithoutGender() {
		Employee employee = new Employee();
		EmployeeSoapServiceImpl service = new EmployeeSoapServiceImpl();
		WebServiceResult addEmployeeResult;
		
		employee.setFirstName("Max");
		employee.setLastName("Mustermann");
		
		addEmployeeResult = service.addEmployee(employee);
		
		//There should be a return message of type E
		assertTrue(addEmployeeResult.getMessages().size() == 1);
		assertTrue(addEmployeeResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//The employee should not have been persisted.
		assertNull(employee.getId());
	}
	
	
	@Test
	/**
	 * Tests deletion of an employee that is not head of a department.
	 */
	public void testDeleteEmployee() {
		WebServiceResult deleteEmployeeResult;
		Employee deletedEmployee;
		
		try {
			//Delete employee "Olaf" using the WebService
			EmployeeSoapServiceImpl service = new EmployeeSoapServiceImpl();
			deleteEmployeeResult = service.deleteEmployee(this.olaf.getId());
			
			//There should be no error messages
			assertTrue(WebServiceTools.resultContainsErrorMessage(deleteEmployeeResult) == false);
			
			//There should be a success message
			assertTrue(deleteEmployeeResult.getMessages().size() == 1);
			assertTrue(deleteEmployeeResult.getMessages().get(0).getType() == WebServiceMessageType.S);
			
			//Check if employee "Olaf" is missing using the DAO.
			deletedEmployee = employeeDAO.getEmployee(this.olaf.getId());
			
			if(deletedEmployee != null)
				fail("Employee 'Olaf' is still persisted but should have been deleted by the WebService operation 'deleteEmployee'.");
		} 
		catch (Exception e) {
			fail(e.getMessage());
		}
		finally {
			//Restore old database state by adding the employee that has been deleted
			try {
				this.olaf.setId(null);
				employeeDAO.insertEmpoyee(this.olaf);
			} 
			catch (Exception e) {
				fail(e.getMessage());
			}
		}
	}
	
	
	@Test
	/**
	 * Tests the deletion of an employee that is head of a department.
	 * An employee should not be allowed to be deleted if he or she is still head of a department.
	 */
	public void testDeleteEmployeeThatIsHead() {
		WebServiceResult deleteEmployeeResult;
		Employee deletedEmployee = null;
		String expectedErrorMessage;
		
		try {
			//Try to delete employee that is head of a department using the WebService
			EmployeeSoapServiceImpl service = new EmployeeSoapServiceImpl();
			deleteEmployeeResult = service.deleteEmployee(this.jim.getId());
			
			//There should be one error message
			assertTrue(deleteEmployeeResult.getMessages().size() == 1);
			assertTrue(deleteEmployeeResult.getMessages().get(0).getType() == WebServiceMessageType.E);
			
			//The error message should give the exact cause
			expectedErrorMessage = MessageFormat.format(this.resources.getString("employee.validation.headDeletion"), 
					this.jim.getId(), this.departmentSd.getCode());
			assertEquals(expectedErrorMessage, deleteEmployeeResult.getMessages().get(0).getText());
			
			//The employee should be still existing.
			deletedEmployee = employeeDAO.getEmployee(this.jim.getId());
			assertNotNull(deletedEmployee);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests updating an employee with a salary that is invalid.
	 */
	public void testUpdateWithInvalidSalary() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		WebServiceResult updateEmployeeResult;
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("employeeSalary", "salaryLastChange");
		EmployeeSalary invalidSalary = new EmployeeSalary();
		
		this.jim.setSalaryData(invalidSalary);
		
		try {
			EmployeeSoapServiceImpl service = new EmployeeSoapServiceImpl();
			updateEmployeeResult = service.updateEmployee(this.jim);
			
			//There should be one error message
			assertTrue(updateEmployeeResult.getMessages().size() == 1);
			assertTrue(updateEmployeeResult.getMessages().get(0).getType() == WebServiceMessageType.E);
			
			//The error message should give the exact cause.
			assertEquals(expectedErrorMessage, updateEmployeeResult.getMessages().get(0).getText());
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
