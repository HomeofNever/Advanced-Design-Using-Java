package com.luox6.conway.gui.components;

import com.luox6.conway.gui.GUIController;

import javax.swing.*;
import java.awt.*;

/**
 * Step action components
 */
public class StepActionPanel extends JPanel {
    /**
     * Controller Reference
     */
    private GUIController controller;

    /* Components */
    private JTextField textField;
    private JButton goBack;
    private JButton goForward;

    public StepActionPanel(GUIController controller) {
        super(new FlowLayout());
        this.controller = controller;

        goBack = new JButton("<<");
        textField = new JTextField(5);
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        goForward = new JButton(">>");

        add(goBack);
        add(textField);
        add(goForward);

        setActions();
    }

    /**
     * Bind GUI action to controller
     */
    private void setActions() {
        goForward.addActionListener(e -> controller.nextStepButtonPressed());
        goBack.addActionListener(e -> controller.backStepButtonPressed());
        textField.addActionListener(e -> controller.stepValueSet(textField.getText()));
    }

    /**
     * Update map textField
     * @param i current map index
     */
    public void updateStep(Integer i) {
        textField.setText(i.toString());
    }
}
