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

import java.net.URL;
import java.util.ResourceBundle;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import org.fuin.examples.javafxcdi.common.Loggable;
import org.fuin.examples.javafxcdi.controls.AboutAlert;
import org.fuin.examples.javafxcdi.controls.NodeControllerPair;
import org.fuin.examples.javafxcdi.controls.StackPaneProgressUI;
import org.fuin.examples.javafxcdi.controls.TextAreaMessageDisplay;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Main application controller.
 */
@Singleton
@Loggable
public class JavafxCdiExampleController implements Initializable {

    private static final String TITLE = "title";

    @FXML
    private MenuBar menuBar;

    @FXML
    private StackPane stackPane;

    @FXML
    private TextArea textAreaMessages;

    @FXML
    private BorderPane borderPaneContent;

    @Inject
    private Instance<FXMLLoader> loaderInstance;

    private ResourceBundle bundle;

    private StackPaneProgressUI progressUI;

    private TextAreaMessageDisplay messageDisplay;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        bundle = resources;
        progressUI = new StackPaneProgressUI(stackPane);
        messageDisplay = new TextAreaMessageDisplay(textAreaMessages);
    }

    @FXML
    public void onQuit(final ActionEvent event) {
        final Scene scene = stackPane.getScene();
        final Stage stage = (Stage) scene.getWindow();
        stage.close();
    }

    @FXML
    public void onOpenChildController(ActionEvent event) {
        final NodeControllerPair<ChildController> childCtrlPair = NodeControllerPair.load(loaderInstance, ChildController.class);
        final ChildController childController = childCtrlPair.getController();
        childController.setProgressUI(progressUI);
        childController.setMessageDisplay(messageDisplay);
        borderPaneContent.setCenter(childCtrlPair.getParent());
        
    }
    
    @FXML
    public void onAbout(ActionEvent event) {
        new AboutAlert().showAndWait();
    }

    /**
     * Returns the title of this controller.
     * 
     * @return Title.
     */
    public String getTitle() {
        return bundle.getString(TITLE);
    }

}
