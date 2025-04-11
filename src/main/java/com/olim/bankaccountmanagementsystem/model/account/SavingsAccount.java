package com.olim.bankaccountmanagementsystem.model.account;

import com.olim.bankaccountmanagementsystem.model.Person;
import com.olim.bankaccountmanagementsystem.model.transaction.TransactionType;

/**
 * Represents a savings account
 * This demonstrates inheritance and method overriding in Java OOP
 */
public class SavingsAccount extends Account {
    private double minBalance;
    private double interestRate;
    
    /**
     * Constructor for a new savings account
     * This demonstrates constructor chaining with super()
     * 
     * @param accountNumber The account number
     * @param initialBalance The initial balance
     * @param owner The account owner
     * @param minBalance The minimum allowed balance
     * @param interestRate The annual interest rate (e.g., 0.05 for 5%)
     */
    public SavingsAccount(String accountNumber, double initialBalance, Person owner,
                         double minBalance, double interestRate) {
        super(accountNumber, initialBalance, owner);
        this.minBalance = minBalance;
        this.interestRate = interestRate;
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
     * Savings accounts do not allow the balance to go below the minimum balance
     * 
     * @param amount The amount to withdraw
     * @return true if the withdrawal was successful, false otherwise
     */
    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            return false;
        }
        
        if (balance - amount < minBalance) {
            return false; // Cannot withdraw below minimum balance
        }
        
        balance -= amount;
        addTransaction(amount, TransactionType.WITHDRAWAL, "Withdrawal");
        return true;
    }
    
    /**
     * Calculate the interest for one period
     * 
     * @return The interest amount
     */
    public double calculateInterest() {
        return balance * interestRate;
    }
    
    /**
     * Apply the calculated interest to the account
     */
    public void applyInterest() {
        double interestAmount = calculateInterest();
        balance += interestAmount;
        addTransaction(interestAmount, TransactionType.INTEREST_ADDED, "Interest applied");
    }
    
    // Getters for savings-specific properties
    public double getMinBalance() {
        return minBalance;
    }
    
    public double getInterestRate() {
        return interestRate;
    }
}