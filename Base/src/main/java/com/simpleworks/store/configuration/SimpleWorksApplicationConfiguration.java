package com.simpleworks.store.configuration;

import javax.annotation.Resource;

import com.simpleworks.store.BaseSimpleWorksService;
import com.simpleworks.store.dataaccess.SimpleWorksDataAccessObjectManager;
import com.simpleworks.store.datatransferobject.OrderItem;
import com.simpleworks.store.datatransferobject.UserItem;
import com.simpleworks.store.manager.SimpleWorksUserProfileManager;

/**
 * The Class SimpleWorksApplicationConfiguration.
 */
public class SimpleWorksApplicationConfiguration extends BaseSimpleWorksService
{

    /** The simple works user profile manager. */
    @Resource
    SimpleWorksUserProfileManager simpleWorksUserProfileManager;

    /** The simple works data access object manager. */
    @Resource
    SimpleWorksDataAccessObjectManager simpleWorksDataAccessObjectManager;

    /** The host name. */
    private String hostName;

    /** The port number. */
    private int portNumber;

    /** The protocol. */
    private String protocol;

    /**
     * Initialize.
     */
    public void initialize()
    {

        logDebug("Initializing SimpleWorksApplicationConfiguration...");
        logDebug("END : Initializing SimpleWorksApplicationConfiguration...");

    }

    /**
     * Gets the host name.
     *
     * @return the host name
     */
    public String getHostName()
    {
        return hostName;
    }

    /**
     * Sets the host name.
     *
     * @param hostName the new host name
     */
    public void setHostName(String hostName)
    {
        this.hostName = hostName;
    }

    /**
     * Gets the port number.
     *
     * @return the port number
     */
    public int getPortNumber()
    {
        return portNumber;
    }

    /**
     * Sets the port number.
     *
     * @param portNumber the new port number
     */
    public void setPortNumber(int portNumber)
    {
        this.portNumber = portNumber;
    }

    /**
     * Gets the protocol.
     *
     * @return the protocol
     */
    public String getProtocol()
    {
        return protocol;
    }

    /**
     * Sets the protocol.
     *
     * @param protocol the new protocol
     */
    public void setProtocol(String protocol)
    {
        this.protocol = protocol;
    }

    /**
     * Gets the simple works user profile manager.
     *
     * @return the simple works user profile manager
     */
    public SimpleWorksUserProfileManager getSimpleWorksUserProfileManager()
    {
        return simpleWorksUserProfileManager;
    }

    /**
     * Gets the current user.
     *
     * @return the current user
     */
    public UserItem getCurrentUser()
    {
        return getSimpleWorksUserSessionConfiguration().getCurrentUser();
    }
    
    /**
     * Gets the current order.
     *
     * @return the current order
     */
    public OrderItem getCurrentOrder()
    {
        return getSimpleWorksUserSessionConfiguration().getOrderItem();
    }

    /**
     * Gets the url.
     *
     * @return the url
     */
    public String getUrl()
    {
        return protocol + "://" + hostName + ":" + portNumber + "/" + "bops/dashboard";
    }

    /**
     * Gets the current user id.
     *
     * @return the current user id
     */
    public int getCurrentUserId()
    {
        int userId = 0;
        if (getCurrentUser() != null) {
            userId = getCurrentUser().getId();
        }
        return userId;
    }

}
