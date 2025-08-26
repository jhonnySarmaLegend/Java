package com.snakesandladders.board;

import com.snakesandladders.model.Jumper;

import java.util.List;
import java.util.Optional;

public class GameBoard {
    private final int size;
    private final List<Jumper> snakes;
    private final List<Jumper> ladders;

    public GameBoard(int size, List<Jumper> snakes, List<Jumper> ladders) {
        this.size = size;
        this.snakes = snakes;
        this.ladders = ladders;
    }

    /** Given a tentative landing cell, returns the final cell after snake/ladder. */
    public int resolveJumpers(int cell) {
        Optional<Jumper> snake = snakes.stream()
                .filter(s -> s.getStart() == cell)
                .findFirst();
        if (snake.isPresent()) {
            System.out.printf("  Bitten by snake at %d, down to %d%n", cell, snake.get().getEnd());
            return snake.get().getEnd();
        }

        Optional<Jumper> ladder = ladders.stream()
                .filter(l -> l.getStart() == cell)
                .findFirst();
        if (ladder.isPresent()) {
            System.out.printf("  Climbed ladder at %d, up to %d%n", cell, ladder.get().getEnd());
            return ladder.get().getEnd();
        }

        return cell;
    }

    public int getSize() {
        return size;
    }
}
