package com.luox6.battleship.gui.component;

import com.luox6.battleship.model.GameBoard;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {

    /* Components */
    private JLabel selfNameLabel = new JLabel();
    private JLabel enemyNameLabel = new JLabel();
    private JLabel selfScoreLabel = new JLabel();
    private JLabel enemyScoreLabel = new JLabel();
    private JLabel selfTimeLabel = new JLabel();
    private JLabel enemyTimeLabel = new JLabel();
    private JLabel currentTurnLabel = new JLabel();
    private JLabel currentTimeLabel = new JLabel();

    public StatusPanel() {
        super();
        setLayout(new GridLayout(4, 3, 1, 1));

        // Components
        JLabel nameLabel = new JLabel("Player Name");
        add(nameLabel);
        JLabel scoreLabel = new JLabel("Score");
        add(scoreLabel);
        JLabel timeLabel = new JLabel("Time left");
        add(timeLabel);

        add(selfNameLabel);
        add(selfScoreLabel);
        add(selfTimeLabel);
        add(enemyNameLabel);
        add(enemyScoreLabel);
        add(enemyTimeLabel);

        JLabel currentLabel = new JLabel("Current turn/time");
        add(currentLabel);
        add(currentTurnLabel);
        add(currentTimeLabel);
    }


    public void updatePlayerTime(int time, GameBoard.PlayerTarget t) {
        switch (t) {
            case SELF -> selfTimeLabel.setText("%d".formatted(time));
            case ENEMY -> enemyTimeLabel.setText("%d".formatted(time));
        }
    }

    public void updatePlayerName(String name, GameBoard.PlayerTarget t) {
        switch (t) {
            case SELF -> selfNameLabel.setText(name);
            case ENEMY -> enemyNameLabel.setText(name);
        }
    }

    public void updatePlayerScore(int score, GameBoard.PlayerTarget t) {
        switch (t) {
            case SELF -> selfScoreLabel.setText("%d".formatted(score));
            case ENEMY -> enemyScoreLabel.setText("%d".formatted(score));
        }
    }

    public void updateCurrentTurn(String name, Integer time) {
        currentTurnLabel.setText(name);
        currentTimeLabel.setText(time.toString());
    }
}
