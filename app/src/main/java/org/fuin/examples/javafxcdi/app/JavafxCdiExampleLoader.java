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
package org.fuin.examples.javafxcdi.app;

import org.fuin.examples.javafxcdi.common.HostServicesBean;
import org.fuin.fxcdibootstrap.FxCdiApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Glue code between CDI and JavaFX.
 */
public final class JavafxCdiExampleLoader extends Application {

	private static final Logger LOG = LoggerFactory.getLogger(JavafxCdiExampleLoader.class);

	private JavafxCdiExample fxWeldApplication;

	@Override
	public void init() throws Exception {
		final SeContainerInitializer containerInit = SeContainerInitializer.newInstance();
		SeContainer container = containerInit.initialize();
		
		final Instance<FxCdiApplication> fxWeldApplications = container.select(FxCdiApplication.class);
		final HostServicesBean hostServicesBean = container.select(HostServicesBean.class).get();
		hostServicesBean.setHostServices(getHostServices());

		fxWeldApplication = (JavafxCdiExample) fxWeldApplications.get();
		LOG.info("Initializing application.");
		fxWeldApplication.init();
	}

	@Override
	public void start(final Stage stage) throws Exception {
		LOG.info("Starting application.");
		fxWeldApplication.start(stage, getParameters());
	}

	@Override
	public void stop() throws Exception {
		LOG.info("Stopping application.");
		fxWeldApplication.stop();
	}

}
