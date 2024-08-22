package backend.model.purchaseOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A lean version of a purchase order that is used by the WebService to transfer object data. The main difference to the
 * regular PurchaseOrder is that IDs are used instead of object references.
 *
 * @author Michael
 */
public class PurchaseOrderWS {
    /**
     * The ID of the purchase order.
     */
    private Integer purchaseOrderId;

    /**
     * The ID of the vendor.
     */
    private Integer vendorId;

    /**
     * The ID of the payment account.
     */
    private Integer paymentAccountId;

    /**
     * The order date.
     */
    private Date orderDate;

    /**
     * The requested date for order delivery.
     */
    private Date requestedDeliveryDate;

    /**
     * The set of active status of the purchase order.
     */
    private Set<PurchaseOrderStatus> status;

    /**
     * The items that are being ordered.
     */
    private List<PurchaseOrderItemWS> items;

    /**
     * Constructor.
     */
    public PurchaseOrderWS() {
        this.items = new ArrayList<PurchaseOrderItemWS>();
        this.status = new HashSet<PurchaseOrderStatus>();
    }

    /**
     * Adds the given item to the order.
     *
     * @param purchaseOrderItem The item to be added.
     */
    public void addItem(final PurchaseOrderItemWS purchaseOrderItem) {
        this.items.add(purchaseOrderItem);
    }

    /**
     * @return the purchaseOrderId
     */
    public Integer getPurchaseOrderId() {
        return purchaseOrderId;
    }

    /**
     * @param purchaseOrderId the purchaseOrderId to set
     */
    public void setPurchaseOrderId(final Integer purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    /**
     * @return the vendorId
     */
    public Integer getVendorId() {
        return vendorId;
    }

    /**
     * @param vendorId the vendorId to set
     */
    public void setVendorId(final Integer vendorId) {
        this.vendorId = vendorId;
    }

    /**
     * @return the paymentAccountId
     */
    public Integer getPaymentAccountId() {
        return paymentAccountId;
    }

    /**
     * @param paymentAccountId the paymentAccountId to set
     */
    public void setPaymentAccountId(final Integer paymentAccountId) {
        this.paymentAccountId = paymentAccountId;
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
    public void setOrderDate(final Date orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * @return the requestedDeliveryDate
     */
    public Date getRequestedDeliveryDate() {
        return requestedDeliveryDate;
    }

    /**
     * @param requestedDeliveryDate the requestedDeliveryDate to set
     */
    public void setRequestedDeliveryDate(final Date requestedDeliveryDate) {
        this.requestedDeliveryDate = requestedDeliveryDate;
    }

    /**
     * @return the status
     */
    public Set<PurchaseOrderStatus> getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(final Set<PurchaseOrderStatus> status) {
        this.status = status;
    }

    /**
     * @return the items
     */
    public List<PurchaseOrderItemWS> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(final List<PurchaseOrderItemWS> items) {
        this.items = items;
    }
}
