package frontend.model;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

/**
 * The salary data of an employee.
 * 
 * @author Michael
 */
public class EmployeeSalary {
	/**
	 * The ID of the salary data set.
	 */
	private Integer id;
	
	/**
	 * The basic salary that an employee receives each month.
	 */
	private int monthlySalary;
	
	/**
	 * The date on which the salary has been changed the last time.
	 */
	private Date salaryLastChange;
	
	
	/**
	 * Creates a new employee salary.
	 * 
	 * @param monthlySalary The monthly salary of the employee.
	 */
	public EmployeeSalary(final int monthlySalary) {
		this.salaryLastChange = new Date();
		this.monthlySalary = monthlySalary;
	}
	
	
	/**
	 * Initialize the model using data provided by the WebService model.
	 * 
	 * @param webServiceSalary The salary data as provided by the WebService
	 */
	public EmployeeSalary(final frontend.generated.ws.soap.employee.EmployeeSalary webServiceEmployeeSalary) {
		this.id = webServiceEmployeeSalary.getId();
		this.monthlySalary = webServiceEmployeeSalary.getMonthlySalary();
		this.salaryLastChange = webServiceEmployeeSalary.getSalaryLastChange().toGregorianCalendar().getTime();
	}
	
	
	/**
	 * Converts the current salary into a type that is used by the employee WebService.
	 * 
	 * @return A representation of this salary that can be processed by the employee WebService.
	 * @exception DatatypeConfigurationException  In case the conversion of the last change date failed.
	 */
	public frontend.generated.ws.soap.employee.EmployeeSalary getEmployeeWebServiceSalary() throws DatatypeConfigurationException {
		frontend.generated.ws.soap.employee.EmployeeSalary webServiceSalary = new frontend.generated.ws.soap.employee.EmployeeSalary();	
		
		webServiceSalary.setId(this.id);
		webServiceSalary.setMonthlySalary(this.monthlySalary);
		
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(this.salaryLastChange);
		webServiceSalary.setSalaryLastChange(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
		
		return webServiceSalary;
	}

	
	/**
	 * Converts the current salary into a type that is used by the department WebService.
	 * 
	 * @return A representation of this salary that can be processed by the department WebService.
	 * @exception DatatypeConfigurationException  In case the conversion of the last change date failed.
	 */
	public frontend.generated.ws.soap.department.EmployeeSalary getDepartmentWebServiceSalary() throws DatatypeConfigurationException {
		frontend.generated.ws.soap.department.EmployeeSalary webServiceSalary = new frontend.generated.ws.soap.department.EmployeeSalary();	
		
		webServiceSalary.setId(this.id);
		webServiceSalary.setMonthlySalary(this.monthlySalary);
		
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(this.salaryLastChange);
		webServiceSalary.setSalaryLastChange(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
		
		return webServiceSalary;
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getMonthlySalary() {
		return monthlySalary;
	}

	public void setMonthlySalary(int monthlySalary) {
		this.salaryLastChange = new Date();
		this.monthlySalary = monthlySalary;
	}

	public Date getSalaryLastChange() {
		return salaryLastChange;
	}

	public void setSalaryLastChange(Date salaryLastChange) {
		this.salaryLastChange = salaryLastChange;
	}
}
