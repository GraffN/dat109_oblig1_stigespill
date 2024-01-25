package com.example.oblig1.classes;

import com.example.oblig1.logic.BoardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private static int nextId = 0;
    private final int id;
    private List<Pawn> pawns;
    private List<Tile> tiles;

    public Board() {
        this.id = nextId;
        nextId++;
        this.pawns = new ArrayList<>();
        this.tiles = new ArrayList<>();
    }
    public Board(int tileCount) {
        this();
        if (!this.addTiles(tileCount)) {
            throw new RuntimeException("failed to create tiles");
        }
        if (!BoardUtil.placeTileItems(this.tiles)) {
            throw new RuntimeException("failed to place ladders and slides on board");
        }
    }

    public int getId() {
        return this.id;
    }

    public List<Pawn> getPawns() {
        return this.pawns;
    }

    public List<Tile> getTiles() {
        return this.tiles;
    }

    private boolean addTile() {
        Tile tileToAdd = new Tile(this.tiles.size());
        return this.tiles.add(tileToAdd);
    }

    private boolean addTiles(int count) {
        List<Tile> prevTiles = this.getTiles();
        for (int i = 0; i < count; i++) {
            if (!this.addTile()) {
                this.tiles = prevTiles;
                return false;
            }
        }
        return true;
    }

    public boolean addPawn(Pawn pawn) {
        if (this.pawns.size() > 4) {
            return false;
        }

        return this.pawns.add(pawn);
    }

    public boolean setPawns(List<Pawn> pawns) {
        List<Pawn> prevList = this.pawns;
        boolean valid = pawns.stream().map(this::addPawn).anyMatch((added) -> !added);
        if (!valid) {
            this.pawns = prevList;
        }
        return valid;
    }
}
