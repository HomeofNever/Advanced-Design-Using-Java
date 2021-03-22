package com.luox6.battleship.model.ship;

import com.luox6.battleship.exception.IllegalLengthException;

import java.util.Objects;
import java.util.UUID;

public class Ship {
    protected int length = 1;
    protected UUID shipId;

    public Ship() {}

    // Ship should have x or y the same
    public Ship(int length) {
        setLength(length);
        shipId = UUID.randomUUID();
    }

    public Ship(int length, UUID uuid) {
        setLength(length);
        shipId = uuid;
    }

    public UUID getShipId() {
        return shipId;
    }

    public void setShipId(UUID shipId) {
        this.shipId = shipId;
    }

    /**
     * Set Ship length
     * Ideally, ship's length should not be changed after initialization.
     * So this method is wrapped as protected.
     * @param length Length of Ship
     * @throws IllegalLengthException
     */
    protected void setLength(int length) {
        if (length <= 0) {
            throw new IllegalLengthException("Ship Length must be at least 1, got %d".formatted(length));
        }
        this.length = length;
    }

    /**
     * Get the length of the ship
     * @return int length of ship
     */
    public int getLength() {
        return length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ship)) return false;
        Ship ship = (Ship) o;
        return getLength() == ship.getLength() && getShipId() == ship.getShipId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLength(), getShipId());
    }

    @Override
    public String toString() {
        return "{%s %s}".formatted(length, shipId);
    }

    protected void setShip(String obj) {
        String[] params = obj.substring(obj.indexOf('{') + 1, obj.indexOf('}')).split(" ");
        setLength(Integer.parseInt(params[0]));
        shipId = UUID.fromString(params[1]);
    }

    public static Ship valueOf(String s) {
        Ship ship = new Ship();
        ship.setShip(s);
        return ship;
    }
}
