
package frontend.generated.ws.soap.employee;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java-Klasse für employeeSalary complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="employeeSalary">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="monthlySalary" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="salaryLastChange" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "employeeSalary", propOrder = {
    "id",
    "monthlySalary",
    "salaryLastChange"
})
public class EmployeeSalary {

    protected Integer id;
    protected int monthlySalary;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar salaryLastChange;

    /**
     * Ruft den Wert der id-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * Legt den Wert der id-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }

    /**
     * Ruft den Wert der monthlySalary-Eigenschaft ab.
     * 
     */
    public int getMonthlySalary() {
        return monthlySalary;
    }

    /**
     * Legt den Wert der monthlySalary-Eigenschaft fest.
     * 
     */
    public void setMonthlySalary(int value) {
        this.monthlySalary = value;
    }

    /**
     * Ruft den Wert der salaryLastChange-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSalaryLastChange() {
        return salaryLastChange;
    }

    /**
     * Legt den Wert der salaryLastChange-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSalaryLastChange(XMLGregorianCalendar value) {
        this.salaryLastChange = value;
    }

}
