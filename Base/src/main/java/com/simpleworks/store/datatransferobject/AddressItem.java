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
 * The Class AddressItem.
 */
@Entity
@Table(name = "address_info", uniqueConstraints = {@UniqueConstraint(columnNames = {"address_id"})})
public class AddressItem implements SimpleWorksBaseItem
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5628656638213113049L;

    /** The address id. */
    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private int addressId;

    /** The full name. */
    @Column(name = "full_name")
    private String fullName;
    
    /** The mobile number. */
    @Column(name = "mobile_number")
    private String mobileNumber;
    
    /** The land mark. */
    @Column(name = "land_mark")
    private String landMark;

    /** The town. */
    @Column(name = "town")
    private String town;
    
    /** The city. */
    @Column(name = "city")
    private String city;
    
    /** The type. */
    @Column(name = "type")
    private String type;
    
    /** The created date. */
    @Column(name = "created_date")
    private Timestamp createdDate;

    /** The last modified date. */
    @Column(name = "last_modified_date")
    private Timestamp lastModifiedDate;

	/**
	 * Gets the address id.
	 *
	 * @return the address id
	 */
	public int getAddressId() {
		return addressId;
	}

	/**
	 * Sets the address id.
	 *
	 * @param addressId the new address id
	 */
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	/**
	 * Gets the full name.
	 *
	 * @return the full name
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Sets the full name.
	 *
	 * @param fullName the new full name
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * Gets the mobile number.
	 *
	 * @return the mobile number
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * Sets the mobile number.
	 *
	 * @param mobileNumber the new mobile number
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * Gets the land mark.
	 *
	 * @return the land mark
	 */
	public String getLandMark() {
		return landMark;
	}

	/**
	 * Sets the land mark.
	 *
	 * @param landMark the new land mark
	 */
	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}

	/**
	 * Gets the town.
	 *
	 * @return the town
	 */
	public String getTown() {
		return town;
	}

	/**
	 * Sets the town.
	 *
	 * @param town the new town
	 */
	public void setTown(String town) {
		this.town = town;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
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