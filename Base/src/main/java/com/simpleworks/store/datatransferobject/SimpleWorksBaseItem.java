package com.simpleworks.store.datatransferobject;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The Interface SimpleWorksBaseItem.
 */
public interface SimpleWorksBaseItem extends Serializable
{

    /**
     * Sets the created date.
     *
     * @param createdDate the new created date
     */
    public void setCreatedDate(Timestamp createdDate);

    /**
     * Sets the last modified date.
     *
     * @param createdDate the new last modified date
     */
    public void setLastModifiedDate(Timestamp createdDate);

    /**
     * Gets the created date.
     *
     * @return the created date
     */
    public Timestamp getCreatedDate();

}
