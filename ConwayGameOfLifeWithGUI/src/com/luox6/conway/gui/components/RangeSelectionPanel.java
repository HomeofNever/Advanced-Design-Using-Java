package com.luox6.conway.gui.components;

import com.luox6.conway.gui.GUIController;
import com.luox6.conway.gui.models.UserSetting;

import javax.swing.*;
import java.awt.*;

public class RangeSelectionPanel extends JFrame {
    private static final String END_RANGE_FORMAT = "End Index (%d): ";

    JTextField startIndex;
    JTextField endIndex;
    JTextField outputFileFormat;
    JLabel endRangeNumber;
    JButton saveButton;

    GUIController controller;

    public RangeSelectionPanel(GUIController controller) {
        this.controller = controller;

        // Set layout
        setTitle("Save Range of Result");
        setLayout(new GridLayout(4, 2));

        startIndex = new JTextField("0");
        endIndex = new JTextField();
        endRangeNumber = new JLabel(END_RANGE_FORMAT);
        outputFileFormat = new JTextField();
        saveButton = new JButton("Save");
        updateSettings();

        // Add all components.
        add(new JLabel("\tStart Range (0): "));
        add(startIndex);
        add(endRangeNumber);
        add(endIndex);
        add(new JLabel("\tOutput Filename Format: "));
        add(outputFileFormat);
        add(new JLabel());
        add(saveButton);
        pack();

        setLocationRelativeTo(null);

        setActions();
    }

    public void updateEndIndex(int i) {
        endRangeNumber.setText(END_RANGE_FORMAT.formatted(i));
        endIndex.setText(String.valueOf(i));
    }

    public void updateSettings() {
        outputFileFormat.setText(UserSetting.getOutputFilesFormat());
    }

    private void setActions() {
        saveButton.addActionListener(e -> controller.saveMultipleFiles(
                startIndex.getText(), endIndex.getText(), outputFileFormat.getText()
        ));
    }
}
