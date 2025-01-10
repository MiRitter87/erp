package backend.model.businessPartner;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;

/**
 * A business partner.
 *
 * @author Michael
 */
@Table(name = "BUSINESS_PARTNER")
@Entity
@SequenceGenerator(name = "bpSequence", initialValue = 1, allocationSize = 1)
public class BusinessPartner {
    /**
     * The maximum company name field length allowed.
     */
    private static final int MAX_COMPANY_NAME_LENGTH = 100;

    /**
     * The maximum first name field length allowed.
     */
    private static final int MAX_FIRST_NAME_LENGTH = 50;

    /**
     * The maximum last name field length allowed.
     */
    private static final int MAX_LAST_NAME_LENGTH = 50;

    /**
     * The maximum street name field length allowed.
     */
    private static final int MAX_STREET_NAME_LENGTH = 100;

    /**
     * The maximum house number field length allowed.
     */
    private static final int MAX_HOUSE_NUMBER_LENGTH = 6;

    /**
     * The maximum zip code field length allowed.
     */
    private static final int MAX_ZIP_CODE_LENGTH = 5;

    /**
     * The maximum city name field length allowed.
     */
    private static final int MAX_CITY_NAME_LENGTH = 100;

    /**
     * The maximum phone number field length allowed.
     */
    private static final int MAX_PHONE_NUMBER_LENGTH = 40;

    /**
     * The maximum business partner type field length allowed.
     */
    private static final int MAX_BP_TYPE_LENGTH = 20;

    /**
     * The ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bpSequence")
    @Column(name = "BUSINESS_PARTNER_ID")
    @Min(value = 1, message = "{businessPartner.id.min.message}")
    private Integer id;

    /**
     * The name of the company.
     */
    @Column(name = "COMPANY_NAME", length = MAX_COMPANY_NAME_LENGTH)
    @Size(min = 1, max = MAX_COMPANY_NAME_LENGTH, message = "{businessPartner.companyName.size.message}")
    @NotNull(message = "{businessPartner.companyName.notNull.message}")
    private String companyName;

    /**
     * The first name of the contact person.
     */
    @Column(name = "FIRST_NAME", length = MAX_FIRST_NAME_LENGTH)
    @Size(min = 0, max = MAX_FIRST_NAME_LENGTH, message = "{businessPartner.firstName.size.message}")
    private String firstName;

    /**
     * The last name of the contact person.
     */
    @Column(name = "LAST_NAME", length = MAX_LAST_NAME_LENGTH)
    @Size(min = 0, max = MAX_LAST_NAME_LENGTH, message = "{businessPartner.lastName.size.message}")
    private String lastName;

    /**
     * The name of the street where the company is located.
     */
    @Column(name = "STREET_NAME", length = MAX_STREET_NAME_LENGTH)
    @Size(min = 1, max = MAX_STREET_NAME_LENGTH, message = "{businessPartner.streetName.size.message}")
    @NotNull(message = "{businessPartner.streetName.notNull.message}")
    private String streetName;

    /**
     * The number of the building where the company is located.
     */
    @Column(name = "HOUSE_NUMBER", length = MAX_HOUSE_NUMBER_LENGTH)
    @Size(min = 1, max = MAX_HOUSE_NUMBER_LENGTH, message = "{businessPartner.houseNumber.size.message}")
    @NotNull(message = "{businessPartner.houseNumber.notNull.message}")
    private String houseNumber;

    /**
     * The ZIP code of the company.
     */
    @Column(name = "ZIP_CODE", length = MAX_ZIP_CODE_LENGTH)
    @Size(min = 1, max = MAX_ZIP_CODE_LENGTH, message = "{businessPartner.zipCode.size.message}")
    @NotNull(message = "{businessPartner.zipCode.notNull.message}")
    private String zipCode;

    /**
     * The name of the city where the company is located.
     */
    @Column(name = "CITY_NAME", length = MAX_CITY_NAME_LENGTH)
    @Size(min = 1, max = MAX_CITY_NAME_LENGTH, message = "{businessPartner.cityName.size.message}")
    @NotNull(message = "{businessPartner.cityName.notNull.message}")
    private String cityName;

    /**
     * The phone number of the company.
     */
    @Column(name = "PHONE_NUMBER", length = MAX_PHONE_NUMBER_LENGTH)
    @Size(min = 1, max = MAX_PHONE_NUMBER_LENGTH, message = "{businessPartner.phoneNumber.size.message}")
    @NotNull(message = "{businessPartner.phoneNumber.notNull.message}")
    private String phoneNumber;

