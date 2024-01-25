package com.example.oblig1.classes;

import java.util.ArrayList;

public class Player {
    private static int nextId = 0;

    private final int id;
    private String name;
    private Tile tile;
    private Pawn pawn;
    public Player() {
        this.id = nextId;
        nextId++;
        this.pawn = new Pawn(this);
    }
    public Player(PawnColor color) {
        this();
        this.pawn = new Pawn(this, color);
    }
    public Player(String name) {
        this();
        this.name = name;
    }
    public Player(String name, PawnColor pawnColor) {
        this(name);
        this.pawn = new Pawn(this, pawnColor);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Tile getTile() {
        return tile;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }
}
