package com.luox6.battleship.model.ship;
import com.luox6.battleship.model.Coordinate;
import com.luox6.battleship.model.Line;

import java.util.List;
import java.util.UUID;

public class BattleShip extends StandbyShip {
    protected Line line;

    public BattleShip(Line line, UUID uuid) {
        super(line.getLength(), line.getDirection(), uuid);
        this.line = line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Coordinate getFirstCoordinate() {
        return line.getStart();
    }

    public Line getLine() {
        return line;
    }

    public List<Coordinate> getShipSequence() {
        return line.getLineSequence();
    }

    public boolean isBody(Coordinate c) {
        return line.isBetween(c);
    }

    public boolean isOverLapped(BattleShip s) {
        return line.isOverLapped(s.line);
    }
}
