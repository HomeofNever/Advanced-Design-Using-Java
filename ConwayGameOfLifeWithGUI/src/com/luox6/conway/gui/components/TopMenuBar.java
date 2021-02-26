package com.luox6.conway.gui.components;

import com.luox6.conway.gui.GUIController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TopMenuBar extends JMenuBar {
    GUIController controller;
    JMenu fileMenu;
    JMenu actionMenu;

    JMenuItem openFile;
    JMenuItem saveFile;
    JMenuItem exitProgram;
    JMenuItem configuration;

    public TopMenuBar(GUIController controller) {
        super();
        this.controller = controller;
        fileMenu = new JMenu("File");
        actionMenu = new JMenu("Actions");
        openFile = new JMenuItem("Open file...");
        saveFile = new JMenuItem("Save file...");
        exitProgram = new JMenuItem("Exit");
        configuration = new JMenuItem("Configuration");

        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.addSeparator();

        actionMenu.add(configuration);
        actionMenu.addSeparator();
        actionMenu.add(exitProgram);

        add(fileMenu);
        add(actionMenu);

        setActions();
    }

    private void setActions() {
        exitProgram.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.exitButtonPressed();
            }
        });
    }
}
