
package frontend.generated.ws.soap.employee;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für salesOrderArray complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="salesOrderArray">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="salesOrders" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="salesOrder" type="{http://soap.webservice.backend/}salesOrder" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "salesOrderArray", propOrder = {
    "salesOrders"
})
public class SalesOrderArray {

    protected SalesOrderArray.SalesOrders salesOrders;

    /**
     * Ruft den Wert der salesOrders-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link SalesOrderArray.SalesOrders }
     *     
     */
    public SalesOrderArray.SalesOrders getSalesOrders() {
        return salesOrders;
    }

    /**
     * Legt den Wert der salesOrders-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesOrderArray.SalesOrders }
     *     
     */
    public void setSalesOrders(SalesOrderArray.SalesOrders value) {
        this.salesOrders = value;
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
     *         &lt;element name="salesOrder" type="{http://soap.webservice.backend/}salesOrder" maxOccurs="unbounded" minOccurs="0"/>
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
        "salesOrder"
    })
    public static class SalesOrders {

        protected List<SalesOrder> salesOrder;

        /**
         * Gets the value of the salesOrder property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the salesOrder property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSalesOrder().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SalesOrder }
         * 
         * 
         */
        public List<SalesOrder> getSalesOrder() {
            if (salesOrder == null) {
                salesOrder = new ArrayList<SalesOrder>();
            }
            return this.salesOrder;
        }

    }

}
