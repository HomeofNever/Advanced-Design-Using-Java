package com.luox6.battleship.model.ship;
import com.luox6.battleship.model.Coordinate;
import com.luox6.battleship.model.Line;

import java.util.List;
import java.util.UUID;

/**
 * BattleShip representation
 * @author Xinhao Luo
 * @version 0.0.1
 */
public class BattleShip extends StandbyShip {
    protected Line line;

    /**
     * Default Constructor
     * @param line Line where the ship lies
     * @param uuid ship id
     */
    public BattleShip(Line line, UUID uuid) {
        super(line.getLength(), line.getDirection(), uuid);
        this.line = line;
    }

    public Line getLine() {
        return line;
    }

    /**
     * Get the ship head
     * @return Coordinate
     */
    public Coordinate getFirstCoordinate() {
        return line.getStart();
    }

    /**
     * Get List of Coordinates where the ship
     * occupies
     * @return List of Coordinates
     */
    public List<Coordinate> getShipSequence() {
        return line.getLineSequence();
    }
}
