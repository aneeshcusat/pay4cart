package com.simpleworks.email.contants;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * The Class EmailMessages.
 */
public class EmailMessages {
	
	/** The Constant BUNDLE_NAME. */
	private static final String BUNDLE_NAME = "com.simpleworks.email.contants.EmailMessages"; //$NON-NLS-1$

	/** The Constant RESOURCE_BUNDLE. */
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	/**
	 * Instantiates a new email messages.
	 */
	private EmailMessages() {
	}

	/**
	 * Gets the string.
	 *
	 * @param key the key
	 * @return the string
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
