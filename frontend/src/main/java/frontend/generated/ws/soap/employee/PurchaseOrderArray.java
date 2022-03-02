
package frontend.generated.ws.soap.employee;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für purchaseOrderArray complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="purchaseOrderArray">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="purchaseOrders" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="purchaseOrder" type="{http://soap.webservice.backend/}purchaseOrder" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "purchaseOrderArray", propOrder = {
    "purchaseOrders"
})
public class PurchaseOrderArray {

    protected PurchaseOrderArray.PurchaseOrders purchaseOrders;

    /**
     * Ruft den Wert der purchaseOrders-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseOrderArray.PurchaseOrders }
     *     
     */
    public PurchaseOrderArray.PurchaseOrders getPurchaseOrders() {
        return purchaseOrders;
    }

    /**
     * Legt den Wert der purchaseOrders-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseOrderArray.PurchaseOrders }
     *     
     */
    public void setPurchaseOrders(PurchaseOrderArray.PurchaseOrders value) {
        this.purchaseOrders = value;
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
     *         &lt;element name="purchaseOrder" type="{http://soap.webservice.backend/}purchaseOrder" maxOccurs="unbounded" minOccurs="0"/>
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
        "purchaseOrder"
    })
    public static class PurchaseOrders {

        protected List<PurchaseOrder> purchaseOrder;

        /**
         * Gets the value of the purchaseOrder property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the purchaseOrder property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPurchaseOrder().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PurchaseOrder }
         * 
         * 
         */
        public List<PurchaseOrder> getPurchaseOrder() {
            if (purchaseOrder == null) {
                purchaseOrder = new ArrayList<PurchaseOrder>();
            }
            return this.purchaseOrder;
        }

    }

}
