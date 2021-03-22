package com.luox6.battleship;

import com.luox6.battleship.gui.GUIController;
import com.luox6.battleship.gui.GUIViewer;
import com.luox6.battleship.gui.factory.Dialog;
import com.luox6.battleship.gui.model.PlayerBoard;
import com.luox6.battleship.gui.model.UserSetting;
import com.luox6.battleship.model.GameBoard;
import com.luox6.battleship.model.ship.StandbyShip;
import com.luox6.battleship.network.Client;
import com.luox6.battleship.network.Connectable;
import com.luox6.battleship.network.Protocol;
import com.luox6.battleship.network.Server;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    /**
     * This main method is main for test purpose.
     * @param args
     */
    public static void main(String[] args) {
        // We need to gather: mode, server name, etc.
        final Protocol.Mode m = Dialog.selectMode(null);
        final String name;
        final Integer port;
        final String address;

        if (m == null) {
            // Cancelled
            return;
        }

        // Server specific info
        Object[] config = null;
        if (m == Protocol.Mode.SERVER) {
            while (config == null) {
                config = getServerInfo();
            }
            port = (Integer) config[0];
            name = (String) config[1];
            address = null;
        } else {
            while (config == null) {
                config = getClientInfo();
            }
            address = (String) config[0];
            port = (Integer) config[1];
            name = (String) config[2];
        }

        // Initialize Network
        JDialog initDialog = new JDialog(null, "Busy", java.awt.Dialog.ModalityType.MODELESS);
        JLabel indicator = new JLabel("Initializing...");
        initDialog.add(indicator);
        initDialog.setSize(400, 150); // For more words
        final PlayerBoard[] pb = new PlayerBoard[1];
        final Protocol[] pr = new Protocol[1];
        final Connectable[] c = new Connectable[1];
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Boolean> res = executor.submit(() -> {
            try {
                if (m == Protocol.Mode.SERVER) {
                    c[0] = new Server(port);
                    indicator.setText("Waiting for Client...");
                } else {
                    c[0] = new Client(address, port);
                    indicator.setText("Try Connecting to Server...");
                }
                c[0].connect();
            } catch (Exception e) {
                Dialog.genericWarningDialog(null, e);
                e.printStackTrace();
                return false;
            }

            if (m == Protocol.Mode.SERVER) {
                indicator.setText("Client Connected! Initializing settings...");
                int row = UserSetting.getGridRow();
                int col = UserSetting.getGridCol();

                List<StandbyShip> ships = new ArrayList<>();
                ships.add(new StandbyShip(5)); // Carrier
                ships.add(new StandbyShip(4)); // Battleship
                ships.add(new StandbyShip(3)); // Destroyer
                ships.add(new StandbyShip(3)); // Submarine
                ships.add(new StandbyShip(2)); // Patrol Boat

                pb[0] = new PlayerBoard(row, col, UserSetting.getTimeLimit(), ships);
                pb[0].setSelfName(name);
                pr[0] = new Protocol(pb[0]);

                indicator.setText("Syncing basic settings with Client...");
                awaitSync(c, pr);

                // we need to have opposite's name
                c[0].send("%s#%s".formatted(Protocol.GameCommand.WHAT, Protocol.GameCommand.NAME));
                pr[0].process(c[0].receive());
                c[0].send(Protocol.GameCommand.NEXT.toString());
            } else {
                // Acquire information
                indicator.setText("Server Connected! Initializing Game setting");
                pb[0] = new PlayerBoard();
                pr[0] = new Protocol(pb[0]);
                // Set basic info
                pb[0].setSelfName(name);

                indicator.setText("Waiting for server echo...");
                // Wait for Server ready
                do {
                    String command = c[0].receive();
                    if (command.equals(Protocol.GameCommand.NEXT.toString())) {
                        break;
                    }
                } while (true);

                indicator.setText("Syncing with Server...");
                // Ask for data
                pr[0].init(c[0]);
                indicator.setText("Waiting Server...");
                awaitSync(c, pr);
            }
            indicator.setText("Sync complete! Starting GUI...");
            initDialog.dispose();
            return true;
        });

        initDialog.setVisible(true);

        try {
            Boolean r = res.get();
            if (!r) {
                System.exit(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.printf("Ready: Enemy Player is %s", pb[0].getEnemyName());

        // Initialize GUI
        // Controller
        GUIController guiController = new GUIController(pb[0], pr[0], c[0]);
        // View
        GUIViewer GUIViewer = new GUIViewer(guiController);
        guiController.setGuiViewer(GUIViewer);

        // Message Broker
        executor.submit(() -> {
            do {
                String command = c[0].receive();
                String response = pr[0].process(command);
                if (response != null) {
                    c[0].send(response);
                }
            } while (true);
        });
        // Launch GUI
        guiController.start();
    }

    private static void awaitSync(Connectable[] c, Protocol[] pr) {
        c[0].send(Protocol.GameCommand.NEXT.toString());
        do {
            String command = c[0].receive();
            if (command.equals(Protocol.GameCommand.NEXT.toString())) {
                break;
            }
            String response = pr[0].process(command);
            if (response != null)
                c[0].send(response);
        } while (true);
    }

    public static Object[] getClientInfo() {
        String[] config = Dialog.clientInitialDialog(null);
        if (config == null) {
            System.exit(1);
        }

        try {
            String address = config[0];
            int port = Integer.parseInt(config[1]);
            String name = config[2];

            if (name.length() < 1) {
                throw new Exception("Name must have at least 1 characters");
            }

            Object[] res = new Object[3];
            res[0] = address;
            res[1] = port;
            res[2] = name;
            return res;
        } catch (NumberFormatException e) {
            Dialog.numberParseDialog(null, e);
            return null;
        } catch (Exception e) {
            Dialog.genericWarningDialog(null, e);
            return null;
        }
    }

    public static Object[] getServerInfo() {
        String[] config = Dialog.serverInitialDialog(null);
        if (config == null) {
            System.exit(1);
        }

        try {
            int port = Integer.parseInt(config[0]);
            String name = config[1];

            if (name.length() > 0) {
                Object[] res = new Object[2];
                res[0] = port;
                res[1] = name;

                return res;
            }

            throw new Exception("Name must be at least 1 character");
        } catch (NumberFormatException e) {
            Dialog.numberParseDialog(null, e);
            return null;
        } catch (Exception e) {
            Dialog.genericWarningDialog(null, e);
            return null;
        }
    }
}
