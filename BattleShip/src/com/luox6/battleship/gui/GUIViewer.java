package com.luox6.battleship.gui;

import com.luox6.battleship.gui.component.*;

import javax.swing.*;
import java.awt.*;

public class GUIViewer extends JFrame {
    /**
     * Reference of the controller, for notification purpose
     */
    protected GUIController guiController;

    /**
     * Top Menu Bar section
     */
    protected TopMenuBar topMenuBar;

    /**
     * Left side quick tool bar
     */
    protected VisualActionPanel visualActionPanel;

    /**
     * Main ship section
     */
    protected ShipBoard shipBoard;

    /**
     * Lower bar of the status
     */
    protected StatusPanel statusPanel;

    /**
     * User Configuration panel
     */
    protected ConfigurationPanel configurationPanel;

    /**
     * Main layout panel
     */
    private JPanel mainPanel;

    public GUIViewer(GUIController guiController) {
        // Creating instance of JFrame
        super("BattleShip");
        this.guiController = guiController;
        setSize(800, 600);
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        // Init Layout
        init();
    }

    /**
     * Initialization subcomponents and set it to the corresponded location of the layout
     */
    private void init() {
        mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        // Set their locations
        setFrame();
        // setPageStart();
        setCenter();
        setPageLeft();
        setPageEnd();

        configurationPanel = new ConfigurationPanel(guiController);
    }


    /* The method below are layout settings */
    private void setPageLeft() {
        visualActionPanel = new VisualActionPanel(guiController);
        mainPanel.add(visualActionPanel, BorderLayout.LINE_START);
    }

    private void setPageEnd() {
        statusPanel = new StatusPanel();
        mainPanel.add(statusPanel, BorderLayout.PAGE_END);
    }

    private void setFrame() {
        topMenuBar = new TopMenuBar(guiController);
        setJMenuBar(topMenuBar);
    }

    private void setCenter() {
        shipBoard = new ShipBoard(guiController);
        mainPanel.add(shipBoard, BorderLayout.CENTER);
    }
}
