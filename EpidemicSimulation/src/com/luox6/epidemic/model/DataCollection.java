package com.luox6.epidemic.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Xinhao Luo
 * @version 0.0.1
 */
public class DataCollection {
    private final List<Integer> infectedCount;
    private final List<Integer> recoveredCount;
    private final List<Integer> deadCount;
    private final List<Integer> susceptibleCount;
    private final List<Integer> ticks;
    private final Graph graph;

    public DataCollection(Graph g) {
        graph = g;
        graph.init();
        infectedCount = new ArrayList<>();
        recoveredCount = new ArrayList<>();
        deadCount = new ArrayList<>();
        susceptibleCount = new ArrayList<>();
        ticks = new ArrayList<>();

        syncGraphData();
    }

    /**
     * Move one tick forward the simulation, and collect data
     * @throws ExecutionException Exception happened during simulation
     * @throws InterruptedException simulation thread interrupted
     */
    public void simulate() throws ExecutionException, InterruptedException {
        graph.simulate();
        syncGraphData();
    }

    /**
     * Sync data from current graph state
     */
    private void syncGraphData() {
        infectedCount.add(graph.getInfectedCount());
        recoveredCount.add(graph.getRecoveredCount());
        deadCount.add(graph.getDeadCount());
        susceptibleCount.add(graph.getSusceptibleCount());
        ticks.add(getTick());
    }

    private boolean checkIndex(int index) {
        return index > -1 && index <= getTick();
    }

    /**
     * Get Simulation data by tick
     * @param start start tick
     * @param end end tick
     * @return List of data point, [ticks[], susceptibleCount[], infectedCount[], deadCount[], recoveredCount[]]
     */
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

    /**
     * Get all possible data from current simulation
     * @return See getDataByRange()
     */
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

    public int getTick() {
        return graph.getTick();
    }

    /** Proxy of container **/
    public void setInitialInfected(Graph.INITIAL_INFECTED_MODE m) {
        // When setting initial Infected, we need to update the infected count as well
        graph.setInitialInfected(m);
        infectedCount.remove(0);
        susceptibleCount.remove(0);
        infectedCount.add(0, graph.getInfectedCount());
        susceptibleCount.add(0, graph.getSusceptibleCount());
    }

    public boolean getInfected() {
        return graph.getInfected();
    }
}
