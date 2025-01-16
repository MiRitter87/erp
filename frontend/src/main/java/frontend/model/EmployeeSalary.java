package frontend.model;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

/**
 * The salary data of an employee.
 *
 * @author Michael
 */
public class EmployeeSalary {
    /**
     * The ID of the salary data set.
     */
    private Integer id;

    /**
     * The basic salary that an employee receives each month.
     */
    private int monthlySalary;

    /**
     * The date on which the salary has been changed the last time.
     */
    private Date salaryLastChange;

    /**
     * The HashCode of the employee salary state that has been persisted to the backend database.
     */
    private int persistedHash;

    /**
     * Creates a new employee salary.
     *
     * @param id            The ID of the employee the salary corresponds to.
     * @param monthlySalary The monthly salary of the employee.
     */
    public EmployeeSalary(final Integer id, final int monthlySalary) {
        this.id = id;
        this.salaryLastChange = new Date();
        this.monthlySalary = monthlySalary;

        this.persistedHash = this.hashCode();
    }

    /**
     * Initialize the model using data provided by the employee WebService model.
     *
     * @param webServiceEmployeeSalary The salary data as provided by the employee WebService
     */
    public EmployeeSalary(final frontend.generated.ws.soap.employee.EmployeeSalary webServiceEmployeeSalary) {
        this.id = webServiceEmployeeSalary.getId();
        this.monthlySalary = webServiceEmployeeSalary.getMonthlySalary();
        this.salaryLastChange = webServiceEmployeeSalary.getSalaryLastChange().toGregorianCalendar().getTime();

        this.persistedHash = this.hashCode();
    }

    /**
     * Initialize the model using data provided by the department WebService model.
     *
     * @param webServiceEmployeeSalary The salary data as provided by the department WebService
     */
    public EmployeeSalary(final frontend.generated.ws.soap.department.EmployeeSalary webServiceEmployeeSalary) {
        this.id = webServiceEmployeeSalary.getId();
        this.monthlySalary = webServiceEmployeeSalary.getMonthlySalary();
        this.salaryLastChange = webServiceEmployeeSalary.getSalaryLastChange().toGregorianCalendar().getTime();

        this.persistedHash = this.hashCode();
    }

    /**
     * Converts the current salary into a type that is used by the employee WebService.
     *
     * @return A representation of this salary that can be processed by the employee WebService.
     * @exception DatatypeConfigurationException In case the conversion of the last change date failed.
     */
    public frontend.generated.ws.soap.employee.EmployeeSalary getEmployeeWebServiceSalary()
            throws DatatypeConfigurationException {
        frontend.generated.ws.soap.employee.EmployeeSalary webServiceSalary =
                new frontend.generated.ws.soap.employee.EmployeeSalary();

        webServiceSalary.setId(this.id);
        webServiceSalary.setMonthlySalary(this.monthlySalary);

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(this.salaryLastChange);
        webServiceSalary.setSalaryLastChange(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));

        return webServiceSalary;
    }

    /**
     * Converts the current salary into a type that is used by the department WebService.
     *
     * @return A representation of this salary that can be processed by the department WebService.
     * @exception DatatypeConfigurationException In case the conversion of the last change date failed.
     */
    public frontend.generated.ws.soap.department.EmployeeSalary getDepartmentWebServiceSalary()
            throws DatatypeConfigurationException {
        frontend.generated.ws.soap.department.EmployeeSalary webServiceSalary =
                new frontend.generated.ws.soap.department.EmployeeSalary();

        webServiceSalary.setId(this.id);
        webServiceSalary.setMonthlySalary(this.monthlySalary);

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(this.salaryLastChange);
        webServiceSalary.setSalaryLastChange(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));

        return webServiceSalary;
    }

    /**
     * Checks if the object has been edited after initialization.
     *
     * @return true, if object has been edited.
     */
    public boolean isEdited() {
        int currentHash = this.hashCode();

        if (this.persistedHash != currentHash) {
            return true;
        }

        return false;
    }

    /**
     * Returns a hash code value for the object.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + monthlySalary;
        return result;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
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
        return true;
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
        if (this.monthlySalary == monthlySalary) {
            return;
        }

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
}
