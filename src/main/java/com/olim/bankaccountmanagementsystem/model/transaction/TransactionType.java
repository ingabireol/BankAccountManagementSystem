package com.olim.bankaccountmanagementsystem.model.transaction;

/**
 * Enum defining the types of transactions that can be performed
 * This demonstrates enum usage in Java
 */
public enum TransactionType {
    DEPOSIT("Deposit"),
    WITHDRAWAL("Withdrawal"),
    INTEREST_ADDED("Interest Added"),
    FEE_CHARGED("Fee Charged");
    
    private final String description;
    
    TransactionType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}