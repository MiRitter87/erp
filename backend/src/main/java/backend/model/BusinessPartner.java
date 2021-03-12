package backend.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A business partner.
 * 
 * @author Michael
 */
@Table(name="BUSINESS_PARTNER")
@Entity
@SequenceGenerator(name = "bpSequence", initialValue = 1, allocationSize = 1)
public class BusinessPartner {
	/**
	 * The ID.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bpSequence")
	@Column(name="BUSINESS_PARTNER_ID")
	@Min(value = 1, message = "{businessPartner.id.min.message}")
	private Integer id;
	
	/**
	 * The name of the company.
	 */
	@Column(name="COMPANY_NAME", length = 100)
	@Size(min = 1, max = 100, message = "{businessPartner.companyName.size.message}")
	@NotNull(message = "{businessPartner.companyName.notNull.message}")
	private String companyName;
	
	/**
	 * The first name of the contact person.
	 */
	@Column(name="FIRST_NAME", length = 50)
	@Size(min = 0, max = 50, message = "{businessPartner.firstName.size.message}")
	private String firstName;
	
	/**
	 * The last name of the contact person.
	 */
	@Column(name="LAST_NAME", length = 50)
	@Size(min = 0, max = 50, message = "{businessPartner.lastName.size.message}")
	private String lastName;
	
	/**
	 * The name of the street where the company is located.
	 */
	@Column(name="STREET_NAME", length = 100)
	@Size(min = 1, max = 100, message = "{businessPartner.streetName.size.message}")
	@NotNull(message = "{businessPartner.streetName.notNull.message}")
	private String streetName;
	
	/**
	 * The number of the building where the company is located.
	 */
	@Column(name="HOUSE_NUMBER", length = 6)
	@Size(min = 1, max = 6, message = "{businessPartner.houseNumber.size.message}")
	@NotNull(message = "{businessPartner.houseNumber.notNull.message}")
	private String houseNumber;
	
	/**
	 * The ZIP code of the company.
	 */
	@Column(name="ZIP_CODE", length = 5)
	@Size(min = 1, max = 5, message = "{businessPartner.zipCode.size.message}")
	@NotNull(message = "{businessPartner.zipCode.notNull.message}")
	private String zipCode;
	
	/**
	 * The name of the city where the company is located.
	 */
	@Column(name="CITY_NAME", length = 100)
	@Size(min = 1, max = 100, message = "{businessPartner.cityName.size.message}")
	@NotNull(message = "{businessPartner.cityName.notNull.message}")
	private String cityName;
	
	/**
	 * The phone number of the company.
	 */
	@Column(name="PHONE_NUMBER", length = 40)
	@Size(min = 1, max = 40, message = "{businessPartner.phoneNumber.size.message}")
	@NotNull(message = "{businessPartner.phoneNumber.notNull.message}")
	private String phoneNumber;
	
	
	/**
	 * Default constructor.
	 */
	public BusinessPartner() {
		
	}
	
	
	/**
	 * Validates the material.
	 * 
	 * @throws Exception In case a general validation error occurred.
	 */
	public void validate() throws Exception {
		this.validateAnnotations();
	}
	
	
	/**
	 * Validates the material according to the annotations of the Validation Framework.
	 * 
	 * @exception Exception In case the validation failed.
	 */
	private void validateAnnotations() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<BusinessPartner>> violations = validator.validate(this);
		
		for(ConstraintViolation<BusinessPartner> violation:violations) {
			throw new Exception(violation.getMessage());
		}
	}


	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}


	/**
	 * @return the company
	 */
	public String getCompanyName() {
		return companyName;
	}


	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}


	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	/**
	 * @return the streetName
	 */
	public String getStreetName() {
		return streetName;
	}


	/**
	 * @param streetName the streetName to set
	 */
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}


	/**
	 * @return the houseNumber
	 */
	public String getHouseNumber() {
		return houseNumber;
	}


	/**
	 * @param houseNumber the houseNumber to set
	 */
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}


	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}


	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}


	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}


	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}


	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}


	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
