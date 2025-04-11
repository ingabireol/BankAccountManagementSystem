package com.olim.bankaccountmanagementsystem.model.account;


import com.olim.bankaccountmanagementsystem.model.transaction.TransactionHistory;

/**
 * Interface defining the basic operations for all bank accounts
 * This demonstrates interface usage in Java OOP
 */
public interface IBankOperations {
    
    /**
     * Deposit money into the account
     * 
     * @param amount The amount to deposit
     * @return true if the deposit was successful, false otherwise
     */
    boolean deposit(double amount);
    
    /**
     * Withdraw money from the account
     * 
     * @param amount The amount to withdraw
     * @return true if the withdrawal was successful, false otherwise
     */
    boolean withdraw(double amount);
    
    /**
     * Get the current balance of the account
     * 
     * @return The current balance
     */
    double getBalance();
    
    /**
     * Get the transaction history of the account
     * 
     * @return The transaction history
     */
    TransactionHistory getTransactionHistory();
}