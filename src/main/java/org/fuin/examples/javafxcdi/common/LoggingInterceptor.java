package org.fuin.examples.javafxcdi.common;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logs all method calls on TRACE level annotated with the {@link Loggable} annotation.
 */
@Interceptor
@Loggable
public final class LoggingInterceptor extends AbstractInterceptor {

    @AroundInvoke
    public final Object logMethodEntry(final InvocationContext ctx) throws Exception {
        final Logger log = LoggerFactory.getLogger(ctx.getMethod().getDeclaringClass());
        if (log.isTraceEnabled()) {
            final int targetHashCode = ctx.getTarget().hashCode();
            final Method method = ctx.getMethod();
            final String methodName = signature(method);
            log.trace("BEGIN {} {}", targetHashCode, methodName);
            if (method.getParameterCount() > 0) {
                final Parameter[] params = method.getParameters();
                for (int i = 0; i < params.length; i++) {
                    log.trace(params[i].getName() + "=" + ctx.getParameters()[i]);
                }
            }
            final Object retVal = ctx.proceed();
            if (method.getReturnType() != void.class) {
                log.trace("returns: {}", retVal);
            }
            log.trace("END   {} {}", targetHashCode, methodName);
            return retVal;
        } else {
            return ctx.proceed();
        }

    }

}
