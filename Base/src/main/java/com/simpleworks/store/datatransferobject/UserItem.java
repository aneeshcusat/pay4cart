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
 * The Class UserItem.
 */
@Entity
@Table(name = "user_info", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id"})})
public class UserItem implements SimpleWorksBaseItem
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -4647776200318098517L;

    /** The id. */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private int id;

    /** The user id. */
    @Column(name = "user_id", nullable = false)
    private String userId;

    /** The password. */
    @Column(name = "password")
    private String password;

    /** The hashkey. */
    @Column(name = "hashkey")
    private String hashkey;

    /** The first name. */
    @Column(name = "first_name")
    private String firstName;

    /** The created by. */
    @Column(name = "created_by")
    private int createdBy;

    /** The created date. */
    @Column(name = "created_date")
    private Timestamp createdDate;

    /** The last modified date. */
    @Column(name = "last_modified_date")
    private Timestamp lastModifiedDate;

    /** The modified by. */
    @Column(name = "modified_by")
    private int modifiedBy;

    /**
     * Gets the created by.
     *
     * @return the created by
     */
    public int getCreatedBy()
    {
        return createdBy;
    }

    /**
     * Sets the created by.
     *
     * @param createdBy the new created by
     */
    public void setCreatedBy(int createdBy)
    {
        this.createdBy = createdBy;
    }

    /* (non-Javadoc)
     * @see com.simpleworks.store.datatransferobject.SimpleWorksBaseItem#getCreatedDate()
     */
    @Override
    public Timestamp getCreatedDate()
    {
        return createdDate;
    }

    /* (non-Javadoc)
     * @see com.simpleworks.store.datatransferobject.SimpleWorksBaseItem#setCreatedDate(java.sql.Timestamp)
     */
    @Override
    public void setCreatedDate(Timestamp createdDate)
    {
        this.createdDate = createdDate;
    }

    /**
     * Gets the modified by.
     *
     * @return the modified by
     */
    public int getModifiedBy()
    {
        return modifiedBy;
    }

    /**
     * Sets the modified by.
     *
     * @param modifiedBy the new modified by
     */
    public void setModifiedBy(int modifiedBy)
    {
        this.modifiedBy = modifiedBy;
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName the new first name
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * Gets the user id.
     * 
     * @return the user id
     */
    public String getUserId()
    {
        return userId;
    }

    /**
     * Sets the user id.
     * 
     * @param userId the new user id
     */
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    /**
     * Gets the password.
     * 
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Sets the password.
     * 
     * @param password the new password
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * Gets the hashkey.
     * 
     * @return the hashkey
     */
    public String getHashkey()
    {
        return hashkey;
    }

    /**
     * Sets the hashkey.
     * 
     * @param hashkey the new hashkey
     */
    public void setHashkey(String hashkey)
    {
        this.hashkey = hashkey;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Gets the last modified date.
     *
     * @return the last modified date
     */
    public Timestamp getLastModifiedDate()
    {
        return lastModifiedDate;
    }

    /* (non-Javadoc)
     * @see com.simpleworks.store.datatransferobject.SimpleWorksBaseItem#setLastModifiedDate(java.sql.Timestamp)
     */
    @Override
    public void setLastModifiedDate(Timestamp lastModifiedDate)
    {
        this.lastModifiedDate = lastModifiedDate;
    }
}
