package org.example.Classes;


import java.io.Serial;
import java.io.Serializable;

/**
 * Represents an organization with basic details.
 */

public final class Organization implements Serializable {
    @Serial
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

    @Override
    public String toString() {
        boolean isEmpty = (fullName == null || fullName.isEmpty()) &&
                annualTurnover == 0 &&
                employeesCount == 0 &&
                (postalAddress == null || postalAddress.isEmpty());

        if (isEmpty) {
            return "N/A";
        }

        return String.format(
                "Full Name: %s%nAnnual Turnover: %d%nEmployees Count: %d%nAddress: %s",
                fullName != null ? fullName : "N/A",
                annualTurnover,
                employeesCount,
                (postalAddress != null && !postalAddress.isEmpty()) ? postalAddress : "N/A"
        );
    }


    public void setAnnualTurnover(int annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    public void setEmployeesCount(int employeesCount) {
        this.employeesCount = employeesCount;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPostalAddress(Address currentAddress) {
        this.postalAddress = currentAddress;
    }

    public String getFullName() {
        return this.fullName;
    }

    public Address getPostalAddress() {
        return  this.postalAddress;
    }

    public double getAnnualTurnover() {
        return this.annualTurnover;
    }

    public int getEmployeesCount() {
        return this.employeesCount;
    }
}
