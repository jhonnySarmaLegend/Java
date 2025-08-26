# Snakes & Ladders Game

A modular, object-oriented implementation of the classic **Snakes & Ladders** board game in Java. Players take turns rolling dice, climbing ladders, and avoiding snakes until someone reaches the final square.

---

## Table of Contents

1. **Overview**  
2. **Features**  
3. **Architecture & Packages**  
4. **Getting Started**  
   1. Prerequisites  
   2. Installation & Build  
   3. Running the Game  
5. **Project Structure**  
6. **Extending the Game**  
7. **Contributing**  
8. **License**

---

## 1. Overview

This project simulates a simple Snakes & Ladders game:

- Players roll dice to advance along a linear board of size 100.  
- Landing on the bottom of a ladder sends you up; landing on a snake’s head sends you down.  
- The first player to land exactly on square 100 wins.

The design is split into clear packages for **models**, **board logic**, **game flow**, and the application **entry point**.

---

## 2. Features

- *Configurable* number of dice and board size  
- *Data models* for Player, Dice, and Jumper (snake/ladder)  
- *GameBoard* class to resolve snakes and ladders  
- *GameEngine* service managing turn order, dice rolls, and win conditions  
- *Modular* package structure for easy testing and extension  

---

## 3. Architecture & Packages

- **com.snakesandladders.model**  
  Contains core data classes:  
  - `Player`  
  - `Dice`  
  - `Jumper`  

- **com.snakesandladders.board**  
  Holds `GameBoard`, which defines the board size and resolves jumps.

- **com.snakesandladders.service**  
  Provides `GameEngine` for the main game loop, turn management, and win logic.

- **com.snakesandladders.app**  
  Includes `Main`, the application entry point that wires everything together.

---

## 4. Getting Started

### 4.1 Prerequisites

- Java Development Kit (JDK) 8 or higher  
- Apache Maven or any build tool of your choice  

### 4.2 Installation & Build

```bash
# Clone the repository
git clone https://github.com/your-username/snakesandladders.git
cd snakesandladders

# Build with Maven
mvn clean package
```

> If you prefer a manual compilation:  
> ```bash
> javac -d out \
>   src/com/snakesandladders/model/*.java \
>   src/com/snakesandladders/board/*.java \
>   src/com/snakesandladders/service/*.java \
>   src/com/snakesandladders/app/*.java
> ```

### 4.3 Running the Game

```bash
# Using Maven
mvn exec:java -Dexec.mainClass="com.snakesandladders.app.Main"

# Or manually
java -cp out com.snakesandladders.app.Main
```

---

## 5. Project Structure

```
src/
└── com/snakesandladders
    ├── app
    │   └── Main.java
    ├── model
    │   ├── Player.java
    │   ├── Dice.java
    │   └── Jumper.java
    ├── board
    │   └── GameBoard.java
    └── service
        └── GameEngine.java
```

- **Main.java**: Bootstraps the game, creates board, dice, players, and starts the engine.  
- **Player.java**: Stores player name and current position.  
- **Dice.java**: Rolls 1…6×n for n dice.  
- **Jumper.java**: Represents a snake or ladder (start → end).  
- **GameBoard.java**: Knows board size, list of snakes, ladders, and applies jumps.  
- **GameEngine.java**: Manages turns, dice rolls, movement, and win detection.

---

## 6. Extending the Game

- **Add more players**  
  Just call `engine.addPlayer(new Player("Name"));` in `Main`.

- **Custom board size**  
  Change the `GameBoard` constructor argument.

- **More snakes & ladders**  
  Pass additional `Jumper(start, end)` instances in the lists.

- **Multiple dice**  
  Instantiate `new Dice(numberOfDice)` with your desired count.

- **GUI or Networked Play**  
  Replace `GameEngine`’s `System.out` calls with UI or remote calls.

---

## 7. Contributing

1. Fork the repository  
2. Create a feature branch (`git checkout -b feature/...`)  
3. Commit your changes (`git commit -m "Add feature"`)  
4. Push to your branch (`git push origin feature/...`)  
5. Open a Pull Request  

Please follow existing code style, include tests, and update this README with any new features.


---




# Snakes & Ladders Game

An implementation of the classic Snakes & Ladders board game in Java, designed with modularity, robustness, and scalability in mind. This project focuses on handling edge cases gracefully and provides a foundation for extending the game with new features.

---

## Table of Contents

