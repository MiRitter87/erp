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
	
	//TODO: Neues Attribut "initialHash" und Hashfunktion. Dann über änderbare Attribute den Hash bilden für Abgleich ob Objekt geändert wurde.
	
	/**
	 * Default constructor.
	 */
	public Employee() {
		
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
		
		if(employeeSalary != null)
			this.salaryData.setId(this.id);
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
}
