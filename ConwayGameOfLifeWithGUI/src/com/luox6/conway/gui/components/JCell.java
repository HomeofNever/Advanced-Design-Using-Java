package com.luox6.conway.gui.components;

import com.luox6.conway.Cell;

import javax.swing.*;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;

public class JCell extends JButton {
    int i;
    int j;

    public JCell(int i, int j) {
        super();
        setOpaque(true);
        setBorderPainted(false);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}
