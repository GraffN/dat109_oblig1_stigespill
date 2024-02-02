package com.example.oblig1.classes;

import com.example.oblig1.interfaces.GameStatus;
import com.example.oblig1.interfaces.PawnColor;
import com.example.oblig1.services.GameService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Game extends Thread {
    private List<Player> players;
    private Board board;

    private GameStatus gameStatus;
    private final GameService gameService;
    private Player winner;
    private Integer numberOfTiles;


    public Game(final List<String> players) {
        this.players = new ArrayList<>();
        this.gameService = new GameService(this);
        this.addPlayers(players);
        this.gameStatus = GameStatus.NOT_STARTED;
        boolean createdBoard = this.createBoard();
        if (!createdBoard) {
            throw new RuntimeException("Failed to create board");
        }
        Player startingPlayer = this.players.get(0);
        this.gameService.nextPlayer(startingPlayer);
    }

    public Game(final List<String> players, final Integer numberOfTiles) {
        this(players);
        this.numberOfTiles = numberOfTiles;
    }


    public List<Player> getPlayers() {
        return this.players;
    }

    public boolean addPlayer(final String name) {
        PawnColor pawnColor = PawnColor.getNextPawnColor(players);
        return players.add(new Player(this.gameService, name, pawnColor));
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

    private boolean createBoard() {
        int numberOfTiles = this.numberOfTiles != null ? this.numberOfTiles : 100;
        this.board = new Board(numberOfTiles);
        Tile startTile = this.board.getStartTile();
        return this.players.stream().anyMatch((p) -> startTile.addPawnToTile(p.getPawn()));
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
        this.gameStatus = GameStatus.STARTED;
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
        this.startGame();
    }
}
