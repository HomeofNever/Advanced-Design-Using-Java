package com.luox6.battleship.model;

import com.luox6.battleship.model.ship.BattleShip;
import com.luox6.battleship.model.ship.StandbyShip;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GameBoard {
    public enum GameStatus {INIT, SELF_TURN, ENEMY_TURN, SELF_WON, ENEMY_WON};
    public enum PlayerStatus {NOT_READY, READY};
    public enum PlayerTarget {SELF, ENEMY};
    protected List<StandbyShip> ships;
    protected GameMap self;
    protected PlayerStatus selfStatus = PlayerStatus.NOT_READY;
    protected GameMap enemy;
    protected PlayerStatus enemyStatus = PlayerStatus.NOT_READY;
    protected GameStatus gameStatus = GameStatus.INIT;
    private int row;
    private int col;
    private String selfName;
    private String enemyName;
    private int enemyCurrentTime = 0;
    private int selfCurrentTime = 0;
    private int timeLimit;

    public GameBoard(int row, int col, List<StandbyShip> ships, int timeLimit) {
        this.row = row;
        this.col = col;
        this.ships = ships;
        this.timeLimit = timeLimit;
        setGameBoard(row, col);
    }

    public GameBoard() {}

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String getSelfName() {
        return selfName;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public int getTimeLimit() { return timeLimit; }

    public int getEnemyCurrentTime() {
        return enemyCurrentTime;
    }

    public int getSelfCurrentTime() {
        return selfCurrentTime;
    }

    public void setEnemyCurrentTime(int enemyCurrentTime) {
        this.enemyCurrentTime = enemyCurrentTime;
    }

    public void setSelfCurrentTime(int selfCurrentTime) {
        this.selfCurrentTime = selfCurrentTime;
    }

    public List<StandbyShip> getShips() {
        return ships;
    }

    public List<StandbyShip> getUnsetShips() {
        List<StandbyShip> ships = new ArrayList<>();
        getShips().forEach(e -> {
            if(!self.getBattleShips().containsKey(e.getShipId())) {
                ships.add(e);
            }
        });

        return ships;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public void setShips(List<StandbyShip> ships) {
        this.ships = ships;
    }

    public void setSelfName(String selfName) {
        this.selfName = selfName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void init() {
        self = new GameMap(row, col);
        enemy = new GameMap(row, col);
        gameStatus = GameStatus.INIT;
        enemyStatus = PlayerStatus.NOT_READY;
        selfStatus = PlayerStatus.NOT_READY;
    }

    public void setGameBoard(int row, int col) {
        this.row = row;
        this.col = col;
        init();
    }

    public Map<UUID, BattleShip> getBattleShip(PlayerTarget p) {
        if (gameStatus == GameStatus.INIT) {
            return switch(p) {
                case SELF -> self.getBattleShips();
                case ENEMY -> enemy.getBattleShips();
            };
        }
        throw new RuntimeException("Trying to get Battleship when it is not init");
    }

    public void addShip(StandbyShip s, Coordinate c, PlayerTarget p) throws Exception {
        if (gameStatus == GameStatus.INIT) {
            switch(p) {
                case SELF -> self.addShip(s, c);
                case ENEMY -> enemy.addShip(s, c);
            }
            return;
        }
        throw new RuntimeException("Trying to add ship when it is not init");
    }

    /**
     * Pass thru removeShip function from game map
     * One can only remove self ship
     * @param uuid ship uuid
     */
    public void removeShip(UUID uuid) {
        self.removeShip(uuid);
    }

    /**
     *
     * @param c Hit Location
     */
    public void mark(Coordinate c) {
        if (gameStatus == GameStatus.SELF_TURN) {
            enemy.mark(c);
        } else if (gameStatus == GameStatus.ENEMY_TURN) {
            self.mark(c);
        } else {
            throw new RuntimeException("Trying to mark ship when it is not in progress");
        }
    }

    public void setPlayerStatus(PlayerStatus status, PlayerTarget target) {
        switch(target) {
            case SELF -> selfStatus = status;
            case ENEMY -> enemyStatus = status;
        }
    }

    public PlayerStatus getPlayerStatus(PlayerTarget t) {
        return switch (t) {
            case SELF -> selfStatus;
            case ENEMY -> enemyStatus;
        };
    }

    public void checkGameStatus() {
        if (gameStatus == GameStatus.SELF_TURN || gameStatus == GameStatus.ENEMY_TURN) {
            if (self.allDown()) {
                setGameStatus(GameStatus.ENEMY_WON);
            } else if (enemy.allDown()) {
                setGameStatus(GameStatus.SELF_WON);
            }
        }
    }

    /**
     * This method used to set game status
     * @param g GameStatus
     */
    public void setGameStatus(GameStatus g) {
        gameStatus = g;
    }

    public boolean getStarted() {
        return selfStatus == PlayerStatus.READY && enemyStatus == PlayerStatus.READY;
    }
}
