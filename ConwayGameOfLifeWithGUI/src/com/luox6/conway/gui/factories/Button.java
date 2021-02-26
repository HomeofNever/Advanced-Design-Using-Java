package com.luox6.conway.gui.factories;

import javax.swing.*;

public class Button {
    public static JButton transparentButton(String title) {
        JButton b = new JButton(title);
        b.setOpaque(true);
        b.setBorderPainted(false);
        return b;
    }
}
