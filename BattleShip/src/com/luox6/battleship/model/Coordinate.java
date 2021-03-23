package com.luox6.battleship.model;

import java.util.Objects;

/**
 * A Generic 2D Coordinate
 * Implementation
 * @author Xinhao Luo
 * @version 0.0.1
 */
public class Coordinate {
    private int x = 0;
    private int y = 0;

    public Coordinate () {}

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate)) return false;
        Coordinate that = (Coordinate) o;
        return getX() == that.getX() && getY() == that.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    @Override
    public String toString() {
        return "[%d %d]".formatted(x, y);
    }

    /**
     * Parse String representation (toString()) of
     * Coordinate and generate Coordinate Object
     * @param s String Representation
     * @return
     */
    public static Coordinate valueOf(String s) {
        String[] nums = s.substring(s.indexOf('[') + 1, s.indexOf(']')).split(" ");
        int row = Integer.parseInt(nums[0]);
        int col = Integer.parseInt(nums[1]);
        return new Coordinate(row, col);
    }
}
