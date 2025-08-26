package SnL.Service;

import java.util.Queue;

import SnL.Board.GameBoard;
import SnL.Model.Dice;
import SnL.Model.Player;  

public class GameEngine {  
    private final GameBoard gameBoard;  
    private final Queue<Player> players;  
    private final Dice dice;  

    public GameEngine(GameBoard gameBoard, Queue<Player> players, Dice dice) {  
        if (players.size() != 4) {  
            throw new IllegalArgumentException("Exactly 4 players required");  
        }  
        this.gameBoard = gameBoard;  
        this.players = players;  
        this.dice = dice;  
    }  

    public void startGame() {  
        while (!players.isEmpty()) {  
            Player current = players.poll();  
            handleTurn(current);  

            if (gameBoard.isWinner(current.getPlayerId())) {  
                System.out.println(current.getDisplayName() + " has won the game!");  
                return;  
            }  

            players.offer(current);  
        }  
    }  

    private void handleTurn(Player player) {  
        System.out.println("Current player: " + player.getDisplayName());  
        int roll = dice.rollDice();  
        System.out.println(player.getDisplayName() + " rolled a " + roll);  
        gameBoard.movePlayer(player.getPlayerId(), roll);  
    }  
}  
