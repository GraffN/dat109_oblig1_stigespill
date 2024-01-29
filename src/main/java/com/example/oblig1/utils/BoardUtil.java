package com.example.oblig1.utils;
import com.example.oblig1.classes.Ladder;
import com.example.oblig1.classes.Slide;
import com.example.oblig1.classes.Tile;

import java.util.List;
import java.util.Random;

public class BoardUtil {
    public static boolean placeTileItems(final List<Tile> tiles) {
        try {
            Random random = new Random();
            int base = tiles.size() / 8;
            int slidesAndLaddersCount = random.nextInt(base - 3, base + 3);
            for (int i = 0; i < slidesAndLaddersCount; i++) {
                boolean isLadder = random.nextInt(2) == 0;
                Tile origin = tiles.get(random.nextInt(0, tiles.size() - 1));
                while (origin.getTileItem() != null) {
                    origin = tiles.get(random.nextInt(0, tiles.size() -1));
                }
                int slideAndLadderBounds = 9;
                if (isLadder) {
                    int minEndIndex = origin.getIndex() + 3;
                    Tile end = tiles.get(random.nextInt(minEndIndex, minEndIndex + slideAndLadderBounds));
                    Ladder ladder = new Ladder(origin, end);
                    origin.setTileItem(ladder);
                } else {
                    int maxEndIndex = origin.getIndex() + 3;
                    Tile end = tiles.get(random.nextInt(maxEndIndex - slideAndLadderBounds, maxEndIndex));
                    Slide slide = new Slide(origin, end);
                    origin.setTileItem(slide);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
