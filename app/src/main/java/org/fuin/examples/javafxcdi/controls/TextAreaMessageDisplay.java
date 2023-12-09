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

import jakarta.validation.constraints.NotNull;

import org.fuin.examples.javafxcdi.common.MessageDisplay;
import org.fuin.utils4j.Utils4J;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 * Message text area that displays output from tasks. It takes care about using the JavaFX application thread.
 */
public final class TextAreaMessageDisplay implements MessageDisplay {

    private final TextArea textArea;

    /**
     * Constructor with mandatory data.
     * 
     * @param textArea
     *            Message output control.
     */
    public TextAreaMessageDisplay(@NotNull final TextArea textArea) {
        super();
        Utils4J.checkNotNull("textArea", textArea);
        this.textArea = textArea;
    }

    @Override
    public void clearMessages() {
        if (Platform.isFxApplicationThread()) {
            textArea.clear();
        } else {
            Platform.runLater(textArea::clear);
        }
    }

    @Override
    public void addMessage(final String message) {
        if (message == null) {
            return;
        }
        if (Platform.isFxApplicationThread()) {
            appendMessage(message);
        } else {
            Platform.runLater(() -> appendMessage(message));
        }
    }

    private void appendMessage(final String message) {
        textArea.appendText(message + System.lineSeparator());
        textArea.setScrollTop(Double.MAX_VALUE);
    }

}