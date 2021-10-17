package org.fuin.examples.javafxcdi.common;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Basic functionality for interceptor beans.
 */
public abstract class AbstractInterceptor {

    /**
     * Create a signature string for a given method.
     * 
     * @param method
     *            Method to create a string from.
     * 
     * @return String representation of the method.
     */
    protected final String signature(final Method method) {
        String name;
        if (method.getReturnType() == Void.class) {
            name = "void";
        } else {
            name = method.getReturnType().getSimpleName();
        }
        name = name + " " + method.getName();
        if (method.getParameterCount() == 0) {
            name = name + "()";
        } else {
            final StringBuilder sb = new StringBuilder("(");
            final Parameter[] params = method.getParameters();
            for (int i = 0; i < params.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(params[i].getType().getSimpleName());
            }
            sb.append(")");
            name = name + sb;
        }
        return name;
    }

}
