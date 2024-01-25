package com.example.oblig1.classes;

import java.util.Random;

public class Dice {
    private static int nextId = 0;
    private final int id;
    private Integer result;

    Random random = new Random();

    public Dice() {
        this.id = nextId;
        nextId++;
        this.result = null;
    }

    public Integer roll() {
        this.result = random.nextInt(1, 6);
        return this.result;
    }

    public int getId() {
        return this.id;
    }

    public Integer getResult() {
        return this.result;
    }
}
