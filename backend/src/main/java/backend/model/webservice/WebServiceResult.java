package backend.model.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlSeeAlso;

import backend.model.BusinessPartnerArray;
import backend.model.DepartmentArray;
import backend.model.EmployeeArray;
import backend.model.MaterialArray;
import backend.model.PurchaseOrderArray;
import backend.model.SalesOrderArray;

/**
 * The result of a WebService call.
 * A result consists of n messages and data.
 * 
 * @author Michael
 *
 */
@XmlSeeAlso({EmployeeArray.class, DepartmentArray.class, MaterialArray.class, BusinessPartnerArray.class, SalesOrderArray.class, PurchaseOrderArray.class})
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
	
	@XmlElementWrapper(name="messages")
	@XmlElement(name="message")
	public List<WebServiceMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<WebServiceMessage> messages) {
		this.messages = messages;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
