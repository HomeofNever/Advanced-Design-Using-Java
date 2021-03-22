package com.luox6.battleship.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    /**
     * The distance include two endpoints
     * @param c
     * @return
     */
    public int distance(Coordinate c) {
        if (c.getX() == getX()) {
            return Math.abs(c.getY() - getY()) + 1;
        }
        return Math.abs(c.getX() - getX()) + 1;
    }

    /**
     *
     * @param c The end coordinates
     * @return A list of coordinates that will
     */
    protected List<Coordinate> getSequence(Coordinate c) {
        ArrayList<Coordinate> res = new ArrayList<>();
        if (c.getX() == getX()) {
            for (int i = getY(); i <= c.getY(); i++) {
                res.add(new Coordinate(getX(), i));
            }
        } else {
            for (int i = getX(); i <= c.getX(); i++) {
                res.add(new Coordinate(i, getY()));
            }
        }

        return res;
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

    public static Coordinate valueOf(String s) {
        String[] nums = s.substring(s.indexOf('[') + 1, s.indexOf(']')).split(" ");
        int row = Integer.parseInt(nums[0]);
        int col = Integer.parseInt(nums[1]);
        return new Coordinate(row, col);
    }
}
