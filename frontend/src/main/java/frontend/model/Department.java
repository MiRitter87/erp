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
	 * The HashCode of the department state that has been persisted to the backend database.
	 */
	private int persistedHash;
	
	
	/**
	 * Default cosntructor.
	 */
	public Department() {
		
	}
	
	
	/**
	 * Initializes the department based on the data of the given department. A copy is created.
	 * 
	 * @param department The department which data are copied.
	 */
	public Department(final Department department) {
		this.code = department.code;
		this.name = department.name;
		this.description = department.description;
		this.head = department.head;
		this.persistedHash = department.persistedHash;
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
		
		this.persistedHash = this.hashCode();
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
	
	
	/**
	 * Checks if the object has been edited after initialization.
	 * 
	 * @return true, if object has been edited.
	 */
	public boolean isEdited() {
		int currentHash = this.hashCode();
		
		if(this.persistedHash != currentHash)
			return true;
		else		
			return false;
	}
	
	
	/**
	 * Calculates the hash code of the current object state.
	 * The hashCode is then stored as reference for further checks if object has changed.
	 */
	public void reHash() {
		this.persistedHash = this.hashCode();
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((head == null) ? 0 : head.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (head == null) {
			if (other.head != null)
				return false;
		} else if (!head.equals(other.head))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
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
}
