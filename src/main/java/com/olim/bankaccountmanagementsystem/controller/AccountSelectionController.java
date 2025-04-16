package com.olim.bankaccountmanagementsystem.controller;

import com.olim.bankaccountmanagementsystem.model.Person;
import com.olim.bankaccountmanagementsystem.model.account.Account;
import com.olim.bankaccountmanagementsystem.model.account.CurrentAccount;
import com.olim.bankaccountmanagementsystem.model.account.FixedDepositAccount;
import com.olim.bankaccountmanagementsystem.model.account.SavingsAccount;
import com.olim.bankaccountmanagementsystem.services.BankingSystem;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Controller for the account selection view
 * Handles account access and creation
 */
public class AccountSelectionController {
    // Banking system service
    private final BankingSystem bankingSystem = new BankingSystem();

    // UI components
    @FXML
    private TextField accountNumberField;

    @FXML
    private TextField customerNameField;

    @FXML
    private TextField contactInfoField;

    @FXML
    private ComboBox<String> accountTypeComboBox;

    @FXML
    private TextField initialBalanceField;

    @FXML
    private VBox savingsOptions;

    @FXML
    private TextField minBalanceField;

    @FXML
    private TextField savingsInterestRateField;

    @FXML
    private VBox currentOptions;

    @FXML
    private TextField overdraftLimitField;

    @FXML
    private VBox fixedDepositOptions;

    @FXML
    private TextField termField;

    @FXML
    private TextField fixedInterestRateField;

    @FXML
    private Label statusLabel;

    /**
     * Initialize the controller
     */
    @FXML
    public void initialize() {
        try {
            // Setup account type dropdown with null check
            if (accountTypeComboBox != null) {
                accountTypeComboBox.setItems(FXCollections.observableArrayList(
                        "Savings Account", "Current Account", "Fixed Deposit Account"));

                // Show/hide specific account options based on selection
                accountTypeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (savingsOptions != null && currentOptions != null && fixedDepositOptions != null) {
                        savingsOptions.setVisible("Savings Account".equals(newValue));
                        currentOptions.setVisible("Current Account".equals(newValue));
                        fixedDepositOptions.setVisible("Fixed Deposit Account".equals(newValue));
                    }
                });

                // Select the default option
                accountTypeComboBox.getSelectionModel().selectFirst();
            } else {
                System.err.println("Warning: accountTypeComboBox is null during initialization");
            }

            // Set initial visibility with null checks
            if (savingsOptions != null) savingsOptions.setVisible(true);
            if (currentOptions != null) currentOptions.setVisible(false);
            if (fixedDepositOptions != null) fixedDepositOptions.setVisible(false);

