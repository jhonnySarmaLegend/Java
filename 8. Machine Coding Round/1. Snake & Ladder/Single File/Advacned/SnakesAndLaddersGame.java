// SnakesAndLaddersGame.java

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

// Main class - Entry point for the game
public class SnakesAndLaddersGame {

    public static void main(String[] args) {
        // Create 4 players
        Player player1 = new Player("Priya");
        Player player2 = new Player("Aman");
        Player player3 = new Player("Rahul");
        Player player4 = new Player("Sneha");

        // The game requires exactly 4 players as per current GameEngine design
        // Changed to LinkedList for Queue operations
        Queue<Player> players = new LinkedList<>(Arrays.asList(player1, player2, player3, player4));

        // Create dice (e.g., two dice)
        Dice dice = new Dice(2);

        // Define snakes
        List<Jumper> snakes = Arrays.asList(
                new Jumper(16, 8),
                new Jumper(98, 13),
                new Jumper(48, 26),
                new Jumper(64, 43),
                new Jumper(87, 57)
        );

        // Define ladders
        List<Jumper> ladders = Arrays.asList(
                new Jumper(3, 22),
                new Jumper(36, 89),
                new Jumper(10, 32),
                new Jumper(28, 50),
                new Jumper(71, 92)
        );

        // Create game board (e.g., 100 cells)
        GameBoard gameBoard = new GameBoard(100, snakes, ladders);

        // Add players to the game board (initial position 0)
        for (Player player : players) {
            gameBoard.addPlayer(player.getPlayerId());
        }

        // Create and start game engine
        GameEngine gameEngine = new GameEngine(gameBoard, players, dice);
        gameEngine.startGame();
    }
}

// Player.java
class Player {
    private static final AtomicInteger idCounter = new AtomicInteger(1); // Atomic counter for player IDs
    private final int playerId;
    private final String playerName;
    private int position;

    public Player(String playerName) {
        this.playerId = idCounter.getAndIncrement(); // Assign unique ID
        this.playerName = playerName;
        this.position = 0; // Start at position 0
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getDisplayName() {
        return playerName + " [ID: " + playerId + "]";
    }
}

// Dice.java
class Dice {
    private final int numberOfDice;
    private final Random random = new Random();

    public Dice(int numberOfDice) {
        this.numberOfDice = numberOfDice;
    }

    public int rollDice() {
        int sum = 0;
        for (int i = 0; i < numberOfDice; i++) {
            sum += random.nextInt(6) + 1; // Each dice roll between 1 and 6
        }
        return sum;
    }
}

// Jumper.java
class Jumper {
    public final int startPoint;
    public final int endPoint;

    public Jumper(int startPoint, int endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }
}

// GameBoard.java
class GameBoard {
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

        if (currentPosition == boardSize) {
            // Player has already won, no further moves
            return;
        }

        if (newPosition > boardSize) {
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
        return playerPositions.get(playerId) == boardSize;
    }

    public int getPlayerPosition(int playerId) {
        return playerPositions.get(playerId);
    }

    public int getBoardSize() {
        return boardSize;
    }
}

// GameEngine.java
class GameEngine {
    private final GameBoard gameBoard;
    private final Queue<Player> players; // Active players
    private final List<Player> winners;  // To store 1st, 2nd, 3rd winners
    private final Dice dice;
    private static final int MAX_WINNERS = 3; // Declare winners up to 3rd place

    public GameEngine(GameBoard gameBoard, Queue<Player> players, Dice dice) {
        // Enforce exactly 4 players as per design, and ensure enough for MAX_WINNERS
        if (players.size() < MAX_WINNERS) {
            throw new IllegalArgumentException("At least " + MAX_WINNERS + " players required to declare " + MAX_WINNERS + " winners.");
        }
        this.gameBoard = gameBoard;
        this.players = players;
        this.winners = new LinkedList<>(); // Initialize winners list
        this.dice = dice;
    }

    public void startGame() {
        System.out.println("Game started! First " + MAX_WINNERS + " players to reach " + gameBoard.getBoardSize() + " will be declared winners.");

        // Continue as long as we haven't found MAX_WINNERS and there are still active players
        while (winners.size() < MAX_WINNERS && !players.isEmpty()) {
            Player currentPlayer = players.poll(); // Get player for current turn

            // A winning player should not be in the queue, but this is a safeguard
            if (gameBoard.isWinner(currentPlayer.getPlayerId())) {
                System.out.println(currentPlayer.getDisplayName() + " is already at the finish line. Skipping turn.");
                // Do not re-add to players queue as they've already won
                continue;
            }

            handleTurn(currentPlayer);

            if (gameBoard.isWinner(currentPlayer.getPlayerId())) {
                winners.add(currentPlayer); // Add to winners list
                System.out.println("\n--- " + currentPlayer.getDisplayName() + " has reached position " + gameBoard.getBoardSize() + "! ---");
                System.out.println("Current Winners: " + getWinnersRanking());

                if (winners.size() == MAX_WINNERS) {
                    System.out.println("\n*** " + MAX_WINNERS + " winners declared! Game Over. ***");
                    break; // Stop the game if we have enough winners
                }
            } else {
                // Only add back to queue if not a winner
                players.offer(currentPlayer);
            }
            System.out.println("\n----------------------------------\n"); // Separator for turns
        }

        // After the loop, display final results
        displayFinalResults();
    }

    private void handleTurn(Player player) {
        System.out.println("Current player: " + player.getDisplayName() + " at position " + gameBoard.getPlayerPosition(player.getPlayerId()));
        int diceValue = dice.rollDice();
        System.out.println(player.getDisplayName() + " rolled a " + diceValue);
        gameBoard.movePlayer(player.getPlayerId(), diceValue);
    }

    private String getWinnersRanking() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < winners.size(); i++) {
            String suffix;
            if ((i + 1) == 1) suffix = "st";
            else if ((i + 1) == 2) suffix = "nd";
            else if ((i + 1) == 3) suffix = "rd";
            else suffix = "th"; // Though we only go up to 3rd

            sb.append((i + 1)).append(suffix).append(" Place: ").append(winners.get(i).getDisplayName());
            if (i < winners.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private void displayFinalResults() {
        System.out.println("\n===== Game Results =====");
        if (!winners.isEmpty()) {
            System.out.println("Winners:");
            for (int i = 0; i < winners.size(); i++) {
                String suffix;
                if ((i + 1) == 1) suffix = "st";
                else if ((i + 1) == 2) suffix = "nd";
                else if ((i + 1) == 3) suffix = "rd";
                else suffix = "th";

                System.out.println((i + 1) + suffix + " Place: " + winners.get(i).getDisplayName());
            }
        } else {
            System.out.println("No winners declared.");
        }

        if (!players.isEmpty()) {
            System.out.println("\nRemaining Players (Did not finish / Losers):");
            // The 'players' queue now contains those who didn't win
            for (Player player : players) {
                System.out.println("- " + player.getDisplayName() + " at position " + gameBoard.getPlayerPosition(player.getPlayerId()));
            }
        } else if (winners.size() < MAX_WINNERS) {
            // This case handles when not enough players finish, but the queue is also empty
            System.out.println("\nNo other players remaining to declare more winners.");
        }
        System.out.println("========================");
    }
}
