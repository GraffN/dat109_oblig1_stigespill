package com.example.oblig1.classes;

import com.example.oblig1.interfaces.GameStatus;
import com.example.oblig1.interfaces.PawnColor;
import com.example.oblig1.logic.GameService;

public class Player extends Thread {
    private static int nextId = 0;
    private final int playerId;
    private String playerName;
    private Pawn pawn;
    private final GameService gameService;
    public Player(GameService gameService) {
        this.playerId = nextId;
        nextId++;
        this.gameService = gameService;
    }
    public Player(GameService gameService, String playerName) {
        this(gameService);
        this.pawn = new Pawn(this);
        this.playerName = playerName;
    }
    public Player(GameService gameService, String playerName, PawnColor pawnColor) {
        this(gameService, playerName);
        this.pawn = new Pawn(this, pawnColor);
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String name) {
        this.playerName = name;
    }

    public Pawn getPawn() {
        return pawn;
    }

    private void rollDice() {
        this.gameService.rollDice();
    }

    @Override
    public void run() {
        synchronized (this.gameService) {
            try {
                while (this.gameService.getGameStatus().equals(GameStatus.STARTED)) {
                    if (!this.gameService.getActivePlayer().equals(this)) {
                        this.wait();
                    } else {
                        this.rollDice();
                        this.gameService.notify();
                    }
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
