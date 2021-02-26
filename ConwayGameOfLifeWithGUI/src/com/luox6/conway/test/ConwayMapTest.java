package com.luox6.conway.test;

import com.luox6.conway.Cell;
import com.luox6.conway.ConwayMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConwayMapTest {

    private ConwayMap c;

    @BeforeEach
    void setUp() {
        c = new ConwayMap(10, 10);
        c.getCell(0,0).setStatus(Cell.LIVE_CELL);
        c.getCell(1, 1).setStatus(Cell.LIVE_CELL);
        c.getCell(9, 1).setStatus(Cell.LIVE_CELL);
    }

    @Test
    void countCellNum() {
        assertEquals(100, c.getTotalCellNum());
        assertEquals(3, c.getAliveCellNum());
        assertEquals(97, c.getDeadCellNum());
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
        assertEquals(Cell.LIVE_CELL, c.getCell(0,0).getStatus());
        assertEquals(Cell.DEAD_CELL, c.getCell(9,9).getStatus());
        assertEquals(Cell.LIVE_CELL, c.getCell(11, 1).getStatus()); // Wrapping
    }

    @Test
    void neighbours() {
        assertEquals( 2, c.neighbours(0,0));
        assertEquals(1, c.neighbours(9, 9));
        assertEquals( 0, c.neighbours(5, 5));
    }

    @Test
    void testToString() {
        assertEquals("""
                10, 10
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0
                0, 1, 0, 0, 0, 0, 0, 0, 0, 0
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                0, 1, 0, 0, 0, 0, 0, 0, 0, 0""", c.toString());
    }
}