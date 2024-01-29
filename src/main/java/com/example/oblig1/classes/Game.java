package com.example.oblig1.classes;

import com.example.oblig1.interfaces.GameStatus;
import com.example.oblig1.interfaces.PawnColor;
import com.example.oblig1.services.GameService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Game implements Runnable {
    private List<Player> players;
    private Board board;

    private GameStatus gameStatus;
    private final GameService gameService;
    private Player winner;
    private Integer numberOfTiles;

    private Game() {
        this.players = new ArrayList<>();
        this.gameService = new GameService(this);
    }

    public Game(final List<String> players) {
        this();
        this.addPlayers(players);
        this.createBoard();
    }

    public Game(final List<String> players, final Integer numberOfTiles) {
        this();
        this.numberOfTiles = numberOfTiles;
        this.addPlayers(players);
        this.createBoard();
    }


    public List<Player> getPlayers() {
        return this.players;
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
        int numberOfTiles = Optional.of(this.numberOfTiles).orElse(100);
        this.board = new Board(numberOfTiles);
        Tile startTile = this.board.getStartTile();
        this.players.forEach((p) -> startTile.addPawnToTile(p.getPawn()));
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
