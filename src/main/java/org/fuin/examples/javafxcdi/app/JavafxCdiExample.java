package org.fuin.examples.javafxcdi.app;

import java.io.IOException;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.fuin.examples.javafxcdi.common.BootstrapBean;
import org.fuin.examples.javafxcdi.common.Preferences;
import org.fuin.examples.javafxcdi.controls.ExceptionAlert;
import org.fuin.examples.javafxcdi.controls.NodeControllerPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.perdoctus.fx.FxCdiApplication;
import javafx.application.Application;
import javafx.application.Application.Parameters;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Dimension2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Main application.
 */
public class JavafxCdiExample extends FxCdiApplication {

    private static final Logger LOG = LoggerFactory.getLogger(JavafxCdiExample.class);

    @Inject
    private Instance<FXMLLoader> loaderInstance;

    @Inject
    private BootstrapBean bootstrapBean;

    @Override
    public void start(final Stage primaryStage, final Parameters parameters) throws IOException {

        LOG.info("Start application");

        final NodeControllerPair<JavafxCdiExampleController> ncw = NodeControllerPair.load(loaderInstance,
                JavafxCdiExampleController.class);

        // Catch all exceptions in UI thread
        Thread.currentThread().setUncaughtExceptionHandler((thread, throwable) -> {

            final Logger log = LoggerFactory.getLogger(JavafxCdiExample.class);
            log.error("Uncaught Exception in " + thread, throwable);

            if (Platform.isFxApplicationThread()) {
                new ExceptionAlert(throwable).showAndWait();
            } else {
                Platform.runLater(() -> new ExceptionAlert(throwable).showAndWait());
            }

        });

        // Set bootstrap information
        bootstrapBean.setParameters(parameters);

        // Start UI
        final Dimension2D dimension = FxUtils.screenAdjustedDimension(1400, 800);
        final Parent parent = ncw.getParent();
        final Scene scene = new Scene(parent, dimension.getWidth(), dimension.getHeight());
        primaryStage.setScene(scene);
        primaryStage.setTitle(ncw.getController().getTitle());
        primaryStage.getIcons().addAll(new Image("/images/javafx-cdi-example-128x128.png"),
                new Image("/images/javafx-cdi-example-64x64.png"), new Image("/images/javafx-cdi-example-48x48.png"),
                new Image("/images/javafx-cdi-example-32x32.png"), new Image("/images/javafx-cdi-example-16x16.png"));
        primaryStage.show();

    }

    @Override
    public void stop() {
        LOG.info("Stop application");
    }

    public static void main(final String[] args) {
        Preferences.init();
        Application.launch(JavafxCdiExampleLoader.class, args);
    }

}
