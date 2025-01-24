package backend.model.employee;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * A list of employees.
 *
 * @author Michael
 */
@XmlRootElement(name = "employees")
public class EmployeeArray {
    /**
     * A list of employees.
     */
    private List<Employee> employees = null;

    /**
     * @return the employees
     */
    @XmlElementWrapper(name = "employees")
    @XmlElement(name = "employee")
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * @param employees the employees to set
     */
    public void setEmployees(final List<Employee> employees) {
        this.employees = employees;
    }
}
