package com.luox6.conway.test;

import com.luox6.conway.gui.models.MapModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapModelTest {

    @Test
    void setCurrentIndex() {
        MapModel m = new MapModel(10, 10);
        try{
            m.setCurrentIndex(10);
            assertEquals(10, m.getCurrentIndex());
            m.setCurrentIndex(5);
            assertEquals(5, m.getCurrentIndex());
            m.setCurrentIndex(-1);
            assertEquals(5, m.getCurrentIndex());
        } catch (Exception ignored) {}
    }

    @Test
    void runOnce() {
        MapModel m = new MapModel(0, 0);
        try {
            m.runOnce();
            assertEquals(1, m.getCurrentIndex());
        } catch (Exception ignored) {}
    }

    @Test
    void goBack() {
        MapModel m = new MapModel(0, 0);
        try {
            m.goBack();
            assertEquals(0, m.getCurrentIndex());
            m.setCurrentIndex(10);
            m.goBack();
            assertEquals(9, m.getCurrentIndex());
        } catch (Exception ignored) {}
    }

    @Test
    void isStarted() {
        MapModel m = new MapModel(10, 10);
        assertFalse(m.isStarted());
        try{
            m.runOnce();
            assertTrue(m.isStarted());
            m.goBack();
            assertTrue(m.isStarted());
        } catch (Exception ignored) {};
    }
}