package backend.model.employee;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.exception.IdentifierMismatchException;
import backend.model.employee.Employee;
import backend.model.employee.EmployeeSalary;
import backend.model.employee.Gender;
import backend.tools.test.ValidationMessageProvider;

/**
 * Tests the employee model.
 * 
 * @author Michael
 */
public class EmployeeTest {
	/**
	 * The employee under test.
	 */
	protected Employee olaf;
	
	/**
	 * Salary data of olaf.
	 */
	protected EmployeeSalary olafSalary;
	
	@BeforeEach
	/**
	 * Tasks to be performed before each test is run.
	 */
	protected void setUp() {
		this.olaf = new Employee();
		this.olaf.setId(1);
		this.olaf.setFirstName("Olaf");
		this.olaf.setLastName("Utan");
		this.olaf.setGender(Gender.MALE);
		
		this.olafSalary = new EmployeeSalary();
		this.olafSalary.setId(this.olaf.getId());
		this.olafSalary.setMonthlySalary(1000);
		this.olafSalary.setSalaryLastChange(new Date());
		
		this.olaf.setSalaryData(this.olafSalary);		
	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	protected void tearDown() {
		this.olaf = null;
	}
	
	
	@Test
	/**
	 * Tests validation of a valid employee.
	 */
	public void testValidationSuccess() {
		try {
			this.olaf.validate();
		}
		catch(Exception exception) {
			fail(exception.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests validation of an employee whose ID is too small.
	 */
	public void testIdTooSmall() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.olaf.setId(0);
		
		String expectedErrorMessage = messageProvider.getMinValidationMessage("employee", "id", "1");
		String errorMessage = "";
		
		try {
			this.olaf.validate();
			fail("Validation should have failed because ID is too small.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of an employee whose ID is too big.
	 */
	public void testIdTooBig() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.olaf.setId(10000);
		
		String expectedErrorMessage = messageProvider.getMaxValidationMessage("employee", "id", "9999");
		String errorMessage = "";
		
		try {
			this.olaf.validate();
			fail("Validation should have failed because ID is too big.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of an employee whose first name is not given.
	 */
	public void testFirstNameNotGiven() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.olaf.setFirstName("");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("employee", "firstName", 
				String.valueOf(this.olaf.getFirstName().length()), "1", "50");
		String errorMessage = "";
		
		try {
			this.olaf.validate();
			fail("Validation should have failed because first name is not given.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of an employee whose first name is too long.
	 */
	public void testFirstNameTooLong() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.olaf.setFirstName("Olaf Freiherr von Utan zu Bonobo und Bana-Joe Billy");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("employee", "firstName", 
				String.valueOf(this.olaf.getFirstName().length()), "1", "50");
		String errorMessage = "";
		
		try {
			this.olaf.validate();
			fail("Validation should have failed because first name is too long.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of an employee whose last name is not given.
	 */
	public void testLastNameNotGiven() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.olaf.setLastName("");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("employee", "lastName", 
				String.valueOf(this.olaf.getLastName().length()), "1", "50");
		String errorMessage = "";
		
		try {
			this.olaf.validate();
			fail("Validation should have failed because last name is not given.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of an employee whose last name is too long.
	 */
	public void testLastNameTooLong() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.olaf.setLastName("Bono Beau Bono Beau Bono Beau Bono Beau Bono Beautox");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("employee", "lastName", 
				String.valueOf(this.olaf.getLastName().length()), "1", "50");
		String errorMessage = "";
		
		try {
			this.olaf.validate();
			fail("Validation should have failed because last name is too long.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of an employee whose gender is not set.
	 */
	public void testGenderNotSet() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();		
		this.olaf.setGender(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("employee", "gender");
		String errorMessage = "";
		
		try {
			this.olaf.validate();
			fail("Validation should have failed because gender is not set.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of an employee whose salary data ID differs from the employee ID.
	 */
	public void testInvalidSalaryDataId() {
		this.olaf.getSalaryData().setId(this.olaf.getId()+1);
		
		try {
			this.olaf.validate();
			fail("Validation should have failed because salary ID differs from employee ID");
		} 
		catch (IdentifierMismatchException expected) {
			//Expected exception. Do nothing.
		} 
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
