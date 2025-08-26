package SnL.Board;


import java.util.List;  
import java.util.Map;

import SnL.Model.Jumper;

import java.util.HashMap;  

public class GameBoard {  
    private final int boardSize;  
    private final List<Jumper> snakes;  
    private final List<Jumper> ladders;  
    private final Map<Integer, Integer> playerPositions = new HashMap<>();  

    public GameBoard(int boardSize, List<Jumper> snakes, List<Jumper> ladders) {  
        this.boardSize = boardSize;  
        this.snakes = snakes;  
        this.ladders = ladders;  
    }  

    public void addPlayer(int playerId) {  
        playerPositions.put(playerId, 0);  
    }  

    public void movePlayer(int playerId, int steps) {  
        int current = playerPositions.get(playerId);  
        int dest = current + steps;  

        if (dest > boardSize) {  
            System.out.println("Cannot move beyond board size.");  
            return;  
        }  

        for (Jumper snake : snakes) {  
            if (snake.startPoint == dest) {  
                System.out.println("Oops! Snake bite at " + dest);  
                dest = snake.endPoint;  
                break;  
            }  
        }  

        for (Jumper ladder : ladders) {  
            if (ladder.startPoint == dest) {  
                System.out.println("Yay! Climbed a ladder at " + dest);  
                dest = ladder.endPoint;  
                break;  
            }  
        }  

        playerPositions.put(playerId, dest);  
        System.out.println("Moved to position " + dest);  
    }  

    public boolean isWinner(int playerId) {  
        return playerPositions.get(playerId) == boardSize;  
    }  

    public int getPlayerPosition(int playerId) {  
        return playerPositions.get(playerId);  
    }  
}  
