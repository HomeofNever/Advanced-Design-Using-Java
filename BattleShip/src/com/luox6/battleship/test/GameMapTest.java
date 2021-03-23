package com.luox6.battleship.test;

import com.luox6.battleship.model.Cell;
import com.luox6.battleship.model.Coordinate;
import com.luox6.battleship.model.GameMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameMapTest {

    GameMap g;

    @BeforeEach
    void setUp() {
        g = new GameMap(5, 5);
    }

    @Test
    void getCellByCoordinate() {
        Cell c = g.getCellByCoordinate(new Coordinate(1, 1));
        Cell c2 = g.getCellByCoordinate(new Coordinate(-1, -1));
        assertEquals(1, c.getX());
        assertEquals(1, c.getY());
        assertNull(c2);
    }

    @Test
    void isValidCoordinate() {
        assertTrue(g.isValidCoordinate(new Coordinate(1, 1)));
        assertFalse(g.isValidCoordinate(new Coordinate(10, 10)));
    }

    @Test
    void mark() {
        assertFalse(g.getCellByCoordinate(new Coordinate(1, 1)).isDiscovered());
        g.mark(new Coordinate(1, 1));
        assertTrue(g.getCellByCoordinate(new Coordinate(1, 1)).isDiscovered());
    }

    @Test
    void allDown() {
        assertTrue(g.allDown());
    }
}