package SnakesAndLadders;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class GameBoard {
    private final int boardSize;
    private final List<Jumper> snakes;
    private final List<Jumper> ladders;
    private final Map<Integer, Integer> playerPositions; // Player ID -> Position

    public GameBoard(int boardSize, List<Jumper> snakes, List<Jumper> ladders) {
        this.boardSize = boardSize;
        this.snakes = snakes;
        this.ladders = ladders;
        this.playerPositions = new HashMap<>();
    }

    public void addPlayer(int playerId) {
        playerPositions.put(playerId, 0); // Start at position 0
    }

    public void movePlayer(int playerId, int steps) {
        int currentPosition = playerPositions.get(playerId);
        int newPosition = currentPosition + steps;

        // A player who has already won should not move further.
        // This check is important if a winning player is somehow still processed in the GameEngine.
        if (currentPosition == boardSize) {
            System.out.println(playerPositions.get(playerId) + " is already at the finish line. No further moves.");
            return;
        }


        if (newPosition > boardSize) {
            // Optional: Implement "bounce-back" or "must land exactly" rule here
            System.out.println("Cannot move beyond board size " + boardSize + ". Player remains at " + currentPosition);
            newPosition = currentPosition; // Player stays at current position if they overshoot
        }


        // Check for snakes
        for (Jumper snake : snakes) {
            if (snake.startPoint == newPosition) {
                System.out.println("Oops! Snake bite at " + newPosition + ". Moving to " + snake.endPoint);
                newPosition = snake.endPoint;
                break;
            }
        }

        // Check for ladders
        for (Jumper ladder : ladders) {
            if (ladder.startPoint == newPosition) {
                System.out.println("Yay! Climbed a ladder at " + newPosition + ". Moving to " + ladder.endPoint);
                newPosition = ladder.endPoint;
                break;
            }
        }

        playerPositions.put(playerId, newPosition);
        System.out.println("Moved to position " + newPosition);
    }

    public boolean isWinner(int playerId) {
        // A player is a winner if their position is exactly the boardSize
        return playerPositions.get(playerId) == boardSize;
    }

    public int getPlayerPosition(int playerId) {
        return playerPositions.get(playerId);
    }

    public int getBoardSize() {
        return boardSize;
    }
}
