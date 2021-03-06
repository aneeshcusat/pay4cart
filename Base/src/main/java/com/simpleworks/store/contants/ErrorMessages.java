package com.simpleworks.store.contants;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.simpleworks.store.BaseSimpleWorksService;

/**
 * The Class ErrorMessages.
 */
public class ErrorMessages extends BaseSimpleWorksService {

	/** The Constant BUNDLE_NAME. */
	private static final String BUNDLE_NAME = "com.simpleworks.store.constants.ErrorMessages";

	/**
	 * Instantiates a new error messages.
	 */
	private ErrorMessages() {

	}

	/** The Constant RESOURCE_BUNDLE. */
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	/**
	 * Gets the string.
	 *
	 * @param key
	 *            the key
	 * @return the string
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			getStaticLogger(ErrorMessages.class).error("key not found", e);
			return '!' + key + '!';
		}
	}
}
