package com.luox6.conway.test;

import com.luox6.conway.MapCollection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapCollectionTest {
    @Test
    void isCalculated() {
        MapCollection m = new MapCollection(10, 10);
        m.runTo(10);
        assertTrue(m.isCalculated(5));
        assertFalse(m.isCalculated(11));
    }

    @Test
    void getLatestIndex() {
        MapCollection m = new MapCollection(10, 10);
        assertEquals(0, m.getLatestIndex());
        m.runTo(10);
        assertEquals(10, m.getLatestIndex());
    }

    @Test
    void reset() {
        MapCollection m = new MapCollection(10, 10);
        m.runTo(10);
        m.reset();
        assertEquals(0, m.getLatestIndex());
    }
}