package bot;

public interface ChooseMoveStrategy {

    // Choose the best move for the given circumstance
    /**
        @return The column where you want to place the move
     */
    int chooseMove();
}
