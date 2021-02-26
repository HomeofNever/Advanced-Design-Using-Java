package com.luox6.conway.gui.components;

import com.luox6.conway.gui.GUIController;
import com.luox6.conway.gui.models.MapModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StepActionPanel extends JPanel {
    GUIController controller;

    JTextField textField;
    JButton goBack;
    JButton goForward;

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

    private void setActions() {
        goForward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.nextStepButtonPressed();
            }
        });

        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.backStepButtonPressed();
            }
        });

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.stepValueSet(textField.getText());
            }
        });
    }

    public void updateStep(Integer i) {
        textField.setText(i.toString());
    }
}
