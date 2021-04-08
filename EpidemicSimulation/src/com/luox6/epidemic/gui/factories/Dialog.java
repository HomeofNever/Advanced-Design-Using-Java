package com.luox6.epidemic.gui.factories;

import javax.swing.*;
import java.awt.*;

/**
 * GUI warning factory
 */
public class Dialog {
    /**
     * Warning for integer conversion exception
     * @param c Frame to attach dialog
     * @param e numberFormatException over the input text
     */
    public static void numberParseDialog(Component c, NumberFormatException e) {
        JOptionPane.showMessageDialog(c,
                "Unable to parse " + e.getMessage() + " as number",
                "Warning",
                JOptionPane.WARNING_MESSAGE);
        e.printStackTrace();
    }

    /**
     * Warning for general exceptions
     * @param c Frame to attach dialog
     * @param e Exception with error messages
     */
    public static void genericWarningDialog(Component c, Exception e) {
        JOptionPane.showMessageDialog(c,
                e.getMessage(),
                "Warning",
                JOptionPane.WARNING_MESSAGE);
        e.printStackTrace();
    }

    /**
     * Success notice for general purpose
     * @param c Frame to attach dialog
     * @param message message to display
     */
    public static void genericSuccessDialog(Component c, String message) {
        JOptionPane.showMessageDialog(c,
                message,
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
