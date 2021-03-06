/*
 * 
 */
package com.simpleworks.store;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.simpleworks.store.configuration.SimpleWorksApplicationConfiguration;
import com.simpleworks.store.configuration.SimpleWorksUserSessionConfiguration;

/**
 * The Class BaseSimpleWorksService.
 */
public class BaseSimpleWorksService
{
    
    /** The simple works application configuration. */
    @Resource
    SimpleWorksApplicationConfiguration simpleWorksApplicationConfiguration;

    /** The simpleWorks user session configuration. */
    @Resource
    SimpleWorksUserSessionConfiguration simpleWorksUserSessionConfiguration;

    /** The logger. */
    private Logger logger = null;

    /**
     * Log debug.
     *
     * @param message the message
     */
    public void logDebug(String message)
    {
            getLogger().debug(message);
    }

    /**
     * Log warning.
     *
     * @param message the message
     */
    public void logWarning(String message)
    {
        getLogger().warn(message);
    }

    /**
     * Log info.
     *
     * @param message the message
     */
    public void logInfo(String message)
    {
        getLogger().info(message);
    }

    /**
     * Log error.
     *
     * @param message the message
     */
    public void logError(String message)
    {
        getLogger().error(message);
    }

    /**
     * Log error.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public void logError(String message, Throwable throwable)
    {
        getLogger().error(message, throwable);
    }

    /**
     * Log trace.
     *
     * @param message the message
     */
    public void logTrace(String message)
    {
        getLogger().trace(message);
    }

    /**
     * Checks if is log info enabled.
     *
     * @return true, if is log info enabled
     */
    public boolean isLogInfoEnabled()
    {
        return getLogger().isInfoEnabled();
    }

    /**
     * Checks if is log warning enabled.
     *
     * @return true, if is log warning enabled
     */
    public boolean isLogWarningEnabled()
    {
        return getLogger().isWarnEnabled();
    }

    /**
     * Gets the logger.
     *
     * @return the logger
     */
    public Logger getLogger()
    {
        if (logger == null) {
            logger = LogManager.getLogger(this.getClass());
        }
        return logger;
    }

    /**
     * Gets the static logger.
     *
     * @param classType the class type
     * @return the static logger
     */
    public static Logger getStaticLogger(Class<?> classType)
    {
        return LogManager.getLogger(classType);
    }

    /**
     * Gets the simpleWorks user session configuration.
     *
     * @return the simpleWorks user session configuration
     */
    public SimpleWorksUserSessionConfiguration getSimpleWorksUserSessionConfiguration()
    {
        return simpleWorksUserSessionConfiguration;
    }

}
