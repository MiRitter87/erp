package backend.model.department;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * A list of departments.
 *
 * @author Michael
 */
@XmlRootElement(name = "departments")
public class DepartmentArray {
    /**
     * A list of departments.
     */
    private List<Department> departments = null;

    /**
     * @return the departments
     */
    @XmlElementWrapper(name = "departments")
    @XmlElement(name = "department")
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * @param departments the departments to set
     */
    public void setDepartments(final List<Department> departments) {
        this.departments = departments;
    }
}
