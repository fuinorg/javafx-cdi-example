package org.fuin.examples.javafxcdi.common;

/**
 * Child controller that displays some data.
 * 
 * @param <T>
 *            Type of data.
 */
public interface ChildController<T> {

    /**
     * Sets the parent controller.
     * 
     * @param parentController
     *            Parent controller.
     */
    public void setParent(ProgressUI parentController);

    /**
     * Sets the data.
     * 
     * @param data
     *            Data to set.
     */
    public void setData(T data);

}
