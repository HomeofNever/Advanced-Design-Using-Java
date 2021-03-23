package com.luox6.battleship.model.ship;

import com.luox6.battleship.exception.IllegalLengthException;

import java.util.Objects;
import java.util.UUID;

/**
 * Generic Ship representation
 * A ship should have an id and a length
 * @author Xinhao Luo
 * @version 0.0.1
 */
public class Ship {
    protected int length = 1;
    protected UUID shipId;

    public Ship() {}

    /**
     * Default constructor
     * will generate random UUID as ship id
     * @param length length of ship
     */
    public Ship(int length) {
        setLength(length);
        shipId = UUID.randomUUID();
    }

    /**
     * Ship Copy Constructor
     * @param length length of ship
     * @param uuid shipId
     */
    public Ship(int length, UUID uuid) {
        setLength(length);
        shipId = uuid;
    }

    /**
     * Get Ship UUID
     * @return UUID ship id
     */
    public UUID getShipId() {
        return shipId;
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

    /**
     * Set this with String representation of Ship
     * @param obj String representation of ship
     */
    protected void setShip(String obj) {
        String[] params = obj.substring(obj.indexOf('{') + 1, obj.indexOf('}')).split(" ");
        setLength(Integer.parseInt(params[0]));
        shipId = UUID.fromString(params[1]);
    }

    /**
     * Get Ship Object from String representation (toString)
     * @param s String representation of Ship
     * @return Ship
     */
    public static Ship valueOf(String s) {
        Ship ship = new Ship();
        ship.setShip(s);
        return ship;
    }
}
