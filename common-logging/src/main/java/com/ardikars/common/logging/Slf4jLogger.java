package com.ardikars.common.logging;

import org.slf4j.Logger;

/**
 * Slf4j logger implementation.
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 */
class Slf4jLogger extends AbstractLogger {

    private final transient Logger logger;

    Slf4jLogger(Logger logger) {
        super(logger.getName());
        this.logger = logger;
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    @Override
    public void debug(String format, Object arg1) {
        logger.debug(format, arg1);
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        logger.debug(format, arg1, arg2);
    }

    @Override
    public void debug(String format, Object... arguments) {
        logger.debug(format, arguments);
    }

    @Override
    public void debug(String format, Throwable throwable) {
        logger.debug(format, throwable);
    }

    @Override
    public void info(String format, Object obj1) {
        logger.info(format, obj1);
    }

    @Override
    public void info(String format, Object obj1, Object obj2) {
        logger.info(format, obj1, obj2);
    }

    @Override
    public void info(String format, Object... arguments) {
        logger.info(format, arguments);
    }

    @Override
    public void info(String format, Throwable throwable) {
        logger.info(format, throwable);
    }

    @Override
    public void warn(String format, Object arg1) {
        logger.warn(format, arg1);
    }

    @Override
    public void warn(String format, Object arg1, Object obj2) {
        logger.warn(format, arg1, obj2);
    }

    @Override
    public void warn(String format, Object... arguments) {
        logger.warn(format, arguments);
    }

    @Override
    public void warn(String format, Throwable throwable) {
        logger.warn(format, throwable);
    }

    @Override
    public void error(String format, Object obj1) {
        logger.error(format, obj1);
    }

    @Override
    public void error(String format, Object obj1, Object obj2) {
        logger.error(format, obj1, obj2);
    }

    @Override
    public void error(String format, Object... arguments) {
        logger.error(format, arguments);
    }

    @Override
    public void error(String format, Throwable throwable) {
        logger.error(format, throwable);
    }

}
