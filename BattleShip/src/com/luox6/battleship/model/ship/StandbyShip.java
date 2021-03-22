package com.luox6.battleship.model.ship;

import com.luox6.battleship.model.Coordinate;
import com.luox6.battleship.model.Directable;

import java.util.Objects;
import java.util.UUID;

public class StandbyShip extends Ship implements Directable {
    protected Direction direction = Direction.HORIZONTAL;

    public StandbyShip() {}

    public StandbyShip(int length) {
        super(length);
    }

    public StandbyShip(int length, Direction direction)  {
        super(length);
        setDirection(direction);
    }

    public StandbyShip(int length, Direction direction, UUID uuid)  {
        super(length, uuid);
        setDirection(direction);
    }


    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Coordinate calculateEnd(Coordinate start) {
        if (direction == Direction.HORIZONTAL) {
            return new Coordinate(start.getX() + getLength() - 1, start.getY());
        } else {
            return new Coordinate(start.getX(), start.getY() + getLength() - 1);
        }
    }

    @Override
    public String toString() {
        return "<%s %s>".formatted(direction, super.toString());
    }

    @Override
    protected void setShip(String obj) {
        String[] params = obj.substring(obj.indexOf('<') + 1, obj.indexOf('>')).split(" ", 2);
        Direction d = Direction.valueOf(params[0]);
        setDirection(d);
        super.setShip(params[1]);
    }

    public static StandbyShip valueOf(String s) {
        StandbyShip ship = new StandbyShip();
        ship.setShip(s);
        return ship;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StandbyShip)) return false;
        if (!super.equals(o)) return false;
        StandbyShip that = (StandbyShip) o;
        return getDirection() == that.getDirection();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDirection());
    }
}
