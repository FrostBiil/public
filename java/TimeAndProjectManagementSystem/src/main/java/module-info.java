module projectManagementApp {
      requires transitive javafx.controls;
      requires javafx.fxml;
      requires com.google.gson;
  requires java.desktop;

  opens dtu.group5 to javafx.fxml; // Gives access to fxml files
      exports dtu.group5;
      exports dtu.group5.backend.model;
      opens dtu.group5.backend.model to javafx.fxml, com.google.gson; // Combined opens directive
      exports dtu.group5.backend.repository;
      opens dtu.group5.backend.repository to javafx.fxml; // Exports the class inheriting from javafx.application.Application
  }