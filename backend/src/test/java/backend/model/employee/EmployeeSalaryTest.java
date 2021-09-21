package backend.model.employee;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.model.employee.EmployeeSalary;
import backend.tools.test.ValidationMessageProvider;

/**
 * Tests the employee salary model.
 * 
 * @author Michael
 */
public class EmployeeSalaryTest {
	/**
	 * The salary under test.
	 */
	protected EmployeeSalary salary;
	
	
	@BeforeEach
	/**
	 * Tasks to be performed before each test is run.
	 */
	protected void setUp() {
		this.salary = new EmployeeSalary();
		this.salary.setId(1);
		this.salary.setMonthlySalary(5000);
		this.salary.setSalaryLastChange(new Date());
	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	protected void tearDown() {
		this.salary = null;
	}
	
	
	@Test
	/**
	 * Tests validation of a valid salary.
	 */
	public void testValidationSuccess() {
		try {
			this.salary.validate();
		}
		catch(Exception exception) {
			fail(exception.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests validation of a monthly salary that is negative.
	 */
	public void testSalaryNegative() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.salary.setMonthlySalary(-1);
		
		String expectedErrorMessage = messageProvider.getMinValidationMessage("employeeSalary", "monthlySalary", "0");
		String errorMessage = "";
		
		try {
			this.salary.validate();
			fail("Validation should have failed because monthly salary is negative.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation of a monthly salary that is too high.
	 */
	public void testSalaryTooHigh() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.salary.setMonthlySalary(1000000);
		
		String expectedErrorMessage = messageProvider.getMaxValidationMessage("employeeSalary", "monthlySalary", "999999");
		String errorMessage = "";
		
		try {
			this.salary.validate();
			fail("Validation should have failed because monthly salary is too high.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests validation if the last change date is not given.
	 */
	public void testDateNotGiven() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.salary.setSalaryLastChange(null);
		
		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("employeeSalary", "salaryLastChange");
		String errorMessage = "";
		
		try {
			this.salary.validate();
			fail("Validation should have failed because date of last change is not set.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
}
