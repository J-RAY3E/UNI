package org.example.Classes;

import java.io.Serializable;

/**
 * Represents an organization with basic details.
 */
public final class Organization implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fullName;
    private int annualTurnover;
    private Integer employeesCount;
    private Address postalAddress;

    /**
     * Default constructor.
     */
    public Organization() {
    }

    /**
     * Constructor specifying organization details.
     *
     * @param fullName       The full name of the organization.
     * @param annualTurnover The annual turnover of the organization.
     * @param employeesCount The number of employees.
     * @param postalAddress  The postal address of the organization.
     */
    public Organization(String fullName, int annualTurnover, Integer employeesCount, Address postalAddress) {
        this.fullName = fullName;
        this.annualTurnover = annualTurnover;
        this.employeesCount = employeesCount;
        this.postalAddress = postalAddress;
    }

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
        return String.format("Full Name: %s, Annual Turnover: %d, Employees Count: %d, Address: %s",
                fullName, annualTurnover, employeesCount, postalAddress);
    }

    public String toCommandString() {
        StringBuilder sb = new StringBuilder();
        sb.append(":organization:");
        sb.append(fullName != null ? fullName.replace(" ", "_") : "N/A");
        sb.append("$").append(annualTurnover);
        sb.append("$").append(employeesCount != null ? employeesCount : "N/A");

        if (postalAddress != null) {
            sb.append(" ").append(postalAddress.toCommandString());
        } else {
            sb.append(" :null:N/A");
        }
        return sb.toString();
    }

}
