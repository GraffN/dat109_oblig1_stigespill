package com.example.oblig1.classes;

import com.example.oblig1.interfaces.TileItem;

import java.util.ArrayList;

public class Tile {
    private static int nextId = 0;
    private final int id;
    private final int index;
    private final ArrayList<Pawn> pawnsOnTile;
    private TileItem tileItem;

    public Tile(final int index) {
        this.id = nextId;
        this.index = index;
        this.pawnsOnTile = new ArrayList<>();
        nextId++;
    }

    public int getId() {
        return this.id;
    }

    public int getIndex() {
        return this.index;
    }

    public ArrayList<Pawn> getPawnsOnTile() {
        return this.pawnsOnTile;
    }

    public boolean removePawnFromTile(final Pawn pawn) {
        return this.pawnsOnTile.remove(pawn);
    }
    public boolean addPawnToTile(final Pawn pawn) {
        pawn.place(this);
        return this.pawnsOnTile.add(pawn);
    }

    public void onPlace(final Pawn pawn) {
        Tile end = this.tileItem.getEndTile();
        if (end == null) {
            return;
        }

        pawn.place(end);
    }

    public TileItem getTileItem() {
        return tileItem;
    }

    public void setTileItem(final TileItem tileItem) {
        this.tileItem = tileItem;
    }

}
