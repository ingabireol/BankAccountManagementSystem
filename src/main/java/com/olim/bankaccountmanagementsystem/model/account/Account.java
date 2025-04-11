package com.olim.bankaccountmanagementsystem.model.account;

import com.olim.bankaccountmanagementsystem.model.Person;
import com.olim.bankaccountmanagementsystem.model.transaction.Transaction;
import com.olim.bankaccountmanagementsystem.model.transaction.TransactionHistory;
import com.olim.bankaccountmanagementsystem.model.transaction.TransactionType;

import java.time.LocalDate;

/**
 * Abstract base class for all account types
 * This demonstrates abstract classes in Java OOP
 */
public abstract class Account implements IBankOperations {
    // Protected fields accessible to subclasses
    protected String accountNumber;
    protected double balance;
    protected LocalDate dateCreated;
    protected Person owner;
    protected TransactionHistory transactions;
    
    /**
     * Constructor for a new account
     * This demonstrates constructor chaining and the this keyword
     * 
     * @param accountNumber The account number
     * @param initialBalance The initial balance
     * @param owner The account owner
     */
    public Account(String accountNumber, double initialBalance, Person owner) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.owner = owner;
        this.dateCreated = LocalDate.now();
        this.transactions = new TransactionHistory();
        
        // Record the initial deposit
        if (initialBalance > 0) {
            addTransaction(initialBalance, TransactionType.DEPOSIT, "Initial deposit");
        }
    }
    
    // Common getters for all account types
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public LocalDate getDateCreated() {
        return dateCreated;
    }
    
    public Person getOwner() {
        return owner;
    }
    
    @Override
    public TransactionHistory getTransactionHistory() {
        return transactions;
    }
    
    /**
     * Add a transaction to the history
     * Protected method used by subclasses to record transactions
     * 
     * @param amount The transaction amount
     * @param type The type of transaction
     * @param description A description of the transaction
     */
    protected void addTransaction(double amount, TransactionType type, String description) {
        Transaction transaction = new Transaction(amount, type, description);
        transactions.addTransaction(transaction);
    }
    
    /**
     * Abstract methods to be implemented by subclasses
     * This demonstrates polymorphism in Java OOP
     */
    @Override
    public abstract boolean deposit(double amount);
    
    @Override
    public abstract boolean withdraw(double amount);
    
    @Override
    public String toString() {
        return String.format("%s[accountNumber=%s, balance=%.2f, owner=%s]", 
                this.getClass().getSimpleName(),
                accountNumber, 
                balance, 
                owner.getName());
    }
}