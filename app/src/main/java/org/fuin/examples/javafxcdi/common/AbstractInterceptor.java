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
