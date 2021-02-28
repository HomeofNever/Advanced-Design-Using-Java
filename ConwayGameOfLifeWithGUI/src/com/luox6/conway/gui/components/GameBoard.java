package com.luox6.conway.gui.components;

import com.luox6.conway.Cell;
import com.luox6.conway.ConwayMap;
import com.luox6.conway.gui.GUIController;
import com.luox6.conway.gui.models.UserSetting;

import javax.swing.*;
import java.awt.*;

/**
 * Main Convey GOL game map visual panel
 */
public class GameBoard extends JPanel {
    /**
     * Controller Reference
     */
    private GUIController controller;

    /**
     * Map row
     */
    private int row = 0;
    /**
     * Map col
     */
    private int col = 0;

    /**
     * Cell representations
     */
    private JCell cells[][];

    public GameBoard(GUIController controller) {
        super();
        this.controller = controller;
    }

    /**
     * Initialization Cells on the map
     * @param c ConwayMap to init
     */
    protected void init(ConwayMap c) {
        removeAll();
        row = c.getMapRowLength();
        col = c.getMapColLength();
        setLayout(new GridLayout(row, col, 1, 1));
        cells = new JCell[getRow()][getCol()];
        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < getCol(); j++) {
                JCell b = new JCell(i, j);
                b.addActionListener(e -> controller.cellButtonPressed(b.getI(), b.getJ()));
                cells[i][j] = b;
                add(b);
            }
        }
    }

    /**
     * Update cell state with given map and ticks
     * @param c ConwayMap providing states
     * @param ticks Current Round for cell shades
     */
    protected void setState(ConwayMap c, int ticks) {
        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < getCol(); j++) {
                setState(i, j, c.getCell(i, j), ticks);
            }
        }
    }

    /**
     * Set single cell status with given coordinates, cell object, and round/tick
     * @param i map row
     * @param j map col
     * @param c Cell at given location
     * @param ticks Current map index
     */
    public void setState(int i, int j, Cell c, int ticks) {
        JCell b = cells[i][j];
        if (c.isAlive()) {
            Color ac = UserSetting.getAliveColor();
            int level = UserSetting.getMaxLevelShade();
            int block = ticks / level;
            // Apply shade based on the total ticks / max shade level
            // find if current survival time need to be increment
            if (level > 1 && block > 0) {
                for (int d = 0; d < c.getTimes() / block; d++) {
                    ac = ac.darker();
                }
            }
            b.setBackground(ac);
        } else {
            b.setBackground(UserSetting.getDeadColor());
        }
        b.setForeground(UserSetting.getTextColor());

        // Show or hide number
        if (UserSetting.getSurvivalStatus()) {
            String times = String.valueOf(c.getTimes());
            b.setText(times);
            b.setToolTipText(times);
        } else {
            b.setText("");
            b.setToolTipText("");
        }
    }

    /**
     * Update view to given map, re-initialize map
     * @param c map top
     * @param ticks
     */
    public void setConwayMap(ConwayMap c, int ticks) {
        if (c.getMapRowLength() != row || c.getMapColLength() != col) {
            init(c);
        }
        setState(c, ticks);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
