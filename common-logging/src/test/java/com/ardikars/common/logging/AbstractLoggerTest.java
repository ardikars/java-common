package com.ardikars.common.logging;

abstract class AbstractLoggerTest {

    private static final String DEFAULT_NAME = "LoggeTest";

    private static final String DEFAULT_FORMAT_ONE = "Hello {}";

    private static final String DEFAULT_FORMAT_TWO = "Hello {} {}";

    private static final String DEFAULT_FORMAT_THREE = "Hello {} {} {}";

    private static final String DEFAULT_MESSAGE_ONE = "Java";

    private static final String DEFAULT_MESSAGE_TWO = "World";

    private static final String DEFAULT_MESSAGE_THREE = "Game";

    private static final Throwable DEFAULT_THROWABLE = new Throwable("Log some error here");

    private Logger logger;

    public abstract void initLogger();

    protected void doInitLogger(LoggerFactory loggerFactory) {
        logger = loggerFactory.newInstance(DEFAULT_NAME);
    }

    public abstract void nameTest();

    protected void doNameTest() {
        assert logger.name().equals(DEFAULT_NAME) || logger.name().equals("NOP");
    }

    public abstract void isEnabledTest();

    protected void doIsEnabledTest() {
        assert logger.isEnabled(LogLevel.DEBUG) || !logger.isEnabled(LogLevel.DEBUG);
        assert logger.isEnabled(LogLevel.ERROR) || !logger.isEnabled(LogLevel.ERROR);
        assert logger.isEnabled(LogLevel.WARN) || !logger.isEnabled(LogLevel.WARN);
        assert logger.isEnabled(LogLevel.INFO) || !logger.isEnabled(LogLevel.INFO);
    }

    public abstract void isDebugEnabledTest();

    protected void doIsDebugEnabledTest() {
        assert logger.isDebugEnabled() || !logger.isDebugEnabled();
    }

    public abstract void isInfoEnabledTest();

    protected void doIsInfoEnabledTest() {
        assert logger.isInfoEnabled() || !logger.isInfoEnabled();
    }

    public abstract void isWarnEnabledTest();

    protected void doIsWarnEnabledTest() {
        assert logger.isWarnEnabled() || !logger.isWarnEnabled();
    }

    public abstract void isErrorEnabledTest();

    protected void doIsErrorEnabledTest() {
        assert logger.isErrorEnabled() || !logger.isErrorEnabled();
    }

    public abstract void logLavelAndMessageTest();

    protected void doLogLavelAndMessageTest() {
        logger.log(LogLevel.DEBUG, DEFAULT_MESSAGE_ONE);
        logger.log(LogLevel.ERROR, DEFAULT_MESSAGE_ONE);
        logger.log(LogLevel.WARN, DEFAULT_MESSAGE_ONE);
        logger.log(LogLevel.INFO, DEFAULT_MESSAGE_ONE);
    }

    public abstract void logLevelAndMessageFormatOneTest();

    protected void doLogLevelAndMessageFormatOneTest() {
        logger.log(LogLevel.DEBUG, DEFAULT_FORMAT_ONE, DEFAULT_MESSAGE_ONE);
        logger.log(LogLevel.ERROR, DEFAULT_FORMAT_ONE, DEFAULT_MESSAGE_ONE);
        logger.log(LogLevel.WARN, DEFAULT_FORMAT_ONE, DEFAULT_MESSAGE_ONE);
        logger.log(LogLevel.INFO, DEFAULT_FORMAT_ONE, DEFAULT_MESSAGE_ONE);
    }

    public abstract void logLevelAndMessageFormatTwoTest();

