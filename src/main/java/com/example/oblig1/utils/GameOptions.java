package com.example.oblig1.utils;

import java.util.Random;

public class GameOptions {
    private int tilesX;
    private int tilesY;
    private int maxPlayers;
    private int numberOfObstacles;

    public GameOptions() {
        this.tilesX = 10;
        this.tilesY = 10;
        this.maxPlayers = 4;
        this.numberOfObstacles = new Random().nextInt(tilesX-1, tilesY+1);
    }

    public GameOptions(final int maxPlayers) {
        this();
        this.maxPlayers = maxPlayers;
    }

    public GameOptions(final int maxPlayers, int tiles) {
        this();
        double root = Math.sqrt(tiles);
        this.tilesX = (int)Math.floor(root);
        int extraTiles = Math.floorMod(tiles, tilesX);
        this.tilesY = tilesX + extraTiles;
        this.maxPlayers = maxPlayers;
        this.numberOfObstacles = new Random().nextInt(tilesX-1, tilesX+2);
    }

    public int getTilesX() {
        return tilesX;
    }

    public int getTilesY() {
        return tilesY;
    }

    public int tiles() {
        return this.tilesY * this.tilesX;
    }

    public int maxPlayers() {
        return maxPlayers;
    }

    public int numberOfObstacles() {
        return numberOfObstacles;
    }
}
