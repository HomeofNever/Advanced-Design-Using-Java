package com.luox6.battleship.gui.model;

import com.luox6.battleship.model.Cell;
import com.luox6.battleship.model.Coordinate;
import com.luox6.battleship.model.GameBoard;
import com.luox6.battleship.model.ship.StandbyShip;

import java.util.List;

public class PlayerBoard extends GameBoard {
    private int selfScore = 0;
    private int enemyScore = 0;

    public PlayerBoard() {}

    public PlayerBoard(int row, int col, int timeLimit, List<StandbyShip> ships) {
        super(row, col, ships, timeLimit);
    }

    public Cell getCell(Coordinate c, PlayerTarget p) {
        return switch(p) {
            case SELF -> self.getCellByCoordinate(c);
            case ENEMY -> enemy.getCellByCoordinate(c);
        };
    }

    public int getEnemyScore() {
        return enemyScore;
    }

    public int getSelfScore() {
        return selfScore;
    }

    public void incrementSelfScore() {
        selfScore++;
    }

    public void incrementEnemyScore() {
        enemyScore++;
    }

    public boolean equalShips() {
        return self.getBattleShips().size() == enemy.getBattleShips().size();
    }
}
