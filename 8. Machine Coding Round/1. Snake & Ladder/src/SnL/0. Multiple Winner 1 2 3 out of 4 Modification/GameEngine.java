package SnakesAndLadders;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GameEngine {

    private final GameBoard gameBoard;
    private final Queue<Player> players; // Active players
    private final List<Player> winners;  // To store 1st, 2nd, 3rd winners
    private final Dice dice;
    private static final int MAX_WINNERS = 3; // Declare winners up to 3rd place

    public GameEngine(GameBoard gameBoard, Queue<Player> players, Dice dice) {
        if (players.size() < MAX_WINNERS) {
            throw new IllegalArgumentException("At least " + MAX_WINNERS + " players required to declare " + MAX_WINNERS + " winners.");
        }
        this.gameBoard = gameBoard;
        this.players = players;
        this.winners = new LinkedList<>(); // Initialize winners list
        this.dice = dice;
    }

    public void startGame() {
        System.out.println("Game started! First " + MAX_WINNERS + " players to reach " + gameBoard.getBoardSize() + " will be declared winners.");

        // Continue as long as we haven't found MAX_WINNERS and there are still active players
        while (winners.size() < MAX_WINNERS && !players.isEmpty()) {
            Player currentPlayer = players.poll(); // Get player for current turn

            // Check if player has already won in a previous turn (should not happen with poll/offer logic, but good for robustness)
            if (gameBoard.isWinner(currentPlayer.getPlayerId())) {
                // If somehow a winner is still in the queue, skip their turn and don't re-add
                continue;
            }

            handleTurn(currentPlayer);

            if (gameBoard.isWinner(currentPlayer.getPlayerId())) {
                winners.add(currentPlayer); // Add to winners list
                System.out.println("\n--- " + currentPlayer.getDisplayName() + " has reached position " + gameBoard.getBoardSize() + "! ---");
                System.out.println("Current Winners: " + getWinnersRanking());

                if (winners.size() == MAX_WINNERS) {
                    System.out.println("\n*** " + MAX_WINNERS + " winners declared! Game Over. ***");
                    break; // Stop the game if we have enough winners
                }
            } else {
                // Only add back to queue if not a winner
                players.offer(currentPlayer);
            }
            System.out.println("\n----------------------------------\n"); // Separator for turns
        }

        // Handle scenario where game ends because no more players can win (e.g., less than 3 players remain and no one can reach finish)
        if (winners.size() < MAX_WINNERS && players.isEmpty()) {
            System.out.println("\nGame ended. Not enough players reached the finish line to declare " + MAX_WINNERS + " winners.");
            System.out.println("Final Winners: " + getWinnersRanking());
        }
    }

    private void handleTurn(Player player) {
        System.out.println("Current player: " + player.getDisplayName() + " at position " + gameBoard.getPlayerPosition(player.getPlayerId()));
        int diceValue = dice.rollDice();
        System.out.println(player.getDisplayName() + " rolled a " + diceValue);
        gameBoard.movePlayer(player.getPlayerId(), diceValue);
    }

    private String getWinnersRanking() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < winners.size(); i++) {
            sb.append((i + 1)).append("st/nd/rd Place: ").append(winners.get(i).getDisplayName());
            if (i < winners.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
