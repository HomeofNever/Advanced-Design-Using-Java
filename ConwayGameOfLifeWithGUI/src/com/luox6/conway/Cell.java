package com.luox6.conway;

/**
 * Cell representation 
 */
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

    /**
     * Number of times the cell has survived
     */
    private int times = 0;

    /**  
     * Default construcor of an dead cell
     */
    public Cell() {};

    /**
     * Constructor based on status
     * @param status cell status
     */
    public Cell(int status) {
        if (isValidStatus(status)) {
            this.status = status;
        } else {
            this.status = UNKNOWN_CELL;
        }
    }

    /**
     * Construct cell based on char representation of cell
     * @param cell char cell representation
     */
    public Cell(char cell) {
        this(charToInt(cell));
    }

    /**
     * Reset Cell survival times
     */
    protected void resetTicks() {
        if (status == DEAD_CELL) {
            times = 0;
        } else {
            times = 1;
        }
    
    }

    /**
     * Set cell status, will affect times of survival
     * @param status Cell status
     * @return true if it is a valid status, false otherwise
     */
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

    /**
     * Get Cell status
     * @return int representation of cell status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Get survival times
     * @return cell survival times
     */
    public int getTimes() {
        return times;
    }

    /**
     * Set Cell survival time, should only be used when cell status need to be copied
     * @param times cell survival time
     */
    private void setTimes(int times) {
        this.times = times;
    }

    /**
     * Increase Cell survival time
     */
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

    /**
     * Identify if given representation is a valid cell status
     * @param status cell status
     * @return true if it is a valid status, false otherwise
     */
    public static boolean isValidStatus(int status) {
        return status == DEAD_CELL || status == LIVE_CELL;
    }

    /**
     * Cell status
     * @return true if cell is alive, false otherwise. Possibly unknown state
     */
    public boolean isAlive() {
        return status == LIVE_CELL;
    }

    /**
     * Filp current cell status (dead to live, live to dead), and it will reset survival time.
     */
    public void flip() {
        if (this.status == LIVE_CELL) {
            setStatus(DEAD_CELL);
        } else {
            setStatus(LIVE_CELL);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(getStatus());
    }
    
    /**
     * Return a new cell with exact same status
     */
    @Override
    public Cell clone() {
        Cell newCell = new Cell(status);
        newCell.setTimes(times);
        return newCell;
    }
}
