package backend.webservice.soap;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.text.MessageFormat;
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
import backend.model.department.Department;
import backend.model.department.DepartmentArray;
import backend.model.employee.Employee;
import backend.model.employee.Gender;
import backend.model.webservice.WebServiceMessageType;
import backend.model.webservice.WebServiceResult;
import backend.tools.WebServiceTools;

public class DepartmentSoapServiceImplTest {
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources = ResourceBundle.getBundle("backend");	
	
	/**
	 * DAO to access department data.
	 */
	private static DepartmentDao departmentDAO;
	
	/**
	 * DAO to access employee data.
	 */
	private static EmployeeDao employeeDAO;
	
	/**
	 * A department "HR - Human Resources" that is used in tests.
	 */
	protected Department departmentHr;
	
	/**
	 * A department "SD - Software Development" that is used in tests.
	 */
	protected Department departmentSd;
	
	/**
	 * An employee named Max that is used in several test cases.
	 */
	protected Employee max;
	
	/**
	 * An employee named Moritz that is used in several test cases.
	 */
	protected Employee moritz;
	
	/**
	 * An employee named Jim.
	 */
	protected Employee jim;
	
	
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
		this.createDummyDepartments();
	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	protected void tearDown() {
		this.deleteDummyDepartments();
		this.deleteDummyEmployees();
	}
	
	
	/**
	 * Initializes the database with dummy employees.
	 */
	private void createDummyEmployees() {
		this.max = new Employee();
		this.max.setFirstName("Max");
		this.max.setLastName("Mustermann");
		this.max.setGender(Gender.MALE);
		
		this.moritz = new Employee();
		this.moritz.setFirstName("Moritz");
		this.moritz.setLastName("Wurst");
		this.moritz.setGender(Gender.MALE);
		
		this.jim = new Employee();
		this.jim.setFirstName("Jim");
		this.jim.setLastName("Knopf");
		this.jim.setGender(Gender.MALE);
		
		try {
			employeeDAO.insertEmpoyee(this.max);
			employeeDAO.insertEmpoyee(this.moritz);
			employeeDAO.insertEmpoyee(this.jim);
		} 
		catch (EntityExistsException e) {
			fail(e.getMessage());
		} 
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Initializes the database with dummy departments.
	 */
	private void createDummyDepartments() {
		this.departmentHr = new Department();
		this.departmentHr.setCode("HR");
		this.departmentHr.setName("Human Resources");
		this.departmentHr.setDescription("A department that manages the staff of the company.");
		this.departmentHr.setHead(this.max);
		
		this.departmentSd = new Department();
		this.departmentSd.setCode("SD");
		this.departmentSd.setName("Software Development");
		this.departmentSd.setDescription("A department that develops new and enhances existing software during projects.");
		this.departmentSd.setHead(this.moritz);
		
		try {
			departmentDAO.insertDepartment(this.departmentHr);
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
	 * Removes dummy employees from the database.
	 */
	private void deleteDummyEmployees() {
		try {
			employeeDAO.deleteEmployee(this.max);
			employeeDAO.deleteEmployee(this.moritz);
			employeeDAO.deleteEmployee(this.jim);
		} 
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Removes dummy departments from the database.
	 */
	private void deleteDummyDepartments() {
		try {
			departmentDAO.deleteDepartment(this.departmentHr);
			departmentDAO.deleteDepartment(this.departmentSd);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests the retrieval of all departments.
	 */
	public void testGetDepartments() {
		WebServiceResult getDepartmentsResult;
		DepartmentArray departments;
		Department department;
		
		//Get all departments
		DepartmentSoapServiceImpl service = new DepartmentSoapServiceImpl();
		getDepartmentsResult = service.getDepartments();
		departments = (DepartmentArray) getDepartmentsResult.getData();
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(getDepartmentsResult) == false);
		
		//Check if two departments are returned
		assertTrue(departments.getDepartments().size() == 2);
		
		//Check both departments by each attribute
		department = departments.getDepartments().get(0);
		assertEquals(department.getCode(), this.departmentHr.getCode());
		assertEquals(department.getName(), this.departmentHr.getName());
		assertEquals(department.getDescription(), this.departmentHr.getDescription());
		assertEquals(department.getHead().getId(), this.max.getId());
		
		department = departments.getDepartments().get(1);
		assertEquals(department.getCode(), this.departmentSd.getCode());
		assertEquals(department.getName(), this.departmentSd.getName());
		assertEquals(department.getDescription(), this.departmentSd.getDescription());
		assertEquals(department.getHead().getId(), this.moritz.getId());
	}
	
	
	@Test
	/**
	 * Tests the retrieval of a distinct department defined by the code.
	 */
	public void testGetDepartment() {
		WebServiceResult getDepartmentResult;
		Department department;
		
		//Get the department with code SD
		DepartmentSoapServiceImpl service = new DepartmentSoapServiceImpl();
		getDepartmentResult = service.getDepartment("SD");
		
		//Assure no error message exists
		assertTrue(WebServiceTools.resultContainsErrorMessage(getDepartmentResult) == false);
		
		//Assure that a department is returned
		assertTrue(getDepartmentResult.getData() instanceof Department);
		
		department = (Department) getDepartmentResult.getData();
		
		//Check each attribute of the department
		assertEquals(department.getCode(), this.departmentSd.getCode());
		assertEquals(department.getName(), this.departmentSd.getName());
		assertEquals(department.getDescription(), this.departmentSd.getDescription());
		assertEquals(department.getHead().getId(), this.moritz.getId());
	}
	
	@Test
	/**
	 * Tests adding of a new - valid - department.
	 */
	public void testAddValidDepartment() {
		Department newDepartment = new Department();
		Department addedDepartment;
		WebServiceResult addDepartmentResult;
		
		//Define the new department
		newDepartment.setCode("FM");
		newDepartment.setName("Facility Management");
		newDepartment.setDescription("Maintains the office buildings.");
		newDepartment.setHead(this.jim);
		
		try {
			//Add a new department to the database via WebService
			DepartmentSoapServiceImpl departmentService = new DepartmentSoapServiceImpl();
			addDepartmentResult = departmentService.addDepartment(newDepartment);
			
			//Assure no error message exists
			assertTrue(WebServiceTools.resultContainsErrorMessage(addDepartmentResult) == false);
			
			//Read the department via DAO
			addedDepartment = departmentDAO.getDepartmentByCode(newDepartment.getCode());
			
			//Check if the department read by the DAO equals the department inserted using the WebService in each attribute.
			assertEquals(newDepartment.getCode(), addedDepartment.getCode());
			assertEquals(newDepartment.getName(), addedDepartment.getName());
			assertEquals(newDepartment.getDescription(), addedDepartment.getDescription());
			assertEquals(newDepartment.getHead().getId(), addedDepartment.getHead().getId());
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		finally {
			//Delete the newly added department
			try {
				departmentDAO.deleteDepartment(newDepartment);
			} 
			catch (Exception e) {
				fail(e.getMessage());
			}
		}
	}
	
	
	@Test
	/**
	 * Tests adding a new department without a code.
	 */
	public void testAddDepartmentWithoutCode() {
		Department newDepartment = new Department();
		Department addedDepartment = null;
		DepartmentSoapServiceImpl service = new DepartmentSoapServiceImpl();
		WebServiceResult addDepartmentResult;
		
		//Define the department without code
		newDepartment.setCode("");
		newDepartment.setName("Some empty department");
		newDepartment.setDescription("This should not be allowed to be added.");
		newDepartment.setHead(this.moritz);
		
		addDepartmentResult = service.addDepartment(newDepartment);
		
		//There should be a return message of type E
		assertTrue(addDepartmentResult.getMessages().size() == 1);
		assertTrue(addDepartmentResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//The department should not have been persisted.
		try {
			addedDepartment = departmentDAO.getDepartmentByCode("");
			assertNull(addedDepartment);
		} 
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests adding a new department without a name.
	 */
	public void testAddDepartmentWithoutName() {
		Department newDepartment = new Department();
		Department addedDepartment = null;
		DepartmentSoapServiceImpl service = new DepartmentSoapServiceImpl();
		WebServiceResult addDepartmentResult;
		
		//Define the department without code
		newDepartment.setCode("FM");
		newDepartment.setName("");
		newDepartment.setDescription("This should not be allowed to be added.");
		newDepartment.setHead(this.moritz);
		
		addDepartmentResult = service.addDepartment(newDepartment);
		
		//There should be a return message of type E
		assertTrue(addDepartmentResult.getMessages().size() == 1);
		assertTrue(addDepartmentResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//The department should not have been persisted.
		try {
			addedDepartment = departmentDAO.getDepartmentByCode("FM");
			assertNull(addedDepartment);
		} 
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests adding a new department without a head.
	 */
	public void testAddDepartmentWithoutHead() {
		Department newDepartment = new Department();
		Department addedDepartment = null;
		DepartmentSoapServiceImpl service = new DepartmentSoapServiceImpl();
		WebServiceResult addDepartmentResult;
		
		//Define the department without head
		newDepartment.setCode("FM");
		newDepartment.setName("Facility Management");
		newDepartment.setDescription("This should not be allowed to be added.");
		newDepartment.setHead(null);
		
		addDepartmentResult = service.addDepartment(newDepartment);
		
		//There should be a return message of type E
		assertTrue(addDepartmentResult.getMessages().size() == 1);
		assertTrue(addDepartmentResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//The department should not have been persisted.
		try {
			addedDepartment = departmentDAO.getDepartmentByCode("FM");
			assertNull(addedDepartment);
		} 
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests deletion of a department.
	 */
	public void testDeleteDepartment() {
		WebServiceResult deleteDepartmentResult;
		Department deletedDepartment;
		
		try {
			//Delete department with ID SD using the WebService
			DepartmentSoapServiceImpl service = new DepartmentSoapServiceImpl();
			deleteDepartmentResult = service.deleteDepartment("SD");
			
			//There should be no error messages
			assertTrue(WebServiceTools.resultContainsErrorMessage(deleteDepartmentResult) == false);
			
			//There should be a success message
			assertTrue(deleteDepartmentResult.getMessages().size() == 1);
			assertTrue(deleteDepartmentResult.getMessages().get(0).getType() == WebServiceMessageType.S);
			
			//Check if department with code SD is missing using the DAO.
			deletedDepartment = departmentDAO.getDepartmentByCode("SD");
			
			if(deletedDepartment != null)
				fail("Department with code 'SD' is still persisted but should have been deleted by the WebService operation 'deleteDepartment'.");
		} 
		catch (Exception e) {
			fail(e.getMessage());
		}
		finally {
			//Restore old database state by adding the department that has been deleted
			try {
				departmentDAO.insertDepartment(this.departmentSd);
			} 
			catch (Exception e) {
				fail(e.getMessage());
			}
		}
	}
	
	
	@Test
	/**
	 * Tests editing of department data.
	 */
	public void testUpdateDepartment() {
		DepartmentSoapServiceImpl service = new DepartmentSoapServiceImpl();
		WebServiceResult updateDepartmentResult;
		Department updatedDepartment;
		
		//Change the data of the development department.
		this.departmentSd.setName("IT-Development");
		this.departmentSd.setName("IT Unit for Software Development");
		this.departmentSd.setHead(this.jim);
		
		try {
			//Update the department using the WebService
			updateDepartmentResult = service.updateDepartment(this.departmentSd);
			
			//Assure no error message exists
			assertTrue(WebServiceTools.resultContainsErrorMessage(updateDepartmentResult) == false);
			
			//Retrieve the updated employee
			updatedDepartment = departmentDAO.getDepartmentByCode(this.departmentSd.getCode());
			
			assertNotNull(updatedDepartment);
			assertEquals(this.departmentSd.getName(), updatedDepartment.getName());
			assertEquals(this.departmentSd.getDescription(), updatedDepartment.getDescription());
			assertEquals(this.departmentSd.getHead().getId(), this.jim.getId());
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests trying to change the department code. This should be prevented because it is the primary key.
	 */
	public void testUpdateDepartmentCode() {
		DepartmentSoapServiceImpl service = new DepartmentSoapServiceImpl();
		WebServiceResult updateDepartmentResult;
		Department updatedDepartment;
		
		//Change the code (primary key)
		this.departmentSd.setCode("Something");
		
		try {
			//Update the department using the WebService
			updateDepartmentResult = service.updateDepartment(this.departmentSd);
			
			//There should be one error message of type E
			assertTrue(updateDepartmentResult.getMessages().size() == 1);
			assertTrue(updateDepartmentResult.getMessages().get(0).getType() == WebServiceMessageType.E);
			
			//Retrieve the updated department
			updatedDepartment = departmentDAO.getDepartmentByCode(this.departmentSd.getCode());
			
			//There should be no department, because the change should have been prevented.
			assertNull(updatedDepartment);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		finally {
			//Change code back to allow the tearDown() method the deletion of the object.
			this.departmentSd.setCode("SD");
		}
	}
	
	
	@Test
	/**
	 * Tests if a department can be added with an employee that already is head of another department.
	 */
	public void testAddDepartmentWithMultiHeadEmployee() {
		Department newDepartment = new Department();
		WebServiceResult addDepartmentResult;
		DepartmentSoapServiceImpl service = new DepartmentSoapServiceImpl();
		String expectedErrorMessage;
		
		//Create the new department. The head is already head of another department.
		newDepartment.setCode("NEW");
		newDepartment.setName("New Department");
		newDepartment.setDescription("The head of department is already head of another department.");
		newDepartment.setHead(this.max);
		
		addDepartmentResult = service.addDepartment(newDepartment);
		
		//There should be one error message of type E
		assertTrue(addDepartmentResult.getMessages().size() == 1);
		assertTrue(addDepartmentResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//The error message should state the correct error reason.
		expectedErrorMessage = MessageFormat.format(this.resources.getString("department.validation.employeeAlreadyHead"), 
				newDepartment.getHead().getId(), this.departmentHr.getCode());
		assertEquals(expectedErrorMessage, addDepartmentResult.getMessages().get(0).getText());
	}

	
	@Test
	/**
	 * Tests if a department can be updated with an employee that already is head of another department.
	 */
	public void testUpdateDepartmentWithMultiHeadEmployee() {
		WebServiceResult updateDepartmentResult;
		DepartmentSoapServiceImpl service = new DepartmentSoapServiceImpl();
		String expectedErrorMessage;
		
		//Try to set an employee as head that is already a head of another department.
		this.departmentSd.setHead(this.max);
		updateDepartmentResult = service.updateDepartment(this.departmentSd);
		
		//There should be one error message of type E
		assertTrue(updateDepartmentResult.getMessages().size() == 1);
		assertTrue(updateDepartmentResult.getMessages().get(0).getType() == WebServiceMessageType.E);
		
		//The error message should state the correct error reason.
		expectedErrorMessage = MessageFormat.format(this.resources.getString("department.validation.employeeAlreadyHead"), 
				this.departmentSd.getHead().getId(), this.departmentHr.getCode());
		assertEquals(expectedErrorMessage, updateDepartmentResult.getMessages().get(0).getText());
	}
	
	
	@Test
	/**
	 * Tests saving of a department whose data have not changed. The backend should not save and provide an information message instead.
	 */
	public void testUpdateUnchangedDepartment() {
		DepartmentSoapServiceImpl service = new DepartmentSoapServiceImpl();
		WebServiceResult updateDepartmentResult;
		String expectedInfoMessage = this.resources.getString("department.updateUnchanged");
		String actualInfoMessage;
		
		updateDepartmentResult = service.updateDepartment(this.departmentHr);
		
		//Assure that a proper return message is provided.
		assertTrue(updateDepartmentResult.getMessages().size() == 1);
		assertTrue(updateDepartmentResult.getMessages().get(0).getType() == WebServiceMessageType.I);
		
		actualInfoMessage = updateDepartmentResult.getMessages().get(0).getText();
		assertEquals(expectedInfoMessage, actualInfoMessage);
	}
}
