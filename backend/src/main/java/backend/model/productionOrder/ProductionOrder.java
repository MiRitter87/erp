package backend.model.productionOrder;

import java.util.Date;

/**
 * Represents an order to produce a certain amount of materials.
 * 
 * @author Michael
 */
public class ProductionOrder {
	/**
	 * The ID.
	 */
	private Integer id;
	
	/**
	 * The order date.
	 */
	private Date orderDate;
	
	/**
	 * The planned execution date.
	 */
	private Date plannedExecutionDate;
	
	/**
	 * The actual executionn date.
	 */
	private Date executionDate;
	
	/**
	 * The status of the production order.
	 */
	private ProductionOrderStatus status;
	
	
	/**
	 * Default constructor.
	 */
	public ProductionOrder() {
		this.orderDate = new Date();
	}


	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}


	/**
	 * @return the orderDate
	 */
	public Date getOrderDate() {
		return orderDate;
	}


	/**
	 * @param orderDate the orderDate to set
	 */
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}


	/**
	 * @return the plannedExecutionDate
	 */
	public Date getPlannedExecutionDate() {
		return plannedExecutionDate;
	}


	/**
	 * @param plannedExecutionDate the plannedExecutionDate to set
	 */
	public void setPlannedExecutionDate(Date plannedExecutionDate) {
		this.plannedExecutionDate = plannedExecutionDate;
	}


	/**
	 * @return the executionDate
	 */
	public Date getExecutionDate() {
		return executionDate;
	}


	/**
	 * @param executionDate the executionDate to set
	 */
	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
	}


	/**
	 * @return the status
	 */
	public ProductionOrderStatus getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(ProductionOrderStatus status) {
		this.status = status;
	}
}
