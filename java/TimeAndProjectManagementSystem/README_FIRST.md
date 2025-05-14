# General
This project can be used as a starting project for your own projects which have a JavaFX GUI. Note, that this project does not require to have JavaFX installed. The JavaFX jar files are referenced as Maven dependencies from the `pom.xml` file.

The project can be run with any installed JDK, starting with JDK 17.  

It contains all the necessary libraries to run Cucumber tests, JUnit 5 tests, and JUnit 4 tests. In addition, it contains the reference to the Mockito libraries.

It is a good idea to change the name of the project. Don't forget to also change the name in the `pom.xml` file. 

# Java version
If you use a different version than Java 17, then change the `release` element in the configuration of the `org.apache.maven.plugins` compiler plugin.
This should only be needed if you want to use a Java version, which offers newer features than those in 17. The setting Java 17 is compatible with all installed JDKs of version 17 or higher.

# Running the tests
The tests can be run through Maven, e.g., `mvn clean test`, Eclipse (select the project and then run as JUnit test), and ItelliJ (select the project and then run all tests). 

In case of Eclipse, if the run configuration for the test is set to JUnit 4, all the Cucumber tests and all the JUnit 4 tests are run, but not the JUnit 5 tests. If the setting is set to JUnit 5, then all the tests are run.

# JavaFX
This project contains also the references to JavaFX in the pom.xml file. This means, that there is no need to use a special version of the JDK, like Azul Zulu. 

Make sure that the main class is correctly set in the pom.xml file in the JavaFX plugin. 

To run the main class execute: `mvn javafx:run`. 

You can also run the JavaFX application from Eclipse and IntelliJ, if you have a correctly setup `module-info.java` file in the `src/main/java` directory. Note that you have to change `opens dtu.example.ui to javafx.fxml;` to the package where you have your `.fxml` files, and `exports dtu.example.ui;` to the package, that contains your start class.

More information can be found at [https://openjfx.io/openjfx-docs/](https://openjfx.io/openjfx-docs/).

## Not using JavaFX
If you don't need JavaFX, you can remove the javafx entries from the pom.xml file.

# Misc
- Be sure to remove unnecessary tests, packages, classes, etc. from the example project in your final project. 
- Remember to create a `README.txt` (plain text) or a `README.md` (using Markdown) file that explains how to build and run the tests and the application and any necessary information to use the information, e.g., a short user manual and a description of any preset logins with password, if required.