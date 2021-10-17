package org.fuin.examples.javafxcdi.common;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.fuin.ext4logback.LogbackStandalone;
import org.fuin.utils4j.PropertiesFilePreferencesFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * Makes sure a preferences folder exists in the user's home directory and initializes Logback and preferences.
 */
public final class Preferences {

    private Preferences() {
        throw new UnsupportedOperationException("Creating an instance of this utility class is not allowed");
    }

    /**
     * Initialize logging and preferences.
     */
    public static void init() {
        try {

            initPreferenceStorage();

            final File logbackXmlFile = new File(getUserPrefDir(), "logback.xml");
            initLogbackXml(logbackXmlFile);
            new LogbackStandalone().init(logbackXmlFile);
            SLF4JBridgeHandler.removeHandlersForRootLogger();
            SLF4JBridgeHandler.install();

        } catch (final RuntimeException ex) {
            System.err.println("Error initializing logging");
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }

    /**
     * Returns the application's preferences directory.
     * 
     * @return Directory.
     */
    public static File getUserPrefDir() {
        final File userHomeDir = new File(System.getProperty("user.home"));
        return new File(userHomeDir, ".javafx-cdi-example");
    }

    private static void initLogbackXml(final File logbackXmlFile) {
        if (logbackXmlFile.exists()) {
            return;
        }
        try {
            String xml = IOUtils.resourceToString("/template-logback.xml", Charset.forName("utf-8"));
            final String tmpPath = FilenameUtils.getFullPathNoEndSeparator(System.getProperty("java.io.tmpdir") + File.separatorChar);
            xml = StringUtils.replace(xml, "${log_path}", tmpPath);
            FileUtils.write(logbackXmlFile, xml, Charset.forName("utf-8"));
        } catch (final IOException ex) {
            throw new InitializationException("Error creating logback config: " + logbackXmlFile, ex);
        }
    }

    private static void initPreferenceStorage() {

        // Create a preferences directory for all users
        final File systemPrefDir = new File(".config");
        if (!systemPrefDir.exists()) {
            systemPrefDir.mkdir();
        }

        // Create a user preferences directory
        final File userPrefDir = getUserPrefDir();
        if (!userPrefDir.exists()) {
            userPrefDir.mkdir();
        }

        // Set both directories as system properties
        System.setProperty(PropertiesFilePreferencesFactory.SYSTEM_PREF_DIR, systemPrefDir.toString());
        System.setProperty(PropertiesFilePreferencesFactory.USER_PREF_DIR, userPrefDir.toString());

        // Set the factory
        System.setProperty("java.util.prefs.PreferencesFactory", PropertiesFilePreferencesFactory.class.getName());

    }

}
