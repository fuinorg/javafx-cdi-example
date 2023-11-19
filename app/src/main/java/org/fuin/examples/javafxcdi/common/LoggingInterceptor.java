/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.fuin.examples.javafxcdi.common;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

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
