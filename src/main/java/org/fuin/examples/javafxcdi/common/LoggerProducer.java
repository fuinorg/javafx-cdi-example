package org.fuin.examples.javafxcdi.common;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CDI factory that creates a logger.
 */
@ApplicationScoped
public class LoggerProducer {

    /**
     * Creates a logger for an injection point.
     * 
     * @param injectionPoint
     *            Location where the logger is injected.
     * 
     * @return Logger.
     */
    @Produces
    public Logger createLog(final InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
    }

}
