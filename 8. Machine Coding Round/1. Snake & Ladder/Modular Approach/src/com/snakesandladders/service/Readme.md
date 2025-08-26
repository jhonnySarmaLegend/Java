
# Snakes and Ladders Console Game

A classic Snakes and Ladders game implemented in Java, designed for a fixed set of four players. This console-based application demonstrates fundamental object-oriented programming principles, turn-based game logic, and thread-safe unique ID generation.

## Features

*   **Fixed Player Count:** The game is designed to be played by **exactly 4 players**. Any attempt to start with a different number of players will result in an error.
*   **Atomic Player IDs:** Each player is automatically assigned a unique, sequential player ID using an **atomic counter** (`java.util.concurrent.atomic.AtomicInteger`), ensuring thread-safe ID generation even in concurrent environments (though the game itself is single-threaded in its current form).
*   **Dynamic Dice Rolling:** Players roll two standard dice (totaling 2-12) to determine their movement.
*   **Snakes and Ladders Logic:** Automated movement down snakes or up ladders when landing on their start points.
*   **Turn-Based Gameplay:** Players take turns in a round-robin fashion until a winner is determined.
*   **Console Output:** Clear, real-time updates on dice rolls, player movements, and game status.

## Description

This project provides a simple yet complete implementation of the traditional Snakes and Ladders board game. The game simulates a 100-square board where players move their tokens based on dice rolls. The objective is to be the first player to reach square 100. The game enforces a strict rule of four players, and each player is internally managed with a unique, automatically assigned atomic ID, ensuring distinct player identification throughout the game.

## Project Structure

The project is organized into a `SnakesAndLadders` package, following a clear separation of concerns for each game component:

SnakesAndLadders/
├── Player.java         # Defines player attributes, position, and generates atomic IDs.
├── Dice.java           # Handles dice rolling mechanism.
├── Jumper.java         # Represents either a snake or a ladder (start and end points).
├── GameBoard.java      # Manages the game board, player positions, and Jumper logic.
├── GameEngine.java     # Orchestrates the game flow, turns, and winning conditions.
└── Main.java           # The main entry point to run the game with predefined players and setup.

## How to Run

To compile and run this Java console game:

1.  **Save the files:** Ensure all `.java` files (Player.java, Dice.java, Jumper.java, GameBoard.java, GameEngine.java, Main.java) are saved within a directory named `SnakesAndLadders`.
2.  **Navigate to the parent directory:** Open your terminal or command prompt and navigate to the directory that *contains* the `SnakesAndLadders` directory (e.g., if `SnakesAndLadders` is in `~/projects/`, navigate to `~/projects/`).
3.  **Compile:** Compile the Java source files:
        javac SnakesAndLadders/*.java
    ```
4.  **Run:** Execute the main class:
    ```bash
    java SnakesAndLadders.Main
    ```

The game will then start running in your console.

## Example Output

Here's a sample of what you might see when running the game, demonstrating player turns, movements, and a winner announcement. Note the unique IDs assigned to each player.

Current player: Priya [ID: 1]
Priya [ID: 1] rolled a 7
Moved to position 7
Current player: Aman [ID: 2]
Aman [ID: 2] rolled a 5
Moved to position 5
Current player: Rahul [ID: 3]
Rahul [ID: 3] rolled a 9
Yay! Climbed a ladder at 9
Moved to position 22
Current player: Sneha [ID: 4]
Sneha [ID: 4] rolled a 6
Moved to position 6
Current player: Priya [ID: 1]
Priya [ID: 1] rolled a 10
Moved to position 17
Oops! Snake bite at 17
Moved to position 8
...
Current player: Priya [ID: 1]
Priya [ID: 1] rolled a 6
Moved to position 100
Priya [ID: 1] has won the game!
```
