package frontend.model;

/**
 * Gender of a person.
 * 
 * @author Michael
 */
public enum Gender {
	/**
	 * Male
	 */
	MALE,
	/**
	 * Female
	 */
	FEMALE;
	
	/**
	 * Converts the gender to a employee WebService compliant representation.
	 * 
	 * @param gender The gender of the frontend data model.
	 * @return The gender of the employee WebService data model.
	 */
	public static frontend.generated.ws.soap.employee.Gender convertoToEmployeeWebServiceGender(final Gender gender) {
		if(gender == Gender.MALE)
			return frontend.generated.ws.soap.employee.Gender.MALE;
		else
			return frontend.generated.ws.soap.employee.Gender.FEMALE;
	}
	
	
	/**
	 * Converts the gender to a department WebService compliant representation.
	 * 
	 * @param gender The gender of the frontend data model.
	 * @return The gender of the department WebService data model.
	 */
	public static frontend.generated.ws.soap.department.Gender convertoToDepartmentWebServiceGender(final Gender gender) {
		if(gender == Gender.MALE)
			return frontend.generated.ws.soap.department.Gender.MALE;
		else
			return frontend.generated.ws.soap.department.Gender.FEMALE;
	}
	
	
	/**
	 * Converts the gender provided by the employee WebService to a frontend compliant representation.
	 * 
	 * @param gender The gender of the employee WebService data model.
	 * @return The gender of the frontend data model.
	 */
	public static Gender convertToGender(final frontend.generated.ws.soap.employee.Gender gender) {
		if(gender == frontend.generated.ws.soap.employee.Gender.MALE)
			return Gender.MALE;
		else
			return Gender.FEMALE;
	}
	
	
	/**
	 * Converts the gender provided by the department WebService to a frontend compliant representation.
	 * 
	 * @param gender The gender of the department WebService data model.
	 * @return The gender of the frontend data model.
	 */
	public static Gender convertToGender(final frontend.generated.ws.soap.department.Gender gender) {
		if(gender == frontend.generated.ws.soap.department.Gender.MALE)
			return Gender.MALE;
		else
			return Gender.FEMALE;
	}
}
