package com.luox6.battleship.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This is a specific line implementation
 * for this game: it assumes that start and
 * end should have same X or Y, which is a straight line
 * @author Xinhao Luo
 * @version 0.0.1
 */
public class Line implements Directable {
    /** two endpoint of a line **/
    private Coordinate start;
    private Coordinate end;

    /**
     * Default Constructor
     * please ensure two coordinates follow the standard
     * @param start Start Coordinate
     * @param end End Coordinate
     */
    public Line(Coordinate start, Coordinate end) {
        this.start = start;
        this.end = end;
    }

    /**
     * The distance include two endpoints
     * @return distance between two point
     */
    public int getLength() {
        if (start.getX() == end.getX()) {
            return Math.abs(start.getY() - end.getY()) + 1;
        }
        return Math.abs(start.getX() - end.getX()) + 1;
    }

    public Coordinate getEnd() {
        return end;
    }

    public Coordinate getStart() {
        return start;
    }

    /**
     * Return all coordinate between the start and the end,
     * include line start and end coordinate
     * @return List of Coordinate
     */
    public List<Coordinate> getLineSequence() {
        ArrayList<Coordinate> res = new ArrayList<>();
        if (start.getX() == end.getX()) {
            for (int i = start.getY(); i <= end.getY(); i++) {
                res.add(new Coordinate(start.getX(), i));
            }
        } else {
            for (int i = start.getX(); i <= end.getX(); i++) {
                res.add(new Coordinate(i, start.getY()));
            }
        }

        return res;
    }

    /**
     * Get line direction
     * @return Direction Horizontal or Vertical
     */
    public Direction getDirection() {
        if (start.getX() == end.getX()) {
            return Direction.VERTICAL;
        } else if (start.getY() == end.getY()) {
            return Direction.HORIZONTAL;
        }

        throw new RuntimeException("Coordinate is either vertical or horizontal");
    }

    /**
     * This method identify if given coordinate is within the
     * line range. Imagine the line expand to a rectangle.
     * @param c Coordinate to identify
     * @return true if it is within the space, false otherwise
     */
    public boolean isCrossed(Coordinate c) {
        return (c.getX() >= start.getX() && c.getX() <= end.getX()) || (c.getY() >= start.getY() && c.getY() <= end.getY());
    }

    /**
     * Identify if given Coordinate has same X or Y with the line
     * @param c
     * @return
     */
    public boolean isSameLevel(Coordinate c) {
        return c.getY() == start.getY() || c.getX() == start.getX();
    }

    /**
     * Identify if given coordinate is on the line (include end and tail
     * @param c Coordinate
     * @return true if it is on the line, false otherwise
     */
    public boolean isBetween(Coordinate c) {
        return isCrossed(c) && isSameLevel(c);
    }

    /**
     * Test if given line will overlap with self
     * @param l Line to test
     * @return true if they will overlap, false otherwise
     */
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