            // Clear status message
            if (statusLabel != null) {
                statusLabel.setText("");
            }
        } catch (Exception e) {
            System.err.println("Error initializing AccountSelectionController: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handle access account button click
     * Validates the account number and navigates to the account operations view if valid
     */
    @FXML
    public void handleAccessAccount() {
        if (accountNumberField == null || statusLabel == null) {
            System.err.println("Error: UI components not initialized in handleAccessAccount");
            return;
        }

        String accountNumber = accountNumberField.getText().trim();

        if (accountNumber.isEmpty()) {
            statusLabel.setText("Please enter an account number");
            return;
        }

        Account account = bankingSystem.findAccountByNumber(accountNumber);

        if (account == null) {
            statusLabel.setText("Account not found: " + accountNumber);
            return;
        }

        navigateToAccountOperations(account);
    }

    /**
     * Handle create account button click
     * Creates a new account based on the selected type and input fields
     */
    @FXML
    public void handleCreateAccount() {
        // Check for null UI components
        if (customerNameField == null || contactInfoField == null || initialBalanceField == null ||
                accountTypeComboBox == null || statusLabel == null) {
            System.err.println("Error: UI components not initialized in handleCreateAccount");
            return;
        }

        // Validate required fields
        if (customerNameField.getText().isEmpty() || contactInfoField.getText().isEmpty() ||
                initialBalanceField.getText().isEmpty() || accountTypeComboBox.getValue() == null) {
            statusLabel.setText("Please fill all required fields");
            return;
        }

        try {
            // Parse the initial balance
            double initialBalance = Double.parseDouble(initialBalanceField.getText());

            if (initialBalance < 0) {
                statusLabel.setText("Initial balance cannot be negative");
                return;
            }

            // Create a person object
            Person owner = new Person(customerNameField.getText(), contactInfoField.getText());

            // Create the account based on type
            Account newAccount = null;
            String accountType = accountTypeComboBox.getValue();

            if ("Savings Account".equals(accountType)) {
                if (minBalanceField == null || savingsInterestRateField == null) {
                    statusLabel.setText("Error: Savings account fields not found");
                    return;
                }

                if (minBalanceField.getText().isEmpty() || savingsInterestRateField.getText().isEmpty()) {
                    statusLabel.setText("Please fill all savings account fields");
                    return;
                }

                double minBalance = Double.parseDouble(minBalanceField.getText());
                double interestRate = Double.parseDouble(savingsInterestRateField.getText()) / 100; // Convert from percentage

                newAccount = bankingSystem.createSavingsAccount(owner, initialBalance, minBalance, interestRate);
            } else if ("Current Account".equals(accountType)) {
                if (overdraftLimitField == null) {
                    statusLabel.setText("Error: Overdraft limit field not found");
                    return;
                }

                if (overdraftLimitField.getText().isEmpty()) {
                    statusLabel.setText("Please enter overdraft limit");
                    return;
                }

                double overdraftLimit = Double.parseDouble(overdraftLimitField.getText());
                newAccount = bankingSystem.createCurrentAccount(owner, initialBalance, overdraftLimit);
            } else if ("Fixed Deposit Account".equals(accountType)) {
                if (termField == null || fixedInterestRateField == null) {
                    statusLabel.setText("Error: Fixed deposit fields not found");
                    return;
                }

                if (termField.getText().isEmpty() || fixedInterestRateField.getText().isEmpty()) {
                    statusLabel.setText("Please fill all fixed deposit fields");
                    return;
                }

                int term = Integer.parseInt(termField.getText());
                double interestRate = Double.parseDouble(fixedInterestRateField.getText()) / 100; // Convert from percentage

                newAccount = bankingSystem.createFixedDepositAccount(owner, initialBalance, term, interestRate);
            }

            if (newAccount != null) {
                statusLabel.setText("Account created successfully: " + newAccount.getAccountNumber());
                clearCreateAccountFields();
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid numeric input: " + e.getMessage());
        } catch (Exception e) {
            statusLabel.setText("Error creating account: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handle logout button click
     * Navigates back to the login view
     */
    @FXML
    public void handleLogout() {
        try {
            // Get a reference to any UI element to find the scene
            Control control = accountNumberField != null ? accountNumberField :
                    (customerNameField != null ? customerNameField : null);

            if (control == null) {
                System.err.println("Error: No UI controls initialized for scene access");
                return;
            }

            // Load the login view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/olim/bankaccountmanagementsystem/login-view.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) control.getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Bank Account Management - Login");
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading login screen: " + e.getMessage());
            e.printStackTrace();

            if (statusLabel != null) {
                statusLabel.setText("Error loading login screen: " + e.getMessage());
            }
        }
    }

    /**
     * Navigate to the account operations view
     *
     * @param account The account to operate on
     */
    private void navigateToAccountOperations(Account account) {
        try {
            // Get a reference to any UI element to find the scene
            Control control = accountNumberField != null ? accountNumberField :
                    (customerNameField != null ? customerNameField : null);

            if (control == null) {
                System.err.println("Error: No UI controls initialized for scene access");
                return;
            }

            // Load the account operations view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/olim/bankaccountmanagementsystem/account-operations-view.fxml"));
            Parent root = loader.load();

            // Get the controller
            AccountOperationsController controller = loader.getController();

            // Initialize the controller with the account
            controller.initializeAccount(account, bankingSystem);

            // Get the current stage
            Stage stage = (Stage) control.getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Bank Account Management - Account Operations");
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading account operations screen: " + e.getMessage());
            e.printStackTrace();

            if (statusLabel != null) {
                statusLabel.setText("Error loading account operations screen: " + e.getMessage());
            }
        }
    }

    /**
     * Clear all create account fields
     */
    private void clearCreateAccountFields() {
        if (customerNameField != null) customerNameField.clear();
        if (contactInfoField != null) contactInfoField.clear();
        if (initialBalanceField != null) initialBalanceField.clear();
        if (minBalanceField != null) minBalanceField.clear();
        if (savingsInterestRateField != null) savingsInterestRateField.clear();
        if (overdraftLimitField != null) overdraftLimitField.clear();
        if (termField != null) termField.clear();
        if (fixedInterestRateField != null) fixedInterestRateField.clear();
    }
}