package com.example.oblig1.classes;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum PawnColor {
    BLUE,
    RED,
    YELLOW,
    GREEN,
    WHITE,
    BLACK;
    public static String getColor(PawnColor pawnColor) {
        return switch (pawnColor) {
            case RED -> "#ff0000";
            case GREEN -> "#00ff00";
            case BLUE -> "#0000ff";
            case CYAN -> "#00ffff";
            case MAGENTA -> "#ff00ff";
            case YELLOW -> "#ffff00"
            case BLACK -> "#000000";
            case WHITE -> "#ffffff";
        };
    }
    public static PawnColor get(int id) {
       return Arrays.stream(PawnColor.values()).filter((p) -> p.ordinal() == id).findFirst().orElse(null);
    }

    public static PawnColor getNextPawnColor(List<Player> players) {
        if (players.size() > 0) {
            return Arrays
                    .stream(PawnColor.values())
                    .filter((c) -> players.stream().anyMatch((p) -> !p.getPawn().getColor().equals(c)))
                    .findFirst()
                    .orElse(null);
        } else {
            Random random = new Random();
            return PawnColor.get(random.nextInt(0, PawnColor.values().length - 1));
        }
    }
}
