package com.luox6.conway.gui.components;

import com.luox6.conway.gui.GUIController;
import com.luox6.conway.gui.models.UserSetting;
import com.luox6.conway.gui.factories.Button;

import javax.swing.*;
import java.awt.*;

public class ConfigurationPanel extends JFrame {
    JButton liveCellColor;
    JButton deadCellColor;
    JButton textColor;
    JCheckBox survivalStatus;
    JTextField maxShadeLevel;

    GUIController controller;

    public ConfigurationPanel(GUIController controller) {
        this.controller = controller;

        // Set layout
        setTitle("UI Configuration");
        setLayout(new GridLayout(5, 2));

        liveCellColor = Button.transparentButton("Click");
        deadCellColor = Button.transparentButton("Click");
        textColor = Button.transparentButton("Click");
        maxShadeLevel = new JTextField();
        survivalStatus = new JCheckBox();

        // Add all components.
        add(new JLabel("\tLive Cell Color"));
        add(liveCellColor);
        add(new JLabel("\tDead Cell Color:"));
        add(deadCellColor);
        add(new JLabel("\tText Color:"));
        add(textColor);
        add(new JLabel("\tMax Shade Level:"));
        add(maxShadeLevel);
        add(new JLabel("\tSurvival Status:"));
        add(survivalStatus);
        pack();

        setLocationRelativeTo(null);
        updateSettings();
        setActions();
    }

    public void updateSettings() {
        liveCellColor.setBackground(UserSetting.getAliveColor());
        deadCellColor.setBackground(UserSetting.getDeadColor());
        textColor.setBackground(UserSetting.getTextColor());
        survivalStatus.setSelected(UserSetting.getSurvivalStatus());
        maxShadeLevel.setText(String.valueOf(UserSetting.getMaxLevelShade()));
    }

    private void setActions() {
        liveCellColor.addActionListener(e -> controller.setLiveCellColor());
        deadCellColor.addActionListener(e -> controller.setDeadCellColor());
        textColor.addActionListener(e -> controller.setTextColor());
        survivalStatus.addActionListener(e -> controller.setSurvivalStatus(survivalStatus.isSelected()));
        maxShadeLevel.addActionListener(e -> controller.setMaxShadeLevel(maxShadeLevel.getText()));
    }
}
