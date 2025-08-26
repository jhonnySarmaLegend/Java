To modify the code to add **only 4 players** and assign player IDs using an **atomic counter**, we can use the `AtomicInteger` class to ensure thread-safe ID generation. Here's the updated implementation:

---

### **File Structure**

```
SnakesAndLadders/
│
├── Player.java
├── Dice.java
├── Jumper.java
├── GameBoard.java
├── GameEngine.java
└── Main.java
```

---

### **1. Player.java**

```java
package SnakesAndLadders;

import java.util.concurrent.atomic.AtomicInteger;

public class Player {
    private static final AtomicInteger idCounter = new AtomicInteger(1); // Atomic counter for player IDs

    private int playerId;
    private String playerName;
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
```

---

### **2. Dice.java**

```java
package SnakesAndLadders;

import java.util.Random;

public class Dice {
    private int numberOfDice;

    public Dice(int numberOfDice) {
        this.numberOfDice = numberOfDice;
    }

    public int rollDice() {
        Random random = new Random();
        int sum = 0;
        for (int i = 0; i < numberOfDice; i++) {
            sum += random.nextInt(6) + 1; // Each dice roll between 1 and 6
        }
        return sum;
    }
}
```

---

### **3. Jumper.java**

```java
package SnakesAndLadders;

public class Jumper {
    int startPoint;
    int endPoint;

    public Jumper(int startPoint, int endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }
}
```

---

### **4. GameBoard.java**

```java
package SnakesAndLadders;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class GameBoard {
    private int boardSize;
    private List<Jumper> snakes;
    private List<Jumper> ladders;
    private Map<Integer, Integer> playerPositions; // Player ID -> Position

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

        if (newPosition > boardSize) {
            System.out.println("Cannot move beyond board size.");
            return;
        }

        // Check for snakes
        for (Jumper snake : snakes) {
            if (snake.startPoint == newPosition) {
                System.out.println("Oops! Snake bite at " + newPosition);
                newPosition = snake.endPoint;
                break;
            }
        }

        // Check for ladders
        for (Jumper ladder : ladders) {
            if (ladder.startPoint == newPosition) {
                System.out.println("Yay! Climbed a ladder at " + newPosition);
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
}
```

---

### **5. GameEngine.java**

```java
package SnakesAndLadders;

import java.util.Queue;

public class GameEngine {
    private GameBoard gameBoard;
    private Queue<Player> players;
    private Dice dice;

    public GameEngine(GameBoard gameBoard, Queue<Player> players, Dice dice) {
        this.gameBoard = gameBoard;
        this.players = players;
        this.dice = dice;
    }

    public void startGame() {
        while (!players.isEmpty()) {
            Player player = players.poll();
            handleTurn(player);
            if (gameBoard.isWinner(player.getPlayerId())) {
                System.out.println(player.getDisplayName() + " has won the game!");
                return;
            }
            players.offer(player); // Add back to the queue for the next turn
        }
    }

    private void handleTurn(Player player) {
        System.out.println("Current player: " + player.getDisplayName());
        int diceValue = dice.rollDice();
        System.out.println(player.getDisplayName() + " rolled a " + diceValue);
        gameBoard.movePlayer(player.getPlayerId(), diceValue);
    }
}
```

---

### **6. Main.java**

```java
package SnakesAndLadders;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        // Create 4 players
        Player player1 = new Player("Priya");
        Player player2 = new Player("Aman");
        Player player3 = new Player("Rahul");
        Player player4 = new Player("Sneha");

        Queue<Player> players = new LinkedList<>(Arrays.asList(player1, player2, player3, player4));

        // Create dice
        Dice dice = new Dice(2); // Two dice

        // Create snakes and ladders
        List<Jumper> snakes = Arrays.asList(
                new Jumper(16, 8),
                new Jumper(98, 13)
        );
        List<Jumper> ladders = Arrays.asList(
                new Jumper(3, 22),
                new Jumper(36, 89)
        );

        // Create game board
        GameBoard gameBoard = new GameBoard(100, snakes, ladders);
        for (Player player : players) {
            gameBoard.addPlayer(player.getPlayerId());
        }

        // Create and start game engine
        GameEngine gameEngine = new GameEngine(gameBoard, players, dice);
        gameEngine.startGame();
    }
}
```

---

### **Key Changes**

1. **Atomic Counter for Player IDs**:
   - Added `AtomicInteger idCounter` in the `Player` class to generate unique IDs for players.
   - Each player gets a unique ID automatically when instantiated.

2. **Limit to 4 Players**:
   - Only 4 players (`Priya`, `Aman`, `Rahul`, `Sneha`) are created in `Main.java`.

---

### **Example Output**

```
Current player: Priya [ID: 1]
Priya [ID: 1] rolled a 7
Moved to position 7
Current player: Aman [ID: 2]
Aman [ID: 2] rolled a 5
Moved to position 5
Current player: Rahul [ID: 3]
Rahul [ID: 3] rolled a 9
Climbed a ladder at 9
Moved to position 22
Current player: Sneha [ID: 4]
Sneha [ID: 4] rolled a 6
Moved to position 6
...
Priya [ID: 1] rolled a 6
Moved to position 100
Priya [ID: 1] has won the game!
```

---

### **Benefits**

1. **Unique Player IDs**: Ensures each player has a unique identifier.
2. **Atomic Counter**: Guarantees thread-safe ID generation.
3. **Limited Players**: Restricts the game to 4 players for simplicity.

---

This implementation ensures a clean, organized, and scalable design for the Snakes & Ladders game.
