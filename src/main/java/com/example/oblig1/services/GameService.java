package com.example.oblig1.services;

import com.example.oblig1.classes.*;
import com.example.oblig1.interfaces.GameStatus;

import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GameService implements Runnable {
    private final Game game;
    private final Stack<Player> playerOrder;
    private Player activePlayer;
    private final Dice dice;
    private int round;
    private final BlockingQueue<Player> turnQueue = new LinkedBlockingQueue<>();

    public GameService(final Game game) {
        this.game = game;
        this.playerOrder = new Stack<>();
        this.dice = new Dice();
        this.round = -1;
    }

    public Stack<Player> getPlayerOrder() {
        return this.playerOrder;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void nextPlayer(Player player) {
        this.activePlayer = player;
    }

    public boolean rollDice(Player player) {
        synchronized (this) {
            if (!this.activePlayer.equals(player)) {
                throw new IllegalStateException("It's not your turn!");
            }

            this.round++;
            Board board = this.game.getBoard();
            int result;
            boolean success;
            do {
                result = this.dice.roll();
                System.out.printf("Player %s rolled the dice: %d\n", activePlayer.getPlayerName(), result);
                boolean rolledSixThreeTimes = this.round > 0 && result == 6;
                if (rolledSixThreeTimes) {
                    System.out.println("Player has rolled six three times. Returning to start.");
                    Tile startTile = board.getTiles().get(0);
                    activePlayer.getPawn().place(startTile);
                    success = true;
                } else {
                    success = board.movePawn(activePlayer.getPawn(), result);
                }
            } while (success && result == 6);
            if (!success) {
                throw new RuntimeException("Failed to move player");
            }

            Tile roundEndTile = activePlayer.getPawn().getTile();
            if (board.isGoal(roundEndTile)) {
                boolean successfullySetWinner = this.game.setWinner(activePlayer);
                if (!successfullySetWinner) {
                    throw new RuntimeException("Failed to set winner");
                }
            }
            this.round++;
            activePlayer.notifyTurnEnd(); // Notify the player that their turn is done
            int nextPlayerIndex = round % this.game.getPlayers().size();
            Player nextPlayer = this.game.getPlayers().get(nextPlayerIndex);
            this.nextPlayer(nextPlayer);
            return success;
        }
    }

    public GameStatus getGameStatus() {
        return this.game.getGameStatus();
    }

    public void notifyPlayerTurn(Player player) {
        turnQueue.offer(player);
    }

    @Override
    public void run() {
        this.game.getPlayers().forEach(Player::run);
        synchronized (this) {
            try {
                GameStatus gameStatus = this.getGameStatus();

                while(gameStatus.equals(GameStatus.STARTED)) {
                    gameStatus = this.getGameStatus();
                    synchronized(this.playerOrder) {
                        Player currentPlayer = this.activePlayer;
                        this.playerOrder.add(currentPlayer);
                        this.playerOrder.notifyAll();

                        while(activePlayer.equals(currentPlayer)) {
                            currentPlayer = this.activePlayer;
                            this.playerOrder.wait();
                        }
                    }
                }

            } catch (InterruptedException var7) {
                throw new RuntimeException(var7);
            }
        }
    }
}
