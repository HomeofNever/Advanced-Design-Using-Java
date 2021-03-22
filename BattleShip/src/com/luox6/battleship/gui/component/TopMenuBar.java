package com.luox6.battleship.gui.component;

import com.luox6.battleship.gui.GUIController;

import javax.swing.*;

public class TopMenuBar extends JMenuBar {
    /**
     * Controller Reference
     */
    private GUIController controller;
    private JMenu actionMenu;

    /* Menu Items */
    private JMenuItem exitProgram;
    private JMenuItem configuration;

    public TopMenuBar(GUIController controller) {
        super();
        this.controller = controller;
        actionMenu = new JMenu("Actions");
        exitProgram = new JMenuItem("Exit");
        configuration = new JMenuItem("Configuration");

        actionMenu.add(configuration);
        actionMenu.addSeparator();
        actionMenu.add(exitProgram);

        add(actionMenu);

        setActions();
    }

    /**
     * Bind GUI action to controller
     */
    private void setActions() {
        configuration.addActionListener(e -> controller.openConfigurationDialog());
        exitProgram.addActionListener(e -> controller.exitButtonPressed());
    }
}
