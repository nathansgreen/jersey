/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2011-2015 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package org.glassfish.jersey.filter;

import javax.annotation.Priority;
import javax.ws.rs.container.PreMatching;
import java.util.logging.Logger;

/**
 * JUL logging filter.
 * <p/>
 * Can be used on client or server side. Has the highest priority.
 *
 * @author Pavel Bucek (pavel.bucek at oracle.com)
 * @author Martin Matula
 */
@PreMatching
@Priority(Integer.MIN_VALUE)
@SuppressWarnings("ClassWithMultipleLoggers")
public final class LoggingFilter extends AbstractLoggingFilter {

    private static final Logger LOGGER = Logger.getLogger(LoggingFilter.class.getName());

    private static final String ENTITY_LOGGER_PROPERTY = LoggingFilter.class.getName() + ".entityLogger";
    private static final String LOGGING_ID_PROPERTY = LoggingFilter.class.getName() + ".id";

    @SuppressWarnings("NonConstantLogger")
    private final Logger logger;

    /**
     * Create a logging filter logging the request and response to a default JDK
     * logger, named as the fully qualified class name of this class. Entity
     * logging is turned off by default.
     */
    public LoggingFilter() {
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
    public LoggingFilter(final Logger logger, final boolean printEntity) {
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
    public LoggingFilter(final Logger logger, final int maxEntitySize) {
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
