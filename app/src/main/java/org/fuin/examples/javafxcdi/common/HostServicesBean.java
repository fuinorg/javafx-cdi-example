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

import java.io.File;
import java.net.MalformedURLException;

import jakarta.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.HostServices;

/**
 * CDI bean that contains a host service instance.
 */
@ApplicationScoped
public class HostServicesBean {

    private static final Logger LOG = LoggerFactory.getLogger(HostServicesBean.class);

    private HostServices delegate;

    public HostServices getDelegate() {
        return delegate;
    }

    public void setHostServices(HostServices hostServices) {
        this.delegate = hostServices;
    }

    public void showUrl(final String url) {
        delegate.showDocument(url);
    }

    public void showFile(final File file) {
        try {
            delegate.showDocument(file.toURI().toURL().toExternalForm());
        } catch (MalformedURLException ex) {
            LOG.error("Failed to open file '" + file + "'", ex);
        }
    }

}
