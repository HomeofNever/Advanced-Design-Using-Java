package com.luox6.battleship.test;

import com.luox6.battleship.model.Coordinate;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {

    @org.junit.jupiter.api.Test
    void getX() {
        Coordinate c = new Coordinate(1, 2);
        assertEquals(2, c.getY());
        assertEquals(1, c.getX());
    }

    @org.junit.jupiter.api.Test
    void testEquals() {
        Coordinate a1 = new Coordinate(1, 1);
        Coordinate a2 = new Coordinate(2, 1);
        Coordinate a3 = new Coordinate(1, 2);
        Coordinate a4 = new Coordinate(1, 1);
        assertNotEquals(a2, a1);
        assertNotEquals(a3, a2);
        assertNotEquals(a4, a3);
        assertEquals(a1, a4);
    }

    @org.junit.jupiter.api.Test
    void valueOf() {
        Coordinate x = Coordinate.valueOf("[1 1]");
        assertEquals(x, new Coordinate(1, 1));
    }
}