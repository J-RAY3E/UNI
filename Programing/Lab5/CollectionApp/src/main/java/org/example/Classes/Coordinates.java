package org.example.Classes;

public class Coordinates {
    private Integer x;
    private Float y;

    public Coordinates() {
    }
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