    protected void doLogLevelAndMessageFormatTwoTest() {
        logger.log(LogLevel.DEBUG, DEFAULT_FORMAT_TWO, DEFAULT_MESSAGE_ONE, DEFAULT_MESSAGE_TWO);
        logger.log(LogLevel.ERROR, DEFAULT_FORMAT_TWO, DEFAULT_MESSAGE_ONE, DEFAULT_MESSAGE_TWO);
        logger.log(LogLevel.WARN, DEFAULT_FORMAT_TWO, DEFAULT_MESSAGE_ONE, DEFAULT_MESSAGE_TWO);
        logger.log(LogLevel.INFO, DEFAULT_FORMAT_TWO, DEFAULT_MESSAGE_ONE, DEFAULT_MESSAGE_TWO);
    }

    public abstract void logLevelAndMessageFormatThreeTest();

    protected void doLogLevelAndMessageFormatThreeTest() {
        logger.log(LogLevel.DEBUG, DEFAULT_FORMAT_THREE, DEFAULT_MESSAGE_ONE, DEFAULT_MESSAGE_TWO, DEFAULT_MESSAGE_THREE);
        logger.log(LogLevel.ERROR, DEFAULT_FORMAT_THREE, DEFAULT_MESSAGE_ONE, DEFAULT_MESSAGE_TWO, DEFAULT_MESSAGE_THREE);
        logger.log(LogLevel.WARN, DEFAULT_FORMAT_THREE, DEFAULT_MESSAGE_ONE, DEFAULT_MESSAGE_TWO, DEFAULT_MESSAGE_THREE);
        logger.log(LogLevel.INFO, DEFAULT_FORMAT_THREE, DEFAULT_MESSAGE_ONE, DEFAULT_MESSAGE_TWO, DEFAULT_MESSAGE_THREE);
    }

    public abstract void logLevelAndThrowableTest();

    protected void doLogLevelAndThrowableTest() {
        logger.log(LogLevel.DEBUG, DEFAULT_THROWABLE);
        logger.log(LogLevel.ERROR, DEFAULT_THROWABLE);
        logger.log(LogLevel.WARN, DEFAULT_THROWABLE);
        logger.log(LogLevel.INFO, DEFAULT_THROWABLE);
    }

    public abstract void logLevelAndMessageThrowableTest();

    protected void doLogLevelAndMessageThrowableTest() {
        logger.log(LogLevel.DEBUG, DEFAULT_MESSAGE_ONE, DEFAULT_THROWABLE);
        logger.log(LogLevel.ERROR, DEFAULT_MESSAGE_ONE, DEFAULT_THROWABLE);
        logger.log(LogLevel.WARN, DEFAULT_MESSAGE_ONE, DEFAULT_THROWABLE);
        logger.log(LogLevel.INFO, DEFAULT_MESSAGE_ONE, DEFAULT_THROWABLE);
    }

    public abstract void debugMessageOneTest();

    protected void doDebugMessageOneTest() {
        logger.debug(DEFAULT_MESSAGE_ONE);
    }

    public abstract void debugThrowableTest();

    protected void doDebugThrowableTest() {
        logger.debug(DEFAULT_THROWABLE);
    }

    public abstract void debugMessageFormatOneTest();

    protected void doDebugMessageFormatOneTest() {
        logger.debug(DEFAULT_FORMAT_ONE, DEFAULT_MESSAGE_ONE);
    }

    public abstract void debugMessageFormatTwoTest();

    protected void doDebugMessageFormatTwoTest() {
        logger.debug(DEFAULT_FORMAT_TWO, DEFAULT_MESSAGE_ONE, DEFAULT_MESSAGE_TWO);
    }
    public abstract void debugMessageFormatThreeTest();

    protected void doDebugMessageFormatThreeTest() {
        logger.debug(DEFAULT_FORMAT_THREE, DEFAULT_MESSAGE_ONE, DEFAULT_MESSAGE_TWO, DEFAULT_MESSAGE_THREE);
    }

    public abstract void debugMessageThrowableTest();

    protected void doDebugMessageThrowableTest() {
        logger.debug(DEFAULT_MESSAGE_ONE, DEFAULT_THROWABLE);
    }

