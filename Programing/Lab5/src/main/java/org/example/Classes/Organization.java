package org.example.Classes;

public class Organization {
    private String fullName;
    private int annualTurnover;
    private Integer employeesCount;
    private Address postalAddress;

    public Organization() {
    }

    // Constructor con campos
    public Organization(String fullName, int annualTurnover, Integer employeesCount, Address postalAddress) {
        this.fullName = fullName;
        this.annualTurnover = annualTurnover;
        this.employeesCount = employeesCount;
        this.postalAddress = postalAddress;
    }

    // Getters y setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAnnualTurnover() {
        return annualTurnover;
    }

    public void setAnnualTurnover(int annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    public Integer getEmployeesCount() {
        return employeesCount;
    }

    public void setEmployeesCount(Integer employeesCount) {
        this.employeesCount = employeesCount;
    }

    public Address getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(Address postalAddress) {
        this.postalAddress = postalAddress;
    }

    @Override
    public String toString() {
        return String.format("Full Name: %s AnnualTurnover: %d Employees Count: %d Address: %s",
                this.fullName, this.annualTurnover, this.employeesCount, this.postalAddress.toString());
    }
}