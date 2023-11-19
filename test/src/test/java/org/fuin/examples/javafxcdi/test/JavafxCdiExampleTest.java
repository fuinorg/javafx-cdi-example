/**
 * Copyright 2023 by fuin.org.
 * 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fuin.examples.javafxcdi.test;

import static org.testfx.assertions.api.Assertions.assertThat;

import org.fuin.fxcdibootstrap.FxCdiApplicationLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.robot.Motion;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * Tests the JavaFX application.
 */
@ExtendWith(ApplicationExtension.class)
public class JavafxCdiExampleTest {

	@BeforeEach
	public void setup() throws Exception {
		FxToolkit.registerPrimaryStage();
		FxToolkit.setupApplication(FxCdiApplicationLoader.class);
	}

	@Test
	public void testClickOnButton(final FxRobot robot) {

		// GIVEN
		final MenuBar menuBar = robot.lookup("#menuBar").queryAs(MenuBar.class);
		assertThat(menuBar).isVisible();		

		// WHEN
		robot.clickOn("#menuHelp");
        robot.clickOn("#menuItemAbout");

		// THEN        
        // TODO Implement!

	}

}
