package com.luox6.battleship.model;

import java.util.List;
import java.util.Objects;

public class Line implements Directable {
    private Coordinate start;
    private Coordinate end;

    // Ship should have x or y the same
    Line(Coordinate start, Coordinate end) {
        this.start = start;
        this.end = end;
    }

    public int getLength() {
        return start.distance(end);
    }

    public Coordinate getEnd() {
        return end;
    }

    public Coordinate getStart() {
        return start;
    }

    public List<Coordinate> getLineSequence() {
        return start.getSequence(end);
    }

    public Direction getDirection() {
        if (start.getX() == end.getX()) {
            return Direction.VERTICAL;
        } else if (start.getY() == end.getY()) {
            return Direction.HORIZONTAL;
        }

        throw new RuntimeException("Coordinate is either vertical or horizontal");
    }

    public boolean isCrossed(Coordinate c) {
        return (c.getX() >= start.getX() && c.getX() <= end.getX()) || (c.getY() >= start.getY() && c.getY() <= end.getY());
    }

    public boolean isSameLevel(Coordinate c) {
        return c.getY() == start.getY() || c.getX() == start.getX();
    }

    public boolean isBetween(Coordinate c) {
        return isCrossed(c) && isSameLevel(c);
    }

    public boolean isOverLapped(Line l) {
        // Same level overlap
        if (isBetween(l.getStart()) || isBetween(l.getEnd())) {
            return true;
        }

        if (getDirection() != l.getDirection()) {
            // @TODO May optimize with better math
            for (Coordinate x : getLineSequence()) {
                for (Coordinate y : l.getLineSequence()) {
                    if (x.equals(y)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Line)) return false;
        Line line = (Line) o;
        return getStart().equals(line.getStart()) && getEnd().equals(line.getEnd());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStart(), getEnd());
    }
}
