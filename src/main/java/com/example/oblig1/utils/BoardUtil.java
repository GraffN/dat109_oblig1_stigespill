package com.example.oblig1.utils;
import com.example.oblig1.Main;
import com.example.oblig1.classes.Ladder;
import com.example.oblig1.classes.Slide;
import com.example.oblig1.classes.Tile;

import java.util.List;
import java.util.Random;

public class BoardUtil {
    public static boolean placeTileItems(final List<Tile> tiles) {
        try {
            Random random = new Random();
            int offset = 3;
            int margin = 2;
            int base = tiles.size() / 8;
            int slidesAndLaddersCount = random.nextInt(base - offset, base + offset);
            for (int i = 0; i < slidesAndLaddersCount; i++) {
                boolean isLadder = random.nextInt(0, 1) == 0;
                Tile originTile = tiles.get(random.nextInt(offset, tiles.size() - offset - margin));
                while (originTile.getTileItem() != null) {
                    originTile = tiles.get(random.nextInt(offset, tiles.size() - offset - margin));
                }
                int originIndex = originTile.getIndex();
                int offsetLimit = 9;
                if (isLadder) {
                    int endIndex = random.nextInt(originIndex + margin, originIndex + offsetLimit);
                    endIndex = Math.min(tiles.size() - 1, endIndex);
                    Tile endTile = tiles.get(endIndex);
                    Ladder ladder = new Ladder(originTile, endTile);
                    originTile.setTileItem(ladder);
                } else {
                    int endIndex = random.nextInt(originIndex - offsetLimit, originIndex - margin);
                    endIndex = Math.max(endIndex, margin);
                    Tile endTile = tiles.get(endIndex);
                    Slide slide = new Slide(originTile, endTile);
                    originTile.setTileItem(slide);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
