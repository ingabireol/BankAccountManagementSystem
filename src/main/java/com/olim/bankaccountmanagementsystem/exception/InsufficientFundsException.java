package com.olim.bankaccountmanagementsystem.exception;

/**
 * Exception thrown when a withdrawal would result in insufficient funds
 * This demonstrates custom exceptions in Java
 */
public class InsufficientFundsException extends Exception {
    private static final long serialVersionUID = 1L;
    private String accountNumber;
    private double requestedAmount;
    private double availableBalance;
    
    /**
     * Constructor for an insufficient funds exception
     * 
     * @param accountNumber The account number
     * @param requestedAmount The requested withdrawal amount
     * @param availableBalance The available balance
     */
    public InsufficientFundsException(String accountNumber, double requestedAmount, double availableBalance) {
        super(String.format("Insufficient funds in account %s: Requested: $%.2f, Available: $%.2f", 
                accountNumber, requestedAmount, availableBalance));
        this.accountNumber = accountNumber;
        this.requestedAmount = requestedAmount;
        this.availableBalance = availableBalance;
    }
    
    // Getters
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public double getRequestedAmount() {
        return requestedAmount;
    }
    
    public double getAvailableBalance() {
        return availableBalance;
    }
}