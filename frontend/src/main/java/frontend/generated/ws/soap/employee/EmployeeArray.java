
package frontend.generated.ws.soap.employee;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für employeeArray complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="employeeArray">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="employees" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="employee" type="{http://soap.webservice.backend/}employee" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "employeeArray", propOrder = {
    "employees"
})
public class EmployeeArray {

    protected EmployeeArray.Employees employees;

    /**
     * Ruft den Wert der employees-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeArray.Employees }
     *     
     */
    public EmployeeArray.Employees getEmployees() {
        return employees;
    }

    /**
     * Legt den Wert der employees-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeArray.Employees }
     *     
     */
    public void setEmployees(EmployeeArray.Employees value) {
        this.employees = value;
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
     *         &lt;element name="employee" type="{http://soap.webservice.backend/}employee" maxOccurs="unbounded" minOccurs="0"/>
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
        "employee"
    })
    public static class Employees {

        protected List<Employee> employee;

        /**
         * Gets the value of the employee property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the employee property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEmployee().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Employee }
         * 
         * 
         */
        public List<Employee> getEmployee() {
            if (employee == null) {
                employee = new ArrayList<Employee>();
            }
            return this.employee;
        }

    }

}
