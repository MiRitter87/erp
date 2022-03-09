package frontend.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


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
	
	
	@BeforeEach
	/**
	 * Tasks to be performed before each test is run.
	 */
	protected void setUp() {
		this.olaf = new Employee("olaf", "Utan", Gender.MALE);
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
	 * Tests if the object correctly states that it has been edited.
	 */
	public void testIsEdited() {
		this.olaf.setFirstName("Olafson");
		
		boolean isEdited = this.olaf.isEdited();
		
		assertTrue(isEdited);
	}
	
	
	@Test
	/**
	 * Tests if the object correctly states that it has not been edited.
	 */
	public void testIsNotEdited() {
		boolean isEdited = this.olaf.isEdited();
		
		assertFalse(isEdited);
	}
}
