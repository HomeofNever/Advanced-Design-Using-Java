package com.luox6.conway.gui;

import com.luox6.conway.Cell;
import com.luox6.conway.ConwayMap;
import com.luox6.conway.gui.factories.Dialog;
import com.luox6.conway.gui.factories.FileChooser;
import com.luox6.conway.gui.models.MapModel;
import com.luox6.conway.gui.models.UserSetting;
import com.luox6.conway.utils.ConveyFileParser;

import javax.swing.*;
import java.io.*;

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
        updateMapView();

        // Setting the frame visibility to true
        guiViewer.pack();
        guiViewer.setVisible(true);
    }

    public void cellButtonPressed(int row, int col) {
        if (!mapModel.isStarted()) {
            Cell c = mapModel.getLatestMap().getCell(row, col);
            c.flip();
            guiViewer.gameBoard.setState(row, col, c, mapModel.getCurrentIndex());
        } else {
            Dialog.genericWarningDialog(guiViewer,
                    new Exception("Current Map has started simulation and cannot be changed. " +
                            "Use 'Set as Begin' to edit from current map."));
        }
    }

    public void nextStepButtonPressed() {
        try {
            mapModel.runOnce();
            updateMapView();
            guiViewer.statusPanel.updateCalculatedSimulation(mapModel.getLatestIndex());
        } catch (Exception e) {
            Dialog.genericWarningDialog(guiViewer, e);
        }
    }

    public void backStepButtonPressed() {
        try {
            mapModel.goBack();
            updateMapView();
        } catch (Exception e) {
            Dialog.genericWarningDialog(guiViewer, e);
        }
    }

    public void stepValueSet(String s) {
        try {
            int step = Integer.parseInt(s);
            mapModel.setCurrentIndex(step);
            updateMapView();
        } catch (NumberFormatException e) {
            Dialog.numberParseDialog(guiViewer, e);
        } catch (Exception e) {
            Dialog.genericWarningDialog(guiViewer, e);
        } finally {
            guiViewer.stepActionPanel.updateStep(mapModel.getCurrentIndex());
        }
    }

    public void exitButtonPressed() {
        System.exit(0);
    }

    public void resetButtonPressed() {
        setNewMap(new ConwayMap(mapModel.getRow(), mapModel.getCol()));
    }

    public void setBeginButtonPressed() {
        ConwayMap m = mapModel.getCurrentConwayMap();
        m.clearTicks();
        setNewMap(m);
    }

    public void openFile() {
        try {
            JFileChooser fc = FileChooser.conwayMapDialog("Open Map File");
            if (fc.showOpenDialog(guiViewer) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fc.getSelectedFile();
                ConwayMap c = ConveyFileParser.parse(new BufferedReader(new FileReader(selectedFile)));
                setNewMap(c);
            }
        } catch (Exception e) {
            Dialog.genericWarningDialog(guiViewer, e);
        }
    }

    public void saveFile() {
        try {
            JFileChooser fc = FileChooser.conwayMapDialog("Save Map as");
            if (fc.showSaveDialog(guiViewer) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fc.getSelectedFile();
                BufferedWriter outputFile = new BufferedWriter(new FileWriter(selectedFile));
                outputFile.append(mapModel.getCurrentConwayMap().toString());
                outputFile.close();
            }
            JOptionPane.showMessageDialog(guiViewer,
                    "File saved successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            Dialog.genericWarningDialog(guiViewer, e);
        }
    }

    public void saveMultipleFiles(String startIndex, String endIndex, String fileFormat) {
        try {
            int si = Integer.parseInt(startIndex);
            if (si < 0 || si > mapModel.getLatestIndex()) {
                throw new Exception("Start Index out of range");
            }
            int ei = Integer.parseInt(endIndex);
            if (ei < 0 || ei > mapModel.getLatestIndex()) {
                throw new Exception("End Index out of range");
            }
            if (si > ei) {
                throw new Exception("Start Index is larger than end Index");
            }
            JFileChooser fc = FileChooser.conwayMapMultipleFileDialog("Save map Files to");
            if (fc.showSaveDialog(guiViewer) == JFileChooser.APPROVE_OPTION) {
                File selectedDirectory = fc.getSelectedFile();
                for (int i = si; i <= ei; i++) {
                    String filename = fileFormat.formatted(i);
                    BufferedWriter outputFile = new BufferedWriter(new FileWriter(new File(selectedDirectory, filename)));
                    outputFile.append(mapModel.getSpecificMap(i).toString());
                    outputFile.close();
                }
                UserSetting.setOutputFilesFormat(fileFormat);
                JOptionPane.showMessageDialog(guiViewer,
                        "Files saved successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            Dialog.numberParseDialog(guiViewer, e);
        } catch (Exception e) {
            Dialog.genericWarningDialog(guiViewer, e);
        }
    }

    public void openConfigurationDialog() {
        guiViewer.configurationPanel.setVisible(true);
    }

    public void setLiveCellColor() {
        UserSetting.setAliveColor(JColorChooser.showDialog(guiViewer, "Set Live Cell Color", UserSetting.getAliveColor()));
        guiViewer.configurationPanel.updateSettings();
        updateMapView();
    }

    public void setDeadCellColor() {
        UserSetting.setDeadColor(JColorChooser.showDialog(guiViewer, "Set Dead Cell Color", UserSetting.getDeadColor()));
        guiViewer.configurationPanel.updateSettings();
        updateMapView();
    }

    public void setTextColor() {
        UserSetting.setTextColor(JColorChooser.showDialog(guiViewer, "Set Text Color", UserSetting.getTextColor()));
        guiViewer.configurationPanel.updateSettings();
        updateMapView();
    }

    public void setSurvivalStatus(boolean status) {
        UserSetting.setShowSurvivalTimes(status);
        guiViewer.configurationPanel.updateSettings();
        updateMapView();
    }

    public void showMultipleFilesPanel() {
        guiViewer.rangeSelectionPanel.updateEndIndex(mapModel.getLatestIndex());
        guiViewer.rangeSelectionPanel.setVisible(true);
    }

    public void setMaxShadeLevel(String text) {
        try {
            int i = Integer.parseInt(text);
            if (i <= 0) {
                throw new Exception("Shade level must be larger than 0");
            }
            UserSetting.setMaxLevelShade(i);

            updateMapView();
        } catch (NumberFormatException e) {
            Dialog.numberParseDialog(guiViewer, e);
        } catch (Exception e) {
            Dialog.genericWarningDialog(guiViewer, e);
        }
    }


    private void setNewMap(ConwayMap c) {
        mapModel = new MapModel(c);
        updateMapView();
        guiViewer.statusPanel.resetSimulation();
    }

    private void updateMapView() {
        ConwayMap c = mapModel.getCurrentConwayMap();
        guiViewer.gameBoard.setConwayMap(c, mapModel.getCurrentIndex());
        guiViewer.stepActionPanel.updateStep(mapModel.getCurrentIndex());
        guiViewer.statusPanel.updateCellStatus(c.getAliveCellNum(), c.getDeadCellNum(), c.getTotalCellNum(), c.getMapRowLength(), c.getMapColLength());
    }
}
