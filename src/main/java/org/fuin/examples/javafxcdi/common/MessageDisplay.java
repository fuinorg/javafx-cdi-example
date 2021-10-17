package org.fuin.examples.javafxcdi.common;

/**
 * Implements a component that can be used to display messages.
 */
public interface MessageDisplay {

    /**
     * Clears the message log
     */
    public void clearMessages();

    /**
     * Adds a message to the main log.
     * 
     * @param message
     *            Message.
     */
    public void addMessage(String message);

}
