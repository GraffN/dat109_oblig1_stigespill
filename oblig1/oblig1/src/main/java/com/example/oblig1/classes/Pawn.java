package com.example.oblig1.classes;

public class Pawn {
    private static int nextId = 0;
    private final int id;
    private PawnColor color;
    private Player player;

    public Pawn() {
        this.id = nextId;
        nextId++;
        this.color = PawnColor.get(this.id);
    }

    public Pawn(Player player) {
        this();
        this.player = player;
    }

    public Pawn(Player player, PawnColor pawnColor) {
        this(player);
        this.color = pawnColor;
    }

    public int getId() {
        return id;
    }

    public PawnColor getColor() {
        return color;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
