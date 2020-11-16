package backend.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A list of employees.
 * 
 * @author Michael
 */
@XmlRootElement(name="employees")
public class EmployeeArray {
	/**
	 * A list of employees.
	 */
    private List<Employee> employees = null;
 
    @XmlElementWrapper(name="employees")
    @XmlElement(name="employee")
    public List<Employee> getEmployees() {
        return employees;
    }
 
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
