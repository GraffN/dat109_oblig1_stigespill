package com.example.oblig1.services;

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
        Player initialPlayer = game.getPlayers().get(0);
        this.setActivePlayer(initialPlayer);
        this.rollCount = 0;
        this.dice = new Dice();

        this.round = 1;
    }

    private void setActivePlayer(final Player player) {
        this.playerOrder.add(player);
        this.activePlayer = player;
    }

    private void nextPlayer() {
        if (this.round == 1) {
            return;
        }
        List<Player> playerList = game.getPlayers();
        int activePlayerIndex = playerList.indexOf(activePlayer);
        int nextPlayerIndex = (activePlayerIndex + 1) % playerList.size();
        this.activePlayer = playerList.get(nextPlayerIndex);
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void rollDice() {
        this.rollCount += 1;
        Board board = this.game.getBoard();
        int result;
        do {
            result = this.dice.roll();
            boolean rolledSixThreeTimes = this.rollCount == 3 && result == 6;
            if (rolledSixThreeTimes || this.rollCount > 3) {
                System.out.println("You have had too much luck, and RNG Jesus have struck you down");
                Tile startTile = board.getTiles().get(0);
                this.activePlayer.getPawn().place(startTile);
                return;
            }
            board.movePawn(activePlayer.getPawn(), result);
        } while (result == 6);
        Tile roundEndTile = activePlayer.getPawn().getTile();
        if (board.isGoal(roundEndTile)) {
            boolean successfullySetWinner = this.game.setWinner(activePlayer);
            if (!successfullySetWinner) {
                throw new RuntimeException("Failed to set winner");
            }
            return;
        }
        this.nextPlayer();
        this.round++;
    }

    public GameStatus getGameStatus() {
        return this.game.getGameStatus();
    }

    @Override
    public void run() {
        try {

            this.game.setGameStatus(GameStatus.STARTED);
            GameStatus gameStatus = this.getGameStatus();

            while (gameStatus.equals(GameStatus.STARTED)) {
                Player activePlayer = playerOrder.peek();
                synchronized (playerOrder) {
                    Player currentPlayer = this.activePlayer;

                    while (activePlayer.equals(currentPlayer)) {
                        currentPlayer = this.activePlayer;
                        this.wait();
                    }
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
