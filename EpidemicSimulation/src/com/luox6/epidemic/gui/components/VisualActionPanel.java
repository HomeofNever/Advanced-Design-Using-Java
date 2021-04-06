package com.luox6.epidemic.gui.components;

import com.luox6.epidemic.gui.GUIController;
import com.luox6.epidemic.model.Graph;

import javax.swing.*;

/**
 * Quick action (left panel) components
 */
public class VisualActionPanel extends JPanel {
    /**
     * Controller Reference
     */
    private GUIController controller;

    /* Components */
    private JButton resetButton;
    private JButton startButton;
    private JButton pauseButton;
    private JButton randomInfectButton;
    private JButton BFSInfectButton;
    private JButton degreeInfectButton;
    private JButton settingButton;
    private JToolBar toolBar;

    public VisualActionPanel(GUIController controller) {
        super();
        this.controller = controller;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        toolBar = new JToolBar("Quick Actions", JToolBar.VERTICAL);

        add(toolBar);
        startButton = new JButton("Start");
        resetButton = new JButton("Reset");
        pauseButton = new JButton("Pause");
        settingButton = new JButton("Settings");
        randomInfectButton = new JButton("Random N Infect");
        degreeInfectButton = new JButton("Degree S Infect");
        BFSInfectButton = new JButton("BFS K Infect");

        toolBar.add(startButton);
        toolBar.add(pauseButton);
        toolBar.add(resetButton);
        toolBar.addSeparator();
        toolBar.add(randomInfectButton);
        toolBar.add(degreeInfectButton);
        toolBar.add(BFSInfectButton);
        toolBar.addSeparator();
        toolBar.add(settingButton);

        setActions();
    }

    /**
     * Bind GUI action to controller
     */
    public void setActions() {
        resetButton.addActionListener(e -> controller.resetButtonPressed());
        startButton.addActionListener(e -> controller.startButtonPressed());
        settingButton.addActionListener(e -> controller.openConfigurationDialog());
        pauseButton.addActionListener(e -> controller.pauseButtonPressed());
        randomInfectButton.addActionListener(e -> controller.infectButtonPressed(Graph.INITIAL_INFECTED_MODE.RANDOM_N));
        degreeInfectButton.addActionListener(e -> controller.infectButtonPressed(Graph.INITIAL_INFECTED_MODE.DEGREE_S));
        BFSInfectButton.addActionListener(e -> controller.infectButtonPressed(Graph.INITIAL_INFECTED_MODE.BFS_K));
    }
}
