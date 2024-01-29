package com.example.oblig1.logic;

import com.example.oblig1.classes.*;
import com.example.oblig1.interfaces.GameStatus;

import java.util.*;

public class GameService implements Runnable {
    private final Game game;
    private final Stack<Player> playerOrder;
    private Player activePlayer;
    private final Dice dice;
    private int rollCount;
    private int round;
    public GameService(final Game game) {
        this.game = game;
        this.playerOrder = new Stack<>();
        this.rollCount = 0;
        this.dice = new Dice();
        this.round = 1;
    }

    public boolean nextPlayer() {
        boolean success;
        synchronized (this.playerOrder) {
            if (this.round == 1 && this.game.getPlayers() != null) {
                this.activePlayer = this.game.getPlayers().get(0);
                this.playerOrder.add(this.activePlayer);
                this.playerOrder.notifyAll();
                return this.activePlayer != null;
            }

            List<Player> playerList = game.getPlayers();
            int activePlayerIndex = playerList.indexOf(activePlayer);
            int playerPlusOne = activePlayerIndex + 1;
            int maxPlayerIndex = playerList.size();
            int nextPlayerIndex = playerPlusOne % maxPlayerIndex;

            System.out.println(playerPlusOne + " % " + maxPlayerIndex + " = " + nextPlayerIndex);
            this.activePlayer = playerList.get(nextPlayerIndex);
            success = this.playerOrder.add(this.activePlayer);
            System.out.println("nextPlayer: " +  this.activePlayer.getPlayerName());
            this.playerOrder.notify();
        }
        return success;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public boolean rollDice() {
        this.rollCount += 1;
        Board board = this.game.getBoard();
        int result;
        boolean success;
        do {
            result = this.dice.roll();
            System.out.printf("terning rullet: %d\n", result);
            boolean rolledSixThreeTimes = this.rollCount == 3 && result == 6;
            if (rolledSixThreeTimes || this.rollCount > 3) {
                System.out.println("You have had too much luck, and RNG Jesus have struck you down");
                Tile startTile = board.getTiles().get(0);
                this.activePlayer.getPawn().place(startTile);
                success = true;
            } else {
                success = board.movePawn(activePlayer.getPawn(), result);
            }
        } while (success && result == 6);
        if (!success) {
            throw new RuntimeException("failed to move player");
        }

        Tile roundEndTile = activePlayer.getPawn().getTile();
        if (board.isGoal(roundEndTile)) {
            boolean successfullySetWinner = this.game.setWinner(activePlayer);
            if (!successfullySetWinner) {
                throw new RuntimeException("Failed to set winner");
            }
        } else {
            System.out.println("REEEE");
            success = this.nextPlayer();
        }
        if (!success) {
            throw new RuntimeException("Failed to set next player");
        }
        this.round++;
        return success;
    }

    public GameStatus getGameStatus() {
        return this.game.getGameStatus();
    }

    @Override
    public void run() {
        try {
            GameStatus gameStatus = this.getGameStatus();

            while (gameStatus.equals(GameStatus.STARTED)) {
                gameStatus = this.getGameStatus();
                Player activePlayer = this.playerOrder.peek();
                synchronized (this.playerOrder) {
                    Player currentPlayer = this.activePlayer;
                    currentPlayer.start();
                    while (activePlayer.equals(currentPlayer)) {
                        currentPlayer = this.activePlayer;
                        this.playerOrder.wait();
                    }
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
