package SnL.Model;


import java.util.Random;  

public class Dice {  
    private final int numberOfDice;  
    private final Random random = new Random();  

    public Dice(int numberOfDice) {  
        this.numberOfDice = numberOfDice;  
    }  

    public int rollDice() {  
        int sum = 0;  
        for (int i = 0; i < numberOfDice; i++) {  
            sum += random.nextInt(6) + 1;  
        }  
        return sum;  
    }  
}  
