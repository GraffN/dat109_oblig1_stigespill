package com.example.oblig1.classes;
import com.example.oblig1.utils.BoardUtil;
import java.util.ArrayList;
import java.util.List;

public class Board extends Thread {
    private static int nextId = 0;
    private final int tileId;
    private List<Tile> tiles;
    public Board() {
        this.tileId = nextId;
        nextId++;
        this.tiles = new ArrayList<>();
    }
    public Board(final int tileCount) {
        this();
        if (!this.addTiles(tileCount)) {
            throw new RuntimeException("failed to create tiles");
        }
        if (!BoardUtil.placeTileItems(this.tiles)) {
            throw new RuntimeException("failed to place ladders and slides on board");
        }
    }

    public int getTileId() {
        return this.tileId;
    }

    public List<Tile> getTiles() {
        return this.tiles;
    }

    private boolean addTile() {
        Tile tileToAdd = new Tile(this.tiles.size());
        return this.tiles.add(tileToAdd);
    }

    private boolean addTiles(final int count) {
        List<Tile> prevTiles = this.getTiles();
        for (int i = 0; i < count; i++) {
            if (!this.addTile()) {
                this.tiles = prevTiles;
                return false;
            }
        }
        return true;
    }

    public Tile getStartTile() {
        return this.tiles.get(0);
    }

    public boolean addPawn(final Pawn pawn) {
        if (this.pawns.size() > 4) {
            return false;
        }
        Tile startTile = this.tiles.get(0);
        pawn.place(startTile);
        return this.pawns.add(pawn);
    }

    public boolean setPawns(final List<Pawn> pawns) {
        List<Pawn> prevList = this.pawns;
        boolean valid = pawns.stream().allMatch(this::addPawn);
        if (!valid) {
            this.pawns = prevList;
        }
        return valid;
    }

    public boolean movePawn(final Pawn pawn, final int roll) {
        Tile goal = tiles.get(tiles.size() -1);
        Tile currentTile = pawn.getTile();
        int currentIndex = this.tiles.indexOf(currentTile);
        int moves = roll;
        boolean success = true;
        for (int i = roll; i > 0; i--) {
            try {
                moves--;
                int direction = roll - moves;
                System.out.println();
                Tile nextTile = this.tiles.get(currentIndex + direction);
                if (nextTile.equals(goal)) {
                    moves = 0;
                }
                success = pawn.place(nextTile);
            } catch (Exception e) {
                e.printStackTrace();
                success = false;
            }
        }
        if (!success) {
            return success;
        }
        success = currentTile.removePawnFromTile(pawn);
        if (!success) {
            return success;
        }
        currentTile = pawn.getTile();
        success = currentTile.addPawnToTile(pawn);
        return success;
    }

    public boolean isGoal(final Tile tile) {
        return this.tiles.indexOf(tile) == this.tiles.size() -1;
    }
}
