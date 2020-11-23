package frontend.model;

import javax.xml.datatype.DatatypeConfigurationException;

/**
 * An employee.
 * 
 * @author Michael
 */
public class Employee {
	/**
	 * Distinct ID of the employee.
	 */
	private Integer id;
	
	/**
	 * The first name.
	 */
	private String firstName;
	
	/**
	 * The last name
	 */
	private String lastName;
	
	/**
	 * The gender
	 */
	private Gender gender;
	
	/**
	 * The salary data.
	 */
	private EmployeeSalary salaryData;
	
	/**
	 * The HashCode of the employee state that has been persisted to the backend database.
	 */
	private int persistedHash;
	
	
	/**
	 * Initializes the employee.
	 * 
	 * @param firstName The first name of the employee.
	 * @param lastName The last name of the employee.
	 * @param gender The gender of the employee.
	 */
	public Employee(final String firstName, final String lastName, final Gender gender) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		
		this.persistedHash = this.hashCode();
	}
	
	
	/**
	 * Initialize the model using data provided by the employee WebService model.
	 * 
	 * @param webServiceEmployee The employee data as provided by the employee WebService.
	 */
	public Employee(final frontend.generated.ws.soap.employee.Employee webServiceEmployee) {
		this.id = webServiceEmployee.getId();
		this.firstName = webServiceEmployee.getFirstName();
		this.lastName = webServiceEmployee.getLastName();
		this.gender = Gender.convertToGender(webServiceEmployee.getGender());
		
		if(webServiceEmployee.getSalaryData() != null)
			this.salaryData = new EmployeeSalary(webServiceEmployee.getSalaryData());
		
		this.persistedHash = this.hashCode();
	}
	
	
	/**
	 * Initialize the model using data provided by the department WebService model.
	 * 
	 * @param webServiceEmployee The employee data as provided by the department WebService.
	 */
	public Employee(final frontend.generated.ws.soap.department.Employee webServiceEmployee) {
		this.id = webServiceEmployee.getId();
		this.firstName = webServiceEmployee.getFirstName();
		this.lastName = webServiceEmployee.getLastName();
		this.gender = Gender.convertToGender(webServiceEmployee.getGender());
		
		if(webServiceEmployee.getSalaryData() != null)
			this.salaryData = new EmployeeSalary(webServiceEmployee.getSalaryData());
		
		this.persistedHash = this.hashCode();
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public EmployeeSalary getSalaryData() {
		return salaryData;
	}

	public void setSalaryData(EmployeeSalary employeeSalary) {
		this.salaryData = employeeSalary;
	}

	/**
	 * Converts the current employee into a type that is used by the employee WebService.
	 * 
	 * @return A representation of this employee that can be processed by the employee WebService.
	 * @throws DatatypeConfigurationException In case the conversion of the last change date of employee salary failed.
	 */
	public frontend.generated.ws.soap.employee.Employee getWebServiceEmployeeForEmployeeService() throws DatatypeConfigurationException {
		frontend.generated.ws.soap.employee.Employee webServiceEmployee = new frontend.generated.ws.soap.employee.Employee();
		
		webServiceEmployee.setId(this.id);
		webServiceEmployee.setFirstName(this.firstName);
		webServiceEmployee.setLastName(this.lastName);
		webServiceEmployee.setGender(Gender.convertoToEmployeeWebServiceGender(this.gender));
		
		if(this.salaryData != null)
			webServiceEmployee.setSalaryData(this.salaryData.getEmployeeWebServiceSalary());
		
		return webServiceEmployee;
	}
	
	
	/**
	 * Converts the current employee into a type that is used by the department WebService.
	 * 
	 * @return A representation of this employee that can be processed by the department WebService.
	 * @throws DatatypeConfigurationException In case the conversion of the last change date of employee salary failed.
	 */
	public frontend.generated.ws.soap.department.Employee getWebServiceEmployeeForDepartmentService() throws DatatypeConfigurationException {
		frontend.generated.ws.soap.department.Employee webServiceEmployee = new frontend.generated.ws.soap.department.Employee();
		
		webServiceEmployee.setId(this.id);
		webServiceEmployee.setFirstName(this.firstName);
		webServiceEmployee.setLastName(this.lastName);
		webServiceEmployee.setGender(Gender.convertoToDepartmentWebServiceGender(this.gender));
		
		if(this.salaryData != null)
			webServiceEmployee.setSalaryData(this.salaryData.getDepartmentWebServiceSalary());
		
		return webServiceEmployee;
	}
	
	
	/**
	 * Returns the full name of the employee consisting of first and last name.
	 * 
	 * @returnThe full name.
	 */
	public String getFullName() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append(this.firstName);
		stringBuilder.append(" ");
		stringBuilder.append(this.lastName);
		
		return stringBuilder.toString();
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
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
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
		Employee other = (Employee) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (gender != other.gender)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}
}
