package org.fuin.examples.javafxcdi.controls;

import javax.validation.constraints.NotNull;

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