1. [Overview](#overview)
2. [Features](#features)
3. [Edge Cases & Solutions](#edge-cases--solutions)
4. [Getting Started](#getting-started)
5. [Project Structure](#project-structure)
6. [Extending the Game](#extending-the-game)
7. [Contributing](#contributing)
8. [License](#license)

---

## Overview

This project simulates the Snakes & Ladders game, where players roll dice to advance along a board, climb ladders, and avoid snakes. The first player to land exactly on the final square wins.

---

## Features

- **Modular Code**: Separate packages for models, board logic, and game flow.
- **Configurable Board**: Supports custom board sizes, snakes, and ladders.
- **Player Management**: Handles duplicate player names using unique identifiers.
- **Edge Case Handling**: Gracefully manages invalid dice rolls, snake/ladder loops, and more.
- **Extensible Design**: Easily add features like multiple dice, custom rules, or persistence.

---

## Edge Cases & Solutions

### 1. **Player Name Collisions**

**Problem**: Two players with the same name join the game.  
**Solution**: Assign each player a unique identifier (UUID) and display it alongside their name.
this.id = UUID.randomUUID().toString();

```java
Player alice1 = new Player("Alice"); // Alice [f47ac10b]
Player alice2 = new Player("Alice"); // Alice [9c0a4f37]
```

---

### 2. **Board Size & Customization**

**Problem**: The board size or snake/ladder configuration may vary.  
**Solution**: Initialize `GameBoard` with customizable parameters.

```java
GameBoard board = new GameBoard(100, List.of(
    new Jumper(16, 8), new Jumper(98, 13)
), List.of(
    new Jumper(3, 22), new Jumper(36, 89)
));
```

---

### 3. **Invalid Dice Rolls**

**Problem**: Rolls may exceed the board size.  
**Solution**: Skip the turn if `currentPosition + diceValue > boardSize`.

```java
if (currentPosition + diceValue > boardSize) {
    System.out.println("Cannot move—need exact roll to finish.");
    turnQueue.offer(player);
    continue;
}
```

---

### 4. **Snake/Ladder Loops**

**Problem**: Ladders or snakes create infinite loops.  
**Solution**: Validate jumpers during board initialization.

```java
public void validateJumpers() {
    for (Jumper jumper : jumpers) {
        if (jumper.getStart() == jumper.getEnd()) {
            throw new IllegalArgumentException("Invalid jumper: start == end.");
        }
    }
}
```

---

### 5. **Multiple Dice**

**Problem**: Players may want to roll multiple dice.  
**Solution**: Add `numberOfDice` as a parameter to `Dice`.

```java
public int roll() {
    int sum = 0;
    for (int i = 0; i < numberOfDice; i++) {
        sum += rand.nextInt(6) + 1;
    }
    return sum;
}
```

---

### 6. **Winning Conditions**

**Problem**: Multiple players may land on the final cell simultaneously.  
**Solution**: Declare the first player to land exactly on `boardSize` as the winner.

```java
if (tentative == finalCell) {
    System.out.printf("%s reaches %d and wins! 🎉%n", player.getDisplayName(), finalCell);
    return;
}
```

---

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher  
- Apache Maven or any build tool of your choice  

### Installation

```bash
# Clone the repository
git clone https://github.com/your-username/snakesandladders.git
cd snakesandladders

# Build with Maven
mvn clean package
```

### Running the Game

```bash
# Using Maven
mvn exec:java -Dexec.mainClass="com.snakesandladders.app.Main"

# Or manually
java -cp out com.snakesandladders.app.Main
```

---

## Project Structure

```
src/
└── com/snakesandladders
    ├── app
    │   └── Main.java
    ├── model
    │   ├── Player.java
    │   ├── Dice.java
    │   └── Jumper.java
    ├── board
    │   └── GameBoard.java
    └── service
        └── GameEngine.java
```

---

## Extending the Game

1. **Add More Players**: Call `engine.addPlayer(new Player("Name"))`.  
2. **Custom Board Size**: Use `GameBoard(boardSize, snakes, ladders)`.  
3. **Multiple Dice**: Instantiate `new Dice(numberOfDice)`.  
4. **Save/Restore Game**: Serialize game state to JSON or a file.  

---

## Contributing

1. Fork the repository.  
2. Create a feature branch.  
3. Commit your changes.  
4. Push to your branch.  
5. Open a Pull Request.  

Please follow existing code style, include tests, and update this README with any new features.


**Enjoy the game, and happy coding!** 🚀

**Enjoy the game, and happy coding!**
