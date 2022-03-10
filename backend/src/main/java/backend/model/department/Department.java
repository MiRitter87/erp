package backend.model.department;

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

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;

import backend.model.employee.Employee;


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
		ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)   
                .configure().constraintExpressionLanguageFeatureLevel(ExpressionLanguageFeatureLevel.BEAN_METHODS)
                .buildValidatorFactory();

		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<Department>> violations = validator.validate(this);
		
		for(ConstraintViolation<Department> violation:violations) {
			throw new Exception(violation.getMessage());
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((head == null) ? 0 : head.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Department other = (Department) obj;
		if (code == null) {
			if (other.code != null) {
				return false;
			}
		} else if (!code.equals(other.code)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (head == null) {
			if (other.head != null) {
				return false;
			}
		} else if (!head.getId().equals(other.head.getId())) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
}
