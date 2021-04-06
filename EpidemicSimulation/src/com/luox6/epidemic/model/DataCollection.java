package com.luox6.epidemic.model;

import java.util.ArrayList;
import java.util.List;

public class DataCollection {
    private List<Integer> infectedCount;
    private List<Integer> recoveredCount;
    private List<Integer> deadCount;
    private List<Integer> susceptibleCount;
    private List<Integer> ticks;
    private Graph graph;

    public DataCollection(Graph g) {
        graph = g;
        infectedCount = new ArrayList<>();
        recoveredCount = new ArrayList<>();
        deadCount = new ArrayList<>();
        susceptibleCount = new ArrayList<>();
        ticks = new ArrayList<>();

        syncGraphData();
    }

    public void simulate() {
        graph.simulate();
        syncGraphData();
    }

    private void syncGraphData() {
        infectedCount.add(graph.getInfectedCount());
        recoveredCount.add(graph.getRecoveredCount());
        deadCount.add(graph.getDeadCount());
        susceptibleCount.add(graph.getSusceptibleCount());
        ticks.add(getTick());
    }

    public boolean checkIndex(int index) {
        return index > -1 && index <= getTick();
    }

    public List<List<Integer>> getDataByRange(int start, int end) {
        if (checkIndex(start)) {
            if (end == -1) {
                end = getTick();
            }
            if (checkIndex(end)) {
                List<List<Integer>> ls = new ArrayList<>();
                end += 1; // Sublist is exclusive
                ls.add(ticks.subList(start, end));
                ls.add(susceptibleCount.subList(start, end));
                ls.add(infectedCount.subList(start, end));
                ls.add(deadCount.subList(start, end));
                ls.add(recoveredCount.subList(start, end));
                return ls;
            }
        }

        return null;
    }

    public List<List<Integer>> getAllData() {
        return getDataByRange(0, getTick());
    }

    /** Getters and setters **/

    public int getCurrentSusceptibleCount() {
        return susceptibleCount.get(susceptibleCount.size() - 1);
    }

    public int getCurrentInfectedCount() {
        return infectedCount.get(infectedCount.size() - 1);
    }

    public int getCurrentRecoveredCount() {
        return recoveredCount.get(recoveredCount.size() - 1);
    }

    public int getCurrentDeadCount() {
        return deadCount.get(deadCount.size() - 1);
    }

    public Graph getGraph() {
        return graph;
    }

    public int getTick() {
        return graph.getTick();
    }

    public int[] getData(int index) {
        if (checkIndex(index)) {
            int[] res = new int[4];

            res[0] = susceptibleCount.get(index);
            res[1] = infectedCount.get(index);
            res[2] = deadCount.get(index);
            res[3] = recoveredCount.get(index);

            return res;
        }

        return null;
    }
}
