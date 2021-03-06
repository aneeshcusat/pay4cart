package com.simpleworks.store.security.login;

import java.io.Serializable;

import com.simpleworks.store.datatransferobject.UserItem;

/**
 * The Enum LoginResult.
 * 
 * @author Aneeshkumar
 * @version 1.0
 */
public class LoginResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2913433474292241747L;

	/**
	 * The Enum status.
	 */
	public enum Status {
		/** The success. */
		SUCCESS,

		/** The failed. */
		FAILED

	}

	/** The status. */
	private Status status = Status.FAILED;

	private String hashKey;

	private UserItem userItem;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public UserItem getUserItem() {
		return userItem;
	}

	public void setUserItem(UserItem userItem) {
		this.userItem = userItem;
	}

	public String getHashKey() {
		return hashKey;
	}

	public void setHashKey(String hashKey) {
		this.hashKey = hashKey;
	}

}
