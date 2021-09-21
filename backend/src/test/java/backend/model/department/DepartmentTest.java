package backend.model.department;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.model.department.Department;
import backend.model.employee.Employee;
import backend.model.employee.Gender;
import backend.tools.test.ValidationMessageProvider;

/**
 * Tests the department model. 
 * 
 * @author Michael
 */
public class DepartmentTest {
	/**
	 * The department under test.
	 */
	protected Department department;
	
	/**
	 * The head of the department under test.
	 */
	protected Employee headOfDepartment;
	
	
	@BeforeEach
	/**
	 * Tasks to be performed before each test is run.
	 */
	protected void setUp() {
		this.headOfDepartment = new Employee();
		this.headOfDepartment.setId(1);
		this.headOfDepartment.setFirstName("Michael");
		this.headOfDepartment.setLastName("Ritter");
		this.headOfDepartment.setGender(Gender.MALE);
		
		this.department = new Department();
		this.department.setCode("SD");
		this.department.setName("Software Development");
		this.department.setDescription("Responsible for development and maintenance of software.");
		this.department.setHead(this.headOfDepartment);
	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	protected void tearDown() {
		this.department = null;
		this.headOfDepartment = null;
	}
	
	
	@Test
	/**
	 * Tests the validation of a valid department.
	 */
	public void testValidationSuccess() {
		try {
			this.department.validate();
		}
		catch(Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	/**
	 * Tests the validation method while the code length is longer than the specified size.
	 */
	public void testCodeLengthTooBigValidation() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.department.setCode("ABCDEF");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("department", "code", 
				String.valueOf(this.department.getCode().length()), String.valueOf(1), String.valueOf(5));
		String errorMessage = "";
		
		try {
			this.department.validate();
			fail("Validation should have failed because code is too long.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
	
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests the validation method while the code length is smaller than the specified size.
	 */
	public void testCodeLengthTooSmallValidation() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.department.setCode("");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("department", "code", 
				String.valueOf(this.department.getCode().length()), String.valueOf(1), String.valueOf(5));
		String errorMessage = "";
		
		try {
			this.department.validate();
			fail("Validation should have failed because code is too small.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
		
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests the validation method while the name length is bigger than the specified size.
	 */
	public void testNameLengthTooBigValidation() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.department.setName("This is the name of the department that is too long");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("department", "name", 
				String.valueOf(this.department.getName().length()), String.valueOf(1), String.valueOf(50));
		String errorMessage = "";
		
		try {
			this.department.validate();
			fail("Validation should have failed because name is too long.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
	
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests the validation method while the description is bigger than the specified size.
	 */
	public void testDescriptionTooBigValidation() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.department.setDescription("This is a long name of a department description. "
				+ "The goal is to reach at least 251 chars because the maximum size of the attribute is defined at 250 chars. "
				+ "This is a long name of a department description. The goal is to reach at least 251 chars "
				+ "because the maximum size of the attribute is defined at 250 chars.");
		
		String expectedErrorMessage = messageProvider.getSizeValidationMessage("department", "description", 
				String.valueOf(this.department.getDescription().length()), String.valueOf(0), String.valueOf(250));
		String errorMessage = "";
		
		try {
			this.department.validate();
			fail("Validation should have failed because description is too long.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
	
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	
	@Test
	/**
	 * Tests the validation method while no head of department is defined.
	 */
	public void testNoHeadValidation() {
		ValidationMessageProvider messageProvider = new ValidationMessageProvider();
		this.department.setHead(null);

		String expectedErrorMessage = messageProvider.getNotNullValidationMessage("department", "head");
		String errorMessage = "";
		
		try {
			this.department.validate();
			fail("Validation should have failed because no head of department is set.");
		} 
		catch (Exception expected) {
			errorMessage = expected.getMessage();
		}
	
		assertEquals(expectedErrorMessage, errorMessage);
	}
}
