package backend.model.purchaseOrder;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;

/**
 * A list of purchase orders.
 *
 * @author Michael
 */
public class PurchaseOrderArray {
    /**
     * A list of purchase orders.
     */
    private List<PurchaseOrder> purchaseOrders = null;

    /**
     * @return the purchaseOrders
     */
    @XmlElementWrapper(name = "purchaseOrders")
    @XmlElement(name = "purchaseOrder")
    public List<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }

    /**
     * @param purchaseOrders the purchaseOrders to set
     */
    public void setPurchaseOrders(final List<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }
}
