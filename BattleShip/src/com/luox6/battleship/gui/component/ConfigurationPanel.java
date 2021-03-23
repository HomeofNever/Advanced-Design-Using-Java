package com.luox6.battleship.gui.component;

import com.luox6.battleship.gui.GUIController;
import com.luox6.battleship.gui.factory.Button;
import com.luox6.battleship.gui.model.UserSetting;

import javax.swing.*;
import java.awt.*;

/**
 * Configuration sub panel
 */
public class ConfigurationPanel extends JFrame {
    /* Components */
    private JButton hiddenCellColor;
    private JButton discoverCellColor;
    private JButton markColor;
    private JButton gridSize;
    private JTextField timeLimit;

    /**
     * Controller Reference
     */
    private GUIController controller;

    public ConfigurationPanel(GUIController controller) {
        this.controller = controller;

        // Set layout
        setTitle("Configuration");
        setLayout(new GridLayout(4, 2));

        discoverCellColor = Button.transparentButton("Click");
        markColor = Button.transparentButton("Click");
        gridSize = Button.transparentButton("Click to view/set");
        gridSize = new JButton("Click");
        timeLimit = new JTextField();

        // Add all components.
        add(new JLabel("\tDiscover Cell Color:"));
        add(discoverCellColor);
        add(new JLabel("\tShip Mark Color:"));
        add(markColor);
        add(new JLabel("Set Grid Size"));
        add(gridSize);
        add(new JLabel("Time limit per round"));
        add(timeLimit);
        pack();

        setLocationRelativeTo(null);
        updateSettings();
        setActions();
    }

    /**
     * Update panel with latest settings
     */
    public void updateSettings() {
        discoverCellColor.setBackground(UserSetting.getDiscoverColor());
        markColor.setBackground(UserSetting.getMarkColor());
        timeLimit.setText(String.valueOf(UserSetting.getTimeLimit()));
    }

    /**
     * Bind action to Controller
     */
    private void setActions() {
        discoverCellColor.addActionListener(e -> controller.setDiscoverColor());
        markColor.addActionListener(e -> controller.setMarkColor());
        gridSize.addActionListener(e -> controller.setGridSize());
        timeLimit.addActionListener(e -> controller.setTimeLimit(timeLimit.getText()));
    }
}
