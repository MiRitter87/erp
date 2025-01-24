package backend.model.productionOrder;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * A list of production orders.
 *
 * @author Michael
 */
@XmlRootElement(name = "productionOrders")
public class ProductionOrderArray {
    /**
     * A list of production orders.
     */
    private List<ProductionOrder> productionOrders = null;

    /**
     * @return the productionOrders
     */
    @XmlElementWrapper(name = "productionOrders")
    @XmlElement(name = "productionOrder")
    public List<ProductionOrder> getProductionOrders() {
        return productionOrders;
    }

    /**
     * @param productionOrders the productionOrders to set
     */
    public void setProductionOrders(final List<ProductionOrder> productionOrders) {
        this.productionOrders = productionOrders;
    }
}
