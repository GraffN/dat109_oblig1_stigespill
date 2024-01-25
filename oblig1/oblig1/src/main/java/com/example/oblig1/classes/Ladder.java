package com.example.oblig1.classes;

import com.example.oblig1.interfaces.TileItem;

public class Ladder implements TileItem {
    private static int nextId = 0;
    private int id;
    private Tile originTile;
    private Tile endTile;

    public Ladder() {
        this.id = nextId;
        nextId++;
    }

    public Ladder(Tile originTile, Tile endTile) {
        this();
        this.originTile = originTile;
        this.endTile = endTile;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tile getOriginTile() {
        return this.originTile;
    }

    public void setOriginTile(Tile originTile) {
        this.originTile = originTile;
    }

    public Tile getEndTile() {
        return this.endTile;
    }

    public void setEndTile(Tile endTile) {
        this.endTile = endTile;
    }
}
