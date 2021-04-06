package com.luox6.epidemic.gui.models;

import com.luox6.epidemic.gui.GUIController;
import com.luox6.epidemic.gui.components.StatusPanel;
import com.luox6.epidemic.model.DataCollection;
import com.luox6.epidemic.model.Graph;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class Collector {
    private Graph graph;
    private DataCollection dataCollection;
    private Thread simulate;
    private AtomicReference<StatusPanel.SIMULATION_STATUS> status;

    private GUIController guiController;

    public Collector(GUIController g) {
        guiController = g;
        status = new AtomicReference<>(StatusPanel.SIMULATION_STATUS.AWAIT_DATA);
        simulate = getNewThread();
        simulate.start();
    }

    public DataCollection getDataCollection() {
        return dataCollection;
    }

    public void start() {
        status.compareAndSet(StatusPanel.SIMULATION_STATUS.READY, StatusPanel.SIMULATION_STATUS.IN_PROGRESS);
        status.compareAndSet(StatusPanel.SIMULATION_STATUS.PAUSED, StatusPanel.SIMULATION_STATUS.IN_PROGRESS);
    }

    public void pause() {
        status.compareAndSet(StatusPanel.SIMULATION_STATUS.IN_PROGRESS, StatusPanel.SIMULATION_STATUS.PAUSED);
    }

    public StatusPanel.SIMULATION_STATUS getStatus() {
        return status.get();
    }

    public void reset() {
        Graph cpg = new Graph(graph);
        // Init graph with parameters
        cpg.setD(UserSetting.getValueD());
        cpg.setK(UserSetting.getValueK());
        cpg.setN(UserSetting.getValueN());
        cpg.setLambda(UserSetting.getLambda());
        cpg.setS(UserSetting.getValueS());
        cpg.setT(UserSetting.getValueT());
        cpg.setNumThread(UserSetting.getThread());
        dataCollection = new DataCollection(cpg);
        status.set(StatusPanel.SIMULATION_STATUS.READY);
    }

    private Thread getNewThread() {
        return new Thread(() -> {
            while (true) {
                if (status.get() == StatusPanel.SIMULATION_STATUS.IN_PROGRESS) {
                    if (dataCollection.getTick() < UserSetting.getStep()) {
                        // Do one per time, until interrupted or target reach
                        // to ensure state integrity
                        try {
                            dataCollection.simulate();
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        status.set(StatusPanel.SIMULATION_STATUS.FINISHED);
                    }
                    guiController.updateGraphView();
                } else {
                    /**
                     * @TODO
                     * It is actually better to submit to executor instead of wait,
                     * but it is working anyway, so let's do it.
                     * https://stackoverflow.com/a/19025596/13843585
                     */
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
        if (getStatus() != StatusPanel.SIMULATION_STATUS.AWAIT_DATA)
            return getDataCollection().getInfected();
        else
            return false;
    }

    public void setInitInfected(Graph.INITIAL_INFECTED_MODE m) {
        getDataCollection().setInitialInfected(m);
    }
}
