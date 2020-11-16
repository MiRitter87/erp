
package frontend.generated.ws.soap.department;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für departmentArray complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="departmentArray">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="departments" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="department" type="{http://soap.webservice.backend/}department" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "departmentArray", propOrder = {
    "departments"
})
public class DepartmentArray {

    protected DepartmentArray.Departments departments;

    /**
     * Ruft den Wert der departments-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link DepartmentArray.Departments }
     *     
     */
    public DepartmentArray.Departments getDepartments() {
        return departments;
    }

    /**
     * Legt den Wert der departments-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link DepartmentArray.Departments }
     *     
     */
    public void setDepartments(DepartmentArray.Departments value) {
        this.departments = value;
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
     *         &lt;element name="department" type="{http://soap.webservice.backend/}department" maxOccurs="unbounded" minOccurs="0"/>
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
        "department"
    })
    public static class Departments {

        protected List<Department> department;

        /**
         * Gets the value of the department property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the department property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDepartment().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Department }
         * 
         * 
         */
        public List<Department> getDepartment() {
            if (department == null) {
                department = new ArrayList<Department>();
            }
            return this.department;
        }

    }

}
