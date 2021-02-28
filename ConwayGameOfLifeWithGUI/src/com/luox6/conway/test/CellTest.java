package com.luox6.conway.test;

import com.luox6.conway.Cell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    
    @Test
    void getStatus() {
        Cell cell1 = new Cell(Cell.DEAD_CELL);
        Cell cell2 = new Cell(Cell.LIVE_CELL);
        Cell cell3 = new Cell(-1);
        assertEquals(Cell.DEAD_CELL, cell1.getStatus());
        assertEquals(Cell.LIVE_CELL, cell2.getStatus());
        assertEquals(Cell.UNKNOWN_CELL, cell3.getStatus());
        cell3.setStatus(Cell.DEAD_CELL);
        assertEquals(Cell.DEAD_CELL, cell3.getStatus());
    }

    @Test
    void getTimes() {
        Cell cell2 = new Cell(Cell.LIVE_CELL);
        assertEquals(0, cell2.getTimes());
        cell2.tick();
        assertEquals(1,cell2.getTimes());
        cell2.setStatus(Cell.DEAD_CELL);
        assertEquals(0, cell2.getTimes());
    }

    @Test
    void isAlive() {
        Cell cell1 = new Cell(Cell.DEAD_CELL);
        Cell cell2 = new Cell(Cell.LIVE_CELL);
        Cell cell3 = new Cell(-1);
        assertFalse(cell1.isAlive());
        assertTrue(cell2.isAlive());
        assertFalse(cell3.isAlive());
    }

    @Test
    void flip() {
        Cell cell1 = new Cell(Cell.DEAD_CELL);
        cell1.flip();
        assertEquals(Cell.LIVE_CELL, cell1.getStatus());
    }

    @Test
    void testClone() {
        Cell cell1 = new Cell(Cell.DEAD_CELL);
        Cell cell2 = cell1.clone();
        cell1.setStatus(Cell.LIVE_CELL);
        assertEquals(Cell.DEAD_CELL, cell2.getStatus());
    }
}