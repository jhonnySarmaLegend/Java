package com.snakesandladders.app;

import com.snakesandladders.board.GameBoard;
import com.snakesandladders.model.Dice;
import com.snakesandladders.model.Jumper;
import com.snakesandladders.model.Player;
import com.snakesandladders.service.GameEngine;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1) Create board elements
        List<Jumper> snakes = List.of(
            new Jumper(16, 8),
            new Jumper(98, 13)
        );
        List<Jumper> ladders = List.of(
            new Jumper(3, 22),
            new Jumper(36, 89)
        );
        GameBoard board = new GameBoard(100, snakes, ladders);

        // 2) Create dice
        Dice dice = new Dice(1);

        // 3) Hook up engine, add players
        GameEngine engine = new GameEngine(dice, board);
        engine.addPlayer(new Player("Priya"));
        engine.addPlayer(new Player("Aman"));

        // 4) Start!
        engine.start();
    }
}
