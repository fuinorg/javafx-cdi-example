package org.fuin.examples.javafxcdi.app;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.fuin.examples.javafxcdi.common.MessageDisplay;
import org.fuin.examples.javafxcdi.common.ProgressUI;
import org.fuin.utils4j.Utils4J;

import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 * Example of a long running task.
 */
public final class LongRunningTaskExample extends Task<String> {

    private final MessageDisplay messageDisplay;

    private final ProgressUI progressUI;

    private final ResourceBundle bundle;

    public LongRunningTaskExample(final MessageDisplay messageDisplay, final ProgressUI progressUI, final ResourceBundle bundle) {
        super();
        this.messageDisplay = messageDisplay;
        this.progressUI = progressUI;
        this.bundle = bundle;
    }

    @Override
    protected String call() throws Exception {
        messageDisplay.clearMessages();
        Platform.runLater(progressUI::startProgress);
        messageDisplay.addMessage(bundle.getString("taskStarted"));
        final long start = System.currentTimeMillis();
        try {
            // Simulate long operation
            for (int i = 1; i <= 10; i++) {
                messageDisplay.addMessage(MessageFormat.format(bundle.getString("taskStep"), i, 10));
                Utils4J.sleep(1000);
            }
            return bundle.getString("taskResult");
        } finally {
            Platform.runLater(progressUI::stopProgress);
            final long durationMillis = System.currentTimeMillis() - start;
            messageDisplay.addMessage(MessageFormat.format(bundle.getString("taskFinished"), durationMillis));
        }
    }

}
