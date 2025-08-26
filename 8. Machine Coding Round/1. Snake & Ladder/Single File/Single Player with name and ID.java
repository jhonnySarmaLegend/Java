import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

// PLAYER CLASS
class Player {
    private String playerName;
    private int playerId; // Unique ID for each player
    private static AtomicInteger counter = new AtomicInteger(0); // Atomic counter for generating IDs

    public Player(String name) {
        this.playerName = name;
        this.playerId = counter.incrementAndGet(); // Assign a unique ID using atomic counter
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerId() {
        return playerId;
    }

    // Format: "PlayerName [ID]"
    public String getDisplayName() {
        return playerName + " [ID: " + playerId + "]";
    }
}

// DICE CLASS
class Dice {
    private int numberOfDice;

    public Dice(int numberOfDice) {
        this.numberOfDice = numberOfDice;
    }

    int rollDice() {
        return ((int) (Math.random() * 6 * numberOfDice)) + 1;
    }
}

// JUMPER CLASS
class Jumper {
    int startPoint;
    int endPoint;

    public Jumper(int startPoint, int endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }
}

// GAMEBOARD CLASS
class GameBoard {
    private Dice dice;
    private Queue<Player> nextTurn;
    private List<Jumper> snakes;
    private List<Jumper> ladders;
    private Map<Integer, Integer> playersCurrentPosition; // Use player ID as key
    private int boardSize;

    public GameBoard(Dice dice, Queue<Player> nextTurn, List<Jumper> snakes, List<Jumper> ladders,
                     Map<Integer, Integer> playersCurrentPosition, int boardSize) {
        this.dice = dice;
        this.nextTurn = nextTurn;
        this.snakes = snakes;
        this.ladders = ladders;
        this.playersCurrentPosition = playersCurrentPosition;
        this.boardSize = boardSize;
    }

    public void startGame() {
        while (nextTurn.size() > 1) {
            Player player = nextTurn.poll();
            int currentPosition = playersCurrentPosition.get(player.getPlayerId());
            int diceValue = dice.rollDice();

            if (currentPosition + diceValue > boardSize) {
                nextTurn.offer(player);
            } else if (currentPosition + diceValue == boardSize) {
                System.out.println(player.getDisplayName() + " has won the game!");
                return;
            } else {
                int nextCell = currentPosition + diceValue;
                boolean gotLadder = false;

                for (Jumper snake : snakes) {
                    if (snake.startPoint == nextCell) {
                        nextCell = snake.endPoint;
                        System.out.println(player.getDisplayName() + " was bitten by a snake at " + currentPosition + "!");
                        break;
                    }
                }

                for (Jumper ladder : ladders) {
                    if (ladder.startPoint == nextCell) {
                        nextCell = ladder.endPoint;
                        gotLadder = true;
                        System.out.println(player.getDisplayName() + " climbed a ladder at " + currentPosition + "!");
                        break;
                    }
                }

                if (nextCell == boardSize) {
                    System.out.println(player.getDisplayName() + " has won the game!");
                    return;
                }

                playersCurrentPosition.put(player.getPlayerId(), nextCell);
                System.out.println(player.getDisplayName() + " is now at position " + nextCell);
                nextTurn.offer(player);
            }
        }

        System.out.println("Game ended in a draw!");
    }
}

// Main Function
public class Main {
    public static void main(String[] args) {
        // Creating Dice
        Dice dice = new Dice(1);

        // Adding Players
        Player player1 = new Player("Priya");
        Player player2 = new Player("Priya"); // Duplicate name
        Queue<Player> allPlayers = new LinkedList<>(Arrays.asList(player1, player2));

        // Setting up Snakes and Ladders
        List<Jumper> snakes = List.of(new Jumper(16, 8), new Jumper(98, 13));
        List<Jumper> ladders = List.of(new Jumper(3, 22), new Jumper(36, 89));

        // Starting Positions
        Map<Integer, Integer> playerPositions = new HashMap<>();
        playerPositions.put(player1.getPlayerId(), 0);
        playerPositions.put(player2.getPlayerId(), 0);

        // Initialize and Start Game
        GameBoard gameBoard = new GameBoard(dice, allPlayers, snakes, ladders, playerPositions, 100);
        gameBoard.startGame();
    }
}
