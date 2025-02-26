package org.example.Enums;

public enum Position {
    HEAD_OF_DEPARTMENT,
    DEVELOPER,
    COOK,
    MANAGER_OF_CLEANING;

    public static Position getFromString(String string){

        for(Position position: Position.values()){
            if(position.name().equals(string.toUpperCase())){
                return position;
            }
        }
        return null;
    }
}