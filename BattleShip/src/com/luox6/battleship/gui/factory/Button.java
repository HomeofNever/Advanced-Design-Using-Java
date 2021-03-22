package com.luox6.battleship.gui.factory;
import javax.swing.*;

/**
 * GUI Button Factory
 */
public class Button {
    /**
     * Transparent button for color display
     * @param title Text on button
     * @return JButton ready for setBackground GUI
     */
    public static JButton transparentButton(String title) {
        JButton b = new JButton(title);
        b.setOpaque(true);
        b.setBorderPainted(false);
        return b;
    }
}
