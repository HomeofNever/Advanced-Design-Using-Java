package com.luox6.epidemic.model;

import java.util.Objects;

/**
 * @author Xinhao Luo
 * @version 0.0.1
 */
public class Node {
    public enum STATUS {SUSCEPTIBLE, INFECTIOUS, RECOVERED, DEAD};
    private String name;
    private STATUS status = STATUS.SUSCEPTIBLE;

    /**
     * The turns Node has lived
     */
    private int infectedAge = 0;

    /**
     * Copy Constructor
     * Age will be ignored if shallow
     * @param n Node
     */
    public Node(Node n, boolean shallow) {
        name = n.name;
        status = n.status;
        if (!shallow)
            infectedAge = n.infectedAge;
    }

    public Node(String n){
        name = n;
    }

    /** Getters and setters **/

    public String getName(){
        return name;
    }

    public STATUS getStatus(){
        return status;
    }

    public int getInfectedAge(){
        return infectedAge;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }


    /**
     * Increase age by one
     */
    public void tick(){
        if (status == STATUS.INFECTIOUS) {
            infectedAge++;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return getInfectedAge() == node.getInfectedAge() && getName().equals(node.getName()) && getStatus() == node.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getStatus(), getInfectedAge());
    }
}
