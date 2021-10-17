package org.fuin.examples.javafxcdi.common;

/**
 * Provides functionality for tasks to display their status.
 */
public interface ProgressUI {

    /**
     * Disables the UI and shows a progress indicator.
     */
    public void startProgress();

    /**
     * Enables the UI and removes the progress indicator.
     */
    public void stopProgress();

}
