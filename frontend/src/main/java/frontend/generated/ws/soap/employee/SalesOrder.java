
package frontend.generated.ws.soap.employee;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java-Klasse f�r salesOrder complex type.
 *
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 *
 * <pre>
 * &lt;complexType name="salesOrder">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="billToParty" type="{http://soap.webservice.backend/}businessPartner" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="items" type="{http://soap.webservice.backend/}salesOrderItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="orderDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="paymentAccount" type="{http://soap.webservice.backend/}account" minOccurs="0"/>
 *         &lt;element name="requestedDeliveryDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="shipToParty" type="{http://soap.webservice.backend/}businessPartner" minOccurs="0"/>
 *         &lt;element name="soldToParty" type="{http://soap.webservice.backend/}businessPartner" minOccurs="0"/>
 *         &lt;element name="status" type="{http://soap.webservice.backend/}salesOrderStatus" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "salesOrder", propOrder = {
    "billToParty",
    "id",
    "items",
    "orderDate",
    "paymentAccount",
    "requestedDeliveryDate",
    "shipToParty",
    "soldToParty",
    "status"
})
public class SalesOrder {

    protected BusinessPartner billToParty;
    protected Integer id;
    @XmlElement(nillable = true)
    protected List<SalesOrderItem> items;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar orderDate;
    protected Account paymentAccount;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar requestedDeliveryDate;
    protected BusinessPartner shipToParty;
    protected BusinessPartner soldToParty;
    @XmlSchemaType(name = "string")
    protected SalesOrderStatus status;

    /**
     * Ruft den Wert der billToParty-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link BusinessPartner }
     *
     */
    public BusinessPartner getBillToParty() {
        return billToParty;
    }

    /**
     * Legt den Wert der billToParty-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link BusinessPartner }
     *
     */
    public void setBillToParty(BusinessPartner value) {
        this.billToParty = value;
    }

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
     * {@link SalesOrderItem }
     *
     *
     */
    public List<SalesOrderItem> getItems() {
        if (items == null) {
            items = new ArrayList<SalesOrderItem>();
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
     * Ruft den Wert der shipToParty-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link BusinessPartner }
     *
     */
    public BusinessPartner getShipToParty() {
        return shipToParty;
    }

    /**
     * Legt den Wert der shipToParty-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link BusinessPartner }
     *
     */
    public void setShipToParty(BusinessPartner value) {
        this.shipToParty = value;
    }

    /**
     * Ruft den Wert der soldToParty-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link BusinessPartner }
     *
     */
    public BusinessPartner getSoldToParty() {
        return soldToParty;
    }

    /**
     * Legt den Wert der soldToParty-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link BusinessPartner }
     *
     */
    public void setSoldToParty(BusinessPartner value) {
        this.soldToParty = value;
    }

    /**
     * Ruft den Wert der status-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link SalesOrderStatus }
     *
     */
    public SalesOrderStatus getStatus() {
        return status;
    }

    /**
     * Legt den Wert der status-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link SalesOrderStatus }
     *
     */
    public void setStatus(SalesOrderStatus value) {
        this.status = value;
    }

}
