package com.luox6.epidemic.gui.models;

import com.luox6.epidemic.gui.GUIController;
import com.luox6.epidemic.gui.components.StatusPanel;
import com.luox6.epidemic.model.DataCollection;
import com.luox6.epidemic.model.Graph;

public class Collector {
    private Graph graph;
    private DataCollection dataCollection;
    private Thread simulate;
    private StatusPanel.SIMULATION_STATUS status = StatusPanel.SIMULATION_STATUS.AWAIT_DATA;

    private GUIController guiController;

    public Collector(GUIController g) {
        guiController = g;
    }

    public DataCollection getDataCollection() {
        return dataCollection;
    }

    public void start() {
        simulate.start();
        status = StatusPanel.SIMULATION_STATUS.IN_PROGRESS;
    }

    public void pause() {
        simulate.interrupt();
        status = StatusPanel.SIMULATION_STATUS.PAUSED;
        simulate = getNewThread();
    }

    public StatusPanel.SIMULATION_STATUS getStatus() {
        return status;
    }

    public void reset() {
        Graph cpg = new Graph(graph);
        status = StatusPanel.SIMULATION_STATUS.READY;
        dataCollection = new DataCollection(cpg);
        simulate = getNewThread();
    }

    private Thread getNewThread() {
        return new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                if (dataCollection.getTick() < UserSetting.getStep()) {
                    // Do one per time, until interrupted or target reach
                    // to ensure state integrity
                    dataCollection.simulate();
                    guiController.updateGraphView();
                } else {
                    status = StatusPanel.SIMULATION_STATUS.FINISHED;
                    break;
                }
            }
        });
    }

    /** Getters and Setters **/
    public void setGraph(Graph graph) {
        this.graph = graph;
        reset();
    }

    public boolean isInitInfected() {
        return getDataCollection().getGraph().getInfected();
    }

    public void setInitInfected(Graph.INITIAL_INFECTED_MODE m) {
        if (isInitInfected()) {
            throw new RuntimeException("Graph has infected!");
        }
        getDataCollection().getGraph().setInitialInfected(m);
    }
}
