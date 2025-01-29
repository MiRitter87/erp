
package frontend.generated.ws.soap.employee;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse f�r updateEmployee complex type.
 *
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 *
 * <pre>
 * &lt;complexType name="updateEmployee">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="employee" type="{http://soap.webservice.backend/}employee" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateEmployee", propOrder = {
    "employee"
})
public class UpdateEmployee {

    protected Employee employee;

    /**
     * Ruft den Wert der employee-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link Employee }
     *
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Legt den Wert der employee-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link Employee }
     *
     */
    public void setEmployee(Employee value) {
        this.employee = value;
    }

}
