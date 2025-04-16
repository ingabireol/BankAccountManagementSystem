package com.olim.bankaccountmanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the login view
 * Handles admin authentication with hardcoded credentials
 */
public class LoginController {
    // Hardcoded admin credentials
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "password";

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel;

    /**
     * Initialize the controller
     * This method is automatically called after the FXML is loaded
     */
    @FXML
    public void initialize() {
        // Initialize with empty error message
        // Check if errorLabel is null before using it
        if (errorLabel != null) {
            errorLabel.setText("");
        }
    }

    /**
     * Handle login button click
     * Validates the admin credentials and navigates to the account selection view if valid
     */
    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            if (errorLabel != null) {
                errorLabel.setText("Username and password are required");
            }
            return;
        }

        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            try {
                // Load the account selection view
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/olim/bankaccountmanagementsystem/account-selection-view.fxml"));
                Parent root = loader.load();

                // Get the current stage
                Stage stage = (Stage) loginButton.getScene().getWindow();

                // Set the new scene
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Bank Account Management - Account Selection");
                stage.show();
            } catch (IOException e) {
                if (errorLabel != null) {
                    errorLabel.setText("Error loading next screen: " + e.getMessage());
                }
                e.printStackTrace();
            }
        } else {
            if (errorLabel != null) {
                errorLabel.setText("Invalid username or password");
            }
            passwordField.clear();
        }
    }
}