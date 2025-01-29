
package frontend.generated.ws.soap.department;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse f�r addDepartment complex type.
 *
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 *
 * <pre>
 * &lt;complexType name="addDepartment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="department" type="{http://soap.webservice.backend/}department" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addDepartment", propOrder = {
    "department"
})
public class AddDepartment {

    protected Department department;

    /**
     * Ruft den Wert der department-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link Department }
     *
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Legt den Wert der department-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link Department }
     *
     */
    public void setDepartment(Department value) {
        this.department = value;
    }

}
