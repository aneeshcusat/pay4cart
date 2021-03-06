package com.simpleworks.store.dataaccess;

import java.util.List;

import org.hibernate.SessionFactory;

import com.simpleworks.store.BaseSimpleWorksService;
import com.simpleworks.store.datatransferobject.SimpleWorksBaseItem;

/**
 * The Class BasesimpleWorksDataAccessObjectManager.
 * 
 * @author Aneeshkumar
 * @version 1.0
 */
public abstract class BaseSimpleWorksDataAccessObjectManager extends BaseSimpleWorksService
{

    /** The session factory. */
    private SessionFactory sessionFactory;

    /**
     * Sets the session factory.
     * 
     * @param sessionFactory the new session factory
     */
    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Gets the session factory.
     * 
     * @return the session factory
     */
    public SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

    /**
     * Save item.
     * 
     * @param saveItem the save item
     */
    public abstract void saveItem(SimpleWorksBaseItem saveItem);

    /**
     * Update item.
     * 
     * @param updateItem the update item
     */
    public abstract void updateItem(SimpleWorksBaseItem updateItem);

    /**
     * Delete item.
     * 
     * @param deleteItem the delete item
     */
    public abstract void deleteItem(SimpleWorksBaseItem deleteItem);

    /**
     * Save or update item.
     *
     * @param updateItem the update item
     * @return the simple works base item
     */
    public abstract SimpleWorksBaseItem saveOrUpdateItem(SimpleWorksBaseItem updateItem);

    /**
     * Gets the item by id.
     * 
     * @param id the id
     * @param className the class name
     * @return the item by id
     */
    public abstract Object getItemById(int id, Class<?> className);

    /**
     * Gets the all items.
     * 
     * @param itemName the item name
     * @return the all items
     */
    public abstract List<?> getAllItems(String itemName);
}
