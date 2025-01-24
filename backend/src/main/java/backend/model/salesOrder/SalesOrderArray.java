package backend.model.salesOrder;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * A list of sales orders.
 *
 * @author Michael
 */
@XmlRootElement(name = "salesOrders")
public class SalesOrderArray {
    /**
     * A list of sales orders.
     */
    private List<SalesOrder> salesOrders = null;

    /**
     * @return the salesOrders
     */
    @XmlElementWrapper(name = "salesOrders")
    @XmlElement(name = "salesOrder")
    public List<SalesOrder> getSalesOrders() {
        return salesOrders;
    }

    /**
     * @param salesOrders the salesOrders to set
     */
    public void setSalesOrders(final List<SalesOrder> salesOrders) {
        this.salesOrders = salesOrders;
    }
}
