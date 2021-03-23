package com.luox6.battleship.test;

import com.luox6.battleship.model.Cell;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @org.junit.jupiter.api.Test
    void getShipId() {
        Cell x = new Cell(1, 1);
        assertNull(x.getShipId());
    }

    @org.junit.jupiter.api.Test
    void hasShip() {
        Cell x = new Cell(1, 1);
        assertFalse(x.hasShip());
        x.setShipId(UUID.randomUUID());
        assertTrue(x.hasShip());
    }

    @org.junit.jupiter.api.Test
    void isDiscovered() {
        Cell x = new Cell(1, 1);
        assertFalse(x.isDiscovered());
        x.setDiscovered(true);
        assertTrue(x.isDiscovered());
    }
}