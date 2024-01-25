package com.example.oblig1.classes;

import com.example.oblig1.interfaces.TileItem;

import java.util.ArrayList;

public class Tile {
    private static int nextId = 0;
    private final int id;
    private int index;
    private ArrayList<Player> playersOnTile;
    private TileItem tileItem;

    public Tile() {
        this.id = nextId;
        nextId++;
    }

    public Tile(int index) {
        this();
        this.index = index;
    }

    public int getId() {
        return this.id;
    }

    public int getIndex() {
        return this.index;
    }

    public ArrayList<Player> getPlayersOnTile() {
        return this.playersOnTile;
    }

    public void setPlayersOnTile(ArrayList<Player> playersOnTile) {
        this.playersOnTile = playersOnTile;
    }

    public TileItem getTileItem() {
        return tileItem;
    }

    public void setTileItem(TileItem tileItem) {
        this.tileItem = tileItem;
    }

}
