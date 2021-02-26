package com.luox6.conway;

import java.util.ArrayList;

public class MapCollection {
    private ArrayList<ConwayMap> maps;

    public MapCollection(ConwayMap c) {
        maps = new ArrayList<>();
        maps.add(c);
    }

    public MapCollection(int row, int col) {
        maps = new ArrayList<>();
        maps.add(new ConwayMap(row, col));
    }

    public int getRow() {
        return getLatestMap().getMapRowLength();
    }

    public int getCol() {
        return getLatestMap().getMapColLength();
    }

    public ConwayMap getLatestMap() {
        return maps.get(maps.size() - 1);
    }

    public ConwayMap getSpecificMap(int index) {
        if (index < maps.size() && index >= 0) {
            return maps.get(index);
        }

        return null;
    }

    public boolean isCalculated(int i) {
        return i <= getLatestIndex();
    }

    public void runTo(int i) {
        if (i > 0) {
            if (!isCalculated(i)) {
                for (int j = getLatestIndex() + 1; j <= i; j++) {
                    maps.add(getLatestMap().tick());
                }
            }
        }
    }

    public int getLatestIndex() {
        return maps.size() - 1;
    }

    public void reset() {
        ConwayMap c = new ConwayMap(getRow(), getCol());
        maps = new ArrayList<>();
        maps.add(c);
    }
}
