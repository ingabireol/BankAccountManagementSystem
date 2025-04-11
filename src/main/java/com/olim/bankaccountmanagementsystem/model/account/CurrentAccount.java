package com.olim.bankaccountmanagementsystem.model.account;

import com.olim.bankaccountmanagementsystem.model.Person;
import com.olim.bankaccountmanagementsystem.model.transaction.TransactionType;

/**
 * Represents a current account
 * This demonstrates inheritance and method overriding in Java OOP
 */
public class CurrentAccount extends Account {
    private double overdraftLimit;
    
    /**
     * Constructor for a new current account
     * 
     * @param accountNumber The account number
     * @param initialBalance The initial balance
     * @param owner The account owner
     * @param overdraftLimit The maximum allowed overdraft
     */
    public CurrentAccount(String accountNumber, double initialBalance, Person owner, double overdraftLimit) {
        super(accountNumber, initialBalance, owner);
        this.overdraftLimit = overdraftLimit;
    }
    
    /**
     * Deposit money into the account
     * This overrides the abstract method from Account
     *
     * @param amount The amount to deposit
     * @return true if the deposit was successful, false otherwise
     */
    @Override
    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        }
        
        balance += amount;
        addTransaction(amount, TransactionType.DEPOSIT, "Deposit");
        return true;
    }
    
    /**
     * Withdraw money from the account
     * This overrides the abstract method from Account
     * Current accounts allow overdrafts up to the overdraft limit
     * 
     * @param amount The amount to withdraw
     * @return true if the withdrawal was successful, false otherwise
     */
    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            return false;
        }
        
        // Check if withdrawal would exceed overdraft limit
        if (balance - amount < -overdraftLimit) {
            return false; // Exceeds overdraft limit
        }
        
        balance -= amount;
        addTransaction(amount, TransactionType.WITHDRAWAL, "Withdrawal");
        
        // If this withdrawal resulted in an overdraft, we could apply a fee
        if (balance < 0) {
            applyOverdraftFee();
        }
        
        return true;
    }
    
    /**
     * Apply an overdraft fee
     * This is called when an account goes into overdraft
     */
    private void applyOverdraftFee() {
        double fee = 5.0; // Example fixed fee
        balance -= fee;
        addTransaction(fee, TransactionType.FEE_CHARGED, "Overdraft fee");
    }
    // Getters and setters for current-specific properties
    public double getOverdraftLimit() {
        return overdraftLimit;
    }
    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }
}