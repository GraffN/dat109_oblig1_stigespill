package com.example.oblig1.classes;

import com.example.oblig1.interfaces.GameStatus;
import com.example.oblig1.interfaces.PawnColor;
import com.example.oblig1.services.GameService;

import java.util.Scanner;

public class Player extends Thread {
    private static int nextId = 0;
    private final int playerId;
    private String playerName;
    private Pawn pawn;
    private final GameService gameService;
    private Player(GameService gameService) {
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
        Scanner scanner = new Scanner(System.in);
        System.out.printf("%s tur, trykk enter for rulle terning\n", this.playerName);
        System.out.printf("%s: ", this.playerName);
        scanner.nextLine();
        boolean success = this.gameService.rollDice();
        if (!success) {
            throw new RuntimeException("Failed to roll dice");
        }
    }

    @Override
    public void run() {
        final GameService gameService = this.gameService;

        synchronized (gameService) {
            try {
                GameStatus gameStatus = gameService.getGameStatus();

                while (gameStatus.equals(GameStatus.STARTED)) {
                    gameStatus = gameService.getGameStatus();
                    Player activePlayer = gameService.getActivePlayer();
                    System.out.println("activePlayer: " + activePlayer.playerName);
                    if (!activePlayer.equals(this)) {
                        gameService.wait();
                    } else {
                        this.rollDice();
                        gameService.notifyAll();
                    }
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
