package com.luox6.conway.gui;

import com.luox6.conway.gui.components.*;

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
     * Lower bar of the status
     */
    protected StatusPanel statusPanel;

    /**
     * Panel buttons on the left side of the application
     */
    protected VisualActionPanel visualActionPanel;

    /**
     * Top panel steppers
     */
    protected StepActionPanel stepActionPanel;

    /**
     * Main game board
     */
    protected GameBoard gameBoard;

    /**
     * User Configuration panel
     */
    protected ConfigurationPanel configurationPanel;

    /**
     * Save result with range panel
     */
    protected RangeSelectionPanel rangeSelectionPanel;

    /**
     * Main layout panel
     */
    private JPanel mainPanel;

    public GUIViewer(GUIController guiController) {
        // Creating instance of JFrame
        super("Convey Game of Life");
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
        setPageStart();
        setCenter();
        setPageLeft();
        setPageEnd();

        configurationPanel = new ConfigurationPanel(guiController);
        rangeSelectionPanel = new RangeSelectionPanel(guiController);
    }


    /* The method below are layout settings */

    private void setPageStart() {
        stepActionPanel = new StepActionPanel(guiController);
        mainPanel.add(stepActionPanel, BorderLayout.PAGE_START);
    }

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
        gameBoard = new GameBoard(guiController);
        mainPanel.add(gameBoard, BorderLayout.CENTER);
    }
}
