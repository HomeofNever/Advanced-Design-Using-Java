package com.luox6.battleship.model;

import com.luox6.battleship.exception.InvalidLocationException;
import com.luox6.battleship.model.ship.BattleShip;
import com.luox6.battleship.model.ship.StandbyShip;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Representation of Ship Map
 * @author Xinhao Luo
 * @version 0.0.1
 */
public class GameMap {
    /**
     * Cell map
     */
    private Cell map[][];

    /**
     * Map of <Ship id, Battleship>
     */
    private Map<UUID, BattleShip> battleShips;

    /**
     * Map row
     */
    private int row;

    /**
     * Map col
     */
    private int col;

    /**
     * Default Constructor
     * @param row row num
     * @param col col num
     */
    public GameMap(int row, int col) {
        this.row = row;
        this.col = col;
        init();
    }

    /**
     * Class initialization helper
     */
    private void init() {
        map = new Cell[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                map[i][j] = new Cell(i, j);
            }
        }
        battleShips = new HashMap<>();
    }

    /**
     * This method is used for set ship on the map
     * @param s
     * @param c The beginning coordinate of the ship
     * @return
     */
    public void addShip(StandbyShip s, Coordinate c) throws Exception {
        if (!isValidCoordinate(c)) {
            throw new InvalidLocationException("Invalid start Location: %s".formatted(c.toString()));
        }

        Coordinate end = s.calculateEnd(c);
        if (isValidCoordinate(end)) {
            Line l = new Line(c, end);

            for (Coordinate sc : l.getLineSequence()) {
                Cell cs = Objects.requireNonNull(getCellByCoordinate(sc));
                if (cs.getShipId() != null) {
                    throw new Exception("Ship overlapped");
                }
            }

            // update ship settings
            BattleShip bs = new BattleShip(l, s.getShipId());
            addShip(bs);
            return;
        }

        throw new InvalidLocationException("Invalid End Coordinate %s".formatted(end.toString()));
    }

    public Map<UUID, BattleShip> getBattleShips() {
        return battleShips;
    }

    /**
     * This method will assume incoming Battleship is valid over the map
     * used for sync between server-client
     * @param bs BattleShip
     */
    public void addShip(BattleShip bs) {
        Line l = bs.getLine();
        for (Coordinate sc : l.getLineSequence()) {
            Cell cs = Objects.requireNonNull(getCellByCoordinate(sc));
            cs.setShipId(bs.getShipId());
        }
        battleShips.put(bs.getShipId(), bs);
    }

    /**
     * Remove the id corresponded ship from the map
     * @param id UUID ship id
     */
    public void removeShip(UUID id) {
        BattleShip bs = battleShips.get(id);
        if (bs != null) {
            for (Coordinate sc : bs.getShipSequence()) {
                Cell cs = getCellByCoordinate(sc);
                cs.setShipId(null);
            }
            battleShips.remove(id);
            return;
        }

        throw new RuntimeException("Try removing an non-existing ship: %s".formatted(id.toString()));
    }

    /**
     * Get Cell representation of the location coordinate
     * represented
     * @param c Coordinate location
     * @return Cell if location is valid, null otherwise
     */
    public Cell getCellByCoordinate(Coordinate c) {
        if (isValidCoordinate(c)) {
            return map[c.getX()][c.getY()];
        }

        return null;
    }

    /**
     * Identify if given coordinate is on the map
     * @param c Coordinate
     * @return true if coordinate is on the Map, false otherwise
     */
    public boolean isValidCoordinate(Coordinate c) {
        return c.getX() >= 0 && c.getX() < row && c.getY() >=0 && c.getY() < col;
    }

    /**
     * Mark given coordinate as discovered
     * @param c Coordinate location to mark
     */
    public void mark(Coordinate c) {
        if (isValidCoordinate(c)) {
            map[c.getX()][c.getY()].setDiscovered(true);
        }
    }

    /**
     * Identify is all ships are down on current map
     * @return true if all ships are marked discovered, false otherwise
     */
    public boolean allDown() {
        for (BattleShip bs : battleShips.values()) {
            for (Coordinate cs : bs.getShipSequence()) {
                Cell c = getCellByCoordinate(cs);
                if (!c.isDiscovered()) {
                    return false;
                }
            }
        }

        return true;
    }
}
