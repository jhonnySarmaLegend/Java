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
        /*
        Difference from add():

           offer() returns false if the element cannot be added (e.g., in a bounded queue that's full).
           add() throws an IllegalStateException if the element cannot be added.

        */
        turnQueue.offer(player);
    }

    /** Runs the main game loop */
    public void start() {
        int finalCell = board.getSize();
        while (turnQueue.size() > 1) {
            /*
               Difference from remove():

                 poll() returns null if the queue is empty.
                 remove() throws a NoSuchElementException if the queue is empty.
            */
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
