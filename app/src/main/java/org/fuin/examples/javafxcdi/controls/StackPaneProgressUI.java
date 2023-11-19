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

import org.fuin.examples.javafxcdi.common.ProgressUI;

import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Implements a progress indicator based on a stack pane.
 */
public final class StackPaneProgressUI implements ProgressUI {

    private final StackPane stackPane;

    private VBox vbox;

    /**
     * Constructor with mandatory data.
     * 
     * @param stackPane
     *            Stack pane to use.
     */
    public StackPaneProgressUI(final StackPane stackPane) {
        super();
        this.stackPane = stackPane;
    }

    @Override
    public void startProgress() {
        if (vbox == null) {
            final ProgressIndicator pi = new ProgressIndicator();
            pi.setPrefWidth(100.0);
            pi.setPrefHeight(100.0);
            vbox = new VBox(pi);
            vbox.setAlignment(Pos.CENTER);
            vbox.setPrefWidth(800.0);
            vbox.setPrefHeight(600.0);
            stackPane.getChildren().add(vbox);
        }
    }

    @Override
    public void stopProgress() {
        if (vbox != null) {
            stackPane.getChildren().remove(vbox);
            vbox = null;
        }
    }

}
