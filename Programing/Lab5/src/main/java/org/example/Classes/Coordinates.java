package org.example.classes;

/**
 * Represents a coordinate with X and Y values.
 */
public final class Coordinates {

    private Integer x;
    private Float y;

    /**
     * Default constructor.
     */
    public Coordinates() {
    }

    /**
     * Constructor specifying x and y values.
     *
     * @param x The X coordinate.
     * @param y The Y coordinate.
     */
    public Coordinates(int x, Float y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("Coordinates: [x=%d, y=%f]", x, y);
    }
}
