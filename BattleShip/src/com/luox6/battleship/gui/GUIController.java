package com.luox6.battleship.gui;

import com.luox6.battleship.gui.component.ConfigurationPanel;
import com.luox6.battleship.gui.factory.Dialog;
import com.luox6.battleship.gui.model.PlayerBoard;
import com.luox6.battleship.gui.model.UserSetting;
import com.luox6.battleship.model.Cell;
import com.luox6.battleship.model.Coordinate;
import com.luox6.battleship.model.Directable;
import com.luox6.battleship.model.GameBoard;
import com.luox6.battleship.model.ship.StandbyShip;
import com.luox6.battleship.network.Connectable;
import com.luox6.battleship.network.Protocol;
import com.luox6.battleship.network.Server;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class GUIController {
    // Models
    private PlayerBoard playerBoard;
    private Protocol protocol;
    private Connectable connection;

    // Executors
    private GameTurn gameTurn;

    // Viewers
    protected GUIViewer guiViewer;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    /**
     * Default Constructor
     * @param playerBoard Game representation
     * @param p Protocol for communication
     * @param c Socket Connection between Server/Client
     */
    public GUIController(PlayerBoard playerBoard, Protocol p, Connectable c) {
        // Init Model
        this.playerBoard = playerBoard;
        this.connection = c;
        this.protocol = p;
    }

    /**
     * Set reference of the Viewer object, for update purposes
     *
     * @param guiViewer
     */
    public void setGuiViewer(GUIViewer guiViewer) {
        this.guiViewer = guiViewer;
    }

    /**
     * Application entry
     */
    public void start() {
        initBind();

        // Setting the frame visibility to true
        guiViewer.pack();
        guiViewer.setVisible(true);
    }

    /**
     * Initialize GUI and PlayerBoard binding
     */
    public void initBind() {
        guiViewer.statusPanel.updatePlayerTime(playerBoard.getTimeLimit(), GameBoard.PlayerTarget.SELF);
        guiViewer.statusPanel.updatePlayerTime(playerBoard.getTimeLimit(), GameBoard.PlayerTarget.ENEMY);
        guiViewer.statusPanel.updatePlayerName(playerBoard.getSelfName(), GameBoard.PlayerTarget.SELF);
        guiViewer.statusPanel.updatePlayerName(playerBoard.getEnemyName(), GameBoard.PlayerTarget.ENEMY);
        guiViewer.statusPanel.updatePlayerScore(playerBoard.getSelfScore(), GameBoard.PlayerTarget.SELF);
        guiViewer.statusPanel.updatePlayerScore(playerBoard.getEnemyScore(), GameBoard.PlayerTarget.ENEMY);
        guiViewer.statusPanel.updateCurrentTurn("", 0);

        playerBoard.init();
        guiViewer.shipBoard.init(playerBoard.getRow(), playerBoard.getCol(), playerBoard);
        guiViewer.pack();
        // Game turn monitor
        executor.submit(() -> {
            while (true) {
                if (playerBoard.getStarted()) {
                    // Exchange Ship info
                    protocol.sendShips(connection);
                    while (!playerBoard.equalShips()) {
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    // sleep an extra 5000ms for sync
                    // @TODO better handshake
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    playerBoard.setGameStatus(connection instanceof Server ? GameBoard.GameStatus.SELF_TURN : GameBoard.GameStatus.ENEMY_TURN);
                    Dialog.genericSuccessDialog(guiViewer, "Game has now started!");
                    gameTurn = new GameTurn(playerBoard, this, connection);
                    gameTurn.start();
                    break;
                }
                try {
                    sleep(1000); // sleep 1 second
                }catch (Exception e) {
                    // suppress exception message here
                }
            }
        });
    }

    /**
     * Exit button handler
     */
    public void exitButtonPressed() {
        System.exit(0);
    }

    /**
     * Action -> Configuration button handler
     */
    public void openConfigurationDialog() {
        Dialog.genericWarningDialog(guiViewer, new Exception("Your changes will be effective after program restart!"));
        guiViewer.configurationPanel.setVisible(true);
    }

    /**
     * Configuration -> Discover Color
     */
    public void setDiscoverColor() {
        UserSetting.setDiscoverColor(JColorChooser.showDialog(guiViewer, "Set Discover Cell Color", UserSetting.getDiscoverColor()));
        guiViewer.configurationPanel.updateSettings();
    }

    /**
     * Configuration -> Mark Color
     */
    public void setMarkColor() {
        UserSetting.setMarkColor(JColorChooser.showDialog(guiViewer, "Set Discover Cell Color", UserSetting.getDiscoverColor()));
        guiViewer.configurationPanel.updateSettings();
    }

    /**
     * Configuration -> Grid Size
     */
    public void setGridSize() {
        try {
            int[] data = Dialog.rowColInputDialog(guiViewer, UserSetting.getGridRow(), UserSetting.getGridCol());
            if (data != null) {
                UserSetting.setGridRow(data[0]);
                UserSetting.setGridCol(data[1]);
            }
        } catch (Exception e) {
            Dialog.genericWarningDialog(guiViewer, e);
        }
        guiViewer.configurationPanel.updateSettings();
    }

    /**
     * Configuration -> Time Limit
     */
    public void setTimeLimit(String s) {
        try {
            int i = Integer.parseInt(s);
            if (i < 4) {
                throw new Exception("Time should be at least 5 second!");
            }
            UserSetting.setTimeLimit(i);
        } catch (NumberFormatException e) {
            Dialog.numberParseDialog(guiViewer, e);
        } catch (Exception e) {
            Dialog.genericWarningDialog(guiViewer, e);
        } finally {
            guiViewer.configurationPanel.updateSettings();
        }
    }

    /**
     * Action when cell from self grid clicked
     * @param coordinate Location of the cell
     */
    public void selfCellClicked(Coordinate coordinate) {
        switch(playerBoard.getGameStatus()) {
            case INIT -> {
                // Now, it is time to set board
                if (playerBoard.getPlayerStatus(GameBoard.PlayerTarget.SELF) == GameBoard.PlayerStatus.NOT_READY) {
                    Cell c = guiViewer.shipBoard.getJCell(coordinate, GameBoard.PlayerTarget.SELF).getCell();
                    // This will be the beginning of the ship
                    // Prompt for ships to add
                    if (!c.hasShip()) {
                        // Get all StandbyShips left
                        List<StandbyShip> ships = playerBoard.getUnsetShips();
                        if (ships.size() == 0) {
                            Dialog.genericSuccessDialog(guiViewer, "All ships are set, click ready to confirm your decision!");
                            return;
                        }
                        StandbyShip s = Dialog.setShipDialog(guiViewer, ships);
                        if (s == null) {
                            return;
                        }
                        Directable.Direction direction = Dialog.askDirectionDialog(guiViewer);
                        if (direction == null) {
                            return;
                        }
                        s.setDirection(direction);
                        try {
                            playerBoard.addShip(s, c, GameBoard.PlayerTarget.SELF);
                        } catch (Exception e) {
                            // Unable to add ship at this location
                            Dialog.genericWarningDialog(guiViewer, e);
                        }
                        guiViewer.shipBoard.updateBoard(GameBoard.PlayerTarget.SELF);
                    } else {
                        // Ask if remove ship
                        boolean remove = Dialog.confirmDialog(guiViewer, "Remove ship at this location?");
                        if (remove) {
                            playerBoard.removeShip(c.getShipId());
                            guiViewer.shipBoard.updateBoard(GameBoard.PlayerTarget.SELF);
                        }
                    }
                } else {
                    Dialog.genericWarningDialog(guiViewer, new Exception("You are now in ready state and can't make further changes!"));
                }
            }
            default -> {
                // Ignore rest of the cases: no action should be taken
            }
        }
    }

    /**
     * Action when cell from enemy grid clicked
     * @param c Location of the cell
     */
    public void enemyCellClicked(Coordinate c) {
        switch(playerBoard.getGameStatus()) {
            case INIT -> {
                // Enemy should not be touched by SELF
            }
            case SELF_TURN -> {
                // HIT!
                Cell cell = playerBoard.getCell(c, GameBoard.PlayerTarget.ENEMY);
                if (cell.isDiscovered()) {
                    Dialog.genericWarningDialog(guiViewer, new Exception("This cell has already discovered!"));
                } else {
                    // Send to Enemy
                    playerBoard.setGameStatus(GameBoard.GameStatus.ENEMY_TURN);
                    cell.setDiscovered(true);
                    guiViewer.shipBoard.getJCell(c, GameBoard.PlayerTarget.ENEMY).updateDisplay();
                    Protocol.markShip(cell, connection);
                    concludeGame();
                }
            }
            case ENEMY_TURN -> {
                // We need to wait for our turn
                Dialog.genericWarningDialog(guiViewer, new Exception("Please wait for your turn"));
            }
        }
    }

    /**
     * Toolbar -> ready button
     */
    public void readyButtonPressed() {
        if (playerBoard.getGameStatus() == GameBoard.GameStatus.INIT) {
            GameBoard.PlayerStatus p = playerBoard.getPlayerStatus(GameBoard.PlayerTarget.SELF);
            if (p == GameBoard.PlayerStatus.NOT_READY) {
                if (playerBoard.getUnsetShips().size() == 0){
                    playerBoard.setPlayerStatus(GameBoard.PlayerStatus.READY, GameBoard.PlayerTarget.SELF);
                    connection.send("%s".formatted(Protocol.GameCommand.READY));
                    Dialog.genericSuccessDialog(guiViewer, "Ready set! Please wait for enemy...");
                } else {
                    Dialog.genericWarningDialog(guiViewer, new Exception("You haven't finished setting ships!"));
                }
            } else {
                Dialog.genericWarningDialog(guiViewer, new Exception("You are now in ready state!"));
            }
        }
    }

    /**
     * Check if game should be ended and response
     * accordingly
     */
    public void concludeGame() {
        playerBoard.checkGameStatus();
        boolean next = false;
        if (playerBoard.getGameStatus() == GameBoard.GameStatus.ENEMY_WON) {
            playerBoard.incrementEnemyScore();
            gameTurn.stop = true;
            next = Dialog.askNextGame(guiViewer, "Enemy won this game!");
        } else if (playerBoard.getGameStatus() == GameBoard.GameStatus.SELF_WON) {
            playerBoard.incrementSelfScore();
            gameTurn.stop = true;
            next = Dialog.askNextGame(guiViewer, "You won this game!");
        }

        if (next) {
            playerBoard.setPlayerStatus(GameBoard.PlayerStatus.NOT_READY, GameBoard.PlayerTarget.SELF);
            connection.send("%s".formatted(Protocol.GameCommand.RESTART));

            // waiting for response
            while (playerBoard.getPlayerStatus(GameBoard.PlayerTarget.ENEMY) == GameBoard.PlayerStatus.READY) {
                Dialog.genericWarningDialog(guiViewer, new Exception("Waiting for response from Enemy..."));
            }

            initBind();
        } else if (gameTurn.stop) {
            System.exit(0);
        }
    }

    /**
     * Update StatusBoard data
     */
    public void updateTurn() {
        switch (playerBoard.getGameStatus()) {
            case SELF_TURN -> {
                guiViewer.statusPanel.updateCurrentTurn(playerBoard.getSelfName(), playerBoard.getSelfCurrentTime());
            }
            case ENEMY_TURN -> {
                guiViewer.statusPanel.updateCurrentTurn(playerBoard.getEnemyName(), playerBoard.getEnemyCurrentTime());
            }
        }
    }
}
