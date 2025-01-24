package backend.model.businessPartner;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * A list of business partners.
 *
 * @author Michael
 */
@XmlRootElement(name = "businessPartners")
public class BusinessPartnerArray {
    /**
     * A list of business partners.
     */
    private List<BusinessPartner> businessPartners = null;

    /**
     * @return the businessPartners
     */
    @XmlElementWrapper(name = "businessPartners")
    @XmlElement(name = "businessPartner")
    public List<BusinessPartner> getBusinessPartners() {
        return businessPartners;
    }

    /**
     * @param businessPartners the businessPartners to set
     */
    public void setBusinessPartners(final List<BusinessPartner> businessPartners) {
        this.businessPartners = businessPartners;
    }
}
