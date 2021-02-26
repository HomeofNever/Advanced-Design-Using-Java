package com.luox6.conway;

import java.util.function.BiFunction;

/**
 * Main class stores the conway map and process ticks
 * @author Xinhao Luo
 * @version 0.0.1 
 */
public class ConwayMap {
    private Cell[][] map;
    private final int row;
    private final int col;
    private int tick = 0;

    /**
     * Initialize map with given row length and col length
     *
     * @param rowLength Length of map rows
     * @param colLength Length of map Columns
     */
    public ConwayMap(int rowLength, int colLength) {
        row = rowLength;
        col = colLength;
        map = new Cell[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                map[i][j] = new Cell();
            }
        }
    }

    public ConwayMap(Cell[][] cells, int row, int col, int tick) {
        map = cells;
        this.row = row;
        this.col = col;
        this.tick = tick;
    }

    /**
     * Get map row length
     * @return map row length
     */
    public int getMapRowLength() {
        return row;
    }

    /**
     * Get map col length
     * @return map col length
     */
    public int getMapColLength() {
        return col;
    }

    /**
     * Get current processed ticks
     * @return ticks/turn the game has simulated
     */
    public int getTick() {
        return tick;
    }

    /**
     * Identify if given row/col combination is valid for current map
     * @param row Map row index
     * @param col Map col index
     * @return true if with map, false otherwise
     */
    public boolean isValidCoordinate(int row, int col) {
        return row >= 0 && row < this.row && col >= 0 && col < this.col;
    }

    /**
     * Get cell status with given coordinates.
     * Out of bound index will be remapped into valid coordinates
     * @param row Map row index
     * @param col Map col index
     * @return cell status
     */
    public Cell getCell(int row, int col) {
        if (isValidCoordinate(row, col)) {
            return map[row][col];
        }

        // If the number if larger than our index, we have the reminder of our index.
        // However, if it is less than 0, we need to have the reminder of our length and then
        // add the length back. Not the index.
        final BiFunction<Integer, Integer, Integer> wrapper =
                (k, v) -> k < 0 ? k % v + v : k % v;
        int realRow = wrapper.apply(row, this.row);
        int realCol = wrapper.apply(col, this.col);

        return map[realRow][realCol];
    }


    /**
     * Calculate the sum of all live cell around given coordinates
     * @param row Map row index
     * @param col Map col index
     * @return number of alive cell
     */
    public int neighbours(int row, int col) {
        int liveNeighbours = 0;
        if (isValidCoordinate(row, col)) {
            // Go through 8 position (neighbours)
            liveNeighbours += getCell(row - 1, col).getStatus();
            liveNeighbours += getCell(row - 1, col + 1).getStatus();
            liveNeighbours += getCell(row - 1, col - 1).getStatus();
            liveNeighbours += getCell(row, col - 1).getStatus();
            liveNeighbours += getCell(row, col + 1).getStatus();
            liveNeighbours += getCell(row + 1, col).getStatus();
            liveNeighbours += getCell(row + 1, col - 1).getStatus();
            liveNeighbours += getCell(row + 1, col + 1).getStatus();

            return liveNeighbours;
        }

        return -1;
    }

    /**
     * Calculate given coordinate's cell status
     * @param row Map row index
     * @param col Map col index
     * @param changed Map to record results
     */
    private void step(int row, int col, Cell[][] changed) {
        int numNeighbours = neighbours(row, col);

        if (numNeighbours == -1) {
            return; // Interesting, this should not happen.
        }
        if (numNeighbours < 2) {
            changed[row][col].setStatus(Cell.DEAD_CELL);
        } else if (numNeighbours > 3) {
            changed[row][col].setStatus(Cell.DEAD_CELL);
        } else if (numNeighbours == 3) {
            changed[row][col].setStatus(Cell.LIVE_CELL);
        }

        if (changed[row][col].isAlive()) {
            changed[row][col].tick();
        }

        // Leave the rest unchanged
    }

    /**
     * Move game forward by calculating each coordinates
     */
    public ConwayMap tick() {
        // Clone the array for record
        Cell[][] changed = new Cell[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                changed[i][j] = map[i][j].clone();
            }
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                step(i, j, changed);
            }
        }

        return new ConwayMap(changed, row, col, tick + 1);
    }

    @Override
    public String toString() {
        StringBuilder x = new StringBuilder();
        x.append(row).append(", ").append(col).append("\n");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col - 1; j++) {
                x.append(map[i][j]).append(", ");
            }
            x.append(map[i][col - 1]).append("\n");
        }

        // Remove last newline
        x.setLength(x.length() - 1);

        return x.toString();
    }

    public void clearTicks() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col - 1; j++) {
                map[i][j].resetTicks();
            }
        }
    }
}
