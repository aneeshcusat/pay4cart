package com.simpleworks.store.datatransferobject;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * The Class OrderItem.
 */
@Entity
@Table(name = "order_info", uniqueConstraints = {@UniqueConstraint(columnNames = {"order_id"})})
public class OrderItem implements SimpleWorksBaseItem
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5628656638213113049L;

    /** The order id. */
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private int orderId;

    /** The user id. */
    @Column(name = "user_id")
    private int userId;
    
    /** The status. */
    @Column(name = "status")
    private String status;
    
    /** The sub total. */
    @Column(name = "sub_total")
    private Double subTotal;

    /** The sku items. */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderItem", cascade = CascadeType.ALL)
    private Set<SkuItem> skuItems;
    
    /** The address item. */
    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = false)
    @JoinColumn(name = "address_id")
    private AddressItem addressItem;
    
    /** The payment item. */
    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = false)
    @JoinColumn(name = "payment_id")
    private PaymentItem paymentItem;
    
    /** The created date. */
    @Column(name = "created_date")
    private Timestamp createdDate;

    /** The last modified date. */
    @Column(name = "last_modified_date")
    private Timestamp lastModifiedDate;

	/**
	 * Gets the order id.
	 *
	 * @return the order id
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * Sets the order id.
	 *
	 * @param orderId the new order id
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Gets the sku items.
	 *
	 * @return the sku items
	 */
	public Set<SkuItem> getSkuItems() {
		return skuItems;
	}

	/**
	 * Sets the sku items.
	 *
	 * @param skuItems the new sku items
	 */
	public void setSkuItems(Set<SkuItem> skuItems) {
		this.skuItems = skuItems;
	}

	/**
	 * Gets the address item.
	 *
	 * @return the address item
	 */
	public AddressItem getAddressItem() {
		return addressItem;
	}

	/**
	 * Sets the address item.
	 *
	 * @param addressItem the new address item
	 */
	public void setAddressItem(AddressItem addressItem) {
		this.addressItem = addressItem;
	}

	/**
	 * Gets the payment item.
	 *
	 * @return the payment item
	 */
	public PaymentItem getPaymentItem() {
		return paymentItem;
	}

	/**
	 * Sets the payment item.
	 *
	 * @param paymentItem the new payment item
	 */
	public void setPaymentItem(PaymentItem paymentItem) {
		this.paymentItem = paymentItem;
	}

	/* (non-Javadoc)
	 * @see com.simpleworks.store.datatransferobject.SimpleWorksBaseItem#getCreatedDate()
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	/* (non-Javadoc)
	 * @see com.simpleworks.store.datatransferobject.SimpleWorksBaseItem#setCreatedDate(java.sql.Timestamp)
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Gets the last modified date.
	 *
	 * @return the last modified date
	 */
	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}

	/* (non-Javadoc)
	 * @see com.simpleworks.store.datatransferobject.SimpleWorksBaseItem#setLastModifiedDate(java.sql.Timestamp)
	 */
	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the sub total.
	 *
	 * @return the sub total
	 */
	public Double getSubTotal() {
		return subTotal;
	}

	/**
	 * Sets the sub total.
	 *
	 * @param subTotal the new sub total
	 */
	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}


}