package org.example.Classes;


import java.io.Serializable;

public final class Address implements Serializable {
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
}