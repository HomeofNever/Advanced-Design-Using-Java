package com.luox6.battleship.model;

public interface Directable {
    public static enum Direction {VERTICAL, HORIZONTAL};

    Direction getDirection();
}
