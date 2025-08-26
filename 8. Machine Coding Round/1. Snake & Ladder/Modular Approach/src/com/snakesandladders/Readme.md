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

## 8. License

This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for details.

---

**Enjoy the game, and happy coding!**
