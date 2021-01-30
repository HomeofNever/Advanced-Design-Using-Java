package com.luox6.conway.test;

import com.luox6.conway.ConwayMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConwayMapTest {

    private ConwayMap c;

    @BeforeEach
    void setUp() {
        c = new ConwayMap(10, 10);
        c.setCell(0,0, ConwayMap.LIVE_CELL);
        c.setCell(1, 1, ConwayMap.LIVE_CELL);
    }

    @Test
    void getMapRowLength() {
        assertEquals(10, c.getMapRowLength());
    }

    @Test
    void getMapColLength() {
        assertEquals(10, c.getMapColLength());
    }

    @Test
    void isValidCoordinate() {
        assertTrue(c.isValidCoordinate(9, 9));
        assertFalse(c.isValidCoordinate(10, 10));
    }

    @Test
    void getCell() {
        assertEquals(ConwayMap.LIVE_CELL, c.getCell(0,0));
        assertEquals(ConwayMap.DEAD_CELL, c.getCell(9,9));
    }

    @Test
    void neighbours() {
        assertEquals( 1, c.neighbours(0,0));
        assertEquals(1, c.neighbours(9, 9));
        assertEquals( 0, c.neighbours(5, 5));
    }

    @Test
    void testToString() {
        assertEquals("""
                ConwayGameOfLife with row 10, col 10, current tick 0.
                Map:\s
                [1, 0, 0, 0, 0, 0, 0, 0, 0, 0]
                [0, 1, 0, 0, 0, 0, 0, 0, 0, 0]
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
                """, c.toString());
    }
}