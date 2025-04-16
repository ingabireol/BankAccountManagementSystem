package com.olim.bankaccountmanagementsystem.controller;

import com.olim.bankaccountmanagementsystem.model.account.Account;
import com.olim.bankaccountmanagementsystem.model.account.CurrentAccount;
import com.olim.bankaccountmanagementsystem.model.account.FixedDepositAccount;
import com.olim.bankaccountmanagementsystem.model.account.SavingsAccount;
import com.olim.bankaccountmanagementsystem.model.transaction.Transaction;
import com.olim.bankaccountmanagementsystem.services.BankingSystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controller for the account operations view
 * Handles deposits, withdrawals, and transaction history display
 */
public class AccountOperationsController {
    // Account and banking system references
    private Account currentAccount;
    private BankingSystem bankingSystem;

    // Inner class for transaction display in the TableView
    public static class TransactionDisplay {
        private final String date;
        private final String type;
        private final double amount;
        private final String description;

        public TransactionDisplay(Transaction transaction) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            this.date = transaction.getDate().format(formatter);
            this.type = transaction.getType().getDescription();
            this.amount = transaction.getAmount();
            this.description = transaction.getDescription();
        }

        // Getters for PropertyValueFactory
        public String getDate() {
            return date;
        }

        public String getType() {
            return type;
        }

        public double getAmount() {
            return amount;
        }

