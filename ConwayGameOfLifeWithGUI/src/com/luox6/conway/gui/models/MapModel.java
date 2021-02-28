package com.luox6.conway.gui.models;

import com.luox6.conway.ConwayMap;

public class MapModel extends com.luox6.conway.MapCollection {
    /**
     * Current User View Index
     */
    int currentIndex = 0;

    public MapModel(ConwayMap c) {
        super(c);
    }

    public MapModel(int row, int col) {
        super(row, col);
    }

    /**
     * Get Current view index
     * @return
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * Set Current view Index
     * @param currentIndex map index
     * @throws Exception Index must not be negative
     */
    public void setCurrentIndex(int currentIndex) throws Exception {
        if (currentIndex < 0) {
            throw new Exception("Index Must be larger or equal to 0");
        }
        if (getLatestIndex() < currentIndex) {
            runTo(currentIndex);
        }

        this.currentIndex = currentIndex;
    }

    /**
     * Get map of current index
     * @return
     */
    public ConwayMap getCurrentConwayMap() {
        return getSpecificMap(currentIndex);
    }

    /**
     * Move currentIndex one forward
     * @throws Exception Index less than 0
     */
    public void runOnce() throws Exception {
        setCurrentIndex(getCurrentIndex() + 1);
    }

    /**
     * Move current Index one backward
     * @throws Exception
     */
    public void goBack() throws Exception {
        setCurrentIndex(getCurrentIndex()- 1);
    }

    /**
     * Identify if current Model is in simulation progress
     * @return true if in progress, false otherwise
     */
    public boolean isStarted() {
        return getLatestIndex() > 0;
    }
}
