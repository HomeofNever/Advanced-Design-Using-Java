package com.luox6.epidemic.test;

import com.luox6.epidemic.model.Node;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    @org.junit.jupiter.api.Test
    void getStatus() {
        Node n = new Node("1");
        assertEquals(0, n.getInfectedAge());
        assertEquals("1", n.getName());
        assertEquals(Node.STATUS.SUSCEPTIBLE, n.getStatus());
    }

    @org.junit.jupiter.api.Test
    void setStatus() {
        Node n = new Node("2");
        n.setStatus(Node.STATUS.DEAD);
        assertEquals(Node.STATUS.DEAD, n.getStatus());
    }

    @org.junit.jupiter.api.Test
    void tick() {
        Node n = new Node("3");
        n.setStatus(Node.STATUS.INFECTIOUS);
        n.tick();
        assertEquals(1, n.getInfectedAge());
    }
}