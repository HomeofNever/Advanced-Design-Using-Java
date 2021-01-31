package com.luox6.conway;

import java.util.Arrays;
import java.util.function.BiFunction;

/**
 * Main class stores the conway map and process ticks
 */
public class ConwayMap {
    private int[][] map;
    private final int row;
    private final int col;
    private int tick = 0;

    /**
     * Cell status alive
     */
    public final static Integer LIVE_CELL = 1;
    /**
     * Cell status dead/no cell
     */
    public final static Integer DEAD_CELL = 0;
    /**
     * Cell status alive
     * Used for String Int conversion
     */
    public final static char LIVE_CELL_CHAR = '1';
    /**
     * Cell status dead
     * Used for String Int conversion
     */
    public final static char DEAD_CELL_CHAR = '0';

    /**
     * Cell status unknown
     * Used as placeholder for error handling
     */
    public final static Integer UNKNOWN_CELL = -1;

    /**
     * Initialize map with given row length and col length
     *
     * @param rowLength Length of map rows
     * @param colLength Length of map Columns
     */
    public ConwayMap(int rowLength, int colLength) {
        row = rowLength;
        col = colLength;
        map = new int[row][col];
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
     * Set given coordinates with cell status
     * @param row Map row index
     * @param col Map col index
     * @param status Cell status
     * @return true if set successfully, false otherwise
     */
    public boolean setCell(int row, int col, int status) {
        if (isValidCoordinate(row, col)) {
            if (status == LIVE_CELL || status == DEAD_CELL) {
                map[row][col] = status;
                return true;
            }
        }

        return false;
    }

    /**
     * Get cell status with given coordinates.
     * Out of bound index will be remapped into valid coordinates
     * @param row Map row index
     * @param col Map col index
     * @return cell status
     */
    public Integer getCell(int row, int col) {
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
            liveNeighbours += getCell(row - 1, col);
            liveNeighbours += getCell(row - 1, col + 1);
            liveNeighbours += getCell(row - 1, col - 1);
            liveNeighbours += getCell(row, col - 1);
            liveNeighbours += getCell(row, col + 1);
            liveNeighbours += getCell(row + 1, col);
            liveNeighbours += getCell(row + 1, col - 1);
            liveNeighbours += getCell(row + 1, col + 1);

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
    private void step(int row, int col, int[][] changed) {
        int numNeighbours = neighbours(row, col);

        if (numNeighbours == -1) {
            return; // Interesting, this should not happen.
        }
        if (numNeighbours < 2) {
            changed[row][col] = DEAD_CELL;
        } else if (numNeighbours > 3) {
            changed[row][col] = DEAD_CELL;
        } else if (numNeighbours == 3) {
            changed[row][col] = LIVE_CELL;
        }

        // Leave the rest unchanged
    }

    /**
     * Move game forward by calculating each coordinates
     */
    public void tick() {
        // Clone the array for record
        int[][] changed = new int[row][col];
        for (int i = 0; i < row; i++) {
            changed[i] = map[i].clone();
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                step(i, j, changed);
            }
        }

        map = changed;
        tick++;
    }

    /**
     * Convert char cell representation into integer
     * @param cell char cell representation
     * @return int cell representation
     */
    public static int charToInt(char cell) {
        if (cell == DEAD_CELL_CHAR) {
            return DEAD_CELL;
        }
        if (cell == LIVE_CELL_CHAR) {
            return LIVE_CELL;
        }

        return UNKNOWN_CELL;
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
}
