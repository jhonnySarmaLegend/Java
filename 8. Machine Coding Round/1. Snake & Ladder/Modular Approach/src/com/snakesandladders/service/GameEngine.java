package com.snakesandladders.service;

import com.snakesandladders.board.GameBoard;
import com.snakesandladders.model.Dice;
import com.snakesandladders.model.Player;

import java.util.LinkedList;
import java.util.Queue;

public class GameEngine {
    private final Dice dice;
    private final GameBoard board;
    private final Queue<Player> turnQueue = new LinkedList<>();

    public GameEngine(Dice dice, GameBoard board) {
        this.dice = dice;
        this.board = board;
    }

    /** Add players in turn order */
    public void addPlayer(Player player) {
        turnQueue.offer(player);
    }

    /** Runs the main game loop */
    public void start() {
        int finalCell = board.getSize();
        while (turnQueue.size() > 1) {
            Player p = turnQueue.poll();
            int roll = dice.roll();
            System.out.printf("%s rolls %d…%n", p.getName(), roll);

            int tentative = p.getPosition() + roll;
            if (tentative > finalCell) {
                System.out.println("  Cannot move—need exact roll to finish.");
                turnQueue.offer(p);
                continue;
            }

            if (tentative == finalCell) {
                System.out.printf("%s reaches %d and wins! 🎉%n", p.getName(), finalCell);
                return;
            }

            // resolve snakes or ladders
            int newPos = board.resolveJumpers(tentative);
            p.setPosition(newPos);
            System.out.printf("  %s moves to %d%n", p.getName(), newPos);
            turnQueue.offer(p);
        }

        System.out.println("Game ends in a draw.");
    }
}
