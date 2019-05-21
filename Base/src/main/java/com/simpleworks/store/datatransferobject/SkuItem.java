package com.simpleworks.store.datatransferobject;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * The Class SkuItem.
 */
@Entity
@Table(name = "sku_info", uniqueConstraints = {@UniqueConstraint(columnNames = {"sku_id"})})
public class SkuItem implements SimpleWorksBaseItem
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5628656638213113049L;

    /** The sku id. */
    @Id
    @Column(name = "sku_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private int skuId;

    /** The order item. */
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderItem orderItem;
    
    /** The item number. */
    @Column(name = "item_number")
    private String itemNumber;
    
    /** The item name. */
    @Column(name = "item_name")
    private String itemName;
    
    /** The amount. */
    @Column(name = "amount")
    private Double amount;
    
    /** The discount amount. */
    @Column(name = "discount_amount")
    private Double discountAmount;
    
    /** The quantity. */
    @Column(name = "quantity")
    private Integer quantity;
    
    /** The created date. */
    @Column(name = "created_date")
    private Timestamp createdDate;

    /** The last modified date. */
    @Column(name = "last_modified_date")
    private Timestamp lastModifiedDate;

	/**
	 * Gets the sku id.
	 *
	 * @return the sku id
	 */
	public int getSkuId() {
		return skuId;
	}

	/**
	 * Sets the sku id.
	 *
	 * @param skuId the new sku id
	 */
	public void setSkuId(int skuId) {
		this.skuId = skuId;
	}

	/**
	 * Gets the order item.
	 *
	 * @return the order item
	 */
	public OrderItem getOrderItem() {
		return orderItem;
	}

	/**
	 * Sets the order item.
	 *
	 * @param orderItem the new order item
	 */
	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	/**
	 * Gets the item number.
	 *
	 * @return the item number
	 */
	public String getItemNumber() {
		return itemNumber;
	}

	/**
	 * Sets the item number.
	 *
	 * @param itemNumber the new item number
	 */
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	/**
	 * Gets the item name.
	 *
	 * @return the item name
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * Sets the item name.
	 *
	 * @param itemName the new item name
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * Gets the discount amount.
	 *
	 * @return the discount amount
	 */
	public Double getDiscountAmount() {
		return discountAmount;
	}

	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	public Double getTotal() {
		return (amount * quantity)- discountAmount;
	}
	
	/**
	 * Sets the discount amount.
	 *
	 * @param discountAmount the new discount amount
	 */
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * Sets the quantity.
	 *
	 * @param quantity the new quantity
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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

}