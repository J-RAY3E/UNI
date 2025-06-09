package org.example.Classes;

import java.io.Serializable;

/**
 * Represents a coordinate with X and Y values.
 */
public final class Coordinates implements Serializable {
    private static final long serialVersionUID = 1L;
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

    public float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("Coordinates: [x=%d, y=%f]", x, y);
    }

    public String toCommandString() {
        return ":coords:" + (x != null ? x : "N/A") + "_" + (y != null ? y : "N/A");
    }

}