        public String getDescription() {
            return description;
        }
    }

    // UI components
    @FXML
    private Label accountNumberLabel;

    @FXML
    private Label accountTypeLabel;

    @FXML
    private Label accountOwnerLabel;

    @FXML
    private Label balanceLabel;

    @FXML
    private Label additionalDetailsLabel;

    @FXML
    private Label additionalDetailsValueLabel;

    @FXML
    private TextField amountField;

    @FXML
    private TableView<TransactionDisplay> transactionTable;

    @FXML
    private TableColumn<TransactionDisplay, String> dateColumn;

    @FXML
    private TableColumn<TransactionDisplay, String> typeColumn;

    @FXML
    private TableColumn<TransactionDisplay, Double> amountColumn;

    @FXML
    private TableColumn<TransactionDisplay, String> descriptionColumn;

    @FXML
    private Label statusLabel;

    /**
     * Initialize the controller
     */
    @FXML
    public void initialize() {
        try {
            // Initialize the table columns with null checks
            if (dateColumn != null) dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            if (typeColumn != null) typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            if (amountColumn != null) amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
            if (descriptionColumn != null)
                descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

            // Clear status message
            if (statusLabel != null) {
                statusLabel.setText("");
            }
        } catch (Exception e) {
            System.err.println("Error initializing AccountOperationsController: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Initialize the account information
     * Called from the account selection controller
     *
     * @param account       The account to operate on
     * @param bankingSystem The banking system service
     */
    public void initializeAccount(Account account, BankingSystem bankingSystem) {
        try {
            if (account == null || bankingSystem == null) {
                System.err.println("Error: Null account or banking system provided to initializeAccount");
                return;
            }

            this.currentAccount = account;
            this.bankingSystem = bankingSystem;

            // Display account information with null checks
            if (accountNumberLabel != null) accountNumberLabel.setText(account.getAccountNumber());
            if (accountTypeLabel != null) accountTypeLabel.setText(account.getClass().getSimpleName());
            if (accountOwnerLabel != null) accountOwnerLabel.setText(account.getOwner().getName());

            updateBalanceDisplay();

            // Display account-specific details
            if (additionalDetailsLabel != null && additionalDetailsValueLabel != null) {
                if (account instanceof SavingsAccount) {
                    SavingsAccount savingsAccount = (SavingsAccount) account;
                    additionalDetailsLabel.setText("Minimum Balance / Interest Rate:");
                    additionalDetailsValueLabel.setText(String.format("$%.2f / %.2f%%",
                            savingsAccount.getMinBalance(), savingsAccount.getInterestRate() * 100));
                } else if (account instanceof CurrentAccount) {
                    CurrentAccount currentAccount = (CurrentAccount) account;
                    additionalDetailsLabel.setText("Overdraft Limit:");
                    additionalDetailsValueLabel.setText(String.format("$%.2f", currentAccount.getOverdraftLimit()));
                } else if (account instanceof FixedDepositAccount) {
                    FixedDepositAccount fixedAccount = (FixedDepositAccount) account;
                    additionalDetailsLabel.setText("Maturity Date / Interest Rate:");
                    additionalDetailsValueLabel.setText(String.format("%s / %.2f%%",
                            fixedAccount.getMaturityDate().toString(), fixedAccount.getInterestRate() * 100));
                } else {
                    additionalDetailsLabel.setText("");
                    additionalDetailsValueLabel.setText("");
                }
            }

            // Load transaction history
            loadTransactionHistory();
        } catch (Exception e) {
            System.err.println("Error in initializeAccount: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handle deposit button click
     */
    @FXML
    public void handleDeposit() {
        if (currentAccount == null || amountField == null || statusLabel == null) {
            System.err.println("Error: UI components or account not initialized in handleDeposit");
            return;
        }

        try {
            double amount = getAmountFromField();

            if (amount <= 0) {
                statusLabel.setText("Deposit amount must be positive");
                return;
            }

            boolean success = currentAccount.deposit(amount);

            if (success) {
                statusLabel.setText(String.format("Successfully deposited $%.2f", amount));
                updateBalanceDisplay();
                loadTransactionHistory();
                amountField.clear();
            } else {
                if (currentAccount instanceof FixedDepositAccount) {
                    statusLabel.setText("Cannot deposit to Fixed Deposit accounts after creation");
                } else {
                    statusLabel.setText("Deposit failed");
                }
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Please enter a valid amount");
        } catch (Exception e) {
            statusLabel.setText("Error processing deposit: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handle withdraw button click
     */
    @FXML
    public void handleWithdraw() {
        if (currentAccount == null || amountField == null || statusLabel == null) {
            System.err.println("Error: UI components or account not initialized in handleWithdraw");
            return;
        }

        try {
            double amount = getAmountFromField();

            if (amount <= 0) {
                statusLabel.setText("Withdrawal amount must be positive");
                return;
            }

            boolean success = currentAccount.withdraw(amount);

            if (success) {
                statusLabel.setText(String.format("Successfully withdrew $%.2f", amount));
                updateBalanceDisplay();
                loadTransactionHistory();
                amountField.clear();
            } else {
                if (currentAccount instanceof SavingsAccount) {
                    SavingsAccount savingsAccount = (SavingsAccount) currentAccount;
                    statusLabel.setText(String.format(
                            "Withdrawal failed: Cannot go below minimum balance of $%.2f",
                            savingsAccount.getMinBalance()));
                } else if (currentAccount instanceof CurrentAccount) {
                    CurrentAccount curAccount = (CurrentAccount) currentAccount;
                    statusLabel.setText(String.format(
                            "Withdrawal failed: Would exceed overdraft limit of $%.2f",
                            curAccount.getOverdraftLimit()));
                } else if (currentAccount instanceof FixedDepositAccount) {
                    FixedDepositAccount fixedAccount = (FixedDepositAccount) currentAccount;
                    if (!fixedAccount.isMatured()) {
                        statusLabel.setText(String.format(
                                "Withdrawal failed: Fixed deposit not yet matured (Maturity date: %s)",
                                fixedAccount.getMaturityDate()));
                    } else {
                        statusLabel.setText("Withdrawal failed: Can only withdraw full amount at maturity");
                    }
                } else {
                    statusLabel.setText("Withdrawal failed: Insufficient funds");
                }
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Please enter a valid amount");
        } catch (Exception e) {
            statusLabel.setText("Error processing withdrawal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handle back button click
     * Navigates back to the account selection view
     */
    @FXML
    public void handleBack() {
        try {
            // Get a control to access the scene
            Control control = amountField != null ? amountField :
                    (statusLabel != null ? statusLabel : null);

            if (control == null) {
                System.err.println("Error: No UI controls initialized for scene access");
                return;
            }

            // Load the account selection view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/olim/bankaccountmanagementsystem/account-selection-view.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) control.getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Bank Account Management - Account Selection");
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading account selection screen: " + e.getMessage());
            e.printStackTrace();

            if (statusLabel != null) {
                statusLabel.setText("Error loading account selection screen: " + e.getMessage());
            }
        }
    }

    /**
     * Handle logout button click
     * Navigates back to the login view
     */
    @FXML
    public void handleLogout() {
        try {
            // Get a control to access the scene
            Control control = amountField != null ? amountField :
                    (statusLabel != null ? statusLabel : null);

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
     * Update the balance display
     */
    private void updateBalanceDisplay() {
        if (balanceLabel != null && currentAccount != null) {
            balanceLabel.setText(String.format("$%.2f", currentAccount.getBalance()));
        }
    }

    /**
     * Get the amount from the amount field
     *
     * @return The amount as a double
     * @throws NumberFormatException if the amount is not a valid number
     */
    private double getAmountFromField() throws NumberFormatException {
        if (amountField == null) {
            throw new NumberFormatException("Amount field not initialized");
        }

        String amountText = amountField.getText().trim();
        if (amountText.isEmpty()) {
            throw new NumberFormatException("Amount cannot be empty");
        }
        return Double.parseDouble(amountText);
    }

    /**
     * Load the transaction history into the table view
     */
    private void loadTransactionHistory() {
        if (transactionTable == null || currentAccount == null) {
            System.err.println("Cannot load transaction history: table or account is null");
            return;
        }

        try {
            // Get transactions from the account
            List<Transaction> transactions = currentAccount.getTransactionHistory().getAllTransactions();

            // Convert to display objects
            ObservableList<TransactionDisplay> displayTransactions = FXCollections.observableArrayList();
            for (Transaction transaction : transactions) {
                displayTransactions.add(new TransactionDisplay(transaction));
            }

            // Update the table
            transactionTable.setItems(displayTransactions);
        } catch (Exception e) {
            System.err.println("Error loading transaction history: " + e.getMessage());
            e.printStackTrace();
        }
    }
}