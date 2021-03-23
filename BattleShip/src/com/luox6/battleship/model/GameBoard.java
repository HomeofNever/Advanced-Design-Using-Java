package com.luox6.battleship.model;

import com.luox6.battleship.model.ship.BattleShip;
import com.luox6.battleship.model.ship.StandbyShip;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Game Representation
 * @author Xinhao Luo
 * @version 0.0.1
 */
public class GameBoard {
    /**
     * Possible game status
     */
    public enum GameStatus {INIT, SELF_TURN, ENEMY_TURN, SELF_WON, ENEMY_WON};

    /**
     * Player status
     */
    public enum PlayerStatus {NOT_READY, READY};

    /**
     * All possible target
     */
    public enum PlayerTarget {SELF, ENEMY};

    /**
     * List of ships will be added to map
     */
    protected List<StandbyShip> ships;

    /** Self representation **/
    protected GameMap self;
    protected PlayerStatus selfStatus = PlayerStatus.NOT_READY;
    private String selfName;
    private int selfCurrentTime = 0;

    /** Enemy representation **/
    protected GameMap enemy;
    protected PlayerStatus enemyStatus = PlayerStatus.NOT_READY;
    private String enemyName;
    private int enemyCurrentTime = 0;

    /** Init parameter **/
    protected GameStatus gameStatus = GameStatus.INIT;
    private int row;
    private int col;
    private int timeLimit;

    /**
     * Initialize game setting
     * @param row map row
     * @param col map col
     * @param ships List of StandbyShips need to be added to map
     * @param timeLimit Round time limit (second)
     */
    public GameBoard(int row, int col, List<StandbyShip> ships, int timeLimit) {
        this.row = row;
        this.col = col;
        this.ships = ships;
        this.timeLimit = timeLimit;
        setGameBoard(row, col);
    }

    /**
     * Default constructor
     * In order to function properly, setGameBoard() is required
     * after creation
     */
    public GameBoard() {}

    /**
     * Get All ships that will be set,
     * including set ships
     * @return List
     */
    public List<StandbyShip> getShips() {
        return ships;
    }

    /**
     * Get list of ship that haven't set by self
     * @return list of StandbyShip
     */
    public List<StandbyShip> getUnsetShips() {
        List<StandbyShip> ships = new ArrayList<>();
        getShips().forEach(e -> {
            if(!self.getBattleShips().containsKey(e.getShipId())) {
                ships.add(e);
            }
        });

        return ships;
    }

    /**
     * Initialize GameMap and status
     */
    public void init() {
        self = new GameMap(row, col);
        enemy = new GameMap(row, col);
        gameStatus = GameStatus.INIT;
        enemyStatus = PlayerStatus.NOT_READY;
        selfStatus = PlayerStatus.NOT_READY;
    }

    /**
     * Initialize Both self and enemy GameMap
     * @param row map row
     * @param col map col
     */
    public void setGameBoard(int row, int col) {
        this.row = row;
        this.col = col;
        init();
    }

    /**
     * Get all battleship on the map
     * @param p PlayerTarget self or enemy
     * @return Map of Battleship with UUID as key
     */
    public Map<UUID, BattleShip> getBattleShip(PlayerTarget p) {
        if (gameStatus == GameStatus.INIT) {
            return switch(p) {
                case SELF -> self.getBattleShips();
                case ENEMY -> enemy.getBattleShips();
            };
        }
        throw new RuntimeException("Trying to get Battleship when it is not init");
    }

    /**
     * Add StandbySHip to the board
     * @param s StandbyShip, should have correct length and direction
     * @param c Start location
     * @param p PlayerTarget, decide which board to go
     * @throws Exception Failed to add due to map conflict
     */
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

    /**
     * Set Player Status
     * @param status PlayerStatus
     * @param target PlayerTarget
     */
    public void setPlayerStatus(PlayerStatus status, PlayerTarget target) {
        switch(target) {
            case SELF -> selfStatus = status;
            case ENEMY -> enemyStatus = status;
        }
    }

    /**
     * Get player current status
     * @param t PlayerTarget
     * @return PlayerStatus
     */
    public PlayerStatus getPlayerStatus(PlayerTarget t) {
        return switch (t) {
            case SELF -> selfStatus;
            case ENEMY -> enemyStatus;
        };
    }

    /**
     * Check if game has reach end state and change state accordingly
     */
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

    /**
     * Check if current game is ready to start
     * @return
     */
    public boolean getStarted() {
        return selfStatus == PlayerStatus.READY && enemyStatus == PlayerStatus.READY;
    }

    /** Getter and setter **/
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
}
