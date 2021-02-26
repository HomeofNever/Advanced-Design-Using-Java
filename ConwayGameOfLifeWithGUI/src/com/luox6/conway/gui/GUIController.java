package com.luox6.conway.gui;

import com.luox6.conway.Cell;
import com.luox6.conway.ConwayMap;
import com.luox6.conway.gui.models.MapModel;

import javax.swing.*;

public class GUIController {
    protected MapModel mapModel;
    protected GUIViewer guiViewer;

    public GUIController() {
        // Init Model
        mapModel = new MapModel(20, 20);
    }

    public void setGuiViewer(GUIViewer guiViewer) {
        this.guiViewer = guiViewer;
    }

    protected void start() {
        guiViewer.gameBoard.setConwayMap(mapModel.getLatestMap());
        guiViewer.stepActionPanel.updateStep(mapModel.getCurrentIndex());

        // Setting the frame visibility to true
        guiViewer.pack();
        guiViewer.setVisible(true);
    }

    public void cellButtonPressed(int row, int col) {
        if (!mapModel.isStarted()) {
            Cell c = mapModel.getLatestMap().getCell(row, col);
            c.flip();
            guiViewer.gameBoard.setState(row, col, c);
        } else {
            JOptionPane.showMessageDialog(guiViewer,
                    "Current Map has started simulation and cannot be changed. Use 'Set as Begin' to edit from current map.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public void nextStepButtonPressed() {
        try {
            mapModel.runOnce();
            guiViewer.gameBoard.setConwayMap(mapModel.getCurrentConwayMap());
            guiViewer.stepActionPanel.updateStep(mapModel.getCurrentIndex());
            guiViewer.statusPanel.updateCalculatedSimulation(mapModel.getLatestIndex());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(guiViewer,
                    e.getMessage(),
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public void backStepButtonPressed() {
        try {
            mapModel.goBack();
            guiViewer.gameBoard.setConwayMap(mapModel.getCurrentConwayMap());
            guiViewer.stepActionPanel.updateStep(mapModel.getCurrentIndex());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(guiViewer,
                    e.getMessage(),
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public void stepValueSet(String s) {
        int step = Integer.parseInt(s);
        try {
            mapModel.setCurrentIndex(step);
            guiViewer.gameBoard.setConwayMap(mapModel.getCurrentConwayMap());
            guiViewer.statusPanel.updateCalculatedSimulation(mapModel.getLatestIndex());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(guiViewer,
                    e.getMessage(),
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
        } finally {
            guiViewer.stepActionPanel.updateStep(mapModel.getCurrentIndex());
        }
    }

    public void exitButtonPressed() {
        System.exit(0);
    }

    public void resetButtonPressed() {
        mapModel.reset();
        guiViewer.gameBoard.setConwayMap(mapModel.getCurrentConwayMap());
        guiViewer.stepActionPanel.updateStep(mapModel.getCurrentIndex());
        guiViewer.statusPanel.resetSimulation();
    }

    public void setBeginButtonPressed() {
        ConwayMap m = mapModel.getCurrentConwayMap();
        m.clearTicks();
        mapModel = new MapModel(m);
        guiViewer.gameBoard.setConwayMap(mapModel.getCurrentConwayMap());
        guiViewer.stepActionPanel.updateStep(mapModel.getCurrentIndex());
        guiViewer.statusPanel.resetSimulation();
    }
}
