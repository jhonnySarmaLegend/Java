package SnL.App;



import java.util.Arrays;  
import java.util.LinkedList;  
import java.util.List;  
import java.util.Queue;

import SnL.Board.GameBoard;
import SnL.Model.*;
import SnL.Service.GameEngine;  

public class main {  
    public static void main(String[] args) {  
        Player p1 = new Player("Priya");  
        Player p2 = new Player("Aman");  
        Player p3 = new Player("Rahul");  
        Player p4 = new Player("Sneha");  

        Queue<Player> players = new LinkedList<>(  
            Arrays.asList(p1, p2, p3, p4)  
        );  

        Dice dice = new Dice(2);  

        List<Jumper> snakes = Arrays.asList(  
            new Jumper(16, 8),  
            new Jumper(98, 13)  
        );  
        List<Jumper> ladders = Arrays.asList(  
            new Jumper(3, 22),  
            new Jumper(36, 89)  
        );  

        GameBoard board = new GameBoard(100, snakes, ladders);  
        for (Player p : players) {  
            board.addPlayer(p.getPlayerId());  
        }  

        GameEngine engine = new GameEngine(board, players, dice);  
        engine.startGame();  
    }  
}  
