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

import javafx.geometry.Dimension2D;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

/**
 * Utilities for the package.
 */
final class FxUtils {

    private FxUtils() {
    }

    /**
     * Adjusts the width/height if the primary screen is smaller than the requested size.
     * 
     * @param width
     *            Requested width.
     * @param height
     *            Requested height.
     * 
     * @return Wither requested values or adjusted to screen size.
     */
    public static Dimension2D screenAdjustedDimension(final double width, final double height) {
        final Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double resultHeight = height;
        if (screenBounds.getHeight() > 100 && screenBounds.getHeight() < resultHeight) {
            resultHeight = screenBounds.getHeight() - 100;
        }
        double resultWidth = width;
        if (screenBounds.getWidth() > 100 && screenBounds.getWidth() < resultWidth) {
            resultWidth = screenBounds.getWidth() - 100;
        }
        return new Dimension2D(resultWidth, resultHeight);
    }

}
