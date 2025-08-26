package com.snakesandladders.model;

import java.util.Random;

public class Dice {
    private final int numberOfDice;
    private final Random rand = new Random();

    public Dice(int numberOfDice) {
        this.numberOfDice = numberOfDice;
    }

    /** Roll 1..6*numberOfDice */
    public int roll() {
        return rand.nextInt(6 * numberOfDice) + 1;
    }
}
