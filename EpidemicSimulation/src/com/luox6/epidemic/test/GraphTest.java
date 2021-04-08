package com.luox6.epidemic.test;

import com.luox6.epidemic.model.Graph;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @Test
    void simulate() {
        Graph g = new Graph();
        List<Map.Entry<String, String>> l = new LinkedList<>();
        l.add(new AbstractMap.SimpleEntry<>("1", "2"));
        g.initGraphData(l);
        g.init();
        assertEquals(0, g.getTick());
        assertEquals(false, g.getInfected());
        assertEquals(0, g.getInfectedCount());
        assertEquals(2, g.getSusceptibleCount());
        assertEquals(0, g.getDeadCount());
        assertEquals(0, g.getRecoveredCount());
    }
}