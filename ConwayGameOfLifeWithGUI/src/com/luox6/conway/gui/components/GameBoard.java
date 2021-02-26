package com.luox6.conway.gui.components;

import com.luox6.conway.Cell;
import com.luox6.conway.ConwayMap;
import com.luox6.conway.gui.GUIController;
import com.luox6.conway.gui.UserSetting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard extends JPanel {
    GUIController controller;
    int row = 0;
    int col = 0;

    private JCell cells[][];

    public GameBoard(GUIController controller) {
        super();
        this.controller = controller;
    }

    protected void init(ConwayMap c) {
        removeAll();
        row = c.getMapRowLength();
        col = c.getMapColLength();
        setLayout(new GridLayout(row, col));
        cells = new JCell[getRow()][getCol()];
        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < getCol(); j++) {
                JCell b = new JCell(i, j);
                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller.cellButtonPressed(b.getI(), b.getJ());
                    }
                });
                cells[i][j] = b;
                add(b);
            }
        }
    }

    protected void setState(ConwayMap c) {
        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < getCol(); j++) {
                setState(i, j, c.getCell(i, j));
            }
        }
    }

    public void setState(int i, int j, Cell c) {
        JCell b = cells[i][j];
        if (c.isAlive()) {
            b.setBackground(UserSetting.getAliveColor());
        } else {
            b.setBackground(UserSetting.getDeadColor());
        }
        b.setForeground(Color.BLACK);
        if (UserSetting.getSurvivalStatus()) {
            String times = String.valueOf(c.getTimes());
            b.setText(times);
            b.setToolTipText(times);
        } else {
            b.setText("");
            b.setToolTipText("");
        }
    }

    public void setConwayMap(ConwayMap c) {
        if (c.getMapRowLength() != row || c.getMapColLength() != col) {
            init(c);
        }
        setState(c);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
