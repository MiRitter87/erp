package backend.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import backend.exception.IdentifierMismatchException;


/**
 * An employee.
 * 
 * @author Michael
 */
@XmlRootElement(name="employee")
@Table(name="EMPLOYEE")
@Entity
@SequenceGenerator(name = "employeeSequence", initialValue = 1, allocationSize = 1)
public class Employee {
	/**
	 * Distinct ID of the employee.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employeeSequence")
	@Column(name="EMPLOYEE_ID")
	@Min(value = 1, message = "{employee.id.min.message}")
	@Max(value = 9999, message = "{employee.id.max.message}")
	private Integer id;
	
	/**
	 * The first name.
	 */
	@Column(name="FIRST_NAME", length = 50)
	@Size(min = 1, max = 50, message = "{employee.firstName.size.message}")
	private String firstName;
	
	/**
	 * The last name
	 */
	@Column(name="LAST_NAME", length = 50)
	@Size(min = 1, max = 50, message = "{employee.lastName.size.message}")
	private String lastName;
	
	/**
	 * The gender
	 */
	@Column(name="GENDER")
	@Enumerated(EnumType.STRING)
	@NotNull(message = "{employee.gender.notNull.message}")
	private Gender gender;
	
	/**
	 * Salary data of the employee.
	 */
	@OneToOne(targetEntity = EmployeeSalary.class, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="SALARY_ID")
	private EmployeeSalary salaryData;
	
	/**
	 * The department which the employee is head of.
	 */
	@OneToOne(mappedBy = "head")
	private Department headOfDepartment;
	
	
	/**
	 * Default Constructor.
	 */
	public Employee() {
		
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public EmployeeSalary getSalaryData() {
		return salaryData;
	}

	public void setSalaryData(EmployeeSalary salaryData) {
		this.salaryData = salaryData;
		
		if(salaryData != null)
			this.salaryData.setId(this.id);
	}

	/**
	 * @return the headOfDepartment
	 */
	@XmlTransient	//Prevents cycle: Employee -> Department -> Employee
	public Department getHeadOfDepartment() {
		return headOfDepartment;
	}

	/**
	 * @param headOfDepartment the headOfDepartment to set
	 */
	public void setHeadOfDepartment(Department headOfDepartment) {
		this.headOfDepartment = headOfDepartment;
	}
	
	
	/**
	 * Validates the employee.
	 * 
	 * @throws Exception In case a general validation error occurred.
	 * @throws IdentifierMismatchException In case the ID of the employee and its salary data differ.
	 */
	public void validate() throws Exception, IdentifierMismatchException {
		this.validateAnnotations();
		this.validateAdditionalCharacteristics();
	}
	
	
	/**
	 * Validates the employee according to the annotations of the Validation Framework.
	 * 
	 * @exception Exception In case the validation failed.
	 */
	public void validateAnnotations() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Employee>> violations = validator.validate(this);
		
		for(ConstraintViolation<Employee> violation:violations) {
			throw new Exception(violation.getMessage());
		}
	}
	
	
	/**
	 * Validates additional characteristics of the employee besides annotations.
	 * 
	 * @throws IdentifierMismatchException In case the ID of the employee and its salary data differ.
	 */
	public void validateAdditionalCharacteristics() throws IdentifierMismatchException {
		if(this.getSalaryData() != null && this.id != this.getSalaryData().getId())
			throw new IdentifierMismatchException();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((salaryData == null) ? 0 : salaryData.hashCode());
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
		Employee other = (Employee) obj;
		if (firstName == null) {
			if (other.firstName != null) {
				return false;
			}
		} else if (!firstName.equals(other.firstName)) {
			return false;
		}
		if (gender != other.gender) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		} else if (!lastName.equals(other.lastName)) {
			return false;
		}
		if (salaryData == null) {
			if (other.salaryData != null) {
				return false;
			}
		} else if (!salaryData.equals(other.salaryData)) {
			return false;
		}
		return true;
	}
 }
