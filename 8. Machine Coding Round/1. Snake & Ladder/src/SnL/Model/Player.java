package SnL.Model;

import java.util.concurrent.atomic.AtomicInteger;  
  
public class Player {  
    private static final AtomicInteger idCounter = new AtomicInteger(1);  
    private final int playerId;  
    private final String playerName;  
    private int position;  

    public Player(String playerName) {  
        this.playerId = idCounter.getAndIncrement();  
        this.playerName = playerName;  
        this.position = 0;  
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
