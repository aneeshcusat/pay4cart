package com.simpleworks.store.dataaccess;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.simpleworks.store.datatransferobject.SimpleWorksBaseItem;

/**
 * The Class SimpleWorksDataAccessObjectManager.
 */
public class SimpleWorksDataAccessObjectManager extends BaseSimpleWorksDataAccessObjectManager
{

    /* (non-Javadoc)
     * @see com.simpleworks.store.dataaccess.BaseSimpleWorksDataAccessObjectManager#saveItem(com.simpleworks.store.datatransferobject.SimpleWorksBaseItem)
     */
    @Override
    public void saveItem(SimpleWorksBaseItem saveItem)
    {
        saveItem.setCreatedDate(new Timestamp(new Date().getTime()));
        saveItem.setLastModifiedDate(new Timestamp(new Date().getTime()));
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(saveItem);
        tx.commit();
        session.close();
    }

    /* (non-Javadoc)
     * @see com.simpleworks.store.dataaccess.BaseSimpleWorksDataAccessObjectManager#updateItem(com.simpleworks.store.datatransferobject.SimpleWorksBaseItem)
     */
    @Override
    public void updateItem(SimpleWorksBaseItem updateItem)
    {
        updateItem.setLastModifiedDate(new Timestamp(new Date().getTime()));
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(updateItem);
        tx.commit();
        session.close();
    }

    /* (non-Javadoc)
     * @see com.simpleworks.store.dataaccess.BaseSimpleWorksDataAccessObjectManager#deleteItem(com.simpleworks.store.datatransferobject.SimpleWorksBaseItem)
     */
    @Override
    public void deleteItem(SimpleWorksBaseItem deleteItem)
    {
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(deleteItem);
        tx.commit();
        session.close();
    }

    /* (non-Javadoc)
     * @see com.simpleworks.store.dataaccess.BaseSimpleWorksDataAccessObjectManager#saveOrUpdateItem(com.simpleworks.store.datatransferobject.SimpleWorksBaseItem)
     */
    @Override
    public SimpleWorksBaseItem saveOrUpdateItem(SimpleWorksBaseItem updateItem)
    {
        if (updateItem.getCreatedDate() == null) {
            updateItem.setCreatedDate(new Timestamp(new Date().getTime()));
        }
        updateItem.setLastModifiedDate(new Timestamp(new Date().getTime()));
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(updateItem);
        tx.commit();
        session.close();
        return updateItem;
    }

    /* (non-Javadoc)
     * @see com.simpleworks.store.dataaccess.BaseSimpleWorksDataAccessObjectManager#getItemById(int, java.lang.Class)
     */
    @Override
    public Object getItemById(int id, Class<?> className)
    {
        Object item = null;
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        item = session.get(className, id);
        tx.commit();
        session.close();
        return item;
    }

    /* (non-Javadoc)
     * @see com.simpleworks.store.dataaccess.BaseSimpleWorksDataAccessObjectManager#getAllItems(java.lang.String)
     */
    @SuppressWarnings("deprecation")
    @Override
    public List<?> getAllItems(String itemName)
    {
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        String hql = String.format("from %s", itemName);
        Query<?> query = session.createQuery(hql);
        List<?> itemList = query.list();
        tx.commit();
        session.close();
        return itemList;
    }
    
    /**
     * Execute all group query.
     *
     * @param hqlQuery the hql query
     * @param dataMap the data map
     * @return the list
     */
    @SuppressWarnings("deprecation")
    public List<?> executeAllGroupQuery(String hqlQuery, Map<String, Object> dataMap)
    {
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query<?> query = session.createQuery(hqlQuery);
        if (dataMap != null) {
            for (String paramName : dataMap.keySet()) {
                query.setParameter(paramName, dataMap.get(paramName));
            }
        }
        List<?> itemList = query.list();
        tx.commit();
        session.close();
        return itemList;
    }


}
