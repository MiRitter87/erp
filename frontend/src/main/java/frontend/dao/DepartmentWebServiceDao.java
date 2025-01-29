package frontend.dao;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.ws.WebServiceException;

import frontend.generated.ws.soap.department.DepartmentArray;
import frontend.generated.ws.soap.department.DepartmentService;
import frontend.generated.ws.soap.department.DepartmentService_Service;
import frontend.generated.ws.soap.department.WebServiceResult;
import frontend.model.Department;

/**
 * Data Access Object that provides access to department data via WebService.
 *
 * @author Michael
 */
public class DepartmentWebServiceDao extends WebServiceDao implements DepartmentDao {
    /**
     * WebService to access department data.
     */
    private DepartmentService departmentService;

    /**
     * Initializes the DAO.
     *
     * @throws WebServiceException In case the WebService is unavailable.
     */
    public DepartmentWebServiceDao() throws WebServiceException {
        this.departmentService = new DepartmentService_Service().getDepartmentSoapServiceImplPort();
    }

    /**
     * Inserts a department.
     */
    @Override
    public void insertDepartment(final Department department) throws Exception {
        WebServiceResult webServiceResult;

        webServiceResult = this.departmentService.addDepartment(department.getWebServiceDepartment());
        this.raiseExceptionForErrors(webServiceResult.getMessages());
    }

    /**
     * Deletes a department.
     */
    @Override
    public void deleteDepartment(final Department department) throws Exception {
        WebServiceResult webServiceResult;

        webServiceResult = this.departmentService.deleteDepartment(department.getCode());
        this.raiseExceptionForErrors(webServiceResult.getMessages());
    }

    /**
     * Gets all departments.
     */
    @Override
    public List<Department> getDepartments() throws Exception {
        WebServiceResult webServiceResult;
        DepartmentArray departmentArray;
        List<Department> departments = new ArrayList<Department>();

        webServiceResult = this.departmentService.getDepartments();
        this.raiseExceptionForErrors(webServiceResult.getMessages());

        // Check WebService result data
        if (webServiceResult.getData() instanceof DepartmentArray) {
            departmentArray = (DepartmentArray) webServiceResult.getData();

            // Wrap WebService model of department to local model of department
            for (frontend.generated.ws.soap.department.Department wsDepartment : departmentArray.getDepartments()
                    .getDepartment()) {
                departments.add(new Department(wsDepartment));
            }
        }

        return departments;
    }

    /**
     * Updates an existing department.
     */
    @Override
    public void updateDepartment(final Department department) throws Exception {
        WebServiceResult webServiceResult;

        webServiceResult = this.departmentService.updateDepartment(department.getWebServiceDepartment());
        this.raiseExceptionForErrors(webServiceResult.getMessages());
    }

}
