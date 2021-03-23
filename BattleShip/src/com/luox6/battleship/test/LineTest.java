package com.luox6.battleship.test;

import com.luox6.battleship.model.Coordinate;
import com.luox6.battleship.model.Directable;
import com.luox6.battleship.model.Line;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LineTest {
    Line l;
    Line l1;
    Line l2;

    @BeforeEach
    void setUp() {
        l = new Line(new Coordinate(1, 1), new Coordinate(1, 2));
        l1 = new Line(new Coordinate(1, 1), new Coordinate(1, 2));
        l2 = new Line(new Coordinate(1, 1), new Coordinate(2, 1));
    }

    @Test
    void getLength() {
        assertEquals(2, l.getLength());
        assertEquals(2, l2.getLength());
    }

    @Test
    void getEnd() {
        assertEquals(new Coordinate(1, 2), l1.getEnd());
        assertEquals(new Coordinate(2, 1), l2.getEnd());
    }

    @Test
    void getStart() {
        assertEquals(new Coordinate(1, 1), l.getStart());
    }

    @Test
    void getLineSequence() {
        List<Coordinate> list = l.getLineSequence();
        assertEquals(new Coordinate(1, 1), list.get(0));
        assertEquals(new Coordinate(1, 2), list.get(1));
    }

    @Test
    void getDirection() {
        assertEquals(Directable.Direction.VERTICAL, l.getDirection());
        assertEquals(Directable.Direction.HORIZONTAL, l2.getDirection());
    }

    @Test
    void isBetween() {
        assertTrue(l.isBetween(new Coordinate(1, 1)));
        assertTrue(l.isBetween(new Coordinate(1, 2)));
    }

    @Test
    void isOverLapped() {
        assertTrue(l.isOverLapped(l2));
    }

    @Test
    void testEquals() {
        assertEquals(l, l1);
    }
}