package com.luox6.epidemic.test;

import com.luox6.epidemic.model.DataCollection;
import com.luox6.epidemic.model.Graph;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DataCollectionTest {
    DataCollection d;

    @BeforeEach
    void setUp() {
        Graph g = new Graph();
        List<Map.Entry<String, String>> l = new LinkedList<>();
        l.add(new AbstractMap.SimpleEntry<>("1", "2"));
        g.initGraphData(l);
        g.init();
        d = new DataCollection(g);
    }

    @Test
    void simulate() {
        assertEquals(0, d.getCurrentDeadCount());
        assertEquals(0, d.getCurrentInfectedCount());
        assertEquals(0, d.getCurrentRecoveredCount());
        assertEquals(0, d.getTick());
        assertEquals(2, d.getCurrentSusceptibleCount());
    }

    @Test
    void getAllData() {
        List<List<Integer>> i = d.getAllData();
        Integer x = i.get(0).get(0);
        assertEquals(0, x);
        x = i.get(1).get(0);
        assertEquals(2, x);
        x = i.get(2).get(0);
        assertEquals(0, x);
        x = i.get(3).get(0);
        assertEquals(0, x);
        x = i.get(4).get(0);
        assertEquals(0, x);
    }
}