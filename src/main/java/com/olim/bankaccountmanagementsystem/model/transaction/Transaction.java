package com.olim.bankaccountmanagementsystem.model.transaction;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a bank transaction
 * This class is used as a node in a linked list for transaction history
 */
public class Transaction {
    private String id;
    private double amount;
    private LocalDateTime date;
    private TransactionType type;
    private String description;
    private Transaction next; // Reference to the next transaction in the linked list
    
    /**
     * Constructor for a new transaction
     * 
     * @param amount The transaction amount
     * @param type The type of transaction
     * @param description A description of the transaction
     */
    public Transaction(double amount, TransactionType type, String description) {
        this.id = UUID.randomUUID().toString(); // Generate a unique ID
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.type = type;
        this.description = description;
        this.next = null;
    }
    
    /**
     * Constructor with custom ID for testing purposes
     */
    public Transaction(String id, double amount, TransactionType type, String description) {
        this.id = id;
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.type = type;
        this.description = description;
        this.next = null;
    }
    
    // Getters
    public String getId() {
        return id;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public LocalDateTime getDate() {
        return date;
    }
    
    public TransactionType getType() {
        return type;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Transaction getNext() {
        return next;
    }
    
    // Setter for next - only used within the linked list implementation
    public void setNext(Transaction next) {
        this.next = next;
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s: $%.2f - %s", 
                date.toString(), 
                type.getDescription(), 
                amount, 
                description);
    }
}