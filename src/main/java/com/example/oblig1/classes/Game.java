package com.example.oblig1.classes;

import com.example.oblig1.interfaces.GameStatus;
import com.example.oblig1.interfaces.PawnColor;
import com.example.oblig1.logic.GameService;

import java.util.ArrayList;
import java.util.List;

public class Game implements Runnable {
    private List<Player> players;
    private Board board;

    private GameStatus gameStatus;

    private final GameService gameService;
    private Player winner;



    private Game() {
        this.gameStatus = GameStatus.NOT_STARTED;
        this.players = new ArrayList<>();
        this.gameService = new GameService(this);
    }

    public Game(final List<String> players) {
        this();
        this.addPlayers(players);
        this.createBoard();
    }


    public List<Player> getPlayers() {
        return this.players;
    }

    private boolean addPlayer() {
        PawnColor pawnColor = PawnColor.getNextPawnColor(players);
        return players.add(new Player(this.gameService));
    }
    public boolean addPlayer(final String name) {
        PawnColor pawnColor = PawnColor.getNextPawnColor(players);
        return players.add(new Player(this.gameService, name, pawnColor));
    }
    public boolean addPlayer(final String name, final PawnColor color) {
        if (players.stream().anyMatch((p) -> p.getPawn().getColor() == color)) {
            return false;
        }
        return this.players.add(new Player(this.gameService, name, color));
    }

    public boolean addPlayers(final List<String> names) {
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
        this.board = new Board(100);
        this.board.setPawns(this.players.stream().map(Player::getPawn).toList());
    }

    public boolean setGameStatus(GameStatus status) {
        if (this.gameStatus.compareTo(status) < 0) {
            return false;
        }
        this.gameStatus = status;
        return true;
    }

    public GameStatus getGameStatus() {
        return this.gameStatus;
    }
    private void startGame() {
        this.gameService.run();
    }

    public boolean setWinner(final Player winner) {
        if (this.gameStatus.equals(GameStatus.ENDED)) {
            return false;
        }
        System.out.printf("%s har vunnet spillet!\n gatulerer!!%n", winner.getPlayerName());
        this.winner = winner;
        this.gameStatus = GameStatus.ENDED;
        return true;
    }

    public Player getWinner() {
        return this.winner;
    }

    @Override
    public void run() {

    }
}