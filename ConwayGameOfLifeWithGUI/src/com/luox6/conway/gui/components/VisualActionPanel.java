package com.luox6.conway.gui.components;

import com.luox6.conway.gui.GUIController;

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
    private JButton newButton;
    private JButton setBeginButton;
    private JButton settingButton;
    private JToolBar toolBar;

    public VisualActionPanel(GUIController controller) {
        super();
        this.controller = controller;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        toolBar = new JToolBar("Quick Actions", JToolBar.VERTICAL);

        add(toolBar);
        resetButton = new JButton("Reset");
        newButton = new JButton("New Map");
        setBeginButton = new JButton("Set as Begin");
        settingButton = new JButton("Settings");
        setBeginButton.setToolTipText("Set current map as a new game map");
        toolBar.add(resetButton);
        toolBar.add(newButton);
        toolBar.add(setBeginButton);
        toolBar.add(settingButton);

        setActions();
    }

    /**
     * Bind GUI action to controller
     */
    public void setActions() {
        resetButton.addActionListener(e -> controller.resetButtonPressed());
        setBeginButton.addActionListener(e -> controller.setBeginButtonPressed());
        settingButton.addActionListener(e -> controller.openConfigurationDialog());
        newButton.addActionListener(e -> controller.newMapDialog());
    }
}
