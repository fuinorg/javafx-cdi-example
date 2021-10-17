package org.fuin.examples.javafxcdi.controls;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ResourceBundle;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ExceptionAlert extends Alert {

    public final static String ALERT_TITLE = "alert.title";
    public final static String ALERT_TEXT = "alert.text";
    public final static String ALERT_STACK_TRACE = "alert.stacktrace";

    public ExceptionAlert(final Throwable ex) {
        super(AlertType.ERROR);

        final ResourceBundle bundle = ResourceBundle.getBundle(getClass().getName());
        final String message;
        if (ex instanceof NullPointerException) {
            message = "NullPointerException";
        } else {
            message = ex.getMessage();
        }

        setTitle(bundle.getString(ALERT_TITLE));
        setHeaderText(message);
        setContentText(bundle.getString(ALERT_TEXT));

        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        final String stackTrace = sw.toString();

        final Label label = new Label(bundle.getString(ALERT_STACK_TRACE));

        final TextArea textArea = new TextArea(stackTrace);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        final GridPane gridPane = new GridPane();
        gridPane.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(label, 0, 0);
        gridPane.add(textArea, 0, 1);

        getDialogPane().setExpandableContent(gridPane);

    }

}
