package org.example.Enums;

public enum PrivilegeLevel {
    CLIENT,
    ADMIN,
    SCRIPT;

    public boolean allows(String userPrivilege) {
        if (userPrivilege == null) {

            userPrivilege = "CLIENT";
        }
        return this.name().equalsIgnoreCase(userPrivilege);
    }

}