package com.luox6.conway.gui.components;

import com.luox6.conway.gui.GUIController;

import javax.swing.*;

public class TopMenuBar extends JMenuBar {
    GUIController controller;
    JMenu fileMenu;
    JMenu actionMenu;

    JMenuItem openFile;
    JMenuItem saveFile;
    JMenuItem saveMultipleFile;
    JMenuItem exitProgram;
    JMenuItem configuration;

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

    private void setActions() {
        openFile.addActionListener(e -> controller.openFile());
        saveFile.addActionListener(e -> controller.saveFile());
        saveMultipleFile.addActionListener(e -> controller.showMultipleFilesPanel());
        configuration.addActionListener(e -> controller.openConfigurationDialog());
        exitProgram.addActionListener(e -> controller.exitButtonPressed());
    }
}
