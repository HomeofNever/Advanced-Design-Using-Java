package com.luox6.conway.gui.components;

import javax.swing.*;
import javax.swing.border.BevelBorder;

public class StatusPanel extends JPanel {
    JLabel statusLabel;
    JLabel simulationLabel;
    JLabel cellLabel;

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

    public void resetSimulation() {
        simulationLabel.setText(SIMULATION_COUNT.formatted(0));
        statusLabel.setText(SIMULATION_NOT_STARTED);
    }

    public void updateCalculatedSimulation(int times) {
        simulationLabel.setText(SIMULATION_COUNT.formatted(times));
        statusLabel.setText(SIMULATION_STARTED);
    }

    public void updateCellStatus(int alive, int dead, int num, int row, int col) {
        cellLabel.setText(SIMULATION_CELL_COUNT.formatted(alive, dead, num, row, col));
    }
}
