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
