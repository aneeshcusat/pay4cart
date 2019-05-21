package com.simpleworks.store.configuration;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.simpleworks.store.datatransferobject.OrderItem;
import com.simpleworks.store.datatransferobject.UserItem;
import com.simpleworks.store.security.login.LoginResult;
import com.simpleworks.store.security.login.LoginResult.Status;

/**
 * The Class SimpleWorksUserSessionConfiguration.
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SimpleWorksUserSessionConfiguration implements Serializable
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2779591617048681125L;

    /** The login result. */
    private LoginResult loginResult;
    
    /** The order item. */
    private OrderItem orderItem;

	/**
	 * Gets the login result.
	 *
	 * @return the login result
	 */
	public LoginResult getLoginResult()
    {
        if (loginResult == null) {
            loginResult = new LoginResult();
            loginResult.setStatus(Status.FAILED);
        }
        return loginResult;
    }

    /**
     * Sets the login result.
     * 
     * @param loginResult the new login result
     */
    public void setLoginResult(LoginResult loginResult)
    {
        this.loginResult = loginResult;
    }

    /**
     * Gets the current user.
     *
     * @return the current user
     */
    public UserItem getCurrentUser()
    {
        return getLoginResult().getUserItem();
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
}
