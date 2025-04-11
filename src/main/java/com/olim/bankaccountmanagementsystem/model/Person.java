package com.olim.bankaccountmanagementsystem.model;

import java.util.UUID;

/**
 * Represents a bank customer
 */
public class Person {
    private String id;
    private String name;
    private String contactInfo; // Could be email, phone, etc.
    
    /**
     * Constructor for a new person with automatically generated ID
     * 
     * @param name The person's name
     * @param contactInfo The person's contact information
     */
    public Person(String name, String contactInfo) {
        this.id = UUID.randomUUID().toString(); // Generate a unique ID
        this.name = name;
        this.contactInfo = contactInfo;
    }
    
    /**
     * Constructor with explicit ID (useful for testing or data imports)
     * 
     * @param id The person's ID
     * @param name The person's name
     * @param contactInfo The person's contact information
     */
    public Person(String id, String name, String contactInfo) {
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
    }
    
    // Getters and setters
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getContactInfo() {
        return contactInfo;
    }
    
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
    
    @Override
    public String toString() {
        return String.format("Person[id=%s, name=%s, contactInfo=%s]", id, name, contactInfo);
    }
}