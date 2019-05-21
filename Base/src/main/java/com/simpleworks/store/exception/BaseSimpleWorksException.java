package com.simpleworks.store.exception;

/**
 * The Class simpleWorksException.
 * 
 * @author Aneeshkumar
 * @version 1.0
 */
public class BaseSimpleWorksException extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1711647094707136020L;

    /**
     * Instantiates a new delivery interface exception.
     *
     * @param message the message
     * @param exception the exception
     */
    public BaseSimpleWorksException(String message, Throwable exception) {
        super(message, exception);
    }
}
