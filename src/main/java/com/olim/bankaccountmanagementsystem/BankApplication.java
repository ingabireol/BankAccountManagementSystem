package com.olim.bankaccountmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main application class for the Bank Account Management System
 */
public class BankApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the login view as the starting point
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/olim/bankaccountmanagementsystem/login-view.fxml"));
            Parent root = loader.load();

            // Set up the primary stage
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Bank Account Management System - Login");
            primaryStage.setResizable(true);
            primaryStage.show();
        } catch (IOException e) {
            // Display error alert
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Application Error");
            alert.setHeaderText("Error Loading Application");
            alert.setContentText("Failed to load the application: " + e.getMessage() +
                    "\n\nResource path: " + getClass().getResource("/com/olim/bankaccountmanagementsystem/login-view.fxml"));

            // Print stack trace for debugging
            e.printStackTrace();

            alert.showAndWait();
        }
    }

    /**
     * Main method to launch the application
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}