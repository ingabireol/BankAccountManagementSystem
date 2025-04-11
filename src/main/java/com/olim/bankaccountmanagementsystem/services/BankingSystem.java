package com.olim.bankaccountmanagementsystem.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.olim.bankaccountmanagementsystem.model.Person;
import com.olim.bankaccountmanagementsystem.model.account.Account;
import com.olim.bankaccountmanagementsystem.model.account.CurrentAccount;
import com.olim.bankaccountmanagementsystem.model.account.FixedDepositAccount;
import com.olim.bankaccountmanagementsystem.model.account.SavingsAccount;

/**
 * Service class to manage all banking operations
 * This demonstrates the use of collections and service layers in OOP
 */
public class BankingSystem {
    private List<Account> accounts;
    
    /**
     * Constructor for a new banking system
     */
    public BankingSystem() {
        this.accounts = new ArrayList<>();
    }
    
    /**
     * Create a new savings account
     * This demonstrates method overloading
     * 
     * @param owner The account owner
     * @param initialBalance The initial balance
     * @param minBalance The minimum balance requirement
     * @param interestRate The interest rate
     * @return The created savings account
     */
    public SavingsAccount createSavingsAccount(Person owner, double initialBalance,
                                               double minBalance, double interestRate) {
        String accountNumber = generateAccountNumber("SAV");
        SavingsAccount account = new SavingsAccount(accountNumber, initialBalance, owner, minBalance, interestRate);
        accounts.add(account);
        return account;
    }
    
    /**
     * Create a new current account
     * 
     * @param owner The account owner
     * @param initialBalance The initial balance
     * @param overdraftLimit The overdraft limit
     * @return The created current account
     */
    public CurrentAccount createCurrentAccount(Person owner, double initialBalance, double overdraftLimit) {
        String accountNumber = generateAccountNumber("CUR");
        CurrentAccount account = new CurrentAccount(accountNumber, initialBalance, owner, overdraftLimit);
        accounts.add(account);
        return account;
    }
    
    /**
     * Create a new fixed deposit account
     * 
     * @param owner The account owner
     * @param depositAmount The deposit amount
     * @param termInMonths The term in months
     * @param interestRate The interest rate
     * @return The created fixed deposit account
     */
    public FixedDepositAccount createFixedDepositAccount(Person owner, double depositAmount,
                                                         int termInMonths, double interestRate) {
        String accountNumber = generateAccountNumber("FIX");
        FixedDepositAccount account = new FixedDepositAccount(accountNumber, depositAmount, owner, 
                                                            termInMonths, interestRate);
        accounts.add(account);
        return account;
    }
    
    /**
     * Find an account by its account number
     * 
     * @param accountNumber The account number to search for
     * @return The account if found, null otherwise
     */
    public Account findAccountByNumber(String accountNumber) {
        return accounts.stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Get all accounts in the system
     * 
     * @return A list of all accounts
     */
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts);
    }
    
    /**
     * Get all accounts owned by a specific person
     * 
     * @param owner The person whose accounts to retrieve
     * @return A list of accounts owned by the person
     */
    public List<Account> getAccountsByOwner(Person owner) {
        return accounts.stream()
                .filter(account -> account.getOwner().getId().equals(owner.getId()))
                .collect(Collectors.toList());
    }
    
    /**
     * Generate a unique account number
     * 
     * @param prefix The account type prefix
     * @return A unique account number
     */
    private String generateAccountNumber(String prefix) {
        // Generate a 6-digit random number
        String randomPart = String.format("%06d", (int)(Math.random() * 1000000));
        return prefix + "-" + randomPart;
    }
    
    /**
     * Apply interest to all savings accounts
     * This would typically be run on a scheduled basis
     */
    public void applyInterestToSavingsAccounts() {
        accounts.stream()
                .filter(account -> account instanceof SavingsAccount)
                .map(account -> (SavingsAccount) account)
                .forEach(SavingsAccount::applyInterest);
    }
}