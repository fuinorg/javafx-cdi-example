package org.fuin.examples.javafxcdi.common;

import java.io.File;
import java.net.MalformedURLException;

import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.HostServices;

/**
 * CDI bean that contains a host service instance.
 */
@ApplicationScoped
public class HostServicesBean {

    private static final Logger LOG = LoggerFactory.getLogger(HostServicesBean.class);

    private HostServices delegate;

    public HostServices getDelegate() {
        return delegate;
    }

    public void setHostServices(HostServices hostServices) {
        this.delegate = hostServices;
    }

    public void showUrl(final String url) {
        delegate.showDocument(url);
    }

    public void showFile(final File file) {
        try {
            delegate.showDocument(file.toURI().toURL().toExternalForm());
        } catch (MalformedURLException ex) {
            LOG.error("Failed to open file '" + file + "'", ex);
        }
    }

}
