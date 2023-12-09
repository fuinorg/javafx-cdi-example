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
package org.fuin.examples.javafxcdi.controls;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import jakarta.enterprise.inject.Instance;
import jakarta.validation.constraints.NotNull;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Combines a node and it's controller.
 * 
 * @param <T>
 *            Type of the controller.
 */
public final class NodeControllerPair<T> {

    private static final Logger LOG = LoggerFactory.getLogger(NodeControllerPair.class);

    private final String name;

    private final Parent parent;

    private final T controller;

    private final ResourceBundle bundle;

    /**
     * Constructor with all data.
     * 
     * @param name
     *            Unique name.
     * @param parent
     *            Parent node.
     * @param controller
     *            UI controller.
     * @param bundle
     *            Resource bundle.
     */
    public NodeControllerPair(@NotNull String name, @NotNull final Parent parent, @NotNull final T controller,
            final @NotNull ResourceBundle bundle) {
        super();
        Validate.notNull(name, "name==null");
        Validate.notNull(parent, "[" + name + "] parent==null");
        Validate.notNull(controller, "[" + name + "] controller==null");
        Validate.notNull(bundle, "[" + name + "] bundle==null");
        this.name = name;
        this.parent = parent;
        this.controller = controller;
        this.bundle = bundle;
    }

    /**
     * Returns the name.
     * 
     * @return Unique name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the parent node.
     * 
     * @return Node.
     */
    public Parent getParent() {
        return parent;
    }

    /**
     * Returns the controller.
     * 
     * @return UI Controller.
     */
    public T getController() {
        return controller;
    }

    /**
     * Returns the resource bundle.
     * 
     * @return Bundle.
     */
    public ResourceBundle getBundle() {
        return bundle;
    }

    /**
     * Creates a new node/controller pair by loading it from an FXML file. The resource is assumed to be in the same folder structure as the
     * class and has the class name. The FXML is assumed to have the same name and path as the resource, but ending with ".fxml".
     * 
     * @param name
     *            Unique name.
     * @param loaderInstance
     *            Instance to use for getting the loader bean.
     * @param controllerClass
     *            Controller class.
     * 
     * @return New instance.
     * 
     * @param <T>
     *            Expected type of the UI controller.
     */
    public static <T> NodeControllerPair<T> load(@NotNull final Instance<FXMLLoader> loaderInstance,
            @NotNull final Class<T> controllerClass) {
        return load(controllerClass.getSimpleName(), loaderInstance, controllerClass.getName().replace('.', '/'));
    }

    /**
     * Creates a new node/controller pair by loading it from an FXML file. The FXML is assumed to have the same name and path as the
     * resource, but ending with ".fxml".
     * 
     * @param name
     *            Unique name.
     * @param loaderInstance
     *            Instance to use for getting the loader bean.
     * @param resource
     *            Resource name.
     * 
     * @return New instance.
     * 
     * @param <T>
     *            Expected type of the UI controller.
     */
    public static <T> NodeControllerPair<T> load(@NotNull final String name, @NotNull final Instance<FXMLLoader> loaderInstance,
            @NotNull final String resource) {
        return load(name, loaderInstance, "/" + resource + ".fxml", resource);
    }

    /**
     * Creates a new node/controller pair by loading it from an FXML file.
     * 
     * @param name
     *            Unique name.
     * @param loaderInstance
     *            Instance to use for getting the loader bean.
     * @param fxml
     *            FXML resource path.
     * @param resource
     *            Resource name.
     * 
     * @return New instance.
     * 
     * @param <T>
     *            Expected type of the UI controller.
     */
    public static <T> NodeControllerPair<T> load(@NotNull final String name, @NotNull final Instance<FXMLLoader> loaderInstance,
            @NotNull final String fxml, @NotNull final String resource) {
        final FXMLLoader loader = loaderInstance.select(FXMLLoader.class).get();
        try {
            try {
                final ResourceBundle bundle = ResourceBundle.getBundle(resource);
                if (bundle == null) {
                    throw new IllegalStateException("Bundle not found: " + resource);
                }
                final URL url = NodeControllerPair.class.getResource(fxml);
                if (url == null) {
                    throw new IllegalStateException("FXML not found: " + fxml);
                }
                LOG.debug("Load ({}) {}", name, url);
                loader.setLocation(url);
                loader.setResources(bundle);
                return new NodeControllerPair<T>(name, loader.load(), loader.getController(), bundle);
            } catch (final IOException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            loaderInstance.destroy(loader);
        }
    }

}
