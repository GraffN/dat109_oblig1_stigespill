package com.example.oblig1;

import com.example.oblig1.classes.Game;
import com.example.oblig1.classes.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        List<String> players = promptUserForPlayers();

        Game game = new Game(players);
        for (Player player : game.getPlayers()) {
            player.startPlayerThread();
        }
        game.start(); // Start the game's thread
    }

    private static List<String> promptUserForPlayers() {
        List<String> nameList = null;

        Predicate<List<String>> isValid = (list) -> list != null && list.size() >= 2 && list.size() <= 4;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Skriv navn på deltakere split med \",\"\nminimun 2, max 4 spillere");

        while (!isValid.test(nameList)) {
            System.out.print("Deltakere: ");
            String names = scanner.nextLine();
            nameList = Arrays.stream(names.split(",\\s*", 4)).toList();
            if (!isValid.test(nameList)) {
                System.out.println("minimum 2 og maksimum 2 spillere, separer navn med \",\"");
            }
        }

        return nameList;
    }
}
