package com.luox6.conway.gui.components;

import com.luox6.conway.gui.GUIController;

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
    private JMenuItem saveFile;
    private JMenuItem saveMultipleFile;
    private JMenuItem exitProgram;
    private JMenuItem configuration;

    public TopMenuBar(GUIController controller) {
        super();
        this.controller = controller;
        fileMenu = new JMenu("File");
        actionMenu = new JMenu("Actions");
        openFile = new JMenuItem("Open file...");
        saveFile = new JMenuItem("Save Current map as ...");
        saveMultipleFile = new JMenuItem("Save range of maps as ...");
        exitProgram = new JMenuItem("Exit");
        configuration = new JMenuItem("Configuration");

        fileMenu.add(openFile);
        fileMenu.addSeparator();
        fileMenu.add(saveFile);
        fileMenu.add(saveMultipleFile);
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
        saveFile.addActionListener(e -> controller.saveFile());
        saveMultipleFile.addActionListener(e -> controller.showMultipleFilesPanel());
        configuration.addActionListener(e -> controller.openConfigurationDialog());
        exitProgram.addActionListener(e -> controller.exitButtonPressed());
    }
}
