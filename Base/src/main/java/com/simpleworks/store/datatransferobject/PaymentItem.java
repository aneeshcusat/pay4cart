package com.simpleworks.store.datatransferobject;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * The Class PaymentItem.
 */
@Entity
@Table(name = "payment_info", uniqueConstraints = {@UniqueConstraint(columnNames = {"payment_id"})})
public class PaymentItem implements SimpleWorksBaseItem
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5628656638213113049L;

    /** The payment id. */
    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private int paymentId;

    /** The paid by. */
    @Column(name = "user_id")
    private int paidBy;
    
    /** The amount. */
    @Column(name = "amount")
    private Double amount;
    
    /** The status. */
    @Column(name = "status")
    private String status;

    /** The created date. */
    @Column(name = "created_date")
    private Timestamp createdDate;

    /** The last modified date. */
    @Column(name = "last_modified_date")
    private Timestamp lastModifiedDate;

	/**
	 * Gets the payment id.
	 *
	 * @return the payment id
	 */
	public int getPaymentId() {
		return paymentId;
	}

	/**
	 * Sets the payment id.
	 *
	 * @param paymentId the new payment id
	 */
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	/**
	 * Gets the paid by.
	 *
	 * @return the paid by
	 */
	public int getPaidBy() {
		return paidBy;
	}

	/**
	 * Sets the paid by.
	 *
	 * @param paidBy the new paid by
	 */
	public void setPaidBy(int paidBy) {
		this.paidBy = paidBy;
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