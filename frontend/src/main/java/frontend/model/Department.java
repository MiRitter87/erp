package frontend.model;

import javax.xml.datatype.DatatypeConfigurationException;

/**
 * A department that is responsible for certain tasks within a company.
 * 
 * @author Michael
 */
public class Department {
	/**
	 * The department code.
	 */
	private String code;
	
	/**
	 * The name of the department.
	 */
	private String name;
	
	/**
	 * A description of the tasks and responsibilities of the department.
	 */
	private String description;
	
	/**
	 * The employee that is the head of the department.
	 */
	private Employee head;
	
	
	/**
	 * Default cosntructor.
	 */
	public Department() {
		
	}
	
	
	/**
	 * Initialize the model using data provided by the WebService model.
	 * 
	 * @param webServiceDepartment The department data as provided by the WebService
	 */
	public Department(final frontend.generated.ws.soap.department.Department webServiceDepartment) {
		this.code = webServiceDepartment.getCode();
		this.name = webServiceDepartment.getName();
		this.description = webServiceDepartment.getDescription();
		
		if(webServiceDepartment.getHead() != null)
			this.head = new Employee(webServiceDepartment.getHead());
	}


	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}


	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the head
	 */
	public Employee getHead() {
		return head;
	}


	/**
	 * @param head the head to set
	 */
	public void setHead(Employee head) {
		this.head = head;
	}
	
	
	/**
	 * Converts the current department into a type that is used by the department WebService.
	 * 
	 * @return A representation of this department that can be processed by the department WebService.
	 * @throws DatatypeConfigurationException In case the conversion of the last change date of employee salary failed.
	 */
	public frontend.generated.ws.soap.department.Department getWebServiceDepartment() throws DatatypeConfigurationException {
		frontend.generated.ws.soap.department.Department webServiceDepartment = new frontend.generated.ws.soap.department.Department();
		
		webServiceDepartment.setCode(this.code);
		webServiceDepartment.setName(this.name);
		webServiceDepartment.setDescription(this.description);
		
		if(this.head != null)
			webServiceDepartment.setHead(this.head.getWebServiceEmployeeForDepartmentService());
		
		return webServiceDepartment;
	}
}
