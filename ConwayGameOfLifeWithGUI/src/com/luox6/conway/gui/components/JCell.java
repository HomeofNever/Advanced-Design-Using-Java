package com.luox6.conway.gui.components;

import javax.swing.*;
import java.awt.*;

/**
 * Custom Cell class with row/col information embed
 */
public class JCell extends JButton {
    /**
     * Cell row
     */
    int i;
    /**
     * Cell Col
     */
    int j;

    public JCell(int i, int j) {
        super();
        setOpaque(true);
        setBorderPainted(false);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.i = i;
        this.j = j;
    }

    /**
     * Get Cell Row
     * @return Cell row
     */
    public int getI() {
        return i;
    }

    /**
     * Get Cell Col
     * @return Cell col
     */
    public int getJ() {
        return j;
    }
}
