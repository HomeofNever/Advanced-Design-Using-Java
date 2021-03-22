package com.luox6.battleship.model;

import com.luox6.battleship.gui.component.JCell;

import java.util.UUID;

public class Cell extends Coordinate implements Discoverable {
    private boolean discovered = false;
    private UUID shipId;

    public Cell(int i, int j) {
        super(i, j);
    }

    public UUID getShipId() {
        return shipId;
    }

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
