package com.luox6.epidemic.gui.models;

import com.luox6.epidemic.gui.GUIController;
import com.luox6.epidemic.gui.components.StatusPanel;
import com.luox6.epidemic.model.DataCollection;
import com.luox6.epidemic.model.Graph;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Xinhao Luo
 * @version 0.0.1
 */
public class Collector {
    private Graph graph;
    private DataCollection dataCollection;
    private final AtomicReference<StatusPanel.SIMULATION_STATUS> status;

    private final GUIController guiController;

    private long elapseTime = 0;

    public Collector(GUIController g) {
        guiController = g;
        status = new AtomicReference<>(StatusPanel.SIMULATION_STATUS.AWAIT_DATA);
        Thread simulate = getNewThread();
        simulate.start();
    }

    /** Actions **/

    public void start() {
        status.compareAndSet(StatusPanel.SIMULATION_STATUS.READY, StatusPanel.SIMULATION_STATUS.IN_PROGRESS);
        status.compareAndSet(StatusPanel.SIMULATION_STATUS.PAUSED, StatusPanel.SIMULATION_STATUS.IN_PROGRESS);
        elapseTime = 0;
    }

    public void pause() {
        status.compareAndSet(StatusPanel.SIMULATION_STATUS.IN_PROGRESS, StatusPanel.SIMULATION_STATUS.PAUSED);
    }

    /**
     * Reset Graph with initial loaded data
     */
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
        cpg.setSeed(UserSetting.getSeed());
        dataCollection = new DataCollection(cpg);
        status.set(StatusPanel.SIMULATION_STATUS.READY);
        elapseTime = 0;
    }

    private Thread getNewThread() {
        return new Thread(() -> {
            long time;
            while (true) {
                if (status.get() == StatusPanel.SIMULATION_STATUS.IN_PROGRESS) {
                    if (dataCollection.getTick() < UserSetting.getStep()) {
                        // Do one per time, until interrupted or target reach
                        // to ensure state integrity
                        try {
                            time = System.nanoTime();
                            dataCollection.simulate();
                            elapseTime += System.nanoTime() - time;
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
                     * but it is working anyway, so let's do so for now.
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
    public DataCollection getDataCollection() {
        return dataCollection;
    }

    public StatusPanel.SIMULATION_STATUS getStatus() {
        return status.get();
    }

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

    public long getElapsedTime() {
        return elapseTime;
    }

    public void setInitInfected(Graph.INITIAL_INFECTED_MODE m) {
        getDataCollection().setInitialInfected(m);
    }
}
