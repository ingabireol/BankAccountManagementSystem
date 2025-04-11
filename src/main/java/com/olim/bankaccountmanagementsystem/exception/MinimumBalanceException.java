package com.olim.bankaccountmanagementsystem.exception;

/**
 * Exception thrown when a withdrawal would result in balance below minimum
 * This demonstrates custom exceptions in Java
 */
public class MinimumBalanceException extends Exception {
    private static final long serialVersionUID = 1L;
    private String accountNumber;
    private double requestedAmount;
    private double availableBalance;
    private double minimumBalance;
    
    /**
     * Constructor for a minimum balance exception
     * 
     * @param accountNumber The account number
     * @param requestedAmount The requested withdrawal amount
     * @param availableBalance The available balance
     * @param minimumBalance The minimum balance requirement
     */
    public MinimumBalanceException(String accountNumber, double requestedAmount, 
                                 double availableBalance, double minimumBalance) {
        super(String.format("Withdrawal of $%.2f from account %s would result in balance $%.2f below minimum $%.2f", 
                requestedAmount, accountNumber, availableBalance - requestedAmount, minimumBalance));
        this.accountNumber = accountNumber;
        this.requestedAmount = requestedAmount;
        this.availableBalance = availableBalance;
        this.minimumBalance = minimumBalance;
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
    
    public double getMinimumBalance() {
        return minimumBalance;
    }
}