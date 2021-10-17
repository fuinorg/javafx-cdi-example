package org.fuin.examples.javafxcdi.controls;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.scene.control.Alert;
import javafx.scene.layout.Region;

public class AboutAlert extends Alert {

    private static final Logger LOG = LoggerFactory.getLogger(AboutAlert.class);

    public static final String ABOUT_TITLE = "title";
    public static final String ABOUT_HEADER = "header";
    public static final String ABOUT_TEXT = "text";

    public AboutAlert() {
        super(AlertType.INFORMATION);
        final ResourceBundle bundle = ResourceBundle.getBundle(getClass().getName());
        getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        setTitle(bundle.getString(ABOUT_TITLE));
        setHeaderText(bundle.getString(ABOUT_HEADER));
        final Properties gitProps = readGitProperties();
        final StringBuilder sb = new StringBuilder();
        sb.append(bundle.getString(ABOUT_TEXT));
        if (!gitProps.isEmpty()) {
            sb.append(StringUtils.LF);
            sb.append(StringUtils.LF);
            sb.append("Version: ").append(gitProps.getProperty("git.build.version")).append(StringUtils.LF);
            sb.append("Commit ID: ").append(gitProps.getProperty("git.commit.id.abbrev")).append(StringUtils.LF);
            sb.append("Build time: ").append(gitProps.getProperty("git.build.time")).append(StringUtils.LF);
        }
        setContentText(sb.toString());
    }

    private static Properties readGitProperties() {
        Properties gitProps = new Properties();
        String propFileName = "git.properties";
        try (InputStream inputStream = AboutAlert.class.getClassLoader().getResourceAsStream(propFileName)) {
            if (inputStream != null) {
                gitProps.load(inputStream);
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return gitProps;
    }

}
