
package frontend.generated.ws.soap.employee;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für businessPartnerArray complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="businessPartnerArray">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="businessPartners" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="businessPartner" type="{http://soap.webservice.backend/}businessPartner" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "businessPartnerArray", propOrder = {
    "businessPartners"
})
public class BusinessPartnerArray {

    protected BusinessPartnerArray.BusinessPartners businessPartners;

    /**
     * Ruft den Wert der businessPartners-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BusinessPartnerArray.BusinessPartners }
     *     
     */
    public BusinessPartnerArray.BusinessPartners getBusinessPartners() {
        return businessPartners;
    }

    /**
     * Legt den Wert der businessPartners-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessPartnerArray.BusinessPartners }
     *     
     */
    public void setBusinessPartners(BusinessPartnerArray.BusinessPartners value) {
        this.businessPartners = value;
    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="businessPartner" type="{http://soap.webservice.backend/}businessPartner" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "businessPartner"
    })
    public static class BusinessPartners {

        protected List<BusinessPartner> businessPartner;

        /**
         * Gets the value of the businessPartner property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the businessPartner property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBusinessPartner().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link BusinessPartner }
         * 
         * 
         */
        public List<BusinessPartner> getBusinessPartner() {
            if (businessPartner == null) {
                businessPartner = new ArrayList<BusinessPartner>();
            }
            return this.businessPartner;
        }

    }

}
