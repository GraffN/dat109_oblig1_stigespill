package com.example.oblig1.interfaces;

import com.example.oblig1.classes.Tile;

public interface TileItem {
    public int getId();

    public void setId(int id);

    public Tile getOriginTile();

    public void setOriginTile(Tile originTile);

    public Tile getEndTile();

    public void setEndTile(Tile endTile);
}
