package backend.model.productionOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Represents an order to produce a certain amount of materials.
 * 
 * @author Michael
 */
@Table(name="PRODUCTION_ORDER")
@Entity
@SequenceGenerator(name = "productionOrderSequence", initialValue = 1, allocationSize = 1)
public class ProductionOrder {
	/**
	 * The ID.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productionOrderSequence")
	@Column(name="PRODUCTION_ORDER_ID")
	private Integer id;
	
	/**
	 * The order date.
	 */
	@Column(name="ORDER_DATE")
	private Date orderDate;
	
	/**
	 * The planned execution date.
	 */
	@Column(name="PLANNED_EXECUTION_DATE")
	private Date plannedExecutionDate;
	
	/**
	 * The actual execution date.
	 */
	@Column(name="EXECUTION_DATE")
	private Date executionDate;
	
	/**
	 * The status of the production order.
	 */
	@Column(name="STATUS", length = 10)
	@Enumerated(EnumType.STRING)
	private ProductionOrderStatus status;
	
	/**
	 * The items that are being produced.
	 */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "productionOrder")
	private List<ProductionOrderItem> items;
	
	
	/**
	 * Default constructor.
	 */
	public ProductionOrder() {
		this.orderDate = new Date();
		this.items = new ArrayList<ProductionOrderItem>();
	}
	
	
	/**
	 * Adds a production order item to the production order.
	 * 
	 * @param item The production order item.
	 */
	public void addItem(final ProductionOrderItem item) {
		item.setProductionOrder(this);
		this.items.add(item);
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


	/**
	 * @return the items
	 */
	public List<ProductionOrderItem> getItems() {
		return items;
	}


	/**
	 * @param items the items to set
	 */
	public void setItems(List<ProductionOrderItem> items) {
		this.items = items;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((executionDate == null) ? 0 : executionDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		result = prime * result + ((orderDate == null) ? 0 : orderDate.hashCode());
		result = prime * result + ((plannedExecutionDate == null) ? 0 : plannedExecutionDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ProductionOrder other = (ProductionOrder) obj;
		if (executionDate == null) {
			if (other.executionDate != null) {
				return false;
			}
		} else if (executionDate.getTime() != other.executionDate.getTime()) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (orderDate == null) {
			if (other.orderDate != null) {
				return false;
			}
		} else if (orderDate.getTime() != other.orderDate.getTime()) {
			return false;
		}
		if (plannedExecutionDate == null) {
			if (other.plannedExecutionDate != null) {
				return false;
			}
		} else if (plannedExecutionDate.getTime() != other.plannedExecutionDate.getTime()) {
			return false;
		}
		if (status != other.status) {
			return false;
		}
		
		if(this.areItemsEqual(other) == false)
			return false;
		
		return true;
	}
	
	
	/**
	 * Checks if the list of items is equal.
	 * 
	 * @param other The other production order for comparison.
	 * @return true, if items are equal; false otherwise.
	 */
	private boolean areItemsEqual(ProductionOrder other) {
		if (this.items == null && other.items != null)
			return false;
		
		if (this.items != null && other.items == null)
			return false;
		
		if(this.items.size() != other.items.size())
			return false;
		
		for(ProductionOrderItem tempItem:this.items) {
			ProductionOrderItem otherItem = other.getItemWithId(tempItem.getId());
			
			if(otherItem == null)
				return false;
			
			if(!tempItem.equals(otherItem))
				return false;
		}
		
		return true;
	}
	
	
	/**
	 * Gets the item with the given id.
	 * 
	 * @param id The id of the item.
	 * @return The item with the given id, if found.
	 */
	public ProductionOrderItem getItemWithId(Integer id) {
		for(ProductionOrderItem tempItem:this.items) {
			if(tempItem.getId() == id)
				return tempItem;
		}
		
		return null;
	}
}
