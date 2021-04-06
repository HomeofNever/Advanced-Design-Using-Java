package com.luox6.epidemic.gui.components;

import com.luox6.epidemic.gui.GUIController;
import com.luox6.epidemic.gui.factories.Button;
import com.luox6.epidemic.gui.models.UserSetting;

import javax.swing.*;
import java.awt.*;

/**
 * Configuration sub panel
 */
public class ConfigurationPanel extends JFrame {
    /* Components */
    private JButton susceptibleColor;
    private JButton infectedColor;
    private JButton deadColor;
    private JButton recoveredColor;
    private JTextField valueN;
    private JTextField valueS;
    private JTextField valueK;
    private JTextField valueD;
    private JTextField valueT;
    private JTextField valueLambda;
    private JTextField numThread;
    private JTextField numStep;
    private JLabel systemThread;

    /**
     * Controller Reference
     */
    private GUIController controller;

    public ConfigurationPanel(GUIController controller) {
        this.controller = controller;

        // Set layout
        setTitle("Configuration");
        setLayout(new GridLayout(13, 2));

        susceptibleColor = Button.transparentButton("Click");
        infectedColor = Button.transparentButton("Click");
        deadColor = Button.transparentButton("Click");
        recoveredColor = Button.transparentButton("Click");

        valueN = new JTextField();
        valueS = new JTextField();
        valueK = new JTextField();
        valueD = new JTextField();
        valueT = new JTextField();
        valueLambda = new JTextField();

        numThread = new JTextField();
        numStep = new JTextField();

        systemThread = new JLabel();

        // Add all components.
        add(new JLabel("\tSusceptible Color"));
        add(susceptibleColor);
        add(new JLabel("\tDead Color:"));
        add(deadColor);
        add(new JLabel("\tInfected Color:"));
        add(infectedColor);
        add(new JLabel("\tRecovered Color:"));
        add(recoveredColor);
        add(new JLabel("\tValue N:"));
        add(valueN);
        add(new JLabel("\tValue S:"));
        add(valueS);
        add(new JLabel("\tValue K:"));
        add(valueK);
        add(new JLabel("\tValue D:"));
        add(valueD);
        add(new JLabel("\tValue T:"));
        add(valueT);
        add(new JLabel("\tValue Lambda:"));
        add(valueLambda);
        add(new JLabel("\tAvailable Cores (recommended # of threads):"));
        add(systemThread);
        add(new JLabel("\tNumber of Thread:"));
        add(numThread);
        add(new JLabel("\tNumber of Steps:"));
        add(numStep);
        pack();

        setLocationRelativeTo(null);
        updateSettings();
        setActions();
    }

    /**
     * Update panel with latest settings
     */
    public void updateSettings() {
        susceptibleColor.setBackground(UserSetting.getSusceptibleColor());
        deadColor.setBackground(UserSetting.getDeadColor());
        infectedColor.setBackground(UserSetting.getInfectedColor());
        recoveredColor.setBackground(UserSetting.getRecoveredColor());

        valueN.setText(String.valueOf(UserSetting.getValueN()));
        valueS.setText(String.valueOf(UserSetting.getValueS()));
        valueK.setText(String.valueOf(UserSetting.getValueK()));
        valueD.setText(String.valueOf(UserSetting.getValueD()));
        valueT.setText(String.valueOf(UserSetting.getValueT()));
        valueLambda.setText(String.valueOf(UserSetting.getLambda()));
        numThread.setText(String.valueOf(UserSetting.getThread()));
        numStep.setText(String.valueOf(UserSetting.getStep()));

        systemThread.setText(String.valueOf(Runtime.getRuntime().availableProcessors()));
    }

    /**
     * Bind action to Controller
     */
    private void setActions() {
        susceptibleColor.addActionListener(e -> controller.setSusceptibleColor());
        deadColor.addActionListener(e -> controller.setDeadColor());
        infectedColor.addActionListener(e -> controller.setInfectedColor());
        recoveredColor.addActionListener(e -> controller.setRecoveredColor());

        valueN.addActionListener(e -> controller.setValueN(valueN.getText()));
        valueS.addActionListener(e -> controller.setValueS(valueS.getText()));
        valueK.addActionListener(e -> controller.setValueK(valueK.getText()));
        valueD.addActionListener(e -> controller.setValueD(valueD.getText()));
        valueT.addActionListener(e -> controller.setValueT(valueT.getText()));
        valueLambda.addActionListener(e -> controller.setValueLambda(valueLambda.getText()));
        numThread.addActionListener(e -> controller.setNumThread(numThread.getText()));
        numStep.addActionListener(e -> controller.setNumStep(numThread.getText()));
    }
}
