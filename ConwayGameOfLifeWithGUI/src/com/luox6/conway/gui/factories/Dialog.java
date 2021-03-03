package com.luox6.conway.gui.factories;

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

    /**
     * Asking user for map row and col
     * @param c Frame to attach
     * @param defaultRow Pre-filled row length
     * @param defaultCol Pre-filled col length
     * @return new int[] with row at int[0] and col at int[1], if user cancel or enter illegal value, null will be returned
     * @throws NumberFormatException input value cannot be parsed as Integer
     * @throws Exception Row/Col must be at least 2
     */
    public static int[] rowColInputDialog(Component c, int defaultRow, int defaultCol) throws NumberFormatException, Exception{
        JTextField row = new JTextField(String.valueOf(defaultRow));
        JTextField col = new JTextField(String.valueOf(defaultCol));
        final JComponent[] inputs = new JComponent[] {
                new JLabel("Please enter map's row and col"),
                new JLabel("Row"),
                row,
                new JLabel("Col"),
                col
        };
        int result = JOptionPane.showConfirmDialog(c, inputs, "My custom dialog", JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            int[] res = new int[2];
            res[0] = Integer.parseInt(row.getText());
            res[1] = Integer.parseInt(col.getText());
            if (res[0] < 1 && res[1] < 1) {
                throw new Exception("Row/Col must be at least 2!");
            }

            return res;
        }

        return null;
    }
}
