package com.olim.bankaccountmanagementsystem.model.account;

import java.time.LocalDate;

import com.olim.bankaccountmanagementsystem.model.Person;
import com.olim.bankaccountmanagementsystem.model.transaction.TransactionType;

/**
 * Represents a fixed deposit account
 * This demonstrates inheritance and method overriding in Java OOP
 */
public class FixedDepositAccount extends Account {
    private double depositAmount;
    private double interestRate;
    private LocalDate maturityDate;
    
    /**
     * Constructor for a new fixed deposit account
     * 
     * @param accountNumber The account number
     * @param depositAmount The fixed deposit amount
     * @param owner The account owner
     * @param termInMonths The term in months until maturity
     * @param interestRate The annual interest rate (e.g., 0.07 for 7%)
     */
    public FixedDepositAccount(String accountNumber, double depositAmount, Person owner,
                              int termInMonths, double interestRate) {
        super(accountNumber, depositAmount, owner);
        this.depositAmount = depositAmount;
        this.interestRate = interestRate;
        this.maturityDate = LocalDate.now().plusMonths(termInMonths);
    }
    
    /**
     * Deposit money into the account
     * Fixed deposit accounts do not allow additional deposits
     * 
     * @param amount The amount to deposit
     * @return false as additional deposits are not allowed
     */
    @Override
    public boolean deposit(double amount) {
        // Fixed deposit accounts do not allow additional deposits
        return false;
    }
    
    /**
     * Withdraw money from the account
     * Fixed deposit accounts only allow withdrawals after maturity
     * 
     * @param amount The amount to withdraw
     * @return true if the withdrawal was successful, false otherwise
     */
    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            return false;
        }
        
        // Check if the account has matured
        if (!isMatured()) {
            return false; // Cannot withdraw before maturity
        }
        
        // Allow withdrawal of the entire balance only
        if (amount != balance) {
            return false; // Can only withdraw the full amount
        }
        
        balance -= amount;
        addTransaction(amount, TransactionType.WITHDRAWAL, "Withdrawal at maturity");
        return true;
    }
    
    /**
     * Check if the fixed deposit has matured
     * 
     * @return true if the account has matured, false otherwise
     */
    public boolean isMatured() {
        return LocalDate.now().isEqual(maturityDate) || LocalDate.now().isAfter(maturityDate);
    }
    
    /**
     * Calculate the maturity amount (principal + interest)
     * This is a simplified calculation
     * 
     * @return The maturity amount
     */
    public double calculateMaturityAmount() {
        // Simple interest calculation for demonstration
        // In a real system, this would use compound interest formulas
        double termInYears = LocalDate.now().until(maturityDate).toTotalMonths() / 12.0;
        double interest = depositAmount * interestRate * termInYears;
        return depositAmount + interest;
    }
    
    // Getters for fixed deposit-specific properties
    public LocalDate getMaturityDate() {
        return maturityDate;
    }
    
    public double getInterestRate() {
        return interestRate;
    }
    
    public double getDepositAmount() {
        return depositAmount;
    }
}