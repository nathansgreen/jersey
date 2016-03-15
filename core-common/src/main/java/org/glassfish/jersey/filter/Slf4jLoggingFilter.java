package org.glassfish.jersey.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SLF4J logging filter.
 * <p/>
 * Can be used on client or server side. Has the highest priority.
 */
public final class Slf4jLoggingFilter extends AbstractLoggingFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Slf4jLoggingFilter.class);

    private static final String ENTITY_LOGGER_PROPERTY = Slf4jLoggingFilter.class.getName() + ".entityLogger";
    private static final String LOGGING_ID_PROPERTY = Slf4jLoggingFilter.class.getName() + ".id";

    @SuppressWarnings("NonConstantLogger")
    private final Logger logger;

    /**
     * Create a logging filter logging the request and response to a default SLF4J
     * logger, named as the fully qualified class name of this class. Entity
     * logging is turned off by default.
     */
    public Slf4jLoggingFilter() {
        this(LOGGER, false);
    }

    /**
     * Create a logging filter with custom logger and custom settings of entity
     * logging.
     *
     * @param logger      the logger to log requests and responses.
     * @param printEntity if true, entity will be logged as well up to the default maxEntitySize, which is 8KB
     */
    @SuppressWarnings("BooleanParameter")
    public Slf4jLoggingFilter(final Logger logger, final boolean printEntity) {
        super(printEntity);
        this.logger = logger;
    }

    /**
     * Creates a logging filter with custom logger and entity logging turned on, but potentially limiting the size
     * of entity to be buffered and logged.
     *
     * @param logger        the logger to log requests and responses.
     * @param maxEntitySize maximum number of entity bytes to be logged (and buffered) - if the entity is larger,
     *                      logging filter will print (and buffer in memory) only the specified number of bytes
     *                      and print "...more..." string at the end. Negative values are interpreted as zero.
     */
    public Slf4jLoggingFilter(final Logger logger, final int maxEntitySize) {
        super(maxEntitySize);
        this.logger = logger;
    }

    @Override
    String entityLoggerProperty() {
        return ENTITY_LOGGER_PROPERTY;
    }

    @Override
    String loggingIdProperty() {
        return LOGGING_ID_PROPERTY;
    }

    void log(final StringBuilder b) {
        if (logger != null) {
            logger.info(b.toString());
        }
    }
}
