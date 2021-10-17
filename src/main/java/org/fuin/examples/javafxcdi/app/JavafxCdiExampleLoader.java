package org.fuin.examples.javafxcdi.app;

import javax.enterprise.inject.Instance;

import org.fuin.examples.javafxcdi.common.HostServicesBean;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.perdoctus.fx.FxCdiApplication;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Glue code between CDI and JavaFX.
 */
public final class JavafxCdiExampleLoader extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(JavafxCdiExampleLoader.class);

    private JavafxCdiExample fxWeldApplication;

    @SuppressWarnings("deprecation")
    @Override
    public void init() throws Exception {
        final Weld weld = new Weld();
        final WeldContainer weldContainer = weld.initialize();
        final Instance<FxCdiApplication> fxWeldApplications = weldContainer.instance().select(FxCdiApplication.class);
        final HostServicesBean hostServicesBean = weldContainer.instance().select(HostServicesBean.class).get();
        hostServicesBean.setHostServices(getHostServices());

        fxWeldApplication = (JavafxCdiExample) fxWeldApplications.get();
        LOG.info("Initializing application.");
        fxWeldApplication.init();
    }

    @Override
    public void start(final Stage stage) throws Exception {
        LOG.info("Starting application.");
        fxWeldApplication.start(stage, getParameters());
    }

    @Override
    public void stop() throws Exception {
        LOG.info("Stopping application.");
        fxWeldApplication.stop();
    }

}
