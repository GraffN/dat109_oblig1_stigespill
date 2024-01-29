package com.example.oblig1.classes;

import com.example.oblig1.interfaces.PawnColor;

public class Pawn extends Thread {
    private static int nextId = 0;
    private final int pawnId;
    private PawnColor color;
    private Player player;
    private Tile tile;

    public Pawn() {
        this.pawnId = nextId;
        nextId++;
        this.color = PawnColor.get(this.pawnId);
    }
    public Pawn(final Player player) {
        this();
        this.player = player;
    }
    public Pawn(final Player player, final PawnColor pawnColor) {
        this(player);
        this.color = pawnColor;
    }
    public int getPawnId() {
        return pawnId;
    }
    public Tile getTile() {
        return tile;
    }

    public PawnColor getColor() {
        return color;
    }
    public Player getPlayer() {
        return player;
    }

    public boolean place(final Tile tile) {
        this.tile = tile;
        return tile.onPlace(this);
    }
}
