package org.example.classes;



public final class Address {
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