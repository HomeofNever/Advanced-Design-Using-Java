package com.luox6.battleship.gui.component;

import com.luox6.battleship.gui.GUIController;
import com.luox6.battleship.model.Directable;
import com.luox6.battleship.model.GameBoard;

import javax.swing.*;

/**
 * Quick action (left panel) components
 */
public class VisualActionPanel extends JPanel {
    /**
     * Controller Reference
     */
    private GUIController controller;

    /* Components */
    private JButton readyButton;
    private JButton settingButton;
    private JToolBar toolBar;

    public VisualActionPanel(GUIController controller) {
        super();
        this.controller = controller;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        toolBar = new JToolBar("Actions", JToolBar.VERTICAL);

        add(toolBar);
        readyButton = new JButton(GameBoard.PlayerStatus.READY.toString());
        settingButton = new JButton("Settings");
        toolBar.add(readyButton);
        toolBar.add(settingButton);

        setActions();
    }

    /**
     * Bind GUI action to controller
     */
    public void setActions() {
        readyButton.addActionListener(e -> controller.readyButtonPressed());
        settingButton.addActionListener(e -> controller.openConfigurationDialog());
    }
}
