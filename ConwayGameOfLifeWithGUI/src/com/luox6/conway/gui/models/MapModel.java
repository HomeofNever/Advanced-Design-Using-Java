package com.luox6.conway.gui.models;

import com.luox6.conway.ConwayMap;

public class MapModel extends com.luox6.conway.MapCollection {
    int currentIndex = 0;

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) throws Exception {
        if (currentIndex < 0) {
            throw new Exception("Index Must be larger than 0");
        }
        if (getLatestIndex() < currentIndex) {
            runTo(currentIndex);
        }

        this.currentIndex = currentIndex;
    }

    public ConwayMap getCurrentConwayMap() {
        return getSpecificMap(currentIndex);
    }

    public MapModel(ConwayMap c) {
        super(c);
    }

    public MapModel(int row, int col) {
        super(row, col);
    }

    public void runOnce() throws Exception {
        setCurrentIndex(getCurrentIndex() + 1);
    }

    public void goBack() throws Exception {
        setCurrentIndex(getCurrentIndex()- 1);
    }

    public boolean isStarted() {
        return getLatestIndex() > 0;
    }
}
