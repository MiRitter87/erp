package backend.model.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlSeeAlso;

import backend.model.account.AccountArray;
import backend.model.billOfMaterial.BillOfMaterialArray;
import backend.model.businessPartner.BusinessPartnerArray;
import backend.model.department.DepartmentArray;
import backend.model.employee.EmployeeArray;
import backend.model.material.MaterialArray;
import backend.model.productionOrder.ProductionOrderArray;
import backend.model.purchaseOrder.PurchaseOrderArray;
import backend.model.salesOrder.SalesOrderArray;

/**
 * The result of a WebService call. A result consists of n messages and data.
 *
 * @author Michael
 *
 */
@XmlSeeAlso({ EmployeeArray.class, DepartmentArray.class, MaterialArray.class, BusinessPartnerArray.class,
        SalesOrderArray.class, PurchaseOrderArray.class, AccountArray.class, BillOfMaterialArray.class,
        ProductionOrderArray.class })
public class WebServiceResult {
    /**
     * A list of messages.
     */
    private List<WebServiceMessage> messages;

    /**
     * The result data of a WebService call.
     */
    private Object data;

    /**
     * Default Constructor.
     */
    public WebServiceResult() {
        this.messages = new ArrayList<WebServiceMessage>();
    }

    /**
     * Creates and initializes a new WebService result.
     *
     * @param data The result data to be set.
     */
    public WebServiceResult(final Object data) {
        this.data = data;
        this.messages = new ArrayList<WebServiceMessage>();
    }

    /**
     * Adds the given message to the message list of the WebService result.
     *
     * @param message The message to be added.
     */
    public void addMessage(final WebServiceMessage message) {
        this.messages.add(message);
    }

    /**
     * Adds a list of messages to the message list of the WebService result.
     *
     * @param messages A list of messages to be added.
     */
    public void addMessages(final List<WebServiceMessage> messages) {
        this.messages.addAll(messages);
    }

    /**
     * @return the messages
     */
    @XmlElementWrapper(name = "messages")
    @XmlElement(name = "message")
    public List<WebServiceMessage> getMessages() {
        return messages;
    }

    /**
     * @param messages the messages to set
     */
    public void setMessages(final List<WebServiceMessage> messages) {
        this.messages = messages;
    }

    /**
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(final Object data) {
        this.data = data;
    }
}
