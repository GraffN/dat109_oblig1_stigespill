package com.example.oblig1.classes;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private int tiles = 60;
    private final List<Dice> dices;
    private List<Player> players;
    private Board board;

    private Game() {
        this.dices = new ArrayList<>();
        this.players = new ArrayList<>();
        this.addDices(1);
    }

    public Game(List<String> players) {
        this();
        this.addPlayers(players);
        this.createBoard();
    }

    public List<Dice> getDices() {
        return dices;
    }

    private boolean addDice() {
        Dice diceToAdd = new Dice();
        return this.dices.add(diceToAdd);
    }

    private boolean addDices(int count) {
        for (int i = 0; i < count; i++) {
            if (!this.addDice()) {
                return false;
            }
        }
        return true;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    private boolean addPlayer() {
        PawnColor pawnColor = PawnColor.getNextPawnColor(players);
        return players.add(new Player());
    }
    public boolean addPlayer(String name) {
        PawnColor pawnColor = PawnColor.getNextPawnColor(players);
        return players.add(new Player(name, pawnColor));
    }
    public boolean addPlayer(String name, PawnColor color) {
        if (players.stream().anyMatch((p) -> p.getPawn().getColor() == color)) {
            return false;
        }
        return this.players.add(new Player(name, color));
    }

    public boolean addPlayers(List<String> names) {
        List<Player> prevList = this.getPlayers();
        for (String name : names) {
            if (!this.addPlayer(name)) {
                this.players = prevList;
                return false;
            }
        }
        return true;
    }

    public Board getBoard() {
        return board;
    }

    private void createBoard() {
        this.board = new Board(this.tiles);
        this.board.setPawns(this.players.stream().map(Player::getPawn).toList());
    }
}
