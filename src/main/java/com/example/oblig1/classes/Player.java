package com.example.oblig1.classes;

import com.example.oblig1.interfaces.GameStatus;
import com.example.oblig1.interfaces.PawnColor;
import com.example.oblig1.services.GameService;

import java.util.Scanner;

public class Player implements Runnable {
    private static int nextId = 0;
    private final int playerId;
    private String playerName;
    private Pawn pawn;
    private final GameService gameService;

    public Player(GameService gameService, String playerName, PawnColor color) {
        this.playerId = nextId;
        nextId++;
        this.gameService = gameService;
        this.playerName = playerName;
        this.pawn = new Pawn(this, color);
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

    public void rollDice() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("%s's turn, press enter to roll the dice\n", this.playerName);
        System.out.printf("%s: ", this.playerName);
        scanner.nextLine();

        // Notify GameService that it's the player's turn
        this.gameService.notifyPlayerTurn(this);
        this.gameService.rollDice(this);

        // Wait until the GameService finishes processing the dice result and player's move
        synchronized (this) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }

    public void notifyTurnEnd() {
        synchronized (this) {
            this.notify(); // Notify waiting GameService thread
        }
    }

    @Override
    public void run() {
        while (!gameService.getGameStatus().equals(GameStatus.STARTED)) {
            synchronized (gameService) {
                while (!gameService.getActivePlayer().equals(this)) {
                    try {
                        gameService.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println(this.playerName + "'s turn");
                this.rollDice();
                System.out.println();

                gameService.notifyAll();
            }
        }
    }

    public void startPlayerThread() {
        new Thread(this).start();
    }
}