    public abstract void errorMessageOneTest();

    protected void doErrorMessageOneTest() {
        logger.error(DEFAULT_MESSAGE_ONE);
    }

    public abstract void errorThrowableTest();

    protected void doErrorThrowableTest() {
        logger.error(DEFAULT_THROWABLE);
    }

    public abstract void errorMessageFormatOneTest();

    protected void doErrorMessageFormatOneTest() {
        logger.error(DEFAULT_FORMAT_ONE, DEFAULT_MESSAGE_ONE);
    }

    public abstract void errorMessageFormatTwoTest();

    protected void doErrorMessageFormatTwoTest() {
        logger.error(DEFAULT_FORMAT_TWO, DEFAULT_MESSAGE_ONE, DEFAULT_MESSAGE_TWO);
    }
    public abstract void errorMessageFormatThreeTest();

    protected void doErrorMessageFormatThreeTest() {
        logger.error(DEFAULT_FORMAT_THREE, DEFAULT_MESSAGE_ONE, DEFAULT_MESSAGE_TWO, DEFAULT_MESSAGE_THREE);
    }

    public abstract void errorMessageThrowableTest();

    protected void doErrorMessageThrowableTest() {
        logger.error(DEFAULT_MESSAGE_ONE, DEFAULT_THROWABLE);
    }

    public abstract void warnMessageOneTest();

    protected void doWarnMessageOneTest() {
        logger.warn(DEFAULT_MESSAGE_ONE);
    }

    public abstract void warnThrowableTest();

    protected void doWarnThrowableTest() {
        logger.warn(DEFAULT_THROWABLE);
    }

    public abstract void warnMessageFormatOneTest();

    protected void doWarnMessageFormatOneTest() {
        logger.warn(DEFAULT_FORMAT_ONE, DEFAULT_MESSAGE_ONE);
    }

    public abstract void warnMessageFormatTwoTest();

    protected void doWarnMessageFormatTwoTest() {
        logger.warn(DEFAULT_FORMAT_TWO, DEFAULT_MESSAGE_ONE, DEFAULT_MESSAGE_TWO);
    }
    public abstract void warnMessageFormatThreeTest();

    protected void doWarnMessageFormatThreeTest() {
        logger.warn(DEFAULT_FORMAT_THREE, DEFAULT_MESSAGE_ONE, DEFAULT_MESSAGE_TWO, DEFAULT_MESSAGE_THREE);
    }

    public abstract void warnMessageThrowableTest();

    protected void doWarnMessageThrowableTest() {
        logger.warn(DEFAULT_MESSAGE_ONE, DEFAULT_THROWABLE);
    }

    public abstract void infoMessageOneTest();

    protected void doInfoMessageOneTest() {
        logger.info(DEFAULT_MESSAGE_ONE);
    }

    public abstract void infoThrowableTest();

    protected void doInfoThrowableTest() {
        logger.info(DEFAULT_THROWABLE);
    }

    public abstract void infoMessageFormatOneTest();

    protected void doInfoMessageFormatOneTest() {
        logger.info(DEFAULT_FORMAT_ONE, DEFAULT_MESSAGE_ONE);
    }

    public abstract void infoMessageFormatTwoTest();

    protected void doInfoMessageFormatTwoTest() {
        logger.info(DEFAULT_FORMAT_TWO, DEFAULT_MESSAGE_ONE, DEFAULT_MESSAGE_TWO);
    }
    public abstract void infoMessageFormatThreeTest();

    protected void doInfoMessageFormatThreeTest() {
        logger.info(DEFAULT_FORMAT_THREE, DEFAULT_MESSAGE_ONE, DEFAULT_MESSAGE_TWO, DEFAULT_MESSAGE_THREE);
    }

    public abstract void infoMessageThrowableTest();

    protected void doInfoMessageThrowableTest() {
        logger.info(DEFAULT_MESSAGE_ONE, DEFAULT_THROWABLE);
    }

}
