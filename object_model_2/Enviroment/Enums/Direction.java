package Enviroment.Enums;

public enum Direction{
    ToSea("is returning to the sea"),
    ToCoast("is going to the coast");

    private final String description;

    Direction(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
}
}