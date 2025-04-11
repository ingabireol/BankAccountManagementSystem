package com.olim.bankaccountmanagementsystem.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Utility class for generating unique account numbers
 * This demonstrates utility classes and static methods in Java
 */
public class AccountNumberGenerator {
    // Private constructor to prevent instantiation
    private AccountNumberGenerator() {
        throw new AssertionError("Utility class should not be instantiated");
    }
    
    /**
     * Generate a unique account number for a specific account type
     * 
     * @param accountType The account type code (e.g., "SAV", "CUR", "FIX")
     * @return A unique account number
     */
    public static String generateAccountNumber(String accountType) {
        // Format: XXX-YYYYMMDD-ZZZZZZ where:
        // XXX is the account type code
        // YYYYMMDD is the current date
        // ZZZZZZ is a random 6-digit number
        
        java.time.LocalDate now = java.time.LocalDate.now();
        String datePart = String.format("%04d%02d%02d", now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        
        // Generate a random 6-digit number
        int randomNum = ThreadLocalRandom.current().nextInt(100000, 1000000);
        
        return String.format("%s-%s-%06d", accountType, datePart, randomNum);
    }
}