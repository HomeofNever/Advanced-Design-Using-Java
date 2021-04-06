package com.luox6.epidemic.gui.components;

import com.luox6.epidemic.gui.GUIController;

import javax.swing.*;

public class TopMenuBar extends JMenuBar {
    /**
     * Controller Reference
     */
    private GUIController controller;
    private JMenu fileMenu;
    private JMenu actionMenu;

    /* Menu Items */
    private JMenuItem openFile;
    private JMenuItem exitProgram;
    private JMenuItem configuration;

    public TopMenuBar(GUIController controller) {
        super();
        this.controller = controller;
        fileMenu = new JMenu("File");
        actionMenu = new JMenu("Actions");
        openFile = new JMenuItem("Open file...");
        exitProgram = new JMenuItem("Exit");
        configuration = new JMenuItem("Configuration");

        fileMenu.add(openFile);
        fileMenu.addSeparator();
        actionMenu.add(configuration);
        actionMenu.addSeparator();
        actionMenu.add(exitProgram);

        add(fileMenu);
        add(actionMenu);

        setActions();
    }

    /**
     * Bind GUI action to controller
     */
    private void setActions() {
        openFile.addActionListener(e -> controller.openFile());
        configuration.addActionListener(e -> controller.openConfigurationDialog());
        exitProgram.addActionListener(e -> controller.exitButtonPressed());
    }
}
