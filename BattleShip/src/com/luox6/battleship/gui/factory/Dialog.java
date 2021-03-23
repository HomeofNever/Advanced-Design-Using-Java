package com.luox6.battleship.gui.factory;

import com.luox6.battleship.model.Directable;
import com.luox6.battleship.model.ship.StandbyShip;
import com.luox6.battleship.network.Client;
import com.luox6.battleship.network.Protocol;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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
        int result = JOptionPane.showConfirmDialog(c, inputs, "Input dialog", JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            int[] res = new int[2];
            res[0] = Integer.parseInt(row.getText());
            res[1] = Integer.parseInt(col.getText());
            if (res[0] < 1 || res[1] < 1) {
                throw new Exception("Row/Col must be at least 1!");
            }

            if (res[0] * res[1] < 18) {
                throw new Exception("Row * Col must be at least 17!");
            }

            return res;
        }

        return null;
    }

    /**
     * Client Server mode selection dialog
     * @param c Component to attach
     * @return Mode
     */
    public static Protocol.Mode selectMode(Component c) {
        Object[] options = {
                "Client",
                "Server"
        };
        int result = JOptionPane.showOptionDialog(c, "Please choose your mode", "Mode Selection", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, Protocol.Mode.SERVER);
        if (result == JOptionPane.OK_OPTION) {
            return Protocol.Mode.CLIENT;
        } else if (result == JOptionPane.NO_OPTION) {
            return Protocol.Mode.SERVER;
        } else {
            return null;
        }
    }

    /**
     * generic confirm dialog
     * @param c component to attach
     * @param s Message to ask
     * @return true if confirm, false otherwise
     */
    public static boolean confirmDialog(Component c, String s) {
        int n = JOptionPane.showConfirmDialog(
                c,
                s,
                "Confirm?",
                JOptionPane.YES_NO_OPTION);
        return n == JOptionPane.YES_OPTION;
    }

    /**
     * Dialog for Client information
     * @param c component to attach
     * @return String [Server Address, Port, Player Name]
     */
    public static String[] clientInitialDialog(Component c) {
        JTextField host = new JTextField("127.0.0.1");
        JTextField port = new JTextField(String.valueOf(Client.DEFAULT_PORT));
        JTextField name = new JTextField();
        final JComponent[] inputs = new JComponent[] {
                new JLabel("Please enter connection info"),
                new JLabel("Server Address"),
                host,
                new JLabel("Port"),
                port,
                new JLabel("Player Name"),
                name
        };
        int result = JOptionPane.showConfirmDialog(c, inputs, "Initialize info", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String[] res = new String[3];
            res[0] = host.getText();
            res[1] = port.getText();
            res[2] = name.getText();
            return res;
        }

        return null;
    }

    /**
     * Dialog for server initialization information
     * @param c Component to attach
     * @return String[Port, Player Name]
     */
    public static String[] serverInitialDialog(Component c) {
        JTextField port = new JTextField(String.valueOf(Client.DEFAULT_PORT));
        JTextField name = new JTextField();
        final JComponent[] inputs = new JComponent[] {
                new JLabel("Please enter all necessary info"),
                new JLabel("Port"),
                port,
                new JLabel("Player Name"),
                name
        };
        int result = JOptionPane.showConfirmDialog(c, inputs, "Initialize info", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String[] res = new String[2];
            res[0] = port.getText();
            res[1] = name.getText();
            return res;
        }

        return null;
    }

    /**
     * Ask for which ship to add
     * @param c Component to attach to
     * @param ships List of StandbyShip waited to add to map
     * @return StandbyShip need to be added
     */
    public static StandbyShip setShipDialog(Component c, List<StandbyShip> ships) {
        String [] selections = new String[ships.size()];
        for (int i = 0; i < ships.size(); i++) {
            selections[i] = "[%d] length %d".formatted(i, ships.get(i).getLength());
        }
        String s = (String)JOptionPane.showInputDialog(
                c,
                "Select a ship to add",
                "Add a ship",
                JOptionPane.PLAIN_MESSAGE,
                null,
                selections,
                selections[0]);
        if (s != null) {
            int i = Integer.parseInt(s.substring(s.indexOf("[") + 1, s.indexOf("]")));
            return ships.get(i);
        }
        return null;
    }

    /**
     * Ask for ship direction
     * @param c component to attached
     * @return Direction
     */
    public static Directable.Direction askDirectionDialog(Component c) {
        Object[] options = {
                Directable.Direction.VERTICAL,
                Directable.Direction.HORIZONTAL
        };
        int result = JOptionPane.showOptionDialog(c, "Please choose a direction", "Mode Selection", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, Directable.Direction.HORIZONTAL);

        if (result == JOptionPane.OK_OPTION) {
            return Directable.Direction.HORIZONTAL;
        } else if (result == JOptionPane.NO_OPTION) {
            return Directable.Direction.VERTICAL;
        } else {
            return null;
        }
    }

    /**
     * Show game result and offer next game
     * @param c component to attach to
     * @param message Win/Lose message
     * @return true if next game, false otherwise
     */
    public static boolean askNextGame(Component c, String message) {
        Object[] options = {
                "Next Game",
                "Exit"
        };
        int result = JOptionPane.showOptionDialog(c, message, "Game Ended!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, Directable.Direction.HORIZONTAL);

        return result == JOptionPane.OK_OPTION;
    }
}
