package frontend.model;

import java.util.ArrayList;
import java.util.List;

import frontend.exception.EntityAlreadyExistsException;

/**
 * Encapsulates a list of multiple departments and provides convenience methods to work with that list.
 *
 * @author Michael
 */
public class DepartmentList {
    /**
     * The departments that are managed.
     */
    private List<Department> departments;

    /**
     * The default constructor initializing an empty list.
     */
    public DepartmentList() {
        this.departments = new ArrayList<Department>();
    }

    /**
     * A constructor creating a new DepartmentList with the given departments.
     *
     * @param departments An ArrayList of employees the EmployeeList is being initialized with.
     */
    public DepartmentList(final List<Department> departments) {
        this.departments = departments;
    }

    /**
     * @return the departments
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * @param departments the departments to set
     */
    public void setDepartments(final List<Department> departments) {
        this.departments = departments;
    }

    /**
     * Gets the department with the given code.
     *
     * @param code The code of the department to be searched.
     * @return The department with the given code. Null, if no department with the given code could be found.
     */
    public Department getDepartmentByCode(final String code) {
        for (Department tempDepartment : this.departments) {
            if (tempDepartment.getCode().equals(code)) {
                return tempDepartment;
            }
        }

        return null;
    }

    /**
     * Adds a department to the department list.
     *
     * @param department The department to be added to the list.
     * @throws EntityAlreadyExistsException In case the department already exists.
     */
    public void addDepartment(final Department department) throws EntityAlreadyExistsException {
        for (Department tempDepartment : this.departments) {
            if (tempDepartment.getCode().equals(department.getCode())) {
                throw new EntityAlreadyExistsException();
            }
        }

        this.departments.add(department);
    }

    /**
     * Deletes the given department from the list.
     *
     * @param department The department to be deleted.
     */
    public void deleteDepartment(final Department department) {
        if (department != null) {
            this.departments.remove(department);
        }
    }
}
