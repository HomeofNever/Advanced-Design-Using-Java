package com.luox6.conway.gui.components;

import com.luox6.conway.gui.GUIController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VisualActionPanel extends JPanel {
    GUIController controller;

    JButton resetButton;
    JButton setBeginButton;

    public VisualActionPanel(GUIController controller) {
        super();
        this.controller = controller;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        resetButton = new JButton("Reset");
        setBeginButton = new JButton("Set as Begin");
        setBeginButton.setToolTipText("Set current map as a new game map");
        add(resetButton);
        add(setBeginButton);

        setActions();
    }

    public void setActions() {
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.resetButtonPressed();
            }
        });

        setBeginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setBeginButtonPressed();
            }
        });
    }
}
