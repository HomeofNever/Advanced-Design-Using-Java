package com.luox6.epidemic.gui;


import com.luox6.epidemic.gui.components.StatusPanel;
import com.luox6.epidemic.gui.factories.Dialog;
import com.luox6.epidemic.gui.factories.FileChooser;
import com.luox6.epidemic.gui.models.Collector;
import com.luox6.epidemic.gui.models.UserSetting;
import com.luox6.epidemic.model.Graph;
import com.luox6.epidemic.utils.Parser;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;

public class GUIController {
    // Models
    protected Collector collector;
    // Viewers
    protected GUIViewer guiViewer;

    public GUIController() {
    }

    /**
     * Set reference of the Viewer object, for update purposes
     *
     * @param guiViewer
     */
    public void setGuiViewer(GUIViewer guiViewer) {
        this.guiViewer = guiViewer;
        this.collector = new Collector(this);
    }

    /**
     * Application entry
     */
    protected void start() {
        // Setting the frame visibility to true
        guiViewer.pack();
        guiViewer.setVisible(true);
    }

    /**
     * Exit button handler
     */
    public void exitButtonPressed() {
        System.exit(0);
    }

    /**
     * Reset button handler
     */
    public void resetButtonPressed() {
        if (collector.getStatus() == StatusPanel.SIMULATION_STATUS.AWAIT_DATA) {
            Dialog.genericWarningDialog(guiViewer, new Exception("Please set graph before reset graph!"));
        } else {
            collector.reset();
            updateGraphView();
        }
    }

    /**
     * File -> Open File MenuItem handler
     */
    public void openFile() {
        try {
            JFileChooser fc = FileChooser.loadMapDialog("Open Map File");
            if (fc.showOpenDialog(guiViewer) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fc.getSelectedFile();
                Graph g = Parser.loadGraph(new FileReader(selectedFile));
                // Apply new Graph
                setNewGraph(g);
                Dialog.genericSuccessDialog(guiViewer, "Graph has loaded successfully!");
            }
        } catch (Exception e) {
            Dialog.genericWarningDialog(guiViewer, e);
        }
    }

    /**
     * Action -> Configuration button handler
     */
    public void openConfigurationDialog() {
        guiViewer.configurationPanel.setVisible(true);
    }


    /* Methods below are for internal batch update */

    private void setNewGraph(Graph g) {
        collector.setGraph(g);
        updateGraphView();
    }

    public void updateGraphView() {
        guiViewer.graphPanel.updateData(collector);
        guiViewer.statusPanel.updateSimulationStatus(collector.getStatus());

        guiViewer.statusPanel.updateSimulation(
                collector.getDataCollection().getTick(),
                collector.getDataCollection().getCurrentSusceptibleCount(),
                collector.getDataCollection().getCurrentInfectedCount(),
                collector.getDataCollection().getCurrentDeadCount(),
                collector.getDataCollection().getCurrentRecoveredCount());
    }

    /**
     * Settings
     **/
    public void setSusceptibleColor() {
        UserSetting.setSusceptibleColor(JColorChooser.showDialog(guiViewer, "Set Susceptible Color", UserSetting.getSusceptibleColor()));
        guiViewer.configurationPanel.updateSettings();
    }

    public void setDeadColor() {
        UserSetting.setDeadColor(JColorChooser.showDialog(guiViewer, "Set Dead Color", UserSetting.getDeadColor()));
        guiViewer.configurationPanel.updateSettings();
    }

    public void setInfectedColor() {
        UserSetting.setInfectedColor(JColorChooser.showDialog(guiViewer, "Set Infected Color", UserSetting.getInfectedColor()));
        guiViewer.configurationPanel.updateSettings();
    }

    public void setRecoveredColor() {
        UserSetting.setRecoveredColor(JColorChooser.showDialog(guiViewer, "Set Recovered Color", UserSetting.getRecoveredColor()));
        guiViewer.configurationPanel.updateSettings();
    }

    public void setValueN(String text) {
        try {
            int i = Integer.parseInt(text);
            if (i < 0) {
                throw new Exception("N(random infected nodes) should be at least 0");
            }

            UserSetting.setValueN(i);
        } catch (NumberFormatException e) {
            Dialog.numberParseDialog(guiViewer, e);
        } catch (Exception e) {
            Dialog.genericWarningDialog(guiViewer, e);
        } finally {
            guiViewer.configurationPanel.updateSettings();
        }
    }

