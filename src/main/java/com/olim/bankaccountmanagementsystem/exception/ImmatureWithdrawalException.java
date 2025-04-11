package com.olim.bankaccountmanagementsystem.exception;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Exception thrown when attempting to withdraw from a fixed deposit before maturity
 * This demonstrates custom exceptions in Java
 */
public class ImmatureWithdrawalException extends Exception {
    private static final long serialVersionUID = 1L;
    private String accountNumber;
    private LocalDate maturityDate;
    private LocalDate currentDate;
    
    /**
     * Constructor for an immature withdrawal exception
     * 
     * @param accountNumber The account number
     * @param maturityDate The maturity date of the fixed deposit
     */
    public ImmatureWithdrawalException(String accountNumber, LocalDate maturityDate) {
        super(String.format("Cannot withdraw from fixed deposit account %s before maturity date %s", 
                accountNumber, maturityDate.format(DateTimeFormatter.ISO_LOCAL_DATE)));
        this.accountNumber = accountNumber;
        this.maturityDate = maturityDate;
        this.currentDate = LocalDate.now();
    }
    
    // Getters
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public LocalDate getMaturityDate() {
        return maturityDate;
    }
    
    public LocalDate getCurrentDate() {
        return currentDate;
    }
    
    /**
     * Get the number of days remaining until maturity
     * 
     * @return The number of days until maturity
     */
    public long getDaysUntilMaturity() {
        return currentDate.until(maturityDate).getDays();
    }
}