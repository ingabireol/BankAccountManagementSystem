package com.olim.bankaccountmanagementsystem.model.transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Maintains a linked list of transactions for an account
 * This demonstrates linked list implementation in Java
 */
public class TransactionHistory {
    private Transaction head; // Head of the linked list
    private int count; // Number of transactions
    
    /**
     * Constructor for a new empty transaction history
     */
    public TransactionHistory() {
        this.head = null;
        this.count = 0;
    }
    
    /**
     * Add a new transaction to the history (at the beginning of the list)
     * This demonstrates insertion in a linked list
     * 
     * @param transaction The transaction to add
     */
    public void addTransaction(Transaction transaction) {
        if (transaction == null) {
            return;
        }
        
        // Insert at the beginning (most recent first)
        if (head != null) {
            transaction.setNext(head);
        }
        head = transaction;

        count++;
    }
    
    /**
     * Get the last N transactions (most recent first)
     * This demonstrates traversal of a linked list
     * 
     * @param n The number of transactions to retrieve
     * @return A list of the last n transactions
     */
    public List<Transaction> getLastNTransactions(int n) {
        List<Transaction> result = new ArrayList<>();
        Transaction current = head;
        int i = 0;
        
        while (current != null && i < n) {
            result.add(current);
            current = current.getNext();
            i++;
        }
        return result;
    }
    
    /**
     * Get all transactions in the history
     * 
     * @return A list of all transactions
     */
    public List<Transaction> getAllTransactions() {
        return getLastNTransactions(count);
    }
    
    /**
     * Get the total number of transactions in the history
     * 
     * @return The number of transactions
     */
    public int getCount() {
        return count;
    }
    
    /**
     * Check if the history is empty
     * 
     * @return true if there are no transactions, false otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }
}