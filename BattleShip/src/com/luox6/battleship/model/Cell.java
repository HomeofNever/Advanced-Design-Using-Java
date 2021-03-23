package com.luox6.battleship.model;

import java.util.UUID;

/**
 * This class implements a basic elements of
 * @author Xinhao Luo
 * @version 0.0.1
 */
public class Cell extends Coordinate implements Discoverable {
    private boolean discovered = false;
    private UUID shipId;

    /**
     * Default constructor of the cell
     * @param i Row the cell located
     * @param j Col the Cell located
     */
    public Cell(int i, int j) {
        super(i, j);
    }

    public UUID getShipId() {
        return shipId;
    }

    /**
     * Test if cell contains part of the ship
     * @return true if a ship exists, false otherwise
     */
    public boolean hasShip() {
        return this.shipId != null;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }

    public void setShipId(UUID shipId) {
        this.shipId = shipId;
    }
}