    /**
     * The type of the business partner.
     */
    @CollectionTable(name = "BUSINESS_PARTNER_TYPES", joinColumns = { @JoinColumn(name = "BUSINESS_PARTNER_ID") })
    @Column(name = "type", nullable = false, length = MAX_BP_TYPE_LENGTH)
    @ElementCollection(targetClass = BusinessPartnerType.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @NotEmpty(message = "{businessPartner.types.notEmpty.message}")
    private Set<BusinessPartnerType> types;

    /**
     * Default constructor.
     */
    public BusinessPartner() {
        this.types = new HashSet<BusinessPartnerType>();
    }

    /**
     * Adds the given type to the set of types.
     *
     * @param type The type to be added.
     */
    public void addType(final BusinessPartnerType type) {
        this.types.add(type);
    }

    /**
     * Calculates the hashCode of a BusinessPartner.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cityName == null) ? 0 : cityName.hashCode());
        result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((houseNumber == null) ? 0 : houseNumber.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
        result = prime * result + ((streetName == null) ? 0 : streetName.hashCode());
        result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
        result = prime * result + ((types == null) ? 0 : types.hashCode());
        return result;
    }

    /**
     * Indicates whether some other BusinessPartner is "equal to" this one.
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BusinessPartner other = (BusinessPartner) obj;
        if (!this.areBasicAttributesEqual(other)) {
            return false;
        }
        if (!this.isAddressEqual(other)) {
            return false;
        }
        return true;
    }

    /**
     * Indicates whether the basic attributes of some other BusinessPartner is "equal to" this one.
     *
     * @param other The other BusinessPartner.
     * @return true if basic attributes are equal.
     */
    private boolean areBasicAttributesEqual(final BusinessPartner other) {
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (companyName == null) {
            if (other.companyName != null) {
                return false;
            }
        } else if (!companyName.equals(other.companyName)) {
            return false;
        }
        if (firstName == null) {
            if (other.firstName != null) {
                return false;
            }
        } else if (!firstName.equals(other.firstName)) {
            return false;
        }
        if (lastName == null) {
            if (other.lastName != null) {
                return false;
            }
        } else if (!lastName.equals(other.lastName)) {
            return false;
        }
        if (phoneNumber == null) {
            if (other.phoneNumber != null) {
                return false;
            }
        } else if (!phoneNumber.equals(other.phoneNumber)) {
            return false;
        }
        if (types == null) {
            if (other.types != null) {
                return false;
            }
        } else if (!types.equals(other.types)) {
            return false;
        }
        return true;
    }

    /**
     * Indicates whether the address of some other BusinessPartner is "equal to" this one.
     *
     * @param other The other BusinessPartner.
     * @return true if address is equal.
     */
    private boolean isAddressEqual(final BusinessPartner other) {
        if (streetName == null) {
            if (other.streetName != null) {
                return false;
            }
        } else if (!streetName.equals(other.streetName)) {
            return false;
        }
        if (houseNumber == null) {
            if (other.houseNumber != null) {
                return false;
            }
        } else if (!houseNumber.equals(other.houseNumber)) {
            return false;
        }
        if (zipCode == null) {
            if (other.zipCode != null) {
                return false;
            }
        } else if (!zipCode.equals(other.zipCode)) {
            return false;
        }
        if (cityName == null) {
            if (other.cityName != null) {
                return false;
            }
        } else if (!cityName.equals(other.cityName)) {
            return false;
        }
        return true;
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
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
                .constraintExpressionLanguageFeatureLevel(ExpressionLanguageFeatureLevel.BEAN_METHODS)
                .buildValidatorFactory();

        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<BusinessPartner>> violations = validator.validate(this);

        for (ConstraintViolation<BusinessPartner> violation : violations) {
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
    public void setId(final Integer id) {
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
    public void setCompanyName(final String companyName) {
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
    public void setFirstName(final String firstName) {
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
    public void setLastName(final String lastName) {
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
    public void setStreetName(final String streetName) {
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
    public void setHouseNumber(final String houseNumber) {
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
    public void setZipCode(final String zipCode) {
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
    public void setCityName(final String cityName) {
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
    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the types
     */
    public Set<BusinessPartnerType> getTypes() {
        return types;
    }

    /**
     * @param types the types to set
     */
    public void setTypes(final Set<BusinessPartnerType> types) {
        this.types = types;
    }
}
