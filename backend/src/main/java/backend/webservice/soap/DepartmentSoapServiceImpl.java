package backend.webservice.soap;

import javax.jws.WebService;

import backend.model.Department;
import backend.model.WebServiceResult;
import backend.webservice.common.DepartmentService;

/**
 * WebService implementation for department access using SOAP technology.
 * 
 * @author Michael
 */
@WebService(endpointInterface="backend.webservice.soap.DepartmentSoapService",
serviceName = "departmentService")
public class DepartmentSoapServiceImpl implements DepartmentSoapService {
	/**
	 * The interface-independent department service.
	 */
	private DepartmentService departmentService;
	
	/**
	 * Initializes the Department Soap Service Implementation.
	 */
	public DepartmentSoapServiceImpl() {
		this.departmentService = new DepartmentService();
	}
	
	@Override
	public WebServiceResult deleteDepartment(String code) {
		return departmentService.deleteDepartment(code);
	}

	@Override
	public WebServiceResult getDepartment(String code) {
		return departmentService.getDepartment(code);
	}

	@Override
	public WebServiceResult getDepartments() {
		return departmentService.getDepartments();
	}

	@Override
	public WebServiceResult addDepartment(Department department) {
		return departmentService.addDepartment(department);
	}

	@Override
	public WebServiceResult updateDepartment(Department department) {
		return departmentService.updateDepartment(department);
	}

}
