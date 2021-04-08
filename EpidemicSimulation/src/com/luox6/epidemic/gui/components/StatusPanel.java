package com.luox6.epidemic.gui.components;

import javax.swing.*;
import javax.swing.border.BevelBorder;

/**
 * Status bar of current map/simulation
 */
public class StatusPanel extends JPanel {
    /* Components */
    private final JLabel statusLabel;
    private final JLabel simulationLabel;
    private final JLabel cellLabel;

    /* Template Strings */
    public enum SIMULATION_STATUS {AWAIT_DATA, READY, IN_PROGRESS, PAUSED, FINISHED}
    private static final String SIMULATION_AWAIT_DATA = "Await data";
    private static final String SIMULATION_READY = "Ready";
    private static final String SIMULATION_IN_PROGRESS = "In Progress...";
    private static final String SIMULATION_PAUSED = "Paused";
    private static final String SIMULATION_FINISHED = "Finished";
    private static final String SIMULATION_COUNT = "Time %d ms | Current Tick(s): %d";
    private static final String SIMULATION_STATUS_COUNT = "Susceptible(%d), Infected(%d), Dead(%d), Recovered(%d)";

    public StatusPanel() {
        super();
        setBorder(new BevelBorder(BevelBorder.LOWERED));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Components
        statusLabel = new JLabel();
        simulationLabel = new JLabel();
        cellLabel = new JLabel();

        // Arrangement
        add(statusLabel);
        add(new JSeparator());
        add(cellLabel);
        add(new JSeparator());
        add(simulationLabel);

        // Init
        updateSimulation(0, 0, 0, 0, 0, 0);
        updateSimulationStatus(SIMULATION_STATUS.AWAIT_DATA);
    }


    /**
     * Update Simulation indicator
     * @param times current tick
     * @param susceptible current susceptible count
     * @param infected current infected count
     * @param dead current dead count
     * @param recovered current recovered count
     */
    public void updateSimulation(long time, int times, int susceptible, int infected, int dead, int recovered) {
        simulationLabel.setText(SIMULATION_COUNT.formatted(time, times));
        cellLabel.setText(SIMULATION_STATUS_COUNT.formatted(susceptible, infected, dead, recovered));
    }

    /**
     * Set simulation status
     * @param status simulation status
     */
    public void updateSimulationStatus(SIMULATION_STATUS status) {
        switch (status) {
            case READY -> statusLabel.setText(SIMULATION_READY);
            case AWAIT_DATA -> statusLabel.setText(SIMULATION_AWAIT_DATA);
            case IN_PROGRESS -> {
                statusLabel.setText(SIMULATION_IN_PROGRESS);
            }
            case PAUSED -> statusLabel.setText(SIMULATION_PAUSED);
            case FINISHED -> statusLabel.setText(SIMULATION_FINISHED);
        }
    }
}