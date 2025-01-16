package frontend.dao;

import java.util.List;

import frontend.generated.ws.soap.employee.EmployeeHeadQueryParameter;
import frontend.model.Employee;

/**
 * Interface for employee persistence.
 *
 * @author Michael
 */
public interface EmployeeDao {
    /**
     * Inserts an employee.
     *
     * @param employee The employee to be inserted.
     * @throws Exception Insertion failed.
     */
    void insertEmpoyee(Employee employee) throws Exception;

    /**
     * Deletes an employee.
     *
     * @param employee The employee to be deleted.
     * @throws Exception Deletion failed.
     */
    void deleteEmployee(Employee employee) throws Exception;

    /**
     * Gets all employees that match the given query parameters.
     *
     * @param employeeHeadQueryParameter Specifies the employees to be selected based on the head attribute.
     * @return All employees that match the given query parameters.
     * @throws Exception Employee retrieval failed.
     */
    List<Employee> getEmployees(EmployeeHeadQueryParameter employeeHeadQueryParameter) throws Exception;

    /**
     * Updates an existing employee.
     *
     * @param employee The employee to be updated.
     * @throws Exception Update failed.
     */
    void updateEmployee(Employee employee) throws Exception;
}
