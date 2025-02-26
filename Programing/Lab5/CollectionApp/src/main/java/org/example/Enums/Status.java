package org.example.Enums;

public enum Status {
    FIRED,
    HIRED,
    RECOMMENDED_FOR_PROMOTION,
    REGULAR,
    PROBATION;

    public static Status getFromString(String string){

        for(Status status: Status.values()){
            if(status.name().equals(string.toUpperCase())){
                return status;
            }
        }
        return null;
    }
}