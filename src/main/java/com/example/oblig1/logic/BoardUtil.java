package com.example.oblig1.logic;
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
                boolean isLadder = random.nextInt(margin) == 0;
                Tile origin = tiles.get(random.nextInt(offset, tiles.size() - offset - margin));
                while (origin.getTileItem() != null) {
                    origin = tiles.get(random.nextInt(offset, tiles.size() - offset - margin));
                }
                int slideAndLadderBounds = 9;
                if (isLadder) {
                    int minEndIndex = origin.getIndex() + offset;
                    Tile end = tiles.get(random.nextInt(minEndIndex, Math.min(tiles.size() - margin, minEndIndex + slideAndLadderBounds)));
                    Ladder ladder = new Ladder(origin, end);
                    origin.setTileItem(ladder);
                } else {
                    int maxEndIndex = origin.getIndex() - offset;
                    Tile end = tiles.get(random.nextInt(Math.max(margin - 1, maxEndIndex - slideAndLadderBounds), maxEndIndex));
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
