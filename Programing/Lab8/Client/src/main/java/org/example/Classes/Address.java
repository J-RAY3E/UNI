package org.example.Classes;


import java.io.Serializable;

public final class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    private String zipCode; 

    public Address(String zipCode) {
        this.zipCode = zipCode;
    }

    public Address() {
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    public String getZipCode() {
        return zipCode;
    }

    @Override
    public String toString() {
        return zipCode;
    }

    public String toCommandString() {
        return ":address:" + (zipCode != null ? zipCode.replace(" ", "_") : "N/A");
    }

}