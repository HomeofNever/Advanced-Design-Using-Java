package com.luox6.conway;

public class Cell implements Cloneable {
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
     * Cell Status
     */
    private int status = DEAD_CELL;
    private int times = 0;

    public Cell() {};

    public Cell(int status) {
        if (isValidStatus(status)) {
            this.status = status;
        } else {
            this.status = UNKNOWN_CELL;
        }
    }

    public Cell(char cell) {
        this(charToInt(cell));
    }

    protected void resetTicks() {
        times = 0;
    }

    public Boolean setStatus(int status) {
        if(isValidStatus(status)) {
            this.status = status;
            if (status == DEAD_CELL) {
                times = 0;
            } else {
                times = 1;
            }
            return true;
        }
        return false;
    }

    public int getStatus() {
        return status;
    }

    public int getTimes() {
        return times;
    }

    private void setTimes(int times) {
        this.times = times;
    }

    public void tick() {
        times++;
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

    public static boolean isValidStatus(int status) {
        return status == DEAD_CELL || status == LIVE_CELL;
    }

    public boolean isAlive() {
        return status == LIVE_CELL;
    }

    public void flip() {
        this.status = this.status == LIVE_CELL ? DEAD_CELL : LIVE_CELL;
        this.times = 0;
    }

    @Override
    public String toString() {
        return String.valueOf(getStatus());
    }

    @Override
    public Cell clone() {
        Cell newCell = new Cell(status);
        newCell.setTimes(times);
        return newCell;
    }
}
