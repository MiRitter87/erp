
package frontend.generated.ws.soap.employee;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für getEmployeesResponse complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="getEmployeesResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://soap.webservice.backend/}webServiceResult" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getEmployeesResponse", propOrder = {
    "_return"
})
public class GetEmployeesResponse {

    @XmlElement(name = "return")
    protected WebServiceResult _return;

    /**
     * Ruft den Wert der return-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link WebServiceResult }
     *     
     */
    public WebServiceResult getReturn() {
        return _return;
    }

    /**
     * Legt den Wert der return-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link WebServiceResult }
     *     
     */
    public void setReturn(WebServiceResult value) {
        this._return = value;
    }

}