    public void setValueS(String text) {
        try {
            int i = Integer.parseInt(text);
            if (i < 0) {
                throw new Exception("S(degree) should be at least 0");
            }

            UserSetting.setValueS(i);
        } catch (NumberFormatException e) {
            Dialog.numberParseDialog(guiViewer, e);
        } catch (Exception e) {
            Dialog.genericWarningDialog(guiViewer, e);
        } finally {
            guiViewer.configurationPanel.updateSettings();
        }
    }

    public void setValueK(String text) {
        try {
            int i = Integer.parseInt(text);
            if (i < 0) {
                throw new Exception("K(BFS level) should be at least 0");
            }

            UserSetting.setValueK(i);
        } catch (NumberFormatException e) {
            Dialog.numberParseDialog(guiViewer, e);
        } catch (Exception e) {
            Dialog.genericWarningDialog(guiViewer, e);
        } finally {
            guiViewer.configurationPanel.updateSettings();
        }
    }

    public void setValueD(String text) {
        try {
            double d = Double.parseDouble(text);
            if (d < 0 || d > 1) {
                throw new Exception("Dead possibility should between 0-1");
            }

            UserSetting.setValueD(d);
        } catch (NumberFormatException e) {
            Dialog.numberParseDialog(guiViewer, e);
        } catch (Exception e) {
            Dialog.genericWarningDialog(guiViewer, e);
        } finally {
            guiViewer.configurationPanel.updateSettings();
        }
    }

    public void setValueT(String text) {
        try {
            int i = Integer.parseInt(text);
            if (i < 1) {
                throw new Exception("Recovered tick should be larger than 0");
            }

            UserSetting.setValueT(i);
        } catch (NumberFormatException e) {
            Dialog.numberParseDialog(guiViewer, e);
        } catch (Exception e) {
            Dialog.genericWarningDialog(guiViewer, e);
        } finally {
            guiViewer.configurationPanel.updateSettings();
        }
    }

    public void setValueLambda(String text) {
        try {
            double d = Double.parseDouble(text);
            if (!(d > 0)) {
                throw new Exception("Lambda(Force of Infection) should be larger than 0");
            }

            UserSetting.setLambda(d);
        } catch (NumberFormatException e) {
            Dialog.numberParseDialog(guiViewer, e);
        } catch (Exception e) {
            Dialog.genericWarningDialog(guiViewer, e);
        } finally {
            guiViewer.configurationPanel.updateSettings();
        }
    }

    public void setNumThread(String text) {
        try {
            int i = Integer.parseInt(text);
            if (i < 1) {
                throw new Exception("Number of thread should be at least 1");
            }

            UserSetting.setThread(i);
        } catch (NumberFormatException e) {
            Dialog.numberParseDialog(guiViewer, e);
        } catch (Exception e) {
            Dialog.genericWarningDialog(guiViewer, e);
        } finally {
            guiViewer.configurationPanel.updateSettings();
        }
    }

    public void setNumStep(String text) {
        try {
            int i = Integer.parseInt(text);
            if (i < 0) {
                throw new Exception("Number of step should be at least 0");
            }

            UserSetting.setStep(i);
        } catch (NumberFormatException e) {
            Dialog.numberParseDialog(guiViewer, e);
        } catch (Exception e) {
            Dialog.genericWarningDialog(guiViewer, e);
        } finally {
            guiViewer.configurationPanel.updateSettings();
        }
    }

    public void startButtonPressed() {
        switch (collector.getStatus()) {
            case READY, PAUSED -> collector.start();
            case AWAIT_DATA -> Dialog.genericWarningDialog(guiViewer, new Exception("Please load graph first!"));
            case IN_PROGRESS -> Dialog.genericWarningDialog(guiViewer, new Exception("Simulation is in progress!"));
            case FINISHED -> Dialog.genericWarningDialog(guiViewer, new Exception("Simulation has finished, please reset and start again!"));
        }
    }

    public void pauseButtonPressed() {
        if (collector.getStatus() == StatusPanel.SIMULATION_STATUS.IN_PROGRESS) {
            collector.pause();
            Dialog.genericSuccessDialog(guiViewer, "Simulation paused!");
        } else {
            Dialog.genericWarningDialog(guiViewer, new Exception("Simulation is not running"));
        }
    }

    public void infectButtonPressed(Graph.INITIAL_INFECTED_MODE m) {
        if (collector.isInitInfected()) {
            Dialog.genericWarningDialog(guiViewer, new Exception("Graph has already init infected: reset to re-infected"));
        } else if (collector.getStatus() == StatusPanel.SIMULATION_STATUS.AWAIT_DATA){
            Dialog.genericWarningDialog(guiViewer, new Exception("Please load graph first!"));
        } else {
            collector.setInitInfected(m);
            updateGraphView();
            Dialog.genericSuccessDialog(guiViewer, "Infected set!");
        }
    }
}
