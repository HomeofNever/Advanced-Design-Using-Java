package com.luox6.conway.gui;

import com.luox6.conway.gui.components.*;

import javax.swing.*;
import java.awt.*;

public class GUIViewer extends JFrame {
    protected GUIController guiController;

    protected TopMenuBar topMenuBar;

    protected StatusPanel statusPanel;
    protected VisualActionPanel visualActionPanel;
    protected StepActionPanel stepActionPanel;
    protected GameBoard gameBoard;

    protected ConfigurationPanel configurationPanel;
    protected RangeSelectionPanel rangeSelectionPanel;

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

    private void init() {
        mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        // Init Components
        setFrame();
        setPageStart();
        setCenter();
        setPageLeft();
        setPageEnd();

        configurationPanel = new ConfigurationPanel(guiController);
        rangeSelectionPanel = new RangeSelectionPanel(guiController);
    }


    private void setPageStart() {
        stepActionPanel = new StepActionPanel(guiController);
        mainPanel.add(stepActionPanel, BorderLayout.PAGE_START);
    }

    private void setPageLeft() {
        visualActionPanel = new VisualActionPanel(guiController);
        mainPanel.add(visualActionPanel, BorderLayout.LINE_START);
    }

    private void setPageEnd () {
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
