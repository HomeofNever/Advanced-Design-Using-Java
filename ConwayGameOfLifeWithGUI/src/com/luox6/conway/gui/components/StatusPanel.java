package com.luox6.conway.gui.components;

import javax.swing.*;
import javax.swing.border.BevelBorder;

public class StatusPanel extends JPanel {
    JLabel statusLabel;
    JLabel simulationLabel;

    private static final String SIMULATION_NOT_STARTED = "Simulation: Ready to start";
    private static final String SIMULATION_STARTED = "Simulation: In Progress...";
    private static final String SIMULATION_COUNT = "Calculated Simulation(s): %d";

    public StatusPanel() {
        super();
        setBorder(new BevelBorder(BevelBorder.LOWERED));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Components
        statusLabel = new JLabel(SIMULATION_NOT_STARTED);
        simulationLabel = new JLabel(SIMULATION_COUNT.formatted(0));
        // Arrangement
        add(statusLabel);
        add(new JSeparator());
        add(simulationLabel);
    }

    public void resetSimulation() {
        simulationLabel.setText(SIMULATION_COUNT.formatted(0));
        statusLabel.setText(SIMULATION_NOT_STARTED);
    }

    public void updateCalculatedSimulation(int times) {
        simulationLabel.setText(SIMULATION_COUNT.formatted(times));
        statusLabel.setText(SIMULATION_STARTED);
    }
}
