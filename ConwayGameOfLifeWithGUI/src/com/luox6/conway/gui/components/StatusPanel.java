package com.luox6.conway.gui.components;

import javax.swing.*;
import javax.swing.border.BevelBorder;

/**
 * Status bar of current map/simulation
 */
public class StatusPanel extends JPanel {
    /* Components */
    private JLabel statusLabel;
    private JLabel simulationLabel;
    private JLabel cellLabel;

    /* Template Strings */
    private static final String SIMULATION_NOT_STARTED = "Simulation: Ready to start";
    private static final String SIMULATION_STARTED = "Simulation: In Progress...";
    private static final String SIMULATION_COUNT = "Calculated Simulation(s): %d";
    private static final String SIMULATION_CELL_COUNT = "Alive(%d), Dead(%d), Size(%d[%dx%d])";

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
        updateCalculatedSimulation(0);
    }

    /**
     * Reset status bar status
     */
    public void resetSimulation() {
        updateCalculatedSimulation(0);
    }

    /**
     * Update status bar current calculation number
     * @param times latest map index
     */
    public void updateCalculatedSimulation(int times) {
        simulationLabel.setText(SIMULATION_COUNT.formatted(times));
        if (times == 0) {
            statusLabel.setText(SIMULATION_NOT_STARTED);
        } else {
            statusLabel.setText(SIMULATION_STARTED);
        }
    }

    /**
     * Update Status with current map info
     * @param alive number of live cells on the map
     * @param dead number of dead cells on the map
     * @param num number of cells on the map
     * @param row map row
     * @param col map col
     */
    public void updateCellStatus(int alive, int dead, int num, int row, int col) {
        cellLabel.setText(SIMULATION_CELL_COUNT.formatted(alive, dead, num, row, col));
    }
}
