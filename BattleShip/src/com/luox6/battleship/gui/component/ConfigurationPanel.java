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

    /**
     * Controller Reference
     */
    private GUIController controller;

    public ConfigurationPanel(GUIController controller) {
        this.controller = controller;

        // Set layout
        setTitle("Configuration");
        setLayout(new GridLayout(4, 2));

        hiddenCellColor = Button.transparentButton("Click");
        discoverCellColor = Button.transparentButton("Click");
        markColor = Button.transparentButton("Click");

        // Add all components.
        add(new JLabel("\tHidden Cell Color"));
        add(hiddenCellColor);
        add(new JLabel("\tDiscover Cell Color:"));
        add(discoverCellColor);
        add(new JLabel("\tShip Mark Color:"));
        add(markColor);
        gridSize = new JButton("Set Grid Size");
        add(gridSize);
        pack();

        setLocationRelativeTo(null);
        updateSettings();
        setActions();
    }

    /**
     * Update panel with latest settings
     */
    public void updateSettings() {
        hiddenCellColor.setBackground(UserSetting.getHiddenColor());
        discoverCellColor.setBackground(UserSetting.getDiscoverColor());
        markColor.setBackground(UserSetting.getMarkColor());
    }

    /**
     * Bind action to Controller
     */
    private void setActions() {
        hiddenCellColor.addActionListener(e -> controller.setHiddenColor());
        discoverCellColor.addActionListener(e -> controller.setDiscoverColor());
        markColor.addActionListener(e -> controller.setMarkColor());
        gridSize.addActionListener(e -> controller.setGridSize());
    }
}
