# javafx-cdi-example
JavaFX with CDI example.

[![Apache](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Java Development Kit 11](https://img.shields.io/badge/JDK-11-green.svg)](https://openjdk.java.net/projects/jdk/11/)

> :warning: In case you have a hi-resolution display and want to run the application on Ubuntu, make sure to pass an environment variable like `GDK_SCALE=2`to the application. Otherwise it will be unscaled. See [Issue #643](https://github.com/javafxports/openjdk-jfx/issues/643) for more details.

## Features
The example application shows how to implement the most important parts of a real world [JavaFX](https://openjfx.io/) application.
Most tutorials for [JavaFX](https://openjfx.io/) only show how to create a screen, but there is more than that.

This example has the following features included:

* [Based on Java 21 with JavaFX 21](#based-on-java-21-with-javafx-21)
* [Maven build](#maven-build)
* [Github build](#github-build)
* [Contexts and Dependency Injection (CDI)](#contexts-and-dependency-injection-cdi)
* [Structuring an application with sub controllers](#structuring-an-application-with-sub-controllers)
* [Using tasks for handling long operations](#using-tasks-for-handling-long-operations)
* [Blocking UI during task execution](#blocking-ui-during-task-execution)
* [About dialog showing a version](#about-dialog-showing-a-version)
* [Catching unexpected exceptions showing a dialog](#catching-unexpected-exceptions-showing-a-dialog)
* [UI test with TestFX](#ui-test-with-testfx)
* [Maven Archetype](#maven-archetype)

### Based on Java 21 with JavaFX 21
The project is based on Java 21 and [JavaFX](https://openjfx.io/).

### Maven build
There is a fully configured Maven build that creates a ZIP archive that has a [batch file](src/main/app/javafx-cdi-example.bat) (Windows) and a [shell script](src/main/app/javafx-cdi-example.sh) (Linux) to start the application.

### Github build
The project is ready [configured](.github/workflows/maven.yml) to be build on Github.
Simply copy the project generated by the below archetype to a fresh Github repository and you have an automated build out of the box.

It also sets the necessary arguments in case you want to run some GUI unit tests:

```
-Djava.awt.headless=true -Dtestfx.robot=glass -Dtestfx.headless=true 
-Dprism.order=sw -Dprism.text=t2k -Dtestfx.setup.timeout=2500
```

### Contexts and Dependency Injection (CDI)
You can use `@Inject` annotations in your application controllers for injecting services, events and configuration.

```java
@Singleton
@Loggable
public class ChildController implements Initializable {

    @FXML
    private Label labelResult;
    
    @Inject
    private Logger log;

    ...

}
```

### Structuring an application with sub controllers
If you start with JavaFX it's not easy to figure out how to structure a larger application.
See the [JavafxCdiExampleController.java](src/main/java/org/fuin/examples/javafxcdi/app/JavafxCdiExampleController.java#L72) to see how a parent controller opens a child controller and passes some data to it.

```java
@FXML
public void onOpenChildController(ActionEvent event) {
    final NodeControllerPair<ChildController> childCtrlPair = 
            NodeControllerPair.load(loaderInstance, ChildController.class);
    final ChildController childController = childCtrlPair.getController();
    childController.setProgressUI(progressUI);
    childController.setMessageDisplay(messageDisplay);
    borderPaneContent.setCenter(childCtrlPair.getParent());
}
```

### Using tasks for handling long operations
There are often some operations that may take a while. You need to create a task for it. See the [LongRunningTaskExample.java](src/main/java/org/fuin/examples/javafxcdi/app/LongRunningTaskExample.java) on how to do it. The task also emits some status information to the UI and blocks it.

### Blocking UI during task execution
During execution of a long running task you most likely want to block the UI and show some progress indicator.

<a href="images/progress-indicator.png"><img src="https://github.com/fuinorg/javafx-cdi-example/raw/main/images/progress-indicator.png" width="206" height="239"></a>

### About dialog showing a version
Use the Maven version from the POM and the Git information inside the project to populate an [About](src/main/java/org/fuin/examples/javafxcdi/controls/AboutAlert.java) dialog.

<a href="images/about.png"><img src="https://github.com/fuinorg/javafx-cdi-example/raw/main/images/about.png" width="241" height="170"></a>

### Catching unexpected exceptions showing a dialog
Sometimes your application will fail and throw an exception. It's a good idea to show something useful to the user (See [ExceptionAlert](src/main/java/org/fuin/examples/javafxcdi/controls/ExceptionAlert.java)).

<a href="images/exception-dialog.png"><img src="https://github.com/fuinorg/javafx-cdi-example/raw/main/images/exception-dialog.png" width="291" height="118"></a>

### UI test with TestFX
Example UI test with TestFX: [JavafxCdiExampleTest.java](test/src/test/java/org/fuin/examples/javafxcdi/test/JavafxCdiExampleTest.java)

### Maven Archetype
There is a [Maven Archetype](https://github.com/fuinorg/javafx-cdi-archetype) available that helps kickstarting your own project based on this code.
 
