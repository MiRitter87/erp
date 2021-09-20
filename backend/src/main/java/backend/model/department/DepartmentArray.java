package backend.model.department;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A list of departments.
 * 
 * @author Michael
 */
@XmlRootElement(name="departments")
public class DepartmentArray {
	/**
	 * A list of departments.
	 */
    private List<Department> departments = null;
 
    
    @XmlElementWrapper(name="departments")
    @XmlElement(name="department")
    public List<Department> getDepartments() {
        return departments;
    }
 
    
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}
