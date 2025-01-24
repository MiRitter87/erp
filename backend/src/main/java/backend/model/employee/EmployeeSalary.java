package backend.model.employee;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * The salary data of an employee.
 *
 * @author Michael
 */
@XmlRootElement(name = "salary")
@Table(name = "EMPLOYEE_SALARY")
@Entity
public class EmployeeSalary {
    /**
     * The maximum employee ID value allowed.
     */
    private static final int MAX_ID_VALUE = 9999;

    /**
     * The maximum monthly salary value allowed.
     */
    private static final int MAX_SALARY_VALUE = 999999;

    /**
     * The id of the salary data set.
     */
    @Id
    @Column(name = "SALARY_ID")
    @Min(value = 1, message = "{employee.id.min.message}") // Use employee message because the ID is shared.
    @Max(value = MAX_ID_VALUE, message = "{employee.id.max.message}") // Use employee message because the ID is shared.
    private Integer id;

    /**
     * The basic salary that an employee receives each month.
     */
    @Column(name = "MONTHLY_SALARY")
    @Min(value = 0, message = "{employeeSalary.monthlySalary.min.message}")
    @Max(value = MAX_SALARY_VALUE, message = "{employeeSalary.monthlySalary.max.message}")
    private int monthlySalary;

    /**
     * The date on which the salary has been changed the last time.
     */
    @Column(name = "SALARY_LAST_CHANGE")
    @NotNull(message = "{employeeSalary.salaryLastChange.notNull.message}")
    private Date salaryLastChange;

    /**
     * Default-Constructor.
     */
    public EmployeeSalary() {

    }

    /**
     * Creates a new employee salary.
     *
     * @param monthlySalary The monthly salary of the employee.
     */
    public EmployeeSalary(final int monthlySalary) {
        this.salaryLastChange = new Date();
        this.monthlySalary = monthlySalary;
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
     * @return the monthlySalary
     */
    public int getMonthlySalary() {
        return monthlySalary;
    }

    /**
     * @param monthlySalary the monthlySalary to set
     */
    public void setMonthlySalary(final int monthlySalary) {
        this.salaryLastChange = new Date();
        this.monthlySalary = monthlySalary;
    }

    /**
     * @return the salaryLastChange
     */
    public Date getSalaryLastChange() {
        return salaryLastChange;
    }

    /**
     * @param salaryLastChange the salaryLastChange to set
     */
    public void setSalaryLastChange(final Date salaryLastChange) {
        this.salaryLastChange = salaryLastChange;
    }

    /**
     * Validates the employee salary according to the given annotations.
     *
     * @throws Exception In case the validation failed.
     */
    public void validate() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<EmployeeSalary>> violations = validator.validate(this);

        for (ConstraintViolation<EmployeeSalary> violation : violations) {
            throw new Exception(violation.getMessage());
        }
    }

    /**
     * Calculates the hashCode of an EmployeeSalary.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + monthlySalary;
        result = prime * result + ((salaryLastChange == null) ? 0 : salaryLastChange.hashCode());
        return result;
    }

    /**
     * Indicates whether some other EmployeeSalary is "equal to" this one.
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
        EmployeeSalary other = (EmployeeSalary) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (monthlySalary != other.monthlySalary) {
            return false;
        }
        if (salaryLastChange == null && other.salaryLastChange != null) {
            return false;
        }
        if (salaryLastChange != null && other.salaryLastChange == null) {
            return false;
        }
        if (salaryLastChange != null && other.salaryLastChange != null) {
            if (salaryLastChange.getTime() != other.salaryLastChange.getTime()) {
                return false;
            }
        }

        return true;
    }
}
