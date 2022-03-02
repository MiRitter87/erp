
package frontend.generated.ws.soap.employee;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java-Klasse für purchaseOrder complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="purchaseOrder">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="items" type="{http://soap.webservice.backend/}purchaseOrderItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="orderDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="paymentAccount" type="{http://soap.webservice.backend/}account" minOccurs="0"/>
 *         &lt;element name="requestedDeliveryDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="status" type="{http://soap.webservice.backend/}purchaseOrderStatus" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="vendor" type="{http://soap.webservice.backend/}businessPartner" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "purchaseOrder", propOrder = {
    "id",
    "items",
    "orderDate",
    "paymentAccount",
    "requestedDeliveryDate",
    "status",
    "vendor"
})
public class PurchaseOrder {

    protected Integer id;
    @XmlElement(nillable = true)
    protected List<PurchaseOrderItem> items;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar orderDate;
    protected Account paymentAccount;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar requestedDeliveryDate;
    @XmlElement(nillable = true)
    @XmlSchemaType(name = "string")
    protected List<PurchaseOrderStatus> status;
    protected BusinessPartner vendor;

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
     * Gets the value of the items property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the items property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItems().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PurchaseOrderItem }
     * 
     * 
     */
    public List<PurchaseOrderItem> getItems() {
        if (items == null) {
            items = new ArrayList<PurchaseOrderItem>();
        }
        return this.items;
    }

    /**
     * Ruft den Wert der orderDate-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOrderDate() {
        return orderDate;
    }

    /**
     * Legt den Wert der orderDate-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOrderDate(XMLGregorianCalendar value) {
        this.orderDate = value;
    }

    /**
     * Ruft den Wert der paymentAccount-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Account }
     *     
     */
    public Account getPaymentAccount() {
        return paymentAccount;
    }

    /**
     * Legt den Wert der paymentAccount-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Account }
     *     
     */
    public void setPaymentAccount(Account value) {
        this.paymentAccount = value;
    }

    /**
     * Ruft den Wert der requestedDeliveryDate-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRequestedDeliveryDate() {
        return requestedDeliveryDate;
    }

    /**
     * Legt den Wert der requestedDeliveryDate-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRequestedDeliveryDate(XMLGregorianCalendar value) {
        this.requestedDeliveryDate = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the status property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStatus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PurchaseOrderStatus }
     * 
     * 
     */
    public List<PurchaseOrderStatus> getStatus() {
        if (status == null) {
            status = new ArrayList<PurchaseOrderStatus>();
        }
        return this.status;
    }

    /**
     * Ruft den Wert der vendor-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BusinessPartner }
     *     
     */
    public BusinessPartner getVendor() {
        return vendor;
    }

    /**
     * Legt den Wert der vendor-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessPartner }
     *     
     */
    public void setVendor(BusinessPartner value) {
        this.vendor = value;
    }

}
