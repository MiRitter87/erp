
package frontend.generated.ws.soap.employee;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für getEmployees complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="getEmployees">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="employeeHeadQuery" type="{http://soap.webservice.backend/}employeeHeadQueryParameter" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getEmployees", propOrder = {
    "employeeHeadQuery"
})
public class GetEmployees {

    @XmlSchemaType(name = "string")
    protected EmployeeHeadQueryParameter employeeHeadQuery;

    /**
     * Ruft den Wert der employeeHeadQuery-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeHeadQueryParameter }
     *     
     */
    public EmployeeHeadQueryParameter getEmployeeHeadQuery() {
        return employeeHeadQuery;
    }

    /**
     * Legt den Wert der employeeHeadQuery-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeHeadQueryParameter }
     *     
     */
    public void setEmployeeHeadQuery(EmployeeHeadQueryParameter value) {
        this.employeeHeadQuery = value;
    }

}
