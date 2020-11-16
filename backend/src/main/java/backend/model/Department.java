package backend.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * A department.
 * 
 * @author Michael
 */
@XmlRootElement(name="department")
@Table(name="DEPARTMENT")
@Entity
public class Department {
	/**
	 * The department code.
	 */
	@Id
	@Column(name="CODE", length = 5)
	@Size(min = 1, max = 5, message = "{department.code.size.message}")
	private String code;
	
	/**
	 * The name of the department.
	 */
	@Column(name="NAME", length = 50)
	@Size(min = 1, max = 50, message = "{department.name.size.message}")
	private String name;
	
	/**
	 * A description of the tasks and responsibilities of the department.
	 */
	@Column(name="DESCRIPTION", length = 250)
	@Size(min = 0, max = 250, message = "{department.description.size.message}")
	private String description;
	
	/**
	 * The employee that is the head of the department.
	 */
	@OneToOne(targetEntity = Employee.class)
	@JoinColumn(name="EMPLOYEE_ID")
	@NotNull(message = "{department.head.notNull.message}")
	private Employee head;

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the head
	 */
	public Employee getHead() {
		return head;
	}

	/**
	 * @param head the head to set
	 */
	public void setHead(Employee head) {
		this.head = head;
	}
	
	
	/**
	 * Validates the department according to the given annotations.
	 */
	public void validate() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Department>> violations = validator.validate(this);
		
		for(ConstraintViolation<Department> violation:violations) {
			throw new Exception(violation.getMessage());
		}
	}
}
