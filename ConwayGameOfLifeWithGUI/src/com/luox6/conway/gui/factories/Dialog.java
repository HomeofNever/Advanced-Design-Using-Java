package com.luox6.conway.gui.factories;

import javax.swing.*;
import java.awt.*;

public class Dialog {
    public static void numberParseDialog(Component c, NumberFormatException e) {
        JOptionPane.showMessageDialog(c,
                "Unable to parse " + e.getMessage() + " as number",
                "Warning",
                JOptionPane.WARNING_MESSAGE);
        e.printStackTrace();
    }

    public static void genericWarningDialog(Component c, Exception e) {
        JOptionPane.showMessageDialog(c,
                e.getMessage(),
                "Warning",
                JOptionPane.WARNING_MESSAGE);
        e.printStackTrace();
    }
}
