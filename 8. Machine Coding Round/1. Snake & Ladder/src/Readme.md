
# Snakes and Ladders Console Game

A classic Snakes and Ladders game implemented in Java, designed for a fixed set of four players. This console-based application demonstrates fundamental object-oriented programming principles, turn-based game logic, and thread-safe unique ID generation.

## Features

*   **Fixed Player Count:** The game is designed to be played by **exactly 4 players**. Any attempt to start with a different number of players will result in an error, ensuring the intended game experience.
*   **Atomic Player IDs:** Each player is automatically assigned a unique, sequential player ID using an **atomic counter** (`java.util.concurrent.atomic.AtomicInteger`), ensuring thread-safe and distinct player identification throughout the game.
*   **Dynamic Dice Rolling:** Players roll two standard dice (totaling 2-12) to determine their movement.
*   **Snakes and Ladders Logic:** Automated movement down snakes or up ladders when landing on their start points.
*   **Turn-Based Gameplay:** Players take turns in a round-robin fashion until the game concludes.
*   **Single-Place Win Detection:** The game concludes immediately once the **1st place winner is declared**, providing a focused race to the finish line.
*   **Console Output:** Clear, real-time updates on dice rolls, player movements, snake bites, ladder climbs, and winner announcements.

## Description

This project provides a simple yet complete implementation of the traditional Snakes and Ladders board game. The game simulates a 100-square board where players move their tokens based on dice rolls. The objective is to be the first player to reach square 100. The game strictly enforces a four-player limit, and each player is internally managed with a unique, automatically assigned atomic ID, ensuring distinct player identification throughout the game. The gameplay proceeds turn by turn until the first player crosses the finish line.



The game will then start running in your console.

## Example Output

Here's a sample of what you might see when running the game, demonstrating player turns, movements, and the single winner announcement. Note the unique IDs assigned to each player.

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
...
Priya [ID: 1] rolled a 6
Moved to position 100
Priya [ID: 1] has won the game!
```
