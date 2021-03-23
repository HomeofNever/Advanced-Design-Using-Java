package com.luox6.battleship.gui;

import com.luox6.battleship.gui.model.PlayerBoard;
import com.luox6.battleship.model.GameBoard;
import com.luox6.battleship.network.Connectable;
import com.luox6.battleship.network.Protocol;

/**
 * Thread calculate Game turn
 * @author Xinhao Luo
 * @version 0.0.1
 */
public class GameTurn extends Thread {

    private PlayerBoard playerBoard;
    private GUIController guiController;
    private Connectable connection;
    public boolean stop = false;
    private boolean updated = false;

    /**
     * Default Game Turn thread
     * @param playerBoard Board will be controlled
     * @param guiController GUI
     * @param connection Peer connection
     */
    public GameTurn(PlayerBoard playerBoard, GUIController guiController, Connectable connection) {
        this.playerBoard = playerBoard;
        this.guiController = guiController;
        this.connection = connection;
    }

    /**
     * Run turns in the game
     */
    @Override
    public void run() {
        Integer time = playerBoard.getTimeLimit();

        while(time > 0 && !stop && !connection.isConnectionClosed()) {
            if (!updated) {
                guiController.guiViewer.shipBoard.updateBoard(GameBoard.PlayerTarget.SELF);
                updated = true;
            }

            guiController.concludeGame();
            if (playerBoard.getGameStatus() == GameBoard.GameStatus.SELF_TURN) {
                playerBoard.setSelfCurrentTime(time);
                connection.send("%s#%d".formatted(Protocol.GameCommand.CURRENT_TIME, time));
                time--;
            } else {
                time = playerBoard.getTimeLimit();
                playerBoard.setSelfCurrentTime(time);
                updated = false;
            }
            try {
                sleep(1000); // sleep 1 second
            }catch (Exception e) {
                // suppress exception message here
            }
            guiController.updateTurn();
        }

        if(!stop) {
            // Ouch, we are out of time!
            playerBoard.setGameStatus(GameBoard.GameStatus.ENEMY_WON);
            connection.send("%s#%s".formatted(Protocol.GameCommand.MARK, "null"));
            guiController.concludeGame();
        }
    }
}
