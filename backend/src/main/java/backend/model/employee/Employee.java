package backend.model.employee;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;

import backend.exception.IdentifierMismatchException;
import backend.model.department.Department;

/**
 * An employee.
 *
 * @author Michael
 */
@XmlRootElement(name = "employee")
@Table(name = "EMPLOYEE")
@Entity
@SequenceGenerator(name = "employeeSequence", initialValue = 1, allocationSize = 1)
public class Employee {
    /**
     * The maximum employee ID value allowed.
     */
    private static final int MAX_ID_VALUE = 9999;

    /**
     * The maximum first name field length allowed.
     */
    private static final int MAX_FIRST_NAME_LENGTH = 50;

    /**
     * The maximum last name field length allowed.
     */
    private static final int MAX_LAST_NAME_LENGTH = 50;

    /**
     * Distinct ID of the employee.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employeeSequence")
    @Column(name = "EMPLOYEE_ID")
    @Min(value = 1, message = "{employee.id.min.message}")
    @Max(value = MAX_ID_VALUE, message = "{employee.id.max.message}")
    private Integer id;

    /**
     * The first name.
     */
    @Column(name = "FIRST_NAME", length = MAX_FIRST_NAME_LENGTH)
    @Size(min = 1, max = MAX_FIRST_NAME_LENGTH, message = "{employee.firstName.size.message}")
    private String firstName;

    /**
     * The last name.
     */
    @Column(name = "LAST_NAME", length = MAX_LAST_NAME_LENGTH)
    @Size(min = 1, max = MAX_LAST_NAME_LENGTH, message = "{employee.lastName.size.message}")
    private String lastName;

    /**
     * The gender.
     */
    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{employee.gender.notNull.message}")
    private Gender gender;

    /**
     * Salary data of the employee.
     */
    @OneToOne(targetEntity = EmployeeSalary.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "SALARY_ID")
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

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(final Gender gender) {
        this.gender = gender;
    }

    /**
     * @return the salaryData
     */
    public EmployeeSalary getSalaryData() {
        return salaryData;
    }

    /**
     * @param salaryData the salaryData to set
     */
    public void setSalaryData(final EmployeeSalary salaryData) {
        this.salaryData = salaryData;

        if (salaryData != null) {
            this.salaryData.setId(this.id);
        }
    }

    /**
     * @return the headOfDepartment
     */
    @XmlTransient // Prevents cycle: Employee -> Department -> Employee
    public Department getHeadOfDepartment() {
        return headOfDepartment;
    }

    /**
     * @param headOfDepartment the headOfDepartment to set
     */
    public void setHeadOfDepartment(final Department headOfDepartment) {
        this.headOfDepartment = headOfDepartment;
    }

    /**
     * Validates the employee.
     *
     * @throws Exception                   In case a general validation error occurred.
     * @throws IdentifierMismatchException In case the ID of the employee and its salary data differ.
     */
    public void validate() throws Exception, IdentifierMismatchException {
        this.validateAnnotations();
        this.validateAdditionalCharacteristics();

        if (this.salaryData != null) {
            this.salaryData.validate();
        }
    }

    /**
     * Validates the employee according to the annotations of the Validation Framework.
     *
     * @exception Exception In case the validation failed.
     */
    private void validateAnnotations() throws Exception {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
                .constraintExpressionLanguageFeatureLevel(ExpressionLanguageFeatureLevel.BEAN_METHODS)
                .buildValidatorFactory();

        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Employee>> violations = validator.validate(this);

        for (ConstraintViolation<Employee> violation : violations) {
            throw new Exception(violation.getMessage());
        }
    }

    /**
     * Validates additional characteristics of the employee besides annotations.
     *
     * @throws IdentifierMismatchException In case the ID of the employee and its salary data differ.
     */
    private void validateAdditionalCharacteristics() throws IdentifierMismatchException {
        if (this.getSalaryData() != null && this.id != this.getSalaryData().getId()) {
            throw new IdentifierMismatchException();
        }
    }

    /**
     * Calculates the hashCode of an Employee.
     */
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

    /**
     * Indicates whether some other Employee is "equal to" this one.
     */
    @Override
    public boolean equals(final Object obj) {
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
