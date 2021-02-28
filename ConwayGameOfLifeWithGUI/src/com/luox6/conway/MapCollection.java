package com.luox6.conway;

import java.util.ArrayList;

/**
 * Collection of Conway maps, derive from the first map of the collection
 */
public class MapCollection {
    /**
     * Store of all calculated map
     */
    private ArrayList<ConwayMap> maps;

    /**
     * Take a map as starting point of map collection
     * @param c Starting point
     */
    public MapCollection(ConwayMap c) {
        maps = new ArrayList<>();
        maps.add(c);
    }

    /**
     * Init Collection with empty map
     * @param row Map row
     * @param col Map Col
     */
    public MapCollection(int row, int col) {
        maps = new ArrayList<>();
        maps.add(new ConwayMap(row, col));
    }

    /**
     * Get map row length
     * @return num of row amount maps
     */
    public int getRow() {
        return getLatestMap().getMapRowLength();
    }

    /**
     * Get map col length
     * @return num of col amoung maps
     */
    public int getCol() {
        return getLatestMap().getMapColLength();
    }

    /**
     * Get the latest map from the collection
     * @return the last conway map of the collection
     */
    public ConwayMap getLatestMap() {
        return maps.get(maps.size() - 1);
    }

    /**
     * Get Map by given index
     * @param index map index
     * @return Conway Map if index is valid and calculated, null otherwise
     */
    public ConwayMap getSpecificMap(int index) {
        if (index < maps.size() && index >= 0) {
            return maps.get(index);
        }

        return null;
    }

    /**
     * Identify if given index has calculated
     * @param i map index
     * @return true if calculated, false otherwise
     */
    public boolean isCalculated(int i) {
        return i <= getLatestIndex();
    }

    /**
     * Run calculation toward given index
     * @param i map index
     */
    public void runTo(int i) {
        if (i > 0) {
            if (!isCalculated(i)) {
                for (int j = getLatestIndex() + 1; j <= i; j++) {
                    maps.add(getLatestMap().tick());
                }
            }
        }
    }

    /**
     * Get largest map index
     * @return map index
     */
    public int getLatestIndex() {
        return maps.size() - 1;
    }

    /**
     * Reset Collection to empty state by keep the same row and col
     */
    public void reset() {
        ConwayMap c = new ConwayMap(getRow(), getCol());
        maps = new ArrayList<>();
        maps.add(c);
    }
}
