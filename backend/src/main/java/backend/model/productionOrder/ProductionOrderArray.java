package backend.model.productionOrder;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A list of production orders.
 * 
 * @author Michael
 */
@XmlRootElement(name="productionOrders")
public class ProductionOrderArray {
	/**
	 * A list of production orders.
	 */
	private List<ProductionOrder> productionOrders = null;

	
	/**
	 * @return the productionOrders
	 */
	@XmlElementWrapper(name="productionOrders")
    @XmlElement(name="productionOrder")
	public List<ProductionOrder> getProductionOrders() {
		return productionOrders;
	}

	
	/**
	 * @param productionOrders the productionOrders to set
	 */
	public void setProductionOrders(List<ProductionOrder> productionOrders) {
		this.productionOrders = productionOrders;
	}
